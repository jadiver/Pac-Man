import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;

public class PacMan extends JPanel implements ActionListener, KeyListener {
    class Block {
        int x, y, width, height;
        Image image;
        int startX, startY;
        char direction = 'U';
        int velocityX = 0, velocityY = 0;

        Block(Image image, int x, int y, int width, int height) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.startX = x;
            this.startY = y;
        }

        void updateDirection(char direction) {
            char prevDirection = this.direction;
            this.direction = direction;
            updateVelocity();
            this.x += this.velocityX;
            this.y += this.velocityY;
            checkCollisionWithWalls(prevDirection);
        }

        void updateVelocity() {
            switch (this.direction) {
                case 'U':
                    this.velocityX = 0;
                    this.velocityY = -tileSize / 4;
                    break;
                case 'D':
                    this.velocityX = 0;
                    this.velocityY = tileSize / 4;
                    break;
                case 'L':
                    this.velocityX = -tileSize / 4;
                    this.velocityY = 0;
                    break;
                case 'R':
                    this.velocityX = tileSize / 4;
                    this.velocityY = 0;
                    break;
            }
        }

        void reset() {
            this.x = this.startX;
            this.y = this.startY;
        }

        private void checkCollisionWithWalls(char prevDirection) {
            for (Block wall : walls) {
                if (collision(this, wall)) {
                    this.x -= this.velocityX;
                    this.y -= this.velocityY;
                    this.direction = prevDirection;
                    updateVelocity();
                }
            }
        }
    }

    private int rowCount = 21;
    private int columnCount = 19;
    private int tileSize = 32;
    private int boardWidth = columnCount * tileSize;
    private int boardHeight = (rowCount * tileSize) + 30;  // Aumentamos la altura de la pantalla en 50 píxeles para los textos

    private Image wallImage, blueGhostImage, orangeGhostImage, pinkGhostImage, redGhostImage;
    private Image pacmanUpImage, pacmanDownImage, pacmanLeftImage, pacmanRightImage, pacmanClosedImage;

    private boolean paused = false;
    private boolean showStartScreen = true; // Variable para controlar si mostrar la pantalla de inicio

    private int mouthState = 0; // 0 -> cerrada, 1 -> abierta
    private int mouthTimer = 0; // Temporizador para alternar el estado de la boca
    private final int MOUTH_TOGGLE_INTERVAL = 4; // Intervalo de tiempo para alternar (ajústalo según lo que necesites)

    private boolean showMessage = false; // Controla si el mensaje debe mostrarse
    private int messageTimer = 0; // Controla el tiempo para mostrar el mensaje
    

    //X = wall, O = skip, P = pac man, ' ' = food
    //Ghosts: b = blue, o = orange, p = pink, r = red
    private String[] tileMap = {
        "XXXXXXXXXXXXXXXXXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X                 X",
        "X XX X XXXXX X XX X",
        "X    X       X    X",
        "XXXX XXXX XXXX XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXrXX X XXXX",
        "O       bpo       O",
        "XXXX X XXXXX X XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXXXX X XXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X  X     P     X  X",
        "XX X X XXXXX X X XX",
        "X    X   X   X    X",
        "X XXXXXX X XXXXXX X",
        "X                 X",
        "XXXXXXXXXXXXXXXXXXX" 
    };

    private HashSet<Block> walls, foods, ghosts;
    private Block pacman;
    private Timer gameLoop;
    private char[] directions = {'U', 'D', 'L', 'R'};
    private Random random = new Random();
    private int score = 0, lives = 3;
    private boolean gameOver = false;

    // Variable para almacenar la dirección deseada
    char desiredDirection = 'R';

    PacMan() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        // Cargar las imágenes
        wallImage = new ImageIcon(getClass().getResource("./img/wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("./img/blueGhost.png")).getImage();
        orangeGhostImage = new ImageIcon(getClass().getResource("./img/orangeGhost.png")).getImage();
        pinkGhostImage = new ImageIcon(getClass().getResource("./img/pinkGhost.png")).getImage();
        redGhostImage = new ImageIcon(getClass().getResource("./img/redGhost.png")).getImage();

        pacmanUpImage = new ImageIcon(getClass().getResource("./img/pacmanUp.png")).getImage();
        pacmanDownImage = new ImageIcon(getClass().getResource("./img/pacmanDown.png")).getImage();
        pacmanLeftImage = new ImageIcon(getClass().getResource("./img/pacmanLeft.png")).getImage();
        pacmanRightImage = new ImageIcon(getClass().getResource("./img/pacmanRight.png")).getImage();
        pacmanClosedImage = new ImageIcon(getClass().getResource("./img/pacmanClosed.png")).getImage();

        loadMap();
        for (Block ghost : ghosts) {
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }
        
        gameLoop = new Timer(50, this);
        gameLoop.start();
    }

    public void loadMap() {
        walls = new HashSet<Block>();
        foods = new HashSet<Block>();
        ghosts = new HashSet<Block>();

        for (int r = 0; r < rowCount; r++) {
            for(int c = 0; c < columnCount; c++) {
                String row = tileMap [r];
                char tileMapChar = row.charAt(c);

                int x = c*tileSize;
                int y = r*tileSize;

                if (tileMapChar == 'X') { // block wall
                    Block wall = new Block(wallImage, x, y, tileSize, tileSize);
                    walls.add(wall);
                }
                else if (tileMapChar =='b') { //blue ghost
                    Block ghost = new Block(blueGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                }
                else if (tileMapChar =='o') { //orange ghost
                    Block ghost = new Block(orangeGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                }
                else if (tileMapChar =='p') { //pink ghost
                    Block ghost = new Block(pinkGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                }
                else if (tileMapChar =='r') { //red ghost
                    Block ghost = new Block(redGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                }
                else if (tileMapChar =='P') {
                    pacman = new Block(pacmanRightImage, x, y, tileSize, tileSize);
                }
                else if (tileMapChar ==' ') {
                    Block food = new Block(null,  x + 14, y + 14, 4, 4);
                    foods.add(food);
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
         // Pantalla de inicio
        if (showStartScreen) {
            String titleText = "Pac-Man"; // Texto para "Pac-Man"
            String startText = "Press ENTER to start"; // Texto para el mensaje adicional
            String instructionsText1 = "SPACE for pause"; // Texto para la primera línea de instrucciones
            String instructionsText2 = "ARROWS to move"; // Texto para la segunda línea de instrucciones

            // Dibujar "Pac-Man" en amarillo y en un tamaño grande
            Font pacManFont = new Font("Arial", Font.BOLD, 64);  // Fuente grande
            g.setFont(pacManFont);
            g.setColor(Color.YELLOW);  // Color amarillo
            FontMetrics pacManMetrics = g.getFontMetrics(pacManFont);
            int pacManWidth = pacManMetrics.stringWidth(titleText);
            int pacManHeight = pacManMetrics.getHeight();
            int pacManX = (getWidth() - pacManWidth) / 2;
            int pacManY = (getHeight() - pacManHeight) / 2;
            g.drawString(titleText, pacManX, pacManY);

            // Dibujar "Presiona ENTER para comenzar" en blanco y en un tamaño más pequeño
            Font smallFont = new Font("Arial", Font.BOLD, 32);  // Fuente más pequeña para el resto del texto
            g.setFont(smallFont);
            g.setColor(Color.WHITE);  // Color blanco para el texto restante
            FontMetrics smallMetrics = g.getFontMetrics(smallFont);

            int smallTextWidth = smallMetrics.stringWidth(startText);
            int smallTextHeight = smallMetrics.getHeight();
            int smallTextX = (getWidth() - smallTextWidth) / 2;
            int smallTextY = pacManY + pacManHeight + 20; // Justo debajo de "Pac-Man"

            // Dibujar el texto más pequeño (mensaje adicional)
            g.drawString(startText, smallTextX, smallTextY);

            // Dibujar las instrucciones en dos líneas
            Font tinyFont = new Font("Arial", Font.PLAIN, 16);  // Fuente aún más pequeña
            g.setFont(tinyFont);
            g.setColor(Color.WHITE);  // Color blanco para las instrucciones
            FontMetrics tinyMetrics = g.getFontMetrics(tinyFont);

            // Calcular posiciones de las dos líneas de instrucciones
            int tinyTextWidth1 = tinyMetrics.stringWidth(instructionsText1);
            int tinyTextWidth2 = tinyMetrics.stringWidth(instructionsText2);
            int tinyTextHeight = tinyMetrics.getHeight();
            
            int tinyTextX1 = (getWidth() - tinyTextWidth1) / 2;
            int tinyTextX2 = (getWidth() - tinyTextWidth2) / 2;

            int tinyTextY1 = smallTextY + smallTextHeight + 10; // Justo debajo del texto "Presiona ENTER para comenzar"
            int tinyTextY2 = tinyTextY1 + tinyTextHeight + 5;  // Justo debajo de "SPACE for pause"

            // Dibujar la primera línea de instrucciones
            g.drawString(instructionsText1, tinyTextX1, tinyTextY1);
            // Dibujar la segunda línea de instrucciones
            g.drawString(instructionsText2, tinyTextX2, tinyTextY2);
        } else if (paused) {
            // Si el juego está pausado, mostrar el fondo negro y el texto "PAUSED"
            String pausedText = "PAUSED";
            Font font = new Font("Arial", Font.BOLD, 48);
            g.setFont(font);
            g.setColor(Color.WHITE);

            FontMetrics metrics = g.getFontMetrics(font);
            int x = (getWidth() - metrics.stringWidth(pausedText)) / 2;
            int y = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();

            g.drawString(pausedText, x, y);
        }else if (gameOver) {
            g.setColor(Color.BLACK); // Establece el color de fondo a negro
            g.fillRect(0, 0, getWidth(), getHeight());  // Dibuja un rectángulo negro que cubre toda la ventana
            
            // Establecer el color del texto a rojo
            g.setColor(Color.RED);
            
            // Usar una fuente más grande para el texto
            Font largeFont = new Font("Arial", Font.BOLD, 30); // Cambia el tamaño de la fuente aquí
            g.setFont(largeFont);
            
            // Calcular la posición centrada para el texto
            String message = "Game Over! Press Enter to Restart";
            
            // Calcular la posición X para centrar el texto
            FontMetrics metrics = g.getFontMetrics(largeFont);
            int x = (getWidth() - metrics.stringWidth(message)) / 2;
            
            // Calcular la posición Y para centrar el texto
            int y = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
            
            // Dibujar el mensaje centrado en la pantalla
            g.drawString(message, x, y);
        } else {
            draw(g);  // Si el juego no está en pausa y no estamos en la pantalla de inicio, dibujamos el juego
        }

        if (showMessage) {
            Font font = new Font("Arial", Font.BOLD, 24);  // Fuente para el mensaje
            g.setFont(font);
            g.setColor(Color.WHITE);
            
            String message = "YOU CAN EAT MORE!!!";
            FontMetrics metrics = g.getFontMetrics(font);
            int x = (getWidth() - metrics.stringWidth(message)) / 2;
            int y = 30;
            
            g.drawString(message, x, y);  // Dibujar mensaje en el centro
            
            // Incrementar el temporizador
            messageTimer++;
            
            // Si han pasado 2 segundos (40 ciclos del juego), ocultar el mensaje
            if (messageTimer >= 40) {
                showMessage = false;  // Ocultar el mensaje después de 2 segundos
            }
        }
    }

    // En el método draw(), ajustamos el tamaño de la fuente para los textos
    public void draw(Graphics g) {
        // Ajustamos la posición de los gráficos del juego hacia abajo
        int gameOffsetY = 30; // Desplazamos todo el juego hacia abajo

        // Dibujar a Pac-Man
        g.drawImage(pacman.image, pacman.x, pacman.y + gameOffsetY, pacman.width, pacman.height, null);

        // Dibujar los fantasmas
        for (Block ghost : ghosts) {
            g.drawImage(ghost.image, ghost.x, ghost.y + gameOffsetY, ghost.width, ghost.height, null);
        }

        // Dibujar las paredes
        for (Block wall : walls) {
            g.drawImage(wall.image, wall.x, wall.y + gameOffsetY, wall.width, wall.height, null);
        }

        // Dibujar la comida
        g.setColor(Color.YELLOW);
        for (Block food : foods) {
            g.fillRect(food.x, food.y + gameOffsetY, food.width, food.height);
        }

        // Reducir el margen superior para el contador
        int topMargin = 5; // Reducimos el margen superior


        // Contador con el texto de las vidas y el score
        String scoreText = "Score: " + String.valueOf(score);
        String livesText = "Lives: " + String.valueOf(lives);

        // Usar una fuente más pequeña, negrita y colorida
        Font font = new Font("Arial", Font.BOLD, 18);  // Reducimos el tamaño de la fuente
        g.setFont(font);
        g.setColor(Color.YELLOW);  // El color de Pac-Man (amarillo)
        
        // Sombra para el score
        g.setColor(Color.YELLOW);
        g.drawString(scoreText, getWidth() - 10 - g.getFontMetrics().stringWidth(scoreText), topMargin + 15);  // Contador principal

        // Sombra para las vidas
        g.setColor(Color.YELLOW);
        g.drawString(livesText, 10, topMargin + 15);  // Contador principal
    }

    public void move() {
        mouthTimer++;
        if (mouthTimer >= MOUTH_TOGGLE_INTERVAL) {
            mouthState = (mouthState + 1) % 2; // Alternar entre 0 y 1 (cerrada o abierta)
            mouthTimer = 0;
        }

        // Comprobar si PacMan está en una intersección (tileSize * x o tileSize * y)
        boolean atIntersection = (pacman.x % tileSize == 0) && (pacman.y % tileSize == 0);
    
        if (atIntersection) {
            // Si PacMan está en una intersección, actualiza la dirección
            // Solo actualizar la dirección si la tecla es diferente a la actual dirección
            if (pacman.direction != desiredDirection) {
                pacman.updateDirection(desiredDirection);
            }
    
            // Cambiar la imagen de PacMan según la dirección y el estado de la boca
            if (pacman.direction == 'U') {
                pacman.image = (mouthState == 0) ? pacmanClosedImage : pacmanUpImage;
            } else if (pacman.direction == 'D') {
                pacman.image = (mouthState == 0) ? pacmanClosedImage : pacmanDownImage;
            } else if (pacman.direction == 'L') {
                pacman.image = (mouthState == 0) ? pacmanClosedImage : pacmanLeftImage;
            } else if (pacman.direction == 'R') {
                pacman.image = (mouthState == 0) ? pacmanClosedImage : pacmanRightImage;
            }
        }
    
        pacman.x += pacman.velocityX;
        pacman.y += pacman.velocityY;
    
        // Verificar el teletransporte horizontal en el pasillo central (coordenadas 0,9 a 0,18)
        if (pacman.y >= 9 * tileSize && pacman.y < 10 * tileSize) { // Pasillo en el rango de Y
            // Si PacMan sale por el borde izquierdo (coordenada (0,9))
            if (pacman.x < 0) {
                pacman.x = 18 * tileSize; // Aparece por el borde derecho del pasillo (coordenada (0,18))
            }
            // Si PacMan sale por el borde derecho (coordenada (0,18))
            else if (pacman.x >= boardWidth) {
                pacman.x = 0; // Aparece por el borde izquierdo del pasillo (coordenada (0,9))
            }
        }
    
        for (Block wall : walls) {
            if (collision(pacman, wall)) {
                pacman.x -= pacman.velocityX;
                pacman.y -= pacman.velocityY;
                break;
            }
        }
    
        for (Block ghost : ghosts) {
            if (collision(ghost, pacman)) {
                lives -= 1;
                if (lives == 0) {
                    gameOver = true;
                    return;
                }
                resetPositions();
            }
            if (ghost.y == tileSize * 9 && ghost.direction != 'U' && ghost.direction != 'D') { //Tocar aquí si quiero lo de las intersecciones
                ghost.updateDirection('U');
            }
            ghost.x += ghost.velocityX;
            ghost.y += ghost.velocityY;
            for (Block wall : walls) {
                if (collision(ghost, wall) || ghost.x <= 0 || ghost.x + ghost.width >= boardWidth) { //esto se tiene que cambiar
                    ghost.x -= ghost.velocityX;
                    ghost.y -= ghost.velocityY;
                    char newDirection = directions[random.nextInt(4)];
                    ghost.updateDirection(newDirection);
                }
            }
        }
    
        Block foodEaten = null;
        for (Block food : foods) {
            if (collision(pacman, food)) {
                foodEaten = food;
                score += 10;
            }
        }
        foods.remove(foodEaten);
    
        if (foods.isEmpty()) {
            // Mostrar el mensaje "You can eat more" cuando se haya comido toda la comida
            showMessage = true;
            messageTimer = 0; // Reiniciar el temporizador
            loadMap();  // Recargar el mapa (reaparecer la comida)
            resetPositions();
        }
    }
    

    public boolean collision(Block a, Block b) {
        return a.x < b.x + b.width && a.x + a.width > b.x && a.y < b.y + b.height && a.y + a.height > b.y;
    }

    public void resetPositions() {
        pacman.reset();
        pacman.velocityX = 0;
        pacman.velocityY = 0;
        for (Block ghost : ghosts) {
            ghost.reset();
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection); 
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!paused && !showStartScreen) {  // Solo mueve PacMan y los fantasmas si no está pausado
            move();
        }
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            // Solo permitir que la tecla ENTER funcione cuando el juego haya terminado
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                // Aquí puedes reiniciar el juego
                loadMap();
                resetPositions();
                lives = 3;
                score = 0;
                gameOver = false;
                gameLoop.start();  // Reinicia el loop del juego
            }
            // Si no es ENTER, no hacer nada (ignorar otras teclas)
            return;
        }
        
        if (showStartScreen) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                showStartScreen = false;
                repaint();  // Redibujar el panel después de comenzar el juego
            }
        } else {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {  // Tecla de pausa
                paused = !paused;  // Alternar entre pausar y reanudar
                repaint();  // Redibujar para reflejar el cambio
            } else if (!paused) {
                // Cambiar la dirección deseada cuando se presiona una tecla
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    desiredDirection = 'U';
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    desiredDirection = 'D';
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    desiredDirection = 'L';
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    desiredDirection = 'R';
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

}

## Descripción

Este es un juego inspirado en el clásico **Pac-Man**, desarrollado en **Java** utilizando la biblioteca **Swing** para la interfaz gráfica. En el juego, controlarás a Pac-Man a través de las teclas de flecha, y tu objetivo es comer toda la comida en el laberinto mientras esquivas a los fantasmas. Si los fantasmas te tocan, perderás una vida. El juego tiene elementos como pausas, reinicios y la posibilidad de mostrar un mensaje cuando has comido toda la comida del nivel.

## Características

- **Pantalla de inicio**: Una pantalla inicial donde puedes ver el título del juego y las instrucciones básicas.
- **Modo Pausa**: Pausa y reanuda el juego usando la tecla **SPACE**.
- **Movimiento de Pac-Man**: Puedes mover a Pac-Man en las 4 direcciones (arriba, abajo, izquierda, derecha) con las teclas de flecha.
- **Fantasmas**: Cuatro fantasmas (azul, naranja, rosa y rojo) persiguen a Pac-Man a lo largo del mapa. Si te tocan, perderás una vida.
- **Comida**: Recoge la comida para sumar puntos y completar el nivel. Al comer todos los puntos del tablero, el mensaje "YOU CAN EAT MORE!!!" aparece por un breve periodo.
- **Reinicio**: Cuando se acaba el juego, presiona **ENTER** para reiniciar.
- **Pantalla de "Game Over"**: Después de perder todas tus vidas, el juego muestra un mensaje de "Game Over" y puedes reiniciar el juego.
- **Movimiento**: He optimizado la lógica del movimiento para que el control no sea tan brusco.

## Requisitos

- **Java 8+**: El código está escrito en Java, por lo que necesitarás tener Java 8 o superior para ejecutar el juego.
- **Biblioteca Swing**: El juego usa la biblioteca Swing para la interfaz gráfica, que viene incluida en el JDK de Java.

## Instrucciones de Juego

1. **Pantalla de inicio**: Al abrir el juego, verás una pantalla inicial con el nombre "Pac-Man" y las instrucciones básicas.
   - **ENTER**: Comienza el juego.
   
2. **Control de Pac-Man**:
   - Usa las teclas de flecha **ARRIBA**, **ABAJO**, **IZQUIERDA** y **DERECHA** para mover a Pac-Man por el laberinto.
   
3. **Comida y Puntos**:
   - Recoge la comida (puntos amarillos) para sumar puntos. Cada comida te da **10 puntos**.

4. **Fantasmas**:
   - Evita a los fantasmas (representados por diferentes colores: azul, naranja, rosa, rojo). Si un fantasma toca a Pac-Man, perderás una vida.

5. **Pausa**:
   - Usa la tecla **SPACE** para pausar el juego en cualquier momento.
   - Puedes reanudar el juego presionando nuevamente **SPACE**.

6. **Pantalla de "Game Over"**:
   - Si pierdes todas tus vidas, aparecerá una pantalla de "Game Over".
   - Presiona **ENTER** para reiniciar el juego.

# Estructura del Código

## Clase Block
La clase `Block` representa un objeto dentro del juego, como Pac-Man, los fantasmas, las paredes y la comida. Esta clase maneja los siguientes aspectos:

- **Dirección**: Define la dirección en la que se mueve el objeto.
- **Velocidad**: Controla la velocidad con la que el objeto se mueve.
- **Colisión**: Detecta si el objeto colisiona con otros elementos del juego, como las paredes o los fantasmas.

## Clase PacMan
La clase `PacMan` se encarga de gestionar el estado del juego, el movimiento de Pac-Man y las interacciones con los elementos del mapa. Sus principales responsabilidades son:

- **Estado del juego**: Controla el estado actual del juego, como la carga del mapa y la lógica de las interacciones.
- **Carga del mapa**: Gestiona el mapa del juego, cargando las posiciones de los objetos (muros, comida, fantasmas, etc.).
- **Lógica de movimiento**: Controla el movimiento de Pac-Man y verifica las colisiones con las paredes y los objetos.
- **Interacciones con las teclas**: Permite al jugador controlar a Pac-Man usando las teclas del teclado.
- **Dibujo en pantalla**: Se encarga de dibujar los diferentes elementos del juego (tablero, Pac-Man, fantasmas, paredes) en la ventana del juego.

## Interacción de Teclas
Se utiliza un `KeyListener` para detectar las teclas presionadas por el jugador. Esto permite que el jugador realice las siguientes acciones:

- **Mover a Pac-Man**: El jugador puede mover a Pac-Man hacia arriba, abajo, izquierda o derecha.
- **Pausar el juego**: El jugador puede pausar el juego cuando lo desee.
- **Reiniciar el juego**: Si el jugador pierde, tiene la opción de reiniciar el juego.


## Detalles Técnicos

- **Mapa**: El mapa está compuesto por una matriz de caracteres, donde:
  - `X`: Pared
  - `P`: Pac-Man
  - `b`: Fantasma Azul
  - `o`: Fantasma Naranja
  - `p`: Fantasma Rosa
  - `r`: Fantasma Rojo
  - `' '`: Comida
- **Fantasmas**: Los fantasmas se mueven aleatoriamente y cambian de dirección cuando chocan con las paredes.
- **Colisiones**: El juego maneja las colisiones de Pac-Man con las paredes, la comida y los fantasmas.

## Capturas de Pantalla

![Texto alternativo](..\src\img\pm1.png)

## Licencia

Este proyecto está licenciado bajo la **MIT License** - consulta el archivo [LICENSE](LICENSE) para más detalles.

# Pac-Man Java Game

## Descripción

Este es un juego inspirado en el clásico **Pac-Man**, desarrollado en **Java** utilizando la biblioteca **Swing** para la interfaz gráfica. En el juego, controlarás a Pac-Man a través de las teclas de flecha, y tu objetivo es comer toda la comida en el laberinto mientras esquivas a los fantasmas. Si los fantasmas te tocan, perderás una vida. El juego tiene elementos como pausas, reinicios y la posibilidad de mostrar un mensaje cuando has comido toda la comida del nivel.

## Características

- **Pantalla de inicio**: Una pantalla inicial donde puedes ver el título del juego y las instrucciones básicas.
- **Modo Pausa**: Pausa y reanuda el juego usando la tecla **SPACE**.
- **Movimiento de Pac-Man**: Puedes mover a Pac-Man en las 4 direcciones (arriba, abajo, izquierda, derecha) con las teclas de flecha.
- **Fantasmas**: Cuatro fantasmas (azul, naranja, rosa y rojo) persiguen a Pac-Man a lo largo del mapa. Si te tocan, perderás una vida.
- **Comida**: Recoge la comida para sumar puntos y completar el nivel.
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

## Estructura del Proyecto

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

(Agregar imágenes del juego si las tienes disponibles)

## Contribuciones

Si deseas contribuir a este proyecto, por favor sigue estos pasos:

1. Haz un **fork** del proyecto.
2. Crea una **rama** para tu nueva funcionalidad (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios.
4. **Commit** de tus cambios (`git commit -am 'Añadir nueva funcionalidad'`).
5. **Push** a la rama (`git push origin feature/nueva-funcionalidad`).
6. Abre un **pull request**.

## Licencia

Este proyecto está licenciado bajo la **MIT License** - consulta el archivo [LICENSE](LICENSE) para más detalles.

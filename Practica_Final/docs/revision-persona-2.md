# Revision exhaustiva - Persona 2

## Alcance revisado

Esta revision comprueba la parte asignada a Persona 2 segun `prd.md`, `plan.md`, `skills.md`, `reparto-trabajo.md`, `organizacion_trabajo.pdf` y el enunciado original.

Responsabilidades de Persona 2 revisadas:

- Proyecto base Maven.
- Estructuras propias: lista, pila, cola, lista circular, arbol, grafo y matriz.
- BFS para casillas alcanzables.
- Ruta minima entre habitaciones.
- Dijkstra por decision de costes variables.
- Tests JUnit de estructuras y algoritmos.
- Documentacion de costes.
- Verificacion de no uso de estructuras prohibidas.

## Resultado de cumplimiento

| Requisito | Estado | Evidencia |
|---|---|---|
| Proyecto base Maven | Cumplido | `pom.xml` |
| Paquetes separados | Cumplido | `app`, `model`, `structures`, `logic`, `persistence`, `ui`, `exceptions`, `log` |
| Lista enlazada propia | Cumplido | `ListaEnlazadaPropia` |
| Pila propia | Cumplido | `PilaPropia` |
| Cola propia | Cumplido | `ColaPropia` |
| Lista circular propia | Cumplido | `ListaCircularPropia` |
| Arbol propio | Cumplido | `ArbolPropio` |
| Grafo propio | Cumplido | `GrafoPropio` |
| Matriz propia | Cumplido | `MatrizPropia` |
| Matriz sin arrays nativos | Cumplido | basada en `ListaEnlazadaPropia` |
| Grafo no dirigido | Cumplido | cada arista se guarda en ambos sentidos |
| Costes variables | Cumplido | aristas ponderadas con `coste` |
| Dijkstra | Cumplido | `CalculadorRutasHabitaciones` |
| BFS casillas alcanzables | Cumplido | `BuscadorCasillasAlcanzables` |
| Movimiento diagonal prohibido | Cumplido en BFS | explora solo arriba, abajo, izquierda y derecha |
| Tests JUnit | Cumplido | 66 tests ejecutados correctamente con `mvn test` |
| Documentacion de costes | Cumplido | `docs/estructuras-costes.md` |
| Guia de integracion | Cumplido | `docs/integracion-persona-2.md` |
| Diario de IA | Cumplido parcialmente | `docs/diario-ia.md`, pendiente revision humana |

## Restricciones revisadas

Busqueda realizada en `src`:

- `ArrayList`: no aparece.
- `HashMap`: no aparece.
- `LinkedList`: no aparece.
- `java.util`: no aparece.
- `Collections`: no aparece.
- `Map<`, `List<`, `Queue<`, `Stack<`: no aparecen.

Busqueda de arrays en estructuras propias:

- No aparecen `new ...[` ni `[]` en `src/main/java/practicafinal/structures`.

Unico uso de `[]` detectado fuera de estructuras:

- `String[] args` en `MainApp`, que no forma parte de las estructuras evaluadas.

## Correcciones aplicadas durante la revision

Se detecto que `GrafoPropio` permitia vertices `null`. Esto era peligroso para Dijkstra porque `null` se usa como ausencia de vertice anterior al reconstruir la ruta.

Correccion aplicada:

- `GrafoPropio.agregarVertice` rechaza vertices `null`.
- `GrafoPropio.agregarArista` rechaza origen o destino `null`.
- Se anadieron tests para vertices nulos.
- Se documento la restriccion en `docs/estructuras-costes.md` y `docs/integracion-persona-2.md`.

Tambien se ampliaron tests de borde:

- `GrafoPropioTest`: vertices, no duplicacion de vecinos y vertice nulo.
- `BuscadorCasillasAlcanzablesTest`: movimiento negativo y matriz nula.
- `CalculadorRutasHabitacionesTest`: destino inexistente y grafo nulo.

## Integracion con el juego final

La parte de Persona 2 esta encaminada al juego final porque proporciona las bases que necesitan Persona 1 y Persona 3:

- `MatrizPropia<Celda>` podra representar habitaciones cuando exista `Celda`.
- `GrafoPropio<String>` o `GrafoPropio<Habitacion>` podra representar el mapa de habitaciones.
- `ColaPropia` podra gestionar turnos.
- `ListaCircularPropia` podra rotar enemigos de la habitacion.
- `ArbolPropio` podra organizar acciones posibles del jugador.
- `BuscadorCasillasAlcanzables` podra iluminar casillas alcanzables en JavaFX.
- `CalculadorRutasHabitaciones` podra informar ruta minima y coste hasta la salida.

## Limites reales de esta parte

La parte de Persona 2 no crea todavia un juego jugable porque faltan responsabilidades de Persona 1 y Persona 3:

- Modelo de dominio real: `Jugador`, `Celda`, `Habitacion`, `Enemigo`, `Objeto`, `Puerta`, `Partida`.
- Reglas de turnos completas.
- Combate.
- Inventario.
- Persistencia JSON.
- Interfaz JavaFX real.
- Condiciones de victoria y derrota.
- Log de eventos.

## Verificacion ejecutada

Se prepararon herramientas portables dentro del proyecto:

- JDK 8: `.tools/jdk8/jdk8u492-b09`.
- Maven: `.tools/maven/apache-maven-3.9.9`.

Comando usado en PowerShell:

```bash
$env:JAVA_HOME = "$PWD\.tools\jdk8\jdk8u492-b09"
$env:PATH = "$env:JAVA_HOME\bin;$PWD\.tools\maven\apache-maven-3.9.9\bin;$env:PATH"
mvn test
```

Resultado:

- `mvn compile`: BUILD SUCCESS.
- `mvn test`: BUILD SUCCESS.
- Tests ejecutados: 66.
- Fallos: 0.
- Errores: 0.
- Omitidos: 0.

Nota de entorno: JavaFX se compila usando el `jfxrt.jar` del JRE Oracle local configurado en `pom.xml`.

## Conclusion

La parte de Persona 2 cumple el alcance documental, compila, pasa sus tests y respeta las restricciones principales de Estructuras de Datos. Queda preparada para integrarse con el modelo y la logica del juego, siempre que Persona 3 use las APIs documentadas y no reimplemente estas estructuras con clases estandar de Java.

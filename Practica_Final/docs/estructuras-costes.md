# Estructuras propias y costes

Este documento resume la parte implementada por Persona 2 para Estructuras de Datos.

## Decisiones aplicadas

- Proyecto base: Maven.
- Grafo de habitaciones: propio, no dirigido y ponderado.
- Costes de conexiones: variables, por lo que se implementa Dijkstra.
- Matriz de habitaciones: propia y sin arrays nativos.
- Arbol propio: organizacion de acciones posibles del jugador.
- Lista circular propia: rotacion ciclica de enemigos.

## ListaEnlazadaPropia

Uso previsto: colecciones internas, inventario si el modelo lo decide, vecinos del grafo, rutas y resultados de algoritmos.

Operaciones y costes:

| Operacion | Coste |
|---|---|
| `agregar` | O(1) |
| `insertar` | O(n) |
| `obtener` | O(n) |
| `eliminar` | O(n) |
| `contiene` / `indiceDe` | O(n) |
| `tamano` / `estaVacia` | O(1) |

## PilaPropia

Uso previsto: reconstruccion de rutas y posibles algoritmos auxiliares.

Operaciones y costes:

| Operacion | Coste |
|---|---|
| `apilar` | O(1) |
| `desapilar` | O(1) |
| `cima` | O(1) |
| `tamano` / `estaVacia` | O(1) |

## ColaPropia

Uso previsto: gestion de turnos y BFS.

Operaciones y costes:

| Operacion | Coste |
|---|---|
| `encolar` | O(1) |
| `desencolar` | O(1) |
| `frente` | O(1) |
| `tamano` / `estaVacia` | O(1) |

## ListaCircularPropia

Uso previsto: rotacion ciclica de enemigos de una habitacion.

Operaciones y costes:

| Operacion | Coste |
|---|---|
| `agregar` | O(1) |
| `obtenerActual` | O(1) |
| `siguiente` | O(1) |
| `eliminar` | O(n) |
| `contiene` | O(n) |
| `tamano` / `estaVacia` | O(1) |

## MatrizPropia

Uso previsto: representacion interna de habitaciones.

Implementacion: lista enlazada propia de filas, donde cada fila es otra lista enlazada propia. No usa arrays nativos.

Operaciones y costes:

| Operacion | Coste |
|---|---|
| `obtener` | O(fila + columna) |
| `establecer` | O(fila + columna) |
| `esCoordenadaValida` | O(1) |
| `getFilas` / `getColumnas` | O(1) |

## GrafoPropio

Uso previsto: mapa global de habitaciones.

Implementacion: lista propia de vertices; cada vertice contiene lista propia de aristas con coste. El grafo es no dirigido, por lo que cada conexion se guarda en ambos sentidos.

Restriccion de integracion: los vertices no pueden ser `null`, porque las habitaciones del juego deben poder identificarse de forma estable y Dijkstra usa `null` como ausencia de anterior.

Operaciones y costes:

| Operacion | Coste |
|---|---|
| `agregarVertice` | O(v) |
| `agregarArista` | O(v + aNodo) |
| `existeVertice` | O(v) |
| `existeArista` | O(v + aNodo) |
| `obtenerVecinos` | O(v + aNodo) |
| `obtenerCoste` | O(v + aNodo) |
| `obtenerVertices` | O(v) |

## ArbolPropio

Uso previsto: organizar acciones posibles del jugador durante su turno.

Operaciones y costes:

| Operacion | Coste |
|---|---|
| `crearRaiz` | O(1) |
| `obtenerRaiz` | O(1) |
| `agregarHijo` | O(n) |
| `obtenerHijos` | O(n) |
| `contiene` | O(n) |
| `recorrerPreorden` | O(n) |
| `tamano` / `estaVacio` | O(1) |

## BFS de casillas alcanzables

Clase: `BuscadorCasillasAlcanzables`.

Uso: calcular destinos alcanzables dentro de una habitacion con movimiento ortogonal.

Estructuras usadas: `ColaPropia`, `MatrizPropia` y `ListaEnlazadaPropia`.

Coste: O(filas * columnas) en el peor caso.

Restricciones cumplidas:

- No permite movimiento diagonal directo.
- Respeta limite de movimiento.
- Evita casillas no transitables.

## Dijkstra entre habitaciones

Clase: `CalculadorRutasHabitaciones`.

Uso: calcular ruta minima entre habitaciones con costes variables.

Estructuras usadas: `GrafoPropio`, `ListaEnlazadaPropia` y `PilaPropia`.

Coste: O(v^2 + e * v) con la implementacion actual basada en listas propias sin cola de prioridad estandar.

Justificacion: se evita usar estructuras prohibidas de Java y se mantiene una implementacion explicable para el alcance de la practica.

## Verificacion

- Hay tests JUnit para lista, pila, cola, lista circular, matriz, grafo, arbol, BFS y Dijkstra.
- Se ha revisado que no aparecen `ArrayList`, `HashMap`, `LinkedList` ni `java.util` en `src`.
- Se ha revisado que las estructuras propias no usan arrays nativos.
- Se ejecuto `mvn test` con JDK 8 y Maven portables: 66 tests, 0 fallos, 0 errores.

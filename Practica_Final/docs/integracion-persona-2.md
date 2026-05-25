# Integracion de Persona 2

Este documento indica que puede usar el resto del grupo de la parte de estructuras y algoritmos.

## Paquetes entregados

- `practicafinal.structures`: estructuras propias.
- `practicafinal.logic`: algoritmos de movimiento y rutas.

## Estructuras disponibles

### ListaEnlazadaPropia<T>

Metodos publicos:

- `agregar(T elemento)`
- `insertar(int indice, T elemento)`
- `obtener(int indice)`
- `eliminar(int indice)`
- `eliminarElemento(T elemento)`
- `contiene(T elemento)`
- `indiceDe(T elemento)`
- `tamano()`
- `estaVacia()`

### PilaPropia<T>

Metodos publicos:

- `apilar(T elemento)`
- `desapilar()`
- `cima()`
- `tamano()`
- `estaVacia()`

### ColaPropia<T>

Metodos publicos:

- `encolar(T elemento)`
- `desencolar()`
- `frente()`
- `tamano()`
- `estaVacia()`

### ListaCircularPropia<T>

Metodos publicos:

- `agregar(T elemento)`
- `obtenerActual()`
- `siguiente()`
- `eliminar(T elemento)`
- `contiene(T elemento)`
- `tamano()`
- `estaVacia()`

Uso previsto: rotar enemigos dentro de una habitacion.

### MatrizPropia<T>

Metodos publicos:

- `obtener(int fila, int columna)`
- `establecer(int fila, int columna, T valor)`
- `esCoordenadaValida(int fila, int columna)`
- `getFilas()`
- `getColumnas()`

Uso previsto: representar las celdas de una habitacion sin arrays nativos.

### GrafoPropio<T>

Metodos publicos:

- `agregarVertice(T valor)`
- `agregarArista(T origen, T destino, int coste)`
- `existeVertice(T valor)`
- `existeArista(T origen, T destino)`
- `obtenerCoste(T origen, T destino)`
- `obtenerVecinos(T valor)`
- `obtenerVertices()`
- `numeroVertices()`

Uso previsto: mapa global de habitaciones. Es no dirigido y ponderado.

### ArbolPropio<T>

Metodos publicos:

- `crearRaiz(T valor)`
- `obtenerRaiz()`
- `agregarHijo(T valorPadre, T valorHijo)`
- `obtenerHijos(T valorPadre)`
- `contiene(T valor)`
- `recorrerPreorden()`
- `tamano()`
- `estaVacio()`

Uso previsto: organizar acciones posibles del jugador.

## Algoritmos disponibles

### BuscadorCasillasAlcanzables

Metodo principal:

```java
ListaEnlazadaPropia<Coordenada> calcular(MatrizPropia<Boolean> transitables,
                                         int filaInicial,
                                         int columnaInicial,
                                         int movimientoMaximo)
```

Convencion actual:

- `Boolean.TRUE` significa celda transitable.
- `Boolean.FALSE` o `null` significa celda no transitable.
- La posicion inicial debe estar dentro de la matriz.
- El resultado no incluye la posicion inicial.
- Solo explora arriba, abajo, izquierda y derecha.

### CalculadorRutasHabitaciones

Metodo principal:

```java
<T> RutaMinima<T> calcularRutaMinima(GrafoPropio<T> grafo, T origen, T destino)
```

Convencion actual:

- Usa Dijkstra porque el grafo tiene costes variables.
- Si no existe ruta, `RutaMinima.existe()` devuelve `false` y el coste es `-1`.
- Si origen y destino son iguales, la ruta existe con coste `0`.
- Los vertices del grafo no pueden ser `null`; las habitaciones deben tener identificador u objeto valido.

## Clases auxiliares de logica

### Coordenada

Metodos publicos:

- `getFila()`
- `getColumna()`
- `mismaPosicion(int fila, int columna)`

### RutaMinima<T>

Metodos publicos:

- `getVertices()`
- `getCosteTotal()`
- `existe()`

## Puntos que Persona 3 debe respetar

- No reimplementar BFS ni Dijkstra con estructuras estandar.
- No cambiar APIs publicas de estructuras sin avisar a Persona 2.
- Adaptar el modelo real de `Celda` a `MatrizPropia<Celda>` cuando Persona 1 y Persona 3 cierren el modelo.
- Para BFS, convertir el estado de la habitacion a `MatrizPropia<Boolean>` de transitabilidad.
- Para rutas, usar identificadores o referencias de habitacion como vertices del `GrafoPropio`.

## Verificacion ejecutada

Se prepararon herramientas portables dentro del proyecto:

- JDK 8: `.tools/jdk8/jdk8u492-b09`.
- Maven: `.tools/maven/apache-maven-3.9.9`.

Comando usado en PowerShell:

```powershell
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

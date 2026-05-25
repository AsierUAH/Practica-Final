# Guia base de Persona 2 para integracion

## 1. Proposito del documento

Este documento explica detalladamente todo lo que se ha construido en la parte de Persona 2 y como debe usarse para completar el juego final.

Debe servir como guia para que Persona 1 y Persona 3 trabajen sobre una base comun, sin duplicar codigo, sin romper restricciones de Estructuras de Datos y sin contradecir el enunciado.

La parte de Persona 2 no implementa el juego completo. Implementa la base tecnica obligatoria: proyecto Maven, estructuras propias, algoritmos de movimiento y rutas, pruebas y documentacion de costes.

## 2. Documentos usados como referencia

La implementacion se ha hecho siguiendo:

- `prd.md`
- `plan.md`
- `AGENTS.md`
- `skills.md`
- `reparto-trabajo.md`
- `organizacion_trabajo.pdf`
- `Practica Final 2026 MP EEDD.pdf`

La parte de Persona 2 corresponde a:

- Proyecto base.
- Estructuras de datos propias.
- Algoritmos de movimiento y rutas.
- Tests de estructuras y algoritmos.
- Documentacion de costes.
- Revision de estructuras prohibidas.

## 3. Estado actual del proyecto

El proyecto ya tiene:

- Proyecto Maven.
- Java 8 configurado.
- JavaFX compilable.
- JUnit 5 configurado.
- Paquetes base separados.
- Estructuras propias obligatorias.
- BFS para casillas alcanzables dentro de una habitacion.
- Dijkstra para rutas entre habitaciones con costes variables.
- Tests JUnit de estructuras y algoritmos.
- Documentacion de costes.
- Diario de IA actualizado.
- Guia de integracion especifica de Persona 2.
- Revision exhaustiva de Persona 2.

Verificacion ejecutada:

- `mvn compile`: BUILD SUCCESS.
- `mvn test`: BUILD SUCCESS.
- Tests ejecutados: 66.
- Fallos: 0.
- Errores: 0.
- Omitidos: 0.

## 4. Decisiones tecnicas cerradas

Estas decisiones ya estan aplicadas en el codigo y en la documentacion:

- Herramienta de proyecto: Maven.
- Lenguaje: Java 8.
- Interfaz prevista: JavaFX.
- Pruebas: JUnit 5.
- Grafo de habitaciones: propio, no dirigido y ponderado.
- Costes entre habitaciones: variables.
- Algoritmo de rutas entre habitaciones: Dijkstra.
- Matriz de habitaciones: propia y sin arrays nativos.
- Arbol propio: organizacion de acciones posibles del jugador.
- Lista circular propia: rotacion ciclica de enemigos.

Estas decisiones afectan directamente al resto del proyecto. Si el grupo cambia alguna, debe actualizar codigo, tests, documentacion, UML, JSON y memoria.

## 5. Herramientas locales y comandos

El PATH global de esta maquina solo tenia `java 1.8.0_201`, sin `mvn` ni `javac`. Para poder compilar y probar, se prepararon herramientas portables dentro del proyecto:

- JDK 8: `.tools/jdk8/jdk8u492-b09`
- Maven: `.tools/maven/apache-maven-3.9.9`

Antes de ejecutar Maven en PowerShell, usar:

```powershell
$env:JAVA_HOME = "$PWD\.tools\jdk8\jdk8u492-b09"
$env:PATH = "$env:JAVA_HOME\bin;$PWD\.tools\maven\apache-maven-3.9.9\bin;$env:PATH"
```

Comandos principales:

```powershell
mvn compile
mvn test
mvn exec:java
mvn package
```

JavaFX se compila usando el `jfxrt.jar` del JRE Oracle local configurado en `pom.xml`.

## 6. Estructura de paquetes

El proyecto tiene estos paquetes:

- `practicafinal.app`: arranque de la aplicacion.
- `practicafinal.model`: clases del dominio que faltan por implementar.
- `practicafinal.structures`: estructuras propias implementadas por Persona 2.
- `practicafinal.logic`: algoritmos y futura logica de juego.
- `practicafinal.persistence`: persistencia JSON pendiente.
- `practicafinal.ui`: interfaz JavaFX pendiente.
- `practicafinal.exceptions`: excepciones personalizadas pendientes.
- `practicafinal.log`: log de eventos pendiente.

Persona 2 ha implementado principalmente:

- `src/main/java/practicafinal/structures`
- `src/main/java/practicafinal/logic`
- `src/test/java/practicafinal/structures`
- `src/test/java/practicafinal/logic`

Persona 1 debe trabajar principalmente en:

- `src/main/java/practicafinal/model`
- `src/main/java/practicafinal/exceptions`
- `src/test/java/practicafinal/model`
- `docs`

Persona 3 debe trabajar principalmente en:

- `src/main/java/practicafinal/logic`
- `src/main/java/practicafinal/persistence`
- `src/main/java/practicafinal/ui`
- `src/main/java/practicafinal/log`
- `src/main/java/practicafinal/app`
- `src/main/resources`
- `src/test/java/practicafinal/logic`
- `src/test/java/practicafinal/persistence`

## 7. Restricciones que no se deben romper

El enunciado y los documentos del proyecto prohiben usar estructuras estandar equivalentes en las partes evaluadas de Estructuras de Datos.

No se debe usar en estructuras, algoritmos ni logica evaluada:

- `ArrayList`
- `HashMap`
- `LinkedList`
- `java.util` para sustituir estructuras propias
- `Collections`
- `Map`
- `List`
- `Queue`
- `Stack`

Revision actual:

- No aparece `ArrayList` en `src`.
- No aparece `HashMap` en `src`.
- No aparece `LinkedList` en `src`.
- No aparece `java.util` en `src`.
- No aparecen arrays nativos en `src/main/java/practicafinal/structures`.

El unico `[]` detectado fuera de estructuras es `String[] args` en `MainApp`, que no forma parte de las estructuras evaluadas.

## 8. ListaEnlazadaPropia

Clase:

`practicafinal.structures.ListaEnlazadaPropia<T>`

Responsabilidad:

Estructura lineal propia basada en nodos enlazados. Sirve como coleccion general para otras estructuras y algoritmos.

Uso actual:

- Vecinos del grafo.
- Lista de vertices del grafo.
- Resultado de BFS.
- Resultado de Dijkstra.
- Hijos del arbol.
- Estados internos de algoritmos.

Uso futuro recomendado:

- Inventario del jugador si el grupo decide implementarlo con lista propia.
- Colecciones de enemigos, objetos o acciones si no se usa otra estructura propia mas especifica.

Metodos publicos:

- `agregar(T elemento)`: anade al final.
- `insertar(int indice, T elemento)`: inserta en posicion.
- `obtener(int indice)`: obtiene por indice.
- `eliminar(int indice)`: elimina por indice y devuelve el elemento.
- `eliminarElemento(T elemento)`: elimina la primera aparicion.
- `contiene(T elemento)`: indica si existe.
- `indiceDe(T elemento)`: devuelve indice o `-1`.
- `tamano()`: devuelve numero de elementos.
- `estaVacia()`: indica si no hay elementos.

Costes:

- `agregar`: O(1), porque mantiene referencia al ultimo nodo.
- `insertar`: O(n).
- `obtener`: O(n).
- `eliminar`: O(n).
- `contiene`: O(n).
- `indiceDe`: O(n).
- `tamano`: O(1).
- `estaVacia`: O(1).

Tests:

- Lista vacia.
- Agregar y obtener en orden.
- Insertar en posicion intermedia.
- Eliminar por indice.
- Eliminar por elemento.
- Buscar elementos.
- Buscar `null`.
- Indices invalidos.

## 9. PilaPropia

Clase:

`practicafinal.structures.PilaPropia<T>`

Responsabilidad:

Pila LIFO propia implementada con nodos.

Uso actual:

- Reconstruccion de rutas en Dijkstra.

Uso futuro posible:

- Historial de acciones si se decide alguna funcionalidad auxiliar.
- Apoyo a algoritmos internos.

Metodos publicos:

- `apilar(T elemento)`
- `desapilar()`
- `cima()`
- `tamano()`
- `estaVacia()`

Costes:

- Todas las operaciones principales son O(1).

Tests:

- Pila vacia.
- Apilar.
- Consultar cima sin eliminar.
- Desapilar en orden LIFO.
- Permitir `null`.
- Excepcion al desapilar vacia.
- Excepcion al consultar cima vacia.

## 10. ColaPropia

Clase:

`practicafinal.structures.ColaPropia<T>`

Responsabilidad:

Cola FIFO propia implementada con nodos.

Uso actual:

- BFS de casillas alcanzables.

Uso futuro obligatorio:

- Gestion de turnos.

Persona 3 debe usar `ColaPropia` para turnos. No debe usar `Queue`, `LinkedList`, `ArrayDeque` ni equivalentes.

Metodos publicos:

- `encolar(T elemento)`
- `desencolar()`
- `frente()`
- `tamano()`
- `estaVacia()`

Costes:

- Todas las operaciones principales son O(1).

Tests:

- Cola vacia.
- Encolar.
- Consultar frente sin eliminar.
- Desencolar en orden FIFO.
- Reutilizar despues de vaciar.
- Permitir `null`.
- Excepcion al desencolar vacia.
- Excepcion al consultar frente vacia.

## 11. ListaCircularPropia

Clase:

`practicafinal.structures.ListaCircularPropia<T>`

Responsabilidad:

Lista circular propia para recorridos ciclicos.

Uso previsto:

- Rotacion ciclica de enemigos de una habitacion.

Metodos publicos:

- `agregar(T elemento)`
- `obtenerActual()`
- `siguiente()`
- `eliminar(T elemento)`
- `contiene(T elemento)`
- `tamano()`
- `estaVacia()`

Costes:

- `agregar`: O(1).
- `obtenerActual`: O(1).
- `siguiente`: O(1).
- `eliminar`: O(n).
- `contiene`: O(n).
- `tamano`: O(1).
- `estaVacia`: O(1).

Uso recomendado por Persona 3:

La habitacion puede tener una lista circular de enemigos para recorrerlos de forma ciclica durante su fase de turno, especialmente si se quiere mantener un orden repetitivo.

Tests:

- Lista vacia.
- Recorrido circular.
- Eliminacion del actual.
- Eliminacion del ultimo.
- Eliminacion del unico elemento.
- Busqueda con `contains`.
- Excepcion al obtener actual en lista vacia.

## 12. MatrizPropia

Clase:

`practicafinal.structures.MatrizPropia<T>`

Responsabilidad:

Representar una matriz bidimensional sin usar arrays nativos.

Implementacion:

- Una `ListaEnlazadaPropia` de filas.
- Cada fila es otra `ListaEnlazadaPropia`.
- Las celdas iniciales se rellenan con `null`.

Uso previsto:

- Representar las habitaciones.

Uso recomendado:

```java
private MatrizPropia<Celda> celdas;
```

Metodos publicos:

- `obtener(int fila, int columna)`
- `establecer(int fila, int columna, T valor)`
- `esCoordenadaValida(int fila, int columna)`
- `getFilas()`
- `getColumnas()`

Costes:

- `obtener`: O(fila + columna).
- `establecer`: O(fila + columna).
- `esCoordenadaValida`: O(1).
- `getFilas`: O(1).
- `getColumnas`: O(1).

Relacion con el modelo:

Persona 1 y Persona 3 deben definir `Celda` para que la matriz pueda guardar contenido del juego.

La matriz no sabe que es un enemigo, una puerta o un objeto. Solo guarda valores. Las reglas de dominio deben estar en `model` y `logic`.

Tests:

- Crear matriz con dimensiones validas.
- Valores iniciales `null`.
- Establecer y obtener valores.
- Sobrescribir valores.
- Coordenadas invalidas.
- Excepcion por coordenada invalida.
- Excepcion por dimensiones no positivas.

## 13. GrafoPropio

Clase:

`practicafinal.structures.GrafoPropio<T>`

Responsabilidad:

Representar el mapa global de habitaciones.

Caracteristicas:

- Grafo propio.
- No dirigido.
- Ponderado.
- No permite vertices `null`.

Por que no permite `null`:

Dijkstra usa `null` internamente para indicar ausencia de anterior al reconstruir una ruta. Ademas, una habitacion del juego debe tener identidad estable, por id u objeto.

Metodos publicos:

- `agregarVertice(T valor)`
- `agregarArista(T origen, T destino, int coste)`
- `existeVertice(T valor)`
- `existeArista(T origen, T destino)`
- `obtenerCoste(T origen, T destino)`
- `obtenerVecinos(T valor)`
- `obtenerVertices()`
- `numeroVertices()`

Costes:

- `agregarVertice`: O(v).
- `agregarArista`: O(v + aNodo).
- `existeVertice`: O(v).
- `existeArista`: O(v + aNodo).
- `obtenerCoste`: O(v + aNodo).
- `obtenerVecinos`: O(v + aNodo).
- `obtenerVertices`: O(v).

Uso recomendado:

Para facilitar JSON, se recomienda usar identificadores:

```java
GrafoPropio<String> mapa = new GrafoPropio<String>();
mapa.agregarArista("entrada", "pasillo", 2);
mapa.agregarArista("pasillo", "salida", 3);
```

Tambien podria usarse:

```java
GrafoPropio<Habitacion> mapa = new GrafoPropio<Habitacion>();
```

Pero si se usa `Habitacion`, habra que cuidar igualdad, serializacion JSON y depuracion.

Tests:

- Grafo vacio.
- Agregar vertices sin duplicar.
- Agregar arista no dirigida.
- Actualizar coste.
- Obtener vecinos.
- Obtener vertices.
- Evitar duplicacion de vecinos.
- Rechazar coste negativo.
- Rechazar vertice nulo.
- Excepcion al pedir vecinos de vertice inexistente.

## 14. ArbolPropio

Clase:

`practicafinal.structures.ArbolPropio<T>`

Responsabilidad:

Representar una jerarquia general propia.

Uso previsto:

- Organizar acciones posibles del jugador durante un turno.

Ejemplo de arbol de acciones:

```text
Turno
  Movimiento
    Mover
    No mover
  Accion
    Atacar
    Recoger objeto
    Usar objeto
    Equipar objeto
    Abrir puerta
    No hacer nada
```

Metodos publicos:

- `crearRaiz(T valor)`
- `obtenerRaiz()`
- `agregarHijo(T valorPadre, T valorHijo)`
- `obtenerHijos(T valorPadre)`
- `contiene(T valor)`
- `recorrerPreorden()`
- `tamano()`
- `estaVacio()`

Costes:

- `crearRaiz`: O(1).
- `obtenerRaiz`: O(1).
- `agregarHijo`: O(n), porque busca el padre.
- `obtenerHijos`: O(n), porque busca el padre.
- `contiene`: O(n).
- `recorrerPreorden`: O(n).
- `tamano`: O(1).
- `estaVacio`: O(1).

Tests:

- Arbol vacio.
- Crear raiz.
- Agregar hijos.
- Niveles anidados.
- Recorrido en preorden.
- Excepcion al agregar sin raiz.
- Excepcion al agregar a padre inexistente.

## 15. BFS de casillas alcanzables

Clase:

`practicafinal.logic.BuscadorCasillasAlcanzables`

Metodo principal:

```java
ListaEnlazadaPropia<Coordenada> calcular(MatrizPropia<Boolean> transitables,
                                         int filaInicial,
                                         int columnaInicial,
                                         int movimientoMaximo)
```

Responsabilidad:

Calcular las casillas a las que puede llegar una unidad dentro de una habitacion, respetando su movimiento maximo.

Estructuras usadas:

- `MatrizPropia<Boolean>` para transitabilidad.
- `MatrizPropia<Boolean>` para visitadas.
- `ColaPropia<NodoBusquedaCasilla>` para BFS.
- `ListaEnlazadaPropia<Coordenada>` para el resultado.

Convenciones:

- `Boolean.TRUE`: celda transitable.
- `Boolean.FALSE`: celda bloqueada.
- `null`: celda bloqueada.
- La posicion inicial debe ser valida.
- El movimiento maximo no puede ser negativo.
- El resultado no incluye la posicion inicial.
- Solo se explora arriba, abajo, izquierda y derecha.
- No hay movimiento diagonal directo.

Uso esperado por Persona 3:

Persona 3 no debe reimplementar BFS. Debe adaptar la habitacion real a una matriz booleana.

Ejemplo conceptual:

```java
MatrizPropia<Boolean> transitables = habitacion.crearMatrizTransitabilidad();
BuscadorCasillasAlcanzables buscador = new BuscadorCasillasAlcanzables();

ListaEnlazadaPropia<Coordenada> alcanzables = buscador.calcular(
    transitables,
    jugador.getFila(),
    jugador.getColumna(),
    jugador.getMovimiento()
);
```

JavaFX debe iluminar las coordenadas devueltas, pero no calcularlas directamente.

Tests:

- Movimiento 1 alcanza solo casillas ortogonales.
- Respeta limite de movimiento.
- Evita casillas no transitables.
- Movimiento 0 no devuelve destinos.
- Posicion inicial invalida.
- Movimiento negativo.
- Matriz nula.

## 16. Coordenada

Clase:

`practicafinal.logic.Coordenada`

Responsabilidad:

Representar una posicion dentro de una matriz.

Metodos publicos:

- `getFila()`
- `getColumna()`
- `mismaPosicion(int fila, int columna)`

Uso actual:

- Resultado de `BuscadorCasillasAlcanzables`.

Uso futuro:

- Persona 3 puede usarla para representar destinos seleccionables en UI o logica.

## 17. Dijkstra y rutas entre habitaciones

Clase:

`practicafinal.logic.CalculadorRutasHabitaciones`

Metodo principal:

```java
<T> RutaMinima<T> calcularRutaMinima(GrafoPropio<T> grafo, T origen, T destino)
```

Responsabilidad:

Calcular la ruta de menor coste entre dos habitaciones del grafo.

Por que Dijkstra:

El grupo decidio que las conexiones entre habitaciones tienen coste variable. Con costes variables, BFS entre habitaciones no basta para asegurar coste minimo, por lo que se implemento Dijkstra.

Estructuras usadas:

- `GrafoPropio<T>`
- `ListaEnlazadaPropia<EstadoRuta<T>>`
- `PilaPropia<T>` para reconstruir la ruta.
- `ListaEnlazadaPropia<T>` como resultado.

Comportamiento:

- Si `grafo` es `null`, lanza excepcion.
- Si origen no existe, lanza excepcion.
- Si destino no existe, lanza excepcion.
- Si no hay ruta, devuelve `RutaMinima` con coste `-1`.
- Si no hay ruta, `RutaMinima.existe()` devuelve `false`.
- Si origen y destino son iguales, la ruta existe con coste `0`.

Uso esperado por Persona 3:

Persona 3 debe usarlo para informar constantemente al jugador de la ruta o coste minimo hacia la salida.

Ejemplo conceptual:

```java
CalculadorRutasHabitaciones calculador = new CalculadorRutasHabitaciones();
RutaMinima<String> ruta = calculador.calcularRutaMinima(mapa, habitacionActualId, salidaId);

if (ruta.existe()) {
    int coste = ruta.getCosteTotal();
    ListaEnlazadaPropia<String> habitaciones = ruta.getVertices();
}
```

Tests:

- Ruta minima con costes variables.
- Origen y destino iguales.
- Destino inalcanzable.
- Origen inexistente.
- Destino inexistente.
- Grafo nulo.

## 18. RutaMinima

Clase:

`practicafinal.logic.RutaMinima<T>`

Responsabilidad:

Encapsular el resultado de Dijkstra.

Metodos publicos:

- `getVertices()`
- `getCosteTotal()`
- `existe()`

Interpretacion:

- Si `existe()` es `true`, `getVertices()` contiene la ruta desde origen hasta destino.
- Si `existe()` es `false`, `getVertices()` esta vacia y el coste es `-1`.

## 19. Como debe construir Persona 1 el modelo encima de esta base

Persona 1 debe definir el modelo de dominio sin sustituir las estructuras propias.

Clases esperadas:

- `Jugador`
- `Habitacion`
- `Celda`
- `Enemigo`
- `Objeto`
- `Puerta`
- `Trampa`
- `Partida`
- Excepciones del dominio.

Recomendaciones concretas:

- `Habitacion` debe contener `MatrizPropia<Celda>`.
- `Jugador` debe guardar habitacion actual y coordenadas.
- `Enemigo` debe guardar coordenadas dentro de la habitacion.
- `Puerta` debe guardar habitacion destino o id destino.
- `Celda` debe impedir multiples entidades principales al mismo tiempo.
- La vida de jugador y enemigos no debe quedar por debajo de cero.
- El modelo no debe depender de JavaFX.
- El modelo no debe hacer persistencia JSON directamente.

Ejemplo conceptual de `Habitacion`:

```java
public class Habitacion {
    private String id;
    private MatrizPropia<Celda> celdas;
}
```

Ejemplo conceptual de `Jugador`:

```java
public class Jugador {
    private String habitacionActualId;
    private int fila;
    private int columna;
    private int vida;
    private int ataque;
    private int defensa;
    private int movimiento;
}
```

La implementacion real puede variar, pero debe mantener compatibilidad con `MatrizPropia`, `GrafoPropio`, BFS y Dijkstra.

## 20. Como debe construir Persona 3 la logica encima de esta base

Persona 3 debe integrar las reglas de juego usando las estructuras y algoritmos ya creados.

Debe usar:

- `ColaPropia` para turnos.
- `MatrizPropia<Celda>` para habitaciones.
- `BuscadorCasillasAlcanzables` para movimiento interno.
- `GrafoPropio` para mapa global.
- `CalculadorRutasHabitaciones` para rutas minimas.
- `ListaCircularPropia` para rotacion de enemigos si se aplica.
- `ArbolPropio` para acciones disponibles si se representa formalmente.

No debe hacer:

- No reimplementar BFS.
- No reimplementar Dijkstra.
- No usar estructuras estandar prohibidas.
- No cambiar APIs publicas de Persona 2 sin avisar.
- No meter reglas de juego en JavaFX.
- No cambiar JSON despues de tener tests sin coordinarlo.

## 21. Integracion del movimiento dentro de una habitacion

Flujo recomendado:

1. El jugador esta en una habitacion actual.
2. La habitacion contiene `MatrizPropia<Celda>`.
3. La logica construye una `MatrizPropia<Boolean>` de transitabilidad.
4. Cada celda libre o pisable se marca como `Boolean.TRUE`.
5. Cada pared, enemigo bloqueante, obstaculo o elemento no pisable se marca como `Boolean.FALSE`.
6. Se llama a `BuscadorCasillasAlcanzables.calcular`.
7. La UI recibe la lista de `Coordenada` alcanzables.
8. La UI ilumina esas casillas.
9. El jugador elige una casilla de esa lista.
10. La logica valida que la casilla elegida esta en la lista.
11. La logica actualiza la posicion del jugador.
12. La logica marca que el jugador ya ha usado su movimiento en ese turno.

Este flujo cumple:

- Movimiento maximo por turno.
- Prohibicion de movimiento diagonal directo.
- Separacion entre logica e interfaz.

## 22. Integracion del mapa de habitaciones

Flujo recomendado:

1. Cada habitacion tiene un id unico.
2. El mapa usa `GrafoPropio<String>`.
3. Cada conexion se crea con `agregarArista(origen, destino, coste)`.
4. Cada puerta conoce el id de habitacion destino.
5. Al abrir una puerta, la logica valida que la conexion existe.
6. Si la puerta conecta con otra habitacion, se cambia la habitacion actual.
7. Si la puerta es salida exterior, se declara victoria.
8. Cambiar de habitacion termina automaticamente el turno.

Ejemplo conceptual:

```java
GrafoPropio<String> mapa = new GrafoPropio<String>();
mapa.agregarArista("entrada", "pasillo", 2);
mapa.agregarArista("pasillo", "tesoro", 4);
mapa.agregarArista("pasillo", "salida", 1);
```

## 23. Integracion de rutas minimas hasta la salida

Requisito del enunciado:

El juego debe informar constantemente al jugador de la distancia minima hacia la puerta adecuada y el numero minimo de habitaciones hasta la salida. Ademas, el jugador puede comprar ver el camino en pantalla.

Con la base actual:

- `CalculadorRutasHabitaciones` da la ruta minima en coste.
- `RutaMinima.getVertices()` da las habitaciones del camino.
- El numero minimo de habitaciones puede calcularse como `ruta.getVertices().tamano() - 1` si la ruta existe.
- La siguiente habitacion de la ruta indica hacia que puerta conviene ir.

Persona 3 debe completar:

- Como se calcula la puerta adecuada dentro de la matriz.
- Como se muestra el coste o ruta en JavaFX.
- Que significa comprar ver el camino, segun decision del grupo.

## 24. Integracion de turnos

Requisito del enunciado:

En un turno el jugador puede hacer como maximo un movimiento y una accion. Primero actua el jugador y despues los enemigos de la habitacion.

Uso recomendado de `ColaPropia`:

1. Crear una cola de actores del turno.
2. Encolar primero al jugador.
3. Encolar despues los enemigos de la habitacion.
4. Desencolar actor por actor.
5. Ejecutar su fase.
6. Al acabar todos, reducir contador de turnos.
7. Preparar nuevo turno.

Ejemplo conceptual:

```java
ColaPropia<Actor> turno = new ColaPropia<Actor>();
turno.encolar(jugador);
turno.encolar(enemigo1);
turno.encolar(enemigo2);

while (!turno.estaVacia()) {
    Actor actor = turno.desencolar();
    gestorTurnos.ejecutar(actor);
}
```

No se debe usar `java.util.Queue`.

## 25. Integracion de enemigos

La lista circular se decidio para rotacion de enemigos.

Uso posible:

```java
ListaCircularPropia<Enemigo> enemigos = new ListaCircularPropia<Enemigo>();
```

Persona 3 puede usarla para:

- Mantener orden ciclico de enemigos.
- Avanzar al siguiente enemigo activo.
- Eliminar enemigos derrotados.

Si finalmente los turnos principales se hacen solo con `ColaPropia`, la lista circular debe seguir teniendo un uso real, por ejemplo para rotar enemigos dentro de cada habitacion antes de encolarlos.

## 26. Integracion de acciones disponibles

El arbol propio se decidio para organizar acciones posibles.

Uso posible:

```java
ArbolPropio<String> acciones = new ArbolPropio<String>();
acciones.crearRaiz("Turno");
acciones.agregarHijo("Turno", "Movimiento");
acciones.agregarHijo("Turno", "Accion");
acciones.agregarHijo("Accion", "Atacar");
acciones.agregarHijo("Accion", "Recoger");
acciones.agregarHijo("Accion", "Usar objeto");
```

Persona 3 puede usarlo para construir el panel de acciones disponibles en JavaFX.

## 27. Integracion con JavaFX

JavaFX debe ser una capa visual. No debe contener reglas de juego.

La interfaz debe mostrar:

- Matriz completa de la habitacion.
- Posicion del jugador.
- Enemigos.
- Objetos.
- Puertas y salidas.
- Casillas alcanzables iluminadas.
- Estado del jugador.
- Inventario visible constantemente.
- Acciones disponibles.
- Registro de eventos.
- Informacion de ruta minima.

JavaFX debe pedir informacion a la logica, no calcularla directamente.

Ejemplo de separacion correcta:

- UI: detecta click en celda.
- Logic: valida si la celda es alcanzable.
- Model: actualiza posicion si procede.
- UI: refresca vista.

Ejemplo incorrecto:

- El controlador JavaFX calcula BFS y modifica vida directamente.

## 28. Integracion con JSON

Persona 3 debe implementar persistencia JSON usando el modelo final.

Como el grafo es ponderado, el JSON de configuracion debe guardar costes:

```json
{
  "conexiones": [
    {
      "origen": "entrada",
      "destino": "pasillo",
      "coste": 2
    }
  ]
}
```

La configuracion inicial debe incluir:

- Habitaciones.
- Dimensiones de matrices.
- Contenido inicial de celdas.
- Grafo de conexiones.
- Costes de conexiones.
- Posicion inicial del jugador.
- Enemigos.
- Objetos.
- Puertas.
- Salidas.
- Turnos maximos.

El estado de partida debe incluir:

- Habitacion actual.
- Coordenadas del jugador.
- Vida, ataque, defensa y movimiento.
- Inventario.
- Objetos equipados.
- Estado de enemigos.
- Objetos recogidos.
- Estado de puertas y trampas.
- Turnos restantes o consumidos.
- Estado general: en curso, victoria o derrota.

## 29. Tests existentes

Hay tests para:

- `BaseProjectTest`
- `ListaEnlazadaPropiaTest`
- `PilaPropiaTest`
- `ColaPropiaTest`
- `ListaCircularPropiaTest`
- `MatrizPropiaTest`
- `GrafoPropioTest`
- `ArbolPropioTest`
- `BuscadorCasillasAlcanzablesTest`
- `CalculadorRutasHabitacionesTest`

Resultado actual:

- Tests ejecutados: 66.
- Fallos: 0.
- Errores: 0.
- Omitidos: 0.

Cada vez que Persona 1 o Persona 3 cambien codigo, deben ejecutar `mvn test`.

## 30. Archivos de documentacion relacionados

Ademas de esta guia, existen:

- `docs/estructuras-costes.md`: costes y justificacion de estructuras.
- `docs/integracion-persona-2.md`: resumen de APIs disponibles.
- `docs/revision-persona-2.md`: revision exhaustiva de cumplimiento.
- `docs/diario-ia.md`: registro de uso de IA.

Si se cambia una API o decision, hay que actualizar estos documentos.

## 31. Que no deben tocar sin coordinar

Persona 1 no debe modificar sin coordinar:

- `practicafinal.structures`
- Algoritmos de BFS o Dijkstra.
- APIs publicas ya usadas por tests.

Persona 3 no debe modificar sin coordinar:

- Estructuras propias.
- Firma de `BuscadorCasillasAlcanzables.calcular`.
- Firma de `CalculadorRutasHabitaciones.calcularRutaMinima`.
- Representacion ponderada del grafo.

Nadie debe cambiar sin consenso:

- Grafo ponderado.
- Costes variables.
- Matriz sin arrays.
- Restricciones de estructuras prohibidas.
- Formato JSON una vez tenga tests.

## 32. Que falta para completar el juego final

Falta implementar:

- Modelo de dominio real.
- Invariantes del modelo.
- Excepciones personalizadas.
- Gestion completa de turnos.
- Movimiento real del jugador.
- Movimiento basico de enemigos.
- Combate con formula del enunciado.
- Defensa automatica.
- Objetos.
- Inventario.
- Equipamiento.
- Objetos fungibles.
- Puertas.
- Trampas.
- Salida exterior.
- Victoria.
- Derrota por vida.
- Derrota por turnos.
- Log de eventos.
- Persistencia JSON.
- JSON de ejemplo.
- JavaFX completa.
- Tests de modelo.
- Tests de logica.
- Tests de persistencia.
- UML obligatorio.
- Bocetos.
- Memoria final.
- Video.

## 33. Checklist para Persona 1

Antes de que Persona 3 integre logica completa, Persona 1 deberia dejar claro:

- `Celda` definida.
- `Habitacion` definida con `MatrizPropia<Celda>`.
- `Jugador` definido con habitacion actual y coordenadas.
- `Enemigo` definido con coordenadas.
- `Objeto` definido.
- `Puerta` definida.
- `Trampa` definida si se implementa.
- `Partida` o estado general definido.
- Invariantes documentados.
- Contratos principales documentados.
- UML actualizado.
- Tests basicos de modelo.

## 34. Checklist para Persona 3

Antes de empezar JavaFX, Persona 3 deberia implementar y probar:

- Turnos con `ColaPropia`.
- Movimiento con `BuscadorCasillasAlcanzables`.
- Cambio de habitacion con `GrafoPropio`.
- Ruta minima con `CalculadorRutasHabitaciones`.
- Combate.
- Objetos e inventario.
- Condiciones de victoria y derrota.
- Log de eventos.
- JSON de configuracion.
- Guardado de partida.
- Carga de partida.
- Tests de logica.
- Tests de persistencia.

## 35. Checklist antes de integrar cambios

Antes de juntar cambios de cualquier persona:

1. Revisar que no se han usado estructuras prohibidas.
2. Ejecutar `mvn test`.
3. Confirmar que no se han roto APIs publicas.
4. Confirmar que la documentacion sigue siendo cierta.
5. Confirmar que UML y memoria no contradicen el codigo.
6. Registrar uso de IA si se ha usado.

Comando recomendado:

```powershell
$env:JAVA_HOME = "$PWD\.tools\jdk8\jdk8u492-b09"
$env:PATH = "$env:JAVA_HOME\bin;$PWD\.tools\maven\apache-maven-3.9.9\bin;$env:PATH"
mvn test
```

## 36. Conclusion

La parte de Persona 2 es una base tecnica ya compilada, probada y documentada. No es el juego completo, pero contiene las estructuras y algoritmos obligatorios sobre los que debe construirse el resto.

Si Persona 1 define el modelo respetando `MatrizPropia`, `GrafoPropio` e invariantes, y Persona 3 implementa la logica usando BFS, Dijkstra y la cola propia, el juego quedara alineado con el enunciado y con las restricciones de Estructuras de Datos.

La regla principal para continuar es: no sustituir esta base por estructuras estandar de Java y no duplicar algoritmos que ya existen.

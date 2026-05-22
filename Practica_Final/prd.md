# PRD - Practica Final MP + EEDD

## Objetivo del proyecto

Desarrollar, en un grupo de 3 personas, un juego sencillo por turnos con interfaz grafica en JavaFX, donde un jugador explora una red de habitaciones conectadas mediante un grafo.

Cada habitacion se representa como una matriz bidimensional de dimensiones arbitrarias, pudiendo ser distintas entre habitaciones. Sus celdas pueden contener enemigos, objetos, trampas, puertas, salidas u otros elementos interactivos.

El objetivo del jugador es partir desde una habitacion inicial y conseguir salir de la red de habitaciones antes de quedarse sin turnos o sin vida.

El proyecto debe demostrar:

- Diseno orientado a objetos.
- Especificacion previa antes de programar.
- Uso de estructuras de datos propias.
- Persistencia en JSON.
- Separacion entre logica de juego e interfaz.
- Aplicacion de metodologia de desarrollo.
- Uso responsable y documentado de herramientas de IA.

## Entregables obligatorios

- Codigo fuente.
- Repositorio GitHub.
- ZIP del proyecto.
- Documento de diseno.
- Diagramas UML.
- JSON de ejemplo.
- Pruebas.
- Diario de uso de IA.
- Bocetos previos de interfaz.
- Memoria en PDF.
- Seccion obligatoria en la memoria llamada "critica del proyecto".
- Video explicativo mostrando todas las funcionalidades.
- Entrega separada para Metodologia de la Programacion.
- Entrega separada para Estructuras de Datos.

## Requisitos funcionales

El sistema debe permitir:

- Crear un grafo de habitaciones.
- Definir cada habitacion como una matriz bidimensional.
- Representar la habitacion completa en JavaFX.
- Mostrar la posicion del jugador.
- Mostrar objetos, enemigos y elementos interactivos.
- Mover al jugador dentro de una habitacion.
- Mover al jugador entre habitaciones.
- Permitir acciones del jugador sobre casillas adyacentes cuando corresponda.
- Calcular casillas alcanzables segun la capacidad de movimiento.
- Prohibir movimiento diagonal directo.
- Gestionar turnos.
- Permitir como maximo un movimiento y una accion por turno.
- Ejecutar primero el turno del jugador y despues el de los enemigos.
- Recoger objetos.
- Usar objetos.
- Equipar objetos cuando corresponda.
- Gestionar inventario.
- Mostrar constantemente el inventario.
- Atacar enemigos.
- Aplicar defensa automatica ante ataques.
- Resolver ataques con la formula del enunciado: `vida = vida - maximo(0, ataque * (aleatorio * 2) - defensa)`.
- Gestionar vida, ataque, defensa y movimiento.
- Gestionar modificadores de objetos.
- Gestionar objetos fungibles.
- Cambiar de habitacion al abrir una puerta.
- Terminar el turno automaticamente al cambiar de habitacion.
- Detectar victoria al abrir una puerta de salida exterior.
- Detectar derrota por falta de turnos.
- Detectar derrota por vida agotada.
- Guardar partida en JSON.
- Cargar partida desde JSON.
- Cargar configuracion inicial desde JSON.
- Mostrar el estado del jugador.
- Mostrar acciones disponibles.
- Mostrar registro de eventos del juego.
- Mostrar el log completo al final de la partida.
- Calcular e informar constantemente la distancia minima hasta la puerta adecuada y el numero minimo de habitaciones hasta la salida.
- Permitir que el jugador pueda "comprar" ver el camino en pantalla.
- Mover enemigos hacia el jugador dentro de la habitacion y permitir que ataquen al llegar o cuando tengan alcance suficiente.

## Requisitos funcionales opcionales o pendientes de decision

Estos puntos aparecen abiertos en el enunciado o dependen de decisiones del grupo. No deben implementarse como obligatorios hasta cerrarlos:

- Puertas que requieran condiciones especiales o llaves.
- Enemigos que cambien de habitacion.
- Contador de turnos por habitacion ademas del contador general.
- Costes variables de movimiento y uso de Dijkstra.
- Equipamiento por zonas corporales, como manos, torso o dedos.
- Objetos con usos maximos o duracion por turnos o tiempo real.
- Mecanicas adicionales de trampas, puertas, objetos especiales o habilidades.
- Uso funcional concreto del arbol propio, si se decide algo distinto al arbol de acciones posibles.

## Requisitos tecnicos

- Lenguaje: Java.
- Interfaz grafica: JavaFX.
- Persistencia: JSON.
- Pruebas: JUnit para todas las clases no visuales.
- Arquitectura recomendada: separacion entre logica, modelo, persistencia e interfaz.
- Patron recomendado: MVC basico o similar.
- La habitacion debe representarse internamente como una matriz implementada con estructuras propias.
- El mapa global debe implementarse como un grafo propio.
- Se deben implementar estructuras de datos propias.
- Se debe evitar el uso de estructuras estandar equivalentes de Java.
- Se deben gestionar excepciones.
- Se debe registrar un log de operaciones del juego.
- Se debe documentar el coste de las operaciones de las estructuras usadas.

## Persistencia JSON obligatoria

El sistema debe manejar dos tipos de datos en JSON:

### Configuracion inicial de partida

Debe incluir como minimo:

- Grafo de habitaciones.
- Dimensiones de cada matriz.
- Contenido inicial de cada celda.
- Posicion inicial del jugador.
- Objetivo del juego y puertas de salida.
- Enemigos iniciales, objetos iniciales y elementos interactivos iniciales.

### Estado de partida

Debe incluir como minimo:

- Habitacion actual del jugador y coordenadas dentro de la habitacion.
- Vida, ataque, defensa, movimiento y modificadores activos del jugador.
- Inventario del jugador y objetos equipados.
- Estado de enemigos.
- Objetos recogidos o eliminados del tablero.
- Estado de puertas, trampas y elementos interactivos que puedan cambiar.
- Numero de turnos restantes o consumidos.
- Estado general del juego: en curso, victoria o derrota.

## Criterios de evaluacion

### Metodologia de la Programacion

- Correcta identificacion y modelado de clases.
- Encapsulamiento adecuado.
- Uso de herencia y polimorfismo.
- Cohesion alta y bajo acoplamiento.
- Claridad y organizacion del codigo.
- UML coherente.
- Diseno visual previo.
- Gestion robusta de excepciones.
- Excepciones personalizadas cuando sea relevante.
- Serializacion y deserializacion correcta en JSON.
- Integridad de datos al guardar y cargar.
- Manejo de errores de entrada/salida.
- Interfaz JavaFX funcional y clara.
- Separacion entre logica e interfaz.
- Respuesta correcta a eventos de usuario.
- Diario de uso de IA completo.
- Critica sobre el uso de IA y reajustes realizados.

### Estructuras de Datos

- Implementacion propia de listas.
- Implementacion propia de pilas.
- Implementacion propia de colas.
- Implementacion propia de listas circulares.
- Implementacion propia de arboles.
- Implementacion propia de grafos.
- Uso correcto de cola para turnos.
- Uso correcto de grafo para habitaciones.
- Uso de BFS para casillas alcanzables o busquedas.
- Uso de Dijkstra si hay costes variables.
- Justificacion de cada estructura usada.
- Coste de operaciones documentado.
- Integracion correcta de las estructuras con la logica del juego.
- Eficiencia razonable.

## Restricciones del profesor

- No se puede usar `ArrayList`.
- No se puede usar `HashMap`.
- No se puede usar `LinkedList`.
- No se pueden usar estructuras estandar equivalentes.
- Usar estructuras de Java o librerias externas para las estructuras evaluadas implica 0 en Estructuras de Datos.
- El mapa debe ser un grafo propio.
- La matriz de habitaciones debe implementarse con estructuras propias.
- Se debe disenar antes de programar.
- No se debe empezar directamente por JavaFX.
- Se debe hacer especificacion previa.
- El uso de IA solo esta permitido tras la especificacion.
- Todo uso de IA debe registrarse.
- Todo el codigo debe poder explicarse.
- Se deben anadir tests unitarios con JUnit para las clases no visuales.
- La memoria debe incluir una seccion llamada "critica del proyecto".
- En la portada de la memoria deben aparecer todos los alumnos.
- Si un alumno no aparece en la portada, se entiende que no ha colaborado suficientemente y tendra nota cero.
- El video debe mostrar todas las funcionalidades.
- En el video deben aparecer todos los alumnos.
- El video debe subirse a Blackboard como fichero de menos de 100 MB o dividido en varios ficheros de menos de 100 MB.
- Si se sube a YouTube, tambien debe subirse a Blackboard; si no esta en Blackboard, no se tendra en cuenta.
- Debe haber una entrega para cada asignatura.
- Se pueden reutilizar desarrollos previos de la asignatura, siempre que todos puedan explicarlos y se adapten al proyecto.

## Invariantes obligatorios del dominio

- El jugador siempre ocupa una celda valida dentro de la habitacion actual.
- Una celda no contiene multiples entidades principales al mismo tiempo.
- Las acciones de atacar, recoger objetos o abrir puertas se realizan sobre celdas adyacentes cuando proceda.
- Para atravesar una puerta, el jugador debe situarse en la celda de la puerta.
- Si una trampa se activa al pasar por ella, debe aplicarse su efecto y desaparecer, salvo decision distinta documentada.
- Un mismo objeto concreto no puede estar duplicado, aunque puede haber varios objetos iguales como instancias distintas.
- La vida de jugadores y enemigos nunca debe quedar por debajo de cero.
- El turno del jugador se resuelve antes que el de los enemigos de la habitacion.
- En un turno el jugador puede hacer como maximo un movimiento y una accion.
- El movimiento diagonal directo esta prohibido.

## Partes relacionadas con Metodologias de la Programacion

- Especificacion de requisitos funcionales.
- Especificacion de requisitos no funcionales.
- Casos de uso.
- Modelo de dominio.
- Contratos e interfaces.
- Invariantes.
- Diseno orientado a objetos.
- Encapsulamiento.
- Herencia y polimorfismo.
- UML obligatorio:
- Diagrama de casos de uso.
- Diagrama de clases.
- Diagrama de secuencia.
- Diagrama de estados.
- Diagrama de actividad.
- Diseno previo de interfaz.
- Bocetos de pantallas.
- Separacion logica/interfaz.
- Gestion de excepciones.
- Persistencia JSON.
- Pruebas.
- Logs.
- Diario de uso de IA.
- Memoria del proyecto.
- Critica del proyecto.

## Partes relacionadas con Estructuras de Datos

- Implementacion propia de listas enlazadas.
- Implementacion propia de pilas.
- Implementacion propia de colas.
- Implementacion propia de listas circulares.
- Implementacion propia de arboles.
- Implementacion propia de grafos.
- Representacion del mapa como grafo propio.
- Representacion de habitaciones como matrices con estructuras propias.
- Inventario mediante estructura propia.
- Turnos mediante cola propia.
- Posibles acciones mediante arbol.
- Busqueda de casillas alcanzables mediante BFS.
- Calculo de rutas entre habitaciones.
- Posible uso de Dijkstra si existen costes variables.
- Justificacion de estructuras.
- Analisis de costes de operaciones.
- Tests especificos de estructuras.

## Dudas o puntos ambiguos que hay que confirmar

- El documento se titula "Practica Final 2026", pero la fecha limite indicada es 28/5/2025 a las 10h.
- No queda cerrado si el grafo de habitaciones debe ser dirigido o no dirigido.
- No se especifica si las conexiones entre habitaciones tienen peso o coste.
- No se define cuantas habitaciones minimas debe tener el juego.
- No se define el tamano minimo o maximo de las matrices.
- No se indica cuantos enemigos, objetos o puertas debe haber como minimo.
- No se concreta si los enemigos pueden cambiar de habitacion; se indica que es decision del grupo.
- No se concreta si debe existir contador de turnos por habitacion; aparece como opcional.
- No se define como se "compra" la visualizacion del camino minimo.
- No se especifica si comprar el camino consume turnos, objetos, puntos u otro recurso.
- No se especifica el formato exacto del JSON.
- No se especifica si se puede usar alguna libreria JSON concreta.
- No se aclara si `java.util` queda completamente prohibido o solo sus estructuras de datos.
- No se concreta si los arrays nativos de Java estan permitidos para la matriz.
- No se define si la matriz propia debe ser una estructura enlazada, una clase envoltorio sobre arrays o una implementacion completamente propia.
- No se concreta si el arbol es obligatorio aunque su uso funcional sea limitado.
- No se especifica que significa exactamente "listas circulares" dentro del juego.
- No se indica el numero minimo de pruebas JUnit.
- No se define si los bocetos deben estar dentro de la memoria o como archivo separado.
- No se indica duracion minima o maxima del video.
- No se especifica si el registro de IA debe ir en la memoria o en documento aparte.

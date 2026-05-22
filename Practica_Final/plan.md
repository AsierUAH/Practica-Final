# Plan de trabajo - Practica Final MP + EEDD

## Fases del trabajo

### Fase 1: Aclaracion y decisiones iniciales

Objetivo: cerrar las decisiones ambiguas antes de disenar o programar.

Tareas:

- Confirmar fecha real de entrega.
- Decidir si el grafo de habitaciones sera dirigido o no dirigido.
- Decidir si las conexiones entre habitaciones tendran coste fijo o variable.
- Decidir si se usaran contadores de turnos por habitacion.
- Decidir si los enemigos pueden cambiar de habitacion.
- Decidir como se compra la visualizacion del camino minimo.
- Decidir numero minimo de habitaciones, enemigos, objetos y puertas.
- Decidir formato de los JSON de configuracion y partida.
- Confirmar que libreria JSON se puede usar.
- Confirmar si se permiten arrays nativos de Java para implementar estructuras internas.
- Decidir si se reutilizaran estructuras o codigo de practicas previas y quien debe revisarlas.
- Decidir el uso funcional concreto del arbol propio.
- Decidir el formato exacto del diario de IA y donde se guardara.
- Definir alcance realista de ampliaciones opcionales.

Resultado esperado:

- Lista de decisiones cerradas para incluir en la memoria.
- Alcance minimo viable del juego.

### Fase 2: Especificacion y diseno previo

Objetivo: completar la parte metodologica antes de programar.

Tareas:

- Redactar requisitos funcionales definitivos.
- Redactar requisitos no funcionales.
- Definir casos de uso principales.
- Definir precondiciones y postcondiciones de los casos de uso.
- Definir modelo de dominio.
- Definir invariantes del sistema.
- Definir contratos e interfaces principales.
- Preparar bocetos de interfaz.
- Definir flujo de pantallas.
- Definir estructura de paquetes o modulos.
- Definir criterios de prueba.
- Crear plantilla del diario de IA.

Resultado esperado:

- Documento de diseno inicial.
- Bocetos de interfaz.
- Casos de uso listos.
- Base para UML.

### Fase 3: UML obligatorio

Objetivo: preparar todos los diagramas exigidos antes de implementar.

Tareas:

- Crear diagrama de casos de uso.
- Crear diagrama de clases.
- Crear diagrama de secuencia para al menos una operacion principal.
- Crear diagrama de estados del juego.
- Crear diagrama de actividad para la logica del turno del jugador.
- Revisar que el UML coincide con el diseno previsto.

Resultado esperado:

- UML listo para la memoria.
- Diseno validado por el grupo.

### Fase 4: Proyecto Java base

Objetivo: crear la base tecnica del proyecto antes de repartir implementaciones.

Tareas:

- Elegir Maven o Gradle.
- Crear estructura inicial de carpetas.
- Configurar JavaFX.
- Configurar JUnit.
- Definir paquete base del proyecto.
- Crear comandos reales de compilacion, ejecucion y pruebas.
- Actualizar `AGENTS.md` con los comandos definitivos.
- Crear `docs/diario-ia.md` si se decide usar un diario separado.
- Crear carpeta `docs/uml` y `docs/bocetos` si se guardan como archivos separados.

Resultado esperado:

- Proyecto Java compilable aunque este vacio.
- Comandos reales documentados.
- Estructura de carpetas preparada para trabajar sin pisarse.

### Fase 5: Estructuras de datos propias

Objetivo: implementar y probar las estructuras antes de usarlas en el juego.

Tareas:

- Implementar lista enlazada propia.
- Implementar pila propia.
- Implementar cola propia.
- Implementar lista circular propia.
- Implementar arbol propio.
- Implementar grafo propio.
- Implementar matriz propia o estructura equivalente para habitaciones.
- Confirmar que el arbol tiene un uso real en el proyecto, preferiblemente para organizar acciones posibles.
- Documentar operaciones y costes.
- Crear pruebas JUnit de estructuras.
- Verificar que no se usan `ArrayList`, `HashMap`, `LinkedList` ni equivalentes.

Resultado esperado:

- Estructuras funcionales.
- Tests de estructuras.
- Justificacion tecnica para la memoria.

### Fase 6: Modelo del juego

Objetivo: implementar las clases centrales sin interfaz grafica.

Tareas:

- Crear clase o modulo de jugador.
- Crear clase o modulo de enemigo.
- Crear clase o modulo de objeto.
- Crear tipos de objetos, como arma, pocion, llave o escudo.
- Crear inventario usando estructuras propias.
- Crear clase o modulo de celda.
- Crear clase o modulo de habitacion.
- Crear clase o modulo de puerta, salida, trampa u otros elementos interactivos.
- Crear clase o modulo de partida o estado de juego.
- Crear reglas de vida, ataque, defensa y modificadores.
- Implementar la formula de combate definida en el enunciado.
- Implementar invariantes del dominio: celda valida, vida no negativa, celda sin multiples entidades principales y objeto no duplicado.
- Crear excepciones personalizadas para errores relevantes.

Resultado esperado:

- Modelo jugable en memoria.
- Clases no visuales listas para pruebas.

### Fase 7: Logica de turnos, movimiento y combate

Objetivo: implementar las reglas del juego sin depender todavia de JavaFX.

Tareas:

- Implementar gestion de turnos con cola propia.
- Implementar movimiento dentro de habitacion.
- Implementar calculo de casillas alcanzables con BFS.
- Implementar prohibicion de movimiento diagonal directo.
- Implementar cambio de habitacion mediante puertas.
- Implementar calculo de ruta minima entre habitaciones.
- Implementar distancia minima hasta puerta adecuada.
- Implementar compra de visualizacion del camino minimo.
- Implementar acciones de recoger objeto, usar objeto, equipar objeto y atacar.
- Implementar defensa automatica.
- Implementar movimiento basico de enemigos hacia el jugador.
- Implementar acciones sobre casillas adyacentes cuando corresponda.
- Implementar condicion de victoria.
- Implementar condiciones de derrota.
- Implementar registro de eventos en log.
- Crear pruebas JUnit de la logica del juego.

Resultado esperado:

- Juego funcional por logica interna.
- Pruebas de reglas principales.

### Fase 8: Persistencia JSON

Objetivo: cargar configuraciones y guardar/cargar partidas.

Tareas:

- Definir JSON de configuracion inicial.
- Definir JSON de estado de partida.
- Documentar los campos obligatorios de ambos JSON antes de programar.
- Crear JSON de ejemplo.
- Implementar carga de configuracion inicial.
- Implementar guardado de partida.
- Implementar carga de partida.
- Gestionar errores de lectura y escritura.
- Validar integridad de los datos cargados.
- Crear pruebas JUnit de persistencia.

Resultado esperado:

- Configuracion inicial cargable desde JSON.
- Guardado y carga de partida funcionales.
- JSON de ejemplo incluido en entrega.

### Fase 9: Interfaz JavaFX

Objetivo: crear la interfaz grafica conectada con la logica ya probada.

Tareas:

- Crear ventana principal JavaFX.
- Crear vista de matriz de habitacion con `GridPane`.
- Crear panel de estado del jugador.
- Crear panel de inventario visible constantemente.
- Crear panel de acciones disponibles.
- Crear panel de registro de eventos.
- Conectar clics o botones con acciones del juego.
- Mostrar casillas alcanzables iluminadas.
- Mostrar objetos, enemigos y elementos interactivos.
- Mostrar informacion de camino minimo.
- Mostrar log completo al final de la partida.
- Separar la interfaz de la logica mediante controlador o patron similar.

Resultado esperado:

- Juego usable desde JavaFX.
- Interfaz clara aunque sencilla.

### Fase 10: Integracion y pruebas finales

Objetivo: comprobar que todo funciona junto antes de preparar la entrega.

Tareas:

- Ejecutar todas las pruebas JUnit.
- Probar una partida completa ganando.
- Probar una partida completa perdiendo por turnos.
- Probar una partida completa perdiendo por vida.
- Probar carga de configuracion desde JSON.
- Probar guardado y carga de partida.
- Probar errores controlados.
- Revisar logs generados.
- Revisar que no se usan estructuras prohibidas.
- Revisar que el diario de IA contiene agentes, skills, prompts, resultados, modificaciones, critica y reajustes.
- Revisar que todos entienden el codigo.

Resultado esperado:

- Version candidata a entrega.
- Lista de errores corregidos o documentados.

### Fase 11: Documentacion y entrega

Objetivo: preparar todos los entregables obligatorios.

Tareas:

- Completar memoria en PDF.
- Incluir portada con todos los miembros del grupo.
- Incluir seccion "critica del proyecto".
- Incluir justificacion de estructuras y costes.
- Incluir capturas o bocetos de interfaz.
- Incluir UML.
- Incluir pruebas realizadas.
- Completar diario de IA.
- Preparar repositorio GitHub.
- Preparar ZIP.
- Grabar video explicativo con todos los miembros.
- Comprobar que el video muestra todas las funcionalidades desarrolladas.
- Comprobar que el video pesa menos de 100 MB o esta dividido en partes validas.
- Subir video a Blackboard.
- Si se usa YouTube, subir tambien el video a Blackboard.
- Preparar entrega para Metodologia de la Programacion.
- Preparar entrega para Estructuras de Datos.

Resultado esperado:

- Entrega completa y revisada.

## Orden recomendado

1. Resolver dudas criticas del alcance.
2. Cerrar diseno general y decisiones tecnicas.
3. Crear requisitos, casos de uso, invariantes y contratos.
4. Crear UML y bocetos de interfaz.
5. Crear el proyecto Java base y documentar comandos reales.
6. Implementar estructuras de datos propias.
7. Probar estructuras de datos.
8. Implementar modelo del juego.
9. Implementar movimiento, turnos, combate y condiciones de fin.
10. Implementar persistencia JSON.
11. Probar toda la logica sin interfaz.
12. Implementar interfaz JavaFX.
13. Integrar interfaz con logica.
14. Ejecutar pruebas finales.
15. Completar memoria, diario de IA, ZIP, GitHub y video.

## Dependencias entre tareas

- La implementacion no debe empezar hasta tener especificacion minima, decisiones de alcance y bocetos iniciales.
- Cualquier implementacion depende de que exista un proyecto Java base compilable.
- El diagrama de clases depende del modelo de dominio.
- Los contratos dependen de los casos de uso y de las estructuras elegidas.
- El modelo del juego depende de las estructuras propias basicas.
- El inventario depende de la lista propia.
- La gestion de turnos depende de la cola propia.
- El mapa de habitaciones depende del grafo propio.
- La habitacion depende de la matriz propia o estructura equivalente.
- El calculo de movimiento depende de la estructura de habitacion y de BFS.
- El cambio entre habitaciones depende del grafo y de las puertas.
- El combate depende de jugador, enemigos, objetos equipables y modificadores.
- La persistencia JSON depende de que el modelo este estable.
- La interfaz JavaFX depende de que la logica principal este probada.
- Las pruebas finales dependen de que modelo, persistencia e interfaz esten integrados.
- La memoria final depende de las decisiones, UML, pruebas, justificacion de estructuras y diario de IA.
- El video depende de tener una version funcional demostrable.

## Archivos o modulos que habra que tocar

La estructura exacta se definira al crear el proyecto Java, pero se recomienda separar los modulos asi:

- `src/main/java/.../app`: arranque de la aplicacion JavaFX.
- `src/main/java/.../model`: jugador, enemigo, objeto, habitacion, celda, partida y estado del juego.
- `src/main/java/.../structures`: lista, pila, cola, lista circular, arbol, grafo y matriz propia.
- `src/main/java/.../logic`: reglas de turnos, movimiento, combate, rutas y condiciones de victoria o derrota.
- `src/main/java/.../persistence`: carga de configuracion, guardado y carga de partida en JSON.
- `src/main/java/.../ui`: vistas JavaFX, controladores, paneles y renderizado de la matriz.
- `src/main/java/.../exceptions`: excepciones personalizadas.
- `src/main/java/.../log`: registro de eventos del juego.
- `src/test/java/.../structures`: pruebas JUnit de estructuras propias.
- `src/test/java/.../logic`: pruebas JUnit de reglas del juego.
- `src/test/java/.../persistence`: pruebas JUnit de JSON.
- `resources`: archivos JSON de ejemplo y recursos de interfaz si hicieran falta.
- `docs`: memoria, UML, bocetos y diario de IA si se decide organizar documentacion dentro del proyecto.

Archivos de documentacion previstos:

- `prd.md`: requisitos iniciales extraidos del enunciado.
- `plan.md`: plan de trabajo.
- `memoria.pdf`: memoria final.
- `diario-ia.md` o seccion equivalente en la memoria.
- `config-ejemplo.json`: configuracion inicial de ejemplo.
- `partida-ejemplo.json`: estado de partida de ejemplo.

## Reparto sugerido por miembros del grupo

### Miembro 1: Metodologia, modelo base y documentacion

- Coordinar requisitos y decisiones ambiguas.
- Redactar casos de uso.
- Definir invariantes y contratos.
- Crear o coordinar UML.
- Definir modelo de dominio.
- Implementar o revisar clases base del modelo acordadas con el grupo.
- Definir invariantes y excepciones del dominio.
- Crear pruebas unitarias basicas del modelo.
- Redactar partes principales de la memoria.
- Mantener diario de IA.
- Revisar coherencia entre diseno, codigo y memoria.

### Miembro 2: Estructuras de datos, algoritmos y proyecto base

- Implementar lista enlazada propia.
- Implementar pila propia.
- Implementar cola propia.
- Implementar lista circular propia.
- Implementar arbol propio.
- Implementar grafo propio.
- Implementar matriz propia.
- Crear el proyecto Java base junto con la configuracion de JUnit.
- Implementar BFS, calculo de casillas alcanzables y rutas entre habitaciones.
- Documentar costes de operaciones.
- Crear pruebas JUnit de estructuras.
- Revisar que no se usan estructuras prohibidas.

### Miembro 3: Logica de juego, persistencia e interfaz

- Implementar logica de turnos.
- Implementar combate, inventario, objetos y reglas de turno sobre el modelo acordado.
- Integrar el movimiento usando los algoritmos de Persona 2.
- Implementar condiciones de victoria y derrota.
- Implementar persistencia JSON.
- Crear JSON de ejemplo.
- Implementar interfaz JavaFX.
- Conectar interfaz con logica.
- Crear pruebas de logica y persistencia.

### Reparto del video y revision final

- Los tres miembros deben participar en el video.
- Persona 1 prepara la explicacion de metodologia, requisitos, UML, memoria y uso de IA.
- Persona 2 prepara la explicacion de estructuras propias, costes, BFS, rutas y restricciones de Estructuras de Datos.
- Persona 3 prepara la explicacion de partida, JavaFX, persistencia JSON y demostracion funcional.
- Los tres revisan la entrega final y comprueban que pueden explicar el proyecto completo.

Responsabilidades compartidas:

- Revisar codigo entre todos.
- Ejecutar pruebas completas.
- Corregir errores de integracion.
- Validar que todos pueden explicar el proyecto.
- Participar en el video final.

## Tareas que pueden hacerse en paralelo

- Aclarar dudas del enunciado y preparar bocetos de interfaz.
- Redactar casos de uso y preparar diagrama de clases inicial.
- Implementar estructuras basicas y redactar justificacion teorica.
- Crear pruebas de estructuras mientras se implementan estructuras.
- Preparar JSON de ejemplo provisional mientras se estabiliza el modelo.
- Disenar interfaz JavaFX en boceto mientras se implementa logica interna.
- Redactar diario de IA en paralelo al desarrollo.
- Preparar memoria por secciones mientras se completan las fases tecnicas.
- Crear UML de secuencia, estados y actividad mientras se cierran reglas del juego.
- Preparar guion del video mientras se hacen pruebas finales.

Tareas que no conviene hacer en paralelo sin coordinacion:

- Cambiar el modelo de dominio y la persistencia JSON al mismo tiempo.
- Cambiar estructuras propias mientras la logica ya depende de ellas.
- Modificar reglas de combate mientras se escriben pruebas finales.
- Cambiar interfaz y controlador si la API de la logica no esta estable.

## Checklist final de entrega

### Codigo

- El proyecto compila correctamente.
- La aplicacion JavaFX arranca.
- Se puede jugar una partida completa.
- Se puede ganar.
- Se puede perder por vida.
- Se puede perder por turnos.
- El jugador puede moverse dentro de habitaciones.
- El jugador puede cambiar de habitacion.
- El jugador puede recoger objetos.
- El jugador puede usar objetos.
- El jugador puede equipar objetos si aplica.
- El jugador puede combatir enemigos.
- Los enemigos actuan en su turno.
- El inventario se ve constantemente.
- El log registra las operaciones.
- El log se muestra al final.
- Se informa del camino o distancia minima requerida.
- Se puede guardar partida en JSON.
- Se puede cargar partida desde JSON.
- Se puede cargar configuracion inicial desde JSON.

### Estructuras de datos

- Hay lista propia.
- Hay pila propia.
- Hay cola propia.
- Hay lista circular propia.
- Hay arbol propio.
- Hay grafo propio.
- Hay matriz propia o estructura propia equivalente.
- El mapa usa grafo propio.
- Los turnos usan cola propia.
- El inventario usa estructura propia.
- Las estructuras tienen pruebas JUnit.
- Las estructuras tienen costes documentados.
- No se usan `ArrayList`, `HashMap`, `LinkedList` ni equivalentes para las partes evaluadas.

### Metodologia y documentacion

- Hay requisitos funcionales.
- Hay requisitos no funcionales.
- Hay casos de uso.
- Hay contratos o interfaces.
- Hay invariantes.
- Hay modelo de dominio.
- Hay diagrama de casos de uso.
- Hay diagrama de clases.
- Hay diagrama de secuencia.
- Hay diagrama de estados.
- Hay diagrama de actividad.
- Hay bocetos de interfaz.
- La memoria incluye portada con todos los miembros.
- La memoria incluye seccion "critica del proyecto".
- La memoria justifica decisiones ambiguas.
- La memoria justifica estructuras y costes.
- La memoria explica pruebas realizadas.
- El diario de IA esta completo.

### Pruebas

- Hay tests JUnit de estructuras.
- Hay tests JUnit de logica del juego.
- Hay tests JUnit de persistencia JSON.
- Se han probado errores de movimiento invalido.
- Se han probado errores de ataque invalido.
- Se han probado errores de lectura JSON.
- Se han probado errores de escritura JSON.
- Se han revisado logs.

### Entrega

- Repositorio GitHub preparado.
- ZIP preparado.
- JSON de ejemplo incluido.
- Memoria PDF generada.
- UML incluido.
- Bocetos incluidos.
- Pruebas incluidas.
- Diario de IA incluido.
- Video grabado con todos los miembros.
- Video preparado para Blackboard en fichero menor de 100 MB o dividido en partes validas.
- Video subido a Blackboard.
- Entrega de Metodologia de la Programacion preparada.
- Entrega de Estructuras de Datos preparada.
- Todos los miembros pueden explicar el proyecto.

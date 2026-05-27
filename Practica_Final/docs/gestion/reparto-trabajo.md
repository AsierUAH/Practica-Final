# Reparto de trabajo del grupo

Este reparto usa como referencia obligatoria `prd.md`, `plan.md`, `AGENTS.md` y `skills.md`.

Antes de trabajar, cada miembro debe leer esos cuatro archivos y no debe programar requisitos que no esten en `prd.md` o aprobados por el grupo.

## Persona 1: Metodologia, documentacion, modelo base y tests de dominio

### Tareas principales

- Coordinar decisiones ambiguas del `prd.md`.
- Redactar requisitos funcionales y no funcionales definitivos.
- Crear casos de uso con precondiciones y postcondiciones.
- Definir invariantes del sistema.
- Definir contratos e interfaces principales.
- Crear y mantener UML obligatorio.
- Definir el modelo de dominio junto con Persona 3.
- Implementar o revisar clases base del modelo acordadas por el grupo.
- Definir excepciones del dominio junto con Persona 3.
- Crear tests unitarios basicos del modelo: vida no negativa, posicion valida, celda valida e inventario basico.
- Redactar memoria tecnica y seccion "critica del proyecto".
- Mantener el diario de IA.
- Revisar coherencia entre requisitos, UML, codigo y memoria.

### Archivos que tocara

- `prd.md`, solo si el grupo autoriza cambios de requisitos.
- `plan.md`, solo para ajustar planificacion aprobada.
- `AGENTS.md`, solo si cambian normas reales del proyecto.
- `skills.md`, solo si se anade o corrige una skill aprobada.
- `docs/memoria.md` o `docs/memoria.pdf`.
- `docs/diario-ia.md`.
- `docs/uml/`.
- `docs/bocetos/`.
- `src/main/java/.../model`, solo para clases de dominio acordadas.
- `src/main/java/.../exceptions`, si define excepciones del modelo.
- `src/test/java/.../model`, para pruebas del modelo base.

### Entregables concretos

- Requisitos finales revisados.
- Casos de uso principales.
- Invariantes y contratos.
- UML obligatorio: casos de uso, clases, secuencia, estados y actividad.
- Bocetos de interfaz iniciales.
- Memoria tecnica completa o borrador avanzado.
- Diario de IA actualizado.
- Lista de decisiones ambiguas resueltas.
- Tests basicos del modelo.

### Que debe pedirle a Opencode exactamente

- "Usa `prd.md`, `plan.md`, `AGENTS.md` y `skills.md`. Aplica la skill de generar memoria tecnica para redactar la seccion de requisitos y casos de uso. No programes nada."
- "Revisa si estos casos de uso cumplen `prd.md` y marca requisitos cumplidos, parciales o pendientes. No modifiques codigo."
- "Genera un borrador de diagrama de clases coherente con el modelo de dominio y con las estructuras propias previstas. No anadas funcionalidades fuera del PRD."
- "Aplica la skill de documentar una clase o modulo para explicar el modulo `model` en la memoria."
- "Aplica la skill crear tests para validar invariantes del modelo: vida no negativa, posicion valida y celda sin multiples entidades principales."
- "Comprueba que la memoria incluye todos los entregables obligatorios del `prd.md` y el checklist del `plan.md`."

### Que no debe tocar

- No implementar estructuras de datos en `structures`.
- No cambiar algoritmos de movimiento, turnos o combate sin coordinarlo con Persona 2 y Persona 3.
- No modificar persistencia JSON sin coordinarlo con Persona 3.
- No cambiar la API publica de estructuras ya usadas.
- No modificar interfaz JavaFX salvo bocetos o documentacion.
- No aprobar ampliaciones opcionales sin consenso del grupo.

### Checklist individual

- Ha leido `prd.md`, `plan.md`, `AGENTS.md` y `skills.md`.
- Ha registrado en el diario cualquier uso de IA.
- Ha cerrado o listado las dudas ambiguas del PRD.
- Ha definido requisitos no funcionales.
- Ha creado casos de uso con precondiciones y postcondiciones.
- Ha definido invariantes.
- Ha definido contratos principales.
- Ha preparado UML obligatorio.
- Ha preparado bocetos de interfaz.
- Ha probado invariantes basicos del modelo.
- Ha revisado que la memoria tiene portada, critica del proyecto y justificacion de decisiones.

## Persona 2: Proyecto base, estructuras de datos, algoritmos y tests de estructuras

### Tareas principales

- Implementar lista enlazada propia.
- Crear el proyecto Java base con Maven o Gradle, JavaFX y JUnit, si el grupo le asigna esta fase.
- Implementar pila propia.
- Implementar cola propia.
- Implementar lista circular propia.
- Implementar arbol propio.
- Implementar grafo propio.
- Implementar matriz propia o estructura propia equivalente para habitaciones.
- Implementar BFS para casillas alcanzables.
- Implementar ruta minima entre habitaciones.
- Implementar Dijkstra solo si el grupo decide usar costes variables.
- Crear pruebas JUnit de estructuras.
- Documentar costes de operaciones.
- Revisar que no se usen `ArrayList`, `HashMap`, `LinkedList` ni equivalentes prohibidos.

### Archivos que tocara

- `src/main/java/.../structures`.
- `pom.xml` o `build.gradle`, solo para configurar el proyecto base y dependencias aprobadas.
- `src/main/java/.../app`, solo para arranque minimo inicial si se crea el proyecto base.
- `src/main/java/.../logic`, solo para algoritmos de rutas y movimiento acordados.
- `src/test/java/.../structures`.
- `src/test/java/.../logic`, solo para tests de algoritmos de rutas o BFS.
- `docs/memoria.md`, solo secciones de estructuras, costes y justificacion.
- `docs/diario-ia.md`, para registrar usos de IA propios.

### Entregables concretos

- Lista propia probada.
- Proyecto Java base compilable y comandos documentados, si asume esa fase.
- Pila propia probada.
- Cola propia probada.
- Lista circular propia probada.
- Arbol propio probado o justificado segun uso acordado.
- Grafo propio probado.
- Matriz propia probada.
- BFS probado.
- Ruta minima probada.
- Documentacion de costes.
- Informe de no uso de estructuras prohibidas.

### Que debe pedirle a Opencode exactamente

- "Usa `prd.md`, `plan.md`, `AGENTS.md` y `skills.md`. Aplica la skill implementar una estructura de datos para crear la cola propia con sus operaciones minimas y tests JUnit. No uses `ArrayList`, `HashMap` ni `LinkedList`."
- "Aplica la skill crear tests para la lista propia. Cubre estructura vacia, insercion, eliminacion, busqueda e indices invalidos."
- "Revisa el modulo `structures` y dime si hay uso de estructuras prohibidas o costes mal documentados. No modifiques codigo."
- "Documenta en la memoria la justificacion del grafo propio, matriz propia y cola de turnos, incluyendo costes de operaciones."
- "Implementa solo BFS para casillas alcanzables usando estructuras propias ya existentes. No cambies reglas de combate ni UI."
- "Aplica la skill crear proyecto base para configurar Maven o Gradle, JavaFX y JUnit. No implementes todavia reglas del juego."

### Que no debe tocar

- No modificar JavaFX ni controladores de interfaz.
- No cambiar reglas de combate, objetos o inventario sin coordinarlo con Persona 3.
- No cambiar el modelo de dominio sin coordinarlo con Persona 1.
- No modificar formato JSON sin coordinarlo con Persona 3.
- No usar estructuras Java prohibidas para ahorrar tiempo.
- No introducir dependencias externas.

### Checklist individual

- Ha leido `prd.md`, `plan.md`, `AGENTS.md` y `skills.md`.
- Ha registrado en el diario cualquier uso de IA.
- Todas las estructuras exigidas existen o estan justificadas segun decision del grupo.
- Las estructuras tienen API minima y clara.
- Las estructuras tienen tests JUnit.
- Los algoritmos BFS y rutas usan estructuras propias.
- No hay `ArrayList`, `HashMap`, `LinkedList` ni equivalentes prohibidos en partes evaluadas.
- Los costes estan documentados.
- Las estructuras compilan y pasan sus tests.

## Persona 3: Logica del juego, persistencia e interfaz JavaFX

### Tareas principales

- Integrar jugador, enemigos, objetos, inventario y partida definidos junto con Persona 1.
- Implementar reglas de turno.
- Integrar movimiento dentro de habitacion usando algoritmos de Persona 2.
- Implementar cambio entre habitaciones usando grafo propio.
- Implementar recogida, uso y equipamiento de objetos.
- Implementar combate, defensa automatica y modificadores.
- Implementar condiciones de victoria y derrota.
- Implementar log de eventos del juego.
- Implementar carga de configuracion inicial en JSON.
- Implementar guardado y carga de partida en JSON.
- Crear JSON de ejemplo.
- Implementar interfaz JavaFX y conectarla con la logica.
- Crear tests JUnit de logica y persistencia.

### Archivos que tocara

- `src/main/java/.../model`, coordinado con Persona 1.
- `src/main/java/.../logic`.
- `src/main/java/.../persistence`.
- `src/main/java/.../ui`.
- `src/main/java/.../app`.
- `src/main/java/.../exceptions`.
- `src/main/java/.../log`.
- `src/main/resources/config-ejemplo.json`.
- `src/main/resources/partida-ejemplo.json`.
- `src/test/java/.../logic`.
- `src/test/java/.../persistence`.
- `docs/memoria.md`, solo secciones de logica, persistencia e interfaz.
- `docs/diario-ia.md`, para registrar usos de IA propios.

### Entregables concretos

- Logica de juego funcional sin JavaFX.
- Gestion de turnos funcional.
- Movimiento, combate e inventario funcionales.
- Victoria y derrota funcionales.
- Log de eventos funcional.
- Persistencia JSON funcional.
- JSON de configuracion y partida de ejemplo.
- Interfaz JavaFX funcional.
- Tests de logica y persistencia.
- Demostracion preparada para el video.

### Que debe pedirle a Opencode exactamente

- "Usa `prd.md`, `plan.md`, `AGENTS.md` y `skills.md`. Implementa solo la logica de turnos usando la cola propia ya existente. No modifiques estructuras."
- "Aplica la skill crear tests para movimiento dentro de habitacion: destino valido, destino fuera de rango, movimiento diagonal prohibido y celda ocupada."
- "Implementa guardado y carga JSON para el estado de partida segun el formato acordado. No cambies el modelo sin avisar."
- "Crea una vista JavaFX sencilla con matriz, estado del jugador, inventario, acciones y log. Mantiene la logica fuera de la UI."
- "Aplica la skill comprobar requisitos del `prd.md` sobre persistencia e interfaz y dime que falta."

### Que no debe tocar

- No modificar estructuras propias salvo bug acordado con Persona 2.
- No usar `ArrayList`, `HashMap`, `LinkedList` ni equivalentes en logica evaluada.
- No cambiar requisitos, casos de uso o UML sin coordinarlo con Persona 1.
- No cambiar formato JSON despues de que haya tests sin avisar al grupo.
- No meter reglas de juego directamente en controladores JavaFX.
- No crear mecanicas opcionales no aprobadas.

### Checklist individual

- Ha leido `prd.md`, `plan.md`, `AGENTS.md` y `skills.md`.
- Ha registrado en el diario cualquier uso de IA.
- La logica principal funciona sin interfaz.
- El turno permite maximo un movimiento y una accion.
- El jugador mueve antes que enemigos.
- Movimiento diagonal directo esta prohibido.
- Cambio de habitacion funciona desde puertas.
- Combate aplica vida, ataque, defensa y modificadores.
- Victoria y derrotas estan implementadas.
- JSON de configuracion carga correctamente.
- Partida se guarda y carga correctamente.
- JavaFX muestra matriz, jugador, inventario, acciones y log.
- Tests de logica y persistencia pasan.

## Orden de integracion

1. Integrar decisiones de alcance de Persona 1.
2. Integrar estructura de carpetas y proyecto base.
3. Integrar estructuras propias de Persona 2.
4. Ejecutar tests de estructuras.
5. Integrar modelo base acordado por Persona 1 y Persona 3.
6. Integrar algoritmos de movimiento y rutas de Persona 2.
7. Integrar logica de turnos, combate e inventario de Persona 3.
8. Ejecutar tests de logica.
9. Integrar persistencia JSON de Persona 3.
10. Ejecutar tests de persistencia.
11. Integrar interfaz JavaFX.
12. Ejecutar pruebas manuales de partida completa.
13. Integrar memoria, UML, bocetos y diario de IA.
14. Aplicar skill de revisar codigo sobre los modulos principales.
15. Aplicar skill de preparar entrega final.

## Reparto del video final

- Persona 1 explica metodologia, requisitos, UML, memoria, bocetos y uso de IA.
- Persona 2 explica estructuras propias, costes, restricciones, BFS, rutas y pruebas de estructuras.
- Persona 3 explica logica de juego, persistencia JSON, JavaFX y demostracion de partida.
- Los tres deben aparecer en el video y poder responder preguntas generales del proyecto.
- Antes de grabar, se comprueba que el video final pueda subirse a Blackboard en ficheros de menos de 100 MB o en partes validas.

## Tareas comunes

- Acordar decisiones ambiguas antes de programar.
- Revisar cambios importantes de los demas.
- Mantener actualizado el diario de IA.
- Ejecutar pruebas antes de integrar.
- Comprobar que no se usan estructuras prohibidas.
- Mantener coherencia entre memoria, UML, codigo y JSON.
- Preparar y ensayar el video final.
- Asegurarse de que todos entienden el proyecto completo.

## Riesgos de conflicto

- Persona 1 y Persona 3 pueden pisarse en `model` si no acuerdan primero clases, atributos e interfaces.
- Persona 2 y Persona 3 pueden pisarse en `logic` si ambos modifican movimiento o rutas.
- Persona 3 puede romper tests de Persona 2 si cambia la API de estructuras.
- Persona 1 puede dejar UML desactualizado si Persona 3 cambia el modelo sin avisar.
- Persistencia JSON puede romperse si cambia el modelo de dominio sin actualizar adaptadores y ejemplos.
- La interfaz puede mezclar logica si se programa antes de estabilizar `logic`.
- La memoria puede contradecir el codigo si se redacta sin revisar la implementacion final.
- El uso accidental de estructuras prohibidas puede invalidar la parte de Estructuras de Datos.

## Normas para evitar pisarse

- Cada persona trabaja principalmente en sus modulos asignados.
- Los cambios en APIs compartidas se anuncian antes de hacerse.
- Nadie cambia requisitos sin autorizacion del grupo.
- Nadie cambia estructuras propias sin avisar a Persona 2.
- Nadie cambia formato JSON sin avisar a Persona 3.
- Nadie cambia UML definitivo sin avisar a Persona 1.
- Antes de integrar, se ejecutan los tests relacionados.
- Si hay conflicto funcional, se para y se decide en grupo.

## Checklist final antes de entregar

### Codigo y ejecucion

- El proyecto compila.
- La aplicacion JavaFX arranca.
- Se puede jugar una partida completa.
- Se puede ganar.
- Se puede perder por vida.
- Se puede perder por turnos.
- El inventario se ve constantemente.
- El log registra operaciones.
- El log se muestra al final.
- La distancia o camino minimo se informa al jugador.

### Estructuras de Datos

- Lista propia implementada y probada.
- Pila propia implementada y probada.
- Cola propia implementada y probada.
- Lista circular propia implementada y probada.
- Arbol propio implementado y probado o uso justificado.
- Grafo propio implementado y probado.
- Matriz propia implementada y probada.
- No hay estructuras prohibidas en partes evaluadas.
- Costes documentados.

### Persistencia y pruebas

- Configuracion inicial carga desde JSON.
- Partida se guarda en JSON.
- Partida se carga desde JSON.
- JSON de ejemplo incluido.
- Tests JUnit de estructuras pasan.
- Tests JUnit de logica pasan.
- Tests JUnit de persistencia pasan.
- Errores de movimiento, ataque y JSON estan controlados.

### Documentacion y entrega

- Memoria PDF generada.
- Portada con todos los miembros.
- Seccion "critica del proyecto" incluida.
- UML obligatorio incluido.
- Bocetos incluidos.
- Diario de IA completo.
- Justificacion de estructuras y costes incluida.
- Repositorio GitHub preparado.
- ZIP preparado.
- Video grabado con todos los miembros.
- Video preparado para Blackboard en fichero menor de 100 MB o dividido en partes validas.
- Video subido a Blackboard.
- Entrega de Metodologia preparada.
- Entrega de Estructuras de Datos preparada.
- Todos los miembros pueden explicar su parte y el flujo general del juego.

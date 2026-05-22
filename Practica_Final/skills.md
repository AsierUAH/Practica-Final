# Skills reutilizables del proyecto

Este archivo define procedimientos reutilizables para que Opencode o cualquier agente de IA trabaje de forma consistente en este proyecto.

Antes de usar cualquier skill, el agente debe leer `prd.md`, `plan.md`, `AGENTS.md` y `skills.md`.

## Skill: Crear proyecto base

### Nombre

Crear proyecto JavaFX base.

### Cuando usarla

Usar esta skill al iniciar el proyecto Java real, antes de implementar estructuras, modelo, logica, persistencia o interfaz.

### Entrada esperada

- Herramienta elegida: Maven o Gradle.
- Version de Java prevista.
- Version de JavaFX prevista.
- Version de JUnit prevista.
- Paquete base acordado.

### Pasos que debe seguir Opencode

1. Leer `prd.md`, `plan.md`, `AGENTS.md` y `skills.md`.
2. Confirmar que la fase de proyecto base esta aprobada.
3. Crear solo la estructura minima de carpetas.
4. Configurar JavaFX y JUnit sin dependencias innecesarias.
5. Crear una aplicacion minima compilable.
6. Crear una prueba JUnit minima si procede.
7. Ejecutar compilacion y pruebas.
8. Actualizar `AGENTS.md` con comandos reales.
9. No implementar reglas del juego en esta fase.

### Salida esperada

- Proyecto Java base compilable.
- Comandos reales documentados.
- Estructura preparada para `model`, `structures`, `logic`, `persistence`, `ui`, `exceptions` y `log`.

## Skill: Especificar requisitos y casos de uso

### Nombre

Redactar especificacion previa.

### Cuando usarla

Usar esta skill antes de programar, o cuando haya que completar requisitos, casos de uso, contratos, invariantes o decisiones abiertas.

### Entrada esperada

- Parte de la especificacion a redactar.
- Decisiones ya aprobadas por el grupo.
- Dudas pendientes.

### Pasos que debe seguir Opencode

1. Leer `prd.md`, `plan.md`, `AGENTS.md` y `skills.md`.
2. Consultar el enunciado original si falta informacion.
3. Separar requisitos obligatorios, opcionales y decisiones del grupo.
4. No inventar requisitos nuevos.
5. Redactar casos de uso con precondiciones y postcondiciones.
6. Redactar invariantes del dominio.
7. Definir contratos o interfaces principales.
8. Marcar cualquier punto pendiente de confirmar con el profesor.

### Salida esperada

- Texto de especificacion claro.
- Casos de uso, contratos e invariantes coherentes.
- Lista de dudas pendientes si las hay.

## Skill: Crear o revisar UML

### Nombre

Crear o revisar diagramas UML.

### Cuando usarla

Usar esta skill para preparar o revisar diagramas de casos de uso, clases, secuencia, estados y actividad.

### Entrada esperada

- Tipo de diagrama.
- Modelo de dominio o flujo que debe representar.
- Decisiones aprobadas.

### Pasos que debe seguir Opencode

1. Leer `prd.md`, `plan.md`, `AGENTS.md` y `skills.md`.
2. Confirmar que el diagrama es obligatorio o util para la memoria.
3. Revisar modelo, casos de uso o flujo real antes de proponer el diagrama.
4. Mantener coherencia con clases, estructuras y paquetes previstos.
5. No anadir clases o relaciones que no existan en el diseno aprobado.
6. Indicar si el UML queda pendiente de actualizar tras cambios de codigo.

### Salida esperada

- Diagrama o descripcion lista para pasarse a una herramienta UML.
- Lista de elementos representados.
- Aviso de incoherencias si existen.

## Skill: Disenar bocetos de interfaz

### Nombre

Disenar interfaz previa.

### Cuando usarla

Usar esta skill antes de implementar JavaFX o cuando se revise el flujo visual del juego.

### Entrada esperada

- Pantallas necesarias.
- Informacion que debe mostrarse.
- Acciones disponibles para el usuario.

### Pasos que debe seguir Opencode

1. Leer `prd.md`, `plan.md`, `AGENTS.md` y `skills.md`.
2. Identificar elementos obligatorios de JavaFX.
3. Proponer distribucion de matriz, estado, inventario, acciones y log.
4. Mantener la interfaz sencilla y clara.
5. No programar JavaFX si solo se piden bocetos.
6. Indicar que la logica debe quedar fuera de la interfaz.

### Salida esperada

- Boceto textual o guia para boceto visual.
- Lista de paneles y controles.
- Riesgos de usabilidad o separacion logica/UI.

## Skill: Implementar una estructura de datos

### Nombre

Implementar una estructura de datos propia.

### Cuando usarla

Usar esta skill cuando haya que implementar, completar o corregir una estructura propia exigida por Estructuras de Datos: lista, pila, cola, lista circular, arbol, grafo, matriz propia u otra estructura aprobada.

### Entrada esperada

- Nombre de la estructura.
- Operaciones requeridas.
- Uso previsto dentro del juego.
- Restricciones concretas.
- Ubicacion esperada del codigo.
- Criterios de prueba.

### Pasos que debe seguir Opencode

1. Revisar `prd.md` y confirmar que la estructura esta permitida o exigida.
2. Revisar `plan.md` para ubicar la tarea en la fase correcta.
3. Comprobar que no se van a usar `ArrayList`, `HashMap`, `LinkedList` ni equivalentes prohibidos.
4. Identificar el modulo afectado, normalmente `structures`.
5. Definir la API minima necesaria antes de implementar.
6. Implementar solo las operaciones necesarias para el proyecto.
7. Mantener atributos privados y nombres claros.
8. Gestionar casos borde como estructura vacia, indices invalidos o elementos nulos si aplica.
9. Documentar el coste de las operaciones principales.
10. Crear o actualizar tests JUnit de la estructura.
11. Ejecutar las pruebas relacionadas.
12. Informar de cambios realizados y verificacion.

### Salida esperada

- Estructura implementada en el modulo correcto.
- Tests JUnit relacionados.
- Costes de operaciones documentados.
- Confirmacion de que no se han usado estructuras prohibidas.

## Skill: Crear tests

### Nombre

Crear pruebas JUnit.

### Cuando usarla

Usar esta skill cuando haya que probar estructuras propias, logica del juego, persistencia JSON, excepciones o reglas no visuales.

### Entrada esperada

- Clase o modulo a probar.
- Comportamientos esperados.
- Casos limite.
- Errores que deben controlarse.
- Comando de pruebas disponible.

### Pasos que debe seguir Opencode

1. Revisar requisitos relacionados en `prd.md`.
2. Revisar dependencias de la tarea en `plan.md`.
3. Identificar si la prueba pertenece a `structures`, `logic` o `persistence`.
4. Crear pruebas pequenas, claras e independientes.
5. Cubrir casos normales.
6. Cubrir casos limite.
7. Cubrir errores y excepciones esperadas.
8. Evitar pruebas dependientes de JavaFX salvo que sea estrictamente necesario.
9. Ejecutar las pruebas relacionadas.
10. Corregir fallos si proceden de la implementacion trabajada.
11. No ocultar fallos ni eliminar pruebas para que pasen.

### Salida esperada

- Tests JUnit creados o actualizados.
- Resultado de ejecucion de pruebas.
- Lista breve de casos cubiertos.
- Aviso claro si alguna prueba queda pendiente o bloqueada.

## Skill: Revisar codigo

### Nombre

Revisar codigo del proyecto.

### Cuando usarla

Usar esta skill cuando se pida una revision, antes de integrar cambios importantes, antes de entrega final o cuando aparezcan fallos dificiles de localizar.

### Entrada esperada

- Archivos o modulos a revisar.
- Objetivo de la revision.
- Requisitos afectados.
- Cambios recientes si se conocen.

### Pasos que debe seguir Opencode

1. Leer `prd.md`, `plan.md` y `AGENTS.md`.
2. Identificar los requisitos que afectan al codigo revisado.
3. Revisar errores funcionales primero.
4. Revisar incumplimientos de estructuras prohibidas.
5. Revisar separacion entre modelo, logica, persistencia e interfaz.
6. Revisar gestion de excepciones.
7. Revisar consistencia con tests existentes.
8. Revisar nombres, encapsulamiento, cohesion y acoplamiento.
9. No modificar codigo salvo que el usuario pida explicitamente corregirlo.
10. Presentar hallazgos ordenados por gravedad con archivo y linea cuando sea posible.

### Salida esperada

- Lista de problemas encontrados ordenados por gravedad.
- Referencias a archivos y lineas.
- Riesgos o dudas abiertas.
- Confirmacion explicita si no se encuentran problemas relevantes.

## Skill: Documentar una clase o modulo

### Nombre

Documentar clase o modulo.

### Cuando usarla

Usar esta skill cuando se cree o modifique una clase importante, una estructura propia, una regla de negocio, una API publica o un modulo que deba explicarse en la memoria.

### Entrada esperada

- Clase o modulo a documentar.
- Responsabilidad principal.
- Metodos publicos relevantes.
- Invariantes o contratos.
- Costes de operaciones si es una estructura.

### Pasos que debe seguir Opencode

1. Confirmar el papel de la clase o modulo dentro de `prd.md` y `plan.md`.
2. Revisar el codigo real antes de documentar.
3. Documentar responsabilidad y limites del modulo.
4. Documentar invariantes si aplica.
5. Documentar precondiciones y postcondiciones cuando sean relevantes.
6. Documentar costes si se trata de estructura de datos.
7. Evitar comentarios obvios o repetitivos.
8. Actualizar memoria, UML o diario de IA si el cambio afecta al diseno.
9. Verificar que la documentacion no promete funcionalidades no implementadas.

### Salida esperada

- Documentacion clara y consistente con el codigo.
- Invariantes, contratos o costes cuando correspondan.
- Aviso si hay diferencias entre codigo, UML, memoria o requisitos.

## Skill: Generar memoria tecnica

### Nombre

Generar o actualizar memoria tecnica.

### Cuando usarla

Usar esta skill para redactar, completar o revisar la memoria del proyecto, especialmente al cerrar fases, justificar decisiones o preparar entrega.

### Entrada esperada

- Seccion de la memoria que hay que generar.
- Decisiones ya aprobadas por el grupo.
- Codigo o modulos relacionados.
- Diagramas disponibles.
- Resultados de pruebas.

### Pasos que debe seguir Opencode

1. Revisar `prd.md` para asegurar que se cubren los entregables obligatorios.
2. Revisar `plan.md` para ubicar la seccion dentro de la fase adecuada.
3. No inventar decisiones que no esten aprobadas.
4. Explicar el diseno orientado a objetos usado.
5. Explicar estructuras propias y su justificacion.
6. Incluir costes de operaciones cuando corresponda.
7. Explicar persistencia JSON si la seccion lo requiere.
8. Explicar pruebas realizadas y resultado.
9. Incluir limitaciones reales del proyecto.
10. Incluir o respetar la seccion obligatoria "critica del proyecto".
11. Mantener el texto claro y defendible oralmente por todos los miembros.

### Salida esperada

- Seccion de memoria redactada o actualizada.
- Texto coherente con requisitos, codigo y pruebas.
- Lista de informacion pendiente si falta algun dato.

## Skill: Comprobar requisitos del prd.md

### Nombre

Comprobar cumplimiento de requisitos.

### Cuando usarla

Usar esta skill antes de cerrar una fase, antes de una entrega parcial, antes de la entrega final o cuando se sospeche que una tarea se sale del alcance.

### Entrada esperada

- Funcionalidad, modulo o documento a comprobar.
- Estado actual del trabajo.
- Evidencias disponibles, como codigo, tests, UML o documentacion.

### Pasos que debe seguir Opencode

1. Leer `prd.md` completo.
2. Identificar requisitos funcionales relacionados.
3. Identificar requisitos tecnicos relacionados.
4. Identificar restricciones del profesor relacionadas.
5. Comparar el estado actual con cada requisito aplicable.
6. Marcar cada requisito como cumplido, parcial, pendiente o bloqueado.
7. Detectar requisitos inventados o ampliaciones no aprobadas.
8. Detectar uso de estructuras prohibidas si aplica.
9. Proponer acciones minimas para corregir incumplimientos.
10. No modificar requisitos sin autorizacion del usuario.

### Salida esperada

- Tabla o lista de cumplimiento.
- Incumplimientos detectados.
- Riesgos para la evaluacion.
- Acciones recomendadas ordenadas por prioridad.

## Skill: Preparar entrega final

### Nombre

Preparar entrega final.

### Cuando usarla

Usar esta skill cuando el proyecto este integrado y haya que preparar ZIP, repositorio, memoria, video, pruebas y entregas separadas.

### Entrada esperada

- Estado del repositorio o carpeta del proyecto.
- Comandos reales para compilar, ejecutar y probar.
- Lista de entregables disponibles.
- Resultado de pruebas.

### Pasos que debe seguir Opencode

1. Leer checklist final de `plan.md`.
2. Verificar que el proyecto compila.
3. Ejecutar todas las pruebas disponibles.
4. Confirmar que la aplicacion arranca.
5. Revisar que existen JSON de ejemplo.
6. Revisar que existe memoria en PDF.
7. Revisar que la memoria tiene portada con todos los miembros.
8. Revisar que la memoria incluye "critica del proyecto".
9. Revisar que UML, bocetos y diario de IA estan incluidos.
10. Revisar que no se usan estructuras prohibidas.
11. Verificar que el video cubre todas las funcionalidades y aparecen todos los miembros.
12. Preparar lista final de archivos para GitHub, ZIP y Blackboard.
13. No hacer commits ni subir nada salvo peticion explicita del usuario.

### Salida esperada

- Checklist final con estado de cada elemento.
- Problemas pendientes antes de entregar.
- Comandos ejecutados y resultados.
- Lista de archivos que deben incluirse en la entrega.

## Skill: Implementar persistencia JSON

### Nombre

Implementar carga y guardado JSON.

### Cuando usarla

Usar esta skill para cargar configuracion inicial, guardar partida, cargar partida o revisar el formato JSON.

### Entrada esperada

- Formato JSON aprobado.
- Clases del modelo afectadas.
- Libreria JSON permitida.
- Casos de prueba esperados.

### Pasos que debe seguir Opencode

1. Leer `prd.md`, `plan.md`, `AGENTS.md` y `skills.md`.
2. Confirmar que el formato JSON esta aprobado antes de programar.
3. Separar configuracion inicial y estado de partida.
4. Validar campos obligatorios y errores de formato.
5. Mantener la persistencia separada de modelo, logica e interfaz.
6. Gestionar errores de lectura y escritura con excepciones claras.
7. Crear o actualizar JSON de ejemplo.
8. Crear o actualizar tests JUnit de persistencia.
9. Ejecutar pruebas relacionadas.

### Salida esperada

- Carga de configuracion inicial.
- Guardado y carga de partida.
- JSON de ejemplo consistente.
- Tests de persistencia y errores controlados.

## Skill: Implementar interfaz JavaFX

### Nombre

Implementar interfaz JavaFX.

### Cuando usarla

Usar esta skill cuando la logica principal ya este probada y haya que crear o modificar la interfaz grafica.

### Entrada esperada

- Bocetos aprobados.
- API de logica disponible.
- Acciones del jugador que debe exponer la interfaz.

### Pasos que debe seguir Opencode

1. Leer `prd.md`, `plan.md`, `AGENTS.md` y `skills.md`.
2. Revisar bocetos y requisitos de interfaz.
3. Crear vista de matriz con JavaFX, preferiblemente `GridPane`.
4. Mostrar jugador, enemigos, objetos y elementos interactivos.
5. Mostrar estado del jugador, inventario constante, acciones y log.
6. Conectar eventos de usuario con la logica mediante controlador o capa equivalente.
7. No introducir reglas de juego dentro de la interfaz.
8. Comprobar manualmente que la aplicacion arranca.

### Salida esperada

- Interfaz JavaFX usable.
- Separacion clara entre UI y logica.
- Lista de pruebas manuales realizadas.

## Skill: Registrar uso de IA

### Nombre

Actualizar diario de IA.

### Cuando usarla

Usar esta skill cada vez que se use Opencode u otra herramienta de IA para analizar, programar, documentar, revisar o preparar entregables.

### Entrada esperada

- Tarea realizada.
- Herramienta o agente usado.
- Skill usada, si procede.
- Resultado obtenido.
- Cambios aceptados y rechazados.

### Pasos que debe seguir Opencode

1. Leer normas de diario de `AGENTS.md`.
2. Localizar `docs/diario-ia.md` o indicar que debe crearse.
3. Registrar prompt o resumen de instruccion.
4. Registrar resultado y modificaciones aplicadas.
5. Registrar problemas, critica y reajustes.
6. No falsear revision humana ni resultados.

### Salida esperada

- Entrada de diario lista o actualizada.
- Aviso si falta informacion que debe completar una persona del grupo.

## Skill: Revisar contra enunciado original

### Nombre

Comprobar documentos contra el enunciado.

### Cuando usarla

Usar esta skill al revisar `prd.md`, `plan.md`, `AGENTS.md`, `skills.md`, memoria o documentos de entrega contra el PDF original.

### Entrada esperada

- Documentos a revisar.
- Enunciado original localizado en la carpeta.
- Objetivo de la revision.

### Pasos que debe seguir Opencode

1. Leer el enunciado original.
2. Leer `prd.md`, `plan.md`, `AGENTS.md` y `skills.md`.
3. Comparar requisitos, entregables, restricciones y criterios de evaluacion.
4. Detectar omisiones, contradicciones y requisitos inventados.
5. Separar fallos criticos de mejoras recomendables.
6. No generar entregables finales si hay fallos pendientes que corregir.

### Salida esperada

- Informe claro de cumplimiento.
- Correcciones necesarias por archivo.
- Confirmacion de si se puede generar el siguiente entregable.

## Skill: Preparar organizacion del grupo

### Nombre

Preparar documento de organizacion.

### Cuando usarla

Usar esta skill para crear o actualizar un documento de reparto, fases, responsabilidades, integracion y checklist para los tres miembros.

### Entrada esperada

- Requisitos revisados.
- Plan de fases aprobado.
- Numero de miembros del grupo.
- Reparto previsto.

### Pasos que debe seguir Opencode

1. Leer `prd.md`, `plan.md`, `AGENTS.md`, `skills.md` y el reparto existente si lo hay.
2. Verificar que no hay fallos pendientes importantes.
3. Equilibrar carga entre tres personas.
4. Indicar archivos que toca cada persona.
5. Indicar que debe pedir cada persona a Opencode.
6. Indicar que no debe tocar cada persona para evitar conflictos.
7. Definir orden de integracion y checklist.
8. No crear PDF final si la revision previa detecta fallos sin corregir.

### Salida esperada

- Documento de organizacion claro.
- Reparto equilibrado.
- Checklist individual y comun.

## Skill: Integrar el trabajo de los tres miembros

### Nombre

Integrar trabajo del grupo.

### Cuando usarla

Usar esta skill cuando se junten cambios de metodologia, estructuras, logica, persistencia o interfaz realizados por distintos miembros del grupo.

### Entrada esperada

- Cambios aportados por cada miembro.
- Archivos modificados.
- Conflictos conocidos.
- Tests disponibles.
- Decisiones de diseno aprobadas.

### Pasos que debe seguir Opencode

1. Revisar responsabilidades del grupo en `plan.md`.
2. Identificar que parte corresponde a cada miembro.
3. Revisar dependencias entre cambios.
4. Comprobar que los cambios no duplican clases o responsabilidades.
5. Comprobar que todos usan las mismas estructuras propias.
6. Comprobar que el modelo coincide con persistencia e interfaz.
7. Resolver conflictos de forma minima y documentada.
8. Mantener separacion entre modelo, logica, persistencia e interfaz.
9. Ejecutar pruebas relacionadas.
10. Actualizar documentacion si cambia alguna decision.
11. Registrar en el diario de IA si la integracion uso asistencia de IA.
12. Informar de cambios integrados, conflictos resueltos y pendientes.

### Salida esperada

- Trabajo integrado sin duplicidades evidentes.
- Conflictos resueltos o identificados.
- Tests ejecutados y resultado.
- Documentacion actualizada si procede.
- Lista de tareas pendientes por miembro si queda trabajo abierto.

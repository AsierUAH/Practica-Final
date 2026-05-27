# AGENTS.md - Instrucciones para agentes de IA

## Contexto del trabajo

Este proyecto corresponde a una practica final conjunta de las asignaturas Metodologias de la Programacion y Estructuras de Datos.

El objetivo es desarrollar un juego por turnos en Java con interfaz grafica JavaFX. El jugador se mueve por habitaciones conectadas mediante un grafo. Cada habitacion contiene una matriz bidimensional con celdas que pueden incluir objetos, enemigos, puertas, trampas, salidas u otros elementos interactivos.

El proyecto debe demostrar diseno orientado a objetos, especificacion previa, uso de estructuras de datos propias, persistencia en JSON, pruebas y documentacion metodologica.

Antes de hacer cualquier cambio, todo agente debe leer:

- `prd.md`
- `plan.md`
- `AGENTS.md`
- `skills.md`

Estos documentos son la referencia principal del proyecto.

Si aparece una duda sobre requisitos, restricciones o entregables, el agente debe consultar tambien el enunciado original en `docs/enunciado/Práctica Final 2026 MP EEDD.pdf` o el archivo equivalente que este en la carpeta del proyecto.

## Asignaturas implicadas

### Metodologias de la Programacion

El proyecto debe cuidar especialmente:

- Requisitos funcionales y no funcionales.
- Casos de uso.
- Modelo de dominio.
- Contratos e interfaces.
- Invariantes.
- UML obligatorio.
- Diseno previo de interfaz.
- Gestion de excepciones.
- Persistencia JSON.
- Separacion entre logica e interfaz.
- Pruebas.
- Diario de uso de IA.
- Memoria final con critica del proyecto.

### Estructuras de Datos

El proyecto debe cuidar especialmente:

- Implementacion propia de listas.
- Implementacion propia de pilas.
- Implementacion propia de colas.
- Implementacion propia de listas circulares.
- Implementacion propia de arboles.
- Implementacion propia de grafos.
- Uso de grafo propio para el mapa de habitaciones.
- Uso de estructura propia para la matriz de habitaciones.
- Justificacion de estructuras.
- Analisis de costes de operaciones.
- Pruebas JUnit de estructuras.

No se deben usar `ArrayList`, `HashMap`, `LinkedList` ni estructuras estandar equivalentes para las partes evaluadas de Estructuras de Datos.

## Normas de estilo de codigo

- Lenguaje principal: Java.
- Interfaz grafica: JavaFX.
- Persistencia: JSON.
- Pruebas: JUnit para clases no visuales.
- Usar nombres claros y descriptivos.
- Mantener atributos privados y exponer comportamiento mediante metodos publicos o protegidos cuando proceda.
- Favorecer clases cohesionadas y con responsabilidad unica.
- Evitar clases grandes que mezclen modelo, interfaz, persistencia y logica.
- Separar claramente modelo, logica, persistencia, estructuras propias e interfaz.
- Evitar duplicacion de codigo.
- No introducir complejidad innecesaria.
- No anadir ampliaciones opcionales si no estan planificadas o aprobadas.
- Escribir comentarios solo cuando expliquen una decision o una logica que no sea evidente.
- Mantener el codigo en ASCII salvo que un archivo ya use caracteres especiales o exista una razon clara.

## Normas para no romper el proyecto

- No trabajar fuera de la carpeta del proyecto.
- No crear archivos fuera de esta carpeta.
- No borrar archivos existentes sin autorizacion explicita.
- No renombrar archivos o carpetas sin justificarlo y actualizar referencias.
- No modificar requisitos de `prd.md` sin autorizacion del usuario.
- No contradecir el orden ni las fases de `plan.md` sin justificarlo.
- No programar funcionalidades que no esten en `prd.md`, `plan.md` o hayan sido aprobadas por el usuario.
- No sustituir estructuras propias por estructuras de Java prohibidas.
- No usar estructuras estandar equivalentes de Java en partes evaluadas de Estructuras de Datos, ni directa ni indirectamente para ahorrar tiempo.
- No mezclar logica de juego directamente dentro de la interfaz JavaFX.
- No introducir dependencias externas innecesarias.
- No cambiar la API publica de una clase usada por otros modulos sin revisar dependencias.
- No ocultar errores con `catch` vacios.
- No dejar pruebas rotas.
- No dejar codigo sin compilar.
- No hacer commits salvo que el usuario lo pida explicitamente.
- No crear PDFs, ZIPs ni entregables finales si una revision previa ha detectado fallos pendientes que el usuario haya pedido corregir primero.

Si aparece una duda funcional importante, parar y preguntar antes de inventar una solucion.

## Comandos para compilar, ejecutar y probar

### Maven (herramienta elegida)

Compilar:

```bash
mvn compile
```

Ejecutar pruebas:

```bash
mvn test
```

Ejecutar aplicacion JavaFX:

```bash
mvn javafx:run
```

Generar paquete:

```bash
mvn package
```

Limpiar y compilar:

```bash
mvn clean compile
```

## Estructura de carpetas recomendada

La estructura final puede ajustarse si el grupo lo decide, pero debe mantener separacion clara de responsabilidades.

```text
Practica_Final/
  AGENTS.md
  prd.md
  plan.md
  README.md
  pom.xml o build.gradle
  src/
    main/
      java/
        .../
          app/
          model/
          structures/
          logic/
          persistence/
          ui/
          exceptions/
          log/
      resources/
        config-ejemplo.json
        partida-ejemplo.json
    test/
      java/
        .../
          structures/
          logic/
          persistence/
  docs/
    memoria.md o memoria.pdf
    diario-ia.md
    uml/
    bocetos/
```

Modulos previstos:

- `app`: arranque de la aplicacion.
- `model`: clases del dominio, como jugador, enemigo, objeto, habitacion, celda y partida.
- `structures`: estructuras de datos propias.
- `logic`: reglas de turnos, movimiento, combate, rutas y condiciones de fin.
- `persistence`: lectura y escritura JSON.
- `ui`: interfaz JavaFX y controladores.
- `exceptions`: excepciones personalizadas.
- `log`: registro de eventos del juego.

## Como documentar cambios

Cada cambio relevante debe quedar documentado en el lugar adecuado.

Documentacion tecnica:

- Actualizar comentarios o documentacion si cambia el comportamiento publico de una clase.
- Actualizar la memoria si cambia una decision de diseno.
- Actualizar UML si cambia el modelo de clases o flujo principal.
- Actualizar JSON de ejemplo si cambia el formato de persistencia.
- Actualizar tests si cambia una regla de negocio.

Decisiones abiertas:

- Si se cierra una duda del `prd.md`, documentarla en la memoria o documento de decisiones acordado.
- Si la decision cambia alcance, requisitos o reparto, actualizar tambien el documento correspondiente tras aprobacion del grupo.
- No presentar una decision abierta como requisito del profesor.

Diario de IA:

- Registrar agente o herramienta usada.
- Registrar skill usada, si procede.
- Registrar prompt o instruccion principal.
- Registrar resultado obtenido.
- Registrar modificaciones aplicadas manualmente.
- Registrar problemas detectados.
- Registrar critica o evaluacion del resultado.
- Registrar reajustes o correcciones aplicadas despues de revisar la salida de IA.
- Usar `docs/diario-ia.md` cuando exista; si todavia no existe, indicar al usuario que debe crearse antes de empezar desarrollo con IA.

Formato recomendado para entradas del diario:

```markdown
## Fecha - Tarea

- Agente/herramienta:
- Objetivo:
- Prompt/resumen de instruccion:
- Resultado:
- Cambios aceptados:
- Cambios rechazados:
- Revision humana:
- Observaciones:
```

## Como trabajar por tareas pequenas

- Leer `prd.md` y `plan.md` antes de empezar.
- Leer tambien `AGENTS.md` y `skills.md` antes de empezar.
- Elegir una unica tarea concreta del plan.
- Identificar archivos afectados antes de editar.
- Hacer el cambio minimo que resuelva la tarea.
- Ejecutar pruebas relacionadas.
- Revisar que no se han introducido estructuras prohibidas.
- Documentar la decision si afecta al diseno.
- Informar claramente de que se ha cambiado y como se ha verificado.

Ejemplos de tareas pequenas correctas:

- Implementar solo la cola propia y sus pruebas.
- Definir solo el modelo de `Jugador` y sus invariantes.
- Crear solo el JSON de configuracion inicial de ejemplo.
- Implementar solo el calculo de casillas alcanzables.
- Crear solo la vista inicial de la matriz sin reglas de combate.

Ejemplos de tareas demasiado grandes:

- Implementar todo el juego completo en una sola pasada.
- Crear modelo, interfaz, persistencia y pruebas a la vez.
- Cambiar toda la arquitectura sin necesidad.
- Anadir mecanicas opcionales no aprobadas.

## Obligacion de seguir `prd.md` y `plan.md`

Todo agente debe cumplir estrictamente lo definido en:

- `prd.md`: requisitos, restricciones, criterios de evaluacion y dudas pendientes.
- `plan.md`: fases, orden recomendado, dependencias, reparto de tareas y checklist final.
- `skills.md`: procedimientos reutilizables para tareas repetibles del proyecto.

Si una instruccion del usuario contradice `prd.md` o `plan.md`, el agente debe indicarlo y pedir confirmacion antes de aplicar el cambio.

Si una tarea no aparece en `prd.md` ni en `plan.md`, el agente debe considerarla fuera de alcance salvo que el usuario la apruebe explicitamente.

## Prohibicion de inventar requisitos

No se deben inventar requisitos no presentes en `prd.md`.

No se deben anadir funcionalidades, reglas de juego, sistemas de puntuacion, tipos de enemigos, tipos de objetos, mecanicas de mapa, dependencias externas o restricciones nuevas sin aprobacion del usuario.

Cuando un punto este ambiguo:

- Consultar la seccion de dudas de `prd.md`.
- Proponer una decision minima y justificable si el usuario pide avanzar.
- Documentar la decision en la memoria o documento correspondiente.
- No presentar una decision inventada como si fuera requisito del profesor.

## Prioridades del proyecto

1. Cumplir requisitos obligatorios del enunciado.
2. Respetar restricciones de Estructuras de Datos.
3. Mantener diseno claro y explicable.
4. Conseguir una version funcional y probada.
5. Documentar adecuadamente decisiones, pruebas e IA.
6. Evitar ampliaciones que pongan en riesgo la entrega.

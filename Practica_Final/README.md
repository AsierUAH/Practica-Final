# La Conquista

Juego JavaFX por turnos tipo dungeon crawler. El jugador explora habitaciones conectadas, recoge equipo, combate enemigos y debe escapar antes de quedarse sin vida o sin turnos.

## Estructura del proyecto

- `src/main/java/practicafinal/app`: arranque, rutas y aplicacion JavaFX.
- `src/main/java/practicafinal/model`: modelo de dominio: jugador, enemigos, objetos, habitaciones, celdas y partida.
- `src/main/java/practicafinal/logic`: reglas de turnos, movimiento, combate, enemigos y musica MIDI procedural.
- `src/main/java/practicafinal/structures`: estructuras propias exigidas por EEDD.
- `src/main/java/practicafinal/persistence`: carga/guardado JSON.
- `src/main/java/practicafinal/ui`: interfaz JavaFX, paneles, pantallas y pixel art.
- `src/main/resources`: CSS, JSON de campania/configuracion y recursos JavaFX.
- `src/test/java`: pruebas JUnit por modulo.
- `docs`: documentacion de entrega, UML, bocetos, diario IA, enunciado y gestion del trabajo.
- `lib`: copias locales de JavaFX/Gson conservadas como apoyo, aunque Maven resuelve dependencias desde `pom.xml`.
- `target`: carpeta generada por Maven; no es necesario entregarla salvo que se quiera incluir el JAR empaquetado.

## Requisitos

- Java 17 o superior.
- En esta entrega se incluye Maven en `../maven/apache-maven-3.9.9`.
- En el equipo de desarrollo los scripts buscan primero `~/.jdks/openjdk-25.0.2`.

## Ejecutar desde desarrollo

```bat
run.bat
```

Equivalente manual:

```bat
..\maven\apache-maven-3.9.9\bin\mvn.cmd javafx:run
```

## Ejecutar pruebas

```bat
test.bat
```

Equivalente manual:

```bat
..\maven\apache-maven-3.9.9\bin\mvn.cmd test
```

## Generar paquete

```bat
package.bat
```

El paquete se genera en `target/`:

- `target/practica-final-1.0-SNAPSHOT.jar`
- `target/dependency/` con dependencias necesarias.

Despues de empaquetar, ejecutar:

```bat
run-packaged.bat
```

O manualmente:

```bat
java -jar target\practica-final-1.0-SNAPSHOT.jar
```

## Guardado

La partida guardada principal se almacena en:

```text
%USERPROFILE%\.la-conquista\guardado-partida.json
```

Tambien hay un ejemplo de guardado en `docs/entrega/guardado-partida-ejemplo.json`.

## Documentacion de entrega

- `docs/entrega/ESTRUCTURA_ENTREGA.md`: indice recomendado para revisar/enviar el proyecto.
- `docs/diario_ia.pdf`: diario obligatorio de uso de IA.
- `docs/memoria.pdf`: memoria final del proyecto.
- `docs/costes_estructuras.pdf`: analisis de costes de las estructuras propias.
- `docs/bocetos_interfaz_y_flujo_mejorados.pdf`: bocetos y flujo de interfaz.
- `docs/uml/`: diagramas PlantUML.
- `docs/bocetos/`: bocetos de interfaz.
- `docs/enunciado/`: enunciado original.
- `docs/gestion/`: organizacion y reparto del trabajo.
- `prd.md`, `plan.md`, `AGENTS.md`, `skills.md`: documentos de trabajo y referencia usados durante el desarrollo.

## Controles

- `W` o flecha arriba: avanzar hacia donde miras.
- `S` o flecha abajo: retroceder.
- `A` o flecha izquierda: moverse a la izquierda.
- `D` o flecha derecha: moverse a la derecha.
- `Space`: atacar.
- `R`: recoger objeto adyacente.
- `E`: usar pocion.
- `Q`: gestionar inventario/equipo.
- `O`: abrir puerta.
- `F`: finalizar turno.
- `G`: guardar partida.

## Ruta recomendada

El juego funciona por ticks simultaneos: cada accion hace reaccionar a los enemigos. No conviene ir directo al jefe. Usa la vision limitada, los pasillos estrechos y los pinchos para sobrevivir.

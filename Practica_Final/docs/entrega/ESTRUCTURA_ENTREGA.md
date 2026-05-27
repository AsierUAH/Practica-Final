# Estructura de entrega

## Proyecto ejecutable

- `../../pom.xml`: configuracion Maven.
- `../../run.bat`: compila y ejecuta el juego desde desarrollo.
- `../../test.bat`: ejecuta las pruebas JUnit.
- `../../package.bat`: genera el JAR y copia dependencias.
- `../../run-packaged.bat`: ejecuta el JAR generado.
- `../../src/main/java/`: codigo fuente Java.
- `../../src/main/resources/`: JSON de configuracion, CSS y recursos.
- `../../src/test/java/`: pruebas unitarias.

## Documentacion obligatoria o de apoyo

- `../diario_ia.pdf`: diario de uso de IA.
- `../memoria.pdf`: memoria final del proyecto.
- `../costes_estructuras.pdf`: analisis de costes de estructuras.
- `../bocetos_interfaz_y_flujo_mejorados.pdf`: bocetos y flujo de interfaz.
- `../uml/`: diagramas UML.
- `../bocetos/`: bocetos de interfaz.
- `../enunciado/Práctica Final 2026 MP EEDD.pdf`: enunciado original.
- `../gestion/reparto-trabajo.md`: reparto de tareas.
- `../gestion/organizacion_trabajo.pdf`: organizacion del trabajo.
- `../../prd.md`: requisitos y restricciones.
- `../../plan.md`: plan de trabajo.

## JSON de ejemplo

- `../../src/main/resources/config-campania.json`: campania principal.
- `../../src/main/resources/config-ejemplo.json`: configuracion minima de ejemplo.
- `../../src/main/resources/config-completo.json`: configuracion ampliada.
- `../../src/main/resources/partida-ejemplo.json`: partida de ejemplo.
- `guardado-partida-ejemplo.json`: copia de guardado de ejemplo para revision.

## Carpetas generadas o locales

- `../../target/`: salida de Maven. Se puede regenerar con `package.bat`.
- `../../.idea/`, `../../*.iml`, `../../.classpath`, `../../.project`: metadatos de IDE, no necesarios para corregir.
- `../../../maven/apache-maven-3.9.9/`: Maven incluido con la entrega.

## Comando recomendado para validar

```bat
cd Practica_Final
test.bat
```

Si se quiere comprobar ejecucion:

```bat
cd Practica_Final
run.bat
```

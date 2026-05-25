# Diario de IA

## 2026-05-25 - Bloques Persona 2

- Agente/herramienta: OpenCode, modelo gpt-5.5.
- Objetivo: analizar la organizacion del trabajo y ejecutar los bloques asignados a Persona 2.
- Prompt/resumen de instruccion: centrar el trabajo en Persona 2, dividir por bloques, cerrar decisiones iniciales y avanzar verificando cada bloque antes de darlo por terminado.
- Resultado: se creo el proyecto base Maven y se implementaron estructuras propias, BFS y Dijkstra con tests JUnit preparados.
- Cambios aceptados: `pom.xml`, paquetes base, lista, pila, cola, lista circular, matriz, grafo, arbol, algoritmos de rutas y documentacion de costes.
- Cambios rechazados: no se usaron estructuras prohibidas ni arrays nativos en estructuras propias.
- Revision humana: pendiente de revisar por el grupo.
- Observaciones: inicialmente no se pudieron ejecutar tests porque `mvn` y `javac` no estaban en el PATH local; despues se prepararon JDK 8 y Maven portables dentro del proyecto.

## 2026-05-25 - Revision exhaustiva Persona 2

- Agente/herramienta: OpenCode, modelo gpt-5.5.
- Objetivo: revisar exhaustivamente la parte de Persona 2 contra los documentos del proyecto y asegurar que queda integrada hacia el juego final.
- Prompt/resumen de instruccion: comprobar cumplimiento de todos los ficheros de la carpeta, restricciones de estructuras, coherencia con la parte asignada y preparacion para integracion.
- Resultado: se revisaron estructuras, algoritmos, tests, documentacion y restricciones. Se detecto y corrigio que `GrafoPropio` permitia vertices `null`.
- Cambios aceptados: validacion de vertices no nulos en grafo, nuevos tests de borde, documentacion de integracion y revision en `docs/revision-persona-2.md`.
- Cambios rechazados: no se anadieron funcionalidades fuera del alcance de Persona 2, como combate, JSON o JavaFX real.
- Revision humana: pendiente de revisar por el grupo.
- Observaciones: se ejecuto `mvn test` con herramientas portables; resultado final: 66 tests, 0 fallos, 0 errores.

## 2026-05-25 - Preparacion de Maven/JDK local

- Agente/herramienta: OpenCode, modelo gpt-5.5.
- Objetivo: ejecutar la verificacion real con Maven y JUnit.
- Prompt/resumen de instruccion: instalar o preparar lo necesario para ejecutar `mvn test`.
- Resultado: se creo `.tools` con JDK 8 portable y Maven 3.9.9 portable; se configuro JavaFX en `pom.xml` usando `jfxrt.jar` local; `mvn compile` y `mvn test` terminaron correctamente.
- Cambios aceptados: actualizacion de `pom.xml`, `AGENTS.md` y documentacion de revision/verificacion.
- Cambios rechazados: no se modificaron estructuras ni algoritmos para hacer pasar tests.
- Revision humana: pendiente de revisar por el grupo.
- Observaciones: `mvn test` ejecuto 66 tests con 0 fallos y 0 errores.

package practicafinal.persistence;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import practicafinal.model.Celda;
import practicafinal.model.Enemigo;
import practicafinal.model.EstadoPartida;
import practicafinal.model.Habitacion;
import practicafinal.model.Jugador;
import practicafinal.model.Objeto;
import practicafinal.model.Partida;
import practicafinal.model.TipoCelda;
import practicafinal.model.TipoObjeto;
import practicafinal.structures.Grafo;
import practicafinal.structures.ListaEnlazada;

public class GestorPersistenciaTest {
    private GestorPersistencia gestor;
    @TempDir Path tempDir;
    @BeforeEach void setUp() { gestor = new GestorPersistencia(); }

    @Test void testCargarConfig() throws IOException {
        String ruta = "src/main/resources/config-ejemplo.json";
        if (!new File(ruta).exists()) return;
        ConfiguracionDTO c = gestor.cargarConfiguracion(ruta);
        assertNotNull(c); assertEquals(2, c.getHabitaciones().tamano()); assertEquals(50, c.getTurnosMaximos());
    }
    @Test void testCargarPuertasConfig() throws IOException {
        String ruta = "src/main/resources/config-ejemplo.json";
        if (!new File(ruta).exists()) return;
        ConfiguracionDTO c = gestor.cargarConfiguracion(ruta);
        assertNotNull(c.getPuertas());
        assertEquals(1, c.getPuertas().tamano());
        ConfiguracionDTO.DatosPuertaDTO p = c.getPuertas().obtener(0);
        assertEquals("sala_central", p.getHabitacionOrigen());
        assertEquals("pasillo_norte", p.getHabitacionDestino());
        assertEquals(3, p.getFila());
        assertEquals(2, p.getColumna());
    }
    @Test void testCargarCampania() throws IOException {
        String ruta = "src/main/resources/config-campania.json";
        if (!new File(ruta).exists()) return;
        ConfiguracionDTO c = gestor.cargarConfiguracion(ruta);
        assertEquals(7, c.getHabitaciones().tamano());
        assertEquals(12, c.getPuertas().tamano());
        assertEquals(8, c.getEnemigos().tamano());
        assertEquals(7, c.getObjetos().tamano());
    }
    @Test void testCargarInexistente() { assertThrows(IOException.class, () -> gestor.cargarConfiguracion("no-existe.json")); }
    @Test void testConfigSinHabitaciones() {
        String json = "{\"jugador\":{\"habitacionInicial\":\"h1\",\"fila\":0,\"columna\":0,\"vida\":10,\"ataque\":1,\"defensa\":0,\"movimiento\":1},\"turnosMaximos\":10}";
        ConfiguracionDTO c = gestor.fromJson(json, ConfiguracionDTO.class);
        assertThrows(IOException.class, () -> gestor.validarConfiguracionParaTest(c));
    }

    @Test void testConfigJugadorEnHabitacionInexistente() {
        String json = "{\"habitaciones\":[{\"id\":\"h1\",\"filas\":2,\"columnas\":2}],\"jugador\":{\"habitacionInicial\":\"h2\",\"fila\":0,\"columna\":0,\"vida\":10,\"ataque\":1,\"defensa\":0,\"movimiento\":1},\"turnosMaximos\":10}";
        ConfiguracionDTO c = gestor.fromJson(json, ConfiguracionDTO.class);
        assertThrows(IOException.class, () -> gestor.validarConfiguracionParaTest(c));
    }

    @Test void testConfigTipoCeldaInvalido() {
        String json = "{\"habitaciones\":[{\"id\":\"h1\",\"filas\":2,\"columnas\":2,\"celdas\":[{\"fila\":0,\"columna\":0,\"tipo\":\"INVALIDA\"}]}],\"jugador\":{\"habitacionInicial\":\"h1\",\"fila\":0,\"columna\":0,\"vida\":10,\"ataque\":1,\"defensa\":0,\"movimiento\":1},\"turnosMaximos\":10}";
        ConfiguracionDTO c = gestor.fromJson(json, ConfiguracionDTO.class);
        assertThrows(IOException.class, () -> gestor.validarConfiguracionParaTest(c));
    }

    @Test void testConfigPuertaFueraDeLimites() {
        String json = "{\"habitaciones\":[{\"id\":\"h1\",\"filas\":2,\"columnas\":2},{\"id\":\"h2\",\"filas\":2,\"columnas\":2}],\"jugador\":{\"habitacionInicial\":\"h1\",\"fila\":0,\"columna\":0,\"vida\":10,\"ataque\":1,\"defensa\":0,\"movimiento\":1},\"turnosMaximos\":10,\"puertas\":[{\"habitacionOrigen\":\"h1\",\"fila\":5,\"columna\":0,\"habitacionDestino\":\"h2\",\"filaEntrada\":0,\"columnaEntrada\":0}]}";
        ConfiguracionDTO c = gestor.fromJson(json, ConfiguracionDTO.class);
        assertThrows(IOException.class, () -> gestor.validarConfiguracionParaTest(c));
    }
    @Test void testCrearHabitaciones() throws IOException {
        String ruta = "src/main/resources/config-ejemplo.json";
        if (!new File(ruta).exists()) return;
        ConfiguracionDTO c = gestor.cargarConfiguracion(ruta);
        ListaEnlazada<Habitacion> l = gestor.crearHabitacionesDesdeConfig(c);
        assertEquals(2, l.tamano());
        Habitacion h = null;
        for (int i = 0; i < l.tamano(); i++) { if ("sala_central".equals(l.obtener(i).getId())) { h = l.obtener(i); break; } }
        assertNotNull(h); assertEquals(5, h.getFilas()); assertEquals(TipoCelda.MURO, h.getCelda(0, 0).getTipo());
    }
    @Test void testCrearJugador() throws IOException {
        String ruta = "src/main/resources/config-ejemplo.json";
        if (!new File(ruta).exists()) return;
        Jugador j = gestor.crearJugadorDesdeConfig(gestor.cargarConfiguracion(ruta));
        assertEquals(100, j.getVida()); assertEquals(10, j.getAtaque()); assertEquals(5, j.getDefensa());
        assertEquals(3, j.getMovimiento()); assertEquals(2, j.getFila()); assertEquals(1, j.getColumna());
    }
    @Test void testGuardarCargarPartida() throws IOException {
        Jugador j = new Jugador("Heroe", 100, 15, 8, 3); j.setPosicion(1, 2);
        Partida p = new Partida(j, 30); p.setHabitacionActual("sala1"); p.agregarEnemigo(new Enemigo("Orco", 50, 12, 5, 3, 3));
        File f = tempDir.resolve("p.json").toFile(); gestor.guardarPartida(p, f.getAbsolutePath());
        Partida c = gestor.cargarPartida(f.getAbsolutePath());
        assertEquals("Heroe", c.getJugador().getNombre()); assertEquals(100, c.getJugador().getVida());
        assertEquals("sala1", c.getHabitacionActual()); assertEquals(1, c.getEnemigos().tamano());
        assertEquals("Orco", c.getEnemigos().obtener(0).getNombre()); assertEquals(30, c.getTurnosMaximos());
    }

    @Test void testPersisteHabitacionMatriz() throws IOException {
        Habitacion hab = new Habitacion("sala1", 3, 4);
        hab.setCelda(0, 0, new Celda(TipoCelda.MURO));
        hab.setCelda(1, 2, new Celda(TipoCelda.PUERTA));
        hab.setCelda(2, 3, new Celda(TipoCelda.SALIDA));
        Partida p = new Partida(new Jugador("T", 100, 10, 5, 3), 20);
        ListaEnlazada<Habitacion> habs = new ListaEnlazada<>();
        habs.agregar(hab);
        p.setHabitaciones(habs);
        p.setHabitacionActual("sala1");
        File f = tempDir.resolve("h.json").toFile();
        gestor.guardarPartida(p, f.getAbsolutePath());
        Partida c = gestor.cargarPartida(f.getAbsolutePath());
        assertEquals(1, c.getHabitaciones().tamano());
        Habitacion recargada = c.getHabitaciones().obtener(0);
        assertEquals("sala1", recargada.getId());
        assertEquals(3, recargada.getFilas());
        assertEquals(4, recargada.getColumnas());
        assertEquals(TipoCelda.MURO, recargada.getCelda(0, 0).getTipo());
        assertEquals(TipoCelda.PUERTA, recargada.getCelda(1, 2).getTipo());
        assertEquals(TipoCelda.SALIDA, recargada.getCelda(2, 3).getTipo());
        assertTrue(recargada.getCelda(0, 0).estaOcupada());
        assertFalse(recargada.getCelda(0, 1).estaOcupada());
    }

    @Test void testPersisteObjetosEnTablero() throws IOException {
        Partida p = new Partida(new Jugador("T", 100, 10, 5, 3), 20);
        p.setHabitacionActual("s1");
        Objeto obj = new Objeto("Pocion", TipoObjeto.POCION);
        obj.setCuracion(30); obj.setHabitacionId("s1"); obj.setFila(2); obj.setColumna(3);
        ListaEnlazada<Objeto> objs = new ListaEnlazada<>();
        objs.agregar(obj);
        p.setObjetosEnTablero(objs);
        File f = tempDir.resolve("o.json").toFile();
        gestor.guardarPartida(p, f.getAbsolutePath());
        Partida c = gestor.cargarPartida(f.getAbsolutePath());
        assertEquals(1, c.getObjetosEnTablero().tamano());
        assertEquals("Pocion", c.getObjetosEnTablero().obtener(0).getNombre());
        assertEquals(30, c.getObjetosEnTablero().obtener(0).getCuracion());
        assertEquals(2, c.getObjetosEnTablero().obtener(0).getFila());
        assertEquals(3, c.getObjetosEnTablero().obtener(0).getColumna());
    }

    @Test void testPersisteGrafo() throws IOException {
        Partida p = new Partida(new Jugador("T", 100, 10, 5, 3), 20);
        p.setHabitacionActual("salaA");
        Grafo<String> grafo = new Grafo<>();
        grafo.agregarVertice("salaA"); grafo.agregarVertice("salaB"); grafo.agregarVertice("salaC");
        grafo.agregarArista("salaA", "salaB");
        grafo.agregarArista("salaB", "salaC");
        p.setGrafoHabitaciones(grafo);
        File f = tempDir.resolve("g.json").toFile();
        gestor.guardarPartida(p, f.getAbsolutePath());
        Partida c = gestor.cargarPartida(f.getAbsolutePath());
        Grafo<String> g = c.getGrafoHabitaciones();
        assertNotNull(g);
        assertTrue(g.contiene("salaA"));
        assertTrue(g.contiene("salaB"));
        assertTrue(g.contiene("salaC"));
        assertFalse(g.contiene("salaD"));
        ListaEnlazada<String> vecinosA = g.obtenerVecinos("salaA");
        assertEquals(1, vecinosA.tamano());
        assertEquals("salaB", vecinosA.obtener(0));
        ListaEnlazada<String> vecinosB = g.obtenerVecinos("salaB");
        assertEquals(2, vecinosB.tamano());
        ListaEnlazada<String> vecinosC = g.obtenerVecinos("salaC");
        assertEquals(1, vecinosC.tamano());
        assertEquals("salaB", vecinosC.obtener(0));
    }
    @Test void testGuardarCargarEventos() throws IOException {
        Partida p = new Partida(new Jugador("T", 50, 10, 3, 2), 20); p.setHabitacionActual("s1");
        p.agregarEvento("E1"); p.agregarEvento("E2");
        File f = tempDir.resolve("pe.json").toFile(); gestor.guardarPartida(p, f.getAbsolutePath());
        Partida c = gestor.cargarPartida(f.getAbsolutePath()); assertEquals(2, c.getEventos().tamano());
    }
    @Test void testGuardarCargarInventario() throws IOException {
        Jugador j = new Jugador("T", 100, 10, 5, 3);
        Objeto e = new Objeto("Espada", TipoObjeto.ARMA); e.setAtaque(8);
        Objeto p = new Objeto("Pocion", TipoObjeto.POCION); p.setCuracion(30);
        j.agregarObjeto(e); j.agregarObjeto(p); j.setArmaEquipada(e);
        Partida partida = new Partida(j, 30); partida.setHabitacionActual("s1");
        File f = tempDir.resolve("pi.json").toFile(); gestor.guardarPartida(partida, f.getAbsolutePath());
        Partida c = gestor.cargarPartida(f.getAbsolutePath());
        assertEquals(2, c.getJugador().getInventario().tamano());
        assertEquals("Espada", c.getJugador().getInventario().obtener(0).getNombre());
        assertEquals(8, c.getJugador().getInventario().obtener(0).getAtaque());
        assertEquals("Pocion", c.getJugador().getInventario().obtener(1).getNombre());
        assertEquals(30, c.getJugador().getInventario().obtener(1).getCuracion());
        assertNotNull(c.getJugador().getArmaEquipada());
    }
    @Test void testDerrotaPersiste() throws IOException {
        Partida p = new Partida(new Jugador("T", 100, 10, 5, 3), 1); p.consumirTurno();
        File f = tempDir.resolve("pd.json").toFile(); gestor.guardarPartida(p, f.getAbsolutePath());
        Partida c = gestor.cargarPartida(f.getAbsolutePath()); assertEquals(0, c.getTurnosRestantes()); assertEquals(EstadoPartida.DERROTA, c.getEstado());
    }
    @Test void testCargarPartidaInexistente() { assertThrows(IOException.class, () -> gestor.cargarPartida("no-existe.json")); }
    @Test void testGuardarRutaInvalida() { assertThrows(IOException.class, () -> gestor.guardarPartida(new Partida(new Jugador("T", 100, 10, 5, 3), 30), "/ruta/invalida/no-permitida/p.json")); }
}

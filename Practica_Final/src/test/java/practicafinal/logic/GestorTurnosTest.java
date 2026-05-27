package practicafinal.logic;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GestorTurnosTest {
    private static class E implements Turnable {
        final String n; boolean v; int t;
        E(String n, boolean v) { this.n = n; this.v = v; this.t = 0; }
        public void tomarTurno() { t++; }
        public boolean estaVivo() { return v; }
        void morir() { v = false; }
    }

    @Test void testJugadorPrimero() {
        E j = new E("J", true); E e = new E("E", true);
        GestorTurnos g = new GestorTurnos(j); g.agregarEnemigo(e); g.iniciarRonda();
        assertEquals(j, g.siguienteTurno());
    }
    @Test void testEnemigosDespues() {
        E j = new E("J", true); E e1 = new E("E1", true); E e2 = new E("E2", true);
        GestorTurnos g = new GestorTurnos(j); g.agregarEnemigo(e1); g.agregarEnemigo(e2); g.iniciarRonda();
        assertEquals(j, g.siguienteTurno()); assertEquals(e1, g.siguienteTurno()); assertEquals(e2, g.siguienteTurno());
    }
    @Test void testRondaTerminada() {
        E j = new E("J", true); E e = new E("E", true);
        GestorTurnos g = new GestorTurnos(j); g.agregarEnemigo(e); g.iniciarRonda();
        assertFalse(g.rondaTerminada()); g.siguienteTurno(); g.siguienteTurno(); assertTrue(g.rondaTerminada());
    }
    @Test void testSinRondaNull() { assertNull(new GestorTurnos(new E("J", true)).siguienteTurno()); }
    @Test void testMuertoNoParticipa() {
        E j = new E("J", true); E v = new E("V", true); E m = new E("M", false);
        GestorTurnos g = new GestorTurnos(j); g.agregarEnemigo(v); g.agregarEnemigo(m); g.iniciarRonda();
        assertEquals(j, g.siguienteTurno()); assertEquals(v, g.siguienteTurno()); assertTrue(g.rondaTerminada());
    }
    @Test void testMuereEntreRondas() {
        E j = new E("J", true); E e = new E("E", true);
        GestorTurnos g = new GestorTurnos(j); g.agregarEnemigo(e); g.iniciarRonda();
        e.morir(); g.iniciarRonda(); assertEquals(j, g.siguienteTurno()); assertTrue(g.rondaTerminada());
    }
    @Test void testReiniciar() {
        GestorTurnos g = new GestorTurnos(new E("J", true)); g.agregarEnemigo(new E("E", true));
        g.iniciarRonda(); g.reiniciar(); assertEquals(0, g.getRondaActual()); assertNull(g.siguienteTurno());
    }
    @Test void testMultiplesRondas() {
        E j = new E("J", true); E e = new E("E", true);
        GestorTurnos g = new GestorTurnos(j); g.agregarEnemigo(e);
        for (int r = 1; r <= 3; r++) { g.iniciarRonda(); assertEquals(j, g.siguienteTurno()); assertEquals(e, g.siguienteTurno()); assertEquals(r, g.getRondaActual()); }
    }
}

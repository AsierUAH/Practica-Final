package practicafinal.logic;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import practicafinal.model.Celda;
import practicafinal.model.Direccion;
import practicafinal.model.Habitacion;
import practicafinal.model.TipoCelda;
import practicafinal.structures.ListaEnlazada;

public class MotorMovimientoTest {
    private Habitacion h;
    private MotorMovimiento m;
    @BeforeEach void setUp() { h = new Habitacion(5, 5); m = new MotorMovimiento(); }

    @Test void testMovValido() { assertTrue(m.esMovimientoValido(h, 2, 2, 2, 3)); assertTrue(m.esMovimientoValido(h, 2, 2, 1, 2)); }
    @Test void testFueraRango() { assertFalse(m.esMovimientoValido(h, 0, 0, -1, 0)); assertFalse(m.esMovimientoValido(h, 4, 4, 5, 4)); }
    @Test void testDiagonal() { assertFalse(m.esMovimientoValido(h, 2, 2, 3, 3)); assertFalse(m.esMovimientoValido(h, 0, 0, 1, 1)); }
    @Test void testOcupada() { Celda c = new Celda(TipoCelda.VACIA); c.setOcupada(true); h.setCelda(2, 3, c); assertFalse(m.esMovimientoValido(h, 2, 2, 2, 3)); }
    @Test void testMuro() { h.setCelda(1, 2, new Celda(TipoCelda.MURO)); assertFalse(m.esMovimientoValido(h, 0, 2, 1, 2)); }
    @Test void testMismaCasilla() { assertFalse(m.esMovimientoValido(h, 2, 2, 2, 2)); }
    @Test void testNoAdyacente() { assertFalse(m.esMovimientoValido(h, 0, 0, 0, 3)); }
    @Test void testAplicarArriba() { assertArrayEquals(new int[]{1, 2}, m.aplicarMovimiento(Direccion.ARRIBA, 2, 2)); }
    @Test void testAplicarAbajo() { assertArrayEquals(new int[]{3, 2}, m.aplicarMovimiento(Direccion.ABAJO, 2, 2)); }
    @Test void testAplicarIzquierda() { assertArrayEquals(new int[]{2, 1}, m.aplicarMovimiento(Direccion.IZQUIERDA, 2, 2)); }
    @Test void testAplicarDerecha() { assertArrayEquals(new int[]{2, 3}, m.aplicarMovimiento(Direccion.DERECHA, 2, 2)); }

    @Test void testAlcance1() {
        ListaEnlazada<int[]> r = m.casillasAlcanzables(h, 2, 2, 1);
        assertEquals(4, r.tamano());
        assertTrue(contiene(r, 1, 2)); assertTrue(contiene(r, 3, 2));
        assertTrue(contiene(r, 2, 1)); assertTrue(contiene(r, 2, 3));
    }
    @Test void testAlcance2() { ListaEnlazada<int[]> r = m.casillasAlcanzables(h, 2, 2, 2); assertTrue(r.tamano() > 4); assertTrue(contiene(r, 0, 2)); }
    @Test void testAlcanceConMuros() {
        h.setCelda(1, 2, new Celda(TipoCelda.MURO)); h.setCelda(2, 1, new Celda(TipoCelda.MURO));
        ListaEnlazada<int[]> r = m.casillasAlcanzables(h, 2, 2, 1); assertEquals(2, r.tamano()); assertTrue(contiene(r, 3, 2)); assertTrue(contiene(r, 2, 3));
    }
    @Test void testAlcanceNoIncluyeOcupadas() {
        Celda ocupada = new Celda(TipoCelda.VACIA);
        ocupada.setOcupada(true);
        h.setCelda(2, 3, ocupada);
        ListaEnlazada<int[]> r = m.casillasAlcanzables(h, 2, 2, 1);
        assertEquals(3, r.tamano());
        assertFalse(contiene(r, 2, 3));
    }
    @Test void testAlcanceCero() { assertTrue(m.casillasAlcanzables(h, 2, 2, 0).estaVacia()); }
    @Test void testAlcanceNull() { assertTrue(m.casillasAlcanzables(null, 0, 0, 3).estaVacia()); }
    @Test void testAlcanceNoIncluyeOrigen() { assertFalse(contiene(m.casillasAlcanzables(h, 2, 2, 3), 2, 2)); }
    @Test void testAlcanceEsquina() { ListaEnlazada<int[]> r = m.casillasAlcanzables(h, 0, 0, 1); assertEquals(2, r.tamano()); assertTrue(contiene(r, 1, 0)); assertTrue(contiene(r, 0, 1)); }

    @Test void testDistPuerta() { h.setCelda(0, 2, new Celda(TipoCelda.PUERTA)); h.setCelda(1, 2, new Celda(TipoCelda.VACIA)); assertEquals(2, m.distanciaMinimaATipo(h, 2, 2, TipoCelda.PUERTA)); }
    @Test void testDistSalida() { h.setCelda(4, 4, new Celda(TipoCelda.SALIDA)); assertEquals(4, m.distanciaMinimaATipo(h, 2, 2, TipoCelda.SALIDA)); }
    @Test void testDistInaccesible() { for (int f = 0; f < 5; f++) for (int c = 0; c < 5; c++) h.setCelda(f, c, new Celda(TipoCelda.MURO)); assertEquals(-1, m.distanciaMinimaATipo(h, 2, 2, TipoCelda.PUERTA)); }
    @Test void testDistMuros() { h.setCelda(0, 2, new Celda(TipoCelda.PUERTA)); h.setCelda(1, 2, new Celda(TipoCelda.MURO)); assertEquals(4, m.distanciaMinimaATipo(h, 2, 2, TipoCelda.PUERTA)); }
    @Test void testDistOrigenEsTipo() { h.setCelda(2, 2, new Celda(TipoCelda.PUERTA)); assertEquals(0, m.distanciaMinimaATipo(h, 2, 2, TipoCelda.PUERTA)); }
    @Test void testDistNull() { assertEquals(-1, m.distanciaMinimaATipo(null, 0, 0, TipoCelda.PUERTA)); }

    private boolean contiene(ListaEnlazada<int[]> l, int f, int c) {
        for (int i = 0; i < l.tamano(); i++) { int[] p = l.obtener(i); if (p[0] == f && p[1] == c) return true; }
        return false;
    }
}

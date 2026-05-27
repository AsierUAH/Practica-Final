package practicafinal.structures;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ListaEnlazadaTest {

    @Test void testVacia() { ListaEnlazada<String> l = new ListaEnlazada<>(); assertTrue(l.estaVacia()); assertEquals(0, l.tamano()); }
    @Test void testAgregarYObtener() {
        ListaEnlazada<String> l = new ListaEnlazada<>(); l.agregar("A"); l.agregar("B"); l.agregar("C");
        assertEquals("A", l.obtener(0)); assertEquals("B", l.obtener(1)); assertEquals("C", l.obtener(2));
    }
    @Test void testEliminarPorIndice() {
        ListaEnlazada<String> l = new ListaEnlazada<>(); l.agregar("A"); l.agregar("B"); l.agregar("C");
        assertEquals("B", l.eliminar(1)); assertEquals(2, l.tamano()); assertEquals("A", l.obtener(0)); assertEquals("C", l.obtener(1));
    }
    @Test void testEliminarCabeza() {
        ListaEnlazada<String> l = new ListaEnlazada<>(); l.agregar("A"); l.agregar("B");
        assertEquals("A", l.eliminar(0)); assertEquals("B", l.obtener(0)); assertEquals(1, l.tamano());
    }
    @Test void testEliminarPorElemento() {
        ListaEnlazada<String> l = new ListaEnlazada<>(); l.agregar("A"); l.agregar("B"); l.agregar("C");
        assertTrue(l.eliminar("B")); assertEquals(2, l.tamano()); assertEquals("A", l.obtener(0)); assertEquals("C", l.obtener(1));
    }
    @Test void testEliminarElementoInexistente() {
        ListaEnlazada<String> l = new ListaEnlazada<>(); l.agregar("A"); assertFalse(l.eliminar("Z")); assertEquals(1, l.tamano());
    }
    @Test void testContiene() {
        ListaEnlazada<String> l = new ListaEnlazada<>(); l.agregar("X"); l.agregar("Y");
        assertTrue(l.contiene("X")); assertTrue(l.contiene("Y")); assertFalse(l.contiene("Z"));
    }
    @Test void testVaciar() { ListaEnlazada<String> l = new ListaEnlazada<>(); l.agregar("A"); l.agregar("B"); l.vaciar(); assertTrue(l.estaVacia()); }
    @Test void testToArray() {
        ListaEnlazada<String> l = new ListaEnlazada<>(); l.agregar("A"); l.agregar("B"); Object[] a = l.toArray();
        assertEquals(2, a.length); assertEquals("A", a[0]); assertEquals("B", a[1]);
    }
    @Test void testIndiceFueraDeRango() {
        ListaEnlazada<String> l = new ListaEnlazada<>(); l.agregar("A");
        assertThrows(IndexOutOfBoundsException.class, () -> l.obtener(5));
        assertThrows(IndexOutOfBoundsException.class, () -> l.eliminar(-1));
    }
}

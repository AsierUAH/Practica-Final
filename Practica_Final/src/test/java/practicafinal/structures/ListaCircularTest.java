package practicafinal.structures;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ListaCircularTest {

    @Test void testVacia() { ListaCircular<String> l = new ListaCircular<>(); assertTrue(l.estaVacia()); }
    @Test void testAgregarYObtener() {
        ListaCircular<String> l = new ListaCircular<>(); l.agregar("A"); l.agregar("B"); l.agregar("C");
        assertEquals("A", l.obtener(0)); assertEquals("B", l.obtener(1)); assertEquals("C", l.obtener(2));
    }
    @Test void testEliminar() {
        ListaCircular<String> l = new ListaCircular<>(); l.agregar("A"); l.agregar("B"); l.agregar("C");
        assertEquals("B", l.eliminar(1)); assertEquals(2, l.tamano());
    }
    @Test void testEliminarUnico() {
        ListaCircular<String> l = new ListaCircular<>(); l.agregar("X"); assertEquals("X", l.eliminar(0)); assertTrue(l.estaVacia());
    }
    @Test void testContiene() {
        ListaCircular<String> l = new ListaCircular<>(); l.agregar("A"); l.agregar("B");
        assertTrue(l.contiene("A")); assertTrue(l.contiene("B")); assertFalse(l.contiene("Z"));
    }
    @Test void testVaciar() { ListaCircular<String> l = new ListaCircular<>(); l.agregar("A"); l.vaciar(); assertTrue(l.estaVacia()); }
    @Test void testCircular() {
        ListaCircular<String> l = new ListaCircular<>(); l.agregar("A"); l.agregar("B"); l.agregar("C");
        assertEquals("A", l.obtener(3)); assertEquals("B", l.obtener(4));
    }
}

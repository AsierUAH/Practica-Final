package practicafinal.structures;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArbolTest {

    @Test void testRaiz() { Arbol<String> a = new Arbol<>("R"); assertEquals("R", a.getRaiz()); assertFalse(a.estaVacio()); }
    @Test void testSetRaiz() { Arbol<String> a = new Arbol<>("A"); a.setRaiz("B"); assertEquals("B", a.getRaiz()); }
    @Test void testAgregarHijo() {
        Arbol<String> a = new Arbol<>("R"); a.agregarHijo("R", "H1"); a.agregarHijo("R", "H2");
        ListaEnlazada<String> h = a.obtenerHijos("R"); assertEquals(2, h.tamano()); assertTrue(h.contiene("H1"));
    }
    @Test void testAgregarHijoInexistente() { Arbol<String> a = new Arbol<>("R"); assertFalse(a.agregarHijo("X", "H")); }
    @Test void testContiene() {
        Arbol<String> a = new Arbol<>("R"); a.agregarHijo("R", "H"); a.agregarHijo("H", "N");
        assertTrue(a.contiene("R")); assertTrue(a.contiene("H")); assertTrue(a.contiene("N")); assertFalse(a.contiene("X"));
    }
    @Test void testPreorden() {
        Arbol<String> a = new Arbol<>("A"); a.agregarHijo("A", "B"); a.agregarHijo("A", "C"); a.agregarHijo("B", "D");
        ListaEnlazada<String> o = a.recorrerPreorden();
        assertEquals("A", o.obtener(0)); assertEquals("B", o.obtener(1)); assertEquals("D", o.obtener(2)); assertEquals("C", o.obtener(3));
    }
    @Test void testVaciar() { Arbol<String> a = new Arbol<>("R"); a.vaciar(); assertTrue(a.estaVacio()); assertNull(a.getRaiz()); }
    @Test void testObtenerHijosSinHijos() { Arbol<String> a = new Arbol<>("R"); assertTrue(a.obtenerHijos("R").estaVacia()); }
}

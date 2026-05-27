package practicafinal.structures;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GrafoTest {

    private Grafo<String> g;
    @BeforeEach void setUp() { g = new Grafo<>(); }

    @Test void testVacio() { assertTrue(g.estaVacio()); }
    @Test void testAgregarVertice() { g.agregarVertice("A"); assertTrue(g.contiene("A")); assertEquals(1, g.tamano()); }
    @Test void testVerticeDuplicado() { g.agregarVertice("A"); g.agregarVertice("A"); assertEquals(1, g.tamano()); }
    @Test void testArista() {
        g.agregarVertice("A"); g.agregarVertice("B"); g.agregarArista("A", "B");
        assertEquals(1, g.obtenerVecinos("A").tamano()); assertTrue(g.obtenerVecinos("A").contiene("B"));
        assertEquals(1, g.obtenerVecinos("B").tamano()); assertTrue(g.obtenerVecinos("B").contiene("A"));
    }
    @Test void testAristaInexistente() { g.agregarVertice("A"); g.agregarArista("A", "Z"); assertTrue(g.obtenerVecinos("A").estaVacia()); }
    @Test void testVecinosInexistente() { assertTrue(g.obtenerVecinos("X").estaVacia()); }
    @Test void testBFS() {
        g.agregarVertice("A"); g.agregarVertice("B"); g.agregarVertice("C"); g.agregarVertice("D");
        g.agregarArista("A", "B"); g.agregarArista("B", "C"); g.agregarArista("C", "D");
        ListaEnlazada<String> r = g.bfs("A", "D");
        assertEquals(4, r.tamano()); assertEquals("A", r.obtener(0)); assertEquals("D", r.obtener(3));
    }
    @Test void testBFSDirecto() { g.agregarVertice("A"); g.agregarVertice("B"); g.agregarArista("A", "B");
        ListaEnlazada<String> r = g.bfs("A", "B"); assertEquals(2, r.tamano()); assertEquals("A", r.obtener(0)); assertEquals("B", r.obtener(1)); }
    @Test void testBFSInexistente() { g.agregarVertice("A"); g.agregarVertice("B"); g.agregarArista("A", "B");
        assertTrue(g.bfs("A", "C").estaVacia()); }
    @Test void testBFSInicioInexistente() { g.agregarVertice("A"); assertTrue(g.bfs("X", "A").estaVacia()); }
    @Test void testAristaNoDuplicada() { g.agregarVertice("A"); g.agregarVertice("B"); g.agregarArista("A", "B"); g.agregarArista("A", "B");
        assertEquals(1, g.obtenerVecinos("A").tamano()); }
}

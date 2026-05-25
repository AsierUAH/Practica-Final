package practicafinal.structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GrafoPropioTest {

    @Test
    void grafoNuevoNoTieneVertices() {
        GrafoPropio<String> grafo = new GrafoPropio<String>();

        assertEquals(0, grafo.numeroVertices());
        assertFalse(grafo.existeVertice("inicio"));
    }

    @Test
    void agregaVerticesSinDuplicarlos() {
        GrafoPropio<String> grafo = new GrafoPropio<String>();

        grafo.agregarVertice("A");
        grafo.agregarVertice("A");

        assertTrue(grafo.existeVertice("A"));
        assertEquals(1, grafo.numeroVertices());
    }

    @Test
    void agregaAristaNoDirigidaConCoste() {
        GrafoPropio<String> grafo = new GrafoPropio<String>();

        grafo.agregarArista("A", "B", 4);

        assertTrue(grafo.existeArista("A", "B"));
        assertTrue(grafo.existeArista("B", "A"));
        assertEquals(4, grafo.obtenerCoste("A", "B"));
        assertEquals(4, grafo.obtenerCoste("B", "A"));
    }

    @Test
    void actualizaCosteDeAristaExistente() {
        GrafoPropio<String> grafo = new GrafoPropio<String>();

        grafo.agregarArista("A", "B", 4);
        grafo.agregarArista("A", "B", 2);

        assertEquals(2, grafo.obtenerCoste("A", "B"));
        assertEquals(2, grafo.obtenerCoste("B", "A"));
        assertEquals(2, grafo.numeroVertices());
    }

    @Test
    void obtieneVecinosDeUnVertice() {
        GrafoPropio<String> grafo = new GrafoPropio<String>();
        grafo.agregarArista("A", "B", 1);
        grafo.agregarArista("A", "C", 1);

        ListaEnlazadaPropia<String> vecinos = grafo.obtenerVecinos("A");

        assertEquals(2, vecinos.tamano());
        assertTrue(vecinos.contiene("B"));
        assertTrue(vecinos.contiene("C"));
    }

    @Test
    void obtieneVerticesDelGrafo() {
        GrafoPropio<String> grafo = new GrafoPropio<String>();
        grafo.agregarArista("A", "B", 1);
        grafo.agregarVertice("C");

        ListaEnlazadaPropia<String> vertices = grafo.obtenerVertices();

        assertEquals(3, vertices.tamano());
        assertTrue(vertices.contiene("A"));
        assertTrue(vertices.contiene("B"));
        assertTrue(vertices.contiene("C"));
    }

    @Test
    void actualizarAristaNoDuplicaVecinos() {
        GrafoPropio<String> grafo = new GrafoPropio<String>();

        grafo.agregarArista("A", "B", 4);
        grafo.agregarArista("A", "B", 2);

        assertEquals(1, grafo.obtenerVecinos("A").tamano());
        assertEquals(1, grafo.obtenerVecinos("B").tamano());
    }

    @Test
    void costeNegativoLanzaExcepcion() {
        final GrafoPropio<String> grafo = new GrafoPropio<String>();

        assertThrows(IllegalArgumentException.class, new org.junit.jupiter.api.function.Executable() {
            @Override
            public void execute() {
                grafo.agregarArista("A", "B", -1);
            }
        });
    }

    @Test
    void verticeNuloLanzaExcepcion() {
        final GrafoPropio<String> grafo = new GrafoPropio<String>();

        assertThrows(IllegalArgumentException.class, new org.junit.jupiter.api.function.Executable() {
            @Override
            public void execute() {
                grafo.agregarVertice(null);
            }
        });
    }

    @Test
    void obtenerVecinosDeVerticeInexistenteLanzaExcepcion() {
        final GrafoPropio<String> grafo = new GrafoPropio<String>();

        assertThrows(IllegalArgumentException.class, new org.junit.jupiter.api.function.Executable() {
            @Override
            public void execute() {
                grafo.obtenerVecinos("no existe");
            }
        });
    }
}

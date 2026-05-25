package practicafinal.logic;

import org.junit.jupiter.api.Test;
import practicafinal.structures.GrafoPropio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalculadorRutasHabitacionesTest {

    @Test
    void calculaRutaMinimaConCostesVariables() {
        GrafoPropio<String> grafo = new GrafoPropio<String>();
        grafo.agregarArista("A", "B", 10);
        grafo.agregarArista("A", "C", 1);
        grafo.agregarArista("C", "B", 2);

        RutaMinima<String> ruta = new CalculadorRutasHabitaciones().calcularRutaMinima(grafo, "A", "B");

        assertTrue(ruta.existe());
        assertEquals(3, ruta.getCosteTotal());
        assertEquals("A", ruta.getVertices().obtener(0));
        assertEquals("C", ruta.getVertices().obtener(1));
        assertEquals("B", ruta.getVertices().obtener(2));
    }

    @Test
    void origenYDestinoIgualesDevuelveCosteCero() {
        GrafoPropio<String> grafo = new GrafoPropio<String>();
        grafo.agregarVertice("A");

        RutaMinima<String> ruta = new CalculadorRutasHabitaciones().calcularRutaMinima(grafo, "A", "A");

        assertTrue(ruta.existe());
        assertEquals(0, ruta.getCosteTotal());
        assertEquals(1, ruta.getVertices().tamano());
        assertEquals("A", ruta.getVertices().obtener(0));
    }

    @Test
    void destinoInalcanzableDevuelveRutaInexistente() {
        GrafoPropio<String> grafo = new GrafoPropio<String>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");

        RutaMinima<String> ruta = new CalculadorRutasHabitaciones().calcularRutaMinima(grafo, "A", "B");

        assertFalse(ruta.existe());
        assertEquals(-1, ruta.getCosteTotal());
        assertTrue(ruta.getVertices().estaVacia());
    }

    @Test
    void origenInexistenteLanzaExcepcion() {
        final GrafoPropio<String> grafo = new GrafoPropio<String>();
        grafo.agregarVertice("A");

        assertThrows(IllegalArgumentException.class, new org.junit.jupiter.api.function.Executable() {
            @Override
            public void execute() {
                new CalculadorRutasHabitaciones().calcularRutaMinima(grafo, "X", "A");
            }
        });
    }

    @Test
    void destinoInexistenteLanzaExcepcion() {
        final GrafoPropio<String> grafo = new GrafoPropio<String>();
        grafo.agregarVertice("A");

        assertThrows(IllegalArgumentException.class, new org.junit.jupiter.api.function.Executable() {
            @Override
            public void execute() {
                new CalculadorRutasHabitaciones().calcularRutaMinima(grafo, "A", "X");
            }
        });
    }

    @Test
    void grafoNuloLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, new org.junit.jupiter.api.function.Executable() {
            @Override
            public void execute() {
                new CalculadorRutasHabitaciones().calcularRutaMinima(null, "A", "B");
            }
        });
    }
}

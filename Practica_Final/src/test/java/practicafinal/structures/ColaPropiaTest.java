package practicafinal.structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ColaPropiaTest {

    @Test
    void colaNuevaEstaVacia() {
        ColaPropia<String> cola = new ColaPropia<String>();

        assertTrue(cola.estaVacia());
        assertEquals(0, cola.tamano());
    }

    @Test
    void encolaYFrenteDevuelvePrimerElementoSinEliminarlo() {
        ColaPropia<String> cola = new ColaPropia<String>();

        cola.encolar("jugador");
        cola.encolar("enemigo");

        assertFalse(cola.estaVacia());
        assertEquals(2, cola.tamano());
        assertEquals("jugador", cola.frente());
        assertEquals(2, cola.tamano());
    }

    @Test
    void desencolaEnOrdenFifo() {
        ColaPropia<String> cola = new ColaPropia<String>();
        cola.encolar("primero");
        cola.encolar("segundo");
        cola.encolar("tercero");

        assertEquals("primero", cola.desencolar());
        assertEquals("segundo", cola.desencolar());
        assertEquals("tercero", cola.desencolar());
        assertTrue(cola.estaVacia());
    }

    @Test
    void puedeReutilizarseTrasQuedarVacia() {
        ColaPropia<String> cola = new ColaPropia<String>();
        cola.encolar("a");
        cola.desencolar();

        cola.encolar("b");

        assertEquals("b", cola.frente());
        assertEquals(1, cola.tamano());
    }

    @Test
    void permiteElementoNulo() {
        ColaPropia<String> cola = new ColaPropia<String>();

        cola.encolar(null);

        assertEquals(null, cola.frente());
        assertEquals(null, cola.desencolar());
        assertTrue(cola.estaVacia());
    }

    @Test
    void desencolarColaVaciaLanzaExcepcion() {
        final ColaPropia<String> cola = new ColaPropia<String>();

        assertThrows(IllegalStateException.class, new org.junit.jupiter.api.function.Executable() {
            @Override
            public void execute() {
                cola.desencolar();
            }
        });
    }

    @Test
    void frenteColaVaciaLanzaExcepcion() {
        final ColaPropia<String> cola = new ColaPropia<String>();

        assertThrows(IllegalStateException.class, new org.junit.jupiter.api.function.Executable() {
            @Override
            public void execute() {
                cola.frente();
            }
        });
    }
}

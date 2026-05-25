package practicafinal.structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PilaPropiaTest {

    @Test
    void pilaNuevaEstaVacia() {
        PilaPropia<String> pila = new PilaPropia<String>();

        assertTrue(pila.estaVacia());
        assertEquals(0, pila.tamano());
    }

    @Test
    void apilaYCimaDevuelveUltimoElementoSinEliminarlo() {
        PilaPropia<String> pila = new PilaPropia<String>();

        pila.apilar("espada");
        pila.apilar("pocion");

        assertFalse(pila.estaVacia());
        assertEquals(2, pila.tamano());
        assertEquals("pocion", pila.cima());
        assertEquals(2, pila.tamano());
    }

    @Test
    void desapilaEnOrdenLifo() {
        PilaPropia<String> pila = new PilaPropia<String>();
        pila.apilar("primero");
        pila.apilar("segundo");
        pila.apilar("tercero");

        assertEquals("tercero", pila.desapilar());
        assertEquals("segundo", pila.desapilar());
        assertEquals("primero", pila.desapilar());
        assertTrue(pila.estaVacia());
    }

    @Test
    void permiteElementoNulo() {
        PilaPropia<String> pila = new PilaPropia<String>();

        pila.apilar(null);

        assertEquals(null, pila.cima());
        assertEquals(null, pila.desapilar());
        assertTrue(pila.estaVacia());
    }

    @Test
    void desapilarPilaVaciaLanzaExcepcion() {
        final PilaPropia<String> pila = new PilaPropia<String>();

        assertThrows(IllegalStateException.class, new org.junit.jupiter.api.function.Executable() {
            @Override
            public void execute() {
                pila.desapilar();
            }
        });
    }

    @Test
    void cimaPilaVaciaLanzaExcepcion() {
        final PilaPropia<String> pila = new PilaPropia<String>();

        assertThrows(IllegalStateException.class, new org.junit.jupiter.api.function.Executable() {
            @Override
            public void execute() {
                pila.cima();
            }
        });
    }
}

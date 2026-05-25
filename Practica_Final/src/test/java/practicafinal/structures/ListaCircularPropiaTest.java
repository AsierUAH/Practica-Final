package practicafinal.structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListaCircularPropiaTest {

    @Test
    void listaNuevaEstaVacia() {
        ListaCircularPropia<String> lista = new ListaCircularPropia<String>();

        assertTrue(lista.estaVacia());
        assertEquals(0, lista.tamano());
    }

    @Test
    void recorreElementosDeFormaCircular() {
        ListaCircularPropia<String> lista = new ListaCircularPropia<String>();
        lista.agregar("enemigo1");
        lista.agregar("enemigo2");
        lista.agregar("enemigo3");

        assertEquals("enemigo1", lista.obtenerActual());
        assertEquals("enemigo2", lista.siguiente());
        assertEquals("enemigo3", lista.siguiente());
        assertEquals("enemigo1", lista.siguiente());
    }

    @Test
    void eliminaElementoActualYContinuaRotacion() {
        ListaCircularPropia<String> lista = new ListaCircularPropia<String>();
        lista.agregar("a");
        lista.agregar("b");
        lista.agregar("c");

        assertTrue(lista.eliminar("a"));

        assertEquals(2, lista.tamano());
        assertEquals("b", lista.obtenerActual());
        assertEquals("c", lista.siguiente());
        assertEquals("b", lista.siguiente());
    }

    @Test
    void eliminaUltimoElementoYConservaCircularidad() {
        ListaCircularPropia<String> lista = new ListaCircularPropia<String>();
        lista.agregar("a");
        lista.agregar("b");
        lista.agregar("c");

        assertTrue(lista.eliminar("c"));

        assertEquals("a", lista.obtenerActual());
        assertEquals("b", lista.siguiente());
        assertEquals("a", lista.siguiente());
    }

    @Test
    void eliminaUnicoElemento() {
        ListaCircularPropia<String> lista = new ListaCircularPropia<String>();
        lista.agregar("unico");

        assertTrue(lista.eliminar("unico"));

        assertTrue(lista.estaVacia());
        assertEquals(0, lista.tamano());
    }

    @Test
    void contieneElementosYNulos() {
        ListaCircularPropia<String> lista = new ListaCircularPropia<String>();
        lista.agregar("enemigo");
        lista.agregar(null);

        assertTrue(lista.contiene("enemigo"));
        assertTrue(lista.contiene(null));
        assertFalse(lista.contiene("no existe"));
    }

    @Test
    void obtenerActualEnListaVaciaLanzaExcepcion() {
        final ListaCircularPropia<String> lista = new ListaCircularPropia<String>();

        assertThrows(IllegalStateException.class, new org.junit.jupiter.api.function.Executable() {
            @Override
            public void execute() {
                lista.obtenerActual();
            }
        });
    }
}

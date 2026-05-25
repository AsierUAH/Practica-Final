package practicafinal.structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListaEnlazadaPropiaTest {

    @Test
    void listaNuevaEstaVacia() {
        ListaEnlazadaPropia<String> lista = new ListaEnlazadaPropia<String>();

        assertTrue(lista.estaVacia());
        assertEquals(0, lista.tamano());
    }

    @Test
    void agregaYObtieneElementosEnOrden() {
        ListaEnlazadaPropia<String> lista = new ListaEnlazadaPropia<String>();

        lista.agregar("espada");
        lista.agregar("pocion");

        assertFalse(lista.estaVacia());
        assertEquals(2, lista.tamano());
        assertEquals("espada", lista.obtener(0));
        assertEquals("pocion", lista.obtener(1));
    }

    @Test
    void insertaEnPosicionIntermedia() {
        ListaEnlazadaPropia<String> lista = new ListaEnlazadaPropia<String>();

        lista.agregar("inicio");
        lista.agregar("fin");
        lista.insertar(1, "centro");

        assertEquals("inicio", lista.obtener(0));
        assertEquals("centro", lista.obtener(1));
        assertEquals("fin", lista.obtener(2));
    }

    @Test
    void eliminaPorIndice() {
        ListaEnlazadaPropia<String> lista = new ListaEnlazadaPropia<String>();
        lista.agregar("a");
        lista.agregar("b");
        lista.agregar("c");

        String eliminado = lista.eliminar(1);

        assertEquals("b", eliminado);
        assertEquals(2, lista.tamano());
        assertEquals("a", lista.obtener(0));
        assertEquals("c", lista.obtener(1));
    }

    @Test
    void eliminaElementoExistente() {
        ListaEnlazadaPropia<String> lista = new ListaEnlazadaPropia<String>();
        lista.agregar("llave");
        lista.agregar("escudo");

        assertTrue(lista.eliminarElemento("llave"));
        assertFalse(lista.contiene("llave"));
        assertEquals(1, lista.tamano());
    }

    @Test
    void buscaElementosYNulos() {
        ListaEnlazadaPropia<String> lista = new ListaEnlazadaPropia<String>();
        lista.agregar("arma");
        lista.agregar(null);

        assertTrue(lista.contiene("arma"));
        assertTrue(lista.contiene(null));
        assertEquals(0, lista.indiceDe("arma"));
        assertEquals(1, lista.indiceDe(null));
        assertEquals(-1, lista.indiceDe("no existe"));
    }

    @Test
    void obtenerIndiceInvalidoLanzaExcepcion() {
        ListaEnlazadaPropia<String> lista = new ListaEnlazadaPropia<String>();

        assertThrows(IndexOutOfBoundsException.class, new ExecutableAssertion() {
            @Override
            public void execute() {
                lista.obtener(0);
            }
        });
    }

    @Test
    void insertarIndiceInvalidoLanzaExcepcion() {
        ListaEnlazadaPropia<String> lista = new ListaEnlazadaPropia<String>();

        assertThrows(IndexOutOfBoundsException.class, new ExecutableAssertion() {
            @Override
            public void execute() {
                lista.insertar(1, "x");
            }
        });
    }

    private interface ExecutableAssertion extends org.junit.jupiter.api.function.Executable {
    }
}

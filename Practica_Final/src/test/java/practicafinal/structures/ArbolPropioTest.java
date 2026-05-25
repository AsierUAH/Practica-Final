package practicafinal.structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArbolPropioTest {

    @Test
    void arbolNuevoEstaVacio() {
        ArbolPropio<String> arbol = new ArbolPropio<String>();

        assertTrue(arbol.estaVacio());
        assertEquals(0, arbol.tamano());
    }

    @Test
    void creaRaiz() {
        ArbolPropio<String> arbol = new ArbolPropio<String>();

        arbol.crearRaiz("acciones");

        assertFalse(arbol.estaVacio());
        assertEquals("acciones", arbol.obtenerRaiz());
        assertEquals(1, arbol.tamano());
    }

    @Test
    void agregaHijosYLosObtiene() {
        ArbolPropio<String> arbol = new ArbolPropio<String>();
        arbol.crearRaiz("turno");

        arbol.agregarHijo("turno", "mover");
        arbol.agregarHijo("turno", "accion");

        ListaEnlazadaPropia<String> hijos = arbol.obtenerHijos("turno");
        assertEquals(2, hijos.tamano());
        assertTrue(hijos.contiene("mover"));
        assertTrue(hijos.contiene("accion"));
    }

    @Test
    void permiteNivelesAnidadosParaAcciones() {
        ArbolPropio<String> arbol = new ArbolPropio<String>();
        arbol.crearRaiz("turno");

        arbol.agregarHijo("turno", "accion");
        arbol.agregarHijo("accion", "atacar");
        arbol.agregarHijo("accion", "recoger");

        assertTrue(arbol.contiene("atacar"));
        assertTrue(arbol.contiene("recoger"));
        assertEquals(4, arbol.tamano());
    }

    @Test
    void recorreEnPreorden() {
        ArbolPropio<String> arbol = new ArbolPropio<String>();
        arbol.crearRaiz("turno");
        arbol.agregarHijo("turno", "mover");
        arbol.agregarHijo("turno", "accion");

        ListaEnlazadaPropia<String> recorrido = arbol.recorrerPreorden();

        assertEquals("turno", recorrido.obtener(0));
        assertEquals("mover", recorrido.obtener(1));
        assertEquals("accion", recorrido.obtener(2));
    }

    @Test
    void agregarHijoSinRaizLanzaExcepcion() {
        final ArbolPropio<String> arbol = new ArbolPropio<String>();

        assertThrows(IllegalStateException.class, new org.junit.jupiter.api.function.Executable() {
            @Override
            public void execute() {
                arbol.agregarHijo("turno", "mover");
            }
        });
    }

    @Test
    void agregarHijoAPadreInexistenteLanzaExcepcion() {
        final ArbolPropio<String> arbol = new ArbolPropio<String>();
        arbol.crearRaiz("turno");

        assertThrows(IllegalArgumentException.class, new org.junit.jupiter.api.function.Executable() {
            @Override
            public void execute() {
                arbol.agregarHijo("no existe", "mover");
            }
        });
    }
}

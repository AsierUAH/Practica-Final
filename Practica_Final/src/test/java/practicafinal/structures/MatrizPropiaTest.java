package practicafinal.structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MatrizPropiaTest {

    @Test
    void creaMatrizConDimensionesValidas() {
        MatrizPropia<String> matriz = new MatrizPropia<String>(2, 3);

        assertEquals(2, matriz.getFilas());
        assertEquals(3, matriz.getColumnas());
        assertTrue(matriz.esCoordenadaValida(1, 2));
    }

    @Test
    void celdasInicialesSonNulas() {
        MatrizPropia<String> matriz = new MatrizPropia<String>(2, 2);

        assertEquals(null, matriz.obtener(0, 0));
        assertEquals(null, matriz.obtener(1, 1));
    }

    @Test
    void estableceYObtieneValores() {
        MatrizPropia<String> matriz = new MatrizPropia<String>(3, 3);

        matriz.establecer(1, 2, "puerta");
        matriz.establecer(0, 0, "jugador");

        assertEquals("puerta", matriz.obtener(1, 2));
        assertEquals("jugador", matriz.obtener(0, 0));
        assertEquals(null, matriz.obtener(2, 2));
    }

    @Test
    void sobrescribeValorSinCambiarDimensiones() {
        MatrizPropia<String> matriz = new MatrizPropia<String>(1, 2);

        matriz.establecer(0, 1, "objeto");
        matriz.establecer(0, 1, "enemigo");

        assertEquals("enemigo", matriz.obtener(0, 1));
        assertEquals(1, matriz.getFilas());
        assertEquals(2, matriz.getColumnas());
    }

    @Test
    void detectaCoordenadasInvalidas() {
        MatrizPropia<String> matriz = new MatrizPropia<String>(2, 2);

        assertFalse(matriz.esCoordenadaValida(-1, 0));
        assertFalse(matriz.esCoordenadaValida(0, -1));
        assertFalse(matriz.esCoordenadaValida(2, 0));
        assertFalse(matriz.esCoordenadaValida(0, 2));
    }

    @Test
    void obtenerCoordenadaInvalidaLanzaExcepcion() {
        final MatrizPropia<String> matriz = new MatrizPropia<String>(2, 2);

        assertThrows(IndexOutOfBoundsException.class, new org.junit.jupiter.api.function.Executable() {
            @Override
            public void execute() {
                matriz.obtener(2, 0);
            }
        });
    }

    @Test
    void dimensionesNoPositivasLanzanExcepcion() {
        assertThrows(IllegalArgumentException.class, new org.junit.jupiter.api.function.Executable() {
            @Override
            public void execute() {
                new MatrizPropia<String>(0, 2);
            }
        });
    }
}

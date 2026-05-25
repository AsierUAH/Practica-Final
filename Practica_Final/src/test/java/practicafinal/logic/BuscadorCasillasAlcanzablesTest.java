package practicafinal.logic;

import org.junit.jupiter.api.Test;
import practicafinal.structures.ListaEnlazadaPropia;
import practicafinal.structures.MatrizPropia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BuscadorCasillasAlcanzablesTest {

    @Test
    void movimientoUnoAlcanzaSoloCasillasOrtogonales() {
        MatrizPropia<Boolean> transitables = matrizTransitable(3, 3);
        BuscadorCasillasAlcanzables buscador = new BuscadorCasillasAlcanzables();

        ListaEnlazadaPropia<Coordenada> resultado = buscador.calcular(transitables, 1, 1, 1);

        assertEquals(4, resultado.tamano());
        assertTrue(contiene(resultado, 0, 1));
        assertTrue(contiene(resultado, 2, 1));
        assertTrue(contiene(resultado, 1, 0));
        assertTrue(contiene(resultado, 1, 2));
        assertFalse(contiene(resultado, 0, 0));
    }

    @Test
    void respetaLimiteDeMovimiento() {
        MatrizPropia<Boolean> transitables = matrizTransitable(5, 5);
        BuscadorCasillasAlcanzables buscador = new BuscadorCasillasAlcanzables();

        ListaEnlazadaPropia<Coordenada> resultado = buscador.calcular(transitables, 2, 2, 2);

        assertTrue(contiene(resultado, 0, 2));
        assertTrue(contiene(resultado, 2, 0));
        assertFalse(contiene(resultado, 0, 0));
    }

    @Test
    void evitaCasillasNoTransitables() {
        MatrizPropia<Boolean> transitables = matrizTransitable(3, 3);
        transitables.establecer(1, 2, Boolean.FALSE);
        BuscadorCasillasAlcanzables buscador = new BuscadorCasillasAlcanzables();

        ListaEnlazadaPropia<Coordenada> resultado = buscador.calcular(transitables, 1, 1, 1);

        assertFalse(contiene(resultado, 1, 2));
        assertEquals(3, resultado.tamano());
    }

    @Test
    void movimientoCeroNoDevuelveDestinos() {
        MatrizPropia<Boolean> transitables = matrizTransitable(3, 3);
        BuscadorCasillasAlcanzables buscador = new BuscadorCasillasAlcanzables();

        ListaEnlazadaPropia<Coordenada> resultado = buscador.calcular(transitables, 1, 1, 0);

        assertTrue(resultado.estaVacia());
    }

    @Test
    void posicionInicialInvalidaLanzaExcepcion() {
        final MatrizPropia<Boolean> transitables = matrizTransitable(2, 2);
        final BuscadorCasillasAlcanzables buscador = new BuscadorCasillasAlcanzables();

        assertThrows(IndexOutOfBoundsException.class, new org.junit.jupiter.api.function.Executable() {
            @Override
            public void execute() {
                buscador.calcular(transitables, 2, 0, 1);
            }
        });
    }

    @Test
    void movimientoNegativoLanzaExcepcion() {
        final MatrizPropia<Boolean> transitables = matrizTransitable(2, 2);
        final BuscadorCasillasAlcanzables buscador = new BuscadorCasillasAlcanzables();

        assertThrows(IllegalArgumentException.class, new org.junit.jupiter.api.function.Executable() {
            @Override
            public void execute() {
                buscador.calcular(transitables, 0, 0, -1);
            }
        });
    }

    @Test
    void matrizNulaLanzaExcepcion() {
        final BuscadorCasillasAlcanzables buscador = new BuscadorCasillasAlcanzables();

        assertThrows(IllegalArgumentException.class, new org.junit.jupiter.api.function.Executable() {
            @Override
            public void execute() {
                buscador.calcular(null, 0, 0, 1);
            }
        });
    }

    private MatrizPropia<Boolean> matrizTransitable(int filas, int columnas) {
        MatrizPropia<Boolean> matriz = new MatrizPropia<Boolean>(filas, columnas);
        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                matriz.establecer(fila, columna, Boolean.TRUE);
            }
        }
        return matriz;
    }

    private boolean contiene(ListaEnlazadaPropia<Coordenada> coordenadas, int fila, int columna) {
        for (int i = 0; i < coordenadas.tamano(); i++) {
            if (coordenadas.obtener(i).mismaPosicion(fila, columna)) {
                return true;
            }
        }
        return false;
    }
}

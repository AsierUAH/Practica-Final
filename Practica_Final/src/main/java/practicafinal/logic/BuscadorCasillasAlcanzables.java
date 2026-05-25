package practicafinal.logic;

import practicafinal.structures.ColaPropia;
import practicafinal.structures.ListaEnlazadaPropia;
import practicafinal.structures.MatrizPropia;

/**
 * Calcula casillas alcanzables con BFS usando solo movimiento ortogonal.
 */
public class BuscadorCasillasAlcanzables {

    public ListaEnlazadaPropia<Coordenada> calcular(MatrizPropia<Boolean> transitables,
                                                     int filaInicial,
                                                     int columnaInicial,
                                                     int movimientoMaximo) {
        if (transitables == null) {
            throw new IllegalArgumentException("La matriz de transitabilidad no puede ser nula");
        }
        if (!transitables.esCoordenadaValida(filaInicial, columnaInicial)) {
            throw new IndexOutOfBoundsException("La posicion inicial no es valida");
        }
        if (movimientoMaximo < 0) {
            throw new IllegalArgumentException("El movimiento maximo no puede ser negativo");
        }

        ListaEnlazadaPropia<Coordenada> alcanzables = new ListaEnlazadaPropia<Coordenada>();
        MatrizPropia<Boolean> visitadas = new MatrizPropia<Boolean>(transitables.getFilas(), transitables.getColumnas());
        ColaPropia<NodoBusquedaCasilla> pendientes = new ColaPropia<NodoBusquedaCasilla>();

        visitadas.establecer(filaInicial, columnaInicial, Boolean.TRUE);
        pendientes.encolar(new NodoBusquedaCasilla(filaInicial, columnaInicial, 0));

        while (!pendientes.estaVacia()) {
            NodoBusquedaCasilla actual = pendientes.desencolar();
            if (actual.getDistancia() < movimientoMaximo) {
                explorarVecino(transitables, visitadas, pendientes, alcanzables, actual, actual.getFila() - 1, actual.getColumna());
                explorarVecino(transitables, visitadas, pendientes, alcanzables, actual, actual.getFila() + 1, actual.getColumna());
                explorarVecino(transitables, visitadas, pendientes, alcanzables, actual, actual.getFila(), actual.getColumna() - 1);
                explorarVecino(transitables, visitadas, pendientes, alcanzables, actual, actual.getFila(), actual.getColumna() + 1);
            }
        }

        return alcanzables;
    }

    private void explorarVecino(MatrizPropia<Boolean> transitables,
                                MatrizPropia<Boolean> visitadas,
                                ColaPropia<NodoBusquedaCasilla> pendientes,
                                ListaEnlazadaPropia<Coordenada> alcanzables,
                                NodoBusquedaCasilla actual,
                                int fila,
                                int columna) {
        if (!transitables.esCoordenadaValida(fila, columna)) {
            return;
        }
        if (Boolean.TRUE.equals(visitadas.obtener(fila, columna))) {
            return;
        }
        if (!Boolean.TRUE.equals(transitables.obtener(fila, columna))) {
            return;
        }

        int distancia = actual.getDistancia() + 1;
        visitadas.establecer(fila, columna, Boolean.TRUE);
        alcanzables.agregar(new Coordenada(fila, columna));
        pendientes.encolar(new NodoBusquedaCasilla(fila, columna, distancia));
    }
}

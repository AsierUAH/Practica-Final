package practicafinal.logic;

class NodoBusquedaCasilla {

    private final int fila;
    private final int columna;
    private final int distancia;

    NodoBusquedaCasilla(int fila, int columna, int distancia) {
        this.fila = fila;
        this.columna = columna;
        this.distancia = distancia;
    }

    int getFila() {
        return fila;
    }

    int getColumna() {
        return columna;
    }

    int getDistancia() {
        return distancia;
    }
}

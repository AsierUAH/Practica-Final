package practicafinal.logic;

public class Coordenada {

    private final int fila;
    private final int columna;

    public Coordenada(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public boolean mismaPosicion(int otraFila, int otraColumna) {
        return fila == otraFila && columna == otraColumna;
    }
}

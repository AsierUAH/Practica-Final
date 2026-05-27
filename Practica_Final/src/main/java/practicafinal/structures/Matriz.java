package practicafinal.structures;

public class Matriz<T> {
    private final T[][] datos;
    private final int filas;
    private final int columnas;

    @SuppressWarnings("unchecked")
    public Matriz(int filas, int columnas) {
        if (filas <= 0 || columnas <= 0)
            throw new IllegalArgumentException("Dimensiones deben ser positivas");
        this.filas = filas;
        this.columnas = columnas;
        this.datos = (T[][]) new Object[filas][columnas];
    }

    public void set(int fila, int columna, T valor) {
        verificarIndices(fila, columna);
        datos[fila][columna] = valor;
    }

    public T get(int fila, int columna) {
        verificarIndices(fila, columna);
        return datos[fila][columna];
    }

    public boolean dentroDeLimites(int fila, int columna) {
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
    }

    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }

    private void verificarIndices(int fila, int columna) {
        if (!dentroDeLimites(fila, columna))
            throw new IndexOutOfBoundsException("Indices (" + fila + "," + columna + ") fuera de limites [" + filas + "x" + columnas + "]");
    }
}

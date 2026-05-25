package practicafinal.structures;

/**
 * Matriz propia sin arrays nativos, basada en listas enlazadas propias.
 *
 * Costes principales:
 * obtener/establecer: O(fila + columna), validar coordenada, filas y columnas: O(1).
 */
public class MatrizPropia<T> {

    private final int filas;
    private final int columnas;
    private final ListaEnlazadaPropia<ListaEnlazadaPropia<T>> datos;

    public MatrizPropia(int filas, int columnas) {
        if (filas <= 0 || columnas <= 0) {
            throw new IllegalArgumentException("La matriz debe tener dimensiones positivas");
        }
        this.filas = filas;
        this.columnas = columnas;
        this.datos = new ListaEnlazadaPropia<ListaEnlazadaPropia<T>>();
        inicializarDatos();
    }

    public T obtener(int fila, int columna) {
        validarCoordenada(fila, columna);
        return obtenerFila(fila).obtener(columna);
    }

    public void establecer(int fila, int columna, T valor) {
        validarCoordenada(fila, columna);
        ListaEnlazadaPropia<T> filaDatos = obtenerFila(fila);
        filaDatos.eliminar(columna);
        filaDatos.insertar(columna, valor);
    }

    public boolean esCoordenadaValida(int fila, int columna) {
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    private void inicializarDatos() {
        for (int fila = 0; fila < filas; fila++) {
            ListaEnlazadaPropia<T> filaDatos = new ListaEnlazadaPropia<T>();
            for (int columna = 0; columna < columnas; columna++) {
                filaDatos.agregar(null);
            }
            datos.agregar(filaDatos);
        }
    }

    private ListaEnlazadaPropia<T> obtenerFila(int fila) {
        return datos.obtener(fila);
    }

    private void validarCoordenada(int fila, int columna) {
        if (!esCoordenadaValida(fila, columna)) {
            throw new IndexOutOfBoundsException("Coordenada fuera de rango: (" + fila + ", " + columna + ")");
        }
    }
}

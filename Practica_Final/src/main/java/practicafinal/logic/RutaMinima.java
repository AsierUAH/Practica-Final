package practicafinal.logic;

import practicafinal.structures.ListaEnlazadaPropia;

public class RutaMinima<T> {

    private final ListaEnlazadaPropia<T> vertices;
    private final int costeTotal;

    public RutaMinima(ListaEnlazadaPropia<T> vertices, int costeTotal) {
        this.vertices = vertices;
        this.costeTotal = costeTotal;
    }

    public ListaEnlazadaPropia<T> getVertices() {
        return vertices;
    }

    public int getCosteTotal() {
        return costeTotal;
    }

    public boolean existe() {
        return costeTotal >= 0;
    }
}

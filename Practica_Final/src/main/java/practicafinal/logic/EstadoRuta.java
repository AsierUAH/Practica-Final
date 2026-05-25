package practicafinal.logic;

class EstadoRuta<T> {

    private final T vertice;
    private int distancia;
    private T anterior;
    private boolean visitado;

    EstadoRuta(T vertice) {
        this.vertice = vertice;
        this.distancia = Integer.MAX_VALUE;
    }

    T getVertice() {
        return vertice;
    }

    int getDistancia() {
        return distancia;
    }

    void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    T getAnterior() {
        return anterior;
    }

    void setAnterior(T anterior) {
        this.anterior = anterior;
    }

    boolean isVisitado() {
        return visitado;
    }

    void marcarVisitado() {
        this.visitado = true;
    }
}

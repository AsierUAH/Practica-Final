package practicafinal.structures;

/**
 * Pila propia LIFO implementada con nodos enlazados.
 *
 * Costes principales:
 * apilar, desapilar, cima, tamano y estaVacia: O(1).
 */
public class PilaPropia<T> {

    private Nodo<T> cima;
    private int tamano;

    public void apilar(T elemento) {
        Nodo<T> nuevo = new Nodo<T>(elemento);
        nuevo.siguiente = cima;
        cima = nuevo;
        tamano++;
    }

    public T desapilar() {
        asegurarNoVacia();
        T elemento = cima.elemento;
        cima = cima.siguiente;
        tamano--;
        return elemento;
    }

    public T cima() {
        asegurarNoVacia();
        return cima.elemento;
    }

    public boolean estaVacia() {
        return tamano == 0;
    }

    public int tamano() {
        return tamano;
    }

    private void asegurarNoVacia() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila esta vacia");
        }
    }

    private static class Nodo<T> {
        private final T elemento;
        private Nodo<T> siguiente;

        private Nodo(T elemento) {
            this.elemento = elemento;
        }
    }
}

package practicafinal.structures;

/**
 * Cola propia FIFO implementada con nodos enlazados.
 *
 * Costes principales:
 * encolar, desencolar, frente, tamano y estaVacia: O(1).
 */
public class ColaPropia<T> {

    private Nodo<T> frente;
    private Nodo<T> fin;
    private int tamano;

    public void encolar(T elemento) {
        Nodo<T> nuevo = new Nodo<T>(elemento);
        if (estaVacia()) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.siguiente = nuevo;
            fin = nuevo;
        }
        tamano++;
    }

    public T desencolar() {
        asegurarNoVacia();
        T elemento = frente.elemento;
        frente = frente.siguiente;
        tamano--;
        if (tamano == 0) {
            fin = null;
        }
        return elemento;
    }

    public T frente() {
        asegurarNoVacia();
        return frente.elemento;
    }

    public boolean estaVacia() {
        return tamano == 0;
    }

    public int tamano() {
        return tamano;
    }

    private void asegurarNoVacia() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola esta vacia");
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

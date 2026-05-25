package practicafinal.structures;

/**
 * Lista circular propia para recorridos ciclicos, como rotacion de enemigos.
 *
 * Costes principales:
 * agregar, siguiente, obtenerActual, tamano y estaVacia: O(1); eliminar/buscar: O(n).
 */
public class ListaCircularPropia<T> {

    private Nodo<T> actual;
    private Nodo<T> ultimo;
    private int tamano;

    public void agregar(T elemento) {
        Nodo<T> nuevo = new Nodo<T>(elemento);
        if (estaVacia()) {
            actual = nuevo;
            ultimo = nuevo;
            nuevo.siguiente = nuevo;
        } else {
            nuevo.siguiente = actual;
            ultimo.siguiente = nuevo;
            ultimo = nuevo;
        }
        tamano++;
    }

    public T obtenerActual() {
        asegurarNoVacia();
        return actual.elemento;
    }

    public T siguiente() {
        asegurarNoVacia();
        actual = actual.siguiente;
        return actual.elemento;
    }

    public boolean eliminar(T elemento) {
        if (estaVacia()) {
            return false;
        }

        Nodo<T> anterior = ultimo;
        Nodo<T> candidato = actual;
        for (int i = 0; i < tamano; i++) {
            if (sonIguales(candidato.elemento, elemento)) {
                eliminarNodo(anterior, candidato);
                return true;
            }
            anterior = candidato;
            candidato = candidato.siguiente;
        }
        return false;
    }

    public boolean contiene(T elemento) {
        if (estaVacia()) {
            return false;
        }

        Nodo<T> candidato = actual;
        for (int i = 0; i < tamano; i++) {
            if (sonIguales(candidato.elemento, elemento)) {
                return true;
            }
            candidato = candidato.siguiente;
        }
        return false;
    }

    public boolean estaVacia() {
        return tamano == 0;
    }

    public int tamano() {
        return tamano;
    }

    private void eliminarNodo(Nodo<T> anterior, Nodo<T> candidato) {
        if (tamano == 1) {
            actual = null;
            ultimo = null;
        } else {
            anterior.siguiente = candidato.siguiente;
            if (candidato == actual) {
                actual = candidato.siguiente;
            }
            if (candidato == ultimo) {
                ultimo = anterior;
            }
        }
        tamano--;
    }

    private void asegurarNoVacia() {
        if (estaVacia()) {
            throw new IllegalStateException("La lista circular esta vacia");
        }
    }

    private boolean sonIguales(T primerElemento, T segundoElemento) {
        if (primerElemento == null) {
            return segundoElemento == null;
        }
        return primerElemento.equals(segundoElemento);
    }

    private static class Nodo<T> {
        private final T elemento;
        private Nodo<T> siguiente;

        private Nodo(T elemento) {
            this.elemento = elemento;
        }
    }
}

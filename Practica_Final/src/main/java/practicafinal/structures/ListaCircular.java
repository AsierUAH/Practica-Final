package practicafinal.structures;

public class ListaCircular<T> {
    private Nodo<T> cabeza;
    private int tamano;

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) { this.dato = dato; }
    }

    public ListaCircular() {
        this.cabeza = null;
        this.tamano = 0;
    }

    public void agregar(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);
        if (cabeza == null) {
            cabeza = nuevo;
            nuevo.siguiente = cabeza;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != cabeza) actual = actual.siguiente;
            actual.siguiente = nuevo;
            nuevo.siguiente = cabeza;
        }
        tamano++;
    }

    public T obtener(int indice) {
        if (tamano == 0) throw new IndexOutOfBoundsException("Lista vacia");
        int idx = indice % tamano;
        if (idx < 0) idx += tamano;
        Nodo<T> actual = cabeza;
        for (int i = 0; i < idx; i++) actual = actual.siguiente;
        return actual.dato;
    }

    public T eliminar(int indice) {
        if (tamano == 0) throw new IndexOutOfBoundsException("Lista vacia");
        int idx = indice % tamano;
        if (idx < 0) idx += tamano;
        Nodo<T> eliminar;
        if (indice == 0) {
            eliminar = cabeza;
            if (tamano == 1) {
                cabeza = null;
            } else {
                Nodo<T> ultimo = cabeza;
                while (ultimo.siguiente != cabeza) ultimo = ultimo.siguiente;
                cabeza = cabeza.siguiente;
                ultimo.siguiente = cabeza;
            }
        } else {
            Nodo<T> anterior = cabeza;
            for (int i = 0; i < indice - 1; i++) anterior = anterior.siguiente;
            eliminar = anterior.siguiente;
            anterior.siguiente = eliminar.siguiente;
        }
        tamano--;
        return eliminar.dato;
    }

    public boolean contiene(T elemento) {
        if (cabeza == null) return false;
        Nodo<T> actual = cabeza;
        do {
            if (actual.dato.equals(elemento)) return true;
            actual = actual.siguiente;
        } while (actual != cabeza);
        return false;
    }

    public boolean estaVacia() { return cabeza == null; }
    public int tamano() { return tamano; }

    public void vaciar() {
        cabeza = null;
        tamano = 0;
    }

    private void verificarIndice(int indice) {
        if (indice < 0 || indice >= tamano)
            throw new IndexOutOfBoundsException("Indice " + indice + " fuera de rango [0, " + (tamano - 1) + "]");
    }
}

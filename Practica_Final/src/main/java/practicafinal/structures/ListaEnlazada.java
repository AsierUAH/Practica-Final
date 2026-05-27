package practicafinal.structures;

public class ListaEnlazada<T> {
    private Nodo<T> cabeza;
    private int tamano;

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) { this.dato = dato; }
    }

    public ListaEnlazada() {
        this.cabeza = null;
        this.tamano = 0;
    }

    public void agregar(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != null) actual = actual.siguiente;
            actual.siguiente = nuevo;
        }
        tamano++;
    }

    public T obtener(int indice) {
        verificarIndice(indice);
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) actual = actual.siguiente;
        return actual.dato;
    }

    public T eliminar(int indice) {
        verificarIndice(indice);
        Nodo<T> eliminar;
        if (indice == 0) {
            eliminar = cabeza;
            cabeza = cabeza.siguiente;
        } else {
            Nodo<T> anterior = cabeza;
            for (int i = 0; i < indice - 1; i++) anterior = anterior.siguiente;
            eliminar = anterior.siguiente;
            anterior.siguiente = eliminar.siguiente;
        }
        tamano--;
        return eliminar.dato;
    }

    public boolean eliminar(T elemento) {
        if (cabeza == null) return false;
        if (cabeza.dato.equals(elemento)) {
            cabeza = cabeza.siguiente;
            tamano--;
            return true;
        }
        Nodo<T> actual = cabeza;
        while (actual.siguiente != null) {
            if (actual.siguiente.dato.equals(elemento)) {
                actual.siguiente = actual.siguiente.siguiente;
                tamano--;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public boolean contiene(T elemento) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (actual.dato.equals(elemento)) return true;
            actual = actual.siguiente;
        }
        return false;
    }

    public boolean estaVacia() { return cabeza == null; }
    public int tamano() { return tamano; }

    public void vaciar() {
        cabeza = null;
        tamano = 0;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] resultado = (T[]) new Object[tamano];
        Nodo<T> actual = cabeza;
        for (int i = 0; i < tamano; i++) {
            resultado[i] = actual.dato;
            actual = actual.siguiente;
        }
        return resultado;
    }

    private void verificarIndice(int indice) {
        if (indice < 0 || indice >= tamano)
            throw new IndexOutOfBoundsException("Indice " + indice + " fuera de rango [0, " + (tamano - 1) + "]");
    }
}

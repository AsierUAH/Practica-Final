package practicafinal.structures;

public class Cola<T> {
    private Nodo<T> frente;
    private Nodo<T> fin;
    private int tamano;

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) { this.dato = dato; this.siguiente = null; }
    }

    public Cola() {
        this.frente = null;
        this.fin = null;
        this.tamano = 0;
    }

    public void enqueue(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);
        if (estaVacia()) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.siguiente = nuevo;
            fin = nuevo;
        }
        tamano++;
    }

    public T dequeue() {
        if (estaVacia()) return null;
        T dato = frente.dato;
        frente = frente.siguiente;
        if (frente == null) fin = null;
        tamano--;
        return dato;
    }

    public T peek() {
        if (estaVacia()) return null;
        return frente.dato;
    }

    public boolean estaVacia() { return frente == null; }
    public int tamano() { return tamano; }

    public void vaciar() {
        frente = null;
        fin = null;
        tamano = 0;
    }
}

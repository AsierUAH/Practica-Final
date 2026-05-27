package practicafinal.structures;

public class Pila<T> {
    private Nodo<T> cima;
    private int tamano;

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) { this.dato = dato; }
    }

    public Pila() {
        this.cima = null;
        this.tamano = 0;
    }

    public void push(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);
        nuevo.siguiente = cima;
        cima = nuevo;
        tamano++;
    }

    public T pop() {
        if (estaVacia()) return null;
        T dato = cima.dato;
        cima = cima.siguiente;
        tamano--;
        return dato;
    }

    public T peek() {
        if (estaVacia()) return null;
        return cima.dato;
    }

    public boolean estaVacia() { return cima == null; }
    public int tamano() { return tamano; }

    public void vaciar() {
        cima = null;
        tamano = 0;
    }
}

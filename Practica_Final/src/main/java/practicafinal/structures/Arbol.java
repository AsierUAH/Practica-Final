package practicafinal.structures;

public class Arbol<T> {
    private Nodo<T> raiz;

    private static class Nodo<T> {
        T dato;
        ListaEnlazada<Nodo<T>> hijos;

        Nodo(T dato) {
            this.dato = dato;
            this.hijos = new ListaEnlazada<>();
        }
    }

    public Arbol(T datoRaiz) {
        this.raiz = new Nodo<>(datoRaiz);
    }

    public T getRaiz() { return raiz != null ? raiz.dato : null; }

    public void setRaiz(T dato) {
        if (raiz == null) raiz = new Nodo<>(dato);
        else raiz.dato = dato;
    }

    public boolean agregarHijo(T padre, T hijo) {
        Nodo<T> nodoPadre = buscar(padre);
        if (nodoPadre == null) return false;
        nodoPadre.hijos.agregar(new Nodo<>(hijo));
        return true;
    }

    public ListaEnlazada<T> obtenerHijos(T padre) {
        Nodo<T> nodo = buscar(padre);
        ListaEnlazada<T> resultado = new ListaEnlazada<>();
        if (nodo == null) return resultado;
        for (int i = 0; i < nodo.hijos.tamano(); i++)
            resultado.agregar(nodo.hijos.obtener(i).dato);
        return resultado;
    }

    public boolean contiene(T dato) { return buscar(dato) != null; }
    public boolean estaVacio() { return raiz == null; }

    public void vaciar() { raiz = null; }

    public ListaEnlazada<T> recorrerPreorden() {
        ListaEnlazada<T> resultado = new ListaEnlazada<>();
        preorden(raiz, resultado);
        return resultado;
    }

    private void preorden(Nodo<T> nodo, ListaEnlazada<T> resultado) {
        if (nodo == null) return;
        resultado.agregar(nodo.dato);
        for (int i = 0; i < nodo.hijos.tamano(); i++)
            preorden(nodo.hijos.obtener(i), resultado);
    }

    private Nodo<T> buscar(T dato) { return buscarRec(raiz, dato); }

    private Nodo<T> buscarRec(Nodo<T> nodo, T dato) {
        if (nodo == null) return null;
        if (nodo.dato.equals(dato)) return nodo;
        for (int i = 0; i < nodo.hijos.tamano(); i++) {
            Nodo<T> encontrado = buscarRec(nodo.hijos.obtener(i), dato);
            if (encontrado != null) return encontrado;
        }
        return null;
    }
}

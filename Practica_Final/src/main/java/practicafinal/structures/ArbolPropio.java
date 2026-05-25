package practicafinal.structures;

/**
 * Arbol general propio para organizar acciones posibles del jugador.
 *
 * Costes principales:
 * crear raiz, obtener raiz, tamano y estaVacio: O(1); buscar/agregar hijo/recorrer: O(n).
 */
public class ArbolPropio<T> {

    private Nodo<T> raiz;
    private int tamano;

    public void crearRaiz(T valor) {
        raiz = new Nodo<T>(valor);
        tamano = 1;
    }

    public T obtenerRaiz() {
        asegurarNoVacio();
        return raiz.valor;
    }

    public void agregarHijo(T valorPadre, T valorHijo) {
        asegurarNoVacio();
        Nodo<T> padre = buscarNodo(raiz, valorPadre);
        if (padre == null) {
            throw new IllegalArgumentException("No existe el padre indicado");
        }
        padre.hijos.agregar(new Nodo<T>(valorHijo));
        tamano++;
    }

    public ListaEnlazadaPropia<T> obtenerHijos(T valorPadre) {
        asegurarNoVacio();
        Nodo<T> padre = buscarNodo(raiz, valorPadre);
        if (padre == null) {
            throw new IllegalArgumentException("No existe el padre indicado");
        }

        ListaEnlazadaPropia<T> hijos = new ListaEnlazadaPropia<T>();
        for (int i = 0; i < padre.hijos.tamano(); i++) {
            hijos.agregar(padre.hijos.obtener(i).valor);
        }
        return hijos;
    }

    public boolean contiene(T valor) {
        return !estaVacio() && buscarNodo(raiz, valor) != null;
    }

    public ListaEnlazadaPropia<T> recorrerPreorden() {
        ListaEnlazadaPropia<T> recorrido = new ListaEnlazadaPropia<T>();
        if (!estaVacio()) {
            recorrerPreorden(raiz, recorrido);
        }
        return recorrido;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    public int tamano() {
        return tamano;
    }

    private void recorrerPreorden(Nodo<T> nodo, ListaEnlazadaPropia<T> recorrido) {
        recorrido.agregar(nodo.valor);
        for (int i = 0; i < nodo.hijos.tamano(); i++) {
            recorrerPreorden(nodo.hijos.obtener(i), recorrido);
        }
    }

    private Nodo<T> buscarNodo(Nodo<T> nodo, T valor) {
        if (sonIguales(nodo.valor, valor)) {
            return nodo;
        }
        for (int i = 0; i < nodo.hijos.tamano(); i++) {
            Nodo<T> encontrado = buscarNodo(nodo.hijos.obtener(i), valor);
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;
    }

    private void asegurarNoVacio() {
        if (estaVacio()) {
            throw new IllegalStateException("El arbol esta vacio");
        }
    }

    private boolean sonIguales(T primerValor, T segundoValor) {
        if (primerValor == null) {
            return segundoValor == null;
        }
        return primerValor.equals(segundoValor);
    }

    private static class Nodo<T> {
        private final T valor;
        private final ListaEnlazadaPropia<Nodo<T>> hijos;

        private Nodo(T valor) {
            this.valor = valor;
            this.hijos = new ListaEnlazadaPropia<Nodo<T>>();
        }
    }
}

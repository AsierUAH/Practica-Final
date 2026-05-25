package practicafinal.structures;

/**
 * Lista enlazada simple propia para las partes evaluadas de Estructuras de Datos.
 *
 * Costes principales:
 * agregar al final: O(1), obtener/eliminar por indice: O(n), buscar: O(n), tamano: O(1).
 */
public class ListaEnlazadaPropia<T> {

    private Nodo<T> primero;
    private Nodo<T> ultimo;
    private int tamano;

    public void agregar(T elemento) {
        Nodo<T> nuevo = new Nodo<T>(elemento);
        if (estaVacia()) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            ultimo.siguiente = nuevo;
            ultimo = nuevo;
        }
        tamano++;
    }

    public void insertar(int indice, T elemento) {
        validarIndiceInsercion(indice);

        if (indice == tamano) {
            agregar(elemento);
            return;
        }

        Nodo<T> nuevo = new Nodo<T>(elemento);
        if (indice == 0) {
            nuevo.siguiente = primero;
            primero = nuevo;
            if (ultimo == null) {
                ultimo = nuevo;
            }
        } else {
            Nodo<T> anterior = nodoEn(indice - 1);
            nuevo.siguiente = anterior.siguiente;
            anterior.siguiente = nuevo;
        }
        tamano++;
    }

    public T obtener(int indice) {
        validarIndiceExistente(indice);
        return nodoEn(indice).elemento;
    }

    public T eliminar(int indice) {
        validarIndiceExistente(indice);

        T eliminado;
        if (indice == 0) {
            eliminado = primero.elemento;
            primero = primero.siguiente;
            if (tamano == 1) {
                ultimo = null;
            }
        } else {
            Nodo<T> anterior = nodoEn(indice - 1);
            Nodo<T> actual = anterior.siguiente;
            eliminado = actual.elemento;
            anterior.siguiente = actual.siguiente;
            if (actual == ultimo) {
                ultimo = anterior;
            }
        }
        tamano--;
        return eliminado;
    }

    public boolean eliminarElemento(T elemento) {
        int indice = indiceDe(elemento);
        if (indice == -1) {
            return false;
        }
        eliminar(indice);
        return true;
    }

    public boolean contiene(T elemento) {
        return indiceDe(elemento) != -1;
    }

    public int indiceDe(T elemento) {
        Nodo<T> actual = primero;
        int indice = 0;
        while (actual != null) {
            if (sonIguales(actual.elemento, elemento)) {
                return indice;
            }
            actual = actual.siguiente;
            indice++;
        }
        return -1;
    }

    public int tamano() {
        return tamano;
    }

    public boolean estaVacia() {
        return tamano == 0;
    }

    private Nodo<T> nodoEn(int indice) {
        Nodo<T> actual = primero;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }
        return actual;
    }

    private void validarIndiceExistente(int indice) {
        if (indice < 0 || indice >= tamano) {
            throw new IndexOutOfBoundsException("Indice fuera de rango: " + indice);
        }
    }

    private void validarIndiceInsercion(int indice) {
        if (indice < 0 || indice > tamano) {
            throw new IndexOutOfBoundsException("Indice de insercion fuera de rango: " + indice);
        }
    }

    private boolean sonIguales(T primeroElemento, T segundoElemento) {
        if (primeroElemento == null) {
            return segundoElemento == null;
        }
        return primeroElemento.equals(segundoElemento);
    }

    private static class Nodo<T> {
        private final T elemento;
        private Nodo<T> siguiente;

        private Nodo(T elemento) {
            this.elemento = elemento;
        }
    }
}

package practicafinal.structures;

public class Grafo<T> {
    private ListaEnlazada<Vertice<T>> vertices;

    private static class Vertice<T> {
        T dato;
        ListaEnlazada<Vertice<T>> adyacentes;

        Vertice(T dato) {
            this.dato = dato;
            this.adyacentes = new ListaEnlazada<>();
        }
    }

    public Grafo() {
        this.vertices = new ListaEnlazada<>();
    }

    public void agregarVertice(T dato) {
        if (contiene(dato)) return;
        vertices.agregar(new Vertice<>(dato));
    }

    public void agregarArista(T origen, T destino) {
        Vertice<T> vOrigen = buscar(origen);
        Vertice<T> vDestino = buscar(destino);
        if (vOrigen == null || vDestino == null) return;
        if (!tieneArista(vOrigen, destino))
            vOrigen.adyacentes.agregar(vDestino);
        if (!tieneArista(vDestino, origen))
            vDestino.adyacentes.agregar(vOrigen);
    }

    public boolean contiene(T dato) { return buscar(dato) != null; }

    public ListaEnlazada<T> obtenerVecinos(T dato) {
        Vertice<T> v = buscar(dato);
        ListaEnlazada<T> resultado = new ListaEnlazada<>();
        if (v == null) return resultado;
        for (int i = 0; i < v.adyacentes.tamano(); i++)
            resultado.agregar(v.adyacentes.obtener(i).dato);
        return resultado;
    }

    public ListaEnlazada<T> obtenerVertices() {
        ListaEnlazada<T> resultado = new ListaEnlazada<>();
        for (int i = 0; i < vertices.tamano(); i++)
            resultado.agregar(vertices.obtener(i).dato);
        return resultado;
    }

    public int tamano() { return vertices.tamano(); }
    public boolean estaVacio() { return vertices.estaVacia(); }

    public ListaEnlazada<T> bfs(T inicio, T objetivo) {
        ListaEnlazada<T> resultado = new ListaEnlazada<>();
        if (!contiene(inicio)) return resultado;

        Cola<Vertice<T>> cola = new Cola<>();
        ListaEnlazada<T> visitados = new ListaEnlazada<>();
        ListaEnlazada<Vertice<T>> padre = new ListaEnlazada<>();
        ListaEnlazada<T> datoPadre = new ListaEnlazada<>();

        Vertice<T> vInicio = buscar(inicio);
        cola.enqueue(vInicio);
        visitados.agregar(inicio);
        padre.agregar(null);
        datoPadre.agregar(null);

        while (!cola.estaVacia()) {
            Vertice<T> actual = cola.dequeue();
            T datoActual = actual.dato;

            if (datoActual.equals(objetivo)) {
                Pila<T> pila = new Pila<>();
                T paso = objetivo;
                while (paso != null) {
                    pila.push(paso);
                    int idx = indiceDe(visitados, paso);
                    paso = (idx >= 0 && idx < datoPadre.tamano()) ? datoPadre.obtener(idx) : null;
                }
                while (!pila.estaVacia())
                    resultado.agregar(pila.pop());
                return resultado;
            }

            for (int i = 0; i < actual.adyacentes.tamano(); i++) {
                Vertice<T> vecino = actual.adyacentes.obtener(i);
                if (!visitados.contiene(vecino.dato)) {
                    visitados.agregar(vecino.dato);
                    cola.enqueue(vecino);
                    padre.agregar(actual);
                    datoPadre.agregar(datoActual);
                }
            }
        }
        return resultado;
    }

    private Vertice<T> buscar(T dato) {
        for (int i = 0; i < vertices.tamano(); i++) {
            Vertice<T> v = vertices.obtener(i);
            if (v.dato.equals(dato)) return v;
        }
        return null;
    }

    private boolean tieneArista(Vertice<T> origen, T destino) {
        for (int i = 0; i < origen.adyacentes.tamano(); i++) {
            if (origen.adyacentes.obtener(i).dato.equals(destino)) return true;
        }
        return false;
    }

    private int indiceDe(ListaEnlazada<T> lista, T dato) {
        for (int i = 0; i < lista.tamano(); i++) {
            if (lista.obtener(i) != null && lista.obtener(i).equals(dato)) return i;
            if (lista.obtener(i) == null && dato == null) return i;
        }
        return -1;
    }
}

package practicafinal.structures;

/**
 * Grafo propio ponderado y no dirigido para el mapa de habitaciones.
 *
 * Costes principales:
 * agregar/existe vertice: O(v), agregar/existe arista: O(v + aNodo), obtener vecinos: O(v + aNodo).
 */
public class GrafoPropio<T> {

    private final ListaEnlazadaPropia<Vertice<T>> vertices;

    public GrafoPropio() {
        this.vertices = new ListaEnlazadaPropia<Vertice<T>>();
    }

    public void agregarVertice(T valor) {
        validarVerticeNoNulo(valor);
        if (!existeVertice(valor)) {
            vertices.agregar(new Vertice<T>(valor));
        }
    }

    public void agregarArista(T origen, T destino, int coste) {
        validarVerticeNoNulo(origen);
        validarVerticeNoNulo(destino);
        if (coste < 0) {
            throw new IllegalArgumentException("El coste de una arista no puede ser negativo");
        }
        agregarVertice(origen);
        agregarVertice(destino);

        Vertice<T> verticeOrigen = obtenerVertice(origen);
        Vertice<T> verticeDestino = obtenerVertice(destino);
        agregarOActualizarArista(verticeOrigen, destino, coste);
        agregarOActualizarArista(verticeDestino, origen, coste);
    }

    public boolean existeVertice(T valor) {
        return obtenerVertice(valor) != null;
    }

    public boolean existeArista(T origen, T destino) {
        Vertice<T> verticeOrigen = obtenerVertice(origen);
        return verticeOrigen != null && obtenerArista(verticeOrigen, destino) != null;
    }

    public int obtenerCoste(T origen, T destino) {
        Vertice<T> verticeOrigen = obtenerVertice(origen);
        if (verticeOrigen == null) {
            throw new IllegalArgumentException("No existe el vertice origen");
        }
        Arista<T> arista = obtenerArista(verticeOrigen, destino);
        if (arista == null) {
            throw new IllegalArgumentException("No existe la arista solicitada");
        }
        return arista.coste;
    }

    public ListaEnlazadaPropia<T> obtenerVecinos(T valor) {
        Vertice<T> vertice = obtenerVertice(valor);
        if (vertice == null) {
            throw new IllegalArgumentException("No existe el vertice");
        }

        ListaEnlazadaPropia<T> vecinos = new ListaEnlazadaPropia<T>();
        for (int i = 0; i < vertice.aristas.tamano(); i++) {
            vecinos.agregar(vertice.aristas.obtener(i).destino);
        }
        return vecinos;
    }

    public ListaEnlazadaPropia<T> obtenerVertices() {
        ListaEnlazadaPropia<T> valores = new ListaEnlazadaPropia<T>();
        for (int i = 0; i < vertices.tamano(); i++) {
            valores.agregar(vertices.obtener(i).valor);
        }
        return valores;
    }

    public int numeroVertices() {
        return vertices.tamano();
    }

    private void agregarOActualizarArista(Vertice<T> origen, T destino, int coste) {
        Arista<T> arista = obtenerArista(origen, destino);
        if (arista == null) {
            origen.aristas.agregar(new Arista<T>(destino, coste));
        } else {
            arista.coste = coste;
        }
    }

    private Vertice<T> obtenerVertice(T valor) {
        for (int i = 0; i < vertices.tamano(); i++) {
            Vertice<T> vertice = vertices.obtener(i);
            if (sonIguales(vertice.valor, valor)) {
                return vertice;
            }
        }
        return null;
    }

    private Arista<T> obtenerArista(Vertice<T> origen, T destino) {
        for (int i = 0; i < origen.aristas.tamano(); i++) {
            Arista<T> arista = origen.aristas.obtener(i);
            if (sonIguales(arista.destino, destino)) {
                return arista;
            }
        }
        return null;
    }

    private boolean sonIguales(T primerValor, T segundoValor) {
        if (primerValor == null) {
            return segundoValor == null;
        }
        return primerValor.equals(segundoValor);
    }

    private void validarVerticeNoNulo(T valor) {
        if (valor == null) {
            throw new IllegalArgumentException("El vertice no puede ser nulo");
        }
    }

    private static class Vertice<T> {
        private final T valor;
        private final ListaEnlazadaPropia<Arista<T>> aristas;

        private Vertice(T valor) {
            this.valor = valor;
            this.aristas = new ListaEnlazadaPropia<Arista<T>>();
        }
    }

    private static class Arista<T> {
        private final T destino;
        private int coste;

        private Arista(T destino, int coste) {
            this.destino = destino;
            this.coste = coste;
        }
    }
}

package practicafinal.logic;

import practicafinal.structures.GrafoPropio;
import practicafinal.structures.ListaEnlazadaPropia;
import practicafinal.structures.PilaPropia;

/**
 * Calcula rutas minimas entre habitaciones con Dijkstra para grafos con costes variables.
 */
public class CalculadorRutasHabitaciones {

    public <T> RutaMinima<T> calcularRutaMinima(GrafoPropio<T> grafo, T origen, T destino) {
        if (grafo == null) {
            throw new IllegalArgumentException("El grafo no puede ser nulo");
        }
        if (!grafo.existeVertice(origen) || !grafo.existeVertice(destino)) {
            throw new IllegalArgumentException("Origen o destino inexistente");
        }

        ListaEnlazadaPropia<EstadoRuta<T>> estados = crearEstados(grafo.obtenerVertices());
        EstadoRuta<T> estadoOrigen = buscarEstado(estados, origen);
        estadoOrigen.setDistancia(0);

        EstadoRuta<T> actual = seleccionarNoVisitadoMasCercano(estados);
        while (actual != null) {
            actual.marcarVisitado();
            if (sonIguales(actual.getVertice(), destino)) {
                break;
            }
            relajarVecinos(grafo, estados, actual);
            actual = seleccionarNoVisitadoMasCercano(estados);
        }

        EstadoRuta<T> estadoDestino = buscarEstado(estados, destino);
        if (estadoDestino.getDistancia() == Integer.MAX_VALUE) {
            return new RutaMinima<T>(new ListaEnlazadaPropia<T>(), -1);
        }
        return new RutaMinima<T>(reconstruirRuta(estados, destino), estadoDestino.getDistancia());
    }

    private <T> ListaEnlazadaPropia<EstadoRuta<T>> crearEstados(ListaEnlazadaPropia<T> vertices) {
        ListaEnlazadaPropia<EstadoRuta<T>> estados = new ListaEnlazadaPropia<EstadoRuta<T>>();
        for (int i = 0; i < vertices.tamano(); i++) {
            estados.agregar(new EstadoRuta<T>(vertices.obtener(i)));
        }
        return estados;
    }

    private <T> void relajarVecinos(GrafoPropio<T> grafo, ListaEnlazadaPropia<EstadoRuta<T>> estados, EstadoRuta<T> actual) {
        ListaEnlazadaPropia<T> vecinos = grafo.obtenerVecinos(actual.getVertice());
        for (int i = 0; i < vecinos.tamano(); i++) {
            T vecino = vecinos.obtener(i);
            EstadoRuta<T> estadoVecino = buscarEstado(estados, vecino);
            if (!estadoVecino.isVisitado()) {
                int nuevaDistancia = actual.getDistancia() + grafo.obtenerCoste(actual.getVertice(), vecino);
                if (nuevaDistancia < estadoVecino.getDistancia()) {
                    estadoVecino.setDistancia(nuevaDistancia);
                    estadoVecino.setAnterior(actual.getVertice());
                }
            }
        }
    }

    private <T> ListaEnlazadaPropia<T> reconstruirRuta(ListaEnlazadaPropia<EstadoRuta<T>> estados, T destino) {
        PilaPropia<T> pila = new PilaPropia<T>();
        T actual = destino;
        while (actual != null) {
            pila.apilar(actual);
            EstadoRuta<T> estado = buscarEstado(estados, actual);
            actual = estado.getAnterior();
        }

        ListaEnlazadaPropia<T> ruta = new ListaEnlazadaPropia<T>();
        while (!pila.estaVacia()) {
            ruta.agregar(pila.desapilar());
        }
        return ruta;
    }

    private <T> EstadoRuta<T> seleccionarNoVisitadoMasCercano(ListaEnlazadaPropia<EstadoRuta<T>> estados) {
        EstadoRuta<T> mejor = null;
        for (int i = 0; i < estados.tamano(); i++) {
            EstadoRuta<T> candidato = estados.obtener(i);
            if (!candidato.isVisitado() && candidato.getDistancia() != Integer.MAX_VALUE) {
                if (mejor == null || candidato.getDistancia() < mejor.getDistancia()) {
                    mejor = candidato;
                }
            }
        }
        return mejor;
    }

    private <T> EstadoRuta<T> buscarEstado(ListaEnlazadaPropia<EstadoRuta<T>> estados, T vertice) {
        for (int i = 0; i < estados.tamano(); i++) {
            EstadoRuta<T> estado = estados.obtener(i);
            if (sonIguales(estado.getVertice(), vertice)) {
                return estado;
            }
        }
        return null;
    }

    private <T> boolean sonIguales(T primerValor, T segundoValor) {
        if (primerValor == null) {
            return segundoValor == null;
        }
        return primerValor.equals(segundoValor);
    }
}

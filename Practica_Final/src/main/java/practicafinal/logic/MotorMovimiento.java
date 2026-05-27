package practicafinal.logic;

import practicafinal.model.Celda;
import practicafinal.model.Direccion;
import practicafinal.model.Habitacion;
import practicafinal.model.TipoCelda;
import practicafinal.structures.Cola;
import practicafinal.structures.ListaEnlazada;

public class MotorMovimiento {

    public boolean esMovimientoValido(Habitacion habitacion, int filaOrigen, int colOrigen,
                                       int filaDestino, int colDestino) {
        if (!habitacion.dentroDeLimites(filaDestino, colDestino)) return false;
        if (!esMovimientoAdyacente(filaOrigen, colOrigen, filaDestino, colDestino)) return false;
        if (esDiagonal(filaOrigen, colOrigen, filaDestino, colDestino)) return false;
        Celda destino = habitacion.getCelda(filaDestino, colDestino);
        return destino.esTransitable() && !destino.estaOcupada();
    }

    public boolean esMovimientoAdyacente(int filaOrigen, int colOrigen,
                                          int filaDestino, int colDestino) {
        int df = Math.abs(filaDestino - filaOrigen);
        int dc = Math.abs(colDestino - colOrigen);
        return (df == 1 && dc == 0) || (df == 0 && dc == 1);
    }

    public boolean esDiagonal(int filaOrigen, int colOrigen,
                               int filaDestino, int colDestino) {
        return Math.abs(filaDestino - filaOrigen) == 1 && Math.abs(colDestino - colOrigen) == 1;
    }

    public int[] aplicarMovimiento(Direccion dir, int filaActual, int colActual) {
        return new int[]{filaActual + dir.getDeltaFila(), colActual + dir.getDeltaColumna()};
    }

    public ListaEnlazada<int[]> casillasAlcanzables(Habitacion habitacion, int fila, int columna, int pasos) {
        ListaEnlazada<int[]> resultado = new ListaEnlazada<>();
        if (habitacion == null || pasos <= 0) return resultado;

        boolean[][] visitados = new boolean[habitacion.getFilas()][habitacion.getColumnas()];
        Cola<int[]> cola = new Cola<>();
        Cola<Integer> distancias = new Cola<>();

        cola.enqueue(new int[]{fila, columna});
        distancias.enqueue(0);
        visitados[fila][columna] = true;

        int[] df = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!cola.estaVacia()) {
            int[] actual = cola.dequeue();
            int dist = distancias.dequeue();

            if (dist > 0 && dist <= pasos)
                resultado.agregar(new int[]{actual[0], actual[1]});
            if (dist >= pasos) continue;

            for (int i = 0; i < 4; i++) {
                int nf = actual[0] + df[i];
                int nc = actual[1] + dc[i];
                if (habitacion.dentroDeLimites(nf, nc) && !visitados[nf][nc]) {
                    Celda celda = habitacion.getCelda(nf, nc);
                    if (celda.esTransitable() && !celda.estaOcupada()) {
                        visitados[nf][nc] = true;
                        cola.enqueue(new int[]{nf, nc});
                        distancias.enqueue(dist + 1);
                    }
                }
            }
        }
        return resultado;
    }

    public int distanciaMinimaATipo(Habitacion habitacion, int fila, int columna, TipoCelda tipoBuscado) {
        if (habitacion == null) return -1;

        boolean[][] visitados = new boolean[habitacion.getFilas()][habitacion.getColumnas()];
        Cola<int[]> cola = new Cola<>();
        Cola<Integer> distancias = new Cola<>();

        cola.enqueue(new int[]{fila, columna});
        distancias.enqueue(0);
        visitados[fila][columna] = true;

        int[] df = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!cola.estaVacia()) {
            int[] actual = cola.dequeue();
            int dist = distancias.dequeue();

            if (habitacion.getCelda(actual[0], actual[1]).getTipo() == tipoBuscado)
                return dist;

            for (int i = 0; i < 4; i++) {
                int nf = actual[0] + df[i];
                int nc = actual[1] + dc[i];
                if (habitacion.dentroDeLimites(nf, nc) && !visitados[nf][nc]) {
                    Celda celda = habitacion.getCelda(nf, nc);
                    if (celda.esTransitable()) {
                        visitados[nf][nc] = true;
                        cola.enqueue(new int[]{nf, nc});
                        distancias.enqueue(dist + 1);
                    }
                }
            }
        }
        return -1;
    }
}

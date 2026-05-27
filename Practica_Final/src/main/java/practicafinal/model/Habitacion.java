package practicafinal.model;

import practicafinal.structures.Matriz;

public class Habitacion {
    private final Matriz<Celda> cuadricula;
    private final int filas;
    private final int columnas;
    private final String id;

    public Habitacion(int filas, int columnas) {
        this("", filas, columnas);
    }

    public Habitacion(String id, int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.id = id;
        this.cuadricula = new Matriz<>(filas, columnas);
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                cuadricula.set(f, c, new Celda(TipoCelda.VACIA));
            }
        }
    }

    public String getId() { return id; }
    public Celda getCelda(int fila, int columna) { return cuadricula.get(fila, columna); }
    public void setCelda(int fila, int columna, Celda celda) { cuadricula.set(fila, columna, celda); }
    public boolean dentroDeLimites(int fila, int columna) { return cuadricula.dentroDeLimites(fila, columna); }
    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }
}

package practicafinal.model;

public class Trampa {
    private String nombre;
    private int dano;
    private boolean activa;
    private String habitacionId;
    private int fila;
    private int columna;

    public Trampa() {
        this.activa = true;
    }

    public Trampa(String nombre, int dano, String habitacionId, int fila, int columna) {
        this.nombre = nombre;
        this.dano = dano;
        this.activa = true;
        this.habitacionId = habitacionId;
        this.fila = fila;
        this.columna = columna;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getDano() { return dano; }
    public void setDano(int dano) { this.dano = dano; }
    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
    public String getHabitacionId() { return habitacionId; }
    public void setHabitacionId(String id) { this.habitacionId = id; }
    public int getFila() { return fila; }
    public void setFila(int fila) { this.fila = fila; }
    public int getColumna() { return columna; }
    public void setColumna(int columna) { this.columna = columna; }
}

package practicafinal.model;

import practicafinal.logic.Turnable;

public class Enemigo implements Turnable {
    private String nombre;
    private int vida;
    private int ataque;
    private int defensa;
    private int fila;
    private int columna;
    private boolean vivo;
    private String habitacionId;

    public Enemigo() {}

    public Enemigo(String nombre, int vida, int ataque, int defensa, int fila, int columna) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        this.fila = fila;
        this.columna = columna;
        this.vivo = true;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getVida() { return vida; }
    public void setVida(int vida) { this.vida = Math.max(0, vida); if (this.vida == 0) this.vivo = false; }
    public int getAtaque() { return ataque; }
    public void setAtaque(int ataque) { this.ataque = ataque; }
    public int getDefensa() { return defensa; }
    public void setDefensa(int defensa) { this.defensa = defensa; }
    public int getFila() { return fila; }
    public void setFila(int fila) { this.fila = fila; }
    public int getColumna() { return columna; }
    public void setColumna(int columna) { this.columna = columna; }
    public boolean isVivo() { return vivo; }
    public void setVivo(boolean vivo) { this.vivo = vivo; }
    public String getHabitacionId() { return habitacionId; }
    public void setHabitacionId(String habitacionId) { this.habitacionId = habitacionId; }
    public boolean estaVivo() { return vivo && vida > 0; }

    @Override public void tomarTurno() {}
}

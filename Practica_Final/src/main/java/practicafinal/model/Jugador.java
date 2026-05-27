package practicafinal.model;

import practicafinal.logic.Turnable;
import practicafinal.structures.ListaEnlazada;

public class Jugador implements Turnable {
    private String nombre;
    private int vida;
    private int vidaMaxima;
    private int ataque;
    private int defensa;
    private int movimiento;
    private int fila;
    private int columna;
    private ListaEnlazada<Objeto> inventario;
    private Objeto armaEquipada;
    private Objeto escudoEquipado;
    private String skin;

    public Jugador() {
        this.inventario = new ListaEnlazada<>();
    }

    public Jugador(String nombre, int vida, int ataque, int defensa, int movimiento) {
        this.nombre = nombre;
        this.vida = vida;
        this.vidaMaxima = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        this.movimiento = movimiento;
        this.inventario = new ListaEnlazada<>();
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getVida() { return vida; }
    public void setVida(int vida) { this.vida = Math.max(0, vida); }
    public int getVidaMaxima() { return vidaMaxima; }
    public void setVidaMaxima(int vidaMaxima) { this.vidaMaxima = vidaMaxima; }

    public int getAtaque() {
        int total = ataque;
        if (armaEquipada != null) total += armaEquipada.getAtaque();
        return total;
    }
    public void setAtaque(int ataque) { this.ataque = ataque; }

    public int getDefensa() {
        int total = defensa;
        if (escudoEquipado != null) total += escudoEquipado.getDefensa();
        return total;
    }
    public void setDefensa(int defensa) { this.defensa = defensa; }

    public int getMovimiento() { return movimiento; }
    public void setMovimiento(int movimiento) { this.movimiento = movimiento; }
    public int getFila() { return fila; }
    public void setFila(int fila) { this.fila = fila; }
    public int getColumna() { return columna; }
    public void setColumna(int columna) { this.columna = columna; }
    public void setPosicion(int fila, int columna) { this.fila = fila; this.columna = columna; }
    public ListaEnlazada<Objeto> getInventario() { return inventario; }
    public void setInventario(ListaEnlazada<Objeto> inventario) { this.inventario = inventario; }
    public void agregarObjeto(Objeto objeto) { inventario.agregar(objeto); }
    public Objeto getArmaEquipada() { return armaEquipada; }
    public void setArmaEquipada(Objeto armaEquipada) { this.armaEquipada = armaEquipada; }
    public Objeto getEscudoEquipado() { return escudoEquipado; }
    public void setEscudoEquipado(Objeto escudoEquipado) { this.escudoEquipado = escudoEquipado; }
    public void curar(int cantidad) { vida = Math.min(vidaMaxima, vida + cantidad); }
    public String getSkin() { return skin != null ? skin : "hero"; }
    public void setSkin(String skin) { this.skin = skin; }

    @Override public void tomarTurno() {}
    @Override public boolean estaVivo() { return vida > 0; }
}

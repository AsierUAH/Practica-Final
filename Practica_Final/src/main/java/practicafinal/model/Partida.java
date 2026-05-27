package practicafinal.model;

import practicafinal.structures.ListaEnlazada;
import practicafinal.structures.Grafo;

public class Partida {
    private String habitacionActual;
    private Jugador jugador;
    private ListaEnlazada<Enemigo> enemigos;
    private EstadoPartida estado;
    private int turnosRestantes;
    private int turnosMaximos;
    private ListaEnlazada<String> eventos;
    private Grafo<String> grafoHabitaciones;
    private ListaEnlazada<String> caminoComprado;
    private boolean caminoVisible;
    private int distanciaMinimaPasos;
    private int habitacionesHastaSalida;
    private ListaEnlazada<Habitacion> habitaciones;
    private ListaEnlazada<Objeto> objetosEnTablero;

    public Partida() {
        this.enemigos = new ListaEnlazada<>();
        this.eventos = new ListaEnlazada<>();
        this.estado = EstadoPartida.EN_CURSO;
        this.grafoHabitaciones = new Grafo<>();
        this.caminoComprado = new ListaEnlazada<>();
        this.caminoVisible = false;
        this.distanciaMinimaPasos = -1;
        this.habitacionesHastaSalida = -1;
        this.habitaciones = new ListaEnlazada<>();
        this.objetosEnTablero = new ListaEnlazada<>();
    }

    public Partida(Jugador jugador, int turnosMaximos) {
        this();
        this.jugador = jugador;
        this.turnosMaximos = turnosMaximos;
        this.turnosRestantes = turnosMaximos;
    }

    public String getHabitacionActual() { return habitacionActual; }
    public void setHabitacionActual(String habitacionActual) { this.habitacionActual = habitacionActual; }
    public Jugador getJugador() { return jugador; }
    public void setJugador(Jugador jugador) { this.jugador = jugador; }
    public ListaEnlazada<Enemigo> getEnemigos() { return enemigos; }
    public void setEnemigos(ListaEnlazada<Enemigo> enemigos) { this.enemigos = enemigos; }
    public void agregarEnemigo(Enemigo enemigo) { enemigos.agregar(enemigo); }
    public EstadoPartida getEstado() { return estado; }
    public void setEstado(EstadoPartida estado) { this.estado = estado; }
    public int getTurnosRestantes() { return turnosRestantes; }
    public void setTurnosRestantes(int turnosRestantes) { this.turnosRestantes = turnosRestantes; }
    public int getTurnosMaximos() { return turnosMaximos; }
    public void setTurnosMaximos(int turnosMaximos) { this.turnosMaximos = turnosMaximos; }
    public ListaEnlazada<String> getEventos() { return eventos; }
    public void setEventos(ListaEnlazada<String> eventos) { this.eventos = eventos; }
    public void agregarEvento(String evento) { eventos.agregar(evento); }
    public Grafo<String> getGrafoHabitaciones() { return grafoHabitaciones; }
    public void setGrafoHabitaciones(Grafo<String> grafoHabitaciones) { this.grafoHabitaciones = grafoHabitaciones; }
    public ListaEnlazada<String> getCaminoComprado() { return caminoComprado; }
    public void setCaminoComprado(ListaEnlazada<String> caminoComprado) { this.caminoComprado = caminoComprado; }
    public boolean isCaminoVisible() { return caminoVisible; }
    public void setCaminoVisible(boolean caminoVisible) { this.caminoVisible = caminoVisible; }
    public int getDistanciaMinimaPasos() { return distanciaMinimaPasos; }
    public void setDistanciaMinimaPasos(int d) { this.distanciaMinimaPasos = d; }
    public int getHabitacionesHastaSalida() { return habitacionesHastaSalida; }
    public void setHabitacionesHastaSalida(int h) { this.habitacionesHastaSalida = h; }

    public ListaEnlazada<Habitacion> getHabitaciones() { return habitaciones; }
    public void setHabitaciones(ListaEnlazada<Habitacion> habitaciones) { this.habitaciones = habitaciones; }
    public ListaEnlazada<Objeto> getObjetosEnTablero() { return objetosEnTablero; }
    public void setObjetosEnTablero(ListaEnlazada<Objeto> objetosEnTablero) { this.objetosEnTablero = objetosEnTablero; }

    public void consumirTurno() {
        if (turnosRestantes > 0) turnosRestantes--;
        if (turnosRestantes <= 0 && estado == EstadoPartida.EN_CURSO) {
            estado = EstadoPartida.DERROTA;
            agregarEvento("Se acabaron los turnos. Derrota.");
        }
    }

    public boolean estaTerminada() { return estado != EstadoPartida.EN_CURSO; }
}

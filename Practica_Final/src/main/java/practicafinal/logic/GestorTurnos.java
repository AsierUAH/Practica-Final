package practicafinal.logic;

import practicafinal.structures.Cola;

public class GestorTurnos {
    private final Cola<Turnable> colaTurnos;
    private int turnoActual;
    private int rondaActual;
    private Turnable jugador;
    private final Cola<Turnable> enemigos;

    public GestorTurnos(Turnable jugador) {
        this.colaTurnos = new Cola<>();
        this.enemigos = new Cola<>();
        this.jugador = jugador;
        this.turnoActual = 0;
        this.rondaActual = 0;
    }

    public void agregarEnemigo(Turnable enemigo) {
        enemigos.enqueue(enemigo);
    }

    public void iniciarRonda() {
        colaTurnos.vaciar();
        colaTurnos.enqueue(jugador);
        Cola<Turnable> temp = new Cola<>();
        while (!enemigos.estaVacia()) {
            Turnable e = enemigos.dequeue();
            if (e.estaVivo()) {
                colaTurnos.enqueue(e);
                temp.enqueue(e);
            }
        }
        while (!temp.estaVacia()) enemigos.enqueue(temp.dequeue());
        rondaActual++;
        turnoActual = 0;
    }

    public Turnable siguienteTurno() {
        if (colaTurnos.estaVacia()) return null;
        Turnable siguiente = colaTurnos.dequeue();
        if (siguiente != null) turnoActual++;
        return siguiente;
    }

    public boolean rondaTerminada() { return colaTurnos.estaVacia(); }
    public int getRondaActual() { return rondaActual; }
    public int getTurnoActual() { return turnoActual; }

    public void reiniciar() {
        colaTurnos.vaciar();
        enemigos.vaciar();
        turnoActual = 0;
        rondaActual = 0;
    }
}

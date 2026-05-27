package practicafinal.log;

import practicafinal.structures.ListaEnlazada;

public class GameLog {
    private final ListaEnlazada<String> eventos;

    public GameLog() { this.eventos = new ListaEnlazada<>(); }

    public void registrar(String evento) {
        eventos.agregar("[" + java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")) + "] " + evento);
    }

    public void registrar(String titulo, String detalle) {
        registrar(titulo + ": " + detalle);
    }

    public ListaEnlazada<String> getEventos() { return eventos; }

    public String getTextoCompleto() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < eventos.tamano(); i++) {
            if (i > 0) sb.append("\n");
            sb.append(eventos.obtener(i));
        }
        return sb.toString();
    }

    public int tamano() { return eventos.tamano(); }

    public void vaciar() { eventos.vaciar(); }
}

package practicafinal.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import practicafinal.model.Partida;
import practicafinal.structures.ListaEnlazada;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PanelLog extends VBox {
    private final TextArea areaLog;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    public PanelLog() {
        setPadding(new Insets(4));
        getStyleClass().add("section-panel");

        Label titulo = new Label("REGISTRO DE EVENTOS");
        titulo.getStyleClass().add("section-title");

        areaLog = new TextArea();
        areaLog.setEditable(false);
        areaLog.setPrefHeight(120);
        areaLog.setWrapText(true);

        getChildren().addAll(titulo, areaLog);
    }

    public void actualizar(Partida partida) {
        areaLog.clear();
        if (partida == null) return;
        ListaEnlazada<String> eventos = partida.getEventos();
        for (int i = 0; i < eventos.tamano(); i++) {
            areaLog.appendText("[" + LocalTime.now().format(FMT) + "] " + eventos.obtener(i) + "\n");
        }
    }

    public void agregarEvento(String texto) {
        areaLog.appendText("[" + LocalTime.now().format(FMT) + "] " + texto + "\n");
    }

    public String getText() { return areaLog.getText(); }
}

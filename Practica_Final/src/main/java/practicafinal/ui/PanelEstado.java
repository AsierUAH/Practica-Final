package practicafinal.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import practicafinal.model.Jugador;
import practicafinal.model.Partida;

public class PanelEstado extends VBox {
    private final Label lblNombre, lblVida;
    private final HBox lblAtaque, lblDefensa, lblMov, lblRoom, lblTurnos;
    private final VBox slotArma, slotEscudo;
    private final ProgressBar barraVida;

    public PanelEstado() {
        setSpacing(2);
        setPadding(new Insets(6));
        getStyleClass().add("section-panel");

        Label titulo = new Label("ESTADO DEL HEROE");
        titulo.getStyleClass().add("section-title");

        lblNombre = lbl("", "#e0e0f0", 12, true);
        lblNombre.getStyleClass().add("hero-name");
        lblVida   = lbl("", "#ff6b6b", 11, false);

        barraVida = new ProgressBar(1);
        barraVida.setPrefWidth(200);
        barraVida.setPrefHeight(10);
        barraVida.getStyleClass().add("hp-bar");
        barraVida.setStyle("-fx-accent: #52b788;");

        HBox hpRow = new HBox(4);
        hpRow.setAlignment(Pos.CENTER_LEFT);
        Label iconVida = new Label("\u2665");
        iconVida.setStyle("-fx-text-fill: #e94560; -fx-font-size: 13px; -fx-min-width: 18;");
        hpRow.getChildren().addAll(iconVida, barraVida, lblVida);

        lblAtaque  = statFila("\u2694", "", "#ffb86c");
        lblDefensa = statFila("\u2748", "", "#6ac9ff");
        lblMov     = statFila("\u27A1", "", "#b0ffb0");
        lblRoom    = statFila("\u2302", "", "#c8a0ff");
        lblTurnos  = statFila("\u23F1", "", "#ffd700");

        Label eqTitle = new Label("EQUIPADO");
        eqTitle.setStyle("-fx-text-fill: #8899bb; -fx-font-size: 9px; " +
                         "-fx-font-family: 'Segoe UI', sans-serif; -fx-padding: 6 0 1 0; -fx-letter-spacing: 1;");

        slotArma = slotEquipo("ARMA");
        slotEscudo = slotEquipo("ESCUDO");

        getChildren().addAll(titulo, lblNombre, hpRow, lblAtaque, lblDefensa, lblMov,
                             lblRoom, lblTurnos, eqTitle, slotArma, slotEscudo);
    }

    private HBox statFila(String icono, String texto, String color) {
        HBox row = new HBox(4);
        row.setAlignment(Pos.CENTER_LEFT);
        Label ic = new Label(icono);
        ic.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 11px; -fx-min-width: 18;");
        Label tx = new Label(texto);
        tx.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 11px; " +
                    "-fx-font-family: Consolas, monospace;");
        row.getChildren().addAll(ic, tx);
        return row;
    }

    private VBox slotEquipo(String nombre) {
        VBox slot = new VBox(1);
        slot.setPadding(new Insets(3, 6, 3, 6));
        slot.getStyleClass().add("equipment-slot");
        Label header = new Label(nombre);
        header.getStyleClass().add("equipment-slot-title");
        slot.getChildren().add(header);
        return slot;
    }

    private Label lbl(String text, String color, int size, boolean bold) {
        Label l = new Label(text);
        String w = bold ? "-fx-font-weight: bold;" : "";
        l.setStyle("-fx-text-fill: " + color + "; -fx-font-size: " + size + "px; " + w +
                   "-fx-font-family: 'Segoe UI', Consolas, monospace;");
        return l;
    }

    public void actualizar(Partida partida) {
        if (partida == null || partida.getJugador() == null) return;
        Jugador j = partida.getJugador();
        lblNombre.setText(j.getNombre());

        double pct = (double) j.getVida() / j.getVidaMaxima();
        barraVida.setProgress(Math.max(0, pct));
        String barColor;
        if (pct < 0.25) barColor = "#e94560";
        else if (pct < 0.5) barColor = "#e9a045";
        else barColor = "#52b788";
        barraVida.setStyle("-fx-accent: " + barColor + ";");
        lblVida.setText(j.getVida() + "/" + j.getVidaMaxima());

        setStatText(lblAtaque, "ATK " + j.getAtaque() + (j.getArmaEquipada() != null ? " (+" + j.getArmaEquipada().getAtaque() + ")" : ""));
        setStatText(lblDefensa, "DEF " + j.getDefensa() + (j.getEscudoEquipado() != null ? " (+" + j.getEscudoEquipado().getDefensa() + ")" : ""));
        setStatText(lblMov, "MOV " + j.getMovimiento());
        setStatText(lblRoom, partida.getHabitacionActual().replace("_", " "));
        setStatText(lblTurnos, partida.getTurnosRestantes() + "/" + partida.getTurnosMaximos());

        actualizarSlot(slotArma, j.getArmaEquipada() != null,
            j.getArmaEquipada() != null ? j.getArmaEquipada().getNombre() + " (ATK+" + j.getArmaEquipada().getAtaque() + ")" : "vacio",
            j.getArmaEquipada() != null ? "#ffb86c" : "#444466");
        actualizarSlot(slotEscudo, j.getEscudoEquipado() != null,
            j.getEscudoEquipado() != null ? j.getEscudoEquipado().getNombre() + " (DEF+" + j.getEscudoEquipado().getDefensa() + ")" : "vacio",
            j.getEscudoEquipado() != null ? "#6ac9ff" : "#444466");
    }

    private void setStatText(HBox row, String text) {
        if (row.getChildren().size() > 1) {
            ((Label) row.getChildren().get(1)).setText(text);
        }
    }

    private void actualizarSlot(VBox slot, boolean equipado, String texto, String color) {
        if (slot.getChildren().size() > 1) slot.getChildren().remove(1, slot.getChildren().size());
        Label val = new Label(texto);
        val.getStyleClass().add("equipment-slot-value");
        val.setStyle("-fx-text-fill: " + color + ";");
        slot.getChildren().add(val);
        slot.setStyle("-fx-border-color: " + (equipado ? color : "#1e2430") + "; -fx-border-width: 1; " +
                      "-fx-border-radius: 3; -fx-background-radius: 3; -fx-background-color: #0a0d14;");
    }
}

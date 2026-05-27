package practicafinal.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import practicafinal.model.Jugador;
import practicafinal.model.Objeto;
import practicafinal.model.TipoObjeto;

public class PanelInventario extends ScrollPane {
    private final VBox contenido;

    public PanelInventario() {
        setFitToWidth(true);
        setPrefHeight(140);
        getStyleClass().add("inventory-scroll");

        VBox root = new VBox(6);
        root.setPadding(new Insets(6));
        root.getStyleClass().add("inventory-root");

        Label titulo = new Label("INVENTARIO");
        titulo.getStyleClass().add("section-title");

        contenido = new VBox(4);
        root.getChildren().addAll(titulo, contenido);
        setContent(root);
    }

    public void actualizar(Jugador jugador) {
        contenido.getChildren().clear();
        if (jugador == null) return;

        if (jugador.getArmaEquipada() != null) contenido.getChildren().add(tarjeta(jugador.getArmaEquipada(), true));
        if (jugador.getEscudoEquipado() != null) contenido.getChildren().add(tarjeta(jugador.getEscudoEquipado(), true));

        if ((jugador.getArmaEquipada() != null || jugador.getEscudoEquipado() != null) && jugador.getInventario().tamano() > 0) {
            Label mochila = new Label("MOCHILA");
            mochila.getStyleClass().add("inventory-subtitle");
            contenido.getChildren().add(mochila);
        }

        for (int i = 0; i < jugador.getInventario().tamano(); i++) {
            contenido.getChildren().add(tarjeta(jugador.getInventario().obtener(i), false));
        }

        if (jugador.getInventario().tamano() == 0 && jugador.getArmaEquipada() == null && jugador.getEscudoEquipado() == null) {
            Label empty = new Label("Mochila vacia");
            empty.getStyleClass().add("inventory-empty");
            contenido.getChildren().add(empty);
        }
    }

    private HBox tarjeta(Objeto obj, boolean equipado) {
        HBox card = new HBox(6);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(4, 6, 4, 6));
        card.getStyleClass().addAll("item-card", claseTipo(obj.getTipo()));
        if (equipado) card.getStyleClass().add("item-equipped");

        Label icon = new Label(iconoTipo(obj.getTipo()));
        icon.getStyleClass().add("item-icon");

        VBox textos = new VBox(1);
        Label nombre = new Label((equipado ? "EQUIPADO - " : "") + obj.getNombre());
        nombre.getStyleClass().add("item-name");
        Label stats = new Label(statsObjeto(obj));
        stats.getStyleClass().add("item-stats");
        textos.getChildren().addAll(nombre, stats);

        card.getChildren().addAll(icon, textos);
        return card;
    }

    private String statsObjeto(Objeto obj) {
        String texto = "";
        if (obj.getAtaque() > 0) texto += "ATK +" + obj.getAtaque();
        if (obj.getDefensa() > 0) texto += (texto.isEmpty() ? "" : " ") + "DEF +" + obj.getDefensa();
        if (obj.getCuracion() > 0) texto += (texto.isEmpty() ? "" : " ") + "CURA +" + obj.getCuracion();
        if (texto.isEmpty()) texto = obj.getTipo().name();
        return texto;
    }

    private String iconoTipo(TipoObjeto tipo) {
        if (tipo == TipoObjeto.ARMA) return "\u2694";
        if (tipo == TipoObjeto.ESCUDO) return "\u2748";
        if (tipo == TipoObjeto.POCION) return "+";
        if (tipo == TipoObjeto.LLAVE) return "\u26BF";
        return "\u25C6";
    }

    private String claseTipo(TipoObjeto tipo) {
        if (tipo == TipoObjeto.ARMA) return "item-weapon";
        if (tipo == TipoObjeto.ESCUDO) return "item-shield";
        if (tipo == TipoObjeto.POCION) return "item-potion";
        if (tipo == TipoObjeto.LLAVE) return "item-key";
        return "item-generic";
    }
}

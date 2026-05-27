package practicafinal.ui;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import practicafinal.model.Partida;

public class PantallaFin extends StackPane {
    private Runnable alVolverAlMenu;

    public PantallaFin(Partida partida, boolean esVictoria) {
        setStyle("-fx-background-color: radial-gradient(center 50% 45%, radius 90%, " +
            (esVictoria ? "#3a2a0e 0%, #1a1620 45%, #050308 100%)" : "#2a0e0e 0%, #1a1218 45%, #050308 100%)"));
        setEffect(new DropShadow(40, Color.rgb(0, 0, 0, 0.6)));

        Label estado = new Label(esVictoria ? "VICTORIA" : "DERROTA");
        estado.setStyle("-fx-text-fill: " + (esVictoria ? "linear-gradient(to bottom, #fff6b8, #d49b2b, #5a3510)" :
            "linear-gradient(to bottom, #ff6b6b, #c0392b, #5a1010)") + ";" +
            "-fx-font-size: 72px; -fx-font-weight: bold; -fx-font-family: 'Georgia', serif;");
        estado.setEffect(new DropShadow(25, esVictoria ? Color.rgb(212, 155, 43, 0.6) : Color.rgb(192, 57, 43, 0.6)));

        Label mensaje = new Label(esVictoria
            ? "Has escapado de las mazmorras de La Conquista"
            : "Tu aventura ha terminado...");
        mensaje.setStyle("-fx-text-fill: #e4dcc8; -fx-font-size: 17px; -fx-font-family: 'Georgia', serif;");
        mensaje.setEffect(new DropShadow(8, Color.rgb(0, 0, 0, 0.5)));

        StringBuilder statsText = new StringBuilder();
        statsText.append("Turnos usados: ").append(partida.getTurnosMaximos() - partida.getTurnosRestantes())
                 .append("/").append(partida.getTurnosMaximos()).append("\n");
        int vivos = 0;
        for (int i = 0; i < partida.getEnemigos().tamano(); i++)
            if (partida.getEnemigos().obtener(i).estaVivo()) vivos++;
        statsText.append("Enemigos restantes: ").append(vivos).append("/").append(partida.getEnemigos().tamano()).append("\n");
        statsText.append("Vida final: ").append(partida.getJugador().getVida()).append("/")
                 .append(partida.getJugador().getVidaMaxima());
        Label stats = new Label(statsText.toString());
        stats.setStyle("-fx-text-fill: #9aa3c0; -fx-font-size: 13px; -fx-font-family: Consolas; -fx-line-spacing: 6;");

        Button volver = new Button("VOLVER AL MENU");
        volver.setStyle("-fx-background-color: linear-gradient(to bottom, #7a561e 0%, #4a2d10 45%, #1b1008 100%);" +
            "-fx-text-fill: #fff0c0; -fx-border-color: #f2c36b #8b6424 #2d1b09 #8b6424; -fx-border-width: 1.5;" +
            "-fx-border-radius: 8; -fx-background-radius: 8; -fx-font-family: Georgia, serif; -fx-font-size: 15px;" +
            "-fx-font-weight: bold; -fx-padding: 10 28; -fx-cursor: hand;");

        VBox box = new VBox(14, estado, mensaje, stats, volver);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(40));
        setOpacity(0);
        getChildren().add(box);

        FadeTransition ft = new FadeTransition(Duration.millis(600), this);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        volver.setOnAction(e -> {
            FadeTransition out = new FadeTransition(Duration.millis(300), this);
            out.setFromValue(1);
            out.setToValue(0);
            out.setOnFinished(ev -> {
                if (alVolverAlMenu != null) alVolverAlMenu.run();
            });
            out.play();
        });
    }

    public void setOnVolverAlMenu(Runnable r) {
        this.alVolverAlMenu = r;
    }
}

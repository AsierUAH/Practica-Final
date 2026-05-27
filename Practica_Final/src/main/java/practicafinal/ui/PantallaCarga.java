package practicafinal.ui;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.util.Duration;

public class PantallaCarga extends StackPane {
    private final Label titulo;
    private final Label subtitulo;
    private final Label mensaje;
    private final ProgressBar barra;
    private final Canvas runeCanvas;
    private Runnable alTerminar;

    public PantallaCarga() {
        setStyle("-fx-background-color: radial-gradient(center 50% 45%, radius 90%, #2a1e12 0%, #101827 50%, #030508 100%);");
        setEffect(new DropShadow(40, Color.rgb(0, 0, 0, 0.5)));

        titulo = new Label("LA CONQUISTA");
        titulo.setStyle("-fx-text-fill: linear-gradient(to bottom, #fff6b8, #d49b2b, #5a3510);" +
                        "-fx-font-size: 52px; -fx-font-weight: bold; -fx-font-family: 'Georgia', serif;");
        titulo.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.7)));
        titulo.setOpacity(0);

        subtitulo = new Label("Preparando la mazmorra...");
        subtitulo.setStyle("-fx-text-fill: #e4dcc8; -fx-font-size: 15px; -fx-font-family: 'Georgia', serif;");
        subtitulo.setEffect(new DropShadow(8, Color.rgb(0, 0, 0, 0.5)));
        subtitulo.setOpacity(0);

        mensaje = new Label("Forjando muros, invocando enemigos, sembrando tesoros...");
        mensaje.setStyle("-fx-text-fill: #9aa3c0; -fx-font-size: 12px; -fx-font-family: Consolas;");
        mensaje.setOpacity(0);

        barra = new ProgressBar(0);
        barra.setPrefWidth(320);
        barra.setPrefHeight(16);
        barra.setStyle("-fx-accent: #d49b2b; -fx-background-color: #1a1620; -fx-background-radius: 8; -fx-border-radius: 8; " +
                       "-fx-border-color: #5a3510; -fx-border-width: 1;");
        barra.setOpacity(0);

        runeCanvas = new Canvas(80, 80);
        runeCanvas.setOpacity(0);
        GraphicsContext gc = runeCanvas.getGraphicsContext2D();
        gc.setStroke(Color.rgb(212, 155, 43, 0.7));
        gc.setLineWidth(3);
        double cx = 40, cy = 40, r = 30;
        gc.strokeOval(cx - r, cy - r, r * 2, r * 2);
        gc.strokeLine(cx, cy - r, cx, cy + r);
        gc.strokeLine(cx - r, cy, cx + r, cy);
        gc.strokeLine(cx - r * 0.7, cy - r * 0.7, cx + r * 0.7, cy + r * 0.7);
        gc.strokeLine(cx - r * 0.7, cy + r * 0.7, cx + r * 0.7, cy - r * 0.7);

        RotateTransition rotar = new RotateTransition(Duration.seconds(3), runeCanvas);
        rotar.setByAngle(360);
        rotar.setInterpolator(Interpolator.LINEAR);
        rotar.setCycleCount(RotateTransition.INDEFINITE);

        VBox box = new VBox(10, runeCanvas, titulo, subtitulo, barra, mensaje);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(40));
        box.setSpacing(14);
        getChildren().add(box);

        setMouseTransparent(true);

        FadeTransition f1 = new FadeTransition(Duration.millis(600), runeCanvas);
        f1.setFromValue(0); f1.setToValue(1);
        FadeTransition f2 = new FadeTransition(Duration.millis(500), titulo);
        f2.setFromValue(0); f2.setToValue(1);
        FadeTransition f3 = new FadeTransition(Duration.millis(400), subtitulo);
        f3.setFromValue(0); f3.setToValue(1);
        FadeTransition f4 = new FadeTransition(Duration.millis(300), barra);
        f4.setFromValue(0); f4.setToValue(1);
        FadeTransition f5 = new FadeTransition(Duration.millis(300), mensaje);
        f5.setFromValue(0); f5.setToValue(1);

        Timeline progreso = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(barra.progressProperty(), 0)),
            new KeyFrame(Duration.millis(800), new KeyValue(barra.progressProperty(), 0.3)),
            new KeyFrame(Duration.millis(1600), new KeyValue(barra.progressProperty(), 0.6)),
            new KeyFrame(Duration.millis(2400), new KeyValue(barra.progressProperty(), 0.85)),
            new KeyFrame(Duration.millis(3000), e -> {
                mensaje.setText("Mazmorra lista. Entrando...");
                KeyValue kv = new KeyValue(barra.progressProperty(), 1.0);
                Timeline ultimo = new Timeline(new KeyFrame(Duration.millis(400), kv));
                ultimo.setOnFinished(ev -> {});
                ultimo.play();
            })
        );

        SequentialTransition seq = new SequentialTransition();
        seq.getChildren().addAll(f1, f2, f3, f4, f5);
        seq.setOnFinished(e -> {
            rotar.play();
            progreso.play();
        });
        seq.play();

        javafx.animation.PauseTransition fin = new javafx.animation.PauseTransition(Duration.millis(3800));
        fin.setOnFinished(e -> {
            rotar.stop();
            if (alTerminar != null) alTerminar.run();
        });
        fin.play();
    }

    public void setOnFinished(Runnable r) { this.alTerminar = r; }
}

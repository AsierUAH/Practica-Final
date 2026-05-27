package practicafinal.ui;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class PantallaSplash extends StackPane {
    private final Label titulo;
    private final Label subtitulo;
    private final Label loading;
    private Runnable alTerminar;

    public PantallaSplash() {
        setStyle("-fx-background-color: radial-gradient(center 50% 45%, radius 85%, #2a1e12 0%, #101827 45%, #030508 100%);");
        setEffect(new DropShadow(40, Color.rgb(0, 0, 0, 0.5)));

        titulo = new Label("LA CONQUISTA");
        titulo.setStyle("-fx-text-fill: linear-gradient(to bottom, #fff6b8, #d49b2b, #5a3510);" +
                        "-fx-font-size: 72px; -fx-font-weight: bold; -fx-font-family: 'Georgia', 'Times New Roman', serif;");
        titulo.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.7)));
        titulo.setOpacity(0);

        subtitulo = new Label("Dungeon crawler por turnos en las ruinas de una fortaleza maldita");
        subtitulo.setStyle("-fx-text-fill: #e4dcc8; -fx-font-size: 17px; -fx-font-family: 'Georgia', serif;");
        subtitulo.setEffect(new DropShadow(8, Color.rgb(0, 0, 0, 0.5)));
        subtitulo.setOpacity(0);

        loading = new Label("CARGANDO");
        loading.setStyle("-fx-text-fill: #9aa3c0; -fx-font-size: 13px; -fx-font-family: Consolas; -fx-padding: 30 0 0 0;");
        loading.setOpacity(0);

        VBox box = new VBox(12, titulo, subtitulo, loading);
        box.setAlignment(Pos.CENTER);
        getChildren().add(box);
    }

    public void setOnFinished(Runnable r) {
        this.alTerminar = r;
    }

    public void iniciar() {
        FadeTransition fadeTitulo = new FadeTransition(Duration.millis(800), titulo);
        fadeTitulo.setFromValue(0);
        fadeTitulo.setToValue(1);

        FadeTransition fadeSub = new FadeTransition(Duration.millis(600), subtitulo);
        fadeSub.setFromValue(0);
        fadeSub.setToValue(1);

        FadeTransition fadeLoad = new FadeTransition(Duration.millis(500), loading);
        fadeLoad.setFromValue(0);
        fadeLoad.setToValue(1);

        Timeline dots = new Timeline(
            new KeyFrame(Duration.ZERO, e -> loading.setText("CARGANDO")),
            new KeyFrame(Duration.millis(400), e -> loading.setText("CARGANDO.")),
            new KeyFrame(Duration.millis(800), e -> loading.setText("CARGANDO..")),
            new KeyFrame(Duration.millis(1200), e -> loading.setText("CARGANDO..."))
        );
        dots.setCycleCount(2);

        SequentialTransition seq = new SequentialTransition();
        seq.getChildren().addAll(fadeTitulo, fadeSub, fadeLoad, new PauseTransition(Duration.millis(200)), dots);

        FadeTransition out = new FadeTransition(Duration.millis(500), this);
        out.setFromValue(1);
        out.setToValue(0);
        out.setOnFinished(e -> {
            if (alTerminar != null) alTerminar.run();
        });

        seq.setOnFinished(e -> out.play());
        seq.play();
    }
}

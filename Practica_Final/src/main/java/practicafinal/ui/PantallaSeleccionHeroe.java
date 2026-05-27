package practicafinal.ui;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class PantallaSeleccionHeroe extends StackPane {
    private static final String[][] HEROES = {
        {"hero",     "Paladin",   "Caballero de la luz,\narmadura azul y corazon\nnoble listo para la batalla."},
        {"heroDark",  "Sombra",    "Asesino envuelto en\noscuridad, ojos violeta\nbrillando en la penumbra."},
        {"heroFire",  "Berserker",  "Guerrero de las llamas,\n ira ardiente y espada\nde fuego purificador."},
        {"heroKnight","Cruzado",   "Titán de acero,\narmadura completa y\nvisera imperturbable."}
    };

    private final Runnable onClose;

    public PantallaSeleccionHeroe(java.util.function.Consumer<String> onSelect) {
        setStyle("-fx-background-color: rgba(8, 10, 16, 0.95);");
        setAlignment(Pos.CENTER);
        getStyleClass().add("hero-select-overlay");

        VBox main = new VBox(24);
        main.setAlignment(Pos.CENTER);
        main.setPadding(new Insets(40));

        Text titulo = new Text("ELIGE TU HEROE");
        titulo.setFont(Font.font("Monospaced", FontWeight.BOLD, 32));
        titulo.setFill(Color.rgb(220, 200, 160));
        titulo.setEffect(new DropShadow(12, Color.rgb(200, 160, 80, 0.5)));

        Text subtitulo = new Text("Cada skin cambia el aspecto de tu personaje en el tablero.");
        subtitulo.setFont(Font.font("Monospaced", 13));
        subtitulo.setFill(Color.rgb(140, 130, 110));

        HBox cards = new HBox(20);
        cards.setAlignment(Pos.CENTER);

        for (int i = 0; i < HEROES.length; i++) {
            String skinId = HEROES[i][0];
            String name = HEROES[i][1];
            String desc = HEROES[i][2];
            VBox card = crearTarjeta(skinId, name, desc, onSelect);
            cards.getChildren().add(card);
        }

        Text hint = new Text("Pulsa ESC para volver al menu");
        hint.setFont(Font.font("Monospaced", 11));
        hint.setFill(Color.rgb(100, 95, 80));

        main.getChildren().addAll(titulo, subtitulo, cards, hint);
        getChildren().add(main);

        FadeTransition fade = new FadeTransition(Duration.millis(200), this);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        onClose = () -> {
            FadeTransition out = new FadeTransition(Duration.millis(150), this);
            out.setFromValue(1);
            out.setToValue(0);
            out.setOnFinished(e -> ((StackPane) getParent()).getChildren().remove(this));
            out.play();
        };

        setFocusTraversable(true);
        addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ESCAPE) onClose.run();
        });
    }

    private VBox crearTarjeta(String skinId, String name, String desc,
                               java.util.function.Consumer<String> onSelect) {
        VBox card = new VBox(8);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(16, 20, 20, 20));
        card.setMinWidth(180);
        card.setMaxWidth(180);
        card.setStyle(
            "-fx-background-color: rgba(30, 28, 38, 0.85);" +
            "-fx-border-color: rgba(100, 90, 75, 0.4);" +
            "-fx-border-width: 1.5;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;" +
            "-fx-cursor: hand;"
        );

        Canvas canvas = PixelArt.crear(skinId + "Sword", 96);
        canvas.setEffect(new DropShadow(10, Color.rgb(0, 0, 0, 0.7)));

        Text label = new Text(name);
        label.setFont(Font.font("Monospaced", FontWeight.BOLD, 16));
        label.setFill(Color.rgb(230, 215, 190));

        Text descText = new Text(desc);
        descText.setFont(Font.font("Monospaced", 11));
        descText.setFill(Color.rgb(160, 150, 130));
        descText.setLineSpacing(2);

        card.getChildren().addAll(canvas, label, descText);

        card.setOnMouseClicked(e -> {
            onSelect.accept(skinId);
            onClose.run();
        });

        card.setOnMouseEntered(e -> {
            card.setStyle(
                "-fx-background-color: rgba(45, 40, 55, 0.95);" +
                "-fx-border-color: rgba(200, 170, 120, 0.7);" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;" +
                "-fx-cursor: hand;"
            );
        });

        card.setOnMouseExited(e -> {
            card.setStyle(
                "-fx-background-color: rgba(30, 28, 38, 0.85);" +
                "-fx-border-color: rgba(100, 90, 75, 0.4);" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;" +
                "-fx-cursor: hand;"
            );
        });

        return card;
    }
}

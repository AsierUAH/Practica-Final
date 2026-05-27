package practicafinal.app;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import practicafinal.logic.GestorMusica;
import practicafinal.ui.ControladorJuego;
import practicafinal.ui.PantallaCarga;
import practicafinal.ui.PantallaSplash;
import practicafinal.ui.PantallaFin;
import practicafinal.ui.PantallaSeleccionHeroe;
import practicafinal.ui.VistaPrincipal;

public class App extends Application {
    private GestorMusica musica;

    @Override
    public void start(Stage stage) {
        musica = new GestorMusica();
        ControladorJuego controlador = new ControladorJuego();
        controlador.setMusica(musica);
        VistaPrincipal vista = new VistaPrincipal(controlador);
        controlador.setVistaPrincipal(vista);
        vista.setVisible(false);

        StackPane root = new StackPane(vista);
        VBox menu = crearMenuPrincipal(stage, root, vista, controlador);
        root.getChildren().add(menu);

        Scene scene = new Scene(root, 1360, 860);
        scene.getStylesheets().add(getClass().getResource("/styles/roguelike.css").toExternalForm());
        stage.setTitle("La Conquista - Dungeon Crawler");
        stage.setScene(scene);
        stage.setMinWidth(1150);
        stage.setMinHeight(720);
        stage.centerOnScreen();

        PantallaSplash splash = new PantallaSplash();
        splash.setOnFinished(() -> {
            root.getChildren().remove(splash);
            musica.iniciarMusicaMenu();
            stage.show();
        });
        root.getChildren().add(0, splash);
        splash.iniciar();
        stage.show();
    }

    private VBox crearMenuPrincipal(Stage stage, StackPane root, VistaPrincipal vista, ControladorJuego controlador) {
        VBox menu = new VBox(14);
        menu.setAlignment(Pos.CENTER);
        menu.getStyleClass().add("main-menu");

        Label titulo = new Label("LA CONQUISTA");
        titulo.getStyleClass().add("menu-title");
        Label subtitulo = new Label("Dungeon crawler por turnos en las ruinas de una fortaleza maldita");
        subtitulo.getStyleClass().add("menu-subtitle");

        Button nueva = botonMenu("Nueva partida");
        Button cargar = botonMenu("Cargar partida");
        Button reglas = botonMenu("Reglas y controles");
        Button salir = botonMenu("Salir");

        nueva.setOnAction(e -> {
            musica.detenerTodaMusica();
            if (!controlador.cargarConfiguracion(RutasJuego.CONFIG_CAMPANIA)) {
                mostrarErrorInicio("No se pudo iniciar la partida", vista.getLogText());
                return;
            }
            PantallaSeleccionHeroe selector = new PantallaSeleccionHeroe(skin -> {
                controlador.setSkin(skin);
                controlador.setOnFinPartida(() -> mostrarFinPartida(root, controlador));
                mostrarJuego(root, menu, vista);
            });
            root.getChildren().add(selector);
            selector.requestFocus();
        });
        cargar.setOnAction(e -> {
            musica.detenerTodaMusica();
            cargarPartida(root, menu, vista, controlador);
        });
        reglas.setOnAction(e -> mostrarReglas());
        salir.setOnAction(e -> {
            musica.close();
            stage.close();
        });

        Label pie = new Label("Consejo: lee las reglas antes de jugar si es tu primera partida.");
        pie.getStyleClass().add("menu-hint");

        menu.getChildren().addAll(titulo, subtitulo, nueva, cargar, reglas, salir, pie);
        return menu;
    }

    private Button botonMenu(String texto) {
        Button boton = new Button(texto);
        boton.getStyleClass().add("menu-button");
        boton.setMinWidth(260);
        return boton;
    }

    private void cargarPartida(StackPane root, VBox menu, VistaPrincipal vista, ControladorJuego controlador) {
        java.io.File guardado = RutasJuego.archivoGuardadoPrincipal();
        if (!guardado.exists()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("La Conquista - Cargar partida");
            alert.setHeaderText("No hay partida guardada");
            alert.setContentText("Empieza una nueva partida y usa Guardar durante el juego para crear un guardado.");
            alert.showAndWait();
            return;
        }
        if (controlador.cargarPartida(guardado.getAbsolutePath())) {
            controlador.setOnFinPartida(() -> mostrarFinPartida(root, controlador));
            mostrarJuego(root, menu, vista);
        }
        else mostrarErrorInicio("No se pudo cargar la partida", vista.getLogText());
    }

    private void mostrarErrorInicio(String titulo, String detalle) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("La Conquista - Error");
        alert.setHeaderText(titulo);
        alert.setContentText(detalle == null || detalle.isBlank() ? "Revisa la configuracion o el archivo de guardado." : detalle);
        alert.showAndWait();
    }

    private void mostrarJuego(StackPane root, VBox menu, VistaPrincipal vista) {
        root.getChildren().remove(menu);
        vista.setVisible(true);

        PantallaCarga carga = new PantallaCarga();
        carga.setOnFinished(() -> {
            musica.iniciarMusicaJuego();
            FadeTransition fadeOut = new FadeTransition(Duration.millis(500), carga);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(ev -> root.getChildren().remove(carga));
            fadeOut.play();
        });
        root.getChildren().add(carga);
    }

    private void mostrarFinPartida(StackPane root, ControladorJuego controlador) {
        boolean esVictoria = controlador.getPartida().getEstado() == practicafinal.model.EstadoPartida.VICTORIA;
        if (esVictoria) musica.reproducirVictoria();
        else musica.reproducirDerrota();

        PantallaFin fin = new PantallaFin(controlador.getPartida(), esVictoria);
        fin.setOnVolverAlMenu(() -> {
            root.getChildren().remove(fin);
            musica.detenerTodaMusica();
            musica.iniciarMusicaMenu();
            VistaPrincipal vista = (VistaPrincipal) root.getChildren().stream()
                .filter(n -> n instanceof VistaPrincipal).findFirst().orElse(null);
            if (vista != null) vista.setVisible(false);
            controlador.setFinMostrado(false);
            VBox menu = crearMenuPrincipal((Stage) root.getScene().getWindow(), root, vista, controlador);
            root.getChildren().add(menu);
        });
        root.getChildren().add(fin);
    }

    private void mostrarReglas() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("La Conquista - Reglas y controles");
        alert.setHeaderText("Aprende a sobrevivir antes de entrar en las ruinas");

        TextArea reglas = new TextArea();
        reglas.setEditable(false);
        reglas.setWrapText(true);
        reglas.setPrefSize(620, 470);
        reglas.getStyleClass().add("rules-text");
        reglas.setText(
            "OBJETIVO\n" +
            "Escapa del primer capitulo de La Conquista antes de quedarte sin vida o sin ticks. Explora con informacion parcial, recoge equipo, usa trampas contra enemigos y alcanza la salida final.\n\n" +
            "TURNO DEL JUGADOR\n" +
            "El juego funciona por ticks simultaneos: cada movimiento, ataque, objeto o espera hace que los enemigos reaccionen al mismo tiempo. Puedes pensar sin limite antes de elegir.\n\n" +
            "MOVIMIENTO\n" +
            "WASD o flechas mueven directamente en el mapa: arriba, abajo, izquierda y derecha. No hay movimiento diagonal.\n\n" +
            "COMBATE\n" +
            "El combate es posicional. Ataca enemigos adyacentes, retrocede para ganar espacio y empuja enemigos hacia pinchos cuando puedas. El dano usa la formula del proyecto: maximo(0, ataque * (aleatorio * 2) - defensa).\n\n" +
            "OBJETOS\n" +
            "Recoge pociones, armas, escudos y llaves. Las pociones curan vida. Las armas aumentan ataque. Los escudos aumentan defensa. Puedes reemplazar equipo desde el boton Equipar o con Q.\n\n" +
            "TRAMPAS\n" +
            "Las trampas causan dano al pisarlas, pero tambien pueden ser armas: si golpeas a un enemigo y hay pinchos detras, lo empujas contra ellos.\n\n" +
            "PUERTAS Y RUTA\n" +
            "Las puertas conectan habitaciones concretas. La camara final exige la Llave de la Cripta, que esta protegida por esqueletos. Puedes comprar la ruta hacia la salida gastando 3 turnos.\n\n" +
            "CONTROLES\n" +
            "W/Flecha arriba: avanzar\n" +
            "S/Flecha abajo: retroceder\n" +
            "A/Flecha izquierda: moverse a la izquierda\n" +
            "D/Flecha derecha: moverse a la derecha\n" +
            "Espacio: atacar\n" +
            "R: recoger objeto\n" +
            "E: usar pocion\n" +
            "Q: equipar objeto\n" +
            "O: abrir puerta\n" +
            "F: finalizar turno\n" +
            "G: guardar partida\n\n" +
            "CONSEJO\n" +
            "No juegues como si fuera un RPG de estadisticas. Cada sala es un puzle espacial: mira, gira, atrae enemigos, usa pinchos y solo entonces entra en la camara final."
        );

        alert.getDialogPane().setContent(reglas);
        alert.getDialogPane().getStyleClass().add("rules-dialog");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

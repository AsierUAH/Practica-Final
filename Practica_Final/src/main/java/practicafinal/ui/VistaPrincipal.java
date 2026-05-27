package practicafinal.ui;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import practicafinal.model.Direccion;
import practicafinal.model.Partida;
import practicafinal.app.RutasJuego;
import practicafinal.structures.ListaEnlazada;

public class VistaPrincipal extends BorderPane {
    private final PanelMatriz panelMatriz;
    private final PanelEstado panelEstado;
    private final PanelInventario panelInventario;
    private final PanelLog panelLog;
    private final PanelAcciones panelAcciones;
    private final Label lblRuta;
    private final ProgressBar barraTurnos;
    private final Label lblFaseTurno;
    private final Label lblSala;
    private final ControladorJuego controlador;
    private ScrollPane scrollMatriz;
    private final Label transicionLabel;
    private final Rectangle transicionOverlay;
    private final Label transicionSub;

    public VistaPrincipal(ControladorJuego controlador) {
        this.controlador = controlador;
        panelMatriz = new PanelMatriz();
        panelEstado = new PanelEstado();
        panelInventario = new PanelInventario();
        panelLog = new PanelLog();
        panelLog.setPrefHeight(110);
        panelAcciones = new PanelAcciones();
        lblRuta = new Label("(no calculada)");
        barraTurnos = new ProgressBar(1);
        lblFaseTurno = new Label(">> ESPERANDO <<");
        lblSala = new Label("");

        transicionOverlay = new Rectangle();
        transicionOverlay.setFill(Color.rgb(0, 0, 0, 0));
        transicionOverlay.setVisible(false);
        transicionOverlay.setMouseTransparent(true);

        transicionLabel = new Label();
        transicionLabel.setVisible(false);
        transicionLabel.setStyle("-fx-text-fill: #ffd700; -fx-font-size: 32px; -fx-font-weight: bold; " +
                                "-fx-font-family: 'Georgia', serif; " +
                                "-fx-effect: dropshadow(gaussian, rgba(255,215,0,0.6), 20, 0.5, 0, 0);");

        transicionSub = new Label();
        transicionSub.setVisible(false);
        transicionSub.setStyle("-fx-text-fill: #c0b090; -fx-font-size: 15px; -fx-font-style: italic; " +
                              "-fx-font-family: 'Georgia', serif; " +
                              "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 8, 0.4, 0, 0);");

        controlador.setPaneles(panelMatriz, panelEstado, panelInventario, panelLog, panelAcciones);

        configurarLayout();
        configurarAcciones();
        panelMatriz.setOnCeldaClick((fila, columna) -> controlador.moverA(fila, columna));
        sceneProperty().addListener((obs, old, scene) -> {
            if (scene != null) inicializarTeclas(scene);
        });
    }

    private void configurarLayout() {
        setStyle("-fx-background-color: #0d1117;");

        /* ─── Top bar minimal ─── */
        lblSala.setStyle("-fx-text-fill: #c0b090; -fx-font-size: 12px; -fx-font-family: 'Georgia', serif; " +
                         "-fx-font-style: italic; -fx-padding: 0 0 0 4;");

        barraTurnos.setPrefWidth(120);
        barraTurnos.setPrefHeight(10);
        barraTurnos.setStyle("-fx-accent: #ffd700;");

        lblFaseTurno.setStyle("-fx-text-fill: #ffd700; -fx-font-size: 11px; -fx-font-weight: bold; " +
                              "-fx-font-family: Consolas, monospace; -fx-padding: 0 6 0 0;");

        lblRuta.setStyle("-fx-text-fill: #8888a0; -fx-font-size: 9px; " +
                         "-fx-font-family: Consolas, monospace; -fx-padding: 0 4 0 0;");

        HBox topBar = new HBox(8);
        topBar.setPadding(new Insets(4, 8, 2, 8));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.getChildren().addAll(lblSala, barraTurnos, lblFaseTurno, lblRuta);

        /* ─── Board ─── */
        scrollMatriz = new ScrollPane(panelMatriz);
        scrollMatriz.setFitToWidth(true);
        scrollMatriz.setFitToHeight(true);
        scrollMatriz.setPannable(true);
        scrollMatriz.setStyle("-fx-background: #0d1117; -fx-background-color: #0d1117; -fx-border-color: #1e2430; " +
                              "-fx-border-width: 1;");

        transicionOverlay.widthProperty().bind(scrollMatriz.widthProperty());
        transicionOverlay.heightProperty().bind(scrollMatriz.heightProperty());

        StackPane transicionLayer = new StackPane();
        transicionLayer.setMouseTransparent(true);
        transicionLayer.getChildren().addAll(transicionOverlay, transicionLabel, transicionSub);
        StackPane.setAlignment(transicionLabel, Pos.CENTER);
        StackPane.setAlignment(transicionSub, Pos.CENTER);
        StackPane.setMargin(transicionSub, new Insets(50, 0, 0, 0));

        StackPane matrizWrapper = new StackPane(scrollMatriz, transicionLayer);
        VBox.setVgrow(matrizWrapper, Priority.ALWAYS);

        /* ─── Log ─── */
        VBox centerArea = new VBox(2, matrizWrapper, panelLog);
        VBox.setVgrow(matrizWrapper, Priority.ALWAYS);

        /* ─── Right panel ─── */
        VBox panelDerecho = new VBox(6);
        panelDerecho.setPrefWidth(230);
        panelDerecho.setMaxWidth(240);
        panelDerecho.setPadding(new Insets(6, 5, 6, 3));
        panelDerecho.getStyleClass().add("right-panel");
        panelDerecho.getChildren().addAll(panelEstado, panelInventario, panelAcciones);

        setTop(topBar);
        setCenter(centerArea);
        setRight(panelDerecho);
    }

    private void inicializarTeclas(javafx.scene.Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            KeyCode k = e.getCode();
            if (k == KeyCode.W || k == KeyCode.UP)     { e.consume(); controlador.avanzar(); }
            else if (k == KeyCode.S || k == KeyCode.DOWN)  { e.consume(); controlador.retroceder(); }
            else if (k == KeyCode.A || k == KeyCode.LEFT)  { e.consume(); controlador.moverIzquierda(); }
            else if (k == KeyCode.D || k == KeyCode.RIGHT) { e.consume(); controlador.moverDerecha(); }
            else if (k == KeyCode.SPACE)  { e.consume(); controlador.atacar(); }
            else if (k == KeyCode.E)      { e.consume(); controlador.usarObjeto(); }
            else if (k == KeyCode.Q)      { e.consume(); controlador.mostrarDialogoEquipar(); }
            else if (k == KeyCode.O)      { e.consume(); controlador.abrirPuerta(); }
            else if (k == KeyCode.F)      { e.consume(); controlador.finalizarTurno(); }
            else if (k == KeyCode.G)      { e.consume(); controlador.guardarPartida(RutasJuego.rutaGuardadoPrincipal()); }
            else if (k == KeyCode.R)      { e.consume(); controlador.recogerObjeto(); }
        });
    }

    private void configurarAcciones() {
        panelAcciones.setOnMoverArriba(() -> controlador.avanzar());
        panelAcciones.setOnMoverAbajo(() -> controlador.retroceder());
        panelAcciones.setOnMoverIzquierda(() -> controlador.moverIzquierda());
        panelAcciones.setOnMoverDerecha(() -> controlador.moverDerecha());
        panelAcciones.setOnAtacar(() -> controlador.atacar());
        panelAcciones.setOnRecoger(() -> controlador.recogerObjeto());
        panelAcciones.setOnUsar(() -> controlador.usarObjeto());
        panelAcciones.setOnAbrirPuerta(() -> controlador.abrirPuerta());
        panelAcciones.setOnComprarCamino(() -> controlador.comprarCamino());
        panelAcciones.setOnFinalizarTurno(() -> controlador.finalizarTurno());
        panelAcciones.setOnGuardar(() -> controlador.guardarPartida(RutasJuego.rutaGuardadoPrincipal()));
        panelAcciones.setOnCargar(() -> {
            java.io.File file = RutasJuego.archivoGuardadoPrincipal();
            if (file.exists()) controlador.cargarPartida(file.getAbsolutePath());
            else panelLog.agregarEvento("No hay partida guardada");
        });
        panelAcciones.setOnNueva(() -> {
            if (controlador.cargarConfiguracion(RutasJuego.CONFIG_CAMPANIA))
                panelLog.agregarEvento("Nueva partida iniciada desde configuracion");
        });
        panelAcciones.setOnEquipar(() -> controlador.mostrarDialogoEquipar());
    }

    public void actualizarFaseTurno(Partida partida) {
        if (partida == null || partida.estaTerminada()) {
            lblFaseTurno.setText("PARTIDA TERMINADA");
            lblFaseTurno.setStyle("-fx-text-fill: #e94560; -fx-font-size: 11px; -fx-font-weight: bold; " +
                                  "-fx-font-family: Consolas, monospace;");
            return;
        }
        lblSala.setText(partida.getHabitacionActual().replace("_", " ").toUpperCase());
        boolean haMovido = controlador.haMovido();
        boolean haActuado = controlador.haActuado();
        int movimientosRestantes = controlador.getMovimientosRestantes();
        String fase;
        String color;
        if (!haActuado && movimientosRestantes > 0) {
            fase = "ELIGE ACCION";
            color = "#52b788";
        } else if (haMovido && !haActuado) {
            fase = "ACTUA";
            color = "#ffb86c";
        } else {
            fase = "TURNO ENEMIGO";
            color = "#e94560";
        }
        lblFaseTurno.setText(fase);
        lblFaseTurno.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 11px; -fx-font-weight: bold; " +
                              "-fx-font-family: Consolas, monospace;");
    }

    public void actualizarRuta(Partida partida) {
        if (partida == null) return;
        double pct = partida.getTurnosRestantes() / (double) Math.max(1, partida.getTurnosMaximos());
        barraTurnos.setProgress(Math.max(0, pct));
        if (pct < 0.2) barraTurnos.setStyle("-fx-accent: #e94560;");
        else if (pct < 0.4) barraTurnos.setStyle("-fx-accent: #e9a045;");
        else barraTurnos.setStyle("-fx-accent: #ffd700;");

        ListaEnlazada<String> ruta = partida.getCaminoComprado();
        if (ruta != null && ruta.tamano() > 0 && partida.isCaminoVisible()) {
            StringBuilder sb = new StringBuilder("Ruta: ");
            for (int i = 0; i < ruta.tamano(); i++) {
                if (i > 0) sb.append(" -> ");
                sb.append(ruta.obtener(i));
            }
            lblRuta.setText(sb.toString());
        } else {
            lblRuta.setText("Dist: " + partida.getDistanciaMinimaPasos() + " pasos / " +
                            partida.getHabitacionesHastaSalida() + " habs");
        }
    }

    public void mostrarTransicionSala(String nombreSala) {
        String nombreBonito = nombreSala.replace("_", " ").toUpperCase();
        transicionLabel.setText(nombreBonito);
        transicionSub.setText("Nueva sala descubierta");
        transicionLabel.setScaleX(0.4);
        transicionLabel.setScaleY(0.4);
        transicionLabel.setOpacity(0);
        transicionLabel.setVisible(true);
        transicionSub.setOpacity(0);
        transicionSub.setVisible(true);
        transicionOverlay.setOpacity(0);
        transicionOverlay.setVisible(true);

        FadeTransition overlayIn = new FadeTransition(Duration.millis(300), transicionOverlay);
        overlayIn.setToValue(0.7);

        ParallelTransition labelIn = new ParallelTransition();
        FadeTransition fadeIn = new FadeTransition(Duration.millis(350), transicionLabel);
        fadeIn.setToValue(1);
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(400), transicionLabel);
        scaleIn.setToX(1);
        scaleIn.setToY(1);
        scaleIn.setInterpolator(javafx.animation.Interpolator.EASE_OUT);
        labelIn.getChildren().addAll(fadeIn, scaleIn);

        FadeTransition subIn = new FadeTransition(Duration.millis(300), transicionSub);
        subIn.setToValue(1);
        subIn.setDelay(Duration.millis(200));

        SequentialTransition show = new SequentialTransition();
        show.getChildren().add(overlayIn);
        ParallelTransition showAll = new ParallelTransition();
        showAll.getChildren().addAll(labelIn, subIn);
        show.getChildren().add(showAll);
        show.getChildren().add(new PauseTransition(Duration.millis(600)));

        FadeTransition overlayOut = new FadeTransition(Duration.millis(350), transicionOverlay);
        overlayOut.setToValue(0);

        FadeTransition labelOut = new FadeTransition(Duration.millis(250), transicionLabel);
        labelOut.setToValue(0);

        FadeTransition subOut = new FadeTransition(Duration.millis(200), transicionSub);
        subOut.setToValue(0);

        ParallelTransition hide = new ParallelTransition();
        hide.getChildren().addAll(overlayOut, labelOut, subOut);
        show.getChildren().add(hide);

        show.setOnFinished(e -> {
            transicionLabel.setVisible(false);
            transicionSub.setVisible(false);
            transicionOverlay.setVisible(false);
        });
        show.play();
    }

    public void mostrarFinPartida(String titulo, String mensaje) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
            javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("La Conquista - " + titulo);
        alert.setHeaderText(mensaje);
        alert.getDialogPane().setStyle("-fx-background-color: #1a1a2e; -fx-font-family: Consolas;");

        Partida p = controlador.getPartida();
        StringBuilder stats = new StringBuilder();
        stats.append("--- Resumen ---\n");
        stats.append("Turnos usados: ").append(p.getTurnosMaximos() - p.getTurnosRestantes())
              .append("/").append(p.getTurnosMaximos()).append("\n");
        int vivos = 0;
        for (int i = 0; i < p.getEnemigos().tamano(); i++)
            if (p.getEnemigos().obtener(i).estaVivo()) vivos++;
        stats.append("Enemigos restantes: ").append(vivos).append("/").append(p.getEnemigos().tamano()).append("\n");
        stats.append("Vida final: ").append(p.getJugador().getVida()).append("/")
              .append(p.getJugador().getVidaMaxima()).append("\n");
        stats.append("Objetos: ").append(p.getJugador().getInventario().tamano()).append("\n\n");
        stats.append("--- LOG ---\n").append(panelLog.getText());

        javafx.scene.control.TextArea ta = new javafx.scene.control.TextArea();
        ta.setText(stats.toString());
        ta.setEditable(false);
        ta.setPrefSize(550, 400);
        ta.setStyle("-fx-control-inner-background: #0a0d14; -fx-text-fill: #c0c0d0; " +
                    "-fx-font-family: Consolas; -fx-font-size: 11px;");
        alert.getDialogPane().setContent(ta);
        alert.showAndWait();
    }

    public PanelLog getPanelLog() { return panelLog; }
    public PanelAcciones getPanelAcciones() { return panelAcciones; }
    public String getLogText() { return panelLog.getText(); }
}

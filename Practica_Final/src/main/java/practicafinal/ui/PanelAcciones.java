package practicafinal.ui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import practicafinal.model.EstadoPartida;
import practicafinal.model.Partida;

public class PanelAcciones extends VBox {
    private final Button btnMoverArriba, btnMoverAbajo, btnMoverIzquierda, btnMoverDerecha;
    private final Button btnAtacar, btnRecoger, btnUsar, btnAbrirPuerta, btnEquipar;
    private final Button btnComprarCamino, btnFinalizarTurno;
    private final Button btnGuardar, btnCargar, btnNueva;

    public PanelAcciones() {
        setSpacing(3);
        setPadding(new Insets(6));
        getStyleClass().add("section-panel");

        Label titulo = new Label("ACCIONES");
        titulo.getStyleClass().add("section-title");

        btnMoverArriba    = b("W", "btn-dir");
        btnMoverAbajo     = b("S", "btn-dir");
        btnMoverIzquierda = b("A", "btn-dir");
        btnMoverDerecha   = b("D", "btn-dir");

        GridPane dirPad = new GridPane();
        dirPad.setHgap(3); dirPad.setVgap(3);
        dirPad.add(btnMoverArriba, 1, 0);
        dirPad.add(btnMoverIzquierda, 0, 1);
        dirPad.add(btnMoverDerecha, 2, 1);
        dirPad.add(btnMoverAbajo, 1, 2);
        GridPane.setHalignment(btnMoverArriba, HPos.CENTER);
        GridPane.setHalignment(btnMoverAbajo, HPos.CENTER);

        btnAtacar         = b("SPACE  ATACAR", "btn-danger");
        btnRecoger        = b("R  RECOGER", "");
        btnUsar           = b("E  POCION", "btn-success");
        btnAbrirPuerta    = b("O  ABRIR PUERTA", "btn-primary");
        btnEquipar        = b("Q  EQUIPO", "");
        btnComprarCamino  = b("COMPRAR RUTA", "");
        btnFinalizarTurno = b("F  ESPERAR", "btn-success");

        btnGuardar = b("G  GUARDAR", "");
        btnCargar  = b("CARGAR", "btn-primary");
        btnNueva   = b("NUEVA PARTIDA", "btn-danger");

        for (Button b : new Button[]{btnAtacar, btnRecoger, btnUsar, btnAbrirPuerta, btnEquipar,
                                      btnComprarCamino, btnFinalizarTurno,
                                      btnGuardar, btnCargar, btnNueva,
                                      btnMoverArriba, btnMoverAbajo, btnMoverIzquierda, btnMoverDerecha}) {
            b.setMaxWidth(Double.MAX_VALUE);
            b.setFocusTraversable(false);
        }

        getChildren().addAll(titulo,
                             subtitulo("MOVIMIENTO"), dirPad,
                             subtitulo("COMBATE"), btnAtacar,
                             subtitulo("INTERACCION"), btnRecoger, btnUsar, btnAbrirPuerta, btnEquipar, btnComprarCamino,
                             subtitulo("TURNO Y PARTIDA"), btnFinalizarTurno, btnGuardar, btnCargar, btnNueva);
    }

    private Label subtitulo(String texto) {
        Label label = new Label(texto);
        label.getStyleClass().add("actions-subtitle");
        label.setAlignment(Pos.CENTER_LEFT);
        return label;
    }

    private Button b(String text, String extraStyle) {
        Button btn = new Button(text);
        if (!extraStyle.isEmpty()) btn.getStyleClass().addAll("button", extraStyle);
        else btn.getStyleClass().add("button");
        return btn;
    }

    public void setOnMoverArriba(Runnable r) { btnMoverArriba.setOnAction(e -> r.run()); }
    public void setOnMoverAbajo(Runnable r) { btnMoverAbajo.setOnAction(e -> r.run()); }
    public void setOnMoverIzquierda(Runnable r) { btnMoverIzquierda.setOnAction(e -> r.run()); }
    public void setOnMoverDerecha(Runnable r) { btnMoverDerecha.setOnAction(e -> r.run()); }
    public void setOnAtacar(Runnable r) { btnAtacar.setOnAction(e -> r.run()); }
    public void setOnRecoger(Runnable r) { btnRecoger.setOnAction(e -> r.run()); }
    public void setOnUsar(Runnable r) { btnUsar.setOnAction(e -> r.run()); }
    public void setOnAbrirPuerta(Runnable r) { btnAbrirPuerta.setOnAction(e -> r.run()); }
    public void setOnEquipar(Runnable r) { btnEquipar.setOnAction(e -> r.run()); }
    public void setOnComprarCamino(Runnable r) { btnComprarCamino.setOnAction(e -> r.run()); }
    public void setOnFinalizarTurno(Runnable r) { btnFinalizarTurno.setOnAction(e -> r.run()); }
    public void setOnGuardar(Runnable r) { btnGuardar.setOnAction(e -> r.run()); }
    public void setOnCargar(Runnable r) { btnCargar.setOnAction(e -> r.run()); }
    public void setOnNueva(Runnable r) { btnNueva.setOnAction(e -> r.run()); }

    public void actualizar(Partida partida, boolean puedeMover, boolean puedeAtacar, boolean puedeRecoger,
                           boolean puedeUsar, boolean puedeAbrirPuerta, boolean puedeEquipar,
                           boolean puedeComprarCamino) {
        boolean terminada = partida == null || partida.getEstado() != EstadoPartida.EN_CURSO;
        btnMoverArriba.setDisable(terminada || !puedeMover);
        btnMoverAbajo.setDisable(terminada || !puedeMover);
        btnMoverIzquierda.setDisable(terminada || !puedeMover);
        btnMoverDerecha.setDisable(terminada || !puedeMover);
        btnAtacar.setDisable(terminada || !puedeAtacar);
        btnRecoger.setDisable(terminada || !puedeRecoger);
        btnUsar.setDisable(terminada || !puedeUsar);
        btnAbrirPuerta.setDisable(terminada || !puedeAbrirPuerta);
        btnEquipar.setDisable(terminada || !puedeEquipar);
        btnComprarCamino.setDisable(terminada || !puedeComprarCamino);
        btnFinalizarTurno.setDisable(terminada);
        btnGuardar.setDisable(terminada);
    }
}

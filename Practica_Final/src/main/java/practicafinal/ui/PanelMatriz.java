package practicafinal.ui;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import practicafinal.model.Celda;
import practicafinal.model.Enemigo;
import practicafinal.model.Habitacion;
import practicafinal.model.Jugador;
import practicafinal.model.Objeto;
import practicafinal.model.TipoCelda;
import practicafinal.model.TipoObjeto;
import practicafinal.structures.ListaEnlazada;

public class PanelMatriz extends VBox {
    static final int TAM_CELDA = 72;

    private final GridPane grid;
    private final HBox legendBox;
    private final Label title;
    private CeldaClickHandler onCeldaClick;

    public interface CeldaClickHandler {
        void aceptar(int fila, int columna);
    }

    public PanelMatriz() {
        setSpacing(8);
        setPadding(new Insets(0));
        setAlignment(Pos.CENTER);
        getStyleClass().add("map-panel");

        title = new Label("Mapa de la Habitacion");
        title.getStyleClass().add("map-title");

        grid = new GridPane();
        grid.setHgap(1.5);
        grid.setVgap(1.5);
        grid.getStyleClass().add("map-grid");

        legendBox = new HBox(12);
        legendBox.getStyleClass().add("legend-box");
        legendBox.setPadding(new Insets(6, 10, 6, 10));

        getChildren().addAll(title, grid, legendBox);
    }

    public void setOnCeldaClick(CeldaClickHandler onCeldaClick) {
        this.onCeldaClick = onCeldaClick;
    }

    public void actualizar(Habitacion habitacion, Jugador jugador, ListaEnlazada<Enemigo> enemigos,
                            ListaEnlazada<Objeto> objetos,
                            ListaEnlazada<int[]> casillasAlcanzables, ListaEnlazada<int[]> caminoResaltado,
                            ListaEnlazada<int[]> casillasVisibles) {
        grid.getChildren().clear();
        legendBox.getChildren().clear();
        if (habitacion == null) return;

        int filas = habitacion.getFilas();
        int columnas = habitacion.getColumnas();
        title.setText("LA CONQUISTA - " + formatearNombreSala(habitacion.getId()) + "  [" + filas + "x" + columnas + "]");
        aplicarTemaSala(habitacion.getId());

        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                Celda celda = habitacion.getCelda(f, c);
                boolean esJugador = jugador != null && jugador.getFila() == f && jugador.getColumna() == c;
                boolean visible = esJugador || contienePosicion(casillasVisibles, f, c);
                Enemigo enemigoAqui = visible ? buscarEnemigo(enemigos, habitacion.getId(), f, c, esJugador) : null;
                Objeto objetoAqui = visible ? buscarObjeto(objetos, habitacion.getId(), f, c) : null;
                boolean esCamino = visible && contienePosicion(caminoResaltado, f, c);
                boolean esAlcanzable = visible && !esCamino && contienePosicion(casillasAlcanzables, f, c);

                boolean heroeConArma = esJugador && jugador.getArmaEquipada() != null;
                String skin = esJugador && jugador.getSkin() != null ? jugador.getSkin() : "hero";
                StackPane cell = crearCelda(visible ? tipoParaMostrar(celda.getTipo(), enemigoAqui != null) : null,
                                            esJugador, heroeConArma, skin, enemigoAqui, objetoAqui, esAlcanzable, esCamino, f, c);
                grid.add(cell, c, f);
            }
        }
        construirLegend();
    }

    private Enemigo buscarEnemigo(ListaEnlazada<Enemigo> enemigos, String habitacionId,
                                  int fila, int columna, boolean hayJugador) {
        if (hayJugador || enemigos == null) return null;
        for (int i = 0; i < enemigos.tamano(); i++) {
            Enemigo e = enemigos.obtener(i);
            if (e.estaVivo() && habitacionId.equals(e.getHabitacionId())
                && e.getFila() == fila && e.getColumna() == columna) return e;
        }
        return null;
    }

    private boolean contienePosicion(ListaEnlazada<int[]> posiciones, int fila, int columna) {
        if (posiciones == null) return false;
        for (int i = 0; i < posiciones.tamano(); i++) {
            int[] p = posiciones.obtener(i);
            if (p[0] == fila && p[1] == columna) return true;
        }
        return false;
    }

    private Objeto buscarObjeto(ListaEnlazada<Objeto> objetos, String habitacionId, int fila, int columna) {
        if (objetos == null) return null;
        for (int i = 0; i < objetos.tamano(); i++) {
            Objeto obj = objetos.obtener(i);
            if (habitacionId.equals(obj.getHabitacionId()) && obj.getFila() == fila && obj.getColumna() == columna)
                return obj;
        }
        return null;
    }

    private TipoCelda tipoParaMostrar(TipoCelda original, boolean hayEnemigoVivo) {
        return hayEnemigoVivo ? TipoCelda.ENEMIGO : original;
    }

    private StackPane crearCelda(TipoCelda tipo, boolean esJugador, boolean heroeConArma, String skin, Enemigo enemigo, Objeto objeto,
                                 boolean esAlcanzable, boolean esCamino, int fila, int columna) {
        Visual visual = visualPara(tipo, esJugador, heroeConArma, skin, enemigo, objeto, esCamino);

        StackPane cell = new StackPane();
        cell.setMinSize(TAM_CELDA, TAM_CELDA);
        cell.setPrefSize(TAM_CELDA, TAM_CELDA);
        cell.setMaxSize(TAM_CELDA, TAM_CELDA);
        cell.getStyleClass().addAll("tile", visual.claseCss);
        if (esAlcanzable) cell.getStyleClass().add("tile-reachable");
        if (esCamino) cell.getStyleClass().add("tile-path");

        Rectangle vignette = new Rectangle(TAM_CELDA - 5, TAM_CELDA - 5);
        vignette.setArcWidth(12);
        vignette.setArcHeight(12);
        vignette.setFill(Color.TRANSPARENT);
        if (esJugador || enemigo != null || objeto != null || tipo == TipoCelda.OBJETO) {
            vignette.setStroke(Color.TRANSPARENT);
            vignette.setStrokeWidth(0);
            cell.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-effect: none;");
        } else {
            vignette.setStroke(visual.borde);
            vignette.setStrokeWidth(visual.bordeAncho);
        }
        vignette.setMouseTransparent(true);

        int spriteSize = esJugador || enemigo != null ? 44 : 48;
        Canvas sprite = PixelArt.crear(visual.sprite, spriteSize);
        sprite.setEffect(new DropShadow(8, Color.rgb(0, 0, 0, 0.85)));

        StackPane spriteLayer = new StackPane();
        boolean entidadSobreSuelo = esJugador || enemigo != null || objeto != null || tipo == TipoCelda.OBJETO;
        if (entidadSobreSuelo) {
            Canvas floorBg = PixelArt.crear("floor", 48);
            spriteLayer.getChildren().add(floorBg);
        }
        spriteLayer.getChildren().add(sprite);
        StackPane.setAlignment(sprite, Pos.CENTER);

        VBox contenido = new VBox(0);
        contenido.setAlignment(Pos.CENTER);
        contenido.getChildren().add(spriteLayer);

        if (enemigo != null) {
            Label hp = new Label("HP " + enemigo.getVida());
            hp.getStyleClass().add("enemy-hp-badge");
            contenido.getChildren().add(hp);
        }

        if (esJugador) {
            Label halo = new Label("HEROE");
            halo.getStyleClass().add("player-badge");
            StackPane.setAlignment(halo, Pos.BOTTOM_CENTER);
            cell.getChildren().addAll(vignette, contenido, halo);
        } else {
            cell.getChildren().addAll(vignette, contenido);
        }

        if (tipo == null) {
            Rectangle fogTexture = new Rectangle(TAM_CELDA - 12, TAM_CELDA - 12);
            fogTexture.getStyleClass().add("fog-texture");
            fogTexture.setMouseTransparent(true);
            fogTexture.setArcWidth(18);
            fogTexture.setArcHeight(18);
            cell.getChildren().add(fogTexture);
        }

        if (esCamino) {
            Label marker = new Label("RUTA");
            marker.getStyleClass().add("path-marker");
            marker.setMouseTransparent(true);
            StackPane.setAlignment(marker, Pos.TOP_CENTER);
            cell.getChildren().add(marker);
        } else if (esAlcanzable) {
            Circle marker = new Circle(6);
            marker.getStyleClass().add("reachable-marker");
            marker.setMouseTransparent(true);
            StackPane.setAlignment(marker, Pos.BOTTOM_RIGHT);
            StackPane.setMargin(marker, new Insets(0, 7, 7, 0));
            cell.getChildren().add(marker);
        }

        String tooltip = tooltipPara(visual.nombre, enemigo, objeto, fila, columna);
        if (esAlcanzable) tooltip += "\nHaz clic para moverte hasta aqui.";
        Tooltip.install(cell, new Tooltip(tooltip));
        if (esAlcanzable && onCeldaClick != null) {
            cell.setOnMouseClicked(e -> onCeldaClick.aceptar(fila, columna));
            cell.getStyleClass().add("tile-clickable");
        }
        animarCelda(cell, esJugador, enemigo != null, esAlcanzable, esCamino);
        return cell;
    }

    private void animarCelda(StackPane cell, boolean esJugador, boolean esEnemigo, boolean esAlcanzable, boolean esCamino) {
        if (esJugador || esEnemigo || esAlcanzable || esCamino) {
            ScaleTransition pulse = new ScaleTransition(Duration.millis(esJugador ? 520 : 300), cell);
            pulse.setFromX(esJugador ? 0.88 : 0.95);
            pulse.setFromY(esJugador ? 0.88 : 0.95);
            pulse.setToX(1.0);
            pulse.setToY(1.0);
            pulse.play();
        }
    }

    private String tooltipPara(String nombre, Enemigo enemigo, Objeto objeto, int fila, int columna) {
        if (enemigo != null) {
            return enemigo.getNombre() + "\nVida: " + enemigo.getVida()
                + " | Ataque: " + enemigo.getAtaque() + " | Defensa: " + enemigo.getDefensa()
                + "\nPosicion: (" + fila + "," + columna + ")";
        }
        if (objeto != null) {
            return objeto.getNombre() + "\nTipo: " + objeto.getTipo().name()
                + (objeto.getAtaque() > 0 ? " | Ataque +" + objeto.getAtaque() : "")
                + (objeto.getDefensa() > 0 ? " | Defensa +" + objeto.getDefensa() : "")
                + (objeto.getCuracion() > 0 ? " | Cura +" + objeto.getCuracion() : "")
                + "\nRecogelo desde una casilla adyacente.";
        }
        return nombre + "\nPosicion: (" + fila + "," + columna + ")";
    }

    private Visual visualPara(TipoCelda tipo, boolean esJugador, boolean heroeConArma, String skin, Enemigo enemigo, Objeto objeto,
                              boolean esCamino) {
        if (tipo == null) return new Visual("?", "shadow", "tile-shadow", Color.rgb(20, 22, 30), Color.rgb(8, 8, 12), 0.5, "Zona no visible");
        if (esJugador) {
            String sprite = heroeConArma ? skin + "Sword" : skin;
            return new Visual("♞", sprite, "tile-player", Color.rgb(190, 230, 255), Color.rgb(75, 190, 255), 2.8, "Jugador");
        }
        if (enemigo != null && enemigo.getNombre() != null && enemigo.getNombre().toLowerCase().contains("conquista"))
            return new Visual("♛", "boss", "tile-enemy", Color.rgb(255, 230, 160), Color.rgb(255, 80, 95), 3.0, "Jefe final");
        if (enemigo != null) return new Visual("X", spriteEnemigo(enemigo), "tile-enemy", Color.rgb(255, 210, 210), Color.rgb(235, 68, 85), 2.5, "Enemigo");
        if (esCamino) return new Visual("*", "exit", "tile-path-base", Color.rgb(225, 190, 255), Color.rgb(178, 120, 255), 2.0, "Camino comprado");

        switch (tipo) {
            case MURO:
                return new Visual("", "wall", "tile-wall", Color.rgb(70, 75, 88), Color.rgb(30, 34, 42), 1.0, "Muro de piedra");
            case OBJETO:
                return visualObjeto(objeto);
            case TRAMPA:
                return new Visual("!", "trap", "tile-trap", Color.rgb(255, 175, 105), Color.rgb(255, 120, 45), 2.0, "Trampa oculta");
            case PUERTA:
                return new Visual("▣", "door", "tile-door", Color.rgb(255, 212, 140), Color.rgb(158, 101, 43), 2.0, "Puerta a otra sala");
            case SALIDA:
                return new Visual("◎", "exit", "tile-exit", Color.rgb(255, 255, 220), Color.rgb(255, 226, 88), 2.8, "Salida exterior");
            case ENEMIGO:
                return new Visual("X", "enemy", "tile-enemy", Color.rgb(255, 210, 210), Color.rgb(235, 68, 85), 2.5, "Enemigo");
            default:
                return new Visual("", "floor", "tile-floor", Color.rgb(90, 98, 112), Color.rgb(37, 46, 58), 0.8, "Suelo antiguo");
        }
    }

    private String spriteEnemigo(Enemigo enemigo) {
        if (enemigo == null || enemigo.getNombre() == null) return "enemy";
        String nombre = enemigo.getNombre().toLowerCase();
        if (nombre.contains("limo")) return "slime";
        if (nombre.contains("esqueleto")) return "skeleton";
        if (nombre.contains("zombi") || nombre.contains("zombie")) return "zombie";
        if (nombre.contains("cultista")) return "cultist";
        if (nombre.contains("bruto")) return "brute";
        return "enemy";
    }

    private Visual visualObjeto(Objeto objeto) {
        if (objeto == null || objeto.getTipo() == null)
            return new Visual("◆", "item", "tile-item", Color.rgb(255, 235, 140), Color.rgb(255, 202, 64), 2.0, "Objeto recogible");
        if (objeto.getTipo() == TipoObjeto.POCION)
            return new Visual("+", "potion", "tile-item", Color.rgb(180, 255, 190), Color.rgb(85, 235, 120), 2.0, "Pocion");
        if (objeto.getTipo() == TipoObjeto.ARMA)
            return new Visual("†", "item", "tile-item", Color.rgb(255, 230, 170), Color.rgb(255, 190, 80), 2.0, "Arma");
        if (objeto.getTipo() == TipoObjeto.ESCUDO)
            return new Visual("⬟", "shield", "tile-item", Color.rgb(190, 230, 255), Color.rgb(95, 180, 255), 2.0, "Escudo");
        if (objeto.getTipo() == TipoObjeto.LLAVE)
            return new Visual("⚿", "key", "tile-key", Color.rgb(255, 240, 150), Color.rgb(255, 215, 80), 2.5, "Llave importante");
        return new Visual("◆", "item", "tile-item", Color.rgb(255, 235, 140), Color.rgb(255, 202, 64), 2.0, "Objeto recogible");
    }

    private void construirLegend() {
        legendBox.getChildren().clear();
        legendBox.getChildren().addAll(
            leg("♞", "Heroe", "tile-player"),
            leg("X", "Enemigo", "tile-enemy"),
            leg("◆", "Objeto", "tile-item"),
            leg("▣", "Puerta", "tile-door"),
            leg("!", "Trampa", "tile-trap"),
            leg("◎", "Salida", "tile-exit"),
            leg("", "Alcance", "tile-reachable"),
            leg("", "Ruta", "tile-path-base"),
            leg("", "Muro", "tile-wall"),
            leg("", "Suelo", "tile-floor"),
            leg("", "Sombra", "tile-shadow")
        );
    }

    private void aplicarTemaSala(String id) {
        grid.getStyleClass().removeIf(c -> c.startsWith("room-theme-"));
        grid.getStyleClass().add(claseTemaSala(id));
    }

    private String claseTemaSala(String id) {
        if (id == null) return "room-theme-default";
        String normalizado = id.toLowerCase();
        if (normalizado.contains("celda")) return "room-theme-cell";
        if (normalizado.contains("pasillo")) return "room-theme-corridor";
        if (normalizado.contains("biblioteca")) return "room-theme-library";
        if (normalizado.contains("oscura")) return "room-theme-dark";
        if (normalizado.contains("pinchos")) return "room-theme-spikes";
        if (normalizado.contains("santuario")) return "room-theme-red";
        if (normalizado.contains("jefe")) return "room-theme-boss";
        return "room-theme-default";
    }

    private HBox leg(String simbolo, String name, String claseCss) {
        StackPane tile = new StackPane();
        tile.setMinSize(24, 24);
        tile.setPrefSize(24, 24);
        tile.setMaxSize(24, 24);
        tile.getStyleClass().addAll("legend-tile", claseCss);

        Label icon = new Label(simbolo);
        icon.getStyleClass().add("legend-icon");
        tile.getChildren().add(icon);

        Label t = new Label(name);
        t.getStyleClass().add("legend-label");
        HBox item = new HBox(5, tile, t);
        item.setAlignment(Pos.CENTER_LEFT);
        return item;
    }

    public void animarAtaque(int fila, int columna) {
        StackPane cell = buscarCelda(fila, columna);
        if (cell == null) return;
        RotateTransition sacudida = new RotateTransition(Duration.millis(80), cell);
        sacudida.setFromAngle(0);
        sacudida.setToAngle(6);
        sacudida.setAutoReverse(true);
        sacudida.setCycleCount(2);
        sacudida.play();
        ScaleTransition golpe = new ScaleTransition(Duration.millis(120), cell);
        golpe.setFromX(1.0); golpe.setFromY(1.0);
        golpe.setToX(1.12); golpe.setToY(1.12);
        golpe.setAutoReverse(true);
        golpe.setCycleCount(2);
        golpe.setDelay(Duration.millis(30));
        golpe.play();
        FadeTransition flash = new FadeTransition(Duration.millis(100), cell);
        flash.setFromValue(1.0); flash.setToValue(0.5);
        flash.setAutoReverse(true);
        flash.setCycleCount(2);
        flash.setDelay(Duration.millis(40));
        flash.play();

        Label slash = new Label("/");
        slash.getStyleClass().add("attack-slash");
        slash.setMouseTransparent(true);
        Canvas sword = PixelArt.crear("slashSword", 58);
        sword.getStyleClass().add("attack-sword");
        sword.setMouseTransparent(true);
        sword.setRotate(-35);
        cell.getChildren().addAll(sword, slash);

        ScaleTransition slashScale = new ScaleTransition(Duration.millis(220), slash);
        slashScale.setFromX(0.5);
        slashScale.setFromY(0.5);
        slashScale.setToX(1.4);
        slashScale.setToY(1.4);
        slashScale.setInterpolator(Interpolator.EASE_OUT);

        FadeTransition slashFade = new FadeTransition(Duration.millis(220), slash);
        slashFade.setFromValue(1.0);
        slashFade.setToValue(0.0);
        slashFade.setOnFinished(e -> cell.getChildren().remove(slash));
        slashScale.play();
        slashFade.play();

        Timeline swordAnim = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(sword.rotateProperty(), -55),
                new KeyValue(sword.translateXProperty(), -18),
                new KeyValue(sword.translateYProperty(), -14),
                new KeyValue(sword.opacityProperty(), 1.0)
            ),
            new KeyFrame(Duration.millis(95),
                new KeyValue(sword.rotateProperty(), 25, Interpolator.EASE_OUT),
                new KeyValue(sword.translateXProperty(), 12, Interpolator.EASE_OUT),
                new KeyValue(sword.translateYProperty(), 10, Interpolator.EASE_OUT),
                new KeyValue(sword.opacityProperty(), 1.0)
            ),
            new KeyFrame(Duration.millis(230),
                new KeyValue(sword.rotateProperty(), 45, Interpolator.EASE_OUT),
                new KeyValue(sword.translateXProperty(), 18, Interpolator.EASE_OUT),
                new KeyValue(sword.translateYProperty(), 16, Interpolator.EASE_OUT),
                new KeyValue(sword.opacityProperty(), 0.0, Interpolator.EASE_OUT)
            )
        );
        swordAnim.setOnFinished(e -> cell.getChildren().remove(sword));
        swordAnim.play();
    }

    public void animarAtaqueDesdeHasta(int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino,
                                       boolean proyectil, boolean ataqueJugador) {
        StackPane origen = buscarCelda(filaOrigen, columnaOrigen);
        StackPane destino = buscarCelda(filaDestino, columnaDestino);
        if (origen == null || destino == null) {
            animarAtaque(filaDestino, columnaDestino);
            return;
        }

        if (proyectil) {
            animarProyectil(origen, destino, filaOrigen, columnaOrigen, filaDestino, columnaDestino, ataqueJugador);
        } else {
            animarGolpeCuerpoACuerpo(origen, destino, filaOrigen, columnaOrigen, filaDestino, columnaDestino, ataqueJugador);
        }
    }

    private void animarGolpeCuerpoACuerpo(StackPane origen, StackPane destino, int filaOrigen, int columnaOrigen,
                                          int filaDestino, int columnaDestino, boolean ataqueJugador) {
        double dx = (columnaDestino - columnaOrigen) * (TAM_CELDA + grid.getHgap());
        double dy = (filaDestino - filaOrigen) * (TAM_CELDA + grid.getVgap());

        Canvas weapon = PixelArt.crear(ataqueJugador ? "slashSword" : "clawSlash", 62);
        weapon.getStyleClass().add(ataqueJugador ? "attack-sword" : "enemy-claw");
        weapon.setMouseTransparent(true);
        weapon.setRotate(anguloAtaque(dx, dy) - 40);
        weapon.setTranslateX(dx * 0.25);
        weapon.setTranslateY(dy * 0.25);
        origen.getChildren().add(weapon);

        Timeline weaponAnim = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(weapon.translateXProperty(), dx * 0.15),
                new KeyValue(weapon.translateYProperty(), dy * 0.15),
                new KeyValue(weapon.rotateProperty(), anguloAtaque(dx, dy) - 70),
                new KeyValue(weapon.opacityProperty(), 1.0),
                new KeyValue(weapon.scaleXProperty(), 0.8),
                new KeyValue(weapon.scaleYProperty(), 0.8)
            ),
            new KeyFrame(Duration.millis(95),
                new KeyValue(weapon.translateXProperty(), dx * 0.55, Interpolator.EASE_OUT),
                new KeyValue(weapon.translateYProperty(), dy * 0.55, Interpolator.EASE_OUT),
                new KeyValue(weapon.rotateProperty(), anguloAtaque(dx, dy) + 25, Interpolator.EASE_OUT),
                new KeyValue(weapon.scaleXProperty(), 1.35, Interpolator.EASE_OUT),
                new KeyValue(weapon.scaleYProperty(), 1.35, Interpolator.EASE_OUT)
            ),
            new KeyFrame(Duration.millis(230),
                new KeyValue(weapon.translateXProperty(), dx * 0.72, Interpolator.EASE_OUT),
                new KeyValue(weapon.translateYProperty(), dy * 0.72, Interpolator.EASE_OUT),
                new KeyValue(weapon.opacityProperty(), 0.0, Interpolator.EASE_OUT),
                new KeyValue(weapon.scaleXProperty(), 1.55, Interpolator.EASE_OUT),
                new KeyValue(weapon.scaleYProperty(), 1.55, Interpolator.EASE_OUT)
            )
        );
        weaponAnim.setOnFinished(e -> origen.getChildren().remove(weapon));
        weaponAnim.play();

        animarImpacto(destino, ataqueJugador ? "impact-player" : "impact-enemy");
    }

    private void animarProyectil(StackPane origen, StackPane destino, int filaOrigen, int columnaOrigen,
                                 int filaDestino, int columnaDestino, boolean ataqueJugador) {
        double dx = (columnaDestino - columnaOrigen) * (TAM_CELDA + grid.getHgap());
        double dy = (filaDestino - filaOrigen) * (TAM_CELDA + grid.getVgap());

        Circle projectile = new Circle(ataqueJugador ? 6 : 7);
        projectile.getStyleClass().add(ataqueJugador ? "player-projectile" : "enemy-projectile");
        projectile.setMouseTransparent(true);
        origen.getChildren().add(projectile);

        Rectangle trail = new Rectangle(28, 5);
        trail.getStyleClass().add(ataqueJugador ? "player-projectile-trail" : "enemy-projectile-trail");
        trail.setMouseTransparent(true);
        trail.setRotate(anguloAtaque(dx, dy));
        origen.getChildren().add(trail);

        Timeline projectileAnim = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(projectile.translateXProperty(), 0),
                new KeyValue(projectile.translateYProperty(), 0),
                new KeyValue(projectile.opacityProperty(), 1.0),
                new KeyValue(trail.translateXProperty(), -dx * 0.08),
                new KeyValue(trail.translateYProperty(), -dy * 0.08),
                new KeyValue(trail.opacityProperty(), 0.0)
            ),
            new KeyFrame(Duration.millis(70),
                new KeyValue(trail.opacityProperty(), 0.8, Interpolator.EASE_OUT)
            ),
            new KeyFrame(Duration.millis(280),
                new KeyValue(projectile.translateXProperty(), dx, Interpolator.EASE_BOTH),
                new KeyValue(projectile.translateYProperty(), dy, Interpolator.EASE_BOTH),
                new KeyValue(projectile.scaleXProperty(), 1.25, Interpolator.EASE_OUT),
                new KeyValue(projectile.scaleYProperty(), 1.25, Interpolator.EASE_OUT),
                new KeyValue(trail.translateXProperty(), dx * 0.65, Interpolator.EASE_BOTH),
                new KeyValue(trail.translateYProperty(), dy * 0.65, Interpolator.EASE_BOTH),
                new KeyValue(trail.opacityProperty(), 0.55, Interpolator.EASE_OUT)
            ),
            new KeyFrame(Duration.millis(360),
                new KeyValue(projectile.opacityProperty(), 0.0, Interpolator.EASE_OUT),
                new KeyValue(projectile.scaleXProperty(), 1.9, Interpolator.EASE_OUT),
                new KeyValue(projectile.scaleYProperty(), 1.9, Interpolator.EASE_OUT),
                new KeyValue(trail.opacityProperty(), 0.0, Interpolator.EASE_OUT)
            )
        );
        projectileAnim.setOnFinished(e -> {
            origen.getChildren().remove(projectile);
            origen.getChildren().remove(trail);
        });
        projectileAnim.play();

        Timeline delayedImpact = new Timeline(new KeyFrame(Duration.millis(280), e ->
            animarImpacto(destino, ataqueJugador ? "impact-player" : "impact-enemy")));
        delayedImpact.play();
    }

    private double anguloAtaque(double dx, double dy) {
        return Math.toDegrees(Math.atan2(dy, dx));
    }

    private void animarImpacto(StackPane cell, String styleClass) {
        Circle burst = new Circle(5);
        burst.getStyleClass().add(styleClass);
        burst.setMouseTransparent(true);
        cell.getChildren().add(burst);

        Timeline burstAnim = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(burst.radiusProperty(), 5),
                new KeyValue(burst.opacityProperty(), 1.0),
                new KeyValue(burst.scaleXProperty(), 0.7),
                new KeyValue(burst.scaleYProperty(), 0.7)
            ),
            new KeyFrame(Duration.millis(260),
                new KeyValue(burst.radiusProperty(), TAM_CELDA * 0.42, Interpolator.EASE_OUT),
                new KeyValue(burst.opacityProperty(), 0.0, Interpolator.EASE_OUT),
                new KeyValue(burst.scaleXProperty(), 1.25, Interpolator.EASE_OUT),
                new KeyValue(burst.scaleYProperty(), 1.25, Interpolator.EASE_OUT)
            )
        );
        burstAnim.setOnFinished(e -> cell.getChildren().remove(burst));
        burstAnim.play();

        ScaleTransition hit = new ScaleTransition(Duration.millis(90), cell);
        hit.setFromX(1.0);
        hit.setFromY(1.0);
        hit.setToX(1.08);
        hit.setToY(1.08);
        hit.setAutoReverse(true);
        hit.setCycleCount(2);
        hit.play();
    }

    public void animarPuerta(int fila, int columna) {
        StackPane cell = buscarCelda(fila, columna);
        if (cell == null) return;
        RotateTransition giro = new RotateTransition(Duration.millis(260), cell);
        giro.setFromAngle(0); giro.setToAngle(10);
        giro.setAutoReverse(true); giro.setCycleCount(2);
        giro.setInterpolator(javafx.animation.Interpolator.EASE_BOTH);
        giro.play();
        ScaleTransition pulse = new ScaleTransition(Duration.millis(200), cell);
        pulse.setFromX(1.0); pulse.setFromY(1.0);
        pulse.setToX(1.06); pulse.setToY(1.06);
        pulse.setAutoReverse(true); pulse.setCycleCount(2);
        pulse.play();
    }

    public void animarMovimiento(int fila, int columna) {
        StackPane cell = buscarCelda(fila, columna);
        if (cell == null) return;

        ScaleTransition squash = new ScaleTransition(Duration.millis(120), cell);
        squash.setFromX(1.0); squash.setFromY(1.0);
        squash.setToX(1.12); squash.setToY(0.88);
        squash.setInterpolator(Interpolator.EASE_OUT);

        ScaleTransition stretch = new ScaleTransition(Duration.millis(160), cell);
        stretch.setFromX(1.12); stretch.setFromY(0.88);
        stretch.setToX(0.96); stretch.setToY(1.04);
        stretch.setInterpolator(Interpolator.EASE_OUT);

        ScaleTransition settle = new ScaleTransition(Duration.millis(120), cell);
        settle.setFromX(0.96); settle.setFromY(1.04);
        settle.setToX(1.0); settle.setToY(1.0);
        settle.setInterpolator(Interpolator.EASE_OUT);

        squash.setOnFinished(e -> stretch.play());
        stretch.setOnFinished(e -> settle.play());
        squash.play();

        RotateTransition wobble = new RotateTransition(Duration.millis(320), cell);
        wobble.setFromAngle(-4);
        wobble.setToAngle(4);
        wobble.setAutoReverse(true);
        wobble.setCycleCount(4);
        wobble.setInterpolator(Interpolator.EASE_BOTH);
        wobble.play();

        Rectangle flash = new Rectangle(TAM_CELDA - 5, TAM_CELDA - 5);
        flash.setFill(Color.rgb(200, 230, 255, 0.35));
        flash.setMouseTransparent(true);
        flash.setArcWidth(12);
        flash.setArcHeight(12);
        cell.getChildren().add(flash);

        FadeTransition flashFade = new FadeTransition(Duration.millis(350), flash);
        flashFade.setFromValue(0.35);
        flashFade.setToValue(0.0);
        flashFade.setOnFinished(e -> cell.getChildren().remove(flash));
        flashFade.play();

        double half = TAM_CELDA / 2.0;
        Circle ring = new Circle(half, half, 3);
        ring.setFill(null);
        ring.setStroke(Color.rgb(180, 220, 255, 0.7));
        ring.setStrokeWidth(2.5);
        ring.setMouseTransparent(true);
        cell.getChildren().add(ring);

        Timeline ringAnim = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(ring.radiusProperty(), 3),
                new KeyValue(ring.opacityProperty(), 1.0),
                new KeyValue(ring.strokeWidthProperty(), 2.5, Interpolator.EASE_OUT)
            ),
            new KeyFrame(Duration.millis(450),
                new KeyValue(ring.radiusProperty(), half * 0.9, Interpolator.EASE_OUT),
                new KeyValue(ring.opacityProperty(), 0.0, Interpolator.EASE_OUT),
                new KeyValue(ring.strokeWidthProperty(), 0.5, Interpolator.EASE_OUT)
            )
        );
        ringAnim.setOnFinished(e -> cell.getChildren().remove(ring));
        ringAnim.play();

        for (int i = 0; i < 3; i++) {
            Circle dust = new Circle(2.5 + i);
            dust.getStyleClass().add("movement-dust");
            dust.setMouseTransparent(true);
            StackPane.setAlignment(dust, Pos.BOTTOM_CENTER);
            dust.setTranslateX((i - 1) * 10);
            dust.setTranslateY(-7);
            cell.getChildren().add(dust);

            Timeline dustAnim = new Timeline(
                new KeyFrame(Duration.ZERO,
                    new KeyValue(dust.translateYProperty(), -7),
                    new KeyValue(dust.opacityProperty(), 0.65),
                    new KeyValue(dust.scaleXProperty(), 0.7),
                    new KeyValue(dust.scaleYProperty(), 0.7)
                ),
                new KeyFrame(Duration.millis(360 + i * 60),
                    new KeyValue(dust.translateYProperty(), -20 - i * 3, Interpolator.EASE_OUT),
                    new KeyValue(dust.opacityProperty(), 0.0, Interpolator.EASE_OUT),
                    new KeyValue(dust.scaleXProperty(), 1.9, Interpolator.EASE_OUT),
                    new KeyValue(dust.scaleYProperty(), 1.9, Interpolator.EASE_OUT)
                )
            );
            dustAnim.setOnFinished(e -> cell.getChildren().remove(dust));
            dustAnim.play();
        }
    }

    public void animarRecoger(int fila, int columna) {
        StackPane cell = buscarCelda(fila, columna);
        if (cell == null) return;
        ScalePopup pop = new ScalePopup(cell);
        pop.play();

        Label spark = new Label("+");
        spark.getStyleClass().add("pickup-spark");
        spark.setMouseTransparent(true);
        StackPane.setAlignment(spark, Pos.TOP_RIGHT);
        StackPane.setMargin(spark, new Insets(3, 7, 0, 0));
        cell.getChildren().add(spark);

        Timeline sparkAnim = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(spark.translateYProperty(), 0),
                new KeyValue(spark.opacityProperty(), 1.0),
                new KeyValue(spark.scaleXProperty(), 0.8),
                new KeyValue(spark.scaleYProperty(), 0.8)
            ),
            new KeyFrame(Duration.millis(420),
                new KeyValue(spark.translateYProperty(), -18, Interpolator.EASE_OUT),
                new KeyValue(spark.opacityProperty(), 0.0, Interpolator.EASE_OUT),
                new KeyValue(spark.scaleXProperty(), 1.5, Interpolator.EASE_OUT),
                new KeyValue(spark.scaleYProperty(), 1.5, Interpolator.EASE_OUT)
            )
        );
        sparkAnim.setOnFinished(e -> cell.getChildren().remove(spark));
        sparkAnim.play();
    }

    public void animarDano(int fila, int columna) {
        StackPane cell = buscarCelda(fila, columna);
        if (cell == null) return;
        RotateTransition sac = new RotateTransition(Duration.millis(60), cell);
        sac.setFromAngle(0); sac.setToAngle(4);
        sac.setAutoReverse(true); sac.setCycleCount(3);
        sac.setInterpolator(javafx.animation.Interpolator.EASE_BOTH);
        sac.play();
        FadeTransition flash = new FadeTransition(Duration.millis(80), cell);
        flash.setFromValue(1.0); flash.setToValue(0.4);
        flash.setAutoReverse(true); flash.setCycleCount(3);
        flash.play();

        Rectangle hit = new Rectangle(TAM_CELDA - 4, TAM_CELDA - 4);
        hit.getStyleClass().add("damage-flash");
        hit.setMouseTransparent(true);
        hit.setArcWidth(12);
        hit.setArcHeight(12);
        cell.getChildren().add(hit);

        FadeTransition hitFade = new FadeTransition(Duration.millis(260), hit);
        hitFade.setFromValue(0.75);
        hitFade.setToValue(0.0);
        hitFade.setOnFinished(e -> cell.getChildren().remove(hit));
        hitFade.play();
    }

    private static class ScalePopup {
        private final javafx.animation.SequentialTransition seq;
        ScalePopup(StackPane cell) {
            ScaleTransition up = new ScaleTransition(Duration.millis(120), cell);
            up.setFromX(1.0); up.setFromY(1.0);
            up.setToX(1.15); up.setToY(1.15);
            up.setInterpolator(javafx.animation.Interpolator.EASE_OUT);
            FadeTransition glow = new FadeTransition(Duration.millis(120), cell);
            glow.setFromValue(1.0); glow.setToValue(1.3);
            ScaleTransition down = new ScaleTransition(Duration.millis(200), cell);
            down.setFromX(1.15); down.setFromY(1.15);
            down.setToX(1.0); down.setToY(1.0);
            down.setInterpolator(javafx.animation.Interpolator.EASE_IN);
            seq = new javafx.animation.SequentialTransition();
            seq.getChildren().addAll(up, glow, down);
        }
        void play() { seq.play(); }
    }

    private StackPane buscarCelda(int fila, int columna) {
        for (javafx.scene.Node node : grid.getChildren()) {
            Integer row = GridPane.getRowIndex(node);
            Integer col = GridPane.getColumnIndex(node);
            if ((row == null ? 0 : row) == fila && (col == null ? 0 : col) == columna && node instanceof StackPane)
                return (StackPane) node;
        }
        return null;
    }

    private String formatearNombreSala(String id) {
        if (id == null || id.isEmpty()) return "Sala desconocida";
        StringBuilder sb = new StringBuilder();
        boolean mayus = true;
        for (int i = 0; i < id.length(); i++) {
            char ch = id.charAt(i);
            if (ch == '_' || ch == '-') {
                sb.append(' ');
                mayus = true;
            } else {
                sb.append(mayus ? Character.toUpperCase(ch) : ch);
                mayus = false;
            }
        }
        return sb.toString();
    }

    private static class Visual {
        final String simbolo;
        final String sprite;
        final String claseCss;
        final Color colorTexto;
        final Color borde;
        final double bordeAncho;
        final String nombre;

        Visual(String simbolo, String sprite, String claseCss, Color colorTexto, Color borde, double bordeAncho, String nombre) {
            this.simbolo = simbolo;
            this.sprite = sprite;
            this.claseCss = claseCss;
            this.colorTexto = colorTexto;
            this.borde = borde;
            this.bordeAncho = bordeAncho;
            this.nombre = nombre;
        }
    }
}

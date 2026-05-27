package practicafinal.ui;

import java.io.IOException;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import practicafinal.model.Direccion;
import practicafinal.model.Enemigo;
import practicafinal.model.EstadoPartida;
import practicafinal.model.Habitacion;
import practicafinal.model.Jugador;
import practicafinal.model.Objeto;
import practicafinal.model.Partida;
import practicafinal.model.TipoCelda;
import practicafinal.model.TipoObjeto;
import practicafinal.logic.GestorTurnos;
import practicafinal.logic.MotorEnemigos;
import practicafinal.logic.MotorCombate;
import practicafinal.logic.MotorMovimiento;
import practicafinal.logic.Turnable;
import practicafinal.logic.GestorMusica;
import practicafinal.persistence.ConfiguracionDTO;
import practicafinal.persistence.GestorPersistencia;
import practicafinal.structures.Cola;
import practicafinal.structures.Grafo;
import practicafinal.structures.ListaEnlazada;

public class ControladorJuego {
    private static final int DANO_TRAMPA = 15;
    private static final int RADIO_VISION = 4;
    private static final int RADIO_PERCEPCION_ENEMIGA = 6;

    private final GestorPersistencia persistencia;
    private final MotorMovimiento motorMovimiento;
    private final MotorCombate motorCombate;
    private final MotorEnemigos motorEnemigos;
    private GestorTurnos gestorTurnos;
    private Partida partida;
    private ConfiguracionDTO configuracionActual;
    private ListaEnlazada<Habitacion> habitaciones;
    private ListaEnlazada<Objeto> objetosEnTablero;
    private Habitacion habitacionActual;
    private boolean haMovido;
    private boolean haActuado;
    private int pasosMovimientoUsados;
    private boolean finMostrado;
    private Runnable onFinPartida;
    private ListaEnlazada<int[]> casillasAlcanzables;
    private ListaEnlazada<int[]> caminoResaltado;
    private ListaEnlazada<int[]> casillasVisibles;

    private PanelMatriz panelMatriz;
    private PanelEstado panelEstado;
    private PanelInventario panelInventario;
    private PanelLog panelLog;
    private PanelAcciones panelAcciones;
    private VistaPrincipal vistaPrincipal;
    private GestorMusica musica;

    public ControladorJuego() {
        this.persistencia = new GestorPersistencia();
        this.motorMovimiento = new MotorMovimiento();
        this.motorCombate = new MotorCombate();
        this.motorEnemigos = new MotorEnemigos();
        this.casillasAlcanzables = new ListaEnlazada<>();
        this.caminoResaltado = new ListaEnlazada<>();
        this.casillasVisibles = new ListaEnlazada<>();
        this.objetosEnTablero = new ListaEnlazada<>();
    }

    public void setPaneles(PanelMatriz matriz, PanelEstado estado, PanelInventario inventario,
                           PanelLog log, PanelAcciones acciones) {
        this.panelMatriz = matriz;
        this.panelEstado = estado;
        this.panelInventario = inventario;
        this.panelLog = log;
        this.panelAcciones = acciones;
    }

    public void setVistaPrincipal(VistaPrincipal vp) { this.vistaPrincipal = vp; }
    public void setOnFinPartida(Runnable r) { this.onFinPartida = r; }
    public void setFinMostrado(boolean v) { this.finMostrado = v; }
    public void setMusica(GestorMusica m) { this.musica = m; }

    public void setSkin(String skin) {
        if (partida != null && partida.getJugador() != null) {
            partida.getJugador().setSkin(skin);
            actualizarPaneles();
        }
    }

    public boolean cargarConfiguracion(String ruta) {
        try {
            ConfiguracionDTO config = persistencia.cargarConfiguracion(ruta);
            configuracionActual = config;
            habitaciones = persistencia.crearHabitacionesDesdeConfig(config);

            objetosEnTablero = persistencia.crearObjetosDesdeConfig(config);

            partida = persistencia.crearPartidaDesdeConfig(config);
            partida.setHabitaciones(habitaciones);
            partida.setObjetosEnTablero(objetosEnTablero);
            finMostrado = false;
            habitacionActual = buscarHabitacion(partida.getHabitacionActual());
            if (habitacionActual == null && habitaciones.tamano() > 0)
                habitacionActual = habitaciones.obtener(0);

            colocarEnemigosEnHabitacion();
            colocarObjetosEnHabitacion();
            iniciarTurnos();
            partida.agregarEvento("Partida iniciada en " + partida.getHabitacionActual());
            partida.agregarEvento(descripcionSala(partida.getHabitacionActual()));
            actualizarPaneles();
            return true;
        } catch (IOException e) {
            if (panelLog != null) panelLog.agregarEvento("Error al cargar configuracion: " + e.getMessage());
            return false;
        }
    }

    private void colocarEnemigosEnHabitacion() {
        for (int i = 0; i < partida.getEnemigos().tamano(); i++) {
            Enemigo e = partida.getEnemigos().obtener(i);
            if (e.getHabitacionId().equals(partida.getHabitacionActual())) {
                Habitacion hab = buscarHabitacion(e.getHabitacionId());
                if (hab != null && hab.dentroDeLimites(e.getFila(), e.getColumna())) {
                    practicafinal.model.Celda celda = new practicafinal.model.Celda(TipoCelda.ENEMIGO);
                    celda.setOcupada(true);
                    hab.setCelda(e.getFila(), e.getColumna(), celda);
                }
            }
        }
    }

    private void colocarObjetosEnHabitacion() {
        for (int i = 0; i < objetosEnTablero.tamano(); i++) {
            Objeto obj = objetosEnTablero.obtener(i);
            Habitacion hab = buscarHabitacion(obj.getHabitacionId());
            if (hab != null && hab.dentroDeLimites(obj.getFila(), obj.getColumna())) {
                practicafinal.model.Celda celda = new practicafinal.model.Celda(TipoCelda.OBJETO);
                celda.setOcupada(false);
                hab.setCelda(obj.getFila(), obj.getColumna(), celda);
            }
        }
    }

    private Habitacion buscarHabitacion(String id) {
        for (int i = 0; i < habitaciones.tamano(); i++) {
            Habitacion h = habitaciones.obtener(i);
            if (h.getId().equals(id)) return h;
        }
        return null;
    }

    private void iniciarTurnos() {
        gestorTurnos = new GestorTurnos(partida.getJugador());
        for (int i = 0; i < partida.getEnemigos().tamano(); i++) {
            Enemigo e = partida.getEnemigos().obtener(i);
            if (e.getHabitacionId().equals(partida.getHabitacionActual()) && e.estaVivo())
                gestorTurnos.agregarEnemigo(e);
        }
        gestorTurnos.iniciarRonda();
        haMovido = false;
        haActuado = false;
        pasosMovimientoUsados = 0;
        actualizarVision();
        calcularCasillasAlcanzables();
        caminoResaltado = new ListaEnlazada<>();
        actualizarDistanciaMinima();
    }

    private void calcularCasillasAlcanzables() {
        Jugador j = partida.getJugador();
        int pasosRestantes = 1;
        ListaEnlazada<int[]> candidatas = motorMovimiento.casillasAlcanzables(
            habitacionActual, j.getFila(), j.getColumna(), pasosRestantes);
        casillasAlcanzables = new ListaEnlazada<>();
        for (int i = 0; i < candidatas.tamano(); i++) {
            int[] p = candidatas.obtener(i);
            if (contienePosicion(casillasVisibles, p[0], p[1])) casillasAlcanzables.agregar(p);
        }
    }

    private void actualizarVision() {
        casillasVisibles = new ListaEnlazada<>();
        if (partida == null || habitacionActual == null || partida.getJugador() == null) return;
        Jugador j = partida.getJugador();
        for (int f = 0; f < habitacionActual.getFilas(); f++) {
            for (int c = 0; c < habitacionActual.getColumnas(); c++) {
                int distancia = Math.abs(j.getFila() - f) + Math.abs(j.getColumna() - c);
                if (distancia <= RADIO_VISION && hayLineaVision(j.getFila(), j.getColumna(), f, c))
                    casillasVisibles.agregar(new int[]{f, c});
            }
        }
    }

    private boolean hayLineaVision(int f0, int c0, int f1, int c1) {
        int df = Integer.compare(f1, f0);
        int dc = Integer.compare(c1, c0);
        int f = f0;
        int c = c0;
        while (f != f1 || c != c1) {
            if (f != f1) f += df;
            if (c != c1) c += dc;
            if ((f != f1 || c != c1) && habitacionActual.getCelda(f, c).getTipo() == TipoCelda.MURO)
                return false;
        }
        return true;
    }

    private void actualizarDistanciaMinima() {
        Jugador j = partida.getJugador();
        int distPuerta = motorMovimiento.distanciaMinimaATipo(habitacionActual, j.getFila(), j.getColumna(), TipoCelda.PUERTA);
        int distSalida = motorMovimiento.distanciaMinimaATipo(habitacionActual, j.getFila(), j.getColumna(), TipoCelda.SALIDA);
        int dist = (distSalida >= 0) ? distSalida : distPuerta;
        partida.setDistanciaMinimaPasos(dist);

        Grafo<String> grafo = partida.getGrafoHabitaciones();
        if (grafo != null) {
            ListaEnlazada<String> rutaHabitaciones = grafo.bfs(partida.getHabitacionActual(), buscarIdSalida(grafo));
            int numHabitaciones = rutaHabitaciones.tamano() > 0 ? rutaHabitaciones.tamano() - 1 : 0;
            partida.setHabitacionesHastaSalida(numHabitaciones);
        } else {
            partida.setHabitacionesHastaSalida(-1);
        }

        partida.agregarEvento("Distancia a puerta/salida: " + dist +
                              " | Habitaciones hasta salida: " + partida.getHabitacionesHastaSalida());
    }

    private String buscarIdSalida(Grafo<String> grafo) {
        for (int i = 0; i < habitaciones.tamano(); i++) {
            Habitacion h = habitaciones.obtener(i);
            for (int f = 0; f < h.getFilas(); f++) {
                for (int c = 0; c < h.getColumnas(); c++) {
                    if (h.getCelda(f, c).getTipo() == TipoCelda.SALIDA)
                        return h.getId();
                }
            }
        }
        return null;
    }

    public void comprarCamino() {
        if (partida == null || partida.estaTerminada() || partida.getTurnosRestantes() < 3) {
            panelLog.agregarEvento("No tienes suficientes turnos para comprar el camino (min 3)");
            return;
        }

        Grafo<String> grafo = partida.getGrafoHabitaciones();
        if (grafo == null) {
            panelLog.agregarEvento("No hay grafo de habitaciones cargado");
            return;
        }

        String idSalida = buscarIdSalida(grafo);
        if (idSalida == null) {
            panelLog.agregarEvento("No hay salida en el mapa");
            return;
        }

        ListaEnlazada<String> ruta = grafo.bfs(partida.getHabitacionActual(), idSalida);
        if (ruta.tamano() == 0) {
            panelLog.agregarEvento("No hay ruta hasta la salida");
            return;
        }

        partida.setTurnosRestantes(partida.getTurnosRestantes() - 3);
        partida.setCaminoComprado(ruta);
        partida.setCaminoVisible(true);
        partida.agregarEvento("Camino comprado (3 turnos). Ruta: " + rutaToString(ruta));
        actualizarPaneles();
    }

    private String rutaToString(ListaEnlazada<String> ruta) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ruta.tamano(); i++) {
            if (i > 0) sb.append(" -> ");
            sb.append(ruta.obtener(i));
        }
        return sb.toString();
    }

    public void finalizarTurno() {
        if (partida == null || partida.estaTerminada()) return;
        haMovido = false;
        haActuado = false;
        pasosMovimientoUsados = 0;
        partida.agregarEvento("Esperas un tick. Los enemigos reaccionan.");
        finalizarTurnoJugador();
    }

    private void finalizarTurnoJugador() {
        partida.consumirTurno();
        if (partida.estaTerminada()) {
            actualizarPaneles();
            return;
        }
        int enemigosActuaron = 0;
        while (!gestorTurnos.rondaTerminada()) {
            Turnable siguiente = gestorTurnos.siguienteTurno();
            if (siguiente == null) break;
            if (siguiente == partida.getJugador()) continue;
            if (siguiente.estaVivo()) {
                siguiente.tomarTurno();
                ejecutarTurnoEnemigo((Enemigo) siguiente);
                enemigosActuaron++;
                if (partida.estaTerminada()) break;
            }
        }
        if (enemigosActuaron > 0)
            partida.agregarEvento("Turno enemigo resuelto: " + enemigosActuaron + " enemigo(s) actuaron.");
        if (!partida.estaTerminada()) {
            gestorTurnos.iniciarRonda();
            haMovido = false;
            haActuado = false;
            pasosMovimientoUsados = 0;
            actualizarVision();
            calcularCasillasAlcanzables();
            caminoResaltado = new ListaEnlazada<>();
        }
        actualizarPaneles();
    }

    private void ejecutarTurnoEnemigo(Enemigo e) {
        if (!habitacionActual.dentroDeLimites(e.getFila(), e.getColumna())) return;
        if (!e.getHabitacionId().equals(partida.getHabitacionActual())) return;
        Jugador j = partida.getJugador();
        int distancia = distanciaMinimaEntre(e.getFila(), e.getColumna(), j.getFila(), j.getColumna());
        if (puedeAtacarJugador(e, distancia)) {
            resolverAtaqueEnemigo(e, j, distancia);
        } else {
            if (!enemigoPercibeJugador(e, j)) return;
            int[] paso = buscarPasoEnemigoHaciaJugador(e, j);
            if (paso != null) {
                int nf = paso[0];
                int nc = paso[1];
                habitacionActual.getCelda(e.getFila(), e.getColumna()).setOcupada(false);
                habitacionActual.getCelda(e.getFila(), e.getColumna()).setTipo(TipoCelda.VACIA);
                e.setFila(nf);
                e.setColumna(nc);
                habitacionActual.getCelda(nf, nc).setOcupada(true);
                habitacionActual.getCelda(nf, nc).setTipo(TipoCelda.ENEMIGO);
                partida.agregarEvento(e.getNombre() + " se movio a (" + nf + "," + nc + ")");
                int nuevaDistancia = distanciaMinimaEntre(e.getFila(), e.getColumna(), j.getFila(), j.getColumna());
                if (puedeAtacarJugador(e, nuevaDistancia)) resolverAtaqueEnemigo(e, j, nuevaDistancia);
            } else {
                partida.agregarEvento(e.getNombre() + " no encuentra camino hasta el jugador");
            }
        }
    }

    private boolean puedeAtacarJugador(Enemigo enemigo, int distancia) {
        if (distancia < 0 || distancia > motorEnemigos.alcanceAtaque(enemigo)) return false;
        return distancia <= 1 || motorEnemigos.ignoraLineaVision(enemigo)
            || hayLineaVision(enemigo.getFila(), enemigo.getColumna(), partida.getJugador().getFila(), partida.getJugador().getColumna());
    }

    private void resolverAtaqueEnemigo(Enemigo enemigo, Jugador jugador, int distancia) {
        int ataque = motorEnemigos.ataqueAjustado(enemigo, distancia);
        int dano = calcularDano(ataque, jugador.getDefensa());
        jugador.setVida(jugador.getVida() - dano);
        if (musica != null) musica.efectoDano();
        Platform.runLater(() -> {
            if (panelMatriz != null) {
                panelMatriz.animarAtaqueDesdeHasta(enemigo.getFila(), enemigo.getColumna(), jugador.getFila(), jugador.getColumna(), distancia > 1, false);
                panelMatriz.animarDano(jugador.getFila(), jugador.getColumna());
            }
        });
        partida.agregarEvento(enemigo.getNombre() + " " + motorEnemigos.descripcionAtaque(enemigo, distancia)
            + " (" + dano + " de dano, vida " + jugador.getVida() + "/" + jugador.getVidaMaxima() + ")");
        if (!jugador.estaVivo()) {
            partida.setEstado(EstadoPartida.DERROTA);
            partida.agregarEvento("DERROTA: El jugador ha muerto");
        }
    }

    private boolean enemigoPercibeJugador(Enemigo e, Jugador j) {
        int distancia = distanciaMinimaEntre(e.getFila(), e.getColumna(), j.getFila(), j.getColumna());
        if (distancia < 0 || distancia > motorEnemigos.radioPercepcion(e, RADIO_PERCEPCION_ENEMIGA)) return false;
        return distancia <= 2 || motorEnemigos.ignoraLineaVision(e)
            || hayLineaVision(e.getFila(), e.getColumna(), j.getFila(), j.getColumna());
    }

    private int distanciaMinimaEntre(int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
        boolean[][] visitado = new boolean[habitacionActual.getFilas()][habitacionActual.getColumnas()];
        Cola<int[]> cola = new Cola<>();
        Cola<Integer> distancias = new Cola<>();
        cola.enqueue(new int[]{filaOrigen, colOrigen});
        distancias.enqueue(0);
        visitado[filaOrigen][colOrigen] = true;
        int[] df = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        while (!cola.estaVacia()) {
            int[] actual = cola.dequeue();
            int dist = distancias.dequeue();
            if (actual[0] == filaDestino && actual[1] == colDestino) return dist;
            for (int i = 0; i < 4; i++) {
                int nf = actual[0] + df[i];
                int nc = actual[1] + dc[i];
                if (habitacionActual.dentroDeLimites(nf, nc) && !visitado[nf][nc]) {
                    practicafinal.model.Celda celda = habitacionActual.getCelda(nf, nc);
                    if (celda.esTransitable()) {
                        visitado[nf][nc] = true;
                        cola.enqueue(new int[]{nf, nc});
                        distancias.enqueue(dist + 1);
                    }
                }
            }
        }
        return -1;
    }

    private int[] buscarPasoEnemigoHaciaJugador(Enemigo enemigo, Jugador jugador) {
        int filas = habitacionActual.getFilas();
        int columnas = habitacionActual.getColumnas();
        boolean[][] visitado = new boolean[filas][columnas];
        int[][] padreF = new int[filas][columnas];
        int[][] padreC = new int[filas][columnas];
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                padreF[f][c] = -1;
                padreC[f][c] = -1;
            }
        }

        practicafinal.structures.Cola<int[]> cola = new practicafinal.structures.Cola<>();
        cola.enqueue(new int[]{enemigo.getFila(), enemigo.getColumna()});
        visitado[enemigo.getFila()][enemigo.getColumna()] = true;

        int objetivoF = -1;
        int objetivoC = -1;
        int[] df = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!cola.estaVacia()) {
            int[] actual = cola.dequeue();
            if (!(actual[0] == enemigo.getFila() && actual[1] == enemigo.getColumna())
                && esAdyacenteOrtogonal(actual[0], actual[1], jugador.getFila(), jugador.getColumna())) {
                objetivoF = actual[0];
                objetivoC = actual[1];
                break;
            }

            for (int i = 0; i < 4; i++) {
                int nf = actual[0] + df[i];
                int nc = actual[1] + dc[i];
                if (habitacionActual.dentroDeLimites(nf, nc) && !visitado[nf][nc]
                    && esCeldaValidaParaEnemigo(nf, nc)) {
                    visitado[nf][nc] = true;
                    padreF[nf][nc] = actual[0];
                    padreC[nf][nc] = actual[1];
                    cola.enqueue(new int[]{nf, nc});
                }
            }
        }

        if (objetivoF == -1) return null;
        int pasoF = objetivoF;
        int pasoC = objetivoC;
        while (!(padreF[pasoF][pasoC] == enemigo.getFila() && padreC[pasoF][pasoC] == enemigo.getColumna())) {
            int pf = padreF[pasoF][pasoC];
            int pc = padreC[pasoF][pasoC];
            if (pf < 0 || pc < 0) return null;
            pasoF = pf;
            pasoC = pc;
        }
        return new int[]{pasoF, pasoC};
    }

    private boolean esCeldaValidaParaEnemigo(int fila, int columna) {
        TipoCelda tipo = habitacionActual.getCelda(fila, columna).getTipo();
        return tipo == TipoCelda.VACIA && !habitacionActual.getCelda(fila, columna).estaOcupada();
    }

    private int calcularDano(int ataque, int defensa) {
        return motorCombate.calcularDano(ataque, defensa);
    }

    private String rangoDanoTexto(int ataque, int defensa) {
        return motorCombate.rangoDanoTexto(ataque, defensa);
    }

    private boolean esAdyacenteOrtogonal(int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
        int df = Math.abs(filaDestino - filaOrigen);
        int dc = Math.abs(colDestino - colOrigen);
        return (df == 1 && dc == 0) || (df == 0 && dc == 1);
    }

    public boolean guardarPartida(String ruta) {
        if (partida == null) return false;
        try {
            partida.setHabitaciones(habitaciones);
            partida.setObjetosEnTablero(objetosEnTablero);
            persistencia.guardarPartida(partida, ruta);
            partida.agregarEvento("Partida guardada en " + ruta);
            actualizarPaneles();
            return true;
        } catch (IOException e) {
            panelLog.agregarEvento("Error al guardar: " + e.getMessage());
            return false;
        }
    }

    public boolean cargarPartida(String ruta) {
        try {
            partida = persistencia.cargarPartida(ruta);
            configuracionActual = null;
            finMostrado = false;
            if (partida.getGrafoHabitaciones() == null)
                partida.setGrafoHabitaciones(new Grafo<>());
            habitaciones = partida.getHabitaciones();
            if (habitaciones == null || habitaciones.estaVacia()) {
                habitaciones = new ListaEnlazada<>();
                habitaciones.agregar(new Habitacion(partida.getHabitacionActual(), 5, 5));
            }
            objetosEnTablero = partida.getObjetosEnTablero();
            if (objetosEnTablero == null)
                objetosEnTablero = new ListaEnlazada<>();
            habitacionActual = buscarHabitacion(partida.getHabitacionActual());
            if (habitacionActual == null && habitaciones.tamano() > 0)
                habitacionActual = habitaciones.obtener(0);
            colocarEnemigosEnHabitacion();
            colocarObjetosEnHabitacion();
            iniciarTurnos();
            partida.agregarEvento("Partida cargada desde " + ruta);
            actualizarPaneles();
            return true;
        } catch (IOException e) {
            panelLog.agregarEvento("Error al cargar partida: " + e.getMessage());
            return false;
        }
    }

    public void mover(Direccion dir) {
        if (partida == null || partida.estaTerminada()) return;

        Jugador j = partida.getJugador();
        int[] destino = motorMovimiento.aplicarMovimiento(dir, j.getFila(), j.getColumna());

        if (motorMovimiento.esMovimientoValido(habitacionActual, j.getFila(), j.getColumna(),
                                                destino[0], destino[1])) {
            boolean esAlcanzable = false;
            for (int i = 0; i < casillasAlcanzables.tamano(); i++) {
                int[] c = casillasAlcanzables.obtener(i);
                if (c[0] == destino[0] && c[1] == destino[1]) { esAlcanzable = true; break; }
            }
            if (!esAlcanzable) {
                panelLog.agregarEvento("Destino fuera de alcance (max " + j.getMovimiento() + " pasos)");
                return;
            }

            habitacionActual.getCelda(j.getFila(), j.getColumna()).setOcupada(false);
            j.setPosicion(destino[0], destino[1]);
            habitacionActual.getCelda(destino[0], destino[1]).setOcupada(true);
            haMovido = true;
            if (musica != null) musica.efectoPaso();
            final int df = destino[0], dc = destino[1];
            Platform.runLater(() -> { if (panelMatriz != null) panelMatriz.animarMovimiento(df, dc); });
            pasosMovimientoUsados++;
            partida.agregarEvento("Jugador se mueve a (" + destino[0] + "," + destino[1] + ")");
            actualizarVision();
            calcularCasillasAlcanzables();

            TipoCelda tipoDestino = habitacionActual.getCelda(destino[0], destino[1]).getTipo();
            if (tipoDestino == TipoCelda.OBJETO) {
                recogerObjetoEnCelda(destino[0], destino[1]);
                tipoDestino = habitacionActual.getCelda(destino[0], destino[1]).getTipo();
            }
            if (tipoDestino == TipoCelda.TRAMPA) {
                activarTrampa(destino[0], destino[1]);
                if (partida.estaTerminada()) { actualizarPaneles(); return; }
            }
            if (tipoDestino == TipoCelda.PUERTA) {
                partida.agregarEvento("Atravesando la puerta...");
                if (panelMatriz != null) panelMatriz.animarPuerta(destino[0], destino[1]);
                if (musica != null) musica.efectoPuerta();
                cambiarHabitacion(destino[0], destino[1]);
                return;
            }
            if (tipoDestino == TipoCelda.SALIDA) {
                if (jefeFinalVivo()) {
                    partida.agregarEvento("La salida esta sellada por el Senor de la Conquista. Derrota al jefe final.");
                    actualizarPaneles();
                    return;
                }
                partida.setEstado(EstadoPartida.VICTORIA);
                partida.agregarEvento("VICTORIA: Has alcanzado la salida");
                actualizarPaneles();
                return;
            }

            finalizarTurnoJugador();
        } else {
            panelLog.agregarEvento("Movimiento no valido");
        }
    }

    public void avanzar() { mover(Direccion.ARRIBA); }

    public void retroceder() { mover(Direccion.ABAJO); }

    public void moverIzquierda() { mover(Direccion.IZQUIERDA); }

    public void moverDerecha() { mover(Direccion.DERECHA); }

    public void moverA(int filaDestino, int columnaDestino) {
        if (partida == null || partida.estaTerminada() || haActuado) return;
        if (!contienePosicion(casillasAlcanzables, filaDestino, columnaDestino)) {
            panelLog.agregarEvento("Esa casilla no esta dentro del alcance actual");
            return;
        }

        ListaEnlazada<int[]> ruta = calcularRutaHasta(filaDestino, columnaDestino);
        if (ruta.tamano() <= 1) {
            panelLog.agregarEvento("No hay ruta valida hasta esa casilla");
            return;
        }

        String habitacionInicial = partida.getHabitacionActual();
        for (int i = 1; i < ruta.tamano(); i++) {
            if (partida.estaTerminada() || haActuado || !habitacionInicial.equals(partida.getHabitacionActual())) break;
            int[] anterior = ruta.obtener(i - 1);
            int[] siguiente = ruta.obtener(i);
            Direccion dir = direccionEntre(anterior, siguiente);
            if (dir == null) break;
            mover(dir);
        }
    }

    private ListaEnlazada<int[]> calcularRutaHasta(int filaDestino, int columnaDestino) {
        ListaEnlazada<int[]> ruta = new ListaEnlazada<>();
        Jugador j = partida.getJugador();
        int filas = habitacionActual.getFilas();
        int columnas = habitacionActual.getColumnas();
        boolean[][] visitado = new boolean[filas][columnas];
        int[][] padreF = new int[filas][columnas];
        int[][] padreC = new int[filas][columnas];
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                padreF[f][c] = -1;
                padreC[f][c] = -1;
            }
        }

        Cola<int[]> cola = new Cola<>();
        cola.enqueue(new int[]{j.getFila(), j.getColumna()});
        visitado[j.getFila()][j.getColumna()] = true;
        int[] df = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!cola.estaVacia()) {
            int[] actual = cola.dequeue();
            if (actual[0] == filaDestino && actual[1] == columnaDestino) break;
            for (int i = 0; i < 4; i++) {
                int nf = actual[0] + df[i];
                int nc = actual[1] + dc[i];
                if (habitacionActual.dentroDeLimites(nf, nc) && !visitado[nf][nc]) {
                    practicafinal.model.Celda celda = habitacionActual.getCelda(nf, nc);
                    if (celda.esTransitable() && !celda.estaOcupada()) {
                        visitado[nf][nc] = true;
                        padreF[nf][nc] = actual[0];
                        padreC[nf][nc] = actual[1];
                        cola.enqueue(new int[]{nf, nc});
                    }
                }
            }
        }

        if (!visitado[filaDestino][columnaDestino]) return ruta;
        PilaRuta pila = new PilaRuta();
        int f = filaDestino;
        int c = columnaDestino;
        while (f >= 0 && c >= 0) {
            pila.apilar(new int[]{f, c});
            if (f == j.getFila() && c == j.getColumna()) break;
            int pf = padreF[f][c];
            int pc = padreC[f][c];
            f = pf;
            c = pc;
        }
        while (!pila.estaVacia()) ruta.agregar(pila.desapilar());
        return ruta;
    }

    private Direccion direccionEntre(int[] origen, int[] destino) {
        int df = destino[0] - origen[0];
        int dc = destino[1] - origen[1];
        for (Direccion dir : Direccion.values()) {
            if (dir.getDeltaFila() == df && dir.getDeltaColumna() == dc) return dir;
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

    private static class PilaRuta {
        private final practicafinal.structures.Pila<int[]> pila = new practicafinal.structures.Pila<>();
        void apilar(int[] valor) { pila.push(valor); }
        int[] desapilar() { return pila.pop(); }
        boolean estaVacia() { return pila.estaVacia(); }
    }

    private boolean hayAccionAdyacenteDisponible() {
        Jugador j = partida.getJugador();
        for (Direccion dir : Direccion.values()) {
            int[] ady = motorMovimiento.aplicarMovimiento(dir, j.getFila(), j.getColumna());
            if (!habitacionActual.dentroDeLimites(ady[0], ady[1])) continue;
            TipoCelda tipo = habitacionActual.getCelda(ady[0], ady[1]).getTipo();
            if (tipo == TipoCelda.OBJETO || tipo == TipoCelda.PUERTA || tipo == TipoCelda.SALIDA)
                return true;
            for (int i = 0; i < partida.getEnemigos().tamano(); i++) {
                Enemigo e = partida.getEnemigos().obtener(i);
                if (e.estaVivo() && e.getHabitacionId().equals(partida.getHabitacionActual())
                    && e.getFila() == ady[0] && e.getColumna() == ady[1]) return true;
            }
        }
        return false;
    }

    private boolean jefeFinalVivo() {
        for (int i = 0; i < partida.getEnemigos().tamano(); i++) {
            Enemigo e = partida.getEnemigos().obtener(i);
            if (e.estaVivo() && e.getNombre() != null && e.getNombre().toLowerCase().contains("conquista"))
                return true;
        }
        return false;
    }

    private boolean tieneLlaveCripta() {
        if (partida == null || partida.getJugador() == null) return false;
        for (int i = 0; i < partida.getJugador().getInventario().tamano(); i++) {
            Objeto obj = partida.getJugador().getInventario().obtener(i);
            if (obj.getTipo() == TipoObjeto.LLAVE && obj.getNombre() != null
                && obj.getNombre().toLowerCase().contains("cripta")) return true;
        }
        return false;
    }

    private void activarTrampa(int fila, int columna) {
        Jugador j = partida.getJugador();
        j.setVida(j.getVida() - DANO_TRAMPA);
        habitacionActual.getCelda(fila, columna).setTipo(TipoCelda.VACIA);
        habitacionActual.getCelda(fila, columna).setOcupada(true);
        if (musica != null) musica.efectoDano();
        Platform.runLater(() -> { if (panelMatriz != null) panelMatriz.animarDano(fila, columna); });
        partida.agregarEvento("Trampa activada en (" + fila + "," + columna + "): -" + DANO_TRAMPA + " vida");
        if (!j.estaVivo()) {
            partida.setEstado(EstadoPartida.DERROTA);
            partida.agregarEvento("DERROTA: El jugador ha muerto por una trampa");
        }
    }

    private void cambiarHabitacion(int filaPuerta, int colPuerta) {
        Grafo<String> grafo = partida.getGrafoHabitaciones();
        if (grafo == null) {
            partida.setEstado(EstadoPartida.VICTORIA);
            partida.agregarEvento("VICTORIA: Has salido");
            actualizarPaneles();
            return;
        }
        ListaEnlazada<String> vecinos = grafo.obtenerVecinos(partida.getHabitacionActual());
        if (vecinos.tamano() == 0) {
            partida.setEstado(EstadoPartida.VICTORIA);
            partida.agregarEvento("VICTORIA: Has salido de la red de habitaciones");
            actualizarPaneles();
            return;
        }

        Habitacion habitacionAnterior = habitacionActual;
        ConfiguracionDTO.DatosPuertaDTO puerta = buscarPuertaConfigurada(partida.getHabitacionActual(), filaPuerta, colPuerta);
        String siguienteHabId = puerta != null ? puerta.getHabitacionDestino() : vecinos.obtener(0);
        if ("camara_del_jefe".equals(siguienteHabId) && !tieneLlaveCripta()) {
            partida.agregarEvento("La puerta de la camara final esta sellada. Necesitas la Llave de la Cripta.");
            actualizarPaneles();
            return;
        }
        partida.setHabitacionActual(siguienteHabId);
        habitacionActual = buscarHabitacion(siguienteHabId);
        if (habitacionActual == null && habitaciones.tamano() > 0)
            habitacionActual = habitaciones.obtener(0);

        Jugador j = partida.getJugador();
        int filaJugadorAnterior = j.getFila();
        int colJugadorAnterior = j.getColumna();
        if (habitacionAnterior != null && habitacionAnterior.dentroDeLimites(filaJugadorAnterior, colJugadorAnterior))
            habitacionAnterior.getCelda(filaJugadorAnterior, colJugadorAnterior).setOcupada(false);
        if (habitacionAnterior != null && habitacionAnterior.dentroDeLimites(filaPuerta, colPuerta))
            habitacionAnterior.getCelda(filaPuerta, colPuerta).setOcupada(false);

        for (int i = 0; i < partida.getEnemigos().tamano(); i++) {
            Enemigo e = partida.getEnemigos().obtener(i);
            if (e.getHabitacionId().equals(siguienteHabId) && e.estaVivo()
                && habitacionActual.dentroDeLimites(e.getFila(), e.getColumna())) {
                habitacionActual.getCelda(e.getFila(), e.getColumna()).setOcupada(true);
                habitacionActual.getCelda(e.getFila(), e.getColumna()).setTipo(TipoCelda.ENEMIGO);
            }
        }

        int[] entrada = puerta != null
            ? buscarEntradaConfiguradaOValida(habitacionActual, puerta.getFilaEntrada(), puerta.getColumnaEntrada())
            : buscarEntradaValida(habitacionActual);
        j.setPosicion(entrada[0], entrada[1]);
        habitacionActual.getCelda(entrada[0], entrada[1]).setOcupada(true);
        partida.agregarEvento("Cambiaste a la habitacion " + siguienteHabId);
        partida.agregarEvento(descripcionSala(siguienteHabId));
        if (vistaPrincipal != null) vistaPrincipal.mostrarTransicionSala(siguienteHabId);
        iniciarTurnos();
        finalizarTurnoJugador();
    }

    private String descripcionSala(String habitacionId) {
        if ("celda_inicial".equals(habitacionId))
            return "Capitulo 1: despiertas en una celda. Aprende el tick: cada paso, giro o ataque hace reaccionar al mundo.";
        if ("pasillo_estrecho".equals(habitacionId))
            return "Pasillo estrecho: avanza con cuidado y usa las trampas como amenaza, no solo como castigo.";
        if ("biblioteca_caida".equals(habitacionId))
            return "Biblioteca caida: combate de posicion. Empuja enemigos hacia pinchos y recoge el primer arma seria.";
        if ("camara_oscura".equals(habitacionId))
            return "Camara oscura: la vision es limitada. Avanza despacio, gira antes de entrar y escucha el log.";
        if ("sala_pinchos".equals(habitacionId))
            return "Sala de pinchos: el bruto es duro; ganar depende de colocarlo frente a una trampa.";
        if ("santuario_rojo".equals(habitacionId))
            return "Santuario rojo: derrota o esquiva cultistas y consigue la Llave de la Cripta Roja.";
        if ("camara_del_jefe".equals(habitacionId))
            return "Objetivo final: derrota al Senor de la Conquista y alcanza la salida exterior antes de agotar turnos.";
        return "Exploras una nueva estancia de la fortaleza.";
    }

    private ConfiguracionDTO.DatosPuertaDTO buscarPuertaConfigurada(String habitacionOrigen, int fila, int columna) {
        if (configuracionActual == null || configuracionActual.getPuertas() == null) return null;
        for (int i = 0; i < configuracionActual.getPuertas().tamano(); i++) {
            ConfiguracionDTO.DatosPuertaDTO puerta = configuracionActual.getPuertas().obtener(i);
            if (habitacionOrigen.equals(puerta.getHabitacionOrigen())
                && puerta.getFila() == fila
                && puerta.getColumna() == columna) {
                return puerta;
            }
        }
        return null;
    }

    private int[] buscarEntradaConfiguradaOValida(Habitacion habitacion, int fila, int columna) {
        if (habitacion != null && habitacion.dentroDeLimites(fila, columna)
            && habitacion.getCelda(fila, columna).esTransitable()
            && !habitacion.getCelda(fila, columna).estaOcupada()) {
            return new int[]{fila, columna};
        }
        return buscarEntradaValida(habitacion);
    }

    private int[] buscarEntradaValida(Habitacion habitacion) {
        if (habitacion != null && habitacion.dentroDeLimites(1, 1)
            && habitacion.getCelda(1, 1).esTransitable()
            && !habitacion.getCelda(1, 1).estaOcupada()) {
            return new int[]{1, 1};
        }
        for (int f = 0; f < habitacion.getFilas(); f++) {
            for (int c = 0; c < habitacion.getColumnas(); c++) {
                if (habitacion.getCelda(f, c).esTransitable() && !habitacion.getCelda(f, c).estaOcupada())
                    return new int[]{f, c};
            }
        }
        return new int[]{0, 0};
    }

    public void atacar() {
        if (partida == null || partida.estaTerminada() || haActuado) return;
        Jugador j = partida.getJugador();

        for (int i = 0; i < partida.getEnemigos().tamano(); i++) {
            Enemigo e = partida.getEnemigos().obtener(i);
            if (!e.estaVivo() || !e.getHabitacionId().equals(partida.getHabitacionActual())) continue;
            if (esAdyacenteOrtogonal(e.getFila(), e.getColumna(), j.getFila(), j.getColumna())) {
                int dano = calcularDano(j.getAtaque(), e.getDefensa());
                e.setVida(e.getVida() - dano);
                haActuado = true;
                if (panelMatriz != null)
                    panelMatriz.animarAtaqueDesdeHasta(j.getFila(), j.getColumna(), e.getFila(), e.getColumna(), false, true);
                if (musica != null) musica.efectoAtaque();
                partida.agregarEvento("Jugador ataca a " + e.getNombre() + " (" + dano + " de dano, vida enemiga "
                    + e.getVida() + ")");
                aplicarEmpujeContraTrampa(e, j);
                if (!e.estaVivo()) {
                    habitacionActual.getCelda(e.getFila(), e.getColumna()).setOcupada(false);
                    habitacionActual.getCelda(e.getFila(), e.getColumna()).setTipo(TipoCelda.VACIA);
                    partida.agregarEvento(e.getNombre() + " ha sido derrotado");
                    if (e.getNombre() != null && e.getNombre().toLowerCase().contains("conquista"))
                        partida.agregarEvento("El sello de la salida se rompe. Ahora puedes escapar.");
                }
                finalizarTurnoJugador();
                return;
            }
        }
        panelLog.agregarEvento("No hay enemigos adyacentes para atacar");
    }

    private void aplicarEmpujeContraTrampa(Enemigo e, Jugador j) {
        if (!e.estaVivo()) return;
        int df = Integer.compare(e.getFila(), j.getFila());
        int dc = Integer.compare(e.getColumna(), j.getColumna());
        int filaEmpuje = e.getFila() + df;
        int colEmpuje = e.getColumna() + dc;
        if (!habitacionActual.dentroDeLimites(filaEmpuje, colEmpuje)) return;
        if (habitacionActual.getCelda(filaEmpuje, colEmpuje).getTipo() != TipoCelda.TRAMPA) return;

        habitacionActual.getCelda(e.getFila(), e.getColumna()).setOcupada(false);
        habitacionActual.getCelda(e.getFila(), e.getColumna()).setTipo(TipoCelda.VACIA);
        e.setFila(filaEmpuje);
        e.setColumna(colEmpuje);
        e.setVida(e.getVida() - DANO_TRAMPA);
        habitacionActual.getCelda(filaEmpuje, colEmpuje).setTipo(e.estaVivo() ? TipoCelda.ENEMIGO : TipoCelda.VACIA);
        habitacionActual.getCelda(filaEmpuje, colEmpuje).setOcupada(e.estaVivo());
        partida.agregarEvento(e.getNombre() + " cae sobre pinchos por el empuje (-" + DANO_TRAMPA + " vida)");
    }

    public void recogerObjeto() {
        if (partida == null || partida.estaTerminada() || haActuado) return;
        Jugador j = partida.getJugador();

        for (Direccion dir : Direccion.values()) {
            int[] ady = motorMovimiento.aplicarMovimiento(dir, j.getFila(), j.getColumna());
            if (!habitacionActual.dentroDeLimites(ady[0], ady[1])) continue;
            if (habitacionActual.getCelda(ady[0], ady[1]).getTipo() == TipoCelda.OBJETO) {
                Objeto obj = objetoEnPosicion(partida.getHabitacionActual(), ady[0], ady[1]);
                if (obj != null) {
                    j.agregarObjeto(obj);
                    objetosEnTablero.eliminar(obj);
                    partida.agregarEvento("Recogido " + descripcionObjetoParaLog(obj));
                } else {
                    partida.agregarEvento("Objeto recogido en (" + ady[0] + "," + ady[1] + ")");
                }
                if (musica != null) musica.efectoRecoger();
                Platform.runLater(() -> { if (panelMatriz != null) panelMatriz.animarRecoger(ady[0], ady[1]); });
                habitacionActual.getCelda(ady[0], ady[1]).setTipo(TipoCelda.VACIA);
                habitacionActual.getCelda(ady[0], ady[1]).setOcupada(false);
                haActuado = true;
                finalizarTurnoJugador();
                return;
            }
        }
        panelLog.agregarEvento("No hay objetos adyacentes para recoger");
    }

    private void recogerObjetoEnCelda(int fila, int columna) {
        Objeto obj = objetoEnPosicion(partida.getHabitacionActual(), fila, columna);
        if (obj != null) {
            partida.getJugador().agregarObjeto(obj);
            objetosEnTablero.eliminar(obj);
            partida.agregarEvento("Recogido " + descripcionObjetoParaLog(obj) + " al pasar por la casilla");
        } else {
            partida.agregarEvento("Objeto recogido en (" + fila + "," + columna + ")");
        }
        habitacionActual.getCelda(fila, columna).setTipo(TipoCelda.VACIA);
        habitacionActual.getCelda(fila, columna).setOcupada(true);
    }

    private Objeto objetoEnPosicion(String idHab, int fila, int col) {
        for (int i = 0; i < objetosEnTablero.tamano(); i++) {
            Objeto obj = objetosEnTablero.obtener(i);
            if (idHab.equals(obj.getHabitacionId()) && obj.getFila() == fila && obj.getColumna() == col)
                return obj;
        }
        return null;
    }

    private String descripcionObjetoParaLog(Objeto obj) {
        StringBuilder sb = new StringBuilder(obj.getNombre());
        if (obj.getAtaque() > 0) sb.append(" (ATK+").append(obj.getAtaque()).append(")");
        if (obj.getDefensa() > 0) sb.append(" (DEF+").append(obj.getDefensa()).append(")");
        if (obj.getCuracion() > 0) sb.append(" (cura ").append(obj.getCuracion()).append(")");
        if (obj.getTipo() == TipoObjeto.LLAVE) sb.append(" (abre una ruta importante)");
        return sb.toString();
    }

    public void usarObjeto() {
        if (partida == null || partida.estaTerminada() || haActuado) return;
        Jugador j = partida.getJugador();

        for (int i = 0; i < j.getInventario().tamano(); i++) {
            Objeto obj = j.getInventario().obtener(i);
            if (obj.getTipo() == TipoObjeto.POCION && obj.getCuracion() > 0) {
                j.curar(obj.getCuracion());
                j.getInventario().eliminar(i);
                haActuado = true;
                if (musica != null) musica.efectoPocion();
                partida.agregarEvento("Usada " + obj.getNombre() + " (cura +" + obj.getCuracion()
                    + ", vida " + j.getVida() + "/" + j.getVidaMaxima() + ")");
                finalizarTurnoJugador();
                return;
            }
        }
        panelLog.agregarEvento("No hay pociones usables en el inventario. Usa Q para equipar armas o escudos.");
    }

    private boolean equiparObjeto(Jugador j, Objeto obj) {
        if (obj.getTipo() == TipoObjeto.ARMA) {
            Objeto anterior = j.getArmaEquipada();
            j.setArmaEquipada(obj);
            j.getInventario().eliminar(obj);
            if (anterior != null) j.getInventario().agregar(anterior);
            partida.agregarEvento("Equipada " + obj.getNombre() + " (ataque total " + j.getAtaque() + ")"
                + (anterior != null ? ". Reemplaza a " + anterior.getNombre() + "." : "."));
            return true;
        }
        if (obj.getTipo() == TipoObjeto.ESCUDO) {
            Objeto anterior = j.getEscudoEquipado();
            j.setEscudoEquipado(obj);
            j.getInventario().eliminar(obj);
            if (anterior != null) j.getInventario().agregar(anterior);
            partida.agregarEvento("Equipado " + obj.getNombre() + " (defensa total " + j.getDefensa() + ")"
                + (anterior != null ? ". Reemplaza a " + anterior.getNombre() + "." : "."));
            return true;
        }
        return false;
    }

    public void abrirPuerta() {
        if (partida == null || partida.estaTerminada() || haActuado) return;
        Jugador j = partida.getJugador();

        TipoCelda tipoActual = habitacionActual.getCelda(j.getFila(), j.getColumna()).getTipo();
        if (tipoActual == TipoCelda.PUERTA) {
            haActuado = true;
            if (panelMatriz != null) panelMatriz.animarPuerta(j.getFila(), j.getColumna());
            if (musica != null) musica.efectoPuerta();
            cambiarHabitacion(j.getFila(), j.getColumna());
            return;
        }
        if (tipoActual == TipoCelda.SALIDA) {
            haActuado = true;
            if (jefeFinalVivo()) {
                partida.agregarEvento("La salida no se abre: el Senor de la Conquista sigue vivo.");
                actualizarPaneles();
                return;
            }
            partida.setEstado(EstadoPartida.VICTORIA);
            partida.agregarEvento("VICTORIA: Has alcanzado la salida");
            actualizarPaneles();
            return;
        }

        for (Direccion dir : Direccion.values()) {
            int[] ady = motorMovimiento.aplicarMovimiento(dir, j.getFila(), j.getColumna());
            if (!habitacionActual.dentroDeLimites(ady[0], ady[1])) continue;
            TipoCelda tipo = habitacionActual.getCelda(ady[0], ady[1]).getTipo();
            if (tipo == TipoCelda.PUERTA) {
                haActuado = true;
                if (panelMatriz != null) panelMatriz.animarPuerta(ady[0], ady[1]);
                if (musica != null) musica.efectoPuerta();
                cambiarHabitacion(ady[0], ady[1]);
                return;
            }
            if (tipo == TipoCelda.SALIDA) {
                haActuado = true;
                if (jefeFinalVivo()) {
                    partida.agregarEvento("La salida no se abre: el Senor de la Conquista sigue vivo.");
                    actualizarPaneles();
                    return;
                }
                partida.setEstado(EstadoPartida.VICTORIA);
                partida.agregarEvento("VICTORIA: Has alcanzado la salida");
                actualizarPaneles();
                return;
            }
        }
        if (musica != null) musica.efectoError();
        panelLog.agregarEvento("No hay puertas o salidas adyacentes");
    }

    public Partida getPartida() { return partida; }
    public ListaEnlazada<int[]> getCasillasAlcanzables() { return casillasAlcanzables; }
    public ListaEnlazada<int[]> getCaminoResaltado() { return caminoResaltado; }
    public Habitacion getHabitacionActual() { return habitacionActual; }

    public void mostrarDialogoEquipar() {
        if (partida == null || partida.estaTerminada() || haActuado) return;
        Jugador j = partida.getJugador();
        if (j.getInventario().tamano() == 0) {
            panelLog.agregarEvento("No hay objetos para equipar en el inventario");
            return;
        }

        javafx.scene.control.Dialog<Objeto> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("La Conquista - Gestionar objeto");
        dialog.setHeaderText("Selecciona un objeto para equipar o usar:");
        dialog.getDialogPane().setStyle("-fx-background-color: #1a1a2e; -fx-font-family: Consolas;");

        VBox contenido = new VBox(6);
        contenido.setPadding(new javafx.geometry.Insets(10));
        contenido.setStyle("-fx-background-color: #1a1a2e;");

        javafx.scene.control.ToggleGroup grupo = new javafx.scene.control.ToggleGroup();
        ListaEnlazada<javafx.scene.control.RadioButton> radios = new ListaEnlazada<>();
        ListaEnlazada<Objeto> objetos = new ListaEnlazada<>();

        StringBuilder infoEq = new StringBuilder();
        infoEq.append("Arma equipada: ").append(j.getArmaEquipada() != null ?
            j.getArmaEquipada().getNombre() + " (+" + j.getArmaEquipada().getAtaque() + ")" : "(ninguna)").append("\n");
        infoEq.append("Escudo equipado: ").append(j.getEscudoEquipado() != null ?
            j.getEscudoEquipado().getNombre() + " (+" + j.getEscudoEquipado().getDefensa() + ")" : "(ninguna)");

        Label infoLabel = new Label(infoEq.toString());
        infoLabel.setStyle("-fx-text-fill: #c0c0d0; -fx-font-size: 11px; -fx-padding: 0 0 6 0;");

        for (int i = 0; i < j.getInventario().tamano(); i++) {
            Objeto obj = j.getInventario().obtener(i);
            objetos.agregar(obj);
            String texto = obj.getNombre() + " (" + obj.getTipo().name() + ")" +
                (obj.getAtaque() > 0 ? ", ATK+" + obj.getAtaque() : "") +
                (obj.getDefensa() > 0 ? ", DEF+" + obj.getDefensa() : "") +
                (obj.getCuracion() > 0 ? ", HEAL+" + obj.getCuracion() : "");
            javafx.scene.control.RadioButton rb = new javafx.scene.control.RadioButton(texto);
            rb.setToggleGroup(grupo);
            rb.setStyle("-fx-text-fill: #c0c0d0; -fx-font-size: 11px;");
            radios.agregar(rb);
            contenido.getChildren().add(rb);
            if (i == 0) rb.setSelected(true);
        }

        contenido.getChildren().add(0, infoLabel);
        dialog.getDialogPane().setContent(contenido);

        javafx.scene.control.ButtonType btnEquipar = new javafx.scene.control.ButtonType("Confirmar",
            javafx.scene.control.ButtonBar.ButtonData.OK_DONE);
        javafx.scene.control.ButtonType btnCancelar = new javafx.scene.control.ButtonType("Cancelar",
            javafx.scene.control.ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(btnEquipar, btnCancelar);

        dialog.setResultConverter(dialogBtn -> {
            if (dialogBtn == btnEquipar) {
                for (int i = 0; i < radios.tamano(); i++) {
                    if (radios.obtener(i).isSelected()) return objetos.obtener(i);
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(obj -> {
            if (equiparObjeto(j, obj)) {
                haActuado = true;
            } else if (obj.getTipo() == TipoObjeto.POCION) {
                j.curar(obj.getCuracion());
                j.getInventario().eliminar(obj);
                haActuado = true;
                partida.agregarEvento("Usada " + obj.getNombre() + " (cura +" + obj.getCuracion()
                    + ", vida " + j.getVida() + "/" + j.getVidaMaxima() + ")");
            } else {
                panelLog.agregarEvento("No se puede equipar " + obj.getNombre());
                return;
            }
            finalizarTurnoJugador();
        });
    }

    public boolean haMovido() { return haMovido; }
    public boolean haActuado() { return haActuado; }
    public int getMovimientosRestantes() {
        if (partida == null || partida.getJugador() == null) return 0;
        return 1;
    }

    public boolean puedeMover() {
        return partida != null && !partida.estaTerminada() && !haActuado && getMovimientosRestantes() > 0
            && casillasAlcanzables != null && !casillasAlcanzables.estaVacia();
    }

    public boolean puedeAtacar() {
        if (partida == null || partida.estaTerminada() || haActuado) return false;
        Jugador j = partida.getJugador();
        for (int i = 0; i < partida.getEnemigos().tamano(); i++) {
            Enemigo e = partida.getEnemigos().obtener(i);
            if (e.estaVivo() && e.getHabitacionId().equals(partida.getHabitacionActual())
                && esAdyacenteOrtogonal(e.getFila(), e.getColumna(), j.getFila(), j.getColumna())) return true;
        }
        return false;
    }

    public boolean puedeRecoger() {
        if (partida == null || partida.estaTerminada() || haActuado) return false;
        Jugador j = partida.getJugador();
        for (Direccion dir : Direccion.values()) {
            int[] ady = motorMovimiento.aplicarMovimiento(dir, j.getFila(), j.getColumna());
            if (habitacionActual.dentroDeLimites(ady[0], ady[1])
                && habitacionActual.getCelda(ady[0], ady[1]).getTipo() == TipoCelda.OBJETO) return true;
        }
        return false;
    }

    public boolean puedeUsarObjeto() {
        if (partida == null || partida.estaTerminada() || haActuado) return false;
        Jugador j = partida.getJugador();
        for (int i = 0; i < j.getInventario().tamano(); i++) {
            Objeto obj = j.getInventario().obtener(i);
            if (obj.getTipo() == TipoObjeto.POCION && obj.getCuracion() > 0) return true;
        }
        return false;
    }

    public boolean puedeEquipar() {
        return partida != null && !partida.estaTerminada() && !haActuado
            && partida.getJugador().getInventario().tamano() > 0;
    }

    public boolean puedeAbrirPuerta() {
        if (partida == null || partida.estaTerminada() || haActuado) return false;
        Jugador j = partida.getJugador();
        for (Direccion dir : Direccion.values()) {
            int[] ady = motorMovimiento.aplicarMovimiento(dir, j.getFila(), j.getColumna());
            if (!habitacionActual.dentroDeLimites(ady[0], ady[1])) continue;
            TipoCelda tipo = habitacionActual.getCelda(ady[0], ady[1]).getTipo();
            if (tipo == TipoCelda.PUERTA || tipo == TipoCelda.SALIDA) return true;
        }
        return false;
    }

    public boolean puedeComprarCamino() {
        return partida != null && !partida.estaTerminada() && partida.getTurnosRestantes() >= 3;
    }

    public String getObjetivoActual() {
        if (partida == null) return "Objetivo: inicia una nueva partida";
        if (partida.estaTerminada()) return "Objetivo: partida terminada";
        String sala = partida.getHabitacionActual();
        if (jefeFinalVivo()) {
            if ("celda_inicial".equals(sala)) return "Objetivo: sal de la celda y aprende que cada accion consume un tick.";
            if ("pasillo_estrecho".equals(sala)) return "Objetivo: cruza el pasillo estrecho. Las trampas sirven para controlar enemigos.";
            if ("biblioteca_caida".equals(sala)) return "Objetivo: consigue arma y aprende a usar trampas contra enemigos adyacentes.";
            if ("camara_oscura".equals(sala)) return "Objetivo: explora con vision limitada. Avanza con cuidado.";
            if ("sala_pinchos".equals(sala)) return "Objetivo: usa el empuje contra pinchos para vencer al bruto.";
            if ("santuario_rojo".equals(sala)) return tieneLlaveCripta()
                ? "Objetivo: entra en la camara final desde la puerta norte."
                : "Objetivo: consigue la Llave de la Cripta Roja.";
            if ("camara_del_jefe".equals(sala)) return "Objetivo final: derrota al Senor de la Conquista.";
            return "Objetivo: avanza por ticks y prepara el combate final.";
        }
        return "Objetivo: el jefe ha caido. Alcanza la salida exterior.";
    }

    public String getAccionesDisponiblesTexto() {
        if (partida == null || partida.estaTerminada()) return "Acciones: inicia o reinicia una partida.";
        StringBuilder sb = new StringBuilder("Acciones cercanas: ");
        boolean alguna = false;
        Jugador j = partida.getJugador();
        for (Direccion dir : Direccion.values()) {
            int[] p = motorMovimiento.aplicarMovimiento(dir, j.getFila(), j.getColumna());
            if (!habitacionActual.dentroDeLimites(p[0], p[1])) continue;
            TipoCelda tipo = habitacionActual.getCelda(p[0], p[1]).getTipo();
            if (tipo == TipoCelda.OBJETO) { sb.append("recoger objeto, "); alguna = true; }
            if (tipo == TipoCelda.PUERTA) { sb.append("abrir puerta, "); alguna = true; }
            if (tipo == TipoCelda.SALIDA) { sb.append("salir, "); alguna = true; }
            for (int i = 0; i < partida.getEnemigos().tamano(); i++) {
                Enemigo e = partida.getEnemigos().obtener(i);
                if (e.estaVivo() && e.getHabitacionId().equals(partida.getHabitacionActual())
                    && e.getFila() == p[0] && e.getColumna() == p[1]) {
                    sb.append("atacar a ").append(e.getNombre())
                      .append(" (dano estimado ")
                      .append(rangoDanoTexto(j.getAtaque(), e.getDefensa()))
                      .append("), ");
                    alguna = true;
                }
            }
        }
        if (!alguna) return "Acciones cercanas: ninguna. Muevete, espera o busca una puerta.";
        return sb.substring(0, sb.length() - 2) + ".";
    }

    private void actualizarPaneles() {
        if (panelMatriz != null) panelMatriz.actualizar(habitacionActual, partida.getJugador(),
                partida.getEnemigos(), objetosEnTablero, casillasAlcanzables, caminoResaltado, casillasVisibles);
        if (panelEstado != null) panelEstado.actualizar(partida);
        if (panelInventario != null) panelInventario.actualizar(partida.getJugador());
        if (panelLog != null) panelLog.actualizar(partida);
        if (panelAcciones != null) panelAcciones.actualizar(partida, puedeMover(), puedeAtacar(), puedeRecoger(),
                puedeUsarObjeto(), puedeAbrirPuerta(), puedeEquipar(), puedeComprarCamino());
        if (vistaPrincipal != null) {
            vistaPrincipal.actualizarRuta(partida);
            vistaPrincipal.actualizarFaseTurno(partida);
        }
        if (partida != null && partida.estaTerminada() && !finMostrado) {
            finMostrado = true;
            if (onFinPartida != null) onFinPartida.run();
        }
    }
}

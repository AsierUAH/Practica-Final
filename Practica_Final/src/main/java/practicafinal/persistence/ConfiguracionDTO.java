package practicafinal.persistence;

import practicafinal.structures.ListaEnlazada;

public class ConfiguracionDTO {
    private ListaEnlazada<DatosHabitacionDTO> habitaciones;
    private DatosJugadorInicialDTO jugador;
    private int turnosMaximos;
    private ListaEnlazada<DatosEnemigoDTO> enemigos;
    private ListaEnlazada<DatosObjetoDTO> objetos;
    private ListaEnlazada<DatosPuertaDTO> puertas;

    public ListaEnlazada<DatosHabitacionDTO> getHabitaciones() { return habitaciones; }
    public void setHabitaciones(ListaEnlazada<DatosHabitacionDTO> habitaciones) { this.habitaciones = habitaciones; }
    public DatosJugadorInicialDTO getJugador() { return jugador; }
    public void setJugador(DatosJugadorInicialDTO jugador) { this.jugador = jugador; }
    public int getTurnosMaximos() { return turnosMaximos; }
    public void setTurnosMaximos(int turnosMaximos) { this.turnosMaximos = turnosMaximos; }
    public ListaEnlazada<DatosEnemigoDTO> getEnemigos() { return enemigos; }
    public void setEnemigos(ListaEnlazada<DatosEnemigoDTO> enemigos) { this.enemigos = enemigos; }
    public ListaEnlazada<DatosObjetoDTO> getObjetos() { return objetos; }
    public void setObjetos(ListaEnlazada<DatosObjetoDTO> objetos) { this.objetos = objetos; }
    public ListaEnlazada<DatosPuertaDTO> getPuertas() { return puertas; }
    public void setPuertas(ListaEnlazada<DatosPuertaDTO> puertas) { this.puertas = puertas; }

    public static class DatosHabitacionDTO {
        private String id;
        private int filas;
        private int columnas;
        private ListaEnlazada<DatoCeldaDTO> celdas;
        private ListaEnlazada<String> conexiones;
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public int getFilas() { return filas; }
        public void setFilas(int filas) { this.filas = filas; }
        public int getColumnas() { return columnas; }
        public void setColumnas(int columnas) { this.columnas = columnas; }
        public ListaEnlazada<DatoCeldaDTO> getCeldas() { return celdas; }
        public void setCeldas(ListaEnlazada<DatoCeldaDTO> celdas) { this.celdas = celdas; }
        public ListaEnlazada<String> getConexiones() { return conexiones; }
        public void setConexiones(ListaEnlazada<String> conexiones) { this.conexiones = conexiones; }
    }

    public static class DatoCeldaDTO {
        private int fila;
        private int columna;
        private String tipo;
        public int getFila() { return fila; }
        public void setFila(int fila) { this.fila = fila; }
        public int getColumna() { return columna; }
        public void setColumna(int columna) { this.columna = columna; }
        public String getTipo() { return tipo; }
        public void setTipo(String tipo) { this.tipo = tipo; }
    }

    public static class DatosJugadorInicialDTO {
        private String habitacionInicial;
        private int fila;
        private int columna;
        private int vida;
        private int ataque;
        private int defensa;
        private int movimiento;
        public String getHabitacionInicial() { return habitacionInicial; }
        public void setHabitacionInicial(String h) { this.habitacionInicial = h; }
        public int getFila() { return fila; }
        public void setFila(int fila) { this.fila = fila; }
        public int getColumna() { return columna; }
        public void setColumna(int columna) { this.columna = columna; }
        public int getVida() { return vida; }
        public void setVida(int vida) { this.vida = vida; }
        public int getAtaque() { return ataque; }
        public void setAtaque(int ataque) { this.ataque = ataque; }
        public int getDefensa() { return defensa; }
        public void setDefensa(int defensa) { this.defensa = defensa; }
        public int getMovimiento() { return movimiento; }
        public void setMovimiento(int movimiento) { this.movimiento = movimiento; }
    }

    public static class DatosEnemigoDTO {
        private String nombre;
        private String habitacionId;
        private int fila;
        private int columna;
        private int vida;
        private int ataque;
        private int defensa;
        public String getNombre() { return nombre; }
        public void setNombre(String n) { this.nombre = n; }
        public String getHabitacionId() { return habitacionId; }
        public void setHabitacionId(String h) { this.habitacionId = h; }
        public int getFila() { return fila; }
        public void setFila(int fila) { this.fila = fila; }
        public int getColumna() { return columna; }
        public void setColumna(int columna) { this.columna = columna; }
        public int getVida() { return vida; }
        public void setVida(int vida) { this.vida = vida; }
        public int getAtaque() { return ataque; }
        public void setAtaque(int ataque) { this.ataque = ataque; }
        public int getDefensa() { return defensa; }
        public void setDefensa(int defensa) { this.defensa = defensa; }
    }

    public static class DatosObjetoDTO {
        private String nombre;
        private String tipo;
        private String habitacionId;
        private int fila;
        private int columna;
        private int ataque;
        private int defensa;
        private int curacion;
        public String getNombre() { return nombre; }
        public void setNombre(String n) { this.nombre = n; }
        public String getTipo() { return tipo; }
        public void setTipo(String t) { this.tipo = t; }
        public String getHabitacionId() { return habitacionId; }
        public void setHabitacionId(String h) { this.habitacionId = h; }
        public int getFila() { return fila; }
        public void setFila(int fila) { this.fila = fila; }
        public int getColumna() { return columna; }
        public void setColumna(int columna) { this.columna = columna; }
        public int getAtaque() { return ataque; }
        public void setAtaque(int ataque) { this.ataque = ataque; }
        public int getDefensa() { return defensa; }
        public void setDefensa(int defensa) { this.defensa = defensa; }
        public int getCuracion() { return curacion; }
        public void setCuracion(int curacion) { this.curacion = curacion; }
    }

    public static class DatosPuertaDTO {
        private String habitacionOrigen;
        private int fila;
        private int columna;
        private String habitacionDestino;
        private int filaEntrada;
        private int columnaEntrada;
        public String getHabitacionOrigen() { return habitacionOrigen; }
        public void setHabitacionOrigen(String habitacionOrigen) { this.habitacionOrigen = habitacionOrigen; }
        public int getFila() { return fila; }
        public void setFila(int fila) { this.fila = fila; }
        public int getColumna() { return columna; }
        public void setColumna(int columna) { this.columna = columna; }
        public String getHabitacionDestino() { return habitacionDestino; }
        public void setHabitacionDestino(String habitacionDestino) { this.habitacionDestino = habitacionDestino; }
        public int getFilaEntrada() { return filaEntrada; }
        public void setFilaEntrada(int filaEntrada) { this.filaEntrada = filaEntrada; }
        public int getColumnaEntrada() { return columnaEntrada; }
        public void setColumnaEntrada(int columnaEntrada) { this.columnaEntrada = columnaEntrada; }
    }
}

package practicafinal.model;

public class Objeto {
    private String nombre;
    private TipoObjeto tipo;
    private int ataque;
    private int defensa;
    private int curacion;
    private String habitacionId;
    private int fila;
    private int columna;

    public Objeto() {}

    public Objeto(String nombre, TipoObjeto tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public TipoObjeto getTipo() { return tipo; }
    public void setTipo(TipoObjeto tipo) { this.tipo = tipo; }
    public int getAtaque() { return ataque; }
    public void setAtaque(int ataque) { this.ataque = ataque; }
    public int getDefensa() { return defensa; }
    public void setDefensa(int defensa) { this.defensa = defensa; }
    public int getCuracion() { return curacion; }
    public void setCuracion(int curacion) { this.curacion = curacion; }
    public String getHabitacionId() { return habitacionId; }
    public void setHabitacionId(String habitacionId) { this.habitacionId = habitacionId; }
    public int getFila() { return fila; }
    public void setFila(int fila) { this.fila = fila; }
    public int getColumna() { return columna; }
    public void setColumna(int columna) { this.columna = columna; }
}

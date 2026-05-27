package practicafinal.model;

public class Celda {
    private TipoCelda tipo;
    private boolean ocupada;

    public Celda(TipoCelda tipo) {
        this.tipo = tipo;
        this.ocupada = (tipo == TipoCelda.MURO);
    }

    public TipoCelda getTipo() { return tipo; }
    public void setTipo(TipoCelda tipo) { this.tipo = tipo; }
    public boolean estaOcupada() { return ocupada || tipo == TipoCelda.MURO; }
    public void setOcupada(boolean ocupada) { this.ocupada = ocupada; }
    public boolean esTransitable() { return tipo != TipoCelda.MURO; }
}

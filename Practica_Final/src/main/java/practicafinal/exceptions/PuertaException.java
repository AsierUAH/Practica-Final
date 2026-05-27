package practicafinal.exceptions;

public class PuertaException extends JuegoException {
    public PuertaException(String mensaje) {
        super(mensaje);
    }
    public PuertaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

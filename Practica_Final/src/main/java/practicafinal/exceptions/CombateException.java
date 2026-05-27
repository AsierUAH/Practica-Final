package practicafinal.exceptions;

public class CombateException extends JuegoException {
    public CombateException(String mensaje) {
        super(mensaje);
    }
    public CombateException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

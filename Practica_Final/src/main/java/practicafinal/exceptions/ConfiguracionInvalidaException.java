package practicafinal.exceptions;

public class ConfiguracionInvalidaException extends JuegoException {
    public ConfiguracionInvalidaException(String mensaje) { super(mensaje); }
    public ConfiguracionInvalidaException(String mensaje, Throwable causa) { super(mensaje, causa); }
}

package practicafinal.app;

import java.io.File;

public final class RutasJuego {
    public static final String CONFIG_CAMPANIA = "config-campania.json";

    private static final String CARPETA_GUARDADOS = ".la-conquista";
    private static final String ARCHIVO_GUARDADO = "guardado-partida.json";

    private RutasJuego() {}

    public static File archivoGuardadoPrincipal() {
        File carpeta = new File(System.getProperty("user.home"), CARPETA_GUARDADOS);
        if (!carpeta.exists()) carpeta.mkdirs();
        return new File(carpeta, ARCHIVO_GUARDADO);
    }

    public static String rutaGuardadoPrincipal() {
        return archivoGuardadoPrincipal().getAbsolutePath();
    }
}

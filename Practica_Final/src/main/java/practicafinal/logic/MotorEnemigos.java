package practicafinal.logic;

import practicafinal.model.Enemigo;

public class MotorEnemigos {
    public int radioPercepcion(Enemigo enemigo, int radioBase) {
        String nombre = nombreNormalizado(enemigo);
        if (nombre.contains("conquista")) return radioBase + 3;
        if (nombre.contains("acechador")) return radioBase + 2;
        if (nombre.contains("cultista")) return radioBase + 1;
        if (nombre.contains("limo")) return Math.max(2, radioBase - 2);
        return radioBase;
    }

    public boolean ignoraLineaVision(Enemigo enemigo) {
        String nombre = nombreNormalizado(enemigo);
        return nombre.contains("acechador") || nombre.contains("conquista");
    }

    public int alcanceAtaque(Enemigo enemigo) {
        String nombre = nombreNormalizado(enemigo);
        if (nombre.contains("conquista")) return 3;
        if (nombre.contains("cultista")) return 3;
        if (nombre.contains("acechador")) return 2;
        return 1;
    }

    public int ataqueAjustado(Enemigo enemigo, int distancia) {
        int ataque = enemigo.getAtaque();
        if (distancia <= 1) return ataque;
        String nombre = nombreNormalizado(enemigo);
        if (nombre.contains("conquista")) return Math.max(1, ataque - 3);
        if (nombre.contains("cultista")) return Math.max(1, ataque - 4);
        if (nombre.contains("acechador")) return Math.max(1, ataque - 5);
        return ataque;
    }

    public String descripcionAtaque(Enemigo enemigo, int distancia) {
        if (distancia <= 1) return "ataca";
        String nombre = nombreNormalizado(enemigo);
        if (nombre.contains("conquista")) return "lanza una onda oscura";
        if (nombre.contains("cultista")) return "lanza un proyectil ritual";
        if (nombre.contains("acechador")) return "golpea desde la penumbra";
        return "ataca a distancia";
    }

    private String nombreNormalizado(Enemigo enemigo) {
        if (enemigo == null || enemigo.getNombre() == null) return "";
        return enemigo.getNombre().toLowerCase();
    }
}

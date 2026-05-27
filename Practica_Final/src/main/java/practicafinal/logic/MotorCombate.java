package practicafinal.logic;

import java.util.Random;

public class MotorCombate {
    private final Random random;

    public MotorCombate() {
        this(new Random());
    }

    public MotorCombate(Random random) {
        this.random = random;
    }

    public int calcularDano(int ataque, int defensa) {
        double aleatorio = random.nextDouble();
        return calcularDano(ataque, defensa, aleatorio);
    }

    public int calcularDano(int ataque, int defensa, double aleatorio) {
        return Math.max(0, (int)(ataque * (aleatorio * 2)) - defensa);
    }

    public int danoMaximo(int ataque, int defensa) {
        return Math.max(0, ataque * 2 - defensa);
    }

    public String rangoDanoTexto(int ataque, int defensa) {
        return "0-" + danoMaximo(ataque, defensa);
    }
}

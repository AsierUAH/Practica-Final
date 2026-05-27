package practicafinal.logic;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

public class MotorCombateTest {
    @Test void testDanoMinimoNoBajaDeCero() {
        MotorCombate motor = new MotorCombate(new Random(1));
        assertEquals(0, motor.calcularDano(5, 20, 0.9));
    }

    @Test void testDanoConAleatorioControlado() {
        MotorCombate motor = new MotorCombate(new Random(1));
        assertEquals(7, motor.calcularDano(10, 5, 0.6));
    }

    @Test void testDanoMaximo() {
        MotorCombate motor = new MotorCombate(new Random(1));
        assertEquals(15, motor.danoMaximo(10, 5));
        assertEquals(0, motor.danoMaximo(5, 20));
    }

    @Test void testRangoDanoTexto() {
        MotorCombate motor = new MotorCombate(new Random(1));
        assertEquals("0-15", motor.rangoDanoTexto(10, 5));
    }

    @Test void testRandomInyectadoEsReproducible() {
        MotorCombate a = new MotorCombate(new Random(42));
        MotorCombate b = new MotorCombate(new Random(42));
        assertEquals(a.calcularDano(12, 3), b.calcularDano(12, 3));
    }
}

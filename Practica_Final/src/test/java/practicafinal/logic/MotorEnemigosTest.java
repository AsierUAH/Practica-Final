package practicafinal.logic;

import org.junit.jupiter.api.Test;
import practicafinal.model.Enemigo;

import static org.junit.jupiter.api.Assertions.*;

class MotorEnemigosTest {
    private final MotorEnemigos motor = new MotorEnemigos();

    @Test
    void cultistaTieneAtaqueADistancia() {
        Enemigo cultista = new Enemigo("Cultista rojo", 20, 11, 2, 1, 1);

        assertEquals(3, motor.alcanceAtaque(cultista));
        assertEquals(7, motor.ataqueAjustado(cultista, 3));
        assertTrue(motor.descripcionAtaque(cultista, 3).contains("proyectil"));
    }

    @Test
    void acechadorIgnoraLineaVisionYPercibeMas() {
        Enemigo acechador = new Enemigo("Acechador ciego", 24, 10, 1, 1, 1);

        assertTrue(motor.ignoraLineaVision(acechador));
        assertEquals(8, motor.radioPercepcion(acechador, 6));
        assertEquals(2, motor.alcanceAtaque(acechador));
    }

    @Test
    void limoPercibeMenosYNoAtacaADistancia() {
        Enemigo limo = new Enemigo("Limo de celda", 16, 6, 1, 1, 1);

        assertFalse(motor.ignoraLineaVision(limo));
        assertEquals(4, motor.radioPercepcion(limo, 6));
        assertEquals(1, motor.alcanceAtaque(limo));
    }

    @Test
    void jefeTieneMayorPercepcionYAlcance() {
        Enemigo jefe = new Enemigo("Senor de la Conquista", 72, 17, 6, 1, 1);

        assertTrue(motor.ignoraLineaVision(jefe));
        assertEquals(9, motor.radioPercepcion(jefe, 6));
        assertEquals(3, motor.alcanceAtaque(jefe));
        assertEquals(14, motor.ataqueAjustado(jefe, 3));
    }
}

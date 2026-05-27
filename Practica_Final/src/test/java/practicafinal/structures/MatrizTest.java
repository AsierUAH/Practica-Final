package practicafinal.structures;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MatrizTest {

    @Test void testSetYGet() { Matriz<String> m = new Matriz<>(3, 4); m.set(0, 0, "A"); m.set(1, 2, "B"); assertEquals("A", m.get(0, 0)); assertEquals("B", m.get(1, 2)); }
    @Test void testLimites() { Matriz<String> m = new Matriz<>(3, 4); assertTrue(m.dentroDeLimites(2, 3)); assertFalse(m.dentroDeLimites(3, 0)); assertFalse(m.dentroDeLimites(-1, 0)); }
    @Test void testDimensiones() { Matriz<String> m = new Matriz<>(5, 7); assertEquals(5, m.getFilas()); assertEquals(7, m.getColumnas()); }
    @Test void testIndiceFueraDeRango() { Matriz<String> m = new Matriz<>(2, 2); assertThrows(IndexOutOfBoundsException.class, () -> m.get(2, 0)); }
    @Test void testDimensionInvalidas() { assertThrows(IllegalArgumentException.class, () -> new Matriz<>(0, 5)); }
    @Test void testValorNulo() { Matriz<String> m = new Matriz<>(2, 2); assertNull(m.get(0, 0)); }
    @Test void testSobrescribir() { Matriz<String> m = new Matriz<>(2, 2); m.set(0, 0, "A"); m.set(0, 0, "B"); assertEquals("B", m.get(0, 0)); }
    @Test void testUnitaria() { Matriz<String> m = new Matriz<>(1, 1); m.set(0, 0, "U"); assertEquals("U", m.get(0, 0)); }
}

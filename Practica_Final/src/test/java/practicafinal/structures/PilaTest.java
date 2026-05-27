package practicafinal.structures;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PilaTest {

    @Test void testVacia() { Pila<String> p = new Pila<>(); assertTrue(p.estaVacia()); assertNull(p.pop()); assertNull(p.peek()); }
    @Test void testPushYPop() {
        Pila<String> p = new Pila<>(); p.push("A"); p.push("B"); p.push("C");
        assertEquals("C", p.pop()); assertEquals("B", p.pop()); assertEquals("A", p.pop()); assertTrue(p.estaVacia());
    }
    @Test void testPeek() { Pila<Integer> p = new Pila<>(); p.push(10); p.push(20); assertEquals(20, p.peek()); assertEquals(2, p.tamano()); }
    @Test void testLIFO() {
        Pila<String> p = new Pila<>(); p.push("1"); p.push("2"); p.push("3");
        assertEquals("3", p.pop()); assertEquals("2", p.pop()); assertEquals("1", p.pop());
    }
    @Test void testVaciar() { Pila<String> p = new Pila<>(); p.push("X"); p.vaciar(); assertTrue(p.estaVacia()); }
}

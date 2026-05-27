package practicafinal.structures;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ColaTest {

    @Test void testVacia() { Cola<String> c = new Cola<>(); assertTrue(c.estaVacia()); assertNull(c.dequeue()); assertNull(c.peek()); }
    @Test void testEnqueueYDequeue() {
        Cola<String> c = new Cola<>(); c.enqueue("A"); c.enqueue("B"); c.enqueue("C");
        assertEquals("A", c.dequeue()); assertEquals("B", c.dequeue()); assertEquals("C", c.dequeue()); assertTrue(c.estaVacia());
    }
    @Test void testPeek() { Cola<Integer> c = new Cola<>(); c.enqueue(10); c.enqueue(20); assertEquals(10, c.peek()); assertEquals(2, c.tamano()); }
    @Test void testFIFO() {
        Cola<String> c = new Cola<>(); c.enqueue("1"); c.enqueue("2"); c.enqueue("3");
        assertEquals("1", c.dequeue()); assertEquals("2", c.dequeue()); assertEquals("3", c.dequeue());
    }
    @Test void testVaciar() { Cola<String> c = new Cola<>(); c.enqueue("X"); c.vaciar(); assertTrue(c.estaVacia()); }
    @Test void testUnElemento() { Cola<String> c = new Cola<>(); c.enqueue("U"); assertEquals("U", c.dequeue()); assertTrue(c.estaVacia()); }
}

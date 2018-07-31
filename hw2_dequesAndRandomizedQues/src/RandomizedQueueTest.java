import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

public class RandomizedQueueTest {
    private RandomizedQueue<String> queue;
    private Iterator<String> iterator;

    private RandomizedQueue<String> build(String... items) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        for (int i = 0; i < items.length; i++) {
            queue.enqueue(items[i]);
        }
        return queue;
    }

    @Before
    public void setUp() {
        queue = null;
        iterator = null;
    }

    @Test
    public void emptyDequeConstructorTest() {
        queue = new RandomizedQueue<String>();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    public void isEmptyTest() {
        queue = build();

        assertTrue(queue.isEmpty());

        queue.enqueue("Sasha");
        assertFalse(queue.isEmpty());

        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

    @Test
    public void sizeTest() {
        queue = build();

        assertEquals(0, queue.size());

        queue.enqueue("Sasha");
        assertEquals(1, queue.size());

        queue.enqueue("Tori");
        assertEquals(2, queue.size());

        queue.dequeue();
        assertEquals(1, queue.size());

        queue.dequeue();
        assertEquals(0, queue.size());
    }

    @Test
    public void enqueueTest() {
        queue = build();

        queue.enqueue("Sasha");
        assertEquals("Sasha", queue.dequeue());
    }

    @Test
    public void enqueueIncrementsSizeTest() {
        queue = build();

        queue.enqueue("Sasha");
        assertEquals(1, queue.size());

        queue.enqueue("Tori");
        assertEquals(2, queue.size());

        queue.enqueue("Lexi");
        assertEquals(3, queue.size());
    }

    @Test(expected=NullPointerException.class)
    public void enqueueNullObjectTest() {
        queue = build();

        queue.enqueue(null);
    }

    @Test
    public void dequeueTest() {
        queue = build("Sasha");

        assertEquals("Sasha", queue.dequeue());
    }

    @Test
    public void dequeueDecrementsSizeTest() {
        queue = build("Sasha", "Tori");

        assertEquals(2, queue.size());

        queue.dequeue();
        assertEquals(1, queue.size());

        queue.dequeue();
        assertEquals(0, queue.size());
    }

    @Test
    public void dequeueRandomItemTest() {
        assertFalse(IntStream.range(0, 1000).mapToObj(new IntFunction<List<String>>() {
            @Override
            public List<String> apply(int value) {
                RandomizedQueue<String> queue = build("Sasha", "Tori", "Lexi");

                List<String> actresses = new ArrayList<String>();
                while(!queue.isEmpty()) actresses.add(queue.dequeue());

                return actresses;
            }
        }).allMatch(actresses ->
                actresses.get(0).equals("Sasha") &&
                        actresses.get(1).equals("Tori") &&
                        actresses.get(1).equals("Lexi")
        ));
    }

    @Test(expected=NoSuchElementException.class)
    public void dequeueEmptyQueueThrowsTest() {
        queue = build();

        queue.dequeue();
    }

    @Test
    public void sampleTest() {
        queue = build("Sasha");

        assertEquals("Sasha", queue.sample());
    }

    @Test
    public void sampleDoesNotDecrementsSizeTest() {
        queue = build("Sasha", "Tori");

        assertEquals(2, queue.size());
        queue.sample();
        assertEquals(2, queue.size());
    }

    @Test
    public void sampleRandomItemTest() {
        queue = build("Sasha", "Tori", "Lexi");

        assertFalse(
                IntStream
                        .range(0, 1000)
                        .mapToObj(i -> queue.sample())
                        .allMatch(actresses -> actresses.equals("Sasha"))
        );
    }

    @Test(expected=NoSuchElementException.class)
    public void sampleEmptyQueueThrowsTest() {
        queue = build();

        queue.sample();
    }

    @Test
    public void iteratorTraversesInOrderTest() {
        iterator = build("Sasha", "Tori").iterator();

        String first = iterator.next();
        assertTrue(first.equals("Sasha") || first.equals("Tori"));

        String second = iterator.next();
        assertTrue(second.equals("Sasha") || second.equals("Tori"));
        assertFalse(second.equals(first));
    }

    @Test
    public void iteratorHasNextTest() {
        iterator = build("Sasha", "Tori").iterator();

        assertTrue(iterator.hasNext());

        iterator.next();
        assertTrue(iterator.hasNext());

        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test(expected=NoSuchElementException.class)
    public void iteratorNextThrowsOnEmptyDequeTest() {
        iterator = build().iterator();

        iterator.next();
    }

    @Test(expected=UnsupportedOperationException.class)
    public void iteratorDoesNotSupportRemoveTest() {
        iterator = build().iterator();

        iterator.remove();
    }
}
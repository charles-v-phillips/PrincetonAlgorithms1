import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DequeTest extends TestCase{

    private Deque<Integer> dq;

    @BeforeEach
    public void setUp(){
        dq = new Deque<Integer>();
    }

    @Test
    public void testDeque() {
        assertNotNull( dq );
    }

    @Test
    public void isEmptyTest1() {
        dq.addFirst(1);
        assertFalse(dq.isEmpty());
        dq.removeFirst();
        assertTrue(dq.isEmpty());
    }
    @Test
    public void isEmptyTest2() {


        dq.addLast(1);
        assertFalse(dq.isEmpty());
        dq.removeLast();
        assertTrue(dq.isEmpty());
    }
    @Test
    public void isEmptyTest3() {

        dq.addFirst(1);
        assertFalse(dq.isEmpty());
        dq.removeLast();
        assertTrue(dq.isEmpty());
    }
    @Test
    public void isEmptyTest4() {

        dq.addLast(1);
        assertFalse(dq.isEmpty());
        dq.removeFirst();
        assertTrue(dq.isEmpty());
    }

    @Test
    public void randomCalls(){
        for(int i = 0; i<500; i++){
            if(i%2==0){dq.addLast(i);}
            else{dq.addFirst(i);}
        }
        for(int i = 0; i < 500 ; i++){
            double r = Math.random();

            if (r<.25){
                dq.addFirst(StdRandom.uniform(200));
            }
            else if(r<.5){
                dq.addLast(StdRandom.uniform(200));
            }
            else if(r<.75){
                assertNotNull(dq.removeFirst());
            }
            else{assertNotNull(dq.removeLast());}
        }

    }



    protected void tearDown(){
        dq = null;
    }


}
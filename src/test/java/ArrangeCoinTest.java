import org.junit.Test;

import static org.junit.Assert.*;


public class ArrangeCoinTest {

    public int arrangeCoin(int n) {
        int plateau = 0;

        while (n > 0) {
            plateau++;
            n -= plateau;

        }
        return (n == 0) ? plateau : plateau -1;
    }

    @Test
    public void testArrangeCoint() {
        assertEquals(1, arrangeCoin(1));
        assertEquals(1, arrangeCoin(2));
        assertEquals(2, arrangeCoin(3));
        assertEquals(2, arrangeCoin(4));
        assertEquals(2, arrangeCoin(5));
        assertEquals(3, arrangeCoin(6));
        assertEquals(3, arrangeCoin(8));
        assertEquals(3, arrangeCoin(9));
        assertEquals(4, arrangeCoin(10));
        assertEquals(5, arrangeCoin(15));
    }
}

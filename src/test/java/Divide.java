import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class Divide {

    public long divideRec(long dividend, long divisor) {
        if (dividend < 0 && divisor < 0 ) return divideRec(-dividend, -divisor);
        if (divisor < 0) return -divideRec(dividend, -divisor);
        if (dividend < 0) return -divideRec(-dividend, divisor);
        if (divisor == 1) return dividend;

        if (divisor == dividend) return 1;
        if (divisor > dividend) return 0;

        int count = 0;
        while (dividend > divisor) {
            count++;
            dividend -= divisor;
        }

//        return (divisor == dividend) ? count + 1: count;
//        if (divisor > dividend) return count;
//        if (divisor == dividend) return count + 1;

        return count + divideRec(dividend, divisor);
    }

    public int divide(int dividend, int divisor) {
        long sol =  divideRec(dividend, divisor);
        if (sol > 0) sol = Math.min(sol, Integer.MAX_VALUE);
        if (sol < 0) sol = Math.max(sol, -Integer.MAX_VALUE-1);
        return (int) sol;
    }


    @Test
    public void testNoRound() {
        assertEquals(3, divide(10, 3));

        assertEquals(100000, divide(1000000, 10));

        assertEquals(-1073741824, divide(-2147483648, 2));

        assertEquals(2147483647, divide(-2147483648, -1));
        assertEquals(-2147483648, divide(-2147483648, 1));


        assertEquals(10, divide(10, 1));
        assertEquals(1, divide(10, 10));
        assertEquals(0, divide(5, 10));
        assertEquals(2, divide(10, 5));


        assertEquals(-10, divide(-10, 1));
        assertEquals(-10, divide(10, -1));
        assertEquals(10, divide(-10, -1));
        assertEquals(1, divide(-10, -10));
        assertEquals(-1, divide(-10, 10));
        assertEquals(-1, divide(10, -10));
        assertEquals(0, divide(-5, 10));
        assertEquals(0, divide(-5, -10));
        assertEquals(0, divide(5, -10));
        assertEquals(-3, divide(-10, 3));
        assertEquals(-3, divide(10, -3));
        assertEquals(3, divide(-10, -3));

    }
}

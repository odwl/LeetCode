import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;


public class AtoiTest {

    public int myAtoi(String s) {
        s = s.trim();
        if (s.isEmpty()) return 0;

        boolean isNeg = false;
        if (s.charAt(0) == '+' || s.charAt(0) == '-') {
            isNeg = s.charAt(0) == '-';
            s = s.substring(1, s.length());
        }

        final String t = s + "a";
        int last = IntStream.range(0, t.length()).dropWhile(i -> t.charAt(i) >= '0' && t.charAt(i) <= '9').findFirst().getAsInt();
        s = t.substring(0, last);
        if (s.isEmpty()) return 0;

        long l;
        try {
            l = s.isEmpty() ? 0 : Long.parseLong(s);
        } catch (NumberFormatException e) {
            l = isNeg ? - (long) Integer.MIN_VALUE :Integer.MAX_VALUE;
        }

        l = isNeg ? -l : l;

        l = Math.min(l, Integer.MAX_VALUE);
        l = Math.max(l, Integer.MIN_VALUE);
        return (int) l;
    }

    @Test
    public void testAtoi() {
        assertEquals(Integer.MIN_VALUE, myAtoi("-20000000000000000000"));
        assertEquals(Integer.MAX_VALUE, myAtoi("20000000000000000000"));

        assertEquals(0, myAtoi("-+12"));


        assertEquals(0, myAtoi(""));
        assertEquals(0, myAtoi("a"));
        assertEquals(0, myAtoi(" +"));

        assertEquals(42, myAtoi("42a"));
        assertEquals(42, myAtoi("42aaa"));
        assertEquals(42, myAtoi(" 42"));
        assertEquals(42, myAtoi("42"));
        assertEquals(42, myAtoi("+42"));

        assertEquals(-42, myAtoi("-42"));
        assertEquals(-42, myAtoi("-42a"));
        assertEquals(-42, myAtoi("  -42a"));

        assertEquals(Integer.MAX_VALUE, myAtoi(String.valueOf(Integer.MAX_VALUE)));
        assertEquals(Integer.MIN_VALUE, myAtoi(String.valueOf(Integer.MIN_VALUE)));
        assertEquals(Integer.MAX_VALUE - 1, myAtoi(String.valueOf(((long) Integer.MAX_VALUE) - 1)));
        assertEquals(Integer.MAX_VALUE, myAtoi(String.valueOf(((long) Integer.MAX_VALUE) + 1)));
        assertEquals(Integer.MIN_VALUE + 1000, myAtoi(String.valueOf(((long) Integer.MIN_VALUE) + 1000)));
        assertEquals(Integer.MIN_VALUE, myAtoi(String.valueOf(((long) Integer.MIN_VALUE) - 1000)));
    }


}

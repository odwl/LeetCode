import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class MultiplyString {

    public String multipyString(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) return "0";
        if (num1.equals("1")) return num2;
        if (num2.equals("1")) return num1;


        int total = 0;
        int tens1 = 1;

        List<Integer> list1 =
                IntStream.range(0, num1.length())
                .map(num1::charAt)
                .map(c -> (int) c - 48)
                .boxed()
                .collect(Collectors.toList());
        Collections.reverse(list1);

        List<Integer> list2 = IntStream.range(0, num2.length())
                .map(num2::charAt)
                .map(c -> (int) c - 48)
                .boxed()
                .collect(Collectors.toList());
        Collections.reverse(list2);

        for (int d1 : list1) {

            int sum = 0;
            int tens = 1;
            for (int d2 : list2) {
                sum += d2 * tens;
                tens *= 10;
            }

            total += d1*sum*tens1;
            tens1 *= 10;
        }

        return String.valueOf(total);
    }

    @Test
    public void testMultiplyString() {
        assertEquals("0", multipyString("0", "5"));
        assertEquals("0", multipyString("5", "0"));
        assertEquals("1", multipyString("1", "1"));
        assertEquals("200", multipyString("1", "200"));
        assertEquals("200", multipyString("200", "1"));
        assertEquals("6", multipyString("2", "3"));
        assertEquals("6", multipyString("3", "2"));
        assertEquals("1110", multipyString("2", "555"));
        assertEquals("666", multipyString("2", "333"));
        assertEquals("24", multipyString("2", "12"));
        assertEquals("56088", multipyString("123", "456"));
        assertEquals("56088", multipyString("123", "456"));
        assertEquals("56088", multipyString("456", "123"));
    }
}

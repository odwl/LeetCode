import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class MultiplyString {

    public String multipyString(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) return "0";
        if (num1.equals("1")) return num2;
        if (num2.equals("1")) return num1;

        Stream.Builder<String> builder = Stream.builder();

        int last1 = num1.length() - 1;
        for (int i = last1; i >= 0; i--) {
            int d1 = num1.charAt(i) - '0';

            StringBuilder termsBuilder = new StringBuilder("0".repeat(last1 - i));
            int remain = 0;
            for (int j = num2.length() - 1; j >= 0; j--) {
                int d2 = num2.charAt(j) - '0';
                int val = d1 * d2 + remain;
                termsBuilder.append(val%10);
                remain = val / 10;
            }

            if (remain > 0) termsBuilder.append(remain);
            builder.add(termsBuilder.reverse().toString());
        }
        return builder.build().reduce(this::addStrings).get();
    }

    public String addStrings(String num1, String num2) {
        StringBuilder builder = new StringBuilder();

        int carry = 0;
        for (int i1 = num1.length() - 1, i2 = num2.length() -1;
             i1 >= 0 || i2 >= 0;
             i1--, i2--) {
            int d1 = i1 >= 0 ? num1.charAt(i1) - '0' : 0;
            int d2 = i2 >= 0 ? num2.charAt(i2) - '0': 0;
            int val = d1 + d2 + carry;
            builder.append(val % 10);
            carry = val / 10;
        }
        if (carry != 0) builder.append(carry);
        return builder.reverse().toString();
    }

    @Test
    public void testMultiplyString() {
        assertEquals("56088", multipyString("123", "456"));

        assertEquals("1110", multipyString("555", "2"));
        assertEquals("0", multipyString("0", "5"));
        assertEquals("0", multipyString("5", "0"));
        assertEquals("1", multipyString("1", "1"));
        assertEquals("200", multipyString("1", "200"));
        assertEquals("200", multipyString("200", "1"));
        assertEquals("1110", multipyString("2", "555"));
        assertEquals("6", multipyString("2", "3"));
        assertEquals("6", multipyString("3", "2"));
        assertEquals("666", multipyString("2", "333"));
        assertEquals("24", multipyString("2", "12"));
        assertEquals("56088", multipyString("123", "456"));
        assertEquals("56088", multipyString("456", "123"));

        assertEquals("419254329864656431168468",
                multipyString("498828660196", "840477629533"));
    }

    @Test
    public void testAddString() {
        assertEquals("201", addStrings("1", "200"));

        assertEquals("5", addStrings("0", "5"));
        assertEquals("5", addStrings("5", "0"));
        assertEquals("2", addStrings("1", "1"));
        assertEquals("201", addStrings("200", "1"));
        assertEquals("557", addStrings("2", "555"));
        assertEquals("557", addStrings("555", "2"));
        assertEquals("5", addStrings("2", "3"));
        assertEquals("5", addStrings("3", "2"));
        assertEquals("1110", addStrings("555", "555"));
        assertEquals("1110", addStrings("555", "555"));
    }
}


//498828660196
//840477629533
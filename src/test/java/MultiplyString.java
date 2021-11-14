import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MultiplyString {

    public String multipyString(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) return "0";
        if (num1.equals("1")) return num2;
        if (num2.equals("1")) return num1;

        String total = "";
        String tens = "";
        for (int i = num1.length() - 1; i >= 0; i--) {
            int d1 = num1.codePointAt(i) - 48;

            String res = "";
            int remain = 0;
            for (int j = num2.length() - 1; j >= 0; j--) {
                int d2 = num2.codePointAt(j) - 48;
                int val = d1 * d2 + remain;
                res = val % 10 + res;
                remain = val / 10;
            }
            res += tens;
            tens += "0";
            if (remain > 0) res = remain + res;

            total = addString(total, res);
        }

        return total;
    }

    public String addString(String num1, String num2) {
        StringBuilder builder = new StringBuilder();

        int remain = 0;
//        for (int i = num1.length() - 1, j = num2.length() - 1, carry = 0;
//             i >= 0 || j >= 0 || carry != 0;
//             i--, j--) {}

        int max = Math.max(num1.length(), num2.length());
        for (int i = 0; i < max; i++) {
            int d1_rev = num1.length() - i - 1;
            int d1 = d1_rev >= 0 ? num1.codePointAt(d1_rev) - '0' : 0;

            int d2_rev = num2.length() - i - 1;
            int d2 = d2_rev >= 0 ? num2.codePointAt(d2_rev) - '0': 0;

            int val = d1 + d2 + remain;

            builder.append(val % 10);

            remain = (int)(val / 10);
        }
        if (remain !=0) builder.append(remain);
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
        assertEquals("201", addString("1", "200"));

        assertEquals("5", addString("0", "5"));
        assertEquals("5", addString("5", "0"));
        assertEquals("2", addString("1", "1"));
        assertEquals("201", addString("200", "1"));
        assertEquals("557", addString("2", "555"));
        assertEquals("557", addString("555", "2"));
        assertEquals("5", addString("2", "3"));
        assertEquals("5", addString("3", "2"));
        assertEquals("1110", addString("555", "555"));
        assertEquals("1110", addString("555", "555"));
    }
}


//498828660196
//840477629533
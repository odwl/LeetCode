import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class MultiplyString {

    public String multipyString(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) return "0";
        if (num1.equals("1")) return num2;
        if (num2.equals("1")) return num1;

        List<Integer> list1 = new StringBuilder(num1).reverse().chars()
                .map(i -> i - 48)
                .boxed()
                .collect(Collectors.toList());

        List<Integer> list2 = new StringBuilder(num2).reverse().chars()
                .map(c -> c - 48)
                .boxed()
                .collect(Collectors.toList());

        String total = "";
        String tens = "";
        for (int d1 : list1) {
            String res = "";
            int remain = 0;
            for (int d2 : list2) {
                int val = d1*d2 + remain;
                res = String.valueOf(val % 10) + res;
                remain = (int)(val / 10);
            }
            res += tens;
            tens += "0";
            if (remain > 0) res = remain + res;

            total = addString(total, res);
        }

        return total;
    }

    public String addString(String num1, String num2) {
        if (num1.equals("0")) return num2;
        if (num2.equals("0")) return num1;

        if (num1.length() < num2.length()) {
            String temp = num1;
            num1 = num2;
            num2 = temp;
        }

        List<Integer> list1 = new StringBuilder(num1).reverse().chars()
                .map(i -> i - 48)
                .boxed()
                .collect(Collectors.toList());

        List<Integer> list2 = new StringBuilder(num2).reverse().chars()
                .map(c -> c - 48)
                .boxed()
                .collect(Collectors.toList());

        String total = "";
        int remain = 0;
        for (int i = 0; i < list1.size(); i++) {
            int d2 = i < list2.size() ? list2.get(i) : 0;
            int val = list1.get(i) + d2 + remain;
            total = String.valueOf(val % 10) + total;
            remain = (int)(val / 10);
        }

        return (remain == 0) ? total : String.valueOf(remain) + total ;
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
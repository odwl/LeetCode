import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    //    public String addString(String num1, String num2) {
//        String total = "";
//        int remain = 0;
//        for (int i = 0; i < Math.max(num1.length(), num2.length()) ; i++) {
//            int d1_rev = num1.length() - i - 1;
//            int d1 = d1_rev >= 0 ? num1.codePointAt(d1_rev) : 48;
//
//            int d2_rev = num2.length() - i - 1;
//            int d2 = d2_rev >= 0 ? num2.codePointAt(d2_rev) : 48;
//
//            int val = d1 + d2 + remain - 2*48;
//
//            total = String.valueOf(val % 10) + total;
//            remain = (int)(val / 10);
//        }
//        return (remain == 0) ? total : String.valueOf(remain) + total ;
//    }
    public String addString(Stream<String> nums) {
        List<String> list = nums.filter(s -> !s.isEmpty()).collect(Collectors.toList());
        if (list.isEmpty()) return "";

        int sum = list.stream()
                .map(s -> s.codePointAt(s.length() - 1) - 48)
                .mapToInt(v -> v >= 0 ? v : 0)
                .sum();
        String total = String.valueOf(sum);

        Stream<String> rec = list.stream()
                .map(s -> s.substring(0, s.length() - 1));
        if (total.length() > 1) rec = Stream.concat(rec, Stream.of(total.substring(0, total.length()-1)));

        return addString(rec) + total.substring(total.length() - 1);

    }

    public String addString(String num1, String num2) {
        StringBuilder builder = new StringBuilder();

        int remain = 0;
        int max = Math.max(num1.length(), num2.length());

        Function<String, List<Integer>> f = num -> {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < max; i++) {
                int d1_rev = num.length() - i - 1;
                int d1 = d1_rev >= 0 ? num.codePointAt(d1_rev) - '0' : 0;
                list.add(d1);
            }
            return list;
        };

        List<Integer> list1 = f.apply(num1);
        List<Integer> list2 = f.apply(num2);

        for (int i = 0; i < max; i++) {
            int val = list1.get(i) + list2.get(i) + remain;
            builder.append(val % 10);
            remain = val / 10;
        }
        if (remain != 0) builder.append(remain);
        return builder.reverse().toString();
    }

//    public String addString(String num1, String num2) {
//        return addString(Stream.of(num1, num2));
//        if (num1.isEmpty()) return num2;
//        if (num2.isEmpty()) return num1;
//
//        int sum = Stream.of(num1, num2)
//                .map(s -> s.codePointAt(s.length() - 1) - 48)
//                .mapToInt(v -> v >= 0 ? v : 0)
//                .sum();
//        String total = String.valueOf(sum);
//
//        Stream<String> rec = Stream.of(num1, num2)
//                .map(s -> num1.substring(0, s.length() - 1));
//        if (total.length() == 1) rec = Stream.concat(rec, Stream.of(total.substring(0, 1)));
//
//        return addString(rec) + total.substring(total.length() - 1, total.length());


//        int d1_rev = num1.length() - 1;
//        int d1 = d1_rev >= 0 ? num1.codePointAt(d1_rev) - 48 : 0;
//
//        int d2_rev = num2.length() - 1;
//        int d2 = d2_rev >= 0 ? num2.codePointAt(d2_rev) - 48 : 0;
//        String total = String.valueOf(d1 + d2);
//
//        String rec = addString(
//                num1.substring(0, num1.length() - 1),
//                num2.substring(0, num2.length() - 1));
//
//        return (total.length() == 1 ? rec : addString(rec, total.substring(0,1)))
//                + total.substring(total.length() -1, total.length());
//    }

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
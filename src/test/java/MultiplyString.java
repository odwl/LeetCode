import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class MultiplyString {

    public String multipyString(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) return "0";

        int[] firstReverse = new StringBuilder(num1).reverse().chars().map(i -> i-'0').toArray();
        int[] secondReverse = new StringBuilder(num2).reverse().chars().map(i -> i-'0').toArray();

        Stream.Builder<String> builder = Stream.builder();
        String tens = "";
        for (int d1: firstReverse) {
            StringBuilder termsBuilder = new StringBuilder(tens);

            int remain = 0;
            for (int d2: secondReverse) {
                int val = d1 * d2 + remain;
                termsBuilder.append(val%10);
                remain = val / 10;
            }

            tens += "0";
            if (remain > 0) termsBuilder.append(remain);
            builder.add(termsBuilder.reverse().toString());
        }
        return builder.build().reduce(this::addStrings).get();
    }

    public String addStrings(String num1, String num2) {
        List<Integer> list1 = num1.chars().map(c -> c - '0').boxed().collect(Collectors.toList());
        List<Integer> list2 = num2.chars().map(c -> c - '0').boxed().collect(Collectors.toList());
        Deque<Integer> list = addStrings2(list1, list2);
        String numberString = list.stream().map(String::valueOf)
                .collect(Collectors.joining(""));
        return numberString;
    }

    private Deque<Integer> addStrings2(List<Integer> num1, List<Integer> num2) {
        Deque<Integer> res = new ArrayDeque<>();
        int carry = 0;
        for (int i1 = num1.size() - 1, i2 = num2.size() -1;
             i1 >= 0 || i2 >= 0;
             i1--, i2--) {
            int d1 = i1 >= 0 ? num1.get(i1): 0;
            int d2 = i2 >= 0 ? num2.get(i2): 0;
            int val = d1 + d2 + carry;
            res.addFirst(val % 10);
            carry = val / 10;
        }
        if (carry != 0) res.addFirst(carry);
        return res;
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
    public void testStringLong() {
        assertEquals("419254329865782088144079441711974401967825360314041722627217596531014324019264982681410793798089",
                multipyString("498828660196840477629533498828660196840477629533", "840477629533840477629533498828660196840477629533"));
    }

    @Test
    public void testMultiplyLong() {
        assertEquals("419254329865782088144079441711974401967825360314041722627217596531014324019264982681410793798089",
                multiply("498828660196840477629533498828660196840477629533", "840477629533840477629533498828660196840477629533"));
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

    // Function to add two strings.
    private ArrayList<Integer> addStrings(ArrayList<Integer> num1, ArrayList<Integer> num2) {
        ArrayList<Integer> ans = new ArrayList<>();
        int carry = 0;

        for (int i = 0; i < num1.size() || i < num2.size(); ++i) {
            // If num2 is shorter than num1 or vice versa, use 0 as the current digit.
            int digit1 = i < num1.size() ? num1.get(i) : 0;
            int digit2 = i < num2.size() ? num2.get(i) : 0;

            // Add current digits of both numbers.
            int sum = digit1 + digit2 + carry;
            // Set carry equal to the tens place digit of sum.
            carry = sum / 10;
            // Append the ones place digit of sum to answer.
            ans.add(sum % 10);
        }

        if (carry != 0) {
            ans.add(carry);
        }
        return ans;
    }

    // Multiply the current digit of secondNumber with firstNumber.
    ArrayList<Integer> multiplyOneDigit(StringBuilder firstNumber, char secondNumberDigit, int numZeros) {
        // Insert zeros at the beginning based on the current digit's place.
        ArrayList<Integer> currentResult = new ArrayList<>();
        for (int i = 0; i < numZeros; ++i) {
            currentResult.add(0);
        }

        int carry = 0;

        // Multiply firstNumber with the current digit of secondNumber.
        for (int i = 0; i < firstNumber.length(); ++i) {
            char firstNumberDigit = firstNumber.charAt(i);
            int multiplication = (secondNumberDigit - '0') * (firstNumberDigit - '0') + carry;
            // Set carry equal to the tens place digit of multiplication.
            carry = multiplication / 10;
            // Append last digit to the current result.
            currentResult.add(multiplication % 10);
        }

        if (carry != 0) {
            currentResult.add(carry);
        }
        return currentResult;
    }

    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }

        StringBuilder firstNumber = new StringBuilder(num1);
        StringBuilder secondNumber = new StringBuilder(num2);

        // Reverse both the numbers.
        firstNumber.reverse();
        secondNumber.reverse();

        // To store the multiplication result of each digit of secondNumber with firstNumber.
        int N = firstNumber.length() + secondNumber.length();
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < N; ++i) {
            ans.add(0);
        }

        // For each digit in secondNumber, multipy the digit by firstNumber and
        // add the multiplication result to ans.
        for (int i = 0; i < secondNumber.length(); ++i) {
            // Add the current result to final ans.
            ans = addStrings(multiplyOneDigit(firstNumber, secondNumber.charAt(i), i), ans);
        }

        // Pop excess 0 from the rear of ans.
        if (ans.get(ans.size() - 1) == 0) {
            ans.remove(ans.size() - 1);
        }

        // Ans is in the reversed order.
        // Copy it in reverse order to get the final ans.
        StringBuilder answer = new StringBuilder();

        for (int i = ans.size() - 1; i >= 0; --i) {
            answer.append(ans.get(i));
        }

        return answer.toString();
    }
}
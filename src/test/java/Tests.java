import com.google.common.collect.*;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class Tests {

    public String addBinary(String a, String b) {

        if (a.length() < b.length()) {
            String c;
            c = a;
            a = b;
            b = c;
        }

        int diff = a.length() - b.length();


        char[] chars = new char[diff];
        Arrays.fill(chars, '0');
        b = new String(chars) + b;

        char[] a1 = new StringBuilder(a).reverse().toString().toCharArray();
        char[] b1 = new StringBuilder(b).reverse().toString().toCharArray();

        int keep = 0;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            int n1 = a1[i] == '0' ? 0 : 1;
            int n2 = b1[i] == '0' ? 0 : 1;
            builder.append(n1 ^ n2 ^ keep);
            keep = ((n1 | n2) & keep) | (n1 & n2);
        }
        if (keep == 1) builder.append("1");
        return builder.reverse().toString();
    }


    public boolean wordPattern(String pattern, String s) {
        Map<Character, String> dict = new HashMap(s.length());
        Map<String, Character> dict2 = new HashMap(s.length());

        String[] splitS = s.split(" ");
        if (splitS.length != pattern.length()) return false;

        for (int i = 0; i < pattern.length(); i++) {
            char pat = pattern.charAt(i);
            boolean inKey = dict.containsKey(pat);
            String split = splitS[i];
            boolean inValue = dict2.containsKey(split);
            if (inKey ^ inValue || (inKey && !split.equals(dict.get(pat)))) return false;

            if (!inKey) {
                dict.put(pat, split);
                dict2.put(split, pat);
            }
        }
        return true;
    }

    public List<Integer> sequentialDigits(int low, int high) {
        String first = String.valueOf(low);
        int lowFirst = Character.getNumericValue(first.charAt(0));

        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            for (int len = first.length() - 1; len <= 9 - i; len++) {
                int num = 0;
                for (int j = 0; j <= len; j++)
                    num += (i + j) * Math.pow(10, len - j);
                if (num > low && num < high) list.add(num);
            }
        }

        return list.stream().sorted().collect(Collectors.toList());
    }

    public int lengthOfLongestSubstring(String s) {
        if (s.length() <= 1) return s.length();

        int max = 0;
        int left = 0;
        int right = 1;
        Set<Character> set = new HashSet<>(List.of(s.charAt(left)));

        while (left < s.length() - 1 && s.length() - left > max) {

            while (right < s.length() && !set.contains(s.charAt(right))) {
                set.add(s.charAt(right));
                right++;
            }
            max = Math.max(max, right - left);

            if (right == s.length()) return max;
            while (s.charAt(left) != s.charAt(right)) {
                set.remove(s.charAt(left));
                left++;
            }
            left++;
            right = Math.max(right, left + 1);


//            left++;
//            right = left + 1;
//
////            set.clear();
////            set.add(s.charAt(left));
//            set = new HashSet<>(List.of(s.charAt(left)));

        }

        return max;
    }

//    public int maxArea(int[] height) {
//        int max = 0;
//        int left = 0;
//        int right = 1;
//
//        while (left < height.length - 1) {
//            while (right < height.length ) {
//                int area = (right - left) * Math.min(height[left], height[right]);
//                max = Math.max(max, area);
//                right++;
//            }
//            left++;
//            right = left + 1;
//        }
//        return max;
//    }

    public int maxArea2(int[] height) {
        int max = 0;
        int left = 0;
        int right = height.length - 1;

        while (left != right) {
            int area = (right - left) * Math.min(height[left], height[right]);
            max = Math.max(max, area);
            if (height[left] <= height[right]) left++;
            else right--;
        }
        return max;
    }

    public int maxArea3(int[] height) {
        return maxAreaRec(height, 0, height.length - 1);
    }

    public int maxAreaRec(int[] height, int left, int right) {
        int area = (right - left) * Math.min(height[left], height[right]);

        if (right - left == 1) return area;

        if (height[left] <= height[right]) left++; else right--;
        return Math.max(area, maxAreaRec(height, left, right));
    }

    class MaxArea implements Supplier<Integer> {
        int[] height;
        int left, right;

        public MaxArea(int[] height) {
            this.height = height;
            left = 0;
            right = height.length - 1;
        }

        @Override
        public Integer get() {
            return recursive();
        }

        private int recursive() {
            int area = (right - left) * Math.min(height[left], height[right]);
            if (right - left == 1) return area;

            if (height[left] <= height[right]) left++; else right--;
            return Math.max(area, recursive());
        }
    }


    public int maxArea(int[] height) {
        return new MaxArea(height).get();
    }

    @Test
    public void testMax() {
        assertEquals(1, maxArea(new int[]{1,1}));

        assertEquals(49, maxArea(new int[]{1,8,6,2,5,4,8,3,7}));

        assertEquals(1, maxArea(new int[]{1,2}));
        assertEquals(2, maxArea(new int[]{1,2,1}));

    }


    @Test
    public void testSubstring() {
        assertEquals(3, lengthOfLongestSubstring("daabd"));

        assertEquals(3, lengthOfLongestSubstring("aaaadbaaabcabcbb"));


        assertEquals(3, lengthOfLongestSubstring("aaaadaaaabdabdbb"));

        assertEquals(4, lengthOfLongestSubstring("aaaadbcaaabcabcbb"));
        assertEquals(3, lengthOfLongestSubstring("aaaadbaaabcabcbb"));

        assertEquals(3, lengthOfLongestSubstring("aaaaaaabcabcbb"));

        assertEquals(10, lengthOfLongestSubstring("abcdefghijab"));


        assertEquals(3, lengthOfLongestSubstring("abcabcbb"));

        assertEquals(3, lengthOfLongestSubstring("pwwkew"));
        assertEquals(2, lengthOfLongestSubstring("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab"));

        assertEquals(2, lengthOfLongestSubstring("aab"));
        assertEquals(1, lengthOfLongestSubstring("bbbbb"));
        assertEquals(2, lengthOfLongestSubstring("ab"));
        assertEquals(0, lengthOfLongestSubstring(""));
        assertEquals(1, lengthOfLongestSubstring("a"));
        assertEquals(1, lengthOfLongestSubstring("aa"));
    }


    @Test
    public void testDigit() {
        assertArrayEquals(new int[]{67, 78, 89, 123}, sequentialDigits(58, 155).stream().mapToInt(i -> i).toArray());
        assertArrayEquals(new int[]{12, 23, 34, 45, 56, 67, 78, 89, 123, 234, 345, 456, 567, 678, 789, 1234, 2345, 3456, 4567, 5678, 6789, 12345, 23456, 34567, 45678, 56789, 123456, 234567, 345678, 456789, 1234567, 2345678, 3456789, 12345678, 23456789, 123456789},
                sequentialDigits(10, 1000000000).stream().mapToInt(i -> i).toArray());


        assertArrayEquals(new int[]{123, 234}, sequentialDigits(100, 300).stream().mapToInt(i -> i).toArray());
        assertArrayEquals(new int[]{1234, 2345, 3456, 4567, 5678, 6789, 12345},
                sequentialDigits(1000, 13000).stream().mapToInt(i -> i).toArray());
    }

    @Test
    public void testWord() {
        assertFalse(wordPattern("abba", "dog dog dog dog"));
        assertTrue(wordPattern("abba", "dog cat cat dog"));
        assertFalse(wordPattern("abbae", "dog cat cat dog"));
        assertFalse(wordPattern("abbae", "dog cat cat"));
    }


    @Test
    public void testAdd() {
        assertEquals("100", addBinary("11", "1"));
        String a = "10100000100100110110010000010101111011011001101110111111111101000000101111001110001111100001101";
        String b = "110101001011101110001111100110001010100001101011101010000011011011001011101111001100000011011110011";
        assertEquals("110111101100010011000101110110100000011101000101011001000011011000001100011110011010010011000000000", addBinary(a, b));

    }

    @Test
    public void testMerge() {

        int[] array = {5, 1};
        int[] aux = new int[array.length];
        MergeSort.merge(array, aux, 0, 0, 1);

        assertTrue(aux[0] == 1);
        assertTrue(aux[1] == 5);
    }

    @Test()
    public void testMergeWith4() {

        int[] array = {4, 5, 1, 3};
        int[] aux = new int[array.length];
        MergeSort.merge(array, aux, 0, 1, 3);

        assertTrue(isArraySorted(aux));
    }

    @Test()
    public void testMergeWith4Bis() {

        int[] array = {1, 3, 4, 5};
        int[] aux = new int[array.length];
        MergeSort.merge(array, aux, 0, 1, 3);

        assertTrue(isArraySorted(aux));
        assertTrue(aux[3] == 5);
    }


    @Test()
    public void testOneElement() {

        Iterable<Integer> sorted = MergeSort.sort(Lists.newArrayList(5));

        assertEquals(sorted, ImmutableList.of(5));
    }

    @Test()
    public void testTwoElement() {
        Iterable<Integer> sorted = MergeSort.sort(Lists.newArrayList(5, 1));

        assertTrue(isArraySorted(sorted));
    }


    @Test()
    public void testMergeSort4() {

        Iterable<Integer> sorted = MergeSort.sort(Lists.newArrayList(1, 3, 4, 5));

        assertEquals(Lists.newArrayList(sorted), Lists.newArrayList(1, 3, 4, 5));
    }

    @Test()
    public void testMergeSortUnsorted() {
        Iterable<Integer> sorted = MergeSort.sort(Lists.newArrayList(1, 5, 4, 3));

        assertEquals(Lists.newArrayList(sorted), Lists.newArrayList(1, 3, 4, 5));
    }

    @Test()
    public void testComplete() {
        Iterable<Integer> sorted = MergeSort.sort(randomArray(10000));
        assertTrue(isArraySorted(sorted));
    }

    @Test()
    public void testGuava() {
        Iterable<Integer> sorted = Iterables.mergeSorted(
                Lists.newArrayList(
                        Lists.newArrayList(1, 5),
                        Lists.newArrayList(3, 4)), Ordering.natural());
        assertEquals(Lists.newArrayList(sorted), Lists.newArrayList(1, 3, 4, 5));
    }

    private static boolean isArraySorted(Iterable<Integer> data) {
        List<Integer> list = Lists.newArrayList(data);
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) > list.get(i + 1))
                return false;
        }
        return true;
    }

    private static boolean isArraySorted(int[] data) {
        return isArraySorted(IntStream.of(data).boxed().collect(Collectors.toList()));
    }

    private static List<Integer> randomArray(int size) {
        int[] array = new int[size];
        Random rng = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = rng.nextInt(Integer.MAX_VALUE);
        }
        return IntStream.of(array).boxed().collect(Collectors.toList());
    }

}

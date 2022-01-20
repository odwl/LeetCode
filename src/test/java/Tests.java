import com.google.common.collect.*;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        Map<Character, String> dict = new HashMap<>(s.length());
        Map<String, Character> dict2 = new HashMap<>(s.length());

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


    public int minCostClimbingStairs(int[] cost) {
        IntStream.iterate(cost.length - 3, i -> i - 1).limit(cost.length - 2)
                .forEach(i -> cost[i] = cost[i] + Math.min(cost[i + 1], cost[i + 2]));
        return Math.min(cost[0], cost[1]);
    }

    public int minCostClimbingStairsLoop(int[] cost) {
        for (int i = cost.length - 3; i >= 0; i--) {
            cost[i] += Math.min(cost[i + 1], cost[i + 2]);
        }
        return Math.min(cost[0], cost[1]);
    }

    public int minEatingSpeed(int[] piles, int h) {
        int start = 1;
        int end = Integer.MAX_VALUE;
        IntStream.range(0, piles.length).forEach(i -> piles[i] -= 1);

        while (start < end) {
            int mid = (start + end) >>> 1;

            int sum = 0;
            for (int pile: piles) {
                sum += pile / mid + 1;
                if (sum > h) break;
            }

            if (sum <= h) end = mid;
            if (sum > h) start = mid + 1;
        }

        return end;
    }

    @Test
    public void testEating() {
        assertEquals(10, minEatingSpeed(new int[]{3, 6, 7, 20}, 5));

        assertEquals(14, minEatingSpeed(new int[]{332484035,524908576,855865114,632922376,222257295,690155293,112677673,679580077,337406589,290818316,877337160,901728858,679284947,688210097,692137887,718203285,629455728,941802184, 312884469}, 823855818));

        assertEquals(2, minEatingSpeed(new int[]{1, 4, 5, 2}, 7));



        assertEquals(1, minEatingSpeed(new int[]{312884470}, 968709470));




        assertEquals(2, minEatingSpeed(new int[]{312884470}, 312884469));



        assertEquals(4, minEatingSpeed(new int[]{3, 6, 7, 4}, 7));


        assertEquals(4, minEatingSpeed(new int[]{3, 6, 7, 8}, 7));

        assertEquals(4, minEatingSpeed(new int[]{3, 6, 7, 11}, 8));


        assertEquals(5, minEatingSpeed(new int[]{3, 6, 7, 5}, 6));
        assertEquals(5, minEatingSpeed(new int[]{3, 6, 2, 5}, 5));
        assertEquals(5, minEatingSpeed(new int[]{3, 1, 2, 5}, 4));


        assertEquals(4, minEatingSpeed(new int[]{3, 6, 7, 2}, 6));


        assertEquals(20, minEatingSpeed(new int[]{3, 6, 7, 20}, 4));

        assertEquals(7, minEatingSpeed(new int[]{3, 6, 7, 11}, 5));
        assertEquals(7, minEatingSpeed(new int[]{3, 6, 7, 14}, 5));
        assertEquals(8, minEatingSpeed(new int[]{3, 6, 7, 15}, 5));


        assertEquals(11, minEatingSpeed(new int[]{3, 6, 7, 11}, 4));


        assertEquals(30, minEatingSpeed(new int[]{30, 11, 23, 4, 20}, 5));
        assertEquals(23, minEatingSpeed(new int[]{30, 11, 23, 4, 20}, 6));

    }

    @Test
    public void testClimbing() {
        assertEquals(6, minCostClimbingStairs(new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1}));
        assertEquals(1, minCostClimbingStairs(new int[]{1, 2}));
        assertEquals(1, minCostClimbingStairs(new int[]{2, 1}));
        assertEquals(15, minCostClimbingStairs(new int[]{10, 15, 20}));


    }

    @Test
    public void testLongStream() {
        int[] data = new int[1000000];
        Arrays.fill(data, 1);
        assertEquals(500000, minCostClimbingStairs(data));
    }

    @Test
    public void testLongLoop() {
        int[] data = new int[1000000];
        Arrays.fill(data, 1);
        assertEquals(500000, minCostClimbingStairsLoop(data));
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

        assertEquals(1, aux[0]);
        assertEquals(5, aux[1]);
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
        assertEquals(5, aux[3]);
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
            keep =((n1 | n2) & keep ) | (n1 & n2);
        }
        if (keep == 1) builder.append("1");
        return builder.reverse().toString();
    }

    @Test
    public void testAdd(){
        assertEquals("100", addBinary("11", "1"));
        String a = "10100000100100110110010000010101111011011001101110111111111101000000101111001110001111100001101";
        String b = "110101001011101110001111100110001010100001101011101010000011011011001011101111001100000011011110011";
        assertEquals("110111101100010011000101110110100000011101000101011001000011011000001100011110011010010011000000000", addBinary(a, b));

    }

    @Test
    public void testMerge(){

        int[] array = {5, 1};
        int[] aux = new int[array.length];
        MergeSort.merge(array, aux, 0, 0, 1);

        assertTrue(aux[0] == 1);
        assertTrue(aux[1] == 5);
    }

    @Test()
    public void testMergeWith4(){

        int[] array = {4, 5, 1, 3};
        int[] aux = new int[array.length];
        MergeSort.merge(array, aux, 0, 1, 3);

        assertTrue(isArraySorted(aux));
    }

    @Test()
    public void testMergeWith4Bis(){

        int[] array = {1,3,4,5};
        int[] aux = new int[array.length];
        MergeSort.merge(array, aux, 0, 1, 3);

        assertTrue(isArraySorted(aux));
        assertTrue(aux[3] == 5);
    }


    @Test()
    public void testOneElement(){

        Iterable<Integer> sorted = MergeSort.sort(Lists.newArrayList(5));

        assertEquals(sorted, ImmutableList.of(5));
    }

    @Test()
    public void testTwoElement(){
        Iterable<Integer> sorted = MergeSort.sort(Lists.newArrayList(5,1));

        assertTrue(isArraySorted(sorted));
    }


    @Test()
    public void testMergeSort4(){

        Iterable<Integer> sorted = MergeSort.sort(Lists.newArrayList(1,3,4,5));

        assertEquals(Lists.newArrayList(sorted), Lists.newArrayList(1,3,4,5));
    }

    @Test()
    public void testMergeSortUnsorted(){
        Iterable<Integer> sorted = MergeSort.sort(Lists.newArrayList(1,5, 4, 3));

        assertEquals(Lists.newArrayList(sorted), Lists.newArrayList(1,3, 4, 5));
    }

    @Test()
    public void testComplete(){
        Iterable<Integer> sorted = MergeSort.sort(randomArray(10000));
        assertTrue(isArraySorted(sorted));
    }

    @Test()
    public void testGuava(){
        Iterable<Integer> sorted = Iterables.mergeSorted(
                Lists.newArrayList(
                        Lists.newArrayList(1,5),
                        Lists.newArrayList(3,4)), Ordering.natural());
        assertEquals(Lists.newArrayList(sorted), Lists.newArrayList(1, 3, 4, 5));
    }

    private static boolean isArraySorted(Iterable<Integer> data){
        List<Integer> list = Lists.newArrayList(data);
        for(int i = 0 ; i < list.size() -1; i++){
            if(list.get(i)>list.get(i+1))
                return false;
        }
        return true;
    }

    private static boolean isArraySorted(int[] data){
        return isArraySorted(IntStream.of(data).boxed().collect(Collectors.toList()));
    }

    private static List<Integer> randomArray(int size){
        int[] array = new int[size];
        Random rng = new Random();
        for(int i = 0 ; i < size ; i++){
            array[i]= rng.nextInt(Integer.MAX_VALUE);
        }
        return IntStream.of(array).boxed().collect(Collectors.toList());
    }

}

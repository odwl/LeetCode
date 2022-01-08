import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class HeapTest {


    public int findKthLargest2(int[] nums, int k) {

        Queue<Integer> heap = Arrays.stream(nums).boxed()
                .collect(Collectors.toCollection(() -> new PriorityQueue<Integer>(Collections.reverseOrder())));

        return IntStream.generate(heap::poll).limit(k).reduce((a, b) -> b).getAsInt();
    }

    public int findKthLargest(int[] nums, int k) {

        Queue<Integer> heap = new PriorityQueue<>();

        for (int num : nums) {
            heap.add(num);
            if (heap.size() > k) heap.poll();
        }
        return heap.poll();
    }

    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> mapCount = new HashMap<>();
        Arrays.stream(nums).forEach(num -> mapCount.compute(num, (i, count) -> count == null ? 1 : count + 1));

        Queue<Integer> heap = new PriorityQueue<Integer>((o2, o1) -> mapCount.get(o2) - mapCount.get(o1));

        for (Integer key : mapCount.keySet()) {
            heap.add(key);
            if (heap.size() > k) heap.poll();
        }

        int[] sol = new int[k];
        IntStream.rangeClosed(0, k-1).forEach(i -> sol[k-1-i] = heap.poll());
        return sol;
    }

    @Test
    public void testKFreq() {
        assertArrayEquals(new int[]{1, 2}, topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2));

        assertArrayEquals(new int[]{1}, topKFrequent(new int[]{1}, 1));
        assertArrayEquals(new int[]{-1, 2}, topKFrequent(new int[]{4, 1, -1, 2, -1, 2, 3}, 2));
    }

    @Test
    public void testFindK() {
        assertEquals(4, findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4));
        assertEquals(1, findKthLargest(new int[]{1}, 1));
        assertEquals(5, findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));
    }

}

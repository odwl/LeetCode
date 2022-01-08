import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


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
        IntStream.rangeClosed(0, k - 1).forEach(i -> sol[k - 1 - i] = heap.poll());
        return sol;
    }

    public class KthLargest {

        Queue<Integer> q;
        int k;

        public KthLargest(int k, int[] a) {
            this.k = k;
            q = new PriorityQueue<>(k);
            Arrays.stream(a).forEach(this::add);
        }

        public int add(int n) {
            if (q.size() < k)
                q.offer(n);
            else if (q.peek() < n) {
                q.poll();
                q.offer(n);
            }
            return q.peek();
        }
    }

    public int lastStoneWeight(int[] stones) {
        Queue<Integer> queue = new PriorityQueue<>(stones.length, Comparator.reverseOrder());
        Arrays.stream(stones).forEach(queue::offer);
        while (queue.size() > 1) {
            int newStone = queue.poll() - queue.poll();
            if (newStone != 0) queue.offer(newStone);
        }
        return queue.size() > 0 ? queue.poll() : 0;
    }

    @Test
    public void testStone() {
        assertEquals(1, lastStoneWeight(new int[] {2,7,4,1,8,1}));
        assertEquals(0, lastStoneWeight(new int[] {2,2}));
        assertEquals(5, lastStoneWeight(new int[] {31,26,33,21,40}));
    }

    @Test
    public void testKClass() {
        KthLargest obj = new KthLargest(3, new int[]{4, 5, 8, 2});
        assertEquals(4, obj.add(3));
        assertEquals(5, obj.add(5));
        assertEquals(5, obj.add(10));
        assertEquals(8, obj.add(9));
        assertEquals(8, obj.add(4));
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

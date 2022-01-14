
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class HeapTest {

    int size = 2;
    int[][] mat = new int[size][size];
    int[] sol = new int[size];

    HeapTest() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mat[i][j] = 0;
            }
        }

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < row + 1; col++) {
                mat[row][col] = 1;
            }
            sol[row] = row;
        }
    }


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

    private int binarySearch(int[] row) {
        int low = 0;
        int high = row.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (row[mid] == 1) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    public int[] kWeakestRowsLeet(int[][] mat, int k) {
        int m = mat.length;
        int n = mat[0].length;

        // Create a Priority Queue that measures firstly on strength and then indexes.
//        Comparator<int[]> descFirtIn = Comparator.<int[]>comparingInt(pair -> pair[0]).thenComparingInt(pair -> pair[1]).reversed();
//        PriorityQueue<int[]> pq = new  PriorityQueue<>(descFirtIn);
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            if (a[0] == b[0]) return b[1] - a[1];
            else return b[0] - a[0];
        });

        // Add strength/index pairs to the pq. Whenever length > k, remove the largest.
        for (int i = 0; i < m; i++) {
            int strength = binarySearch(mat[i]);
            int[] entry = new int[]{strength, i};
            pq.add(entry);
            if (pq.size() > k) {
                pq.poll();
            }
        }

        // Pull the indexes out of the priority queue.
        int[] indexes = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            int[] pair = pq.poll();
            indexes[i] = pair[1];
        }

        return indexes;
    }

    public int[] kWeakestRowsBinary(int[][] mat, int k) {
        Integer[] rows = Arrays.stream(mat).map(this::binarySearch).toArray(Integer[]::new);
        Comparator<Integer> descFirtIn = Comparator.<Integer>comparingInt(j -> rows[j]).thenComparingInt(i -> i).reversed();

        Queue<Integer> heap = new PriorityQueue<>(k, descFirtIn);

        for (int i = 0; i < mat.length; i++) {
            heap.add(i);
            if (heap.size() > k) heap.poll();
        }

        int[] sol = new int[k];
        IntStream.rangeClosed(0, k - 1).forEach(i -> sol[k - 1 - i] = heap.poll());
        return sol;
    }

    public int[] kWeakestRowsBinary2(int[][] mat, int k) {
        Comparator<int[]> descFirtIn = Comparator.<int[]>comparingInt(pair -> pair[0])
                .thenComparingInt(pair -> pair[1]).reversed();

        Queue<int[]> heap = new PriorityQueue<>(k, descFirtIn);

        for (int i = 0; i < mat.length; i++) {
            heap.add(new int[]{binarySearch(mat[i]), i});
            if (heap.size() > k) heap.poll();
        }

        int[] sol = new int[k];
        IntStream.rangeClosed(0, k - 1).forEach(i -> sol[k - 1 - i] = heap.poll()[1]);
        return sol;
    }


    public int[] kWeakestRowsSum(int[][] mat, int k) {
        List<Integer> rows = Arrays.stream(mat).map(vec -> Arrays.stream(vec).sum()).collect(Collectors.toList());

        Comparator<Integer> descFirtIn = Comparator.comparingInt(rows::get).thenComparingInt(i -> i).reversed();
        Queue<Integer> heap = new PriorityQueue<>(k, descFirtIn);

        for (int i = 0; i < mat.length; i++) {
            heap.add(i);
            if (heap.size() > k) heap.poll();
        }

        int[] sol = new int[k];
        IntStream.rangeClosed(0, k - 1).forEach(i -> sol[k - 1 - i] = heap.poll());
        return sol;
    }

    public int[] kWeakestMismatct(int[][] mat, int k) {
        int[] zeros = new int[mat[0].length];
        Arrays.fill(zeros, 1);

        List<Integer> rows = Arrays.stream(mat).map(vec -> Arrays.mismatch(zeros, vec)).map(i -> i == -1 ? mat[0].length : i).collect(Collectors.toList());

        Comparator<Integer> descFirtIn = Comparator.comparingInt(rows::get).thenComparingInt(i -> i).reversed();
        Queue<Integer> heap = new PriorityQueue<>(k, descFirtIn);

        for (int i = 0; i < mat.length; i++) {
            heap.add(i);
            if (heap.size() > k) heap.poll();
        }

        int[] sol = new int[k];
        IntStream.rangeClosed(0, k - 1).forEach(i -> sol[k - 1 - i] = heap.poll());
        return sol;
    }

    public int kthSmallest2(int[][] matrix, int k) {
            Queue<int[]> heap = new PriorityQueue<>(matrix.length, Comparator.comparingInt(ter -> matrix[ter[0]][ter[1]]));
            IntStream.range(0, matrix.length).mapToObj(row -> new int[]{row, 0})
                    .forEach(heap::offer);

            int[] minTer = new int[2];
            for (int count = 0; count < k; count++) {
                minTer = heap.poll();
                if (minTer[1] < matrix[0].length - 1) heap.offer(new int[]{minTer[0], minTer[1]+1});
            }
            return matrix[minTer[0]][minTer[1]];
    }

    class MyHeapNode {

        int row;
        int column;
        int value;

        public MyHeapNode(int v, int r, int c) {
            this.value = v;
            this.row = r;
            this.column = c;
        }

        public int getValue() {
            return this.value;
        }

        public int getRow() {
            return this.row;
        }

        public int getColumn() {
            return this.column;
        }
    }

    class MyHeapComparator implements Comparator<MyHeapNode> {
        public int compare(MyHeapNode x, MyHeapNode y) {
            return x.value - y.value;
        }
    }

    public int kthSmallest(int[][] matrix, int k) {

        int N = matrix.length;

        PriorityQueue<MyHeapNode> minHeap =
                new PriorityQueue<MyHeapNode>(Math.min(N, k), new MyHeapComparator());

        // Preparing our min-heap
        for (int r = 0; r < Math.min(N, k); r++) {

            // We add triplets of information for each cell
            minHeap.offer(new MyHeapNode(matrix[r][0], r, 0));
        }

        MyHeapNode element = minHeap.peek();
        while (k-- > 0) {

            // Extract-Min
            element = minHeap.poll();
            int r = element.getRow(), c = element.getColumn();

            // If we have any new elements in the current row, add them
            if (c < N - 1) {

                minHeap.offer(new MyHeapNode(matrix[r][c + 1], r, c + 1));
            }
        }

        return element.getValue();
    }


    @Test
    public void testSmallest() {
        assertEquals(3, kthSmallest(new int[][]{{1, 2}, {3, 4}}, 3));
        assertEquals(4, kthSmallest(new int[][]{{1, 2}, {3, 4}}, 4));
        assertEquals(1, kthSmallest(new int[][]{{1}}, 1));
        assertEquals(1, kthSmallest(new int[][]{{1, 2}, {3, 4}}, 1));
        assertEquals(2, kthSmallest(new int[][]{{1, 2}, {3, 4}}, 2));
        assertEquals(9, kthSmallest(new int[][]{{3,8,8}, {3,8,8}, {3,9,13}}, 8));
        assertEquals(14, kthSmallest(new int[][]{{1, 3, 5}, {6, 7, 12}, {11, 14, 14}}, 8));
        assertEquals(11, kthSmallest(new int[][]{{1, 3, 5}, {6, 7, 12}, {11, 14, 14}}, 6));
        assertEquals(13, kthSmallest(new int[][]{{1,5,9}, {10,11,13}, {12,13,15}}, 8));
    }


    @Test
    public void testWeekestRow() {
        assertArrayEquals(new int[]{0, 1}, kWeakestRowsBinary2(new int[][]{{1, 0}, {1, 1}}, 2));


        assertArrayEquals(new int[]{0}, kWeakestRowsBinary2(new int[][]{{1, 0}, {1, 0}}, 1));
        assertArrayEquals(new int[]{0}, kWeakestRowsBinary2(new int[][]{{1, 0}, {1, 1}}, 1));
        assertArrayEquals(new int[]{0, 1}, kWeakestRowsBinary2(new int[][]{{1, 0}, {1, 0}}, 2));
        assertArrayEquals(new int[]{0, 1}, kWeakestRowsBinary2(new int[][]{{1, 0}, {1, 0}}, 2));
        assertArrayEquals(new int[]{2, 0, 3}, kWeakestRowsBinary2(new int[][]{
                {1, 1, 0, 0, 0},
                {1, 1, 1, 1, 0},
                {1, 0, 0, 0, 0},
                {1, 1, 0, 0, 0},
                {1, 1, 1, 1, 1}}, 3));

        assertArrayEquals(new int[]{2, 0, 3}, kWeakestRowsBinary2(new int[][]{
                {1, 1, 0, 0, 0},
                {1, 1, 1, 1, 0},
                {1, 0, 0, 0, 0},
                {1, 1, 1, 0, 0},
                {1, 1, 1, 1, 1}}, 3));
    }

    @Test
    public void testWeakestLongLeet() {
        assertArrayEquals(sol, kWeakestRowsLeet(mat, size));
    }

    @Test
    public void testWeakestLongBinary() {
        assertArrayEquals(sol, kWeakestRowsBinary(mat, size));
    }

    @Test
    public void testWeakestLongBinary2() {
        assertArrayEquals(sol, kWeakestRowsBinary2(mat, size));
    }

    @Test
    public void testWeakestLongSum() {
        assertArrayEquals(sol, kWeakestRowsSum(mat, size));
    }

    @Test
    public void testWeakestLongMismatch() {
        assertArrayEquals(sol, kWeakestMismatct(mat, size));
    }

    @Test
    public void testStone() {
        assertEquals(1, lastStoneWeight(new int[]{2, 7, 4, 1, 8, 1}));
        assertEquals(0, lastStoneWeight(new int[]{2, 2}));
//        assertEquals(5, lastStoneWeight(new int[]{31, 26, 33, 21, 40}));
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

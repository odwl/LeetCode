import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ClosestPointTest {
    public int[][] kClosest(int[][] points, int k) {
        Queue<int[]> queue = new PriorityQueue<int[]>(k,
                (a, b) -> -(a[0] * a[0] + a[1] * a[1]) + (b[0] * b[0] + b[1] * b[1]));

        for (int[] point : points) {
            queue.offer(point);
            if (queue.size() > k) queue.poll();
        }
        int[][] sol = new int[k][2];
        return queue.toArray(sol);
    }

    public int connectSticks(int[] sticks) {
        Queue<Integer> queue = new PriorityQueue<>(sticks.length);
        Arrays.stream(sticks).forEach(queue::offer);
        int sum = 0;
        while (queue.size() > 1) {
            int elem = queue.poll() + queue.poll();
            queue.offer(elem);
            sum += elem;
        }
        return sum;
    }


    public int maxDistToClosestRec(int[] seats, int start) {
        if (seats.length - start == 2) return 1;

        OptionalInt first = IntStream.range(start + (seats[start] == 1 ? 1 : 0), seats.length).dropWhile(i -> seats[i] == 0).findFirst();
        if (first.isEmpty()) return seats.length - start - 1;
        int nextOne = first.getAsInt();
        int sol = (nextOne - start) / (seats[start] == 1 ? 2 : 1);
        if (nextOne < seats.length - 1) {
            sol = Math.max(sol, maxDistToClosestRec(seats, nextOne));
        }
        return sol;
    }

    public int maxDistToClosest(int[] seats) {
        return maxDistToClosestRec(seats, 0);
    }


    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        Queue<Integer> brickUsage = new PriorityQueue<>(Comparator.reverseOrder());

        int i = 1;
        for (; i < heights.length; i++) {
            int diff = heights[i] - heights[i - 1];
            if (diff <= 0) continue;

            if (bricks < diff && ladders > 0) {
                    ladders--;
                    if (!brickUsage.isEmpty() && brickUsage.peek() > diff) {
                        bricks += brickUsage.poll();
                    } else continue;
            }

            if (bricks < diff) break;

            brickUsage.offer(diff);
            bricks -= diff;
        }
        return i - 1;
    }

    @Test
    public void testBuilding() {
        assertEquals(7, furthestBuilding(new int[]{1, 13, 1, 1, 13, 5, 11, 11}, 10, 8));


        assertEquals(7, furthestBuilding(new int[]{4, 12, 2, 7, 3, 18, 20, 3, 19}, 10, 2));
        assertEquals(4, furthestBuilding(new int[]{4, 2, 7, 6, 9, 14, 12}, 5, 1));
        assertEquals(3, furthestBuilding(new int[]{14, 3, 19, 3}, 17, 0));
    }

    @Test
    public void testMax() {
        assertEquals(2, maxDistToClosest(new int[]{1, 0, 0, 0, 1, 0, 1}));

        assertEquals(2, maxDistToClosest(new int[]{1, 0, 0}));
        assertEquals(3, maxDistToClosest(new int[]{1, 0, 0, 0}));
        assertEquals(4, maxDistToClosest(new int[]{1, 0, 0, 0, 0}));

        assertEquals(1, maxDistToClosest(new int[]{1, 0}));
        assertEquals(1, maxDistToClosest(new int[]{1, 0, 1}));
        assertEquals(2, maxDistToClosest(new int[]{0, 0, 1}));
        assertEquals(4, maxDistToClosest(new int[]{0, 0, 0, 0, 1, 0, 1}));
        assertEquals(3, maxDistToClosest(new int[]{1, 0, 0, 0, 0, 0, 1, 0, 1}));
        assertEquals(3, maxDistToClosest(new int[]{1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1}));
    }

    @Test
    public void testConnected() {
        assertEquals(14, connectSticks(new int[]{2, 4, 3}));

        assertEquals(0, connectSticks(new int[]{1}));
        assertEquals(30, connectSticks(new int[]{1, 8, 3, 5}));
        assertEquals(151646, connectSticks(new int[]{3354, 4316, 3259, 4904, 4598, 474, 3166, 6322, 8080, 9009}));
    }

    @Test
    public void testClosest() {
        assertArrayEquals(new int[][]{{-2, 2}}, kClosest(new int[][]{{1, 3}, {-2, 2}}, 1));

        assertArrayEquals(new int[][]{{1, 1}}, kClosest(new int[][]{{1, 1}, {2, 2}}, 1));
        assertArrayEquals(new int[][]{{2, 2}, {1, 1}}, kClosest(new int[][]{{1, 1}, {2, 2}}, 2));
        assertArrayEquals(new int[][]{{1, 1}}, kClosest(new int[][]{{1, 1}}, 1));

    }

}

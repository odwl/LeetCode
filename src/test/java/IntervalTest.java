import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.*;


public class IntervalTest {

    public int findMinArrowShots(int[][] points) {
        Arrays.sort(points, (o1, o2) -> {
            if (o1[1] == o2[1]) return 0;
            if (o1[1] < o2[1]) return -1;
            return 1;
        });
        int arrow = 1;
        int end = points[0][1];
        for (int[] p : points) {
            int xStart = p[0];
            int xEnd = p[1];
            if (xStart > end) {
                arrow++;
                end = xEnd;
            }
        }
        return arrow;
    }

    public int findMinArrowShots3(int[][] points) {
        Arrays.sort(points, Comparator.comparingInt(vec -> vec[1]));
        int count = 0;

        long cut = -Long.MAX_VALUE;
        for (int[] point : points) {
            cut = Math.min(cut, point[1]);
            if (point[0] > cut) {
                cut = point[1];
                count++;
            }
        }
        return count;
    }

    private int findMinArrowShotsRec(int[][] points, int first) {
        if (points.length == first) return 0;

        int cut = points[first][1];
        first++;
        while (first != points.length && points[first][0] <= cut) {
            cut = Math.min(cut, points[first][1]);
            first++;
        }
        return 1 + findMinArrowShotsRec(points, first);
    }

    public int findMinArrowShots2(int[][] points) {
        Arrays.sort(points, Comparator.comparingInt(vec -> vec[1]));
        return findMinArrowShotsRec(points, 0);
    }

    public boolean canAttendMeetings(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(vec -> vec[0]));
        return IntStream.range(0, intervals.length - 1)
                .allMatch(i -> intervals[i][1] <= intervals[i + 1][0]);
    }

    public int minMeetingRooms2(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(vec -> vec[0]));

        Queue<Integer> rooms = new PriorityQueue<>();
        for (int[] pair: intervals) {
            if (!rooms.isEmpty() && rooms.peek() <= pair[0]) rooms.poll();
            rooms.offer(pair[1]);
        }

        return rooms.size();
    }

    public int minMeetingRooms(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(vec -> vec[0]));

        Queue<Integer> queue = Arrays.stream(intervals).collect(
                PriorityQueue<Integer>::new,
                (rooms, meeting) -> {
                    if (!rooms.isEmpty() && rooms.peek() <= meeting[0]) rooms.poll();
                    rooms.offer(meeting[1]);
                },
                (q, l) -> q.addAll(l));
        return queue.size();
    }


    @Test
    public void testMin() {
        assertEquals(2, minMeetingRooms(new int[][]{{1,2}, {1, 2}, {3, 4}}));
        assertEquals(1, minMeetingRooms(new int[][]{{13,15}, {1, 13}}));
        assertEquals(2, minMeetingRooms(new int[][]{{19, 20}, {1, 10}, {5, 14}}));
        assertEquals(1, minMeetingRooms(new int[][]{{1, 2}}));
    }

    @Test
    public void testCan() {

        assertTrue(canAttendMeetings(new int[][]{{13, 15}, {1, 13}}));

        assertFalse(canAttendMeetings(new int[][]{{19, 20}, {1, 10}, {5, 14}}));

        assertFalse(canAttendMeetings(new int[][]{{5, 8}, {6, 8}}));


        assertFalse(canAttendMeetings(new int[][]{{6, 15}, {13, 20}, {6, 17}}));

        assertFalse(canAttendMeetings(new int[][]{{0, 30}, {5, 10}, {15, 20}}));
        assertTrue(canAttendMeetings(new int[0][0]));
        assertTrue(canAttendMeetings(new int[][]{{1, 2}}));
        assertTrue(canAttendMeetings(new int[][]{{1, 2}, {3, 4}}));
    }


    @Test
    public void testArrows() {
        assertEquals(1, findMinArrowShots(new int[][]{{-2147483648, 2147483647}}));
        assertEquals(2, findMinArrowShots(new int[][]{{9, 12}, {1, 10}, {4, 11}, {8, 12}, {3, 9}, {6, 9}, {6, 7}}));
        assertEquals(2, findMinArrowShots(new int[][]{{3, 9}, {7, 12}, {3, 8}, {6, 8}, {9, 10}, {2, 9}, {0, 9}, {3, 9}, {0, 6}, {2, 8}}));
//        assertEquals(0, findMinArrowShots(new int[0][0]));
        assertEquals(1, findMinArrowShots(new int[][]{{10, 16}}));
        assertEquals(2, findMinArrowShots(new int[][]{{10, 16}, {2, 8}, {1, 6}, {7, 12}}));
    }


}

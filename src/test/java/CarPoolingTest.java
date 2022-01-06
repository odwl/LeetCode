import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CarPoolingTest {

    public boolean carPooling(int[][] trips, int capacity) {

        int[] road = new int[999];
        for (int[] trip: trips) {
            for (int pos = trip[1]; pos < trip[2]; pos++) {
                road[pos] += trip[0];
                if (road[pos] > capacity) return false;
            }
        }

        return true;
    }

    @Test
    public void testCar() {
        assertTrue(carPooling(new int[][]{{2,1,5}, {3,5,7}}, 3));

        assertFalse(carPooling(new int[][]{{2,1,5}}, 1));
        assertTrue(carPooling(new int[][]{{2,1,5}}, 2));
        assertTrue(carPooling(new int[][]{{2,0,1}, {2, 1,2}}, 2));
        assertFalse(carPooling(new int[][]{{2,0,1}, {2, 0,2}}, 2));
        assertTrue(carPooling(new int[][]{{2,0,1}, {2, 2,2}}, 2));
        assertTrue(carPooling(new int[][]{{2,0,1}, {1, 1,2}}, 3));


    }
}

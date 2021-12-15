import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HouseRobberTest {

    public static int houseRobber(int[] houses) {
        if (houses.length == 1) return houses[0];

        int two = houses[houses.length - 1];
        int one = Math.max(two, houses[houses.length - 2]);

        for (int i = houses.length - 3; i >= 0; i--) {
            int temp = houses[i] + two;
            two = one;
            one = Math.max(temp, one);
        }
        return one;
    }

    @Test
    public void testHouseRobber() {

        assertEquals(4, houseRobber(new int[]{1, 2, 3, 1}));

        assertEquals(1, houseRobber(new int[]{1}));
        assertEquals(2, houseRobber(new int[]{1, 2}));
        assertEquals(2, houseRobber(new int[]{2, 1}));
        assertEquals(4, houseRobber(new int[]{1, 2, 3}));
        assertEquals(3, houseRobber(new int[]{1, 3, 2}));
        assertEquals(4, houseRobber(new int[]{3, 2, 1}));
        System.out.println("-------");
        assertEquals(12, houseRobber(new int[]{2, 7, 9, 3, 1}));
    }
}

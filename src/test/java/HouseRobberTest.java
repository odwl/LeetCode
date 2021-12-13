import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class HouseRobberTest {

    public static Map<String, Integer> cache = new HashMap<>();


    public static int houseRobber(int[] houses) {
        if (houses.length == 1) return houses[0];
        if (houses.length == 2) return Math.max(houses[0], houses[1]);

        int[] sub1 = Arrays.copyOfRange(houses, 1, houses.length);
        int second_sol = cache.computeIfAbsent(sub1.toString(), s -> houseRobber(sub1));

        int[] sub2 = Arrays.copyOfRange(houses, 2, houses.length);
        int first_sol = houses[0] + cache.computeIfAbsent(sub2.toString(), s -> houseRobber(sub2));

        return Math.max(first_sol, second_sol);
    }

    @Test
    public void testHouseRobber() {
        assertEquals(1,houseRobber(new int[] {1}));
        assertEquals(2,houseRobber(new int[] {1,2}));
        assertEquals(2,houseRobber(new int[] {2,1}));
        assertEquals(4,houseRobber(new int[] {1,2,3}));
        assertEquals(3,houseRobber(new int[] {1,3,2}));
        assertEquals(4,houseRobber(new int[] {3,2,1}));
        assertEquals(4,houseRobber(new int[] {1,2,3,1}));
        System.out.println("-------");
        assertEquals(12,houseRobber(new int[] {2,7,9,3,1}));
    }
}

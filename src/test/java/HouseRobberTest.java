import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class HouseRobberTest {

    public static Map<List<Integer>, Integer> cache = new ConcurrentHashMap<>();


    public static int houseRobber(int[] houses) {
        System.out.println("len: " + houses.length);
        if (houses.length == 1) return houses[0];
        if (houses.length == 2) return Math.max(houses[0], houses[1]);

        int first_sol = houses[0] + houseRobber(Arrays.copyOfRange(houses, 2, houses.length));
        int second_sol = houseRobber(Arrays.copyOfRange(houses, 1, houses.length));

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

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class HouseRobberTest {

    private static Map<List<Integer>, Integer> cache = new HashMap<>();


    private static void populate(List<Integer> houses) {

        if (houses.size() == 1)  {
            cache.put(houses, houses.get(0));
            return;

        }
        if (houses.size() == 2)  {
            cache.put(houses, Math.max(houses.get(0), houses.get(1)));
            return;
        }

        int second_sol = cache.get(houses.subList(1, houses.size()));
        int first_sol = houses.get(0) + cache.get(houses.subList(2, houses.size()));

        int max = Math.max(first_sol, second_sol);
        cache.put(houses, max);
    }

    public static int houseRobber(int[] houses) {
        List<Integer> list = Arrays.stream(houses).boxed().collect(Collectors.toList());
        for (int i = houses.length - 1; i >= 0; i--) {
            populate(list.subList(i, list.size()));
        }
        return cache.get(list);
    }

    @Test
    public void testHouseRobber() {

        assertEquals(4,houseRobber(new int[] {1,2,3,1}));

        assertEquals(1,houseRobber(new int[] {1}));
        assertEquals(2,houseRobber(new int[] {1,2}));
        assertEquals(2,houseRobber(new int[] {2,1}));
        assertEquals(4,houseRobber(new int[] {1,2,3}));
        assertEquals(3,houseRobber(new int[] {1,3,2}));
        assertEquals(4,houseRobber(new int[] {3,2,1}));
        System.out.println("-------");
        assertEquals(12,houseRobber(new int[] {2,7,9,3,1}));
    }
}

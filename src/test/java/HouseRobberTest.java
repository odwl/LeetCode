import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class HouseRobberTest {

    private static int one, two;

    private static void populate(List<Integer> houses) {

        if (houses.size() == 2)  {
            two = one;
            one = Math.max(houses.get(0), houses.get(1));
            return;
        }

        int second_sol = one;
        int first_sol = houses.get(0) + two;
        two = one;
        one = Math.max(first_sol, second_sol);
    }

    public static int houseRobber(int[] houses) {
        one = houses[houses.length-1];

        List<Integer> list = Arrays.stream(houses).boxed().collect(Collectors.toList());
        for (int i = houses.length - 2; i >= 0; i--) {
            populate(list.subList(i, list.size()));
        }
        return one;
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

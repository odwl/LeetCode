import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class Forbidden {

    public static class MinJump implements Supplier<Integer> {
        Map<Integer, Integer> dp;
        Set<Integer> forbid;
        int forward, backward, end;
        LinkedList<Integer> candidates;

        public MinJump(int[] forbidden, int forward, int backward, int end) {
            dp = new HashMap<>();
            forbid = Arrays.stream(forbidden).boxed().collect(Collectors.toUnmodifiableSet());
            this.forward = forward;
            this.backward = backward;
            this.end = end;
            this.candidates = new LinkedList<Integer>();
        }

        @Override
        public Integer get() {
            forbid.forEach(el -> dp.put(el, -1));
            candidates.push(0);
            dp.put(0, 0);

            while (candidates.size() > 0) {
                int cand = candidates.pop();

                int cand1 = cand + forward;
                if ((!dp.containsKey(cand1) || dp.get(cand1) > dp.get(cand) + 1) && cand1 <= 6000) {
                    dp.put(cand1, dp.get(cand) + 1);
                    candidates.push(cand1);

                    if (cand1 > backward && (!dp.containsKey(cand1 - backward) || dp.get(cand1 - backward) > dp.get(cand) + 2)) {
                        dp.put(cand1 - backward, dp.get(cand) + 2);
                        candidates.push(cand1 - backward);
                    }
                }
            }

            return dp.containsKey(end) ? dp.get(end) : -1;
        }
    }

    public static int minimumJumps(int[] forbidden, int a, int b, int x) {
        return new MinJump(forbidden, a, b, x).get();
    }


    @Test
    public void testJump() {
        assertEquals(3998, minimumJumps(new int[]{1998}, 1999, 2000, 2000));



        assertEquals(4, minimumJumps(new int[]{}, 3, 2, 2));

        assertEquals(-1, minimumJumps(new int[]{8, 3, 16, 6, 12, 20}, 15, 13, 11));

        assertEquals(2, minimumJumps(new int[]{1, 6, 2, 14, 5, 17, 4}, 16, 9, 7));


        assertEquals(3, minimumJumps(new int[]{}, 3, 15, 9));
        assertEquals(-1, minimumJumps(new int[]{6}, 3, 15, 9));

        assertEquals(-1, minimumJumps(new int[]{3}, 3, 15, 6));

        assertEquals(3, minimumJumps(new int[]{}, 3, 2, 4));
        assertEquals(2, minimumJumps(new int[]{}, 3, 2, 1));
        assertEquals(10, minimumJumps(new int[]{}, 3, 7, 10));

        assertEquals(0, (int) new MinJump(new int[]{}, 3, 15, 0).get());
        assertEquals(1, (int) new MinJump(new int[]{}, 3, 15, 3).get());
        assertEquals(2, (int) new MinJump(new int[]{}, 3, 15, 6).get());
        assertEquals(3, (int) new MinJump(new int[]{}, 3, 15, 9).get());

        assertEquals(1, minimumJumps(new int[]{}, 3, 15, 3));
        assertEquals(2, minimumJumps(new int[]{}, 3, 15, 6));
        assertEquals(3, minimumJumps(new int[]{}, 3, 15, 9));

        assertEquals(3, minimumJumps(new int[]{14, 4, 18, 1, 15}, 3, 15, 9));

        assertEquals(-1, minimumJumps(new int[]{}, 3, 15, 2));

        assertEquals(1, (int) new MinJump(new int[]{}, 3, 15, 3).get());


    }

    @Test
    public void testJump2() {
        assertEquals(120, (int) new MinJump(new int[]{    1401,832,1344,173,1529,1905,1732,277,1490,650,1577,1886,185,1728,1827,1924,1723,1034,1839,1722,1673,1198,1667,538,911,1221,1201,1313,251,752,40,1378,1515,1789,1580,1422,907,1536,294,1677,1807,1419,1893,654,1176,812,1094,1942,876,777,1850,1382,760,347,112,1510,1278,1607,1491,429,1902,1891,647,1560,1569,196,539,836,290,1348,479,90,1922,111,1411,1286,1362,36,293,1349,667,430,96,1038,793,1339,792,1512,822,269,1535,1052,233,1835,1603,577,936,1684,1402,1739,865,1664,295,977,1265,535,1803,713,1298,1537,135,1370,748,448,254,1798,66,1915,439,883,1606,796
        }, 19, 18, 1540).get());

    }
}

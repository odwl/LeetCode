

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertArrayEquals;


public class TwoSum {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> d = new HashMap();

        for (int i = 0; i < nums.length; i++) {
            int el = nums[i];
            if (d.containsKey(el)) return new int[]{d.get(el), i};
            d.put(target - el, i);
        }
        return null;
    }

    public int[] twoSumExc(int[] nums, int target, int exc) {
        Map<Integer, Integer> d = new HashMap();

        for (int i = 0; i < nums.length; i++) {
            if (i != exc) {
                int el = nums[i];
                if (d.containsKey(el)) return new int[]{nums[d.get(el)], nums[i]};
                d.put(target - el, i);
            }
        }
        return null;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        if (nums.length <= 1) return new ArrayList<List<Integer>>();

        Set<List<Integer>> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            int[] twoSum = twoSumExc(nums, -nums[i], i);
            if (twoSum != null && twoSum.length == 2) {
                set.add(List.of(nums[i], twoSum[0], twoSum[1]).stream().sorted().collect(Collectors.toList()));
            }
        }

        List<List<Integer>> sol = new ArrayList<List<Integer>>();
        for (List<Integer> l : set) {
            sol.add(l);
        }
        return sol;
    }

    @Test
    public void test3() {
//        assertArrayEquals(new int[][]{{-1, -1, 2}, {-1, 0, 1}}, threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
//
//        assertArrayEquals(new int[][]{{}}, threeSum(new int[]{}));
//        assertArrayEquals(new int[][]{{}}, threeSum(new int[]{0}));

    }


    @Test
    public void test() {
        int[] nums = {2, 7, 11, 15};
        int[] res = twoSum(nums, 9);
        int[] sol = {0, 1};
        assertArrayEquals(sol, res);
    }
}

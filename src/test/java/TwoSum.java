import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;


public class TwoSum {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> d = new HashMap();

        for (int i = 0; i < nums.length; i++) {
            int el = nums[i];
            if (d.containsKey(el)) {
                return new int[]{d.get(el), i};
            }
            d.put(target - el, i);
        }
        return null;
    }



    @Test()
    public void test() {
        int[] nums = {2,7,11,15};
        int[] res = twoSum(nums, 9);
        int[] sol = {0,1};
        assertArrayEquals(sol, res);
    }
}

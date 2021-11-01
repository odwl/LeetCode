import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class FindMinRotation {


    public int findMin(int[] nums) {
        if (nums.length == 0){
            return 60000;
        }

        if (nums.length == 1){
            return nums[0];
        }

        if (nums.length == 2){
            return Math.min(nums[0], nums[1]);
        }

        int mid = (int) Math.floor(nums.length/2);

        if (nums[mid] > nums[mid+1]) {
            return nums[mid+1];
        }

        if (nums[mid-1] > nums[mid]) {
            return nums[mid];
        }

        if (nums[mid-1] <= nums[mid] && nums[mid] <= nums[mid+1]) {

            int[] left = IntStream
                    .range(0, mid)
                    .map(i -> nums[i])
                    .toArray();
            int[] right = IntStream
                    .range(mid + 2, nums.length)
                    .map(i -> nums[i])
                    .toArray();

            int min = nums[mid];
            if (nums[mid+1] <= nums[nums.length-1]) {
                min =  findMin(left);
            }

            if(nums[0] <= nums[mid-1]) {
                min = Math.min(min, findMin(right));
            }

            return min;
        }



        return -1;
    }

    @Test()
    public void test1() {
        int[] nums = {3};
        int res = findMin(nums);
        assertEquals(3, res);
    }

    @Test()
    public void test2() {
        int[] nums = {5, 3};
        int res = findMin(nums);
        assertEquals(3, res);
    }

    @Test()
    public void test2Eq() {
        int[] nums = {3, 3};
        int res = findMin(nums);
        assertEquals(3, res);
    }

    @Test()
    public void test3() {
        int[] nums = {1,2,3};
        int res = findMin(nums);
        assertEquals(1, res);
    }

    @Test()
    public void test3Eq() {
        int[] nums = {1,1,1};
        int res = findMin(nums);
        assertEquals(1, res);
    }

    @Test()
    public void test3EqBis() {
        int[] nums = {1,1,2};
        int res = findMin(nums);
        assertEquals(1, res);
    }

    @Test()
    public void test3EqTer() {
        int[] nums = {2,1,2};
        int res = findMin(nums);
        assertEquals(1, res);
    }

    @Test()
    public void test3EqQuar() {
        int[] nums = {1,1,2};
        int res = findMin(nums);
        assertEquals(1, res);
    }

    @Test()
    public void test3Bis() {
        int[] nums = {2,3,1};
        int res = findMin(nums);
        assertEquals(1, res);
    }

    @Test()
    public void test3Ter() {
        int[] nums = {3,1, 2};
        int res = findMin(nums);
        assertEquals(1, res);
    }

    @Test()
    public void test4() {
        int[] nums = {5,2,3,4};
        int res = findMin(nums);
        assertEquals(2, res);
    }

    @Test()
    public void test4Bis() {
        int[] nums = {1,3,3,3};
        int res = findMin(nums);
        assertEquals(1, res);
    }

    @Test()
    public void test5() {
        int[] nums = {2,2,2,0,1};
        int res = findMin(nums);
        assertEquals(0, res);
    }
    @Test()
    public void test6() {
        int[] nums = {2,2,2,0,1,2};
        int res = findMin(nums);
        assertEquals(0, res);
    }

    @Test()
    public void test10() {
        int[] nums = {3,3,3,3,3,3,3,3,1,3};
        int res = findMin(nums);
        assertEquals(1, res);
    }
}

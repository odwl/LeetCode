import org.junit.Test;

import java.util.function.Supplier;

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

    private static class HouseRobberTree implements Supplier<Integer> {

        RobTree rootResult;

        HouseRobberTree(TreeNode root) {
           rootResult = houseRobberRec(root);
        }

        private  RobTree houseRobberRec(TreeNode root) {
            if (root.left == null && root.right == null) return new RobTree(root.val, 0);

            RobTree left = root.left != null ? houseRobberRec(root.left) : RobTree.ZERO;
            RobTree right = root.right != null ? houseRobberRec(root.right) : RobTree.ZERO;

            return new RobTree(root.val + left.valWithout + right.valWithout, left.max() + right.max());
        }

        @Override
        public Integer get() {
            return rootResult.max();
        }

        private static class RobTree {
            int valWith;
            int valWithout;
            public static final RobTree ZERO = new RobTree(0,0);

            public RobTree(int valWith, int valWithout) {
                this.valWith = valWith;
                this.valWithout = valWithout;
            }

            public int max() {
                return Math.max(this.valWith, this.valWithout);
            }
        }
    }

    public static int houseRobber(TreeNode root) {
        return new HouseRobberTree(root).get();
    }

    @Test
    public void testHouseRobberTree() {

        assertEquals(7, houseRobber(Wrapper.stringToTreeNode("[4,1,null,2,null,3]").get()));

        assertEquals(1, houseRobber(new TreeNode(1)));
        assertEquals(2, houseRobber(Wrapper.stringToTreeNode("[1,2]").get()));
        assertEquals(2, houseRobber(Wrapper.stringToTreeNode("[2,1]").get()));
        assertEquals(6, houseRobber(Wrapper.stringToTreeNode("[1,2,4]").get()));
        assertEquals(6, houseRobber(Wrapper.stringToTreeNode("[1,4,2]").get()));
        assertEquals(4, houseRobber(Wrapper.stringToTreeNode("[4,1,2]").get()));
        assertEquals(7, houseRobber(Wrapper.stringToTreeNode("[3,2,3,null,3,null,1]").get()));
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

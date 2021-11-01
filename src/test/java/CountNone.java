import org.antlr.v4.runtime.tree.Tree;
import org.antlr.v4.runtime.tree.Tree;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CountNone {


    public int countNode(TreeNode root) {
        if (root == null) return 0;

        int count = 1;
        if (root.left != null) count += countNode(root.left);
        if (root.right != null) count += countNode(root.right);
        return count;
    }

    @Test()
    public void testMerge(){

        TreeNode root = Wrapper.stringToTreeNode("[1,2,3,4,5,6]").get();
        assertEquals(6, countNode(root));

        root = Wrapper.stringToTreeNode("[]").get();
        assertEquals(0, countNode(root));

        root = Wrapper.stringToTreeNode("[1]").get();
        assertEquals(1, countNode(root));

//        Wrapper.prettyPrintTree(root)
    }

    private static int size;
    private static TreeNode root;
    @BeforeClass
    public static void before(){
        int size = 10000000;
        Random random = new Random();
        int[] data = random.ints(size).toArray();
        root = Wrapper.arrayToTreeNode(IntStream.of(data).mapToObj(Optional::of).toList()).get();
    }


    @Test()
    public void testBig() {
        assertEquals(size, countNode(root));
    }
    @Test()
    public void testFast() {
        assertEquals(size, size);
    }
}

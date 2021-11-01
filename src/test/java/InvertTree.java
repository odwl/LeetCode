import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InvertTree {

    public TreeNode invertTree(TreeNode root) {
        if (root == null) return root;
        if (root.left == null && root.right == null) return root;

        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;

        return root;
    }

    @Test()
    public void testEmpty(){
        TreeNode root = Wrapper.stringToTreeNode("[]");
        assertEquals("[]", Wrapper.treeNodeToString(invertTree(root)));
    }

    @Test()
    public void testSingle(){
        TreeNode root = Wrapper.stringToTreeNode("[1]");
        assertEquals("[1]", Wrapper.treeNodeToString(invertTree(root)));
    }

    @Test()
    public void testTwo(){
        TreeNode root = Wrapper.stringToTreeNode("[1,null,2]");
        assertEquals("[1,2,null]", Wrapper.treeNodeToString(invertTree(root)));
    }

    @Test()
    public void testTwoBis(){
        TreeNode root = Wrapper.stringToTreeNode("[1,2]");
        assertEquals("[1,null,2]", Wrapper.treeNodeToString(invertTree(root)));
    }

    @Test()
    public void testThree(){
        TreeNode root = Wrapper.stringToTreeNode("[4,2,1]");
        assertEquals("[4,1,2]", Wrapper.treeNodeToString(invertTree(root)));

        root = Wrapper.stringToTreeNode("[4,null,1]");
        assertEquals("[4,1,null]", Wrapper.treeNodeToString(invertTree(root)));

        root = Wrapper.stringToTreeNode("[4,1]");
        assertEquals("[4,null,1]", Wrapper.treeNodeToString(invertTree(root)));
    }

    @Test()
    public void testFour(){
        TreeNode root = Wrapper.stringToTreeNode("[4,2,1,3]");
        assertEquals("[4,1,2,null,null,null,3]", Wrapper.treeNodeToString(invertTree(root)));
    }

    @Test()
    public void testMerge(){
        TreeNode root = Wrapper.stringToTreeNode("[4,2,1,3,7,6,9]");
        assertEquals("[4,7,9,6,2,3,1]",
                Wrapper.treeNodeToString(invertTree(root)));
    }
}

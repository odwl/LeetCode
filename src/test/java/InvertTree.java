import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InvertTree {

    @Test()
    public void testSingle(){
        TreeNode root = Wrapper.stringToTreeNode("[1]");
//        assert root != null;
        assertEquals("[1]", root.invert().toString());
    }

    @Test()
    public void testTwo(){
        TreeNode root = Wrapper.stringToTreeNode("[1,null,2]");
        assertEquals("[1,2]", root.invert().toString());
    }

    @Test()
    public void testTwoBis(){
        TreeNode root = Wrapper.stringToTreeNode("[1,2]");
        assertEquals("[1,null,2]", root.invert().toString());
    }

    @Test()
    public void testThree(){
        TreeNode root = Wrapper.stringToTreeNode("[4,2,1]");
        assertEquals("[4,1,2]", root.invert().toString());

        root = Wrapper.stringToTreeNode("[4,null,1]");
        assertEquals("[4,1]", root.invert().toString());

        root = Wrapper.stringToTreeNode("[4,1]");
        assertEquals("[4,null,1]", root.invert().toString());
    }

    @Test()
    public void testFour(){
        TreeNode root = Wrapper.stringToTreeNode("[4,2,1,3]");
        assertEquals("[4,1,2,null,null,null,3]", root.invert().toString());

        root = Wrapper.stringToTreeNode("[4,2,1,null,3]");
        assertEquals("[4,1,2,null,null,3]", root.invert().toString());

        root = Wrapper.stringToTreeNode("[4,2,1,null,null,3]");
        assertEquals("[4,1,2,null,3]", root.invert().toString());

        root = Wrapper.stringToTreeNode("[4,2,1,null,null,null,3]");
        assertEquals("[4,1,2,3]", root.invert().toString());
    }

    @Test()
    public void testMerge(){
        TreeNode root = Wrapper.stringToTreeNode("[4,2,1,3,7,6,9]");
        assertEquals("[4,1,2,9,6,7,3]",
                root.invert().toString());
    }
}

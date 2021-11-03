import com.google.common.collect.Iterators;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class TreeNodeTest {

    @Test
    public void testParseFloor() {
        TreeNode root;

        root = Wrapper.arrayToTreeNode(Stream.of(2).map(Optional::of).toList()).get();
        assertEquals(List.of(2), root.floorParse().stream().map(Optional::get).toList());

        root = Wrapper.arrayToTreeNode(Stream.of(2, 3).map(Optional::of).toList()).get();
        assertEquals(List.of(2, 3), root.floorParse().stream().map(Optional::get).toList());

        root = Wrapper.arrayToTreeNode(Stream.of(4, 2, 1, 3).map(Optional::of).toList()).get();
        assertEquals(List.of(4, 2, 1, 3), root.floorParse().stream().map(Optional::get).toList());
    }

    @Test
    public void testToStringOne() {
        TreeNode root = new TreeNode(1);
        assertEquals("[1]", root.toString());
    }

    @Test
    public void testRound() {
        assertEquals("[1]", Wrapper.stringToTreeNode("[1]").get().toString());
        assertEquals("[1,2,3]", Wrapper.stringToTreeNode("[1,2,3]").get().toString());
    }

    @Test
    public void testRoundNull() {
        assertEquals("[1,null,3]", Wrapper.stringToTreeNode("[1,null,3]").get().toString());
    }

    @Test
    public void testEquals() {
        assertTrue(new TreeNode(1).equals(new TreeNode(1)));
        assertFalse(new TreeNode(1).equals(new TreeNode(2)));
        assertFalse(new TreeNode(1, new TreeNode(2), null).equals(new TreeNode(1)));

        TreeNode root = Wrapper.stringToTreeNode("[1,2,3]").get();
        TreeNode root2 = Wrapper.stringToTreeNode("[1,2,3]").get();
        assertTrue(root.equals(root2));

        root = Wrapper.stringToTreeNode("[1,2,3]").get();
        root2 = Wrapper.stringToTreeNode("[1,2,3,4]").get();
        assertFalse(root.equals(root2));
    }

    @Test
    public void testMerge() {
        TreeNode root1;
        TreeNode root2;
        TreeNode merge;

        root1 = new TreeNode(1);
        root2 = new TreeNode(1);
        merge = root1.merge(root2);
        assertEquals("[2]", merge.toString());

        root1 = new TreeNode(1);
        root2 = Wrapper.stringToTreeNode("[1,2]").get();
        merge = root1.merge(root2);
        assertEquals("[2,2]", merge.toString());

        root1 = new TreeNode(1);
        root2 = Wrapper.stringToTreeNode("[1,null,2]").get();
        merge = root1.merge(root2);
        assertEquals("[2,null,2]", merge.toString());

        root1 = Wrapper.stringToTreeNode("[1,1,1]").get();
        root2 = Wrapper.stringToTreeNode("[1,null,2]").get();
        merge = root1.merge(root2);
        assertEquals("[2,1,3]", merge.toString());

        root1 = Wrapper.stringToTreeNode("[1,1,1]").get();
        root2 = null;
        merge = root1.merge(root2);
        assertEquals("[1,1,1]", merge.toString());
    }

    @Test
    public void testGetChildren() {
        TreeNode root;
        root = new TreeNode(1);
        assertTrue(root.getPresentChildren().toList().isEmpty());

        root = new TreeNode(1, new TreeNode(2), null);
        Iterators.elementsEqual(List.of(new TreeNode(2)).iterator(), root.getPresentChildren().iterator());

        root = new TreeNode(1, null, new TreeNode(2));
        Iterators.elementsEqual(List.of(new TreeNode(2)).iterator(), root.getPresentChildren().iterator());

        root = Wrapper.stringToTreeNode("[1,2,3]").get();
        List<TreeNode> list = root.getPresentChildren().toList();
        Iterators.elementsEqual(List.of(new TreeNode(2)).iterator(), list.iterator());
    }

    @Test
    public void testInOrder() {
        TreeNode root = Wrapper.stringToTreeNode("[1,2,3,4]").get();
        Stream<Integer> inOrder = root.inOrderParse().map(TreeNode::getVal);
        assertEquals("[1, 2, 4, 3]", inOrder.toList().toString());
    }

    @Test
    public void testPreOrder() {
        TreeNode root = Wrapper.stringToTreeNode("[1,2,3,4]").get();
        Stream<Integer> inOrder = root.preOrderParse().map(TreeNode::getVal);
        assertEquals("[4, 2, 1, 3]", inOrder.toList().toString());
    }
}

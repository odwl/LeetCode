import com.google.common.collect.Iterators;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class TreeNodeTest {

    public int sumRootToLeaf(TreeNode root) {
        int sum = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            int shift = node.val << 1;

            if (node.left != null) {
                node.left.val += shift;
                queue.offer(node.left);
            }
            if (node.right != null) {
                node.right.val += shift;
                queue.offer(node.right);
            }
            if (node.left == null && node.right == null) sum += node.val;
        }
        return sum;
    }

    @Test
    public void testSum() {
        assertEquals(22, sumRootToLeaf(Wrapper.stringToTreeNode("[1,0,1,0,1,0,1]").get()));
        assertEquals(4, sumRootToLeaf(Wrapper.stringToTreeNode("[1,0,0]").get()));
        assertEquals(5, sumRootToLeaf(Wrapper.stringToTreeNode("[1,1,0]").get()));
        assertEquals(6, sumRootToLeaf(Wrapper.stringToTreeNode("[1,1,1]").get()));
        assertEquals(2, sumRootToLeaf(Wrapper.stringToTreeNode("[1,0]").get()));
        assertEquals(1, sumRootToLeaf(Wrapper.stringToTreeNode("[1]").get()));
    }

    @Test
    public void testParseFloor() {
        TreeNode root;

        root = Wrapper.arrayToTreeNode(Stream.of(2).map(Optional::of).toList());
        assertEquals(List.of(2), root.floorParse().stream().map(Optional::get).toList());

        root = Wrapper.arrayToTreeNode(Stream.of(2, 3).map(Optional::of).toList());
        assertEquals(List.of(2, 3), root.floorParse().stream().map(Optional::get).toList());

        root = Wrapper.arrayToTreeNode(Stream.of(4, 2, 1, 3).map(Optional::of).toList());
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
        assertEquals("[4, 2, 1, 3]", inOrder.toList().toString());
    }

    @Test
    public void testPreOrder() {
        TreeNode root = Wrapper.stringToTreeNode("[2,4]").get();
        Stream<Integer> inOrder = root.preOrderParse().map(TreeNode::getVal);
        assertEquals("[2, 4]", inOrder.toList().toString());

        root = Wrapper.stringToTreeNode("[1,2,3,4]").get();
        inOrder = root.preOrderParse().map(TreeNode::getVal);
        assertEquals("[1, 2, 4, 3]", inOrder.toList().toString());
    }

    @Test
    public void testPostOrder() {
        TreeNode root = Wrapper.stringToTreeNode("[1,2,3,4]").get();
        Stream<Integer> inOrder = root.postOrderParse().map(TreeNode::getVal);
        assertEquals("[4, 2, 3, 1]", inOrder.toList().toString());
    }

    @Test
    public void testMinDifference() {
        TreeNode root = Wrapper.stringToTreeNode("[1,2]").get();
        assertEquals(1, root.minDifference());

        root = Wrapper.stringToTreeNode("[1,3]").get();
        assertEquals(2, root.minDifference());
    }

    @Test
    public void testIsSymetric() {
        assertTrue(Wrapper.stringToTreeNode("[1]").get().isSymetric());
        assertFalse(Wrapper.stringToTreeNode("[1,2]").get().isSymetric());
        assertFalse(Wrapper.stringToTreeNode("[1,2,3]").get().isSymetric());
        assertTrue(Wrapper.stringToTreeNode("[1,2,2]").get().isSymetric());
        assertTrue(Wrapper.stringToTreeNode("[1,2,2,3,4,4,3]").get().isSymetric());
        assertFalse(Wrapper.stringToTreeNode("[1,2,2,3,4,4,2]").get().isSymetric());
        assertFalse(Wrapper.stringToTreeNode("[1,2,2,null,3,null,3]").get().isSymetric());
        assertFalse(Wrapper.stringToTreeNode("[1,2,2,2,null,2]").get().isSymetric());
    }

    @Test
    public void testLeaveNodes() {
        Stream<TreeNode> treeNodeStream = TreeNode.leaveNodes(Wrapper.stringToTreeNode("[1,2,2,2,null,2]").get());
        assertEquals(List.of(2, 2), treeNodeStream.map(TreeNode::getVal).toList());
    }

    @Test
    public void testSumLeaveNodes() {
        TreeNode root = Wrapper.stringToTreeNode("[1,2,2,2,null,2]").get();
        assertEquals(4, TreeNode.sumLeaveNodes(root));
    }

    @Test
    public void testSumLeftLeaveNodes() {
        TreeNode root = Wrapper.stringToTreeNode("[1,2,2,2,null,2]").get();
        assertEquals(4, TreeNode.sumLeftLeaveNodes(root));

        root = Wrapper.stringToTreeNode("[1,2,2,2,2]").get();
        assertEquals(2, TreeNode.sumLeftLeaveNodes(root));

        root = Wrapper.stringToTreeNode("[1,2,2,2,null,null, 2]").get();
        assertEquals(2, TreeNode.sumLeftLeaveNodes(root));

        root = Wrapper.stringToTreeNode("[3,9,20,null,null,15,7]").get();
        assertEquals(24, TreeNode.sumLeftLeaveNodes(root));

        root = Wrapper.stringToTreeNode("[0,2,4,1,null,3,-1,5,1,null,6,null,8]").get();
        assertEquals(5, TreeNode.sumLeftLeaveNodes(root));
    }

    @Test
    public void testBottomLeftValue() {
        TreeNode root;

        root = new TreeNode(0, null, new TreeNode(-1));
        assertEquals(-1, TreeNode.bottomLeftValue(root));

        root = Wrapper.stringToTreeNode("[2,1,3]").get();
        assertEquals(1, TreeNode.bottomLeftValue(root));

        root = Wrapper.stringToTreeNode("[2,1,3,4,5]").get();
        assertEquals(4, TreeNode.bottomLeftValue(root));

        root = Wrapper.stringToTreeNode("[2,1,3,null,5]").get();
        assertEquals(5, TreeNode.bottomLeftValue(root));

        root = Wrapper.stringToTreeNode("[2,1,3,null,5,6]").get();
        assertEquals(5, TreeNode.bottomLeftValue(root));

        root = Wrapper.stringToTreeNode("[1,2,3,4,null,5,6,null,null,7]").get();
        assertEquals(7, TreeNode.bottomLeftValue(root));
    }

    @Test
    public void testIsBalanced() {
        TreeNode root;

        root = Wrapper.stringToTreeNode("[1,3,3,9,null,9,null,2]").get();
        assertFalse(TreeNode.isBalanced(root));

        root = Wrapper.stringToTreeNode("[3,9,20]").get();
        assertTrue(TreeNode.isBalanced(root));

        root = Wrapper.stringToTreeNode("[3,9,null,2]").get();
        assertFalse(TreeNode.isBalanced(root));

        root = Wrapper.stringToTreeNode("[3,9,2,null,null,3,4]").get();
        assertTrue(TreeNode.isBalanced(root));

        root = Wrapper.stringToTreeNode("[3,9,20,null,null,15,7]").get();
        assertTrue(TreeNode.isBalanced(root));

        root = Wrapper.stringToTreeNode("[1,2,2,3,3,null,null,4,4]").get();
        assertFalse(TreeNode.isBalanced(root));
    }


}

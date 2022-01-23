import com.google.common.collect.Iterators;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Supplier;
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

    public TreeNode findNearestRightNode2(TreeNode root, TreeNode u) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        Queue<TreeNode> little = new LinkedList<>();
        while (!queue.isEmpty()) {

            TreeNode node = queue.poll();
            if (node == u) return queue.peek();
            if (node.left != null) little.offer(node.left);
            if (node.right != null) little.offer(node.right);
            if (queue.isEmpty()) {
                queue = little;
                little = new LinkedList<>();
            }
        }

        return null;
    }

    private TreeNode findNearestRightNodeRec(TreeNode u, Queue<TreeNode> queue) {
        Queue<TreeNode> little = new LinkedList<>();
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == u) return queue.peek();

            if (node.left != null) little.offer(node.left);
            if (node.right != null) little.offer(node.right);
        }
        return findNearestRightNodeRec(u, little);
    }

    public TreeNode findNearestRightNode3(TreeNode root, TreeNode u) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        return findNearestRightNodeRec(u, queue);
    }

    class FindNearest implements Supplier<TreeNode>{
        TreeNode u;
        Queue<TreeNode> queue;
        TreeNode sol;

        public FindNearest(TreeNode root, TreeNode u) {
            this.u = u;
            this.queue = new LinkedList<TreeNode>(Arrays.asList(root));
        }

        @Override
        public TreeNode get() {
            return compute();
        }

        private TreeNode compute() {
            Queue<TreeNode> little = new LinkedList<>();
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if (node == u) return queue.peek();

                if (node.left != null) little.offer(node.left);
                if (node.right != null) little.offer(node.right);
            }
            queue = little;
            return compute();
        }
    }

    public TreeNode findNearestRightNode(TreeNode root, TreeNode u) {
        return new FindNearest(root, u).get();
    }

    @Test
    public void testNearst() {
        TreeNode root, u, sol;
        root = Wrapper.stringToTreeNode("[1,2,3,4]");
        u = root.right;
        assertEquals(null, findNearestRightNode(root, u));

        root = Wrapper.stringToTreeNode("[3,10,1,7,8,9,4,null,null,2,null,null,11,null,null,null,null,5,null,6]");
        u = root.right.right;
        assertEquals(null, findNearestRightNode(root, u));

        root = Wrapper.stringToTreeNode("[1,null,2]");
        u = root.right;
        assertEquals(null, findNearestRightNode(root, u));

        root = Wrapper.stringToTreeNode("[1,2,null,3]");
        u = root.left;
        assertEquals(null, findNearestRightNode(root, u));


        root = Wrapper.stringToTreeNode("[1,2,3,null,4,5,6]");

        u = root;
        assertEquals(null, findNearestRightNode(root, u));

        u = root.left;
        sol = root.right;
        assertEquals(sol, findNearestRightNode(root, u));

        u = root.left.right;
        sol = root.right.left;
        assertEquals(sol, findNearestRightNode(root, u));
    }

    @Test
    public void testSum() {
        assertEquals(22, sumRootToLeaf(Wrapper.stringToTreeNode("[1,0,1,0,1,0,1]")));
        assertEquals(4, sumRootToLeaf(Wrapper.stringToTreeNode("[1,0,0]")));
        assertEquals(5, sumRootToLeaf(Wrapper.stringToTreeNode("[1,1,0]")));
        assertEquals(6, sumRootToLeaf(Wrapper.stringToTreeNode("[1,1,1]")));
        assertEquals(2, sumRootToLeaf(Wrapper.stringToTreeNode("[1,0]")));
        assertEquals(1, sumRootToLeaf(Wrapper.stringToTreeNode("[1]")));
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
        assertEquals("[1]", Wrapper.stringToTreeNode("[1]").toString());
        assertEquals("[1,2,3]", Wrapper.stringToTreeNode("[1,2,3]").toString());
    }

    @Test
    public void testRoundNull() {
        assertEquals("[1,null,3]", Wrapper.stringToTreeNode("[1,null,3]").toString());
    }

    @Test
    public void testEquals() {
        assertTrue(new TreeNode(1).equals(new TreeNode(1)));
        assertFalse(new TreeNode(1).equals(new TreeNode(2)));
        assertFalse(new TreeNode(1, new TreeNode(2), null).equals(new TreeNode(1)));

        TreeNode root = Wrapper.stringToTreeNode("[1,2,3]");
        TreeNode root2 = Wrapper.stringToTreeNode("[1,2,3]");
        assertTrue(root.equals(root2));

        root = Wrapper.stringToTreeNode("[1,2,3]");
        root2 = Wrapper.stringToTreeNode("[1,2,3,4]");
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
        root2 = Wrapper.stringToTreeNode("[1,2]");
        merge = root1.merge(root2);
        assertEquals("[2,2]", merge.toString());

        root1 = new TreeNode(1);
        root2 = Wrapper.stringToTreeNode("[1,null,2]");
        merge = root1.merge(root2);
        assertEquals("[2,null,2]", merge.toString());

        root1 = Wrapper.stringToTreeNode("[1,1,1]");
        root2 = Wrapper.stringToTreeNode("[1,null,2]");
        merge = root1.merge(root2);
        assertEquals("[2,1,3]", merge.toString());

        root1 = Wrapper.stringToTreeNode("[1,1,1]");
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

        root = Wrapper.stringToTreeNode("[1,2,3]");
        List<TreeNode> list = root.getPresentChildren().toList();
        Iterators.elementsEqual(List.of(new TreeNode(2)).iterator(), list.iterator());
    }

    @Test
    public void testInOrder() {
        TreeNode root = Wrapper.stringToTreeNode("[1,2,3,4]");
        Stream<Integer> inOrder = root.inOrderParse().map(TreeNode::getVal);
        assertEquals("[4, 2, 1, 3]", inOrder.toList().toString());
    }

    @Test
    public void testPreOrder() {
        TreeNode root = Wrapper.stringToTreeNode("[2,4]");
        Stream<Integer> inOrder = root.preOrderParse().map(TreeNode::getVal);
        assertEquals("[2, 4]", inOrder.toList().toString());

        root = Wrapper.stringToTreeNode("[1,2,3,4]");
        inOrder = root.preOrderParse().map(TreeNode::getVal);
        assertEquals("[1, 2, 4, 3]", inOrder.toList().toString());
    }

    @Test
    public void testPostOrder() {
        TreeNode root = Wrapper.stringToTreeNode("[1,2,3,4]");
        Stream<Integer> inOrder = root.postOrderParse().map(TreeNode::getVal);
        assertEquals("[4, 2, 3, 1]", inOrder.toList().toString());
    }

    @Test
    public void testMinDifference() {
        TreeNode root = Wrapper.stringToTreeNode("[1,2]");
        assertEquals(1, root.minDifference());

        root = Wrapper.stringToTreeNode("[1,3]");
        assertEquals(2, root.minDifference());
    }

    @Test
    public void testIsSymetric() {
        assertTrue(Wrapper.stringToTreeNode("[1]").isSymetric());
        assertFalse(Wrapper.stringToTreeNode("[1,2]").isSymetric());
        assertFalse(Wrapper.stringToTreeNode("[1,2,3]").isSymetric());
        assertTrue(Wrapper.stringToTreeNode("[1,2,2]").isSymetric());
        assertTrue(Wrapper.stringToTreeNode("[1,2,2,3,4,4,3]").isSymetric());
        assertFalse(Wrapper.stringToTreeNode("[1,2,2,3,4,4,2]").isSymetric());
        assertFalse(Wrapper.stringToTreeNode("[1,2,2,null,3,null,3]").isSymetric());
        assertFalse(Wrapper.stringToTreeNode("[1,2,2,2,null,2]").isSymetric());
    }

    @Test
    public void testLeaveNodes() {
        Stream<TreeNode> treeNodeStream = TreeNode.leaveNodes(Wrapper.stringToTreeNode("[1,2,2,2,null,2]"));
        assertEquals(List.of(2, 2), treeNodeStream.map(TreeNode::getVal).toList());
    }

    @Test
    public void testSumLeaveNodes() {
        TreeNode root = Wrapper.stringToTreeNode("[1,2,2,2,null,2]");
        assertEquals(4, TreeNode.sumLeaveNodes(root));
    }

    @Test
    public void testSumLeftLeaveNodes() {
        TreeNode root = Wrapper.stringToTreeNode("[1,2,2,2,null,2]");
        assertEquals(4, TreeNode.sumLeftLeaveNodes(root));

        root = Wrapper.stringToTreeNode("[1,2,2,2,2]");
        assertEquals(2, TreeNode.sumLeftLeaveNodes(root));

        root = Wrapper.stringToTreeNode("[1,2,2,2,null,null, 2]");
        assertEquals(2, TreeNode.sumLeftLeaveNodes(root));

        root = Wrapper.stringToTreeNode("[3,9,20,null,null,15,7]");
        assertEquals(24, TreeNode.sumLeftLeaveNodes(root));

        root = Wrapper.stringToTreeNode("[0,2,4,1,null,3,-1,5,1,null,6,null,8]");
        assertEquals(5, TreeNode.sumLeftLeaveNodes(root));
    }

    @Test
    public void testBottomLeftValue() {
        TreeNode root;

        root = new TreeNode(0, null, new TreeNode(-1));
        assertEquals(-1, TreeNode.bottomLeftValue(root));

        root = Wrapper.stringToTreeNode("[2,1,3]");
        assertEquals(1, TreeNode.bottomLeftValue(root));

        root = Wrapper.stringToTreeNode("[2,1,3,4,5]");
        assertEquals(4, TreeNode.bottomLeftValue(root));

        root = Wrapper.stringToTreeNode("[2,1,3,null,5]");
        assertEquals(5, TreeNode.bottomLeftValue(root));

        root = Wrapper.stringToTreeNode("[2,1,3,null,5,6]");
        assertEquals(5, TreeNode.bottomLeftValue(root));

        root = Wrapper.stringToTreeNode("[1,2,3,4,null,5,6,null,null,7]");
        assertEquals(7, TreeNode.bottomLeftValue(root));
    }

    @Test
    public void testIsBalanced() {
        TreeNode root;

        root = Wrapper.stringToTreeNode("[1,3,3,9,null,9,null,2]");
        assertFalse(TreeNode.isBalanced(root));

        root = Wrapper.stringToTreeNode("[3,9,20]");
        assertTrue(TreeNode.isBalanced(root));

        root = Wrapper.stringToTreeNode("[3,9,null,2]");
        assertFalse(TreeNode.isBalanced(root));

        root = Wrapper.stringToTreeNode("[3,9,2,null,null,3,4]");
        assertTrue(TreeNode.isBalanced(root));

        root = Wrapper.stringToTreeNode("[3,9,20,null,null,15,7]");
        assertTrue(TreeNode.isBalanced(root));

        root = Wrapper.stringToTreeNode("[1,2,2,3,3,null,null,4,4]");
        assertFalse(TreeNode.isBalanced(root));
    }


}

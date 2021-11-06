import com.google.common.base.Functions;
import com.google.common.collect.Iterators;
import com.google.common.collect.Streams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public List<Optional<Integer>> floorParse() {
        if (this == null) return List.of();

        List<Optional<Integer>> result = new ArrayList();
        Queue<Optional<TreeNode>> remaining = new LinkedList<>(List.of(Optional.of(this)));
        int countNull = 0;

        while (!remaining.isEmpty() && remaining.size() > countNull) {
            Optional<TreeNode> on = remaining.poll();
            result.add(on.map(TreeNode::getVal));

            if (on.isPresent()) {
                TreeNode node = on.get();
                remaining.add(Optional.ofNullable(node.left));
                remaining.add(Optional.ofNullable(node.right));
                if (node.left == null) countNull++;
                if (node.right == null) countNull++;
            } else {
                countNull--;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        if (this == null) return "[]";

        return this.floorParse().stream()
                .map(o -> o.map(Object::toString).orElse("null"))
                .collect(Collectors.joining(",", "[", "]"));
    }

    public Integer getVal() {
        return val;
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public Stream<TreeNode> getPresentChildren() {
        return getAllChildren().filter(Optional::isPresent).map(Optional::get);
    }

    public Stream<Optional<TreeNode>> getAllChildren() {
        return Stream.of(Optional.ofNullable(this.left), Optional.ofNullable(this.right));
    }

    public TreeNode invert() {
        if (this.left == null && this.right == null) return this;

        TreeNode tmp = this.left != null ? this.left.invert() : null;
        this.left = this.right != null ? this.right.invert() : null;
        this.right = tmp;

        return this;
    }

    @Override
    public boolean equals(Object node) {
        if (node == null || this.val != ((TreeNode) node).val) return false;
        return Iterators.elementsEqual(this.getAllChildren().iterator(), ((TreeNode) node).getAllChildren().iterator());
    }

    public TreeNode merge(TreeNode node) {
        if (node == null) return this;
        TreeNode merge = new TreeNode(this.val + node.val);

        if (this.left != null && node.left != null) merge.left = this.left.merge(node.left);
        else merge.left = (this.left == null) ? node.left : this.left;

        if (this.right != null && node.right != null) merge.right = this.left.merge(node.right);
        else merge.right = (this.right == null) ? node.right : this.right;

        return merge;
    }

    public Stream<TreeNode> preOrderParse() {
        return Stream.concat(Stream.of(this), this.getPresentChildren().flatMap(TreeNode::preOrderParse));
    }

    public long countNode() {
        return inOrderParse().count();
    }

    public Stream<TreeNode> inOrderParse() {
        Stream<TreeNode> left = this.left == null ? Stream.empty() : this.left.inOrderParse();
        Stream<TreeNode> right = this.right == null ? Stream.empty() : this.right.inOrderParse();
        Stream<TreeNode> out = Stream.of(left, Stream.of(this), right).flatMap(Functions.identity());
        return out;
    }

    public Stream<TreeNode> postOrderParse() {
        return Stream.concat(this.getPresentChildren().flatMap(TreeNode::postOrderParse), Stream.of(this));
    }

    public int minDifference() {
        List<Integer> nodes = this.preOrderParse().map(TreeNode::getVal).toList();
        return Streams.zip(nodes.stream(), nodes.stream().skip(1), (a, b) -> b - a)
                .min(Integer::compare)
                .get();
    }

    private static boolean areSymetric(TreeNode node1, TreeNode node2) {
        if (node1 == null) return node2 == null;
        if (node2 == null) return false;
        if (node1.val != node2.val) return false;

        return areSymetric(node1.left, node2.right) && areSymetric(node1.right, node2.left);
    }

    public boolean isSymetric() {
        return areSymetric(this.left, this.right);
    }

    public static Stream<TreeNode> leaveNodes(TreeNode node) {
        if (node == null) return Stream.empty();
        if (node.left == null && node.right == null) return Stream.of(node);

        Stream<TreeNode> concat = Stream.concat(leaveNodes(node.left), leaveNodes(node.right));
        return concat;
    }

    public static int sumLeaveNodes(TreeNode node) {
        return leaveNodes(node).collect(Collectors.summingInt(TreeNode::getVal));
    }

    public static Stream<TreeNode> leftLeaveNodes(TreeNode node, boolean fromLeft) {
        if (node == null) return Stream.empty();
        if (node.left == null && node.right == null) return fromLeft ? Stream.of(node) : Stream.empty();
        return Stream.concat(leftLeaveNodes(node.left, true), leftLeaveNodes(node.right, false));
    }

    public static int sumLeftLeaveNodes(TreeNode node) {
        return leftLeaveNodes(node, false).collect(Collectors.summingInt(TreeNode::getVal));
    }

    private static class LeaveNode {
        int val;
        int level;

        public LeaveNode(int val, int level) {
            this.val = val;
            this.level = level;
        }

        public static Comparator<LeaveNode> compare() {
            return new Comparator<LeaveNode>() {
                @Override
                public int compare(LeaveNode o1, LeaveNode o2) {
                    return o1.level == o2.level ? -1 :  o2.level - o1.level;
                }
            };
        }
    }

    private static Stream<LeaveNode> bottomLeftValue(TreeNode node, int level) {
        if (node == null) return Stream.empty();

        if (node.left == null && node.right == null)
            return Stream.of(new LeaveNode(node.val, level));

        Stream<LeaveNode> left = bottomLeftValue(node.left, level + 1);
        Stream<LeaveNode> right = bottomLeftValue(node.right, level + 1);
        return Stream.concat(left, right);
    }

    public static int bottomLeftValue(TreeNode node) {
        return bottomLeftValue(node, 0).min(LeaveNode.compare()).get().val;
    }
}

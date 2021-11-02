import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }

    protected Stream<Optional<Integer>> parseFloor() {
        if (this == null) return Stream.of();

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
        return result.stream();
    }

    public String toString() {
        if (this == null) return "[]";

        return this.parseFloor()
                .map(i -> i.isPresent() ? i.get().toString(): null)
                .collect(Collectors.joining(",", "[", "]"));
    }

    public int getVal() {
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

    public TreeNode invert() {
        if (this == null) return this;
        if (this.left == null && this.right == null) return this;

        TreeNode tmp = this.left != null ? this.left.invert() : null;
        this.left = this.right != null ? this.right.invert() : null;
        this.right = tmp;

        return this;
    }



}
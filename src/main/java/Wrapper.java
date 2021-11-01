import com.google.common.collect.Streams;
import com.google.common.primitives.Ints;
import org.antlr.v4.runtime.tree.Tree;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Wrapper {

    private static Stream<Integer> nodeToSring(TreeNode root) {
        if (root == null) return Stream.of(-1);
        if (root.left == null && root.right == null) return Stream.of(root.val);

        return Stream.of(root.left, root.right)
                .map(node -> nodeToSring(node))
                .reduce(Stream.of(root.val), Stream::concat);
    }

    public static String treeNodeToString(TreeNode root) {
        if (root == null) return "[]";

        return nodeToSring(root)
                .map(i -> i == -1 ? "null" : i.toString())
                .collect(Collectors.joining(",", "[", "]"));
    }

    public static TreeNode arrayToTreeNode(List<Optional<Integer>> input) {
        if (input.size() == 0) {
            return null;
        }

        List<Optional<TreeNode>> inputQueue = input.stream()
                .map(ot -> ot.map(TreeNode::new)).toList();

        TreeNode root = inputQueue.get(0).get();
        Queue<TreeNode> nodeQueue = new LinkedList<>(List.of(root));


        int index = 1;
        while (index < inputQueue.size()) {
            TreeNode node = nodeQueue.remove();

            Stream<Consumer<TreeNode>> list = Stream.of(node::setLeft, node::setRight);
            Stream<Optional<TreeNode>> children = inputQueue.subList(index, Math.min(index + 2, input.size())).stream();

            Streams.forEachPair(list, children, (consumer, child) ->
                    child.ifPresent(consumer.andThen(nodeQueue::add)));
            index += 2;
        }
        return root;
    }

    public static TreeNode stringToTreeNode(String input) {
        if (input == "[]") return null;

        List<Optional<Integer>> nodeList = Pattern.compile(",")
                .splitAsStream(input.substring(1, input.length() - 1))
                .map(s -> Optional.ofNullable(Ints.tryParse(s)))
                .collect(Collectors.toList());
        return arrayToTreeNode(nodeList);
    }

    public static void prettyPrintTree(TreeNode node, String prefix, boolean isLeft) {
        if (node == null) {
            System.out.println("Empty tree");
            return;
        }

        if (node.right != null) {
            prettyPrintTree(node.right, prefix + (isLeft ? "│   " : "    "), false);
        }

        System.out.println(prefix + (isLeft ? "└── " : "┌── ") + node.val);

        if (node.left != null) {
            prettyPrintTree(node.left, prefix + (isLeft ? "    " : "│   "), true);
        }
    }

    public static void prettyPrintTree(TreeNode node) {
        prettyPrintTree(node, "", true);
    }
}

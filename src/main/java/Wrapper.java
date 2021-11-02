import com.google.common.collect.Streams;
import com.google.common.primitives.Ints;

import java.sql.Array;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Wrapper {

    public static Stream<Optional<Integer>> parseFloor(TreeNode root) {
        if (root == null) return Stream.of();

        List<Optional<Integer>> result = new ArrayList();
        Queue<TreeNode> remaining = new LinkedList<>(List.of(root));
        int countNull = 0;

        while (!remaining.isEmpty() && remaining.size() > countNull) {
            TreeNode node = remaining.poll();
            if (node != null) {
                result.add(Optional.of(node.val));
                remaining.add(node.left == null ? null : node.left);
                remaining.add(node.right == null ? null : node.right);
                if (node.left == null) countNull++;
                if (node.right == null) countNull++;
            } else {
                countNull--;
                result.add(Optional.empty());
            }
        }

//        while (result.get(result.size() -1).isEmpty()) {
//            result.remove(result.size()-1);
//        }
        return result.stream();
    }

    public static String treeNodeToString(TreeNode root) {
        if (root == null) return "[]";

        return parseFloor(root)
                .map(i -> i.isPresent() ? i.get().toString(): null)
                .collect(Collectors.joining(",", "[", "]"));
    }

    public static Optional<TreeNode> arrayToTreeNode(List<Optional<Integer>> input) {
        if (input.size() == 0) {
            return Optional.empty();
        }

        LinkedList<Optional<TreeNode>> inputQueue = input.stream()
                .map(ot -> ot.map(TreeNode::new))
                .collect(Collectors.toCollection(LinkedList::new));

        TreeNode root = inputQueue.poll().get();
        Queue<TreeNode> nodeQueue = new LinkedList<>(List.of(root));

        while (!inputQueue.isEmpty()) {
            TreeNode parent = nodeQueue.remove();
            Stream<Consumer<TreeNode>> setters = Stream.of(parent::setLeft, parent::setRight);

            Streams.forEachPair(
                    setters, inputQueue.stream(),
                    (consumer, child) -> child.ifPresent(consumer.andThen(nodeQueue::add)));

            Stream.generate(inputQueue::poll).limit(2).count();
        }
        return Optional.of(root);
    }

    public static Optional<TreeNode> stringToTreeNode(String input) {
        if (input.equals("[]")) return Optional.empty();

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

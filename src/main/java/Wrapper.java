import com.google.common.collect.Streams;
import com.google.common.primitives.Ints;

import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Wrapper {

    public static TreeNode arrayToTreeNode(List<Optional<Integer>> input) {
        LinkedList<Optional<TreeNode>> inputQueue = input.stream()
                .map(ot -> ot.map(TreeNode::new))
                .collect(Collectors.toCollection(LinkedList::new));

        TreeNode root = inputQueue.poll().get();
        Queue<TreeNode> nodeQueue = new LinkedList<>(List.of(root));

        while (!inputQueue.isEmpty()) {
            TreeNode parent = nodeQueue.remove();
            Stream<Optional<TreeNode>> nodes = Stream.generate(inputQueue::poll)
                    .limit(2)
                    .filter(Objects::nonNull);
            Stream<Consumer<TreeNode>> setters = Stream.of(parent::setLeft, parent::setRight);
            Streams.forEachPair(setters.map(c -> c.andThen(nodeQueue::add)), nodes,
                    (consumer, child) -> child.ifPresent(consumer));
        }

        return root;
    }

    public static TreeNode stringToTreeNode(String input) {
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

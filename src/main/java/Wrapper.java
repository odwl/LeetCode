import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import org.antlr.v4.runtime.tree.Tree;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import java.lang.Math;

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

    public static TreeNode arrayToTreeNode(List<Integer> input) {
        if (input.size() == 0) {
            return null;
        }

        List<Integer> parts = input;
        int item = parts.get(0);
        TreeNode root = new TreeNode(item);
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);

        int index = 1;
        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.remove();

            List<Entry> list = new ArrayList<>();
            if (index < parts.size()) {
                list.add(new Entry(node::getLeft, node::setLeft, new TreeNode(parts.get(index++))));
            }
            if (index < parts.size()) {
                list.add(new Entry(node::getRight, node::setRight, new TreeNode(parts.get(index++))));
            }

            for (Entry entry: list) {
                entry.operation.accept(entry.toAdd);
                nodeQueue.add(entry.getOperation.get());
            }

        }
        return root;
    }

    public static class Entry {

        public Entry(Supplier<TreeNode> getOperation, Consumer<TreeNode> operation, TreeNode toAdd) {
            this.getOperation = getOperation;
            this.operation = operation;
            this.toAdd = toAdd;
        }

        Supplier<TreeNode> getOperation;
        Consumer<TreeNode> operation;
        TreeNode toAdd;

    }

    public static TreeNode stringToTreeNode(String input) {
        if (input == "[]") return null;

        List<TreeNode> nodeList = Pattern.compile(",")
                .splitAsStream(input.substring(1, input.length() - 1))
                .map(s -> s.equals("null") ? null : new TreeNode(Integer.parseInt(s)))
                .collect(Collectors.toList());

        List<Stream<TreeNode>> floors = IntStream.range(0, nodeList.size())
                .boxed()
                .collect(Collectors.groupingBy(i -> Math.ceil(Math.log(i + 2) / Math.log(2))))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .map(l -> l.stream().map(nodeList::get))
                .collect(Collectors.toList());

        TreeNode first = floors.get(0).findFirst().get();
        floors.remove(0);

        Stream<Consumer<TreeNode>> funct = Stream.of(first::setLeft, first::setRight);
        for (Stream<TreeNode> toAdd : floors) {
            List<Consumer<TreeNode>> newQueue = new ArrayList<>();
            Streams.forEachPair(funct, toAdd, (f1,n) -> {
                if (n != null) {
                    f1.accept(n);
                    Collections.addAll(newQueue, n::setLeft, n::setRight);
                }
            });

            funct = newQueue.stream();
        }
        return first;
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

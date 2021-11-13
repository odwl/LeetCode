import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static ListNode fromArray(int[] input) {
        ListNode node = new ListNode();
        ListNode root = node;
        for (int el: input) {
            node.next = new ListNode(el);
            node = node.next;
        }
        return root.next;
    }

    @Override
    public String toString() {
        List<Integer> list = new ArrayList<>(this.val);
        ListNode node = this;
        while (node != null) {
            list.add(node.val);
            node = node.next;
        }

        return list.stream()
                .map(i -> i.toString())
                .collect(
                Collectors.joining(",", "[", "]"));
    }
}
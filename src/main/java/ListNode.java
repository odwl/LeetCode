import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        for (int el : input) {
            node.next = new ListNode(el);
            node = node.next;
        }
        return root.next;
    }

    @Override
    public String toString() {
        List<String> list = new ArrayList<>(this.val);
        ListNode node = this;
        while (node != null) {
            list.add(String.valueOf(node.val));
            node = node.next;
        }
        return list.stream()
                .collect(Collectors.joining(",", "[", "]"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListNode listNode = (ListNode) o;
        return val == listNode.val && Objects.equals(next, listNode.next);
    }

    public static ListNode removeElements(ListNode head, int val) {
        if (head == null) return head;
        ListNode remaining = removeElements(head.next, val);
        if (head.val == val) return remaining;
        head.next = remaining;
        return head;
    }

    public static ListNode oddEven(ListNode head) {
        ListNode odd = new ListNode(), even = new ListNode();
        ListNode even_head = even, it = head;

        while (it != null) {
            odd.next = odd = it;
            it = it.next;

            if (it != null) {
                even.next = even = it;
                it = it.next;
            }
        }

        even.next = null;
        odd.next = even_head.next;

        return head;
    }
}

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotEquals;


public class ListNodeTest {

    public ListNode detectCycle(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        ListNode node = head;
        while (node != null) {
            if (set.contains(node)) return node;
            set.add(node);
            node = node.next;
        }
        return null;
    }

    @Test
    public void testCycle() {
        ListNode node;

        node = ListNode.fromArray(new int[]{1, 2});
        node.next.next = node;
        assertEquals(node, detectCycle(node));

        node = ListNode.fromArray(new int[]{1, 2});
        assertEquals(null, detectCycle(node));

        node = ListNode.fromArray(new int[]{1});
        assertEquals(null, detectCycle(node));

        node = ListNode.fromArray(new int[]{1});
        node.next = node;
        assertEquals(node, detectCycle(node));

        node = ListNode.fromArray(new int[]{1, 2});
        node.next = node;
        assertEquals(node, detectCycle(node));


    }

    @Test
    public void testFromString() {
        ListNode node;

        node = ListNode.fromArray(new int[]{1});
        assertEquals(new ListNode(1), node);

        node = ListNode.fromArray(new int[]{1, 2});
        assertEquals(new ListNode(1, new ListNode(2)), node);
    }

    @Test
    public void testToString() {
        ListNode node;

        node = ListNode.fromArray(new int[]{1});
        assertEquals("[1]", node.toString());

        node = ListNode.fromArray(new int[]{1, 2});
        assertEquals("[1,2]", node.toString());
    }

    @Test
    public void testEquals() {
        assertEquals(new ListNode(1), new ListNode(1));
        assertNotEquals(new ListNode(1), new ListNode(2));
        assertNotEquals(new ListNode(1), ListNode.fromArray(new int[]{1, 2}));
        assertEquals(
                ListNode.fromArray(new int[]{1, 2}),
                ListNode.fromArray(new int[]{1, 2}));
    }

    @Test
    public void testRemoveElements() {
        assertNull(ListNode.removeElements(null, 1));

        ListNode node;
        node = ListNode.removeElements(new ListNode(1), 2);
        assertEquals(new ListNode(1), node);

        node = ListNode.removeElements(new ListNode(1), 1);
        assertNull(node);

        node = ListNode.removeElements(ListNode.fromArray(new int[]{1, 2}), 2);
        assertEquals(new ListNode(1), node);

        node = ListNode.removeElements(ListNode.fromArray(new int[]{1, 2}), 1);
        assertEquals(new ListNode(2), node);

        node = ListNode.removeElements(ListNode.fromArray(new int[]{1, 2}), 3);
        assertEquals(ListNode.fromArray(new int[]{1, 2}), node);

        node = ListNode.removeElements(ListNode.fromArray(new int[]{1, 2, 3, 4, 5, 6}), 6);
        assertEquals(ListNode.fromArray(new int[]{1, 2, 3, 4, 5}), node);

        node = ListNode.removeElements(ListNode.fromArray(new int[]{1, 2, 3, 4, 5, 6}), 5);
        assertEquals(ListNode.fromArray(new int[]{1, 2, 3, 4, 6}), node);

        node = ListNode.removeElements(ListNode.fromArray(new int[]{1, 2, 3, 4, 5, 6}), 1);
        assertEquals(ListNode.fromArray(new int[]{2, 3, 4, 5, 6}), node);

        node = ListNode.removeElements(ListNode.fromArray(new int[]{1, 2, 3, 4, 5, 6}), 7);
        assertEquals(ListNode.fromArray(new int[]{1, 2, 3, 4, 5, 6}), node);
    }

    @Test
    public void testOddEven() {
        ListNode node;

        assertEquals(null, ListNode.oddEven(null));

        node = ListNode.fromArray(new int[]{1});
        assertEquals("[1]", ListNode.oddEven(node).toString());

        node = ListNode.fromArray(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        assertEquals("[1,3,5,7,2,4,6,8]", ListNode.oddEven(node).toString());

        node = ListNode.fromArray(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        assertEquals("[1,3,5,7,9,2,4,6,8]", ListNode.oddEven(node).toString());

        node = ListNode.fromArray(new int[]{1, 2});
        assertEquals("[1,2]", ListNode.oddEven(node).toString());

        node = ListNode.fromArray(new int[]{1, 2, 3});
        assertEquals("[1,3,2]", ListNode.oddEven(node).toString());

        node = ListNode.fromArray(new int[]{1, 2, 3, 4});
        assertEquals("[1,3,2,4]", ListNode.oddEven(node).toString());
    }
}

import org.junit.Test;
import static org.junit.Assert.*;

public class ListNodeTest {

    @Test
    public void testFromString() {
        ListNode node;

        node = ListNode.fromArray(new int[] {1});
        assertEquals(new ListNode(1), node);

        node = ListNode.fromArray(new int[] {1,2});
        assertEquals(new ListNode(1, new ListNode(2)), node);
    }

    @Test
    public void testToString() {
        ListNode node;

        node = ListNode.fromArray(new int[] {1});
        assertEquals("[1]", node.toString());

        node = ListNode.fromArray(new int[] {1,2});
        assertEquals("[1,2]", node.toString());
    }

    @Test
    public void testEquals() {
        assertEquals(new ListNode(1), new ListNode(1));
        assertNotEquals(new ListNode(1), new ListNode(2));
        assertNotEquals(new ListNode(1), ListNode.fromArray(new int[] {1,2}));
        assertEquals(
                ListNode.fromArray(new int[] {1,2}),
                ListNode.fromArray(new int[] {1,2}));
    }

    @Test
    public void testRemoveElements() {
        assertNull(ListNode.removeElements(null,1));

        ListNode node;
        node = ListNode.removeElements(new ListNode(1), 2);
        assertEquals(new ListNode(1), node);

        node = ListNode.removeElements(new ListNode(1), 1);
        assertNull(node);

        node = ListNode.removeElements(ListNode.fromArray(new int[] {1,2}), 2);
        assertEquals(new ListNode(1), node);

        node = ListNode.removeElements(ListNode.fromArray(new int[] {1,2}), 1);
        assertEquals(new ListNode(2), node);

        node = ListNode.removeElements(ListNode.fromArray(new int[] {1,2}), 3);
        assertEquals(ListNode.fromArray(new int[] {1,2}), node);

        node = ListNode.removeElements(ListNode.fromArray(new int[] {1,2,3,4,5,6}), 6);
        assertEquals(ListNode.fromArray(new int[] {1,2,3,4,5}), node);

        node = ListNode.removeElements(ListNode.fromArray(new int[] {1,2,3,4,5,6}), 5);
        assertEquals(ListNode.fromArray(new int[] {1,2,3,4,6}), node);

        node = ListNode.removeElements(ListNode.fromArray(new int[] {1,2,3,4,5,6}), 1);
        assertEquals(ListNode.fromArray(new int[] {2,3,4,5,6}), node);

        node = ListNode.removeElements(ListNode.fromArray(new int[] {1,2,3,4,5,6}), 7);
        assertEquals(ListNode.fromArray(new int[] {1,2,3,4,5,6}), node);
    }
}

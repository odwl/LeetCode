import org.junit.Test;
import static org.junit.Assert.*;

public class ListNodeTest {

    @Test
    public void testFromString() {
        ListNode node;

        node = ListNode.fromArray(new int[] {1});
        assertEquals(1, node.val);
        assertEquals(null, node.next);

        node = ListNode.fromArray(new int[] {1,2});
        assertEquals(1, node.val);
        assertEquals(2, node.next.val);
        assertEquals(null , node.next.next);
    }

    @Test
    public void testToString() {
        ListNode node;

        node = ListNode.fromArray(new int[] {1});
        assertEquals("[1]", node.toString());

        node = ListNode.fromArray(new int[] {1,2});
        assertEquals("[1,2]", node.toString());
    }
}

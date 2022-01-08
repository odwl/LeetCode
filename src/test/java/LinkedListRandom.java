import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;


public class LinkedListRandom {

    class Solution {

        List list;
        Random random = new Random();

        public Solution(ListNode head) {
            list = new ArrayList();
            ListNode it = head;
            while (it != null) {
                list.add(it.val);
                it = it.next;
            }
        }

        public int getRandom() {
            return (int) list.get(random.nextInt(list.size()));
        }
    }

    public boolean whatDoesIsDo(int x) {
        System.out.println("x: " + (x & (x-1)));
        return (x & (x - 1)) == 0;
    }

    @Test
    public void testRandom() {

        assertFalse(whatDoesIsDo(-2));
        assertFalse(whatDoesIsDo(-1));
        assertTrue(whatDoesIsDo(0));
        assertTrue(whatDoesIsDo(1));
        assertTrue(whatDoesIsDo(2));
        assertFalse(whatDoesIsDo(3));
        assertTrue(whatDoesIsDo(4));
        assertTrue(whatDoesIsDo(8));
        assertFalse(whatDoesIsDo(12));

        assertTrue(whatDoesIsDo(16));

        ListNode node = ListNode.fromArray(new int[]{1,2,3});
        Solution obj = new Solution(node);
        int param_1 = obj.getRandom();
        System.out.println(param_1);
    }
}

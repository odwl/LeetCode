import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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


    @Test
    public void testRandom() {
        ListNode node = ListNode.fromArray(new int[]{1,2,3});
        Solution obj = new Solution(node);
        int param_1 = obj.getRandom();
        System.out.println(param_1);
    }
}

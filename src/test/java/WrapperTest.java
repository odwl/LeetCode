import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class WrapperTest {

    @Test
    public void testToTreeOne() {
        TreeNode root = Wrapper.stringToTreeNode("[1]").get();
        assertEquals(1, root.val);
        assertEquals(null, root.left);
        assertEquals(null, root.right);
    }

    @Test
    public void testToTreeThree() {
        TreeNode root = Wrapper.stringToTreeNode("[1,2,3]").get();
        assertEquals(1, root.val);
        assertEquals(2, root.left.val);
        assertEquals(3, root.right.val);
    }

    @Test
    public void testToTreeThreeNull() {
        TreeNode root = Wrapper.stringToTreeNode("[1,null,3]").get();
        assertEquals(1, root.val);
        assertEquals(null, root.left);
        assertEquals(3, root.right.val);
    }

    @Test
    public void testToTreeFour() {
        TreeNode root = Wrapper.stringToTreeNode("[4,2,1,3]").get();
        assertEquals(4, root.val);
        assertEquals(2, root.left.val);
        assertEquals(1, root.right.val);
        assertEquals(3, root.left.left.val);
    }

    @Test
    public void testToTreeFourInt() {
        TreeNode root = Wrapper.arrayToTreeNode(Stream.of(4, 2, 1, 3).map(Optional::of).toList()).get();
        assertEquals(4, root.val);
        assertEquals(2, root.left.val);
        assertEquals(1, root.right.val);
        assertEquals(3, root.left.left.val);

        String back = root.toString();
        assertEquals("[4,2,1,3]", back);
    }


    public static int[] countAlphabet(String s) {
        int[] arr = new int[26];
        Arrays.fill(arr, 0);
        for (char ch : s.toUpperCase().toCharArray()) {
            int ord = (int) ch - 65;
            if (ord >= 0 && ord <= 26) arr[ord]++;
        }
        return arr;
    }

    public static boolean isAnagram(String s1, String s2) {
        int[] cA1 = countAlphabet(s1);
        int[] cA2 = countAlphabet(s2);
        return Arrays.equals(cA1, cA2);
    }

    @Test
    public void testAscii() {
        assertEquals(3, countAlphabet("AAA")[0]);
        assertEquals(26, countAlphabet("").length);

        String s = new String();
        for (int i = 0; i < 26; i++) {
            s += (char) ('A' + i);
            s += (char) ('a' + i);
        }
        assertEquals(2, countAlphabet(s)[0]);

        assertEquals(0, countAlphabet("!")[0]);
        assertArrayEquals(countAlphabet("Listen"), countAlphabet(("Silent")));
    }

    @Test
    public void testAnagram() {
        assertTrue(isAnagram("Listen", "Silent"));
        assertFalse(isAnagram("Listen", "Silence"));
        assertTrue(isAnagram("Madam Curie", "Radium came"));
        assertTrue(isAnagram("Madam Curie", "aRidmuc mae"));
        assertFalse(isAnagram("Madam Curie", "aRidmuc mad"));
    }

    @Test
    public void testPattern() {
        String input = "[1,2,4]";
        List<String> sol = Pattern.compile(",")
                .splitAsStream(input.substring(1, input.length() - 1))
                .collect(Collectors.toList());
        assertEquals(List.of("1", "2", "4"), sol);

        int b = (int) 'a';
        int a = 300;
        byte c = (byte) a;
        assertEquals(300 % 128, c);
    }
}

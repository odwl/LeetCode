import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class TreeNodeTest {

    @Test
    public void testParseFloor() {
        TreeNode root;

        root = Wrapper.arrayToTreeNode(Stream.of(2).map(Optional::of).toList()).get();
        assertEquals(List.of(2), root.parseFloor().map(Optional::get).toList());

        root = Wrapper.arrayToTreeNode(Stream.of(2, 3).map(Optional::of).toList()).get();
        assertEquals(List.of(2, 3), root.parseFloor().map(Optional::get).toList());

        root = Wrapper.arrayToTreeNode(Stream.of(4, 2, 1, 3).map(Optional::of).toList()).get();
        assertEquals(List.of(4, 2, 1, 3), root.parseFloor().map(Optional::get).toList());

    }

    @Test
    public void testToStringOne() {
        TreeNode root = new TreeNode(1);
        assertEquals("[1]", root.toString());
    }

    @Test
    public void testRound() {
        assertEquals("[1]", Wrapper.stringToTreeNode("[1]").get().toString());
        assertEquals("[1,2,3]", Wrapper.stringToTreeNode("[1,2,3]").get().toString());
    }

    @Test
    public void testRoundNull() {
        assertEquals("[1,null,3]", Wrapper.stringToTreeNode("[1,null,3]").get().toString());
    }
}

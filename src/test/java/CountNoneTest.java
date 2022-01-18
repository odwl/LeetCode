import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;


public class CountNoneTest {

    @Test()
    public void testCountNode() {

        TreeNode root = Wrapper.stringToTreeNode("[1,2,3,4,5,6]");
        assertEquals(6, root.countNode());

        root = Wrapper.stringToTreeNode("[1]");
        assertEquals(1, root.countNode());
    }

    private static int size;
    private static TreeNode root;

    @BeforeEach
    public void before() {
        size = 1000;
        Random random = new Random();
        int[] data = random.ints(size).toArray();
        root = Wrapper.arrayToTreeNode(IntStream.of(data).mapToObj(Optional::of).toList());
    }

    @Test()
    public void testBig() {
        assertEquals(size, root.countNode());
    }
}

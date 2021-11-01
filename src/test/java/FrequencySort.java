import org.junit.Test;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;


public class FrequencySort {

    public String sortByFreq(String txt) {
        return txt.codePoints()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Character, Long>comparingByValue().reversed())
                .map(entry -> new String(
                        new char[entry.getValue().intValue()]).replace('\0', entry.getKey()))
                .collect(Collectors.joining(""));
    }

    @Test
    public void testSimple() {
        assertEquals("aaabbc", sortByFreq("aabbca"));
        assertEquals("eert", sortByFreq("tree"));
        assertEquals("aaaccc", sortByFreq("cccaaa"));
    }
}

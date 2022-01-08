import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PalindromPartitionTest {


    private boolean isPalindrom(String str) {
        StringBuilder sb=new StringBuilder(str);
        sb.reverse();
        return str.equals(sb.toString());
    }

    public List<String> palindroms(String s) {
        if (s.isEmpty()) return new ArrayList<>();
        String first = s.substring(0,1);

        List<String> list = IntStream.range(0, s.length())
                .filter(i -> s.charAt(i) == s.charAt(0))
                .mapToObj(i -> s.substring(0,i+1))
                .filter(this::isPalindrom)
                .toList();

        return list;

    }



    public List<List<String>> partition(String s) {
        if (s.isEmpty()) return new ArrayList<>();

        List<List<String>> sol = new ArrayList<>();
        if (s.length() == 1) sol.add(List.of(s.substring(0,1)));

        for (String str: palindroms(s)) {
            for (List<String> l: partition(s.substring(1, s.length()))) {
                l.add(0, str);
                sol.add(l);
            }
        }

        return sol;
    }

    @Test
    public void testPalin() {
        assertEquals("[]", palindroms("").toString());
        assertEquals("[a]", palindroms("a").toString());
        assertEquals("[a]", palindroms("ab").toString());
        assertEquals("[a, aa]", palindroms(("aa")).toString());
        assertEquals("[a, aa]", palindroms(("aab")).toString());
        assertEquals("[a, aba]", palindroms(("aba")).toString());
        assertEquals("[a]", palindroms(("abca")).toString());

    }

    @Test
    public void testPartition() {
        assertEquals("[]", partition("").toString());
        assertEquals("[[a]]", partition("a").toString());
//        assertEquals("[[a, a, b], [aa, b]]", partition("aab").toString());
    }
}



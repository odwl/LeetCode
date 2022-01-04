import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;


public class TownJudgeTest {

    public int findJudge2(int n, int[][] trust) {
        if (trust.length < n - 1) return -1;
        Set<Integer> distincts = Arrays.stream(trust).map(arr -> arr[0]).distinct().collect(Collectors.toSet());
        List<Integer> candidates = IntStream.rangeClosed(1, n).filter(p -> !distincts.contains(p)).boxed().collect(Collectors.toList());

        if ( candidates.size() != 1) return -1;
        int cand = candidates.get(0);

        int count =  (int) Arrays.stream(trust).map(arr -> arr[1]).filter(p -> p == cand).count();

        return count == n - 1 ? cand : -1;
    }

    public int findJudge3(int n, int[][] trust) {
        if (trust.length < n - 1) return -1;
        if (n == 1) return trust.length == 0 ? 1 : -1;

        Set<Integer> likers = new HashSet<>();
        Map<Integer, Integer> isLiked = new HashMap<>();
        for (int[] pair: trust) {
            isLiked.compute(pair[1], (k,v) -> v == null ? 1 : v+1);
            likers.add(pair[0]);
        }

        List<Map.Entry<Integer, Integer>> candidates = isLiked.entrySet().stream()
                .filter(entry -> entry.getValue() == n - 1).collect(Collectors.toList());

        if ( candidates.size() != 1) return -1;
        int cand = candidates.get(0).getKey();
        return likers.contains(cand) ? -1 : cand;
    }

    public int findJudge(int N, int[][] trust) {
        if (trust.length < N - 1) return -1;

        int[] indegrees = new int[N + 1];
        int[] outdegrees = new int[N + 1];

        for (int[] relation : trust) {
            outdegrees[relation[0]]++;
            indegrees[relation[1]]++;
        }

        return IntStream.range(1, N+1)
                .filter(i -> indegrees[i] == N - 1)
                .filter(i -> outdegrees[i] == 0)
                .findFirst().orElse(-1);
    }

    @Test
    public void testJudge() {

        assertEquals(2, findJudge(3, new int[][]{{1,2}, {3,2}}));

        assertEquals(-1, findJudge(3, new int[][]{{1,2}, {3,1}}));


        assertEquals(1, findJudge(1, new int[][] {}));

        assertEquals(-1, findJudge(10, new int[][] {}));
        assertEquals(-1, findJudge(3, new int[][]{{1,2}}));

        assertEquals(-1, findJudge(2, new int[][]{{1,2}, {2,1}}));
        assertEquals(-1, findJudge(5, new int[][]{{1,2}, {3,2}, {2,1}}));







        assertEquals(2, findJudge(2, new int[][]{{1,2}}));
        assertEquals(1, findJudge(2, new int[][]{{2,1}}));
        assertEquals(-1, findJudge(3, new int[][]{{1,2}, {3,2}, {2,1}}));
        assertEquals(-1, findJudge(3, new int[][]{{1,3}, {2,3}, {3,1}}));
        assertEquals(3, findJudge(4, new int[][]{{1,3}, {1,4}, {2,3}, {2,4}, {4,3}}));

        assertEquals(3, findJudge(4, new int[][]{{1,2}, {1,3}, {2,1}, {2,3}, {1,4}, {4,3}, {4,1}}));
    }
}

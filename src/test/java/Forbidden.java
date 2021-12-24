import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class Forbidden {

    public static class MinJump implements Supplier<Integer> {
        Map<Integer, Integer> dp;
        Set<Integer> forbid;
        int forward, backward, end;
        PriorityQueue<Integer> candidates;

        public MinJump(int[] forbidden, int forward, int backward, int end) {
            dp = new HashMap<>();
            forbid = Arrays.stream(forbidden).boxed().collect(Collectors.toUnmodifiableSet());
            this.forward = forward;
            this.backward = backward;
            this.end = end;
            this.candidates = new PriorityQueue<>((o1, o2) -> dp.get(o1) - dp.get(o2));
        }

        @Override
        public Integer get() {
            if (end < forward && forward > backward && end % (forward - backward) !=0) return -1;

            forbid.forEach(el -> dp.put(el, -1));
            candidates.add(0);
            dp.put(0, 0);
            int best = Integer.MAX_VALUE;

            while (candidates.size() > 0) {
                int cand = candidates.remove();

                if (dp.get(cand) > best) return best;
                if (cand == end) best = dp.get(cand);

                int cand1 = cand + forward;
                if (!dp.containsKey(cand1) || dp.get(cand1) > dp.get(cand) + 1) {
                    dp.put(cand1, dp.get(cand) + 1);
                    if (cand1 <= 4000) candidates.add(cand1);
                }

                if (dp.get(cand1) >= dp.get(cand) + 1) {
                    int previous = cand1 - backward;
                    if (cand1 > backward && (!dp.containsKey(previous) || dp.get(previous) > dp.get(cand) + 2)) {
                        dp.put(previous, dp.get(cand) + 2);
                        candidates.add(previous);
                    }
                }
            }

            return dp.containsKey(end) ? dp.get(end) : -1;
        }
    }

    public static int minimumJumps(int[] forbidden, int forward, int backward, int end) {
        return new MinJump(forbidden, forward, backward, end).get();
    }


    @Test
    public void testJump() {
        assertEquals(3, minimumJumps(new int[]{}, 3, 2, 4));
        assertEquals(2, minimumJumps(new int[]{}, 3, 2, 1));
        assertEquals(10, minimumJumps(new int[]{}, 3, 7, 10));

        assertEquals(0, (int) new MinJump(new int[]{}, 3, 15, 0).get());
        assertEquals(1, (int) new MinJump(new int[]{}, 3, 15, 3).get());
        assertEquals(2, (int) new MinJump(new int[]{}, 3, 15, 6).get());
        assertEquals(3, (int) new MinJump(new int[]{}, 3, 15, 9).get());
        assertEquals(4, minimumJumps(new int[]{}, 3, 2, 2));
        assertEquals(3, minimumJumps(new int[]{}, 3, 15, 9));

        assertEquals(2, minimumJumps(new int[]{1, 6, 2, 14, 5, 17, 4}, 16, 9, 7));

        assertEquals(1036, minimumJumps(new int[]{1}, 560, 573, 64));
        assertEquals(67, minimumJumps(new int[]{1}, 160, 173, 64));
        assertEquals(1, minimumJumps(new int[]{}, 3, 15, 3));
        assertEquals(2, minimumJumps(new int[]{}, 3, 15, 6));
        assertEquals(3, minimumJumps(new int[]{}, 3, 15, 9));
        assertEquals(3, minimumJumps(new int[]{14, 4, 18, 1, 15}, 3, 15, 9));
        assertEquals(1, (int) new MinJump(new int[]{}, 3, 15, 3).get());
    }

    @Test
    public void testJumpNegative() {
        assertEquals(-1, minimumJumps(new int[]{}, 15, 13, 11));
        assertEquals(-1, minimumJumps(new int[]{}, 15, 13, 1));
        assertEquals(-1, minimumJumps(new int[]{}, 15, 13, 3));
        assertEquals(-1, minimumJumps(new int[]{}, 15, 13, 5));
        assertEquals(-1, minimumJumps(new int[]{}, 15, 13, 13));
        assertEquals(-1, minimumJumps(new int[]{8, 3, 16, 6, 12, 20}, 15, 13, 11));
    }

    @Test
    public void testJumpNegative2() {
        assertEquals(-1, minimumJumps(new int[]{6}, 3, 15, 9));
        assertEquals(-1, minimumJumps(new int[]{3}, 3, 15, 6));
        assertEquals(-1, minimumJumps(new int[]{}, 3, 15, 2));
    }

    @Test
    public void testJump2() {

        assertEquals(199, (int) new MinJump(new int[]{732,316,894,1598,1417,480,1223,396,1338,62,717,1938,446,1474,620,1089,811,342,1863,491,93,1144,1070,1373,1473,392,827,974,832,1141,1761,1022,828,1377,495,1603,1553,1169,89,1518,1666,293,1056,18,1094,1128,608,1702,1919,856,311,1777,490,345,367,1203,1501,1912,672,215,1933,1577,1436,305,210,1140,300,400,1358,987,559,1290,1294,90,723,735,1517,1152,870,666,871,1303,584,1488,1775,1600,1300,1901,599,1525,1024,1679,1656,1604,651,1758,1876,1498,317,1448,671,1865,1974,1945,1992,1769,1329,377,1884,1085,602,1385,328,1696,1079,1565,1814,422,1159,753,1189,241,363,147,1107,1817,1116,1888,1029,132,1910,435,604,1231,1908,60,472,957,23,706,1691,705,667,1798,1568,875,1943,1259,64,1305,1870,1124,1856,1830,1170,91,1249,956,936,1733,557,728,1834,831,1768,893,1481,1343,308,686,1882,37,606,1583,895,645,471,565,502,1283,1868,754,1420,202,1532,1257,1408,1570,1978,411,1449,1971,88,878,1643,877,1529,454,185,1098,1736,1299,1914,1255,923,598,1619,1333,1709,890,934,1225,851,1403,939,1892,1130,1984,1717,1590,214,1406,652,943,1909,332,1849,98,858,524,1051,1998,874,1118,1558,834,842,430,13,592,149,1991,1397,1745,1111,790,1885,521,712,1220,340,1400,309,144,1139,719,966,1756,928,968,897,795,1964,582,952,659,1510,722,891,1776,1925,1344,253,32,1142,1781,603,1815,1662,477,1905,1672,1744,727,509,1009,456,979,1208,362,7,143,741,236,1924,312,1579,264,729,390,1281,1386,1655,1633,701,405,1534,1693,1699,744,516,1444,1206,859,1390,865,402,773,967,1150,1948,1454,47,1953,195,558,704,1599,1097,365,1401,1260,1019,99,1787,983,747,1930,1190,1087,1005,853,1035,1660,425,784,146,1412,1160,1241,1646,1155,1212,1667,689,1719,1011,1027,142,295,1698,1839,1432,531,918,122,1359,1926,1617,414,1988,562,1595,1796,1747,1068,1748,56,805,1853,1278,628,1739,1522,1127,421,572,166,1143,270,1718,1659,801,991,1960,2000,591,181,188,1243,1721,1171,199,503,1627,715,287,193,1378,1462,168,517,1842,803,478,1706,867,1907,1638,1349,141,201,551,1864,607,1648,800,1848,1711,15,512,573,1751,84,1315,1350,283,785,650,1419,1479,302,1201,647,913,1805,161,1804,1108,1,398},
                19, 14, 1999).get());
        assertEquals(120, (int) new MinJump(new int[]{    1401,832,1344,173,1529,1905,1732,277,1490,650,1577,1886,185,1728,1827,1924,1723,1034,1839,1722,1673,1198,1667,538,911,1221,1201,1313,251,752,40,1378,1515,1789,1580,1422,907,1536,294,1677,1807,1419,1893,654,1176,812,1094,1942,876,777,1850,1382,760,347,112,1510,1278,1607,1491,429,1902,1891,647,1560,1569,196,539,836,290,1348,479,90,1922,111,1411,1286,1362,36,293,1349,667,430,96,1038,793,1339,792,1512,822,269,1535,1052,233,1835,1603,577,936,1684,1402,1739,865,1664,295,977,1265,535,1803,713,1298,1537,135,1370,748,448,254,1798,66,1915,439,883,1606,796
        }, 19, 18, 1540).get());

    }

    @Test
    public void testJump3() {
        assertEquals(121, minimumJumps(new int[]{162,118,178,152,167,100,40,74,199,186,26,73,200,127,30,124,193,84,184,36,103,149,153,9,54,154,133,95,45,198,79,157,64,122,59,71,48,177,82,35,14,176,16,108,111,6,168,31,134,164,136,72,98}, 29, 98, 80));
        assertEquals(121, minimumJumps(new int[]{}, 29, 98, 80));
        assertEquals(3998, minimumJumps(new int[]{1998}, 1999, 2000, 2000));
    }
}

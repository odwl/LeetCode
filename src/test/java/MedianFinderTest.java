import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MedianFinderTest {



    class MedianFinder {

        Queue<Integer> asc;
        Queue<Integer> desc;

        public MedianFinder() {
            asc = new PriorityQueue<>(Comparator.reverseOrder());
            desc = new PriorityQueue<>();
        }

        public void addNum(int num) {
            Queue<Integer> toOffer, other;

            if (asc.size() == 0 || num <= asc.peek()) {
                toOffer = asc;
                other = desc;
            } else {
                toOffer = desc;
                other = asc;
            }
            toOffer.offer(num);
            if (toOffer.size() >= other.size() + 2) other.offer(toOffer.poll());
        }

        public double findMedian() {
            if (asc.size() == desc.size()) return (asc.peek() + desc.peek()) / 2.0;
            Queue<Integer> longuest = (asc.size() > desc.size()) ? asc : desc;
            return longuest.peek();
        }

    }

    @Test
    public void testMedian() {
        MedianFinder median = new MedianFinder();
        median.addNum(1);
        assertEquals(1, median.findMedian());
        median.addNum(2);
        assertEquals(1.5, median.findMedian());
        median.addNum(3);
        assertEquals(2, median.findMedian());
    }

}

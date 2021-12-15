import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MergeSort {

    /**
     * Pre-conditions: a[lo..mid] and a[mid+1..hi] are sorted
     * Post-conditions: aux[lo..hi] is sorted and a is left unchanged
     *
     * For example, let a = [4, 5, 1, 3], lo = 0, mid = 1, hi = 3
     * We have that the portion [4, 5] and [1, 3] are sorted
     *
     * The merge function take this two portions and put them in aux
     * in the correct order
     *
     * At the end of the execution, we have a = [4, 5, 1, 3] and 
     * aux = [1, 3, 4, 5]
     */
    public static void merge(int[] a, int[] aux, int lo, int mid, int hi) {
        int i = lo;
        int j = mid + 1;
        for (int ind = lo; ind <= hi; ind++) {

            if (i<=mid && ( j > hi || a[i] <= a[j])) {
                aux[ind] = a[i];
                i++;
            } else if(j <= hi) {
                aux[ind] = a[j];
                j++;
            }

        }
    }

    /**
     *
     * @param a the array to sort from lo to hi
     * @param aux the auxiliary array used in the merge function
     * @param lo the lower bound index for the sort
     * @param hi the upper bound index for the sort
     * @return nothing. The a array is sorted from lo to hi
     *
     * This function is recursive. This means that you should first call it
     * on the first half part of the array, then the other half. Once this is done,
     * you should merge the two parts together.
     *
     * So if a = [1, 4, 2, 6, 3, 10], you should recursively call the method on
     * the part with [1, 4, 2] and [6, 3, 10] (! use the lo and hi index) then merge
     * these parts with the merge function.
     *
     * hint: since the mergeSort function modify only from lo to hi, you can call it
     * successively on different part of the array
     */
    public static void mergeSort(int[] a, int [] aux, int lo, int hi) {
        // Condition simple
        if (lo == hi) {
            aux[lo] = a[lo];
            return;
        }

        int mid = lo + Math.round((hi - lo) / 2);
        mergeSort(a, aux, lo, mid);
        mergeSort(a, aux, mid+1, hi);

        int[] newAux = new int[aux.length];
        merge(aux, newAux, lo, mid, hi);

        for (int i = lo; i <= hi; i++) {
            aux[i] = newAux[i];
        }
        return;
    }

    /**
     * Rearranges the array in ascending order, using the natural order
     */
    public static Iterable<Integer> sort(List<Integer> list) {
        int size = Iterables.size(list);
        if (size == 1) {
            return Lists.newArrayList(list);
        }

        Iterable<Iterable<Integer>> sorted = Lists.partition(list, size / 2)
                .stream()
                .map(sub -> sort(sub)).collect(Collectors.toList());

        return Iterables.mergeSorted(sorted, Ordering.natural());
    }
}

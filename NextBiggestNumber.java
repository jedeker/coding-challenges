import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create a function that takes a positive integer and returns the next bigger number that can be formed by rearranging its digits.
 * Examples:
 *  12 ==> 21
 *  513 ==> 531
 *  2017 ==> 2071
 */
public class NextBiggerNumber {
    public static long nextBiggerNumber(long n) {
        LexicographicIterator<String> iterator = new LexicographicIterator<>(Long.toString(n).split(""));
        return iterator.hasNext() ? Long.parseLong(iterator.next().stream()
                .collect(Collectors.joining())) : -1;
    }

    private static class LexicographicIterator<E extends Comparable> implements Iterator<List<E>> {

        private List<E> elements;
        private int x, y;

        public LexicographicIterator(E[] a) {
            elements = Arrays.asList(a);
        }

        @Override
        public boolean hasNext() {
            x = -1;
            for (int i = 0; i < elements.size() - 1; i++) {
                if (elements.get(i).compareTo(elements.get(i + 1)) < 0)
                    x = i;
            }
            return x >= 0;
        }

        @Override
        public List<E> next() {
            y = 0;
            for (int i = x; i < elements.size(); i++) {
                if (elements.get(x).compareTo(elements.get(i)) < 0)
                    y = i;
            }

            swap(x, y);
            reverse(x + 1);

            return new ArrayList<E>(elements);
        }

        private void swap(int a, int b) {
            E temp = elements.get(a);
            elements.set(a, elements.get(b));
            elements.set(b, temp);
        }

        private void reverse(int n) {
            for (int i = 0; i < (elements.size() - n) / 2; i++) {
                swap(i + n, (elements.size() - 1) - i);
            }
        }
    }
}

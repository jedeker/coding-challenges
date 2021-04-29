import java.util.ArrayList;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;

/**
 * Create an endless stream of prime numbers. The stream must be able to
 * produce a million primes in a few seconds.
 */
public class PrimeStream {
    public static IntStream stream() {
        return IntStream.concat(IntStream.of(2, 3, 5, 7),
                IntStream.generate(new IntSupplier() {
                    private int[] a = {5, 7};
                    int i = 1;

                    @Override
                    public int getAsInt() {
                        i = 1 - i;
                        return a[i] += 6;
                    }
                }).filter(new IntPredicate() {
                    private List<Integer> p = new ArrayList<>();
                    {
                        p.add(2);
                        p.add(3);
                        p.add(5);
                        p.add(7);
                    }

                    @Override
                    public boolean test(int n) {
                        for (int i : p) {
                            if (i > Math.sqrt(n)) break;
                            if (n % i == 0) return false;
                        }
                        p.add(n);
                        return true;
                    }
                })
        );
    }
}

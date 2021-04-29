import java.util.ArrayDeque;
import java.util.StringJoiner;

/**
 * Given a positive integral number n, return a strictly increasing sequence of numbers,
 * so that the sum of the squares is equal to n².
 * If there are multiple solutions, return as far as possible the result with the largest
 * possible values.
 */
public class SquaresIntoSquares {

    public String decompose(long n) {
        if (n < 5) return null; // Five the is first valid case
        ArrayDeque<Long> chain = new ArrayDeque<>(); // Stores squares
        // Add first element
        chain.add(n - 1);
        n = n * n - (n - 1) * (n - 1);
        // Start
        search:
        while (true) {
            // Find decreasing squares
            for (long i = chain.getFirst() - 1; i > 0; i--) {
                if (n - i * i >= 0) {
                    chain.addFirst(i);
                    if ((n -= i * i) == 0)
                        break search; // Complete
                }
            }
            long x = 0;
            while (!chain.isEmpty()) {
                long l = chain.removeFirst(); // Backtrack
                if (l == ++x) {
                    n += l * l;
                } else {
                    chain.addFirst(l - 1);
                    n += l * l - (l - 1) * (l - 1);
                    continue search; // Try again with smaller square
                }
            }
            return null; // No solutions
        }
        // Create String representation
        StringJoiner joiner = new StringJoiner(" ");
        for (long l : chain)
            joiner.add(Long.toString(l));
        return joiner.toString();
    }
}

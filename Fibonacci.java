import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Calculate the nth fibonacci number, where n <= 2,000,000.
 */
public class Fibonacci {

    static Map<Integer, BigInteger> values = new HashMap<>();
    static {
        values.put(0, BigInteger.ZERO);
        values.put(1, BigInteger.ONE);
        values.put(2, BigInteger.ONE);
    }
    
    public static BigInteger fib(int n) {
        if (values.containsKey(n)) return values.get(n);
        int k = n / 2;
        BigInteger x = (n % 2 == 0) ? fib(k).multiply( fib(k).add(BigInteger.TWO.multiply(fib(k-1))) )
                                    : fib(k).multiply( fib(k)).add(fib(k+1).multiply(fib(k+1)) );
        // BigInteger x = fib(k + 1).multiply(fib(n - k)).add(fib(k).multiply(fib(n - (k + 1))));
        values.put(n, x);
        return x;
    }
}

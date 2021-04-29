import java.util.ArrayList;
import java.util.List;

/**
 * If n is the numerator and d the denominator of a fraction, that fraction is
 * defined a (reduced) proper fraction if and only if GCD(n,d)==1.
 * Build a function that computes how many proper fractions you can build with
 * a given denominator.
 */
public class ReducedFractions {

    private static long gcd(long x, long y) {
        while (y != 0)
            y = x % (x = y);
        return x;
    }

    private static long lcm(long x, long y) {
        return x / gcd(x, y) * y;
    }

    private static List<Long> factors(long x) {
        List<Long> set = new ArrayList<>();
        if (x % 2 == 0) {
            set.add(2L);
            do {
                x /= 2;
            } while (x % 2 == 0);
        }
        if (x % 3 == 0) {
            set.add(3L);
            do {
                x /= 3;
            } while (x % 3 == 0);
        }
        for (long l = 5; l <= Math.sqrt(x); l += 6) {
            if (x % l == 0) {
                set.add(l);
                do {
                    x /= l;
                } while (x % l == 0);
            }
        }
        for (long l = 7; l <= Math.sqrt(x); l += 6) {
            if (x % l == 0) {
                set.add(l);
                do {
                    x /= l;
                } while (x % l == 0);
            }
        }
        if (x > 1) set.add(x);
        return set;
    }

    private static long overlap(long x, List<Long> list) {
        if (list.size() < 1) return 0;
        long t = 0;
        for (int i = 1; i < list.size(); i++) {
            List<Long> tList = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                long lcm = lcm(list.get(i), list.get(j));
                t += (x - 1) / lcm;
                if (lcm < x) tList.add(lcm);
            }
            t -= overlap(x, tList);
        }
        return t;
    }

    public static long properFractions(long x) {
        List<Long> factors = factors(x);
        long t = x - 1;
        for (long l : factors)
            t -= (x - 1) / l;
        return t + overlap(x, factors);
    }
}

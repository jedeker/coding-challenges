import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Write a function expand that takes in an expression with a single, one
 * character variable, and expands it. The expression is in the form (ax+b)^n
 * where a and b are integers which may be positive or negative, x is any
 * single character variable, and n is a natural number. If a = 1, no
 * coefficient will be placed in front of the variable. If a = -1, a "-" will
 * be placed in front of the variable.
 */
public class BinomialExpansion {

    private static long nCr(long n, long k) {
        long p = 1, q = 1;
        for (int i = 1; i <= k; i++) {
            p *= n - (i - 1);
            q *= i;
        }
        return p / q;
    }

    private static long pow(long b,  long n) {
        if (n == 0) return 1;
        long p = b;
        for (int i = 1; i < n; i++) {
            p *= b;
        }
        return p;
    }

    public static String expand(String expression) {
        Pattern p = Pattern.compile("^\\((-\\d+|\\d+)?(-)?([a-z])([+\\-]\\d+)\\)\\^(\\d+)$");
        Matcher m = p.matcher(expression);
        long a, b, n;
        char x;

        if (!m.find()) throw new IllegalArgumentException();
        a = m.group(1) == null ? 1 : Long.parseLong(m.group(1));
        if (m.group(2) != null) a = -a;
        x = m.group(3).charAt(0);
        b = Long.parseLong(m.group(4));
        n = Long.parseLong(m.group(5));

        StringBuilder s = new StringBuilder();
        for (int i = 0; i <= n; i++) {
            long c = nCr(n, i) * pow(a, n - i) * pow(b, i);
            if (c != 0) {
                if (i > 0 && c > 0) s.append('+');
                if (c < 0) s.append('-');
                if (Math.abs(c) != 1 || n == i) s.append(Math.abs(c));
                if (n - i > 0) s.append(x);
                if (n - i > 1) s.append('^').append(n - i);
            }
        }
        return s.toString();
    }
}

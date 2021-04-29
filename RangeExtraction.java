import java.util.StringJoiner;

/**
 * A format for expressing an ordered list of integers is to use a comma
 * separated list of either:
 * - individual integers
 * - or a range of integers denoted by the starting integer separated from
 *   the end integer in the range by a dash, '-'. The range includes all
 *   integers in the interval including both endpoints. It is not considered
 *   a range unless it spans at least 3 numbers. For example "12,13,15-17".
 */
public class RangeExtraction {
    public static String rangeExtraction(int[] a) {
        StringJoiner joiner = new StringJoiner(",");
        int length = 1;
        for (int i = 1; i < a.length; i++) {
            if (a[i] - 1 == a[i - 1]) {
                length++;
            } else {
                if (length > 2) {
                    joiner.add(a[i - length] + "-" + a[i - 1]);
                } else {
                    for (int j = 0; j < length; j++) {
                        joiner.add(a[i - length + j] + "");
                    }
                }
                length = 1;
            }
        }
        if (length > 2) {
            joiner.add(a[a.length - length] + "-" + a[a.length - 1]);
        } else {
            for (int j = 0; j < length; j++) {
                joiner.add(a[a.length - length + j] + "");
            }
        }
        return joiner.toString();
    }
}

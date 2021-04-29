import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Complete the solution so that it strips all text that follows any of a set
 * of comment markers passed in. Any whitespace at the end of the line should
 * also be stripped out.
 */
public class StripComments {

    public static String stripComments(String text, String[] symbols) {
        return Arrays.stream(text.split("\n"))
                .map(s -> s.substring(0, Arrays.stream(symbols)
                        .mapToInt(s::indexOf)
                        .filter(i -> i >= 0)
                        .min().orElse(s.length())).replaceFirst("\\s+$", ""))
                .collect(Collectors.joining("\n"));
    }
}

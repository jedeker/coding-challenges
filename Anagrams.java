import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
/**
 * Consider a "word" as any sequence of capital letters A-Z (not limited to just
 * "dictionary words"). For any word with at least two different letters, there
 * are other words composed of the same letters but in a different order (for
 * instance, STATIONARILY/ANTIROYALIST, which happen to both be dictionary words;
 * for our purposes "AAIILNORSTTY" is also a "word" composed of the same letters
 * as these two).
 * We can then assign a number to every word, based on where it falls in an
 * alphabetically sorted list of all words made up of the same group of letters.
 * One way to do this would be to generate the entire list of words and find the
 * desired one, but this would be slow if the word is long.
 * Given a word, return its number. The function should be able to accept any
 * word 25 letters or less in length (possibly with some letters repeated), and
 * take no more than 500 milliseconds to run.
 */
public class Anagrams {

    private static BigInteger factorial(int x) {
        BigInteger y = BigInteger.ONE;
        for (int i = 2; i <= x; i++) {
            y = y.multiply(BigInteger.valueOf(i));
        }
        return y;
    }
    
    private static <E> BigInteger permutations(List<E> x) {
        Map<E, Integer> map = new HashMap<>();
        for (E e : x) {
            map.merge(e, 1, Integer::sum);
        }

        BigInteger y = factorial(x.size());

        for (Integer i : map.values()) {
            if (i > 1)
                y = y.divide(factorial(i));
        }
        return y;
    }

    private static <E extends Comparable> List<Integer> simplify(List<E> word) {
        List<Integer> list = new ArrayList<>(word.size());
        TreeSet<E> set = new TreeSet<>();
        Map<E, Integer> map = new HashMap<>();

        for (E e : word)
            if (!set.contains(e)) set.add(e);
        for (int i = 0; !set.isEmpty(); i++)
            map.put(set.pollFirst(), i);
        for (E e : word)
            list.add(map.get(e));

        return list;
    }

    public static BigInteger listPosition(String word) {
        return listPosition(word.chars().boxed().collect(Collectors.toList()));
    }

    private static BigInteger listPosition(List<Integer> word) {
        if (word.size() == 1) return BigInteger.ONE;
        BigInteger y = BigInteger.ZERO;
        word = simplify(word);
        for (int i = 0; i < word.get(0); i++) {
            List<Integer> l = new ArrayList<>(word);
            l.remove(l.indexOf(i));
            y = y.add(permutations(l));
        }
        return y.add(listPosition(word.subList(1, word.size())));
    }
}

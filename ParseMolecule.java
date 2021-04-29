import java.util.*;

/**
 * For a given chemical formula represented by a string, count the number of atoms of each element
 * contained in the molecule and return an object.
 */
public class ParseMolecule {

    public static Map<String, Integer> getAtoms(String formula) {
        Map<String, Integer> atoms = new HashMap<>();
        List<String> list = split(formula);
        for (int i = 0; i < list.size(); i += 2) {
            String x = list.get(i), y = list.get(i + 1);
            if ("([{".indexOf(x.charAt(0)) > -1) {
                for (Map.Entry<String, Integer> e : getAtoms(x.substring(1, x.length() - 1)).entrySet())
                    atoms.merge(e.getKey(), e.getValue() * Integer.parseInt(y), Integer::sum);
            } else {
                atoms.merge(x, Integer.parseInt(y), Integer::sum);
            }
        }
        return atoms;
    }

    private static List<String> split(String x) {
        List<String> list = new ArrayList<>();
        Deque<Character> deque = new ArrayDeque<>();
        StringBuilder builder = new StringBuilder();
        int previous = 0;
        for (char c : x.toCharArray()) {
            int i = "([{)]}".indexOf(c);
            if (i > -1) {
                if (i < 3) {
                    if (previous != 3 && builder.length() > 0) {
                        list.add(builder.toString());
                        builder = new StringBuilder();
                        if (previous != 2) list.add("1");
                    }
                    deque.add(")]}".charAt(i));
                    previous = 3;
                } else if (deque.isEmpty() || deque.pollLast() != ")]}".charAt(i - 3)) {
                    throw new IllegalArgumentException();
                } else if (deque.isEmpty()) previous = 0;
            } else if (deque.isEmpty()) {
                if (Character.isUpperCase(c) && builder.length() > 0) {
                    list.add(builder.toString());
                    builder = new StringBuilder();
                    if (previous != 2) list.add("1");
                    previous = 0;
                } else if (Character.isLowerCase(c)) {
                    if (previous == 1) throw new IllegalArgumentException();
                    previous = 1;
                } else if (Character.isDigit(c)) {
                    if (previous != 2) {
                        list.add(builder.toString());
                        builder = new StringBuilder();
                    }
                    previous = 2;
                }
            }
            builder.append(c);
        }
        if (!deque.isEmpty()) throw new IllegalArgumentException();
        if (builder.length() > 0) {
            list.add(builder.toString());
            if (previous != 2) list.add("1");
        }
        return list;
    }
}

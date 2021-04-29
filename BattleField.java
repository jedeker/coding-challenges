import java.util.*;
import java.util.stream.Collectors;

/**
 * Write a method that takes a field for well-known board game "Battleship" as
 * an argument and returns true if it has a valid disposition of ships, false
 * otherwise. Argument is guaranteed to be 10*10 two-dimension array. Elements
 * in the array are numbers, 0 if the cell is free and 1 if occupied by ship.
 */
public class BattleField {

    private static final int[] sizes = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};

    public static boolean fieldValidator(int[][] field) {
        if (Arrays.stream(field).flatMapToInt(Arrays::stream).sum() != Arrays.stream(sizes).sum()) {
            return false;
        }

        for (int n = 1; n < field.length - 1; n++) {
            for (int m = 1; m < field[n].length - 1; m++) {
                if (field[n][m] == 0) continue;
                if (field[n - 1][m - 1] == 1|| field[n - 1][m + 1] == 1 || field[n + 1][m + 1] == 1 || field[n + 1][m - 1] == 1) {
                    return false;
                }
                if (((field[n - 1][m] | field[n + 1][m]) & (field[n][m - 1] | field[n][m + 1])) == 1) {
                    return false;
                }
            }
        }
        
        List<Integer> t = Arrays.stream(sizes).boxed().collect(Collectors.toList());

        for (int n = 0; n < field.length; n++) {
            for (int m = 0; m < field[n].length; m++) {
                if (field[n][m] == 0) continue;
                field[n][m] = 0;
                int l = 1;
                if (n + 1 < field.length && field[n + 1][m] == 1) {
                    do {
                        field[n + l][m] = 0;
                        l++;
                    } while (n + l < field.length && field[n + l][m] == 1);
                } else if (m + 1 < field[n].length && field[n][m + 1] == 1) {
                    do {
                        field[n][m + l] = 0;
                        l++;
                    } while (m + l < field[n].length && field[n][m + l] == 1);
                }
                int i = t.indexOf(l);
                if (i < 0) {
                    return false;
                }
                t.remove(i);
            }
        }

        return t.size() == 0;
    }
}

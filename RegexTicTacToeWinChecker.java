import java.util.regex.Pattern;

/**
 * Checks win conditions using a 49 character regex.
 * This can be reduced (at the least by 2 characters),
 * but I kept the longer one for readability purposes.
 */
public class RegexTicTacToeWinChecker {
    public static boolean regexTicTacToeWinChecker(String board) {
        return Pattern.compile("([XO])(\\1\\1(...)*$|..\\1..\\1|...\\1...\\1|.\\1.\\1..$)").matcher(board).find();
    }
}

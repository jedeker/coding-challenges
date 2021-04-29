import java.util.Arrays;
import java.util.List;

/**
 * Convert a string to an integer.
 */
public class IntParserReloaded {

    private enum Number {
        ZERO(0),
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        ELEVEN(11),
        TWELVE(12),
        THIRTEEN(13),
        FOURTEEN(14),
        FIFTEEN(15),
        SIXTEEN(16),
        SEVENTEEN(17),
        EIGHTEEN(18),
        NINETEEN(19),
        TWENTY(20),
        THIRTY(30),
        FORTY(40),
        FIFTY(50),
        SIXTY(60),
        SEVENTY(70),
        EIGHTY(80),
        NINETY(90);

        static int getValue(String number) {
            for (Number n : Number.values()) {
                if (n.name().equalsIgnoreCase(number)) {
                    return n.VALUE;
                }
            }
            throw new IllegalArgumentException();
        }

        final int VALUE;

        Number(int n) {
            VALUE = n;
        }
    }

    private enum Power {
        HUNDRED (100),
        THOUSAND(1_000),
        MILLION (1_000_000);

        final int VALUE;

        Power(int n) {
            VALUE = n;
        }
    }

    public static int parseInt(String number) {
        return parseInt(Arrays.asList(number.replaceAll(" and ", " ").split(" ")));
    }

    private static int parseInt(List<String> list) {
        int maxPower = 0, maxIndex = 0;
        for (int i = 0; i < list.size(); i++) {
            for (Power p : Power.values()) {
                if (p.name().equalsIgnoreCase(list.get(i)) && p.VALUE > maxPower) {
                    maxPower = p.VALUE;
                    maxIndex = i;
                }
            }
        }
        if (maxPower > 0) {
            int n = parseInt(list.subList(0, maxIndex)) * maxPower;
            if (maxIndex < list.size() - 1) n += parseInt(list.subList(maxIndex + 1, list.size()));
            return n;
        } else if (list.size() == 1) {
            String s = list.get(0);
            int i = s.indexOf('-');
            if (i > 0) {
                return Number.getValue(s.substring(0, i)) + Number.getValue(s.substring(i + 1));
            } else {
                return Number.getValue(s);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}

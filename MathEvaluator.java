import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Given a mathematical expression as a String, it is evaluated and returned as a double
 * through the <code>calculate</code> method.
 * Integer numbers and decimal numbers are supported.
 * Operations supported include, addition (+), subtraction and negation (-), multiplication (*), and division (/).
 * Multiple levels of parenthesis are supported.
 * The expression is not validated beforehand,
 * invalid input most likely results in a <code>ClassCastException</code>.
 * Whitespace is completely ignored.
 */
public class MathEvaluator {

    /**
     * Allows the operations themselves to be responsible for their individual evaluation.
     */
    private interface Function {
        double apply(double... args);
    }

    /**
     * Contains all supported operations.
     */
    private enum Operation implements Function {
        ADDITION("+", Type.BINARY, 1) {
            @Override
            public double apply(double... args) {
                return args[0] + args[1];
            }
        },
        SUBTRACTION("-", Type.BINARY, 1) {
            @Override
            public double apply(double... args) {
                return args[0] - args[1];
            }
        },
        MULTIPLICATION("*", Type.BINARY, 2) {
            @Override
            public double apply(double... args) {
                return args[0] * args[1];
            }
        },
        DIVISION("/", Type.BINARY, 2) {
            @Override
            public double apply(double... args) {
                return args[0] / args[1];
            }
        },
        NEGATION("_", Type.UNARY_PREFIX, 3) {
            @Override
            public double apply(double... args) {
                return -args[0];
            }
        };

        /**
         * Determines the number of arguments and the position of the arguments.
         */
        enum Type {
            UNARY_PREFIX, UNARY_POSTFIX, BINARY
        }

        /**
         * Base for parenthesis counting system. Max priority of all operations plus one.
         */
        static final int PRIORITY_BASE = 4;

        static Operation of(String symbol) {
            for (Operation o : values()) if (o.SYMBOL.equals(symbol)) return o;
            return null;
        }

        /**
         * A String representation of the operation as found in the expression.
         * The representation is a String, rather than a character, to allow operations such as
         * functions (sqrt, floor, cos, log) and concatenation (||).
         */
        final String SYMBOL;
        /**
         * Determines the arguments of the operation.
         *
         * @see Type
         */
        final Type TYPE;
        /**
         * A value representing a operations position in order of operations.
         */
        final int PRIORITY;

        Operation(String symbol, Type type, int priority) {
            SYMBOL = symbol;
            TYPE = type;
            PRIORITY = priority;
        }
    }

    /**
     * A piece of the expression, may be a number or an operation.
     */
    private interface Token {
        /**
         * The priority of this operation, determined by the operation and depth of parenthesis.
         *
         * @return The priority.
         */
        default int getPriority() {
            return 0;
        }

        /**
         * Returns the number or operation.
         *
         * @return A number or operation.
         */
        Object getValue();
    }

    /**
     * Regex patterns used in parsing and formatting the expression.
     */
    private static final String NUMBER_PATTERN = "\\d+(\\.\\d+)?", OPERATION_PATTERN = "[+\\-*/()_]";

    /**
     * The expression to be evaluated.
     */
    private String expression;
    private ArrayList<Token> tokens = new ArrayList<>();

    public double calculate(String expression) {
        tokens.clear();
        init(expression);
        return evaluate();
    }

    /**
     * Prepares the expression for evaluation and parses it into the token list.
     *
     * @param x The String to be evaluated.
     */
    private void init(String x) {
        /* Prepare */
        // Remove all whitespace
        expression = x.replaceAll("\\s+", "");
        // Test for negation
        if (expression.matches("-" + NUMBER_PATTERN)) expression = expression.replace("-", "_");
        expression = expression.replaceAll("([+\\-*/(])-", "$1_");

        /* Parse */
        Matcher matcher = Pattern.compile(String.format("(?:(%s)|(%s))", OPERATION_PATTERN, NUMBER_PATTERN)).matcher(expression);
        int depth = 0; // Keeps track of nested parenthesis
        while (matcher.find()) {
            String a = matcher.group(1), b = matcher.group(2);
            if (a != null) { // Found operation
                switch (a) {
                    case "(":
                        depth += Operation.PRIORITY_BASE;
                        continue;
                    case ")":
                        depth -= Operation.PRIORITY_BASE;
                        continue;
                }
                Operation o = Operation.of(a);
                int p = o.PRIORITY + depth;
                tokens.add(new Token() {
                    @Override
                    public int getPriority() {
                        return p;
                    }

                    @Override
                    public Object getValue() {
                        return o;
                    }
                });
            } else if (b != null) { // Found number
                tokens.add(() -> Double.parseDouble(b));
            }
        }
    }

    /**
     * Evaluates the expression and returns the result.
     *
     * @return The result.
     */
    private double evaluate() {
        while (tokens.size() > 1) {
            // Find the highest priority operation
            int priority = 0, index = 0;
            for (int i = 0; i < tokens.size(); i++) {
                Token t = tokens.get(i);
                if (t.getPriority() > priority) {
                    priority = t.getPriority();
                    index = i;
                }
            }
            // Evaluate the operation
            Operation o = (Operation) tokens.remove(index).getValue();
            double result;
            switch (o.TYPE) {
                case UNARY_PREFIX:
                    result = o.apply((double) tokens.remove(index).getValue());
                    break;
                case UNARY_POSTFIX:
                    index--;
                    result = o.apply((double) tokens.remove(index).getValue());
                    break;
                case BINARY:
                    index--;
                    result = o.apply((double) tokens.remove(index).getValue(), (double) tokens.remove(index).getValue());
                    break;
                default:
                    result = 0;
            }
            tokens.add(index, () -> result);
        }
        return (double) tokens.get(0).getValue();
    }
}

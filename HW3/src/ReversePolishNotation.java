import java.math.BigInteger;
import java.util.Scanner;

/**
 * This class reads and then evaluates postfix expressions involving BigIntegers and variables.
 * It reads a line from the user, evaluates the expression and displays the result.
 * It continues to do this until the user hits the return key with no input or it encounters an error.
 */
public class ReversePolishNotation {
    private static DynamicStack stack;
    /**
     * Your program will also use the Red Black Tree to store and retrieve variables and their values.
     */
    private static RedBlackTree redBlackTree = new RedBlackTree();

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            stack = new DynamicStack();
            String line = scanner.nextLine();

            if (line.equals("")) {
                System.out.println("terminating");
                break;
            }
            String[] commandLines = line.split(" ");

            for (String s : commandLines) {
                // use the standard binary operators (+, -, *, / , %, =) with their usual meanings.
                if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("%")) {
                    if (stack.getSize() < 2) {
                        throw new Exception("error: stack underflow exception");
                    } else {
                        String one = (String) stack.pop();
                        String two = (String) stack.pop();
                        BigInteger a = getBigInteger(one);
                        if (a == null) {
                            throw new Exception("error: no variable " + one);
                        }
                        BigInteger b = getBigInteger(two);
                        if (b == null) {
                            throw new Exception("error: no variable " + two);
                        }
                        BigInteger result = BigInteger.ZERO;
                        if (s.equals("+")) {
                            result = a.add(b);
                        } else if (s.equals("-")) {
                            result = b.subtract(a);
                        } else if (s.equals("*")) {
                            result = a.multiply(b);
                        } else if (s.equals("/")) {
                            result = b.divide(a);
                        } else if (s.equals("%")) {
                            result = b.mod(a);
                        }
                        stack.push(result.toString());
                    }
                } else if (s.equals("=")) {
                    BigInteger a = getBigInteger((String) stack.pop());
                    // The assignment operator “=” requires that the left hand side of the expression be a variable.
                    String b = (String) stack.pop();
                    if (isWord(b)) {
                        redBlackTree.insert(b, a);
                    } else {
                        throw new Exception(b + " not an lvalue");
                    }
                    stack.push(a.toString());
                } else if (s.equals("^")) {
                    // a ternary operation - powerMod. It will be represented by the circumflex character “^”.
                    if (stack.getSize() < 3) {
                        throw new Exception("error: stack underflow exception");
                    } else {
                        try {
                            BigInteger a = new BigInteger((String) stack.pop());
                            BigInteger b = new BigInteger((String) stack.pop());
                            BigInteger c = new BigInteger((String) stack.pop());

                            // powerMod computes x to the y modulo z.
                            BigInteger d = c.modPow(b, a);
                            stack.push(d.toString());
                        } catch (NumberFormatException e) {
                            System.err.println("error: stack underflow exception");
                        }
                    }
                } else if (s.equals("~")) {
                    // use a unary minus.
                    BigInteger a = new BigInteger((String) stack.pop());
                    // The unary minus will be represented with the tilde character “~”.
                    stack.push(a.negate().toString());
                } else {
                    stack.push(s);
                }
            }
            System.out.println(getBigInteger((String) stack.pop()));
        }
    }

    /**
     * Simple private helper method to validate a word.
     * @param text text to check
     * @return true if valid, false if not
     */
    private static boolean isWord(String text) {
        return text.matches("[a-zA-Z]+");
    }

    private static BigInteger getBigInteger(String s) {
        if (isWord(s)) {
            return redBlackTree.search(s);
        }
        try {
            return new BigInteger(s);
        } catch (NumberFormatException e) {
            System.err.println("error: stack underflow exception");
        }
        return null;
    }
}

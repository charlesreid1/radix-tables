import java.io.*;
import java.util.*;

/**
 * This class computes reciprocal expansions (1/n) in base b.
 *
 * For each n from 1 to Nmax, it performs long division of 1/n in the
 * given base, detecting the repeating cycle.  The output shows:
 *   - The integer part (always 0 for n>1)
 *   - The non-repeating prefix digits
 *   - The repeating cycle in parentheses
 *   - The period length
 *
 * Highlights:
 *   - Terminating fractions (period 0) in green
 *   - Full-reptend primes (period = n-1) in red
 *   - Palindromic cycles in blue
 */
public class Reciprocals {

    public static void main(String[] args) throws FileNotFoundException {
        for (int n = 2; n <= 20; n++) {
            System.out.println("Creating html file for base " + n);
            generateReciprocalsTable(n);
        }
    }

    public static void generateReciprocalsTable(int base) throws FileNotFoundException {

        int Nmax = 200;

        String filename = String.format("docs/reciprocals%d.html", base);
        PrintStream ps = new PrintStream(filename);

        // Sieve to know which n are prime (for full-reptend detection)
        boolean[] isPrime = new boolean[Nmax + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = false;
        isPrime[1] = false;
        for (int i = 2; i * i <= Nmax; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= Nmax; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        printPageHeader(ps);
        ps.println("<p>Reciprocal expansions (1/n) in radix " + base + ":</p>");
        printTableHeader(ps);

        // Column headers
        ps.println("<tr class=\"clean floorline\">");
        ps.println("<td id=\"fat\" class=\"col-n\">n<sub>10</sub></td>");
        ps.println("<td id=\"fat\" class=\"col-n\">n</td>");
        ps.println("<td id=\"fat\" class=\"col-expansion\">1/n</td>");
        ps.println("<td id=\"fat\" class=\"col-period\">period</td>");
        ps.println("</tr>");
        ps.println();

        // Spacer row
        ps.println("<tr>");
        ps.println("<td class=\"col-n\">&nbsp;</td>");
        ps.println("<td class=\"col-n\">&nbsp;</td>");
        ps.println("<td class=\"col-expansion\">&nbsp;</td>");
        ps.println("<td class=\"col-period\">&nbsp;</td>");
        ps.println("</tr>");
        ps.println();

        // 1/1 = 1.0 (special case)
        ps.println("<tr>");
        ps.println("<td class=\"col-n\"><code>1</code></td>");
        ps.println("<td class=\"col-n\"><code>1</code></td>");
        ps.println("<td class=\"col-expansion\"><code class=\"terminating\">1</code></td>");
        ps.println("<td class=\"col-period\"><code>0</code></td>");
        ps.println("</tr>");
        ps.println();

        for (int n = 2; n <= Nmax; n++) {

            // Long division of 1/n in the given base.
            // Track remainders to detect the cycle.
            List<Integer> digits = new ArrayList<>();
            Map<Integer, Integer> remainderPosition = new HashMap<>();

            int remainder = 1;  // we're computing 1/n
            int prefixEnd = -1;
            int cycleStart = -1;

            while (remainder != 0) {
                if (remainderPosition.containsKey(remainder)) {
                    cycleStart = remainderPosition.get(remainder);
                    break;
                }
                remainderPosition.put(remainder, digits.size());
                remainder *= base;
                digits.add(remainder / n);
                remainder = remainder % n;
            }

            // Build the expansion string
            StringBuilder expansion = new StringBuilder("0.");
            int period = 0;
            String cycleStr = "";

            if (remainder == 0) {
                // Terminating
                for (int d : digits) {
                    expansion.append(digitChar(d));
                }
                period = 0;
            } else {
                // Non-terminating: prefix + repeating cycle
                for (int i = 0; i < cycleStart; i++) {
                    expansion.append(digitChar(digits.get(i)));
                }
                expansion.append("(");
                StringBuilder cycleBuf = new StringBuilder();
                for (int i = cycleStart; i < digits.size(); i++) {
                    char dc = digitChar(digits.get(i));
                    expansion.append(dc);
                    cycleBuf.append(dc);
                }
                expansion.append(")");
                period = digits.size() - cycleStart;
                cycleStr = cycleBuf.toString();
            }

            // Determine highlighting class
            String cssClass = "";
            if (period == 0) {
                cssClass = "terminating";
            } else if (isPrime[n] && period == n - 1) {
                cssClass = "full-reptend";
            } else if (period > 0 && isPalindrome(cycleStr) && cycleStr.length() > 1) {
                cssClass = "palindrome";
            }

            // Base-n representation of n
            String nInBase = Integer.toString(n, base);

            ps.println("<tr>");

            // n in base 10
            ps.println("<td class=\"col-n\"><code>" + n + "</code></td>");

            // n in current base
            ps.println("<td class=\"col-n\"><code>" + nInBase + "</code></td>");

            // expansion
            if (!cssClass.isEmpty()) {
                ps.println("<td class=\"col-expansion\"><code class=\"" + cssClass + "\">" + expansion + "</code></td>");
            } else {
                ps.println("<td class=\"col-expansion\"><code>" + expansion + "</code></td>");
            }

            // period
            ps.println("<td class=\"col-period\"><code>" + period + "</code></td>");

            ps.println("</tr>");

            // Spacer every 5 rows
            if (n % 5 == 0) {
                ps.println("<tr>");
                ps.println("<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>");
                ps.println("</tr>");
            }

            ps.println();
        }

        printTableFooter(ps);
        printPageFooter(ps);
    }

    /** Convert a digit value (0-35) to its character representation. */
    static char digitChar(int d) {
        if (d < 10) return (char) ('0' + d);
        return (char) ('a' + d - 10);
    }

    static boolean isPalindrome(String s) {
        String r = new StringBuilder(s).reverse().toString();
        return s.equals(r);
    }

    static void printPageHeader(PrintStream ps) {
        ps.println("<div id=\"guts\">");
    }

    static void printPageFooter(PrintStream ps) {
        ps.println("</div><!-- end guts -->");
    }

    static void printTableHeader(PrintStream ps) {
        ps.println("<table class=\"table\">");
        ps.println();
    }

    static void printTableFooter(PrintStream ps) {
        ps.println("</table>");
        ps.println();
    }
}

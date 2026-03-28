import java.io.*;
import java.util.*;
import java.math.BigInteger;

/**
 * This class generates a table of Fibonacci numbers in each base,
 * highlighting palindromes and Pisano period boundaries.
 */
public class Fibonacci {

    public static void main(String[] args) throws FileNotFoundException {

        for( int n = 2; n <= 20; n++ ) {
            System.out.println("Creating html file for base "+n);
            generateFibonacciTable(n);
        }

    }

    /**
     * Compute the Pisano period — the period of F(n) mod m.
     * The cycle always starts with 0, 1, so we look for that pair repeating.
     */
    public static int pisanoPeriod(int m) {
        int prev = 0, curr = 1;
        for(int i = 1; i <= m * m + 1; i++) {
            int next = (prev + curr) % m;
            prev = curr;
            curr = next;
            if(prev == 0 && curr == 1) {
                return i;
            }
        }
        return -1; // shouldn't happen
    }

    public static void generateFibonacciTable(int b) throws FileNotFoundException {

        int maxFib = 500;

        String filename = String.format("docs/fibonacci%d.html", b);

        PrintStream ps = new PrintStream(filename);

        int base = b;


        ////////////////////////////////////
        // The Maths

        BigInteger[] fib = new BigInteger[maxFib + 1];
        fib[0] = BigInteger.ZERO;
        fib[1] = BigInteger.ONE;
        for(int i = 2; i <= maxFib; i++) {
            fib[i] = fib[i-1].add(fib[i-2]);
        }

        int pisano = pisanoPeriod(base);

        // Count palindromes
        int palindromeCount = 0;
        for(int i = 0; i <= maxFib; i++) {
            String s = fib[i].toString(base);
            if(checkIfPalindrome(s)) palindromeCount++;
        }


        ////////////////////////////////////
        // The HTMLs

        printPageHeader(ps);
        ps.println("<p>The first " + (maxFib + 1)
                + " Fibonacci numbers in radix " + base
                + ". Pisano period &pi;(" + base + ") = " + pisano
                + " (the last-digit cycle repeats every " + pisano + " terms). There are " + palindromeCount + " palindromes in this range.</p>");
        printTableHeader(ps);

        // column headers
        ps.println("<tr>");
        ps.print("<td id=\"fat\" class=\"left\">");
        ps.print("n<sub>10</sub>");
        ps.println("</td>");
        ps.print("<td id=\"fat\" class=\"left\">");
        ps.print("n");
        ps.println("</td>");
        ps.print("<td id=\"fat\" class=\"right\">");
        ps.print("F(n)");
        ps.println("</td>");
        ps.println("</tr>");
        ps.println();

        // spacer row
        ps.println("<tr>");
        ps.print("<td class=\"left\">&nbsp;</td>");
        ps.print("<td class=\"left\">&nbsp;</td>");
        ps.print("<td class=\"right\">&nbsp;</td>");
        ps.println("</tr>");
        ps.println();

        // rows of Fibonacci numbers
        for(int r = 1; r <= maxFib; r++) {

            // Mark Pisano period boundaries
            boolean isPisanoBoundary = (pisano > 0 && r > 0 && r % pisano == 0);

            if(isPisanoBoundary) {
                ps.println("<tr class=\"pisano-boundary\">");
            } else {
                ps.println("<tr>");
            }

            BigInteger temp = new BigInteger(Integer.toString(r));

            // base 10 index
            ps.print("<td class=\"left\">");
            ps.print("<code>");
            ps.print(r);
            ps.print("</code>");
            ps.println("</td>");

            // base n index
            ps.print("<td class=\"left\">");
            ps.print("<code>");
            ps.print(temp.toString(base));
            ps.print("</code>");
            ps.println("</td>");

            // F(n)
            String fibStr = fib[r].toString(base);
            boolean isPalindrome = checkIfPalindrome(fibStr);
            ps.print("<td class=\"right\">");
            if(isPalindrome) {
                ps.print("<code class=\"palindrome\">");
            } else if(isPisanoBoundary) {
                ps.print("<code class=\"pisano\">");
            } else {
                ps.print("<code>");
            }
            ps.print(fibStr);
            ps.print("</code>");
            ps.println("</td>");

            ps.println("</tr>");

            // spacer row every 5 rows
            if(r % 5 == 0) {
                ps.println("<tr>");
                ps.print("<td class=\"left\">&nbsp;</td>");
                ps.print("<td class=\"left\">&nbsp;</td>");
                ps.print("<td class=\"right\">&nbsp;</td>");
                ps.println("</tr>");
            }

        }

        printTableFooter(ps);
        printPageFooter(ps);

    }

    public static void printPageHeader(PrintStream ps) {
        ps.println("<div id=\"guts\">");
    }

    public static void printPageFooter(PrintStream ps) {
        ps.println("</div><!-- end guts -->");
    }

    public static void printTableHeader(PrintStream ps) {
        ps.println("<table class=\"table\">");
        ps.println();
    }

    public static void printTableFooter(PrintStream ps) {
        ps.println("</table>");
        ps.println();
    }

    public static boolean checkIfPalindrome(String s) {
        String r = new StringBuilder(s).reverse().toString();
        if( s.equals(r) && s.length() > 2 ) {
            return true;
        } else {
            return false;
        }
    }

}

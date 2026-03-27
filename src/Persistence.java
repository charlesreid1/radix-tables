import java.io.*;
import java.util.*;
import java.math.BigInteger;

/**
 * This class generates a grid of integers 1..500, colored by their
 * multiplicative persistence — the number of times you must multiply
 * a number's digits together before reaching a single digit.
 */
public class Persistence {

    public static void main(String[] args) throws FileNotFoundException {

        for( int n = 2; n <= 20; n++ ) {
            System.out.println("Creating html file for base "+n);
            generatePersistenceTable(n);
        }

    }

    /**
     * Compute multiplicative persistence of num in the given base.
     * Returns the number of digit-product steps to reach a single digit.
     */
    public static int persistence(int num, int base) {
        if(num < base) return 0;
        int steps = 0;
        long current = num;
        while(current >= base) {
            String digits = Long.toString(current, base);
            long product = 1;
            for(int i = 0; i < digits.length(); i++) {
                product *= Character.digit(digits.charAt(i), base);
                if(product == 0) {
                    // once we hit zero, result is zero (single digit)
                    steps++;
                    return steps;
                }
            }
            current = product;
            steps++;
        }
        return steps;
    }

    public static void generatePersistenceTable(int b) throws FileNotFoundException {

        String filename = String.format("docs/persistence%d.html", b);

        PrintStream ps = new PrintStream(filename);

        int base = b;

        int Ncols = 25;
        int Nrows = 20;
        int total = Ncols * Nrows; // 500


        ////////////////////////////////////
        // The Maths

        int[] pers = new int[total + 1];
        int maxPers = 0;

        // Track smallest number achieving each persistence level
        Map<Integer, Integer> smallestWithPersistence = new TreeMap<>();

        for(int i = 1; i <= total; i++) {
            pers[i] = persistence(i, base);
            if(pers[i] > maxPers) maxPers = pers[i];
            if(!smallestWithPersistence.containsKey(pers[i])) {
                smallestWithPersistence.put(pers[i], i);
            }
        }

        // Count numbers at each persistence level
        int[] countByLevel = new int[maxPers + 1];
        for(int i = 1; i <= total; i++) {
            countByLevel[pers[i]]++;
        }


        ////////////////////////////////////
        // The HTMLs

        printPageHeader(ps);
        ps.println("<p>Integers 1&ndash;" + total + " in radix " + base
                + ". Colored by multiplicative persistence (max " + maxPers + " in this range).</p>");

        // Summary table: smallest number with each persistence level
        ps.println("<p><strong>Smallest number with persistence <em>k</em>:</strong></p>");
        ps.println("<table class=\"table summary-table\">");
        ps.println("<tr>");
        ps.println("<td id=\"fat\"><code>k</code></td>");
        for(int k = 0; k <= maxPers; k++) {
            ps.println("<td id=\"fat\"><code class=\"pers" + k + "\">" + k + "</code></td>");
        }
        ps.println("</tr>");
        ps.println("<tr>");
        ps.println("<td id=\"fat\"><code>smallest</code></td>");
        for(int k = 0; k <= maxPers; k++) {
            int val = smallestWithPersistence.get(k);
            BigInteger temp = new BigInteger(Integer.toString(val));
            String baseStr = temp.toString(base);
            ps.println("<td><code class=\"pers" + k + "\">" + baseStr + "</code></td>");
        }
        ps.println("</tr>");
        ps.println("<tr>");
        ps.println("<td id=\"fat\"><code>count</code></td>");
        for(int k = 0; k <= maxPers; k++) {
            ps.println("<td><code class=\"pers" + k + "\">" + countByLevel[k] + "</code></td>");
        }
        ps.println("</tr>");
        ps.println("</table>");

        // Main grid
        printTableHeader(ps);

        // column headers
        ps.println("<tr class=\"clean floorline\">");
        ps.println("<td></td>");
        for( int c = 0; c < Ncols; c++ ) {
            if(c % 5 == 0) {
                ps.print("<td class=\"vtint\" id=\"fat\">");
            } else {
                ps.print("<td id=\"fat\">");
            }
            ps.print("<code>");
            ps.print( c );
            ps.print("</code>");
            ps.println("</td>");
        }
        ps.println("</tr>");

        // spacer row
        ps.println("<tr>");
        ps.println("<td>&nbsp;</td>");
        for(int c = 0; c < Ncols; c++) {
            if(c % 5 == 0) {
                ps.println("<td class=\"vtint\">&nbsp;</td>");
            } else {
                ps.println("<td>&nbsp;</td>");
            }
        }
        ps.println("</tr>");
        ps.println();

        // rows of numbers (row-major order)
        for(int r = 0; r < Nrows; r++) {

            ps.println("<tr>");

            // row label
            ps.println("    <td class=\"clean\" id=\"fat\">" + (r * Ncols) + "</td>");

            for(int c = 0; c < Ncols; c++) {

                int num = r * Ncols + c + 1;

                BigInteger temp = new BigInteger( Integer.toString(num) );
                String tempstring = temp.toString(base);

                if(c % 5 == 0) {
                    ps.print("    <td class=\"vtint\">");
                } else {
                    ps.print("    <td>");
                }

                int p = pers[num];
                ps.print("<code class=\"pers" + p + "\">");
                ps.print(tempstring);
                ps.println("</code>");
                ps.println("</td>");
            }
            ps.println("</tr>");

            // spacer row every 5 rows
            if(r % 5 == 4) {
                ps.println("<tr>");
                for(int c = 0; c < Ncols; c++) {
                    ps.println("<td>&nbsp;</td>");
                }
                ps.println("</tr>");
            }

            ps.println();
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

}

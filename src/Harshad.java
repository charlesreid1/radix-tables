import java.io.*;
import java.util.*;
import java.math.BigInteger;

/**
 * This class generates a grid of integers 1..500, highlighting
 * Harshad (Niven) numbers — numbers divisible by their digit sum.
 */
public class Harshad {

    public static void main(String[] args) throws FileNotFoundException {

        for( int n = 2; n <= 20; n++ ) {
            System.out.println("Creating html file for base "+n);
            generateHarshadTable(n);
        }

    }

    public static void generateHarshadTable(int b) throws FileNotFoundException {

        String filename = String.format("docs/harshad%d.html", b);

        PrintStream ps = new PrintStream(filename);

        int base = b;

        int Ncols = 25;
        int Nrows = 20;
        int total = Ncols * Nrows; // 500


        ////////////////////////////////////
        // The Maths

        // For each number 1..500, check if Harshad in this base
        boolean[] isHarshad = new boolean[total + 1];
        int harshadCount = 0;
        for(int i = 1; i <= total; i++) {
            String digits = Integer.toString(i, base);
            int digitSum = 0;
            for(int d = 0; d < digits.length(); d++) {
                digitSum += Character.digit(digits.charAt(d), base);
            }
            if(digitSum > 0 && i % digitSum == 0) {
                isHarshad[i] = true;
                harshadCount++;
            }
        }


        ////////////////////////////////////
        // The HTMLs

        printPageHeader(ps);
        ps.println("<p>Integers 1&ndash;" + total + " in radix " + base
                + ". Harshad numbers are highlighted ("
                + harshadCount + " of " + total + ").</p>");
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

                boolean palindrome = checkIfPalindrome(tempstring);

                if(isHarshad[num] && palindrome) {
                    ps.print("<code class=\"harshad-palindrome\">");
                } else if(isHarshad[num]) {
                    ps.print("<code class=\"harshad\">");
                } else {
                    ps.print("<code class=\"non-harshad\">");
                }
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

    public static boolean checkIfPalindrome(String s) {
        String r = new StringBuilder(s).reverse().toString();
        if( s.equals(r) && s.length() > 2 ) {
            return true;
        } else {
            return false;
        }
    }

}

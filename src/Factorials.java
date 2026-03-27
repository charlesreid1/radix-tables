import java.io.*;
import java.util.*;
import java.math.BigInteger;

/**
 * This class prints triangle numbers and integer powers base N. 
 */
public class Factorials {

    public static void main(String[] args) throws FileNotFoundException { 

        for( int n = 2; n <= 20; n++ ) {
            System.out.println("Creating html file for base "+n);
            generateFactorialsTable(n);
        }

    }

    public static void generateFactorialsTable(int b) throws FileNotFoundException { 

        int maxFactorial = 100;

        int Ncols = 2;

        String filename = String.format("docs/factorials%d.html",b);

        PrintStream ps = new PrintStream(filename);

        int base = b;


        ////////////////////////////////////
        // The HTMLs

        printPageHeader(ps);
        ps.println("<p>The factorials in radix " + base + ":</p>");
        printTableHeader(ps);


        // row headers
        ps.println("<tr>");

        ps.print("<td id=\"fat\" class=\"left\">");
        ps.print("n<sub>10</sub>");
        ps.println("</td>");

        ps.print("<td id=\"fat\" class=\"left\">");
        ps.print("n");
        ps.println("</td>");

        ps.print("<td id=\"fat\" class=\"right\">");
        ps.print("n!");
        ps.println("</td>");

        ps.println("</tr>");
        ps.println();


        // Empty spacer row
        ps.println("<tr>");
        ps.print("<td id=\"fat\" class=\"left\">&nbsp;</td>");
        ps.print("<td id=\"fat\" class=\"left\">&nbsp;</td>");
        ps.print("<td id=\"fat\" class=\"right\">&nbsp;</td>");
        ps.println("</tr>");
        ps.println();


        // rows and rows of powers
        BigInteger temp;
        String tempstring = "";
        for(int r=1; r<=maxFactorial; r++) {

            ps.println("<tr>");

            temp = new BigInteger( Integer.toString(r) );

            // base 10 representation
            ps.print("<td class=\"left\">");
            tempstring = temp.toString(10);
            ps.print("<code>");
            ps.print(tempstring);
            ps.print("</code>");
            ps.println("</td>");

            // base n representation
            ps.print("<td class=\"left\">");
            tempstring = temp.toString(base);
            ps.print("<code>");
            ps.print(tempstring);
            ps.print("</code>");
            ps.println("</td>");

            // number itself
            ps.print("<td class=\"right\">");
            tempstring = factorial(temp).toString(base);
            boolean ispalindrome = checkIfPalindrome(tempstring);
            if( ispalindrome ) {
                ps.print("<div class=\"palindrome\">");
            }
            ps.print("<code>");
            ps.print(tempstring);
            ps.print("</code>");
            if( ispalindrome ) {
                ps.println("</div>");
            }
            ps.println("</td>");

            ps.println("</tr>");

        }


        // all done, time to print footer and finish the table
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
        // table header
        ps.println("<table class=\"table\">");
        ps.println();
    }

    public static void printTableFooter(PrintStream ps) {
        // table close
        ps.println("</table>");
        ps.println();
    }

    public static BigInteger factorial(BigInteger n) { 
        if( n.equals( BigInteger.ONE ) ) {
            return BigInteger.ONE;
        }
        // return n*factorial(n-1)
        return n.multiply( factorial( n.subtract(BigInteger.ONE) ) );
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



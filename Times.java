import java.io.*;
import java.util.*;
import java.math.BigInteger;

/**
 * This class prints times table in base n
 */
public class Times {

    public static void main(String[] args) throws FileNotFoundException { 
        for( int n = 2; n <= 20; n++ ) {
            System.out.println("Creating html file for base "+n);
            generateTimesTable(n);
        }
    }



    public static void generateTimesTable(int b) throws FileNotFoundException {

        String filename = String.format("docs/times%d.html",b);

        PrintStream ps = new PrintStream(filename);

        int base = b;

        int Nmax = 50;
        int Nrows = Nmax, Ncols = Nmax;



        ////////////////////////////////////
        // The Maths

        ////////////////////////////////////
        // The HTMLs

        printPageHeader(ps);
        ps.println("<p>Times table in radix "+base+":</p>");
        printTableHeader(ps);


        // row headers: multipliers
        ps.println("<tr class=\"clean floorline\">");
        ps.println("<td class=\"vtint\"></td>"); // one empty column
        for( int c=0; c < Ncols; c++ ) {
            if( (c+1)%5==0) {
                ps.println("<td class=\"vtint\" id=\"fat\">");
            } else {
                ps.println("<td id=\"fat\">");
            }
            ps.println( c+1 );
            ps.println("</td>");
        }
        ps.println("</tr>");
        ps.println();

        // rows and rows of prime numbers
        int p = 0;
        boolean hasTint = false;
        BigInteger temp;
        String tempstring = "";
        for(int r=0; r<Nrows; r++) {

            ps.println("<tr>");

            // left-side: multiplier
            ps.println("    <td class=\"vtint\" id=\"fat\">" + (r+1) + "</td>"); 

            for(int c=0; c<Ncols; c++) {

                p = (r+1)*(c+1);
                temp = new BigInteger( Integer.toString(p) );
                tempstring = temp.toString(base);
                boolean onDiag = (r+1)==(c+1);
                if( (c+1)%5==0 || onDiag ) {
                    ps.print("    <td class=\"vtint\">");
                } else {
                    ps.print("    <td>");
                }

                boolean ispalindrome = checkIfPalindrome(tempstring);
                if(ispalindrome) {
                    ps.println("<div class=\"palindrome\">");
                }
                ps.println(tempstring);
                if(ispalindrome) { 
                    ps.println("</div>");
                }

                ps.println("</td>");
            }
            ps.println("</tr>");
            ps.println();

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

    public static boolean checkIfPalindrome(String s) { 
        String r = new StringBuilder(s).reverse().toString();
        if( s.equals(r) && s.length() > 2 ) {
            return true;
        } else {
            return false;
        }
    }

}

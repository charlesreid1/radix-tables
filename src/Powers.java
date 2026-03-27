import java.io.*;
import java.util.*;
import java.math.BigInteger;

/**
 * This class prints triangle numbers and integer powers base N. 
 */
public class Powers {

    public static void main(String[] args) throws FileNotFoundException { 

        for( int n = 2; n <= 20; n++ ) {
            System.out.println("Creating html file for base "+n);
            generatePowersTable(n);
        }

    }

    public static void generatePowersTable(int b) throws FileNotFoundException { 

        int maxPower = 10;
        int Ncols = maxPower + 2; // n thru n^maxPower, plus triangle nums, plus n in base 10
        int Nrows = 1000;

        String filename = String.format("docs/powers%d.html",b);

        PrintStream ps = new PrintStream(filename);

        int base = b;


        // Note: this one does just-in-time maths, 
        // since we don't need to store these numbers 
        // (formatting table is a little easier than with primes)
        


        ////////////////////////////////////
        // The HTMLs

        printPageHeader(ps);
        ps.println("<p>The integer powers in radix " + base + ":</p>");
        printTableHeader(ps);


        // row headers
        ps.println("<tr class=\"clean floorline\">");
        for( int c=0; c < Ncols; c++ ) {
            if(c%2==1) {
                ps.print("<td id=\"fat\" class=\"vtint\">");
            } else {
                ps.print("<td id=\"fat\">");
            }
            if(c==0) {
                ps.print("n<sub>10</sub>");
            } else if(c==1) {
                ps.print("n");
            } else if(c==2) {
                ps.print("T(n)");
            } else {
                int power = c-1;
                ps.print("n<sup>"+(power)+"</sup>");
            }
            ps.println("</td>");
        }
        ps.println("</tr>");
        ps.println();


        // Empty spacer row
        ps.println("<tr>");
        for(int c=0; c<Ncols; c++) {
            if(c%2==1) {
                ps.println("<td class=\"vtint\">&nbsp;</td>");
            } else {
                ps.println("<td>&nbsp;</td>");
            }
        }
        ps.println("</tr>");
        ps.println();


        // rows and rows of powers
        BigInteger temp, temp2;
        String tempstring = "";
        for(int r=1; r<=Nrows; r++) {

            ps.println("<tr>");

            for(int c=0; c<Ncols; c++) {

                //ps.println("<td>");
                if(c%2==1) {
                    ps.print("<td class=\"vtint\">");
                } else {
                    ps.print("<td>");
                }

                temp = new BigInteger( Integer.toString(r) );
                if(c==0) {
                    tempstring = temp.toString(10);
                    ps.println(tempstring);
                } else if(c==1) {
                    tempstring = temp.toString(base);
                    ps.println(tempstring);

                } else if(c==2) {
                    temp2 = temp.multiply( temp.add(BigInteger.ONE) ).divide( new BigInteger("2") );
                    tempstring = temp2.toString(base);
                    boolean ispalindrome = checkIfPalindrome(tempstring);
                    if( ispalindrome ) {
                        ps.println("<div class=\"palindrome\">");
                    }
                    ps.println(tempstring);
                    if( ispalindrome ) {
                        ps.println("</div>");
                    }

                } else {
                    int power = c-1;
                    tempstring = temp.pow(power).toString(base);
                    boolean ispalindrome = checkIfPalindrome(tempstring);
                    if( ispalindrome ) {
                        ps.println("<div class=\"palindrome\">");
                    }
                    ps.println( tempstring );
                    if( ispalindrome ) {
                        ps.println("</div>");
                    }

                }
                ps.println("</td>");
            }
            ps.println("</tr>");
            ps.println();


            // spacer row
            if(r%5==0) { 
                ps.println("<tr>");
                for(int c=0; c<Ncols; c++) {
                    ps.println("<td>&nbsp;</td>");
                }
                ps.println("</tr>");
            }


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



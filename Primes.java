import java.io.*;
import java.util.*;
import java.math.BigInteger;

/**
 * This class prints the primes in base N.
 */
public class Primes {

    public static void main(String[] args) throws FileNotFoundException { 

        for( int n = 2; n <= 20; n++ ) {
            System.out.println("Creating html file for base "+n);
            generatePrimesTable(n);
        }

    }

    public static void generatePrimesTable(int b) throws FileNotFoundException { 

        String filename = String.format("docs/primes%d.html",b);

        PrintStream ps = new PrintStream(filename);

        int base = b;

        int Ncols = 50;
        int Nrows = 100;
        int n = 100000;


        ////////////////////////////////////
        // The Maths

        // initially assume all integers are prime
        boolean[] isPrime = new boolean[n+1];
        for (int i = 2; i <= n; i++) {
            isPrime[i] = true;
        }

        // mark non-primes <= n using Sieve of Eratosthenes
        for (int factor = 2; factor*factor <= n; factor++) {

            // if factor is prime, then mark multiples of factor as nonprime
            // suffices to consider mutiples factor, factor+1, ...,  n/factor
            if (isPrime[factor]) {
                for (int j = factor; factor*j <= n; j++) {
                    isPrime[factor*j] = false;
                }
            }
        }

        // count primes
        int nprimes = 0;
        for (int i = 2; i <= n; i++) {
            if (isPrime[i]) nprimes++;
        }

        // Make a list of primes (sooper inefficient, I know...)
        LinkedList<Integer> primes = new LinkedList<Integer>();
        for(int i=0; i<isPrime.length; i++) {
            boolean z = isPrime[i];
            if(z) {
                primes.add(i);
            }
        }




        ////////////////////////////////////
        // The HTMLs

        printPageHeader(ps);
        ps.println("<p>The first "+(Ncols*Nrows)+" primes in radix "+base+":</p>");
        printTableHeader(ps);

        // row headers
        ps.println("<tr class=\"clean floorline\">");
        ps.println("<td></td>"); // one empty column
        for( int c=0; c < Ncols; c++ ) {
            if(c%5==0) {
                ps.print("<td class=\"vtint\" id=\"fat\">");
            } else {
                ps.print("<td id=\"fat\">");
            }
            ps.print("<code>");
            ps.print( c*100 );
            ps.print("</code>");
            ps.println("</td>");
        }
        ps.println("</tr>");

        // empty spacer row before we start the prime numbers
        ps.println("<tr>");
        ps.println("<td>&nbsp;</td>");
        for(int c=0; c<Ncols; c++) {
            if(c%5==0) {
                ps.println("<td class=\"vtint\">&nbsp;</td>");
            } else {
                ps.println("<td>&nbsp;</td>");
            }
        }
        ps.println("</tr>");
        ps.println();



        // rows and rows of prime numbers
        int p = 0;
        int pm2 = 0; 
        int pp2 = 0;
        boolean hasTint = false;
        BigInteger temp;
        String tempstring = "";
        for(int r=0; r<Nrows; r++) {

            ps.println("<tr>");

            ps.println("    <td class=\"clean\" id=\"fat\">" + (r+1) + "</td>"); // prime index

            for(int c=0; c<Ncols; c++) {

                p = primes.get(c*Nrows + r);

                boolean isTwin = false;
                if(r>0||c>0) {
                    pm2 = primes.get(c*Nrows + r-1);
                    if( p==(pm2+2) ) {
                        isTwin = true;
                    }
                }
                if(r<(Nrows-1)||c<(Ncols-1)) {
                    pp2 = primes.get(c*Nrows + r+1);
                    if( p==(pp2-2) ) {
                        isTwin = true;
                    }
                }

                temp = new BigInteger( Integer.toString(p) );
                tempstring = temp.toString(base);
                if(c%5==0) {
                    ps.print("    <td class=\"vtint\">");
                } else {
                    ps.print("    <td>");
                }

                boolean ispalindrome = checkIfPalindrome(tempstring);
                if(ispalindrome) {
                    ps.println("<code class=\"palindrome\">");
                } else if(isTwin) { 
                    ps.println("<code class=\"twin\">");
                } else {
                    ps.print("<code>");
                }
                ps.println(tempstring);
                ps.println("</code>");
                ps.println("</td>");
            }
            ps.println("</tr>");

            // spacer row
            if(r%5==4) { 
                ps.println("<tr>");
                for(int c=0; c<Ncols; c++) {
                    ps.println("<td>&nbsp;</td>");
                }
            }

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


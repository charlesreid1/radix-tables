import java.io.*;
import java.util.*;
import java.math.BigInteger;

/**
 * This class prints the primes in base N.
 */
public class Primes {

    public static void main(String[] args) throws FileNotFoundException { 

        for( int n = 2; n <= 10; n++ ) {
            System.out.println("Creating html file for base "+n);
            generateTable(n);
        }

    }

    public static void generateTable(int b) throws FileNotFoundException { 

        String filename = String.format("primes%d.html",b);

        PrintStream ps = new PrintStream(filename);

        int base = b;

        int Ncols = 20;
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
        ps.println("<p>The first 2,000 primes in radix "+base+":</p>");
        printTableHeader(ps);

        // row headers
        ps.println("<tr class=\"clean floorline\">");
        ps.println("<td></td>"); // one empty column
        for( int c=0; c < Ncols; c++ ) {
            if(c%5==0) {
                ps.println("<td class=\"vtint\" id=\"fat\">");
            } else {
                ps.println("<td id=\"fat\">");
            }
            ps.println( c*100 );
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
        ps.println();



        // rows and rows of prime numbers
        int p = 0;
        boolean hasTint = false;
        BigInteger temp;
        String tempstring = "";
        for(int r=0; r<Nrows; r++) {

            /*
            if(r%5==0) {
                hasTint = !hasTint;
            }
            if( hasTint ) { 
                ps.println("<tr class=\"tint\">");
            } else {
                ps.println("<tr>");
            }
            */
            ps.println("<tr>");

            ps.println("    <td class=\"clean\" id=\"fat\">" + (r+1) + "</td>"); // prime index

            for(int c=0; c<Ncols; c++) {
                p = primes.get(c*Nrows + r);
                temp = new BigInteger( Integer.toString(p) );
                tempstring = temp.toString(base);
                if(c%5==0) {
                    ps.print("    <td class=\"vtint\">");
                } else {
                    ps.print("    <td>");
                }
                ps.println(tempstring);
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
}


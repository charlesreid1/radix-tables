import java.util.*;
import java.math.BigInteger;

/**
 * This class prints the primes in base 3.
 */
public class PrimesPage3 {

    public static void main(String[] args) { 

        int base = 3;

        int Ncols = 20;
        int Nrows = 100;
        int n = 100000;
        //System.out.println("You'd better have at least "+Ncols*Nrows+" primes. You have "+nprimes);

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

        printPageHeader();
        printTableHeader();

        // row headers
        System.out.println("<tr class=\"clean floorline\">");
        System.out.println("<td></td>"); // one empty column
        for( int c=0; c < Ncols; c++ ) {
            if(c%5==0) {
                System.out.println("<td class=\"vtint\" id=\"fat\">");
            } else {
                System.out.println("<td id=\"fat\">");
            }
            System.out.println( c*100 );
            System.out.println("</td>");
        }
        System.out.println("</tr>");
        System.out.println();

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
                System.out.println("<tr class=\"tint\">");
            } else {
                System.out.println("<tr>");
            }
            */
            System.out.println("<tr>");

            System.out.println("    <td class=\"clean\" id=\"fat\">" + (r+1) + "</td>"); // prime index

            for(int c=0; c<Ncols; c++) {
                p = primes.get(c*Nrows + r);
                temp = new BigInteger( Integer.toString(p) );
                tempstring = temp.toString(base);
                if(c%5==0) {
                    System.out.print("    <td class=\"vtint\">");
                } else {
                    System.out.print("    <td>");
                }
                System.out.println(tempstring);
                System.out.println("</td>");
            }
            System.out.println("</tr>");

            if(r%5==4) { 
                System.out.println("<tr>");
                for(int c=0; c<Ncols+1; c++) {
                    System.out.println("<td>&nbsp;</td>");
                }
            }

            System.out.println();
        }

        printTableFooter();
        printPageFooter();

    }

    public static void printPageHeader() {
        System.out.println("<div id=\"guts\">");
    }

    public static void printPageFooter() {
        System.out.println("</div><!-- end guts -->");
    }

    public static void printTableHeader() {
        // table header
        System.out.println("<table class=\"table\">");
        System.out.println();
    }

    public static void printTableFooter() {
        // table close
        System.out.println("</table>");
        System.out.println();
    }
}



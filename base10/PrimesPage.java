import java.util.*;

/**
 * This class prints the primes in base 10.
 */
public class PrimesPage {

    public static void main(String[] args) { 
        int Ncols = 21;
        int Nrows = 100;
        int n = 1000000;
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
        //System.out.println(primes.size());



        // table header
        System.out.println("<table class=\"table\">");
        System.out.println();

        // row headers
        System.out.println("<tr>");
        System.out.println("<td></td>"); // one empty column
        for( int c=0; c < Ncols; c++ ) {
            System.out.println("<td id=\"fat\">" + (c*100) + "</td>");
        }
        System.out.println("</tr>");
        System.out.println();

        int p = 0;
        boolean hasTint = false;
        for(int r=0; r<Nrows; r++) {

            if(r%5==0) {
                hasTint = !hasTint;
            }

            if( hasTint ) {
                System.out.println("<tr id=\"tint\">");
            } else {
                System.out.println("<tr>");
            }

            System.out.println("    <td id=\"fat\">" + (r+1) + "</td>"); // prime index

            for(int c=0; c<Ncols; c++) {
                p = primes.get(c*Nrows + r);
                System.out.println("    <td>" + p + "</td>");
            }
            System.out.println("</tr>");
            System.out.println();
        }

        // table close
        System.out.println("</table>");
        System.out.println();

    }
}



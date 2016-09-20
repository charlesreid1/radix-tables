import java.util.*;

/**
 * This class prints the primes in base 10.
 */
public class PrintPrimes {

    public static void main(String[] args) { 
        int Ncols = 100;
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
        System.out.println("The number of primes <= " + n + " is " + nprimes);

        // Make a list of primes (sooper inefficient, I know...)
        LinkedList<Integer> primes = new LinkedList<Integer>();
        for(int i=0; i<isPrime.length; i++) {
            boolean z = isPrime[i];
            if(z) {
                primes.add(i);
            }
        }
        //System.out.println(primes.size());

        // row headers
        System.out.printf("%3s "," ");
        for(int c=0; c<Ncols; c++) {
            System.out.printf("%6d ",c);
        }
        System.out.print("\n\n");

        int p = 0;
        for(int r=0; r<Nrows; r++) {
            System.out.printf("%3d ",r+1);
            for(int c=0; c<Ncols; c++) {
                p = primes.get(c*Nrows + r);
                System.out.printf("%6d ",p);
            }
            System.out.print("\n");
            if((r+1)%5==0) {
                System.out.print("\n");
            }
        }

    }
}


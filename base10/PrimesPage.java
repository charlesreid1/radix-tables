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

        printPageHeader();
        printTableHeader();

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

        printTableFooter();
        printPageFooter();

    }

    public static void printPageHeader() {
        System.out.println("<!DOCTYPE html>");
        System.out.println("<html lang=\"en\">");
        System.out.println("  <head>");
        System.out.println("    <meta charset=\"utf-8\">");
        System.out.println("    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
        System.out.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
        System.out.println("    <meta name=\"description\" content=\"\">");
        System.out.println("    <meta name=\"author\" content=\"\">");
        System.out.println("");
        System.out.println("    <title>Radix Tables - Prime Numbers</title>");
        System.out.println("");
        System.out.println("    <link rel=\"stylesheet\" href=\"primes.css\">");
        System.out.println("");
        System.out.println("    <!-- Latest compiled and minified CSS -->");
        System.out.println("    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
        System.out.println("    ");
        System.out.println("    <!-- Optional theme -->");
        System.out.println("    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css\" integrity=\"sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp\" crossorigin=\"anonymous\">");
        System.out.println("");
        System.out.println("  </head>");
        System.out.println("");
        System.out.println("  <body>");
        System.out.println("    <div class=\"container\">");
        System.out.println("");
        System.out.println("        <div class=\"row\">");
        System.out.println("            <div class=\"col-md-12\">");
        System.out.println("                <h1>Prime Numbers</h1>");
        System.out.println("                <p class=\"lead\">The following table lists several thousand of the first prime numbers. ");
        System.out.println("                Prime numbers are numbers that are evenly divisible only by themselves and 1. </p>");
        System.out.println("                <p>The primes are listed in column-major order: to look up the 709th prime, ");
        System.out.println("                find the 700 column, then scroll down to row 9. The 709th prime is 5381. ");
        System.out.println("                (Coincidentally, 709 is the 127th prime, and 127 is the 31st prime, and 31 is the 11th prime,");
        System.out.println("                and 11 is the 5th prime, and 5 is the 3rd prime, and 3 is the 2nd prime, ");
        System.out.println("                and 2 is the 1st prime.)</p>");
        System.out.println("            </div>");
        System.out.println("        </div>");
        System.out.println("        <div class=\"row\">");
        System.out.println("            <div class=\"col-md-12\">");
        System.out.println("");
        System.out.println("<!--");
        System.out.println("insert table here.");
        System.out.println("-->");
    }

    public static void printPageFooter() {
        System.out.println("<!--");
        System.out.println("end insert table here.");
        System.out.println("-->");
        System.out.println("");
        System.out.println("            </div>");
        System.out.println("        </div>");
        System.out.println("");
        System.out.println("    </div> <!-- /container -->");
        System.out.println("");
        System.out.println("  </body>");
        System.out.println("</html>");
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



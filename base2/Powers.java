import java.math.BigInteger;

/**
 * Powers:
 * Print the integer powers up to 8.
 */
public class Powers {
    public static void main(String[] args) { 

        int max = 128; 

        System.out.printf("%15s ","n");
        System.out.printf("%20s ","n^2");
        System.out.printf("%25s ","n^3");
        System.out.printf("%30s ","n^4");
        System.out.printf("%37s ","n^5");
        //System.out.printf("%40s ","n^6");
        //System.out.printf("%45s ","n^7");
        //System.out.printf("%50s ","n^8");

        //System.out.print("\n");
        System.out.print("\n");

        for(int j = 1; j <= max; j++ ) { 

            BigInteger n = new BigInteger( Integer.toString(j) );
            System.out.printf("%15s ", n.toString(2) );

            System.out.printf("%20s ", n.pow(2).toString(2) ); 
            System.out.printf("%25s ", n.pow(3).toString(2) ); 
            System.out.printf("%30s ", n.pow(4).toString(2) ); 
            System.out.printf("%37s ", n.pow(5).toString(2) ); 
            //System.out.printf("%40s ", n.pow(6).toString(2) ); 
            //System.out.printf("%45s ", n.pow(7).toString(2) ); 
            //System.out.printf("%50s ", n.pow(8).toString(2) ); 

            System.out.print("\n");
            if( (j)%5==0 ) {
                System.out.print("\n");
            }
        }


    }
}

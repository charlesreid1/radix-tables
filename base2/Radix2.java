import java.math.BigInteger;

public class Radix2 {
    public static void main(String[] args) { 

        int max = 128; 

        System.out.printf("%15s ","n");
        System.out.printf("%20s ","n^2");
        System.out.printf("%25s ","n^3");
        System.out.printf("%30s ","n^4");
        System.out.printf("%40s ","n^5");

        //System.out.print("\n");
        System.out.print("\n");

        for(int j = 1; j <= max; j++ ) { 

            BigInteger n = new BigInteger( Integer.toString(j) );
            System.out.printf("%15s ", n.toString(2) );

            // square
            System.out.printf("%20s ", n.pow(2).toString(2) ); 

            // cube
            System.out.printf("%25s ", n.pow(3).toString(2) ); 

            // 4
            System.out.printf("%30s ", n.pow(4).toString(2) ); 

            // 5
            System.out.printf("%40s ", n.pow(5).toString(2) ); 

            System.out.print("\n");
            if( (j)%5==0 ) {
                System.out.print("\n");
            }
        }


    }
}

/**
 * This class prints the integers n, along with:
 * n^2, sqrt(n), sqrt(10n), n^3, root3(n), root3(10n), root3(100n)
 */
public class SquaresCubesRoots {
    public static void main(String[] args) { 

        int max = 1000;

        System.out.printf("%5s ","n");
        System.out.printf("%12s ","n^2");
        System.out.printf("%18s ","sqrt(n)");
        System.out.printf("%18s ","sqrt(10n)");

        System.out.printf("%14s ","n^3");
        System.out.printf("%18s ","root3(n)");
        System.out.printf("%18s ","root3(10n)");
        System.out.printf("%18s ","root3(100n)");

        //System.out.print("\n");
        System.out.print("\n");



        for(int i=0; i<max; i++) {
            int j = i+1;
            System.out.printf("%5d ", j);

            // square
            System.out.printf("%12d ", j*j);
            System.out.printf("%18.10f ", Math.sqrt(j));
            System.out.printf("%18.10f ", Math.sqrt(10*j));

            // cube
            System.out.printf("%14d ", j*j*j);
            System.out.printf("%18.10f ", Math.pow(j,1.0/3.0));
            System.out.printf("%18.10f ", Math.pow(10*j,1.0/3.0));
            System.out.printf("%18.10f ", Math.pow(100*j,1.0/3.0));

            System.out.print("\n");
            if(j%5==0) {
                System.out.print("\n");
            }
        }

    }
}

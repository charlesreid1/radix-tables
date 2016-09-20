import java.math.BigInteger;

public class Radix2 {
    public static void main(String[] args) { 
        BigInteger number = new BigInteger("2008");

        System.out.println("This program illustrates the use of the BigInteger class to convert to a different radix.");
        System.out.println("Number = " + number);
        System.out.println("Base 2 = " + number.toString(2));
        System.out.println("Base 3 = " + number.toString(3));
        System.out.println("Base 4 = " + number.toString(4));

    }
}


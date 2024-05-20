import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import java.util.*;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class Newton1 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Newton1() {
    }
    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     * 
     * @param x
     *            positive number to compute square root of
     * @return estimate of square root
     */
    private static double sqrt(double x) {
      //  SimpleWriter out = new SimpleWriter1L();
        double r = x;
        final double EPSILON = 1E-2;
        while (Math.abs(r * r - x) / x > (EPSILON * EPSILON)) {
            r = ((r + (x / r)) / 2.0);
        }
        return r;
    }

    /**
     * Main method
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Put your main program code here; it may call myMethod as shown
         */
        String yn; //Stores the choice by the user
        out.println("Enter 'y' if you would like to compute a square root:");
        yn = in.nextLine();
        if (yn.equals("y")) {
        out.println("Enter a number to find the square root for: ");
        double x = in.nextDouble(); 
        double result = sqrt(x);
        out.println(result);
        }
        while (yn.equals("y")) {
            out.println("Would you like to compute another square root? enter 'y'");
            yn = in.nextLine();
            if (yn.equals("y")) {
                out.println("Enter a number to find the square root for: ");
                double x = in.nextDouble(); 
                double result = sqrt(x);
                out.println(result);
                }

           
            }
        
        /*
         * Close input and output streams
         */
    in.close();
    out.close();
   }

}


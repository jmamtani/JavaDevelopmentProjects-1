import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import java.util.*;

/**
 * Put a short phrase describing the program here.
 *
 * @author JM
 *
 */
public final class Newton3 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Newton3() {
    }
    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     * 
     * @param x
     *            positive number to compute square root of
     * @return estimate of square root
     */
    private static double sqrt(double x, double e) {
      //  SimpleWriter out = new SimpleWriter1L();
        if (!((x > 0) || (x < 0))) { 
            return 0;
        }
        else {
        double r = x;
        while (Math.abs(r * r - x) / x > (e * e)) {
            r = ((r + (x / r)) / 2.0);
        }
        return r;
        }
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
            out.println("Please enter the value for Epsilon:");
            double valueOfE = in.nextDouble();
            out.println("Enter a number to find the square root for: ");
            double x = in.nextDouble(); 
            out.println("The square root is: " +sqrt(x, valueOfE));
        }
        while (yn.equals("y")) {
            out.println("Would you like to compute another square root? enter 'y'");
            yn = in.nextLine();
            if (yn.equals("y")) {
                out.println("Please enter the value for Epsilon:");
                double valueOfE = in.nextDouble();
                out.println("Enter a number to find the square root for: ");
                double x = in.nextDouble(); 
                out.println("The square root is: " +sqrt(x, valueOfE));
                }

           
            }

        /*
         * Close input and output streams
         */
 
        in.close();
        out.close();
        }
   }



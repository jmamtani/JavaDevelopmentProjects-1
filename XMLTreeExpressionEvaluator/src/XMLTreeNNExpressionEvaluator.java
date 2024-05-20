import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;
import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Program to evaluate XMLTree expressions of {@code int}.

 * @author Jatin Mamtani

 */
public final class XMLTreeIntExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeIntExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.

     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static NaturalNumber evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        if (exp.label().equals("plus")) {
            NaturalNumber num1 = new NaturalNumber2(evaluate(exp.child(0)));
            num1.add(evaluate(exp.child(1)));
            return num1;
            }
        
        else if (exp.label().equals("times")) {
            NaturalNumber num2 = evaluate(exp.child(0));
            num2.multiply(evaluate(exp.child(1)));
            return num2;
            }

        else if (exp.label().equals("divide")) {
            NaturalNumber num1 = evaluate(exp.child(0));
            NaturalNumber num2 = evaluate(exp.child(1));
            if (num2.canConvertToInt() && num2.toInt() == 0) {
            Reporter.fatalErrorToConsole("Dividing by zero error");
            }
            num1.divide(num2);
            return num1;
        }

        else if (exp.label().equals("minus")) {
            NaturalNumber num1 = new NaturalNumber2(evaluate(exp.child(1)));
            NaturalNumber num2 = new NaturalNumber2(evaluate(exp.child(0)));
            num2.subtract(num1);
            return num2;
        }

        else if (exp.label().equals("number")) {
             return new NaturalNumber2(Integer.parseInt(exp.attributeValue("value")));
             }
        return new NaturalNumber2(0);
    }
    
  


    /**
     * Main method.
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}
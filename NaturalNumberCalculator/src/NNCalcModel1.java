import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Model class.
 *
 * @author Jatin Mamtani
 */
public final class NNCalcModel1 implements NNCalcModel {

    /**
     * Model variables.
     */
    private final NaturalNumber top, bottom;

    /**
     * Default constructor.
     */
    public NNCalcModel1() {
        this.top = new NaturalNumber2();
        this.bottom = new NaturalNumber2();
    }

    @Override
    public NaturalNumber top() {

        //entering the correct return value to display the value on top.

        /*
         * This line added just to make the program compilable. Should be
         * replaced with appropriate return statement.
         */
        return this.top;

    }

    @Override
    public NaturalNumber bottom() {

        //entering the correct return value to display the value on bottom.
        /*
         * This line added just to make the program compilable. Should be
         * replaced with appropriate return statement.
         */
        return this.bottom;
    }

}

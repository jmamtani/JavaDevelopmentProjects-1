import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * @author Jatin Mamtani
 *
 */

public class StringReassemblyTest {

    @Test
    public void combinationTest() {
        final int overlay = 1;
        String test1 = "HelloW";
        String test2 = "World";
        String return2 = StringReassembly.combination(test1, test2, overlay);
        assertEquals(return2, "HelloWorld");
    }

    @Test
    public void addToSetAvoidingSubstringsTest() {
        String test2 = "World";
        Set<String> setStr = new Set1L<>();
        Set<String> check = new Set1L<>();
        setStr.add("Hello");
        check.add("Hello");
        check.add("World");
        StringReassembly.addToSetAvoidingSubstrings(setStr, test2);
        assertEquals(setStr, check);
    }

    @Test
    public void addToSetAvoidingSubstringsTest2() {
        Set<String> setStr = new Set1L<>();
        setStr.add("Software");

        // expected set at the end
        Set<String> check = new Set1L<>();
        check.add("Software is best");

        String test2 = "Software is best";
        StringReassembly.addToSetAvoidingSubstrings(setStr, test2);
        assertEquals(setStr, check);
    }

    @Test
    public void linesFromInputTest() {
        Set<String> str = new Set1L<>();
        SimpleReader in = new SimpleReader1L("cheer-8-2.txt");
        Set<String> check = new Set1L<>();
        check.add("Bucks -- Beat");
        check.add("Go Bucks");
        check.add("o Bucks -- B");
        check.add("Beat Mich");
        check.add("Michigan~");
        str.add(StringReassembly.linesFromInput(in));
        assertEquals(check, str);
        in.close();
    }

    @Test
    public void linesFromInputTest2() {
        Set<String> str = new Set1L<>();
        SimpleReader in = new SimpleReader1L("linesFromInputTest2.txt");

        Set<String> check = new Set1L<>();
        check.add("my name");
        check.add("is Jatin");

        str.add(StringReassembly.linesFromInput(in));
        assertEquals(check, str);
        in.close();
    }

    @Test
    public void printWithLineSeparatorsTest() {
        SimpleWriter out = new SimpleWriter1L();
        String test1 = "This ~ is ~ not ~ easy! ~ send ~ help ~ :)";
        StringReassembly.printWithLineSeparators(test1, out);
        out.close();
    }

}

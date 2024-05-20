import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Creates a glossary with an index page with terms and their definitions.
 *
 * @author Jatin Mamtani
 *
 */
public final class Glossary {

    /**
     * Set to a private constructor to prevent being instantiated.
     */

    private Glossary() {

    }

    /**
     * Compares Strings in alphabetical order.
     */

    private static class StringList implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    /**
     * Generating the output tags by creating header file names.
     *
     * @param out
     *            to store the outputs in a single file
     */

    private static void outputHeader(SimpleWriter out) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Glossary</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Glossary</h2>");
        out.println("<hr/>");
        out.println("<h3>Index</h3>");
        out.println("<ul>");

    }

    /**
     * generating word for output file.
     *
     * @param in
     *            the input file
     * @param out
     *            the output index page
     * @param location
     *            to set the destination of file
     */

    private static void words(SimpleReader in, SimpleWriter out,
            String location) {

        Map<String, String> defT = new Map1L<>();
        String term = "";
        StringBuilder defntn = new StringBuilder("");
        String d = "";

        String line = in.nextLine();

        Queue<String> chrctr = new Queue1L<>();

        Comparator<String> sortChrctr = new StringList();

        while (!in.atEOS()) { //will run until the end is reached
            defntn = new StringBuilder("");

            //if line is not empty

            if (!line.isBlank()) {
                term = line;
                line = in.nextLine();
                defntn.append(line);
                line = in.nextLine();

                //the break condition to update the next term and definition

                if (!line.isBlank()) {

                    while (!line.isBlank()) {
                        d = line;
                        defntn.append(" ").append(d);
                        line = in.nextLine();
                    }

                }

                defT.add(term, defntn.toString());
            } else {

                if (!in.atEOS()) {
                    line = in.nextLine();
                }
            }
        }
        //using map to go through all the terms and storing them in the main file
        for (Map.Pair<String, String> p : defT) {
            SimpleWriter termFile = new SimpleWriter1L(
                    location + "/" + p.key() + ".html");

            outputTermHeader(termFile, p.key(), p.value(), defT);
            chrctr.enqueue(p.key());
        }

        //sorting the list of terms and printing them

        chrctr.sort(sortChrctr);
        int length = chrctr.length();
        while (length > 0) {
            out.println("<li><a href= " + chrctr.front() + ".html" + ">"
                    + chrctr.front() + "</a></li>");
            chrctr.dequeue();
            length = chrctr.length();

        }

    }

    /**
     * Outputs the terms with definition.
     *
     * @param out
     *            the term files
     * @param term
     *            the key word from file
     * @param def
     *            the definition from file
     * @param termDef
     *            the key terms and their definition is added to the map
     */

    private static void outputTermHeader(SimpleWriter out, String term,
            String def, Map<String, String> termDef) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + term + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println(
                "   <h2><i><p style=\"color:red\">" + term + "</p></i></h2>");

        //calling the method to print the definitions

        checkDefinition(out, term, def, termDef);
        out.println("   <hr/>");
        out.println("<p>Return to <a href= index.html> index</a>.</p>");
        out.println("<ul>");

    }

    /**
     * searching for the terms within the definition and linking them to their
     * html file.
     *
     * @param out
     *            the term files
     * @param term
     *            the key word from file
     * @param def
     *            the definition from file
     * @param termDef
     *            the key terms and their definition is added to the map
     */
    private static void checkDefinition(SimpleWriter out, String term,
            String def, Map<String, String> termDef) {
        out.println("<p><t>");
        Set<Character> separator = new Set1L<>();
        separator.add(',');
        separator.add('!');
        separator.add('.');
        separator.add(';');
        separator.add(':');
        separator.add('"');
        separator.add('\'');
        separator.add('\t');
        separator.add(' ');
        separator.add('\n');
        separator.add('?');
        separator.add('/');
        separator.add('-');
        separator.add('(');
        separator.add(')');

        int j = 0;
        String next;
        String value = def;

        //traverse till the length of string and create a hyperlink if the word is found

        while (j < value.length()) {
            next = chkForBrk(value, j, separator);

            if (termDef.hasKey(next)) {

                out.print("<a href=" + next + ".html>" + next + "</a>");
            } else {

                out.print(next);
            }
            j += next.length();
        }
        out.print("</t></p>");

    }

    /**
     * Outputs the closing tags to the HTML file.
     *
     * @param out
     *            the output stream
     */
    private static void outputFooter(SimpleWriter out) {
        out.println("  </ul>");
        out.println("</body>");
        out.println("</html>");

    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the definition from the string
     * @param position
     *            initial position
     * @param separators
     *            breaks or separators in the string
     * @return the word or break separator
     */
    private static String chkForBrk(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        char check = text.charAt(position);

        StringBuilder result = new StringBuilder("");
        boolean match = separators.contains(check);
        boolean contains = true;

        //if it starts of at a character thats a separators

        for (int i = position; i < text.length() && match; i++) {
            if (separators.contains(text.charAt(i)) && contains) {
                result.append(text.charAt(i));

            } else {
                contains = false;
            }
        }

        //if it starts of at a character that isn't a separator

        for (int i = position; i < text.length() && !match; i++) {

            boolean test = separators.contains(text.charAt(i));

            if (!test) {

                result.append(text.charAt(i));

            } else {
                match = true;

            }
        }

        return result.toString();

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //taking the input file

        out.print("Enter name of input file(.txt): ");
        String name = in.nextLine();
        SimpleReader file = new SimpleReader1L(name);

        // taking the location

        out.print("Enter location for the files to be stored: ");
        String output = in.nextLine();

        // index.html will also be stored there

        SimpleWriter locationFile = new SimpleWriter1L(output + "/index.html");

        outputHeader(locationFile);
        words(file, locationFile, output);
        outputFooter(locationFile);

        in.close();
        out.close();
    }

}

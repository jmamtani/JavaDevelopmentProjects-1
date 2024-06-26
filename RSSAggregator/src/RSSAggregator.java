import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to convert an XML RSS (version 2.0) feed from a given URL into the
 * corresponding HTML output file.
 * 
 * @author Jatin Mamtani
 * 
 */
public final class RSSAggregator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private RSSAggregator() {
    }

    /**
     * Outputs the "opening" tags in the generated HTML file. These are the
     * expected elements generated by this method:
     * 
     * <html>
     * <head>
     * <title>the channel tag title as the page title</title>
     * </head>
     * <body>
     *  <h1>the page title inside a link to the <channel> link</h1>
     *  <p>the channel description</p>
     *  <table border="1">
     *   <tr>
     *    <th>Date</th>
     *    <th>Source</th>
     *    <th>News</th>
     *   </tr>
     * 
     * @param channel
     *            the channel element XMLTree
     * @param out
     *            the output stream
     * @updates out.content
     * @requires [the root of channel is a <channel> tag] and out.is_open
     * @ensures out.content = #out.content * [the HTML "opening" tags]
     */
    private static void outputHeader(XMLTree channel, SimpleWriter out) {
        assert channel != null : "Violation of: channel is not null";
        assert out != null : "Violation of: out is not null";
        assert channel.isTag() && channel.label().equals("channel") : ""
                + "Violation of: the label root of channel is a <channel> tag";
        assert out.isOpen() : "Violation of: out.is_open";


        //searches for the title, description and link gives child
        int title = getChildElement(channel, "title");
        int description = getChildElement(channel, "description");
        int link = getChildElement(channel, "link");

        /*
         * The output will be "Empty child" if title tag has no children if it
         * does not have any child text. But if it has it will be assigned to a
         * string variable named titletag.
         */

        //condition for no title
        String titletag = "Empty Title";

        if (channel.child(title).numberOfChildren() == 1) {
            titletag = channel.child(title).child(0).label();
        }

        /*
         * If the child description does not have a child text the output will
         * be "No description". But if it contains a child it will be assigned
         * to the string variable desriptiontag.
         */

        String descriptiontag = "Empty description";
        if (channel.child(description).numberOfChildren() == 1) {
            descriptiontag = channel.child(description).child(0).label();
        }

        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + titletag + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1><a href=\"" + channel.child(link).child(0).label()
                + "\">" + titletag + "</a></h1>");
        out.println("<p>" + descriptiontag + "</p>");
        out.println("<table border=\"1\">");
        out.println("<tr>");
        out.println("<th>Date</th>");
        out.println("<th>Source</th>");
        out.println("<th>News</th>");
        out.println("</tr>");
    }

    /**
     * Outputs the "closing" tags in the generated HTML file.  These are the
     * expected elements generated by this method:
     * 
     * </table>
     * </body>
     * </html>
     * 
     * @param out
     *            the output stream
     * @updates out.contents
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "closing" tags]
     */
    private static void outputFooter(SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Finds the first occurrence of the given tag among the children of the
     * given {@code XMLTree} and return its index; returns -1 if not found.
     * @param xml
     *            the {@code XMLTree} to search
     * @param tag
     *            the tag to look for
     * @return the index of the first child of type tag of the {@code XMLTree}
     *         or -1 if not found
     * @requires [the label of the root of xml is a tag]
     * @ensures <pre>
     * getChildElement =
     *  [the index of the first child of type tag of the {@code XMLTree} or
     *   -1 if not found]
     * </pre>
     */
    private static int getChildElement(XMLTree xml, String tag) {
        assert xml != null : "Violation of: xml is not null";
        assert tag != null : "Violation of: tag is not null";
        assert xml.isTag() : "Violation of: the label root of xml is a tag";

        int n = xml.numberOfChildren();
        int marker = -1;
        int m = 0;
        while (m < n) {
            if (xml.child(m).label().equals(tag)) {
                marker = m;
            }
            m++;
        }
        return marker;
    }

    /**
     * Processes one news item and outputs one table row. The row contains three
     * elements: the publication date, the source, and the title (or
     * description) of the item.
     * @param item
     *            the news item
     * @param out
     *            the output stream
     * @updates out.content
     * @requires
     * [the label of the root of item is an <item> tag] and out.is_open
     * @ensures <pre>
     * out.content = #out.content *
     *   [an HTML table row with publication date, source, and title of news item]
     * </pre>
     */
    private static void processItem(XMLTree item, SimpleWriter out) {
        assert item != null : "Violation of: item is not null";
        assert out != null : "Violation of: out is not null";
        assert item.isTag() && item.label().equals("item") : ""
                + "Violation of: the label root of item is an <item> tag";
        assert out.isOpen() : "Violation of: out.is_open";

        out.println("<tr>"); 

        String publishDatetag = "No date available";
        if (getChildElement(item, "pubDate") != -1) {
            publishDatetag = item.child(getChildElement(item, "pubDate")).child(0)
                    .label();   }

        out.println("<th>" + publishDatetag + "</th>");

        //if the source is present it will be added under source else no source message shown
        
        String sourcetag = "No source available";
        String sourceURL = "";
        if (getChildElement(item, "source") != -1) {
            sourcetag = item.child(getChildElement(item, "source")).child(0)
                    .label();
            sourceURL = item.child(getChildElement(item, "source"))
                    .attributeValue("url");
        }
        out.println("<td><a href = \"" + sourceURL + "\" >" + sourcetag
                + "</a></td>");

        
        //getting the element values for the title and description 
        int t = getChildElement(item, "title"); 
        int d = getChildElement(item, "description"); 
        
        
        String news = "No title available";
        if (d != -1 && item.child(d).numberOfChildren() == 1) {
            news = item.child(d).child(0).label();
        }
        if (t != -1 && item.child(t).numberOfChildren() == 1) {
            news = item.child(t).child(0).label();
        }
        int link = getChildElement(item, "link");
        String linkURL = "";
        if (link != -1) {
            linkURL = item.child(link).child(0).label();
        }
        out.println("<td><a href = \"" + linkURL + "\">" + news
                + "</a></td>\n</tr>");
    }
    
    /**
     * Processes one XML RSS (version 2.0) feed from a given URL converting it
     * into the corresponding HTML output file.
     *
     * @param url
     *            the URL of the RSS feed
     * @param file
     *            the name of the HTML output file
     * @param out
     *            the output stream to report progress or errors
     * @updates out.content
     * @requires out.is_open
     * @ensures <pre>
     * [reads RSS feed from url, saves HTML document with table of news items
     *   to file, appends to out.content any needed messages]
     * </pre>
     */
    private static void processFeed(String url, String file, SimpleWriter out) {
        SimpleWriter fileOut = new SimpleWriter1L(file);
        XMLTree root = new XMLTree1(url);
        if (root.label().equals("rss")) {
            if (root.hasAttribute("version")) {
                if (root.attributeValue("version").equals("2.0")) {
                    XMLTree channel = root.child(0);
                    outputHeader(channel, fileOut);
                    for (int i = 0; i < channel.numberOfChildren(); i++) {
                        if (channel.child(i).label().equals("item")) {
                            processItem(channel.child(i), fileOut);
                        }
                    }
                    outputFooter(fileOut);
                }
            }
        } else {
            out.println("Invalid file!");
        }
    }


    /**
     * Main method.
     * 
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //taking the .xml file as input from the user
     
        out.println("Please enter xml file name with extension (.xml): ");
        String file = in.nextLine();
        
        SimpleWriter fileOut = new SimpleWriter1L("index.html");
        XMLTree index = new XMLTree1(file);

        for (int i = 0; i < index.numberOfChildren(); i++) {
            String fileName = index.child(i).attributeValue("file");
            String urlName = index.child(i).attributeValue("url");
            processFeed(urlName, fileName, out);
        }
        String title = index.attributeValue("title");

        fileOut.print("<html>\n" + "<head>\n" + "\t<title>");
        fileOut.print(title);
        fileOut.print("</title>\n" + "</head>\n" + "<body>\n");
        fileOut.print("<head>\n" + "\t<h1>\n");
        fileOut.print(title);
        fileOut.print("\t</h1>\n" + "</head>\n" + "\t<ul>\n");

        for (int n = 0; n < index.numberOfChildren(); n++) {
            String name = index.child(n).attributeValue("name");
            String fileName = index.child(n).attributeValue("file");

            fileOut.print("\t<li>\n" + "\t<p>\n" + "\t\t<a href=\"");
            fileOut.print(fileName + "\">");
            fileOut.print(name);
            fileOut.print("</a>\n" + "\t</p>\n" + "\t</li>\n");

        }
        fileOut.print("\t</ul>\n" + "</body>\n" + "</html>");
        in.close();
        out.close();
        fileOut.close();
    }

}
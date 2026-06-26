/**
 * Represents bibliographic information for websites.
 */
public class Website implements Publication {
    private final String title, author, websiteName, url;
    private final int year;

    /**
     * Constructs a {@code Website} object.
     *
     * @param title       the title of the webpage/article
     * @param author      the author of the webpage
     * @param websiteName the name of the website
     * @param url         the URL of the website
     * @param year        the year of publication or last update
     */
    public Website(String title, String author, String websiteName, String url, int year) {
        this.title = title;
        this.author = author;
        this.websiteName = websiteName;
        this.url = url;
        this.year = year;
    }

    @Override
    public String citeApa() {
        // Format: Author (Year). Title. Website Name. URL
        return author + " (" + year + "). " + title + ". " + websiteName + ". " + url;
    }

    @Override
    public String citeMla() {
        // Format: Author. "Title." Website Name, Year, URL.
        return author + ". \"" + title + ".\" " + websiteName + ", " + year + ", " + url + ".";
    }
}
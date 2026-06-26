import org.junit.Test;
import static org.junit.Assert.*;

public class WebsiteTest {
    Publication wiki = new Website(
            "Java (programming language)",
            "Wikipedia contributors",
            "Wikipedia",
            "https://en.wikipedia.org/wiki/Java_(programming_language)",
            2026
    );

    @Test
    public void testCiteApa() {
        assertEquals(
                "Wikipedia contributors (2026). Java (programming language). Wikipedia. https://en.wikipedia.org/wiki/Java_(programming_language)",
                wiki.citeApa()
        );
    }

    @Test
    public void testCiteMla() {
        assertEquals(
                "Wikipedia contributors. \"Java (programming language).\" Wikipedia, 2026, https://en.wikipedia.org/wiki/Java_(programming_language).",
                wiki.citeMla()
        );
    }
}
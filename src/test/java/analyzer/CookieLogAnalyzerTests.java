package analyzer;
import org.cookie.analyzer.CookieLogAnalyzer;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CookieLogAnalyzerTests {
    @Test
    void testMostActiveCookie_singleMaxCookie() throws IOException {
        CookieLogAnalyzer analyzer = new CookieLogAnalyzer();
        Path path = Paths.get("src/test/resources/cookie_log_test.csv");

        List<String> result = analyzer.findMostActiveCookies(path, "2018-12-09");
        assertEquals(1, result.size());
        assertEquals("AtY0laUfhglK3lC7", result.get(0));
    }

    @Test
    void testMostActiveCookie_multipleMaxCookies() throws IOException {
        CookieLogAnalyzer analyzer = new CookieLogAnalyzer();
        Path path = Paths.get("src/test/resources/cookie_log_test.csv");

        List<String> result = analyzer.findMostActiveCookies(path, "2018-12-08");
        assertEquals(3, result.size());
        assertEquals("SAZuXPGUrfbcn5UA", result.get(0));
        assertEquals("4sMM2LxV07bPJzwf", result.get(1));
        assertEquals("fbcn5UAVanZf6UtG", result.get(2));
    }

    @Test
    void testMostActiveCookie_noCookiesOnDate() throws IOException {
        CookieLogAnalyzer analyzer = new CookieLogAnalyzer();
        Path path = Paths.get("src/test/resources/cookie_log_test.csv");

        List<String> result = analyzer.findMostActiveCookies(path, "2018-12-10");
        assertEquals(0, result.size());
    }
}

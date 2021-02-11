package tests.userInfo;

import adapters.PlayerAdapter;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class ChangeLanguageTest extends BaseTest {

    @Test(description = "put language")
    public void putLanguage() {
        String[] languages = new String[]{"ru", "en", "de", "fr", "tr"};
        String language = languages[random.nextInt(languages.length)];
        new PlayerAdapter().putLanguage(token, language, 200);
        String updatedLanguage = new PlayerAdapter().getLanguage(token);
        assertEquals(updatedLanguage, language, "Changing language error");
    }

    @Test(description = "change language. Not existing language")
    public void putLanguageNotExistingLanguage() {
        responseBody = new PlayerAdapter().putLanguage(token, "ua", 404);
        assertEquals(responseBody.getMessage(), "Language 'ua' not found", "Changing language with not existing language error");
    }

    @Test(description = "change language without header")
    public void putLanguageWithoutHeader() {
        responseBody = new PlayerAdapter().putLanguageNoHeader(token);
        assertEquals(responseBody.getMessage(), "Missing request header 'lang' for method parameter of type String", "Changing language without header error");
    }
}

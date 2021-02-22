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
        new PlayerAdapter().putLanguage(language, 200);
        String updatedLanguage = new PlayerAdapter().getLanguage();
        assertEquals(updatedLanguage, language, "Changing language error");
    }

    @Test(description = "change language. Not existing language")
    public void putLanguageNotExistingLanguage() {
        responseBetOn = new PlayerAdapter().putLanguage("ua", 404);
        assertEquals(responseBetOn.getMessage(), "Language 'ua' not found", "Changing language with not existing language error");
    }

    @Test(description = "change language without header")
    public void putLanguageWithoutHeader() {
        responseBetOn = new PlayerAdapter().putLanguageNoHeader();
        assertEquals(responseBetOn.getMessage(), "Missing request header 'lang' for method parameter of type String", "Changing language without header error");
    }
}

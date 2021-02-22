package tests.userInfo;

import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class ChangeLanguageTest extends BaseTest {

    @Test(description = "put language")
    public void putLanguage() {
        String[] languages = new String[]{"ru", "en", "de", "fr", "tr"};
        String language = languages[random.nextInt(languages.length)];
        playerAdapter.putLanguage(language, 200);
        String updatedLanguage = playerAdapter.getLanguage();
        assertEquals(updatedLanguage, language, "Changing language error");
    }

    @Test(description = "change language. Not existing language")
    public void putLanguageNotExistingLanguage() {
        responseBetOn = playerAdapter.putLanguage("ua", 404);
        assertEquals(responseBetOn.getMessage(), "Language 'ua' not found", "Changing language with not existing language error");
    }

    @Test(description = "change language without header")
    public void putLanguageWithoutHeader() {
        responseBetOn = playerAdapter.putLanguageNoHeader();
        assertEquals(responseBetOn.getMessage(), "Missing request header 'lang' for method parameter of type String", "Changing language without header error");
    }
}

package tests.userInfo;

import adapters.PlayerAdapter;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class ChangeLanguageTest extends BaseTest {

    @Test(description = "change language")
    public void changeLanguage() {
        String language = "ru";
        new PlayerAdapter().changeLanguage(token, language, 200);
        String updatedLanguage = new PlayerAdapter().getLanguage(token);
        assertEquals(updatedLanguage, language, "Changing language error");
    }

    @Test(description = "change language. Not existing language")
    public void changeLanguageNotExistingLanguage() {
        responseBody = new PlayerAdapter().changeLanguage(token, "ua", 404);
        assertEquals(responseBody.getMessage(), "Language 'ua' not found", "Changing language with not existing language error");
    }

    @Test(description = "change language without header")
    public void changeLanguageWithoutHeader() {
        responseBody = new PlayerAdapter().changeLanguageNoHeader(token);
        assertEquals(responseBody.getMessage(), "Missing request header 'lang' for method parameter of type String", "Changing language without header error");
    }
}

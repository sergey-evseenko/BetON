package tests.sportEvents;

import adapters.TranslationAdapter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class TranslationTest extends BaseTest {

    @DataProvider(name = "Params")
    public Object[][] params() {
        return new Object[][]{
                //langIso is de
                {"22350223", "de", "Fußball", 200, ""},
                //langIso is empty
                {"22350223", "", "Soccer", 200, ""},
                //eventBetradarId is empty
                {"", "de", "", 400, "Required Long parameter 'eventBetradarId' is not present"},
                //eventBetradarId is null
                {null, "de", "", 400, "Required Long parameter 'eventBetradarId' is not present"},
                //langIso is null
                {"22350223", null, "", 400, "Required String parameter 'langIso' is not present"},
        };
    }

    @Test(description = "translate match", dataProvider = "Params")
    public void translateMatch(String eventBetradarId, String langIso, String translation, int expectedStatusCode, String responseMessage) {

        response = new TranslationAdapter().get(eventBetradarId, langIso, translation, expectedStatusCode);
        if (expectedStatusCode == 400) {
            assertEquals(response.getMessage(), responseMessage, "Invalid response");
        }

    }
}

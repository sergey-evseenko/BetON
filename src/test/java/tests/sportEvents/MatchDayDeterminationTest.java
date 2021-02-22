package tests.sportEvents;

import adapters.MatchDayAdapter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class MatchDayDeterminationTest extends BaseTest {

    @DataProvider(name = "Params")
    public Object[][] params() {
        return new Object[][]{
                //all params are null
                {null, null, 400, "Required String parameter 'langIso' is not present"},
                //langIso is null
                {"7", null, 400, "Required String parameter 'langIso' is not present"},
                //categoryId is null
                {null, "en", 400, "Required Long parameter 'categoryId' is not present"},
                //invalid type of categoryId
                {"qwerty", "en", 400, "Type mismatch."},
                //unknow categoryId
                {"123456789", "en", 200, ""},
        };
    }

    @Test(description = "translate match", dataProvider = "Params")
    public void validateParams(String categoryId, String langIso, int expectedStatusCode, String responseMessage) {

        responseBetOn = new MatchDayAdapter().validate(categoryId, langIso, expectedStatusCode);
        if (expectedStatusCode == 400) {
            assertEquals(responseBetOn.getMessage(), responseMessage, "Invalid response");
        }

    }

    @Test(description = "translate match")
    public void translateMatch() {
        new MatchDayAdapter().get("de");
    }

    @Test(description = "translate match with invalid langIso")
    public void translateMatchInvaldLangIso() {
        new MatchDayAdapter().get("qwerty");
    }
}

package tests.sportEvents;

import models.Tournament;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class ListOfTournamentsTest extends BaseTest {

    @Test(description = "get list of tournaments")
    public void getListOfTournaments() {
        Tournament tournament = data.get("tournament.json", Tournament.class);
        Tournament[] responseList = catalogAdapter.getTournaments("de", "1");
        assertEquals(responseList[0], tournament, "Invalid list of tournaments");
    }

    @DataProvider(name = "params")
    public Object[][] params() {
        return new Object[][]{
                //langIso is en
                {"en", "1", 200, ""},
                //empty langIso
                {"", "1", 200, ""},
                //invalid langIso
                {"qwerty", "1", 200, ""},
                //langIso is null
                {null, "1", 400, "Required String parameter 'langIso' is not present"},
                //empty categoryId
                {"en", "", 400, "Required Long parameter 'categoryId' is not present"},
                //categoryId is invalid
                {"en", "qwerty", 400, "Type mismatch."},
                //categoryId is null
                {"en", null, 400, "Required Long parameter 'categoryId' is not present"},
        };
    }

    @Test(description = "validate", dataProvider = "params")
    public void validateParams(String langIso, String categoryId, int expectedStatusCode, String responseMessage) {

        responseBetOn = catalogAdapter.getTournaments(langIso, categoryId, expectedStatusCode);
        if (expectedStatusCode == 400) {
            assertEquals(responseBetOn.getMessage(), responseMessage, "Invalid response");
        }
    }
}

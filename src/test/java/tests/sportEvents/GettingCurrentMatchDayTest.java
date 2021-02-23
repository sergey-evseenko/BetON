package tests.sportEvents;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class GettingCurrentMatchDayTest extends BaseTest {

    @DataProvider(name = "Params")
    public Object[][] params() {
        return new Object[][]{
                //valid params
                {"66825", "35", "en", 200, ""},
                //missed langIso
                {"66825", "35", null, 400, "Required String parameter 'langIso' is not present"},
                //invalid tournamentId
                {"qwerty", "1", "en", 400, "Type mismatch."},
                //invalid roundId
                {"66825", "qwerty", "en", 400, "Type mismatch."},

        };
    }

    @Test(description = "translate match", dataProvider = "Params")
    public void validateParams(String tournamentId, String roundId, String langIso, int expectedStatusCode, String responseMessage) {

        responseBetOn = matchDayAdapter.getMatchDay(tournamentId, roundId, langIso, expectedStatusCode);
        if (expectedStatusCode == 400) {
            assertEquals(responseBetOn.getMessage(), responseMessage, "Invalid response");
        }
    }
}

package tests.sportEvents;

import models.Sport;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class ListOfSportsTest extends BaseTest {

    @Test(description = "get list of sports")
    public void getListOfSports() {
        Sport sport = data.get("sport.json", Sport.class);
        Sport[] responseList = catalogAdapter.getSports("de");
        assertEquals(responseList[0], sport, "Invalid list of sports");
    }

    @DataProvider(name = "params")
    public Object[][] params() {
        return new Object[][]{
                //valid param
                {"en", 200, ""},
                //empty langIso
                {"", 200, ""},
                //invalid langIso
                {"qwerty", 200, ""},
                //invalid langIso (digits)
                {"1234567", 200, ""},
                //langIso is null
                {null, 400, "Required String parameter 'langIso' is not present"},

        };
    }

    @Test(description = "validate params", dataProvider = "params")
    public void validateParams(String langIso, int expectedStatusCode, String responseMessage) {

        responseBetOn = catalogAdapter.getSports(langIso, expectedStatusCode);
        if (expectedStatusCode == 400) {
            assertEquals(responseBetOn.getMessage(), responseMessage, "Invalid response");
        }
    }
}

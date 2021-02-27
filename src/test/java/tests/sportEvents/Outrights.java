package tests.sportEvents;

import models.Outright;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class Outrights extends BaseTest {

    @DataProvider(name = "langIso")
    public Object[][] langIso() {
        return new Object[][]{
                {"en"},
                {"de"},
        };
    }

    @Test(description = "get available outrights", dataProvider = "langIso")
    public void getOutrights(String langIso) {
        Outright outright = data.get("outright.json", Outright.class);
        Outright[] outrightList = outrightAdapter.getOutrights("1", langIso);
        assertEquals(outrightList[0], outright, "Invalid list of outrights");
    }

    @DataProvider(name = "Params")
    public Object[][] params() {
        return new Object[][]{
                {"1", null, 400, "Required String parameter 'langIso' is not present"},
                //outrightId=null & langIso=null
                {"", null, 400, "Required String parameter 'langIso' is not present"},
                //outrightId=null
                {"", "en", 400, "Type mismatch."},
                //outrightId=invalid
                {"qwerty", "en", 400, "Type mismatch."},
                //outrightId=unknown
                {"12345", "en", 200, ""},
        };
    }

    @Test(description = "get available outrights", dataProvider = "Params")
    public void validateParams(String outrightId, String langIso, int expectedStatusCode, String responseMessage) {

        responseBetOn = outrightAdapter.getOutrights(outrightId, langIso, expectedStatusCode);
        if (expectedStatusCode == 400) {
            assertEquals(responseBetOn.getMessage(), responseMessage, "Invalid response");
        }
    }
}

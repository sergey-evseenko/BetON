package tests.sportEvents;

import models.OutrightCategory;
import models.OutrightCompetitor;
import models.OutrightTournament;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class Outright extends BaseTest {

    @Test(description = "get available outright for competitors")
    public void getOutrightForCompetitors() {
        OutrightCompetitor[] outrightList;
        OutrightCompetitor outright = data.get("outrightCompetitor.json", OutrightCompetitor.class);
        //for english (en)
        outrightList = outrightAdapter.getOutright("1", "en", "competitors", OutrightCompetitor[].class);
        assertEquals(outrightList[0], outright, "Invalid list of outright for competitors");
        //for german (de)
        outrightList = outrightAdapter.getOutright("1", "de", "competitors", OutrightCompetitor[].class);
        assertEquals(outrightList[0].getCn(), "Claverie, Luis", "Invalid German translation");
        //for russian (ru)
        outrightList = outrightAdapter.getOutright("1", "ru", "competitors", OutrightCompetitor[].class);
        assertEquals(outrightList[0].getCn(), "", "Invalid Russian translation");
    }

    @Test(description = "get available outright for tournaments")
    public void getOutrightForTournaments() {
        OutrightTournament[] outrightList;
        OutrightTournament outright = data.get("outrightTournament.json", OutrightTournament.class);
        //for english (en)
        outrightList = outrightAdapter.getOutright("25", "en", "tournament", OutrightTournament[].class);
        assertEquals(outrightList[0], outright, "Invalid list of outright for tournaments");
        //for german (de)
        outrightList = outrightAdapter.getOutright("25", "de", "tournament", OutrightTournament[].class);
        assertEquals(outrightList[0].getOn(), "NCAA(F) - Conference - Atlantic Coast - Sieger", "Invalid German translation");
        //for russian (ru)
        outrightList = outrightAdapter.getOutright("25", "ru", "tournament", OutrightTournament[].class);
        assertEquals(outrightList[0].getOn(), "", "Invalid Russian translation");

    }

    @Test(description = "get available outright for categories")
    public void getOutrightForCategory() {
        OutrightCategory[] outrightList;
        OutrightCategory outright = data.get("outrightCategory.json", OutrightCategory.class);
        //for english (en)
        outrightList = outrightAdapter.getOutright("27", "en", "category", OutrightCategory[].class);
        assertEquals(outrightList[0], outright, "Invalid list of outright for categories (en)");
        //for german (de)
        outrightList = outrightAdapter.getOutright("27", "de", "category", OutrightCategory[].class);
        assertEquals(outrightList[0].getCn(), "Männer", "Invalid German translation");
        //for russian (ru)
        outrightList = outrightAdapter.getOutright("27", "ru", "category", OutrightCategory[].class);
        assertEquals(outrightList[0].getSn(), "Гольф", "Invalid Russian translation");
    }

    @DataProvider(name = "Params")
    public Object[][] params() {
        return new Object[][]{
                //outrights for competitors
                //langIso=invalid
                {"competitors", "1", "qwerty", 400, ""},
                //langIso=null
                {"competitors", "1", null, 400, "Required String parameter 'langIso' is not present"},
                //outrightId=invalid
                {"competitors", "qwerty", "en", 400, "Type mismatch."},
                //outrightId=null
                {"competitors", "", "en", 400, "Type mismatch."},
                //outrightId=null & langIso=null
                {"competitors", "", null, 400, "Required String parameter 'langIso' is not present"},
                //outrightId=unknown
                {"competitors", "12345", "en", 200, ""},

                //outrights for tournament
                //langIso=invalid
                {"tournament", "25", "qwerty", 400, ""},
                //langIso=null
                {"tournament", "25", null, 400, "Required String parameter 'langIso' is not present"},
                //outrightId=invalid
                {"tournament", "qwerty", "en", 400, "Type mismatch."},
                //outrightId=null
                {"tournament", "", "en", 400, "Type mismatch."},
                //outrightId=null & langIso=null
                {"tournament", "", null, 400, "Required String parameter 'langIso' is not present"},
                //outrightId=unknown
                {"tournament", "12345", "en", 200, ""},

                //outrights for category
                //langIso=invalid
                {"category", "27", "qwerty", 400, ""},
                //langIso=null
                {"category", "27", null, 400, "Required String parameter 'langIso' is not present"},
                //outrightId=invalid
                {"category", "qwerty", "en", 400, "Type mismatch."},
                //outrightId=null
                {"category", "", "en", 400, "Type mismatch."},
                //outrightId=null & langIso=null
                {"category", "", null, 400, "Required String parameter 'langIso' is not present"},
                //outrightId=unknown
                {"category", "12345", "en", 200, ""},
        };
    }

    @Test(description = "validate params", dataProvider = "Params")
    public void validateParams(String path, String id, String langIso, int expectedStatusCode, String responseMessage) {

        responseBetOn = outrightAdapter.validateParams(path, id, langIso, expectedStatusCode, responseMessage);
        if (StringUtils.isNotEmpty(responseMessage)) {
            assertEquals(responseBetOn.getMessage(), responseMessage, "Invalid response");
        }
    }
}

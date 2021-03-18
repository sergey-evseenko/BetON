package tests.sportEvents;

import models.BetSlip;
import models.LiveMatch;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class TranslationTest extends BaseTest {

    LiveMatch liveMatch;
    BetSlip betSlip;
    String eventId;
    String marketId;
    String[] markets = new String[1];

    @BeforeClass
    public void getEventIdMarketId() throws Exception {
        betSlip = getBetSlip(0);
        eventId = betSlip.getEventId();
        marketId = betSlip.getMarketId();
        markets[0] = marketId;
    }

    @DataProvider(name = "Params")
    public Object[][] params() {
        return new Object[][]{
                //langIso is de
                {eventId, "de", "Fußball", 200, ""},
                //langIso is empty
                {eventId, "", "Soccer", 200, ""},
                //eventBetradarId is empty
                {"", "de", "", 400, "Required Long parameter 'eventBetradarId' is not present"},
                //eventBetradarId is null
                {null, "de", "", 400, "Required Long parameter 'eventBetradarId' is not present"},
                //langIso is null
                {eventId, null, "", 400, "Required String parameter 'langIso' is not present"},
        };
    }

    @Test(description = "translate match", dataProvider = "Params")
    public void translateMatch(String eventId, String langIso, String translation, int expectedStatusCode, String responseMessage) {

        responseBetOn = translationAdapter.get(eventId, langIso, translation, expectedStatusCode);
        if (expectedStatusCode == 400) {
            assertEquals(responseBetOn.getMessage(), responseMessage, "Invalid response");
        }
    }

    @Test(description = "translate matches with valid Id")
    public void translateMatches() {
        liveMatch = new LiveMatch(eventId, "de", markets);
        translationAdapter.post(liveMatch, 200);
    }

    @Test(description = "translate matches with invalid event Id")
    public void translateMatchesInvalidEventId() {
        liveMatch = new LiveMatch("1", "de", markets);
        translationAdapter.post(liveMatch, 417);
    }
}

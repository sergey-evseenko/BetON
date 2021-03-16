package tests.betSlip;

import adapters.SoonLiveWebSocket;
import models.BetSlip;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class BetSlipTest extends BaseTest {

    @BeforeClass
    public void getBet() throws Exception {
        webSocketClientEndpoint = new SoonLiveWebSocket(new URI(webSocketUrl));
        webSocketRequest = new String(Files.readAllBytes(Paths.get("src/test/resources/data/webSocketRequest.json")));
        webSocketClientEndpoint.sendMessage(webSocketRequest);
        betSlip = webSocketClientEndpoint.getBetSlip();
    }

    @Test(description = "Add bet", priority = 1)
    public void addBet() {
        betSlipAdapter.addBet(betSlip, 200);
    }

    @Test(description = "Add bet that was already added", priority = 2)
    public void addBetAlreadyAdded() {
        responseBetOn = betSlipAdapter.addBet(betSlip, 400);
        assertEquals(responseBetOn.getMessage(), "Pick already exists in betslip", "Invalid response");
    }

    @Test(description = "Delete bet", priority = 3)
    public void deleteBet() {
        betSlipAdapter.deleteBet(betSlip, 200);
    }

    @Test(description = "Delete bet already deleted", priority = 4)
    public void deleteBetAlreadyDeleted() {
        responseBetOn = betSlipAdapter.deleteBet(betSlip, 404);
        assertEquals(responseBetOn.getMessage(), "BetSlip not found", "Invalid response");
    }

    @Test(description = "Delete all bets", priority = 5)
    public void deleteAllBets() {
        addBet();
        betSlipAdapter.deleteAll(200);
    }

    @Test(description = "Delete all bets already deleted", priority = 6)
    public void deleteAllBetsAlreadyDeleted() {
        responseBetOn = betSlipAdapter.deleteAll(404);
        assertEquals(responseBetOn.getMessage(), "BetSlip not found", "Invalid response");
    }

    @DataProvider(name = "Params")
    public Object[][] params() {
        int bId = betSlip.getBetRadarId();
        String eventId = betSlip.getEventId();
        String language = betSlip.getLanguage();
        String marketId = betSlip.getMarketId();
        int outcomeId = betSlip.getOutcomeId();
        return new Object[][]{
                //Not existing event id
                {bId, "111", language, marketId, outcomeId, 404, "No live events found for that pick"},
                //Event id is null
                {bId, null, language, marketId, outcomeId, 400, "Pick has wrong values"},
                //Not existing market id
                //{bId, eventId, language, "qwe", outcomeId, 404, "No markets found matching that pick"},
                //Market id is null
                {bId, eventId, language, null, outcomeId, 400, "Pick has wrong values"},
                //Language is null
                {bId, eventId, null, marketId, outcomeId, 400, "field 'languageCode': rejected value [null]"},
                //Not existing outcomes
                {bId, eventId, language, marketId, 123456789, 404, "No outcomes found matching that pick"},
        };
    }

    @Test(description = "Add bet. Validate params", dataProvider = "Params", priority = 7)
    public void validateBet(int bId, String eventId, String language, String marketId, int outcomeId, int expectedStatusCode, String responseMessage) {
        BetSlip invalidBetSlip = new BetSlip(bId, eventId, language, marketId, outcomeId);
        responseBetOn = betSlipAdapter.addBet(invalidBetSlip, expectedStatusCode);
        assertTrue(responseBetOn.getMessage().contains(responseMessage), "Invalid response");
    }
}

package tests.betSlip;

import models.BetSlip;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class AddBetSlipTest extends BaseTest {

    BetSlip betSlip;

    @Test(description = "Add bet", priority = 1)
    public void addBet() {
        betSlip = data.get("betSlip.json", BetSlip.class);
        betSlipAdapter.add(betSlip, 200);
    }

    @Test(description = "Add bet that was already added", priority = 2)
    public void addBetAlreadyAdded() {
        responseBetOn = betSlipAdapter.add(betSlip, 400);
        assertEquals(responseBetOn.getMessage(), "Pick already exists in betslip", "Invalid response");
    }

    @Test(description = "Delete bet", priority = 3)
    public void deleteBet() {
        betSlipAdapter.delete(betSlip, 200);
    }

    @Test(description = "Delete bet already deleted", priority = 4)
    public void deleteBetAlreadyDeleted() {
        responseBetOn = betSlipAdapter.delete(betSlip, 404);
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

    @Test(description = "Add bet. Not existing event id")
    public void addBetNotExistEventId() {
        betSlip = data.get("betSlip.json", BetSlip.class);
        betSlip.setEi("111");
        responseBetOn = betSlipAdapter.add(betSlip, 404);
        assertEquals(responseBetOn.getMessage(), "No live events found for that pick", "Invalid response");
    }

    @Test(description = "Add bet. Not existing event id")
    public void deleteBetNotExistEventId() {
        betSlip = data.get("betSlip.json", BetSlip.class);
        betSlip.setEi("111");
        responseBetOn = betSlipAdapter.delete(betSlip, 404);
        assertEquals(responseBetOn.getMessage(), "BetSlip not found", "Invalid response");
    }

    @Test(description = "Add bet. Event id is null")
    public void addBetNullEventId() {
        betSlip = data.get("betSlip.json", BetSlip.class);
        betSlip.setEi(null);
        responseBetOn = betSlipAdapter.add(betSlip, 400);
        assertEquals(responseBetOn.getMessage(), "Pick has wrong values", "Invalid response");
    }

    @Test(description = "Add bet. Not existing market id")
    public void addBetNotExistMarketId() {
        betSlip = data.get("betSlip.json", BetSlip.class);
        betSlip.setMi("qwe");
        responseBetOn = betSlipAdapter.add(betSlip, 404);
        assertEquals(responseBetOn.getMessage(), "No markets found matching that pick", "Invalid response");
    }

    @Test(description = "Add bet. Market id is null ")
    public void addBetNullMarketId() {
        betSlip = data.get("betSlip.json", BetSlip.class);
        betSlip.setMi(null);
        responseBetOn = betSlipAdapter.add(betSlip, 400);
        assertEquals(responseBetOn.getMessage(), "Pick has wrong values", "Invalid response");
    }

    @Test(description = "Add bet. Language is null")
    public void addBetNullLanguage() {
        betSlip = data.get("betSlip.json", BetSlip.class);
        betSlip.setLc(null);
        responseBetOn = betSlipAdapter.add(betSlip, 400);
        assertTrue(responseBetOn.getMessage().contains("field 'languageCode': rejected value [null]"), "Invalid response");
    }

    @Test(description = "Add bet. Not existing outcomes")
    public void addBetNotExistOutcomes() {
        betSlip = data.get("betSlip.json", BetSlip.class);
        betSlip.setOi(123456789);
        responseBetOn = betSlipAdapter.add(betSlip, 404);
        assertEquals(responseBetOn.getMessage(), "No outcomes found matching that pick", "Invalid response");
    }
    //TODO: more then 30 picks
}

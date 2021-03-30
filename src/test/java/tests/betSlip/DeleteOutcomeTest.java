package tests.betSlip;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.Bets;

public class DeleteOutcomeTest extends Bets {

    @BeforeMethod
    public void addBets() {
        for (int i = 0; i < numberOfBets; i++) {
            betSlipAdapter.addBet(bets[i], 200);
        }
    }

    @AfterMethod
    public void deleteBets() {
        betSlipAdapter.deleteAll(200);
    }

    @Test(description = "delete outcome from the top of the list")
    public void deleteOutcomeTop() {
        betSlipAdapter.deleteOutcome(bets[0].getEventId(), 1);
    }

    @Test(description = "delete outcome from the bottom of the list")
    public void deleteOutcomeBottom() {
        betSlipAdapter.deleteOutcome(bets[2].getEventId(), 3);
    }

    @Test(description = "delete outcome from the middle of the list")
    public void deleteOutcomeMiddle() {
        betSlipAdapter.deleteOutcome(bets[1].getEventId(), 2);
    }

    @Test(description = "delete all outcomes")
    public void deleteOutcomeAll() {
        betSlipAdapter.deleteOutcomeAll(bets);
        betSlipAdapter.addBet(bets[0], 200);
    }

    @Test(description = "delete outcome with invalid event id")
    public void deleteOutcomeInvalidEventId() {
        betSlipAdapter.deleteOutcome("1", 1, "Selection not found");
    }

    @Test(description = "delete outcome with invalid selection")
    public void deleteOutcomeInvalidSelection() {
        betSlipAdapter.deleteOutcome(bets[0].getEventId(), 2, "Selection not found");
    }

    @Test(description = "delete outcome without bets")
    public void deleteOutcomeNoBets() {
        betSlipAdapter.deleteAll(200);
        betSlipAdapter.deleteOutcome(bets[0].getEventId(), 1, "BetSlip not found");
        betSlipAdapter.addBet(bets[0], 200);
    }
}

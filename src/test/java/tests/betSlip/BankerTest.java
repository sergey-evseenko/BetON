package tests.betSlip;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class BankerTest extends BaseTest {

    @BeforeClass
    public void addBet() throws Exception {
        bet = getBets()[0];
        betSlipAdapter.addBet(bet, 200);
    }

    @AfterClass
    public void deleteBet() {
        betSlipAdapter.deleteBet(bet, 200);
    }

    @Test(description = "Add banker", priority = 1)
    public void addBanker() {
        betSlipAdapter.addBanker(bet.getEventId(), 200);
    }

    @Test(description = "Delete banker", priority = 2)
    public void deleteBanker() {
        betSlipAdapter.deleteBanker(bet.getEventId(), 200);
    }

    @Test(description = "Add banker. Not existing event id")
    public void addBankerNotExistEventId() {
        responseBetOn = betSlipAdapter.addBanker("111", 404);
        assertEquals(responseBetOn.getMessage(), "Event not found", "Invalid response");
    }

    @Test(description = "Delete banker. Not existing event id")
    public void deleteBankerNotExistEventId() {
        responseBetOn = betSlipAdapter.deleteBanker("111", 404);
        assertEquals(responseBetOn.getMessage(), "Event not found", "Invalid response");
    }
}

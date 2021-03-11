package tests.betSlip;

import models.BetSlip;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class BankerTest extends BaseTest {

    BetSlip betSlip;

    @BeforeClass
    public void addBet() {
        betSlip = data.get("betSlip.json", BetSlip.class);
        betSlipAdapter.addBet(betSlip, 200);
    }

    @AfterClass
    public void deleteBet() {
        betSlipAdapter.deleteBet(betSlip, 200);
    }

    @BeforeMethod
    public void getBet() {
        betSlip = data.get("betSlip.json", BetSlip.class);
    }

    @Test(description = "Add banker", priority = 1)
    public void addBanker() {
        betSlipAdapter.addBanker(betSlip, 200);
    }

    @Test(description = "Delete banker", priority = 2)
    public void deleteBanker() {
        betSlipAdapter.deleteBanker(betSlip, 200);
    }

    @Test(description = "Add banker. Not existing event id")
    public void addBankerNotExistEventId() {
        betSlip.setEi("111");
        responseBetOn = betSlipAdapter.addBanker(betSlip, 404);
        assertEquals(responseBetOn.getMessage(), "Event not found", "Invalid response");
    }

    @Test(description = "Delete banker. Not existing event id")
    public void deleteBankerNotExistEventId() {
        betSlip.setEi("111");
        responseBetOn = betSlipAdapter.deleteBanker(betSlip, 404);
        assertEquals(responseBetOn.getMessage(), "Event not found", "Invalid response");
    }

}

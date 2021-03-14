package tests.betSlip;

import adapters.SoonLiveWebSocket;
import models.BetSlip;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tests.BaseTest;
import utils.PropertyManager;

import java.net.URI;

import static org.testng.Assert.assertEquals;

public class BankerTest extends BaseTest {

    BetSlip betSlip;

    @BeforeClass
    public void addBet() throws Exception {
        String webSocketUrl = new PropertyManager().get("webSocketUrl");
        SoonLiveWebSocket webSocketClientEndpoint = new SoonLiveWebSocket(new URI(webSocketUrl));
        webSocketClientEndpoint.sendMessage("{\"desktop\":true,\"sportId\":1,\"langIso\":\"en\",\"soonType\":\"SOON\"}");
        Thread.sleep(1000);
        betSlip = webSocketClientEndpoint.getBetSlip();
        betSlipAdapter.addBet(betSlip, 200);
    }

    @AfterClass
    public void deleteBet() {
        betSlipAdapter.deleteBet(betSlip, 200);
    }

    @Test(description = "Add banker", priority = 1)
    public void addBanker() {
        betSlipAdapter.addBanker(betSlip.getEventId(), 200);
    }

    @Test(description = "Delete banker", priority = 2)
    public void deleteBanker() {
        betSlipAdapter.deleteBanker(betSlip.getEventId(), 200);
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

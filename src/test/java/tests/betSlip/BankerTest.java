package tests.betSlip;

import adapters.SoonLiveWebSocket;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tests.BaseTest;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.testng.Assert.assertEquals;

public class BankerTest extends BaseTest {

    @BeforeClass
    public void addBet() throws Exception {
        webSocketClientEndpoint = new SoonLiveWebSocket(new URI(webSocketUrl));
        webSocketRequest = new String(Files.readAllBytes(Paths.get("src/test/resources/data/webSocketRequest.json")));
        webSocketClientEndpoint.sendMessage(webSocketRequest);
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

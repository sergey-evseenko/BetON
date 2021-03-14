package adapters;

import com.jayway.jsonpath.JsonPath;
import models.BetSlip;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.ClientEndpoint;
import javax.websocket.DeploymentException;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;

@ClientEndpoint
public class SoonLiveWebSocket extends MainAdapter {
    private final Session session;
    BetSlip betSlip;

    public SoonLiveWebSocket(URI serverEndpointURI) throws IOException, DeploymentException {
        this.session = ClientManager.createClient().connectToServer(this, serverEndpointURI);
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
        String eventId = JsonPath.read(message, "ms[0].bId").toString();
        int marketId = JsonPath.read(message, "ms[0].mr.1.bId");
        int outcomeId = JsonPath.read(message, "ms[0].mr.1.oc[0].bid");
        betSlip = new BetSlip(marketId, eventId, "en", "1", outcomeId);
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public BetSlip getBetSlip() {
        return betSlip;
    }
}

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
    Boolean isFirstMessage = true;


    public SoonLiveWebSocket(URI serverEndpointURI) throws IOException, DeploymentException {
        this.session = ClientManager.createClient().connectToServer(this, serverEndpointURI);
        System.out.println("web socket connection was opened...");
    }

    @OnMessage
    public void onMessage(String message) {
        if (isFirstMessage) {
            String eventId = JsonPath.read(message, "ms[0].bId").toString();
            int betRadarId = JsonPath.read(message, "ms[0].mr.1.bId");
            int outcomeId = JsonPath.read(message, "ms[0].mr.1.oc[0].bid");
            betSlip = new BetSlip(betRadarId, eventId, "en", "1", outcomeId);
            isFirstMessage = false;
        }
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public BetSlip getBetSlip() {
        for (int i = 0; i < 100; i++) {
            if (betSlip == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("web socket connection was closed...");
                break;
            }
        }
        return betSlip;
    }
}

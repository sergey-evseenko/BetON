package adapters;

import com.jayway.jsonpath.JsonPath;
import models.Bet;
import org.glassfish.tyrus.client.ClientManager;
import utils.PropertyManager;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

@ClientEndpoint
public class SoonLiveWebSocket extends MainAdapter {
    private final Session session;
    int numberOfBets = 3;
    Bet[] bets = new Bet[numberOfBets];
    Boolean isFirstMessage = true;
    String webSocketUrl = new PropertyManager().get("webSocketUrl");
    String webSocketRequest = new String(Files.readAllBytes(Paths.get("src/test/resources/data/webSocketRequest.json")));


    public SoonLiveWebSocket() throws Exception {
        this.session = ClientManager.createClient().connectToServer(this, new URI(webSocketUrl));
        System.out.println("web socket connection was opened...");
    }

    @OnMessage
    public void onMessage(String message) {
        if (isFirstMessage) {
            for (int i = 0; i < numberOfBets; i++) {
                String eventId = JsonPath.read(message, "ms[" + i + "].bId").toString();
                int betRadarId = JsonPath.read(message, "ms[" + i + "].mr.1.bId");
                int outcomeId = JsonPath.read(message, "ms[" + i + "].mr.1.oc[0].bid");
                bets[i] = new Bet(betRadarId, eventId, "en", "1", outcomeId);
            }
            isFirstMessage = false;
        }
    }

    public void sendMessage() throws IOException {
        this.session.getBasicRemote().sendText(webSocketRequest);
    }

    public Bet[] getBets() {
        for (int i = 0; i < 100; i++) {
            if (bets[numberOfBets - 1] == null) {
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
        return bets;
    }
}

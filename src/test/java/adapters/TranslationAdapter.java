package adapters;

import io.restassured.http.ContentType;
import models.LiveMatch;
import models.Response;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class TranslationAdapter extends MainAdapter {
    String urlMatch = "sport-events/v1/translation/translateLiveMatch";
    String urlMatches = "sport-events/v1/translation/translateLiveMatches";


    public Response get(String eventBetradarId, String langIso, String translation, int expectedStatusCode) {
        if (eventBetradarId != null && langIso != null) {
            requestSpec = given()
                    .queryParam("eventBetradarId", eventBetradarId)
                    .queryParam("langIso", langIso);
        }
        if (eventBetradarId == null) {
            requestSpec = given()
                    .queryParam("langIso", langIso);
        }
        if (langIso == null) {
            requestSpec = given()
                    .queryParam("eventBetradarId", eventBetradarId);
        }
        response = get(urlMatch, requestSpec, expectedStatusCode);
        if (expectedStatusCode == 200) {
            assertEquals(response.path("sp.nm"), translation, "Invalid translation");
            assertEquals(response.path("bId").toString(), eventBetradarId, "Invalid betradar id");
            return null;
        } else {
            return gson.fromJson(response.asString().trim(), Response.class);
        }
    }

    public void post(LiveMatch liveMatch, int expectedStatusCode) {
        body = gson.toJson(liveMatch);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .body(body);
        response = post(urlMatches, requestSpec, expectedStatusCode);
        if (expectedStatusCode == 200) {
            assertEquals(response.path("sp.nm[0]"), "Fußball", "Invalid translation");
            assertEquals(response.path("bId[0]"), liveMatch.getEventBetradarId(), "Invalid betradar id");
        } else {
            assertEquals(response.asString().trim(), "[]", "Invalid betradar id error");
        }
    }
}

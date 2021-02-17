package adapters;

import models.Response;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class TranslationAdapter extends MainAdapter {
    String url = "sport-events/v1/translation/translateLiveMatch";

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
        response = get(url, requestSpec, expectedStatusCode);
        if (expectedStatusCode == 200) {
            assertEquals(response.path("sp.nm"), translation);
            assertEquals(response.path("bId").toString(), eventBetradarId);
            return null;
        } else {
            return gson.fromJson(response.asString().trim(), Response.class);
        }
    }
}

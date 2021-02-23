package adapters;

import models.ResponseBetOn;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class MatchDayAdapter extends MainAdapter {
    String url = "sport-events/v1/matchday/";

    public ResponseBetOn validate(String categoryId, String langIso, int expectedStatusCode) {
        requestSpec = given()
                .queryParam("categoryId", categoryId)
                .queryParam("langIso", langIso);
        response = get(url + "tornament", requestSpec, expectedStatusCode);
        if (expectedStatusCode == 400) {
            return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
        } else {
            assertEquals(response.asString().trim(), "[]", "Invalid response");
            return null;
        }
    }

    public void getMatchDay(String langIso) {
        requestSpec = given()
                .queryParam("categoryId", "7")
                .queryParam("langIso", langIso);
        response = get(url + "tornament", requestSpec, 200);
        assertEquals(response.path("nm[0]"), "Utr Pro Tennis Series France, Women", "Invali response");
    }

    public ResponseBetOn getMatchDay(String tournamentId, String roundId, String langIso, int expectedStatusCode) {
        if (langIso != null) {
            requestSpec = given()
                    .pathParam("tournamentId", tournamentId)
                    .pathParam("roundId", roundId)
                    .queryParam("langIso", langIso);
        } else {
            requestSpec = given()
                    .pathParam("tournamentId", tournamentId)
                    .pathParam("roundId", roundId);
        }
        response = get(url + "{tournamentId}/{roundId}", requestSpec, expectedStatusCode);
        if (expectedStatusCode == 400) {
            return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
        } else {
            return null;
        }

    }

}

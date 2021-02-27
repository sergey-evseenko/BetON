package adapters;

import models.Outright;
import models.ResponseBetOn;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class OutrightAdapter extends MainAdapter {
    String url = "sport-events/v1/outrights/competitors/";

    public Outright[] getOutrights(String outrightId, String langIso) {
        requestSpec = given()
                .pathParam("outrightId", outrightId)
                .queryParam("langIso", langIso);
        response = get(url + "{outrightId}", requestSpec, 200);
        return gson.fromJson(response.asString().trim(), Outright[].class);
    }

    public ResponseBetOn getOutrights(String outrightId, String langIso, int expectedStatusCode) {
        if (langIso != null) {
            requestSpec = given()
                    .pathParam("outrightId", outrightId)
                    .queryParam("langIso", langIso);
        } else {
            requestSpec = given()
                    .pathParam("outrightId", outrightId);
        }
        response = get(url + "{outrightId}", requestSpec, expectedStatusCode);
        if (expectedStatusCode == 400) {
            return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
        } else {
            assertEquals(response.asString().trim(), "[]", "unknown outrightId error");
            return null;
        }
    }
}

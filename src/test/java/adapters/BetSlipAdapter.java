package adapters;

import io.restassured.http.ContentType;
import models.BetSlip;
import models.ResponseBetOn;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class BetSlipAdapter extends MainAdapter {
    String url = "sport-events/v1/betslip/";

    public ResponseBetOn add(BetSlip betSlip, int expectedStatusCode) {
        String jsonPath = "ps." + betSlip.getEi();
        body = gson.toJson(betSlip);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .cookie("betSlipId", "7d40d329-f872-48a0-8fa5-94858f6ea79b")
                .body(body);
        response = post(url + "pick", requestSpec, expectedStatusCode);
        if (expectedStatusCode == 200) {
            assertEquals(response.path(jsonPath + ".ei[0]").toString(), betSlip.getEi());
            assertEquals(response.path(jsonPath + ".mi[0]"), betSlip.getMi());
            assertEquals(response.path(jsonPath + ".oi[0]"), betSlip.getOi());
            assertEquals(response.path(jsonPath + ".bmi[0]"), betSlip.getBmi());
            return null;
        } else {
            return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
        }

    }

    public ResponseBetOn delete(BetSlip betSlip, int expectedStatusCode) {
        body = gson.toJson(betSlip);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .cookie("betSlipId", "7d40d329-f872-48a0-8fa5-94858f6ea79b")
                .body(body);
        response = delete(url + "pick", requestSpec, expectedStatusCode);
        if (expectedStatusCode == 404) {
            return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
        } else {
            return null;
        }
    }

    public ResponseBetOn deleteAll(int expectedStatusCode) {
        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .cookie("betSlipId", "7d40d329-f872-48a0-8fa5-94858f6ea79b");
        response = delete(url + "clear", requestSpec, expectedStatusCode);
        if (expectedStatusCode == 404) {
            return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
        } else {
            return null;
        }
    }
}

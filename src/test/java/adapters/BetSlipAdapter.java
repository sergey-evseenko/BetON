package adapters;

import io.restassured.http.ContentType;
import models.BetSlip;
import models.ResponseBetOn;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class BetSlipAdapter extends MainAdapter {
    String url = "sport-events/v1/betslip/";
    String betSlipId = "7d40d329-f872-48a0-8fa5-94858f6ea79b";

    public ResponseBetOn addBet(BetSlip betSlip, int expectedStatusCode) {
        String jsonPath = "ps." + betSlip.getEi();
        body = gson.toJson(betSlip);
        requestSpec = given()
                .cookie("betSlipId", betSlipId)
                .contentType(ContentType.JSON)
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

    public ResponseBetOn addBanker(BetSlip betSlip, int expectedStatusCode) {
        requestSpec = given()
                .cookie("betSlipId", betSlipId);
        response = post(url + "banker/" + betSlip.getEi(), requestSpec, expectedStatusCode);
        if (expectedStatusCode == 200) {
            assertEquals(response.path("to.bts[0].bst.bnk"), 1, "Invalid response");
            assertEquals(response.path("to.evs[0].id").toString(), betSlip.getEi(), "Invalid response");
            return null;
        } else {
            return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
        }
    }

    public ResponseBetOn deleteBet(BetSlip betSlip, int expectedStatusCode) {
        body = gson.toJson(betSlip);
        requestSpec = given()
                .cookie("betSlipId", betSlipId)
                .contentType(ContentType.JSON)
                .body(body);
        response = delete(url + "pick", requestSpec, expectedStatusCode);
        if (expectedStatusCode == 404) {
            return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
        } else {
            return null;
        }
    }

    public ResponseBetOn deleteBanker(BetSlip betSlip, int expectedStatusCode) {
        body = gson.toJson(betSlip);
        requestSpec = given()
                .cookie("betSlipId", betSlipId);
        response = delete(url + "banker/" + betSlip.getEi(), requestSpec, expectedStatusCode);
        if (expectedStatusCode == 200) {
            assertEquals(response.path("to.bts[0].bst.bnk"), 0, "Invalid response");
            assertEquals(response.path("to.evs[0].id").toString(), betSlip.getEi(), "Invalid response");
            return null;
        } else {
            return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
        }
    }

    public ResponseBetOn deleteAll(int expectedStatusCode) {
        requestSpec = given()
                .cookie("betSlipId", betSlipId);
        response = delete(url + "clear", requestSpec, expectedStatusCode);
        if (expectedStatusCode == 404) {
            return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
        } else {
            return null;
        }
    }
}

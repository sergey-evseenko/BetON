package adapters;

import io.restassured.http.ContentType;
import models.BetSlip;
import models.ResponseBetOn;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class BetSlipAdapter extends MainAdapter {
    String url = "sport-events/v1/betslip/";

    public ResponseBetOn addBet(BetSlip betSlip, int expectedStatusCode) {
        String jsonPath = "ps." + betSlip.getEventId();
        body = gson.toJson(betSlip);
        requestSpec = given()
                .cookie("betSlipId", betSlipId)
                .contentType(ContentType.JSON)
                .body(body);
        response = post(url + "pick", requestSpec, expectedStatusCode);
        if (expectedStatusCode == 200) {
            assertEquals(response.path(jsonPath + ".ei[0]").toString(), betSlip.getEventId());
            assertEquals(response.path(jsonPath + ".mi[0]"), betSlip.getMarketId());
            assertEquals(response.path(jsonPath + ".oi[0]"), betSlip.getOutcomeId());
            assertEquals(response.path(jsonPath + ".bmi[0]"), betSlip.getBetRadarId());
            return null;
        } else {
            return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
        }
    }

    public ResponseBetOn addBanker(String eventId, int expectedStatusCode) {
        requestSpec = given()
                .cookie("betSlipId", betSlipId);
        response = post(url + "banker/" + eventId, requestSpec, expectedStatusCode);
        if (expectedStatusCode == 200) {
            assertEquals(response.path("to.bts[0].bst.bnk"), 1, "Invalid response");
            assertEquals(response.path("to.evs[0].id").toString(), eventId, "Invalid response");
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

    public ResponseBetOn deleteBanker(String eventId, int expectedStatusCode) {
        requestSpec = given()
                .cookie("betSlipId", betSlipId);
        response = delete(url + "banker/" + eventId, requestSpec, expectedStatusCode);
        if (expectedStatusCode == 200) {
            assertEquals(response.path("to.bts[0].bst.bnk"), 0, "Invalid response");
            assertEquals(response.path("to.evs[0].id").toString(), eventId, "Invalid response");
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

    public void addCombination(String path, String[] eventIds, int size, String type) {
        File file = new File(path);
        requestSpec = given()
                .cookie("betSlipId", betSlipId)
                .contentType(ContentType.JSON)
                .body(file);
        if (size != 0) {
            response = post(url + "combination", requestSpec, 200);
            assertEquals(response.path("selectionCount"), size, "invalid count");
            assertEquals(response.path("to.bt"), type, "invalid count");
            for (int i = 0; i < size; i++) {
                assertEquals(response.path(String.format("to.evs[%s].id", i)).toString(), eventIds[i], "invalid count");
            }
        } else {
            response = post(url + "combination", requestSpec, 404);
            assertEquals(response.path("message"), "BetSlip not found", "invalid response");
        }

    }

    public void deleteCombination(String path, String[] eventIds, int size, String type) {
        File file = new File(path);
        requestSpec = given()
                .cookie("betSlipId", betSlipId)
                .contentType(ContentType.JSON)
                .body(file);
        if (size != 0) {
            response = delete(url + "combination", requestSpec, 200);
            assertEquals(response.path("selectionCount"), size, "invalid count");
            assertEquals(response.path("to.bt"), type, "invalid count");
            for (int i = 0; i < size; i++) {
                assertEquals(response.path(String.format("to.evs[%s].id", i)).toString(), eventIds[i], "invalid count");
            }
        } else {
            response = delete(url + "combination", requestSpec, 404);
            assertEquals(response.path("message"), "BetSlip not found", "invalid response");
        }
    }
}

package adapters;

import io.restassured.http.ContentType;
import models.Bet;
import models.ResponseBetOn;
import models.Wager;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class BetSlipAdapter extends MainAdapter {
    String url = "sport-events/v1/betslip/";
    Bet[] responseBets;
    float maxOddActual, maxOddExpected;

    public ResponseBetOn addBet(Bet bet, int expectedStatusCode) {
        body = gson.toJson(bet);
        requestSpec = given()
                .cookie("betSlipId", betSlipId)
                .contentType(ContentType.JSON)
                .body(body);
        response = post(url + "pick", requestSpec, expectedStatusCode);
        if (expectedStatusCode == 200) {
            responseBets = gson.fromJson(response.path("ps." + bet.getEventId()).toString(), Bet[].class);
            responseBets[0].setLanguage("en");
            assertEquals(responseBets[0], bet, "Invalid response");
            return null;
        } else {
            return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
        }
    }

    public void addBets(Bet[] bets) {
        for (Bet bet : bets) {
            addBet(bet, 200);
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

    public ResponseBetOn deleteBet(Bet bet, int expectedStatusCode) {
        body = gson.toJson(bet);
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

    public void addCombination(String fileName, int expectedStatusCode) {
        File file = new File("src/test/resources/data/" + fileName);
        requestSpec = given()
                .cookie("betSlipId", betSlipId)
                .contentType(ContentType.JSON)
                .body(file);
        response = post(url + "combination", requestSpec, expectedStatusCode);
    }

    public void deleteCombination(String fileName, int expectedStatusCode) {
        File file = new File("src/test/resources/data/" + fileName);
        requestSpec = given()
                .cookie("betSlipId", betSlipId)
                .contentType(ContentType.JSON)
                .body(file);
        response = delete(url + "combination", requestSpec, expectedStatusCode);
    }

    public void validateCombinationResponse(Bet[] bets, int numberOfBets, String type, int expectedStatusCode) {
        if (expectedStatusCode == 200) {
            assertEquals(response.path("selectionCount"), numberOfBets, "invalid count");
            assertEquals(response.path("to.bt"), type, "invalid count");
            for (int i = 0; i < numberOfBets; i++) {
                assertEquals(response.path(String.format("to.evs[%s].id", i)).toString(), bets[i].getEventId(), "invalid count");
            }
        } else {
            assertEquals(response.path("message"), "BetSlip not found", "invalid response");
        }
    }

    public void getAll(Bet[] bets, int expectedStatusCode) {
        requestSpec = given()
                .cookie("betSlipId", betSlipId);
        response = get(url + "full", requestSpec, expectedStatusCode);
        if (expectedStatusCode == 200) {
            assertEquals(response.path("selectionCount"), 3, "invalid count");
            for (int i = 0; i < bets.length; i++) {
                assertEquals(response.path(String.format("to.evs[%s].id", i)).toString(), bets[i].getEventId(), "invalid count");
            }
        } else {
            assertEquals(response.path("message"), "BetSlip not found", "invalid response");
        }
    }

    public void getShort(Bet[] bets, int expectedStatusCode) {
        requestSpec = given()
                .cookie("betSlipId", betSlipId);
        response = get(url + "short", requestSpec, expectedStatusCode);
        if (expectedStatusCode == 200) {
            for (Bet bet : bets) {
                responseBets = gson.fromJson(response.path("ps." + bet.getEventId()).toString(), Bet[].class);
                responseBets[0].setLanguage("en");
                assertEquals(responseBets[0], bet, "Invalid response");
            }
        } else {
            assertEquals(response.path("message"), "BetSlip not found", "invalid response");
        }
    }

    public void changeWager(Wager wager, String combinationType, int expectedStatusCode, int numberOfBets) {
        int n;
        if (combinationType.equals("COMBINATION")) {
            n = 1;
        } else {
            n = numberOfBets;
        }

        body = gson.toJson(wager, Wager.class);
        requestSpec = given()
                .cookie("betSlipId", betSlipId)
                .contentType(ContentType.JSON)
                .body(body);
        response = put(url + "wager", requestSpec, expectedStatusCode);
        assertEquals(response.path("to.bt"), combinationType, "invalid count");
        if (wager.getWagerType().equals("T")) {
            assertEquals(response.path("to.wa"), wager.getWager(), "invalid total wager");
            float betWager = response.path("to.bw");
            assertEquals((int) betWager, wager.getWager() / n, "invalid bet wager");
        } else {
            float totalWager = response.path("to.wa");
            assertEquals((int) totalWager, wager.getWager() * n, "invalid total wager");
            assertEquals(response.path("to.bw"), wager.getWager(), "invalid bet wager");
        }
    }

    public void changeWager(Wager wager, int expectedStatusCode) {
        body = gson.toJson(wager, Wager.class);
        requestSpec = given()
                .cookie("betSlipId", betSlipId)
                .contentType(ContentType.JSON)
                .body(body);
        response = put(url + "wager", requestSpec, expectedStatusCode);
    }

    public void deleteOutcome(String eventId, int section) {
        requestSpec = given()
                .cookie("betSlipId", betSlipId);
        response = delete(url + "event/" + eventId + "/selection/" + section, requestSpec, 200);
        assertEquals(response.path("selectionCount"), 2, "invalid number of events");
        assertFalse(response.asString().contains(eventId), "event wasn't removed");
    }

    public void deleteOutcome(String eventId, int section, String message) {
        requestSpec = given()
                .cookie("betSlipId", betSlipId);
        response = delete(url + "event/" + eventId + "/selection/" + section, requestSpec, 404);
        assertEquals(response.path("message"), message, "invalid response");
    }

    public void deleteOutcomeAll(Bet[] bets) {
        for (int i = 0; i < 3; i++) {
            response = delete(url + "event/" + bets[i].getEventId() + "/selection/1", requestSpec, 200);
        }
        assertTrue(response.asString().isEmpty(), "invalid response");
    }

    public float[] getRates(int numberOfBets, int expectedStatusCode) {
        float[] rates = new float[numberOfBets];
        requestSpec = given()
                .cookie("betSlipId", betSlipId);
        response = get(url + "full", requestSpec, expectedStatusCode);
        for (int i = 0; i < numberOfBets; i++) {
            rates[i] = response.path("to.evs[" + i + "].sl.sel." + (i + 1) + ".oc.odd.fval");
        }
        return rates;
    }

    public void validateMaxOddSingle(float[] rates) {
        maxOddActual = response.path("to.mo");
        maxOddExpected = rates[0] + rates[1] + rates[2];
        assertEquals((int) maxOddActual * 100, (int) maxOddExpected * 100, "invalid maxOdd");
    }

    public void validateMaxOddCombi(float[] rates) {
        maxOddActual = response.path("to.mo");
        maxOddExpected = rates[0] * rates[1] * rates[2];
        assertEquals((int) maxOddActual * 100, (int) maxOddExpected * 100, "invalid maxOdd");
    }

    public void validateMaxOddSystem(float[] rates) {
        maxOddActual = response.path("to.mo");
        maxOddExpected = rates[0] * rates[1] + rates[0] * rates[2] + rates[1] * rates[2];
        assertEquals((int) maxOddActual * 100, (int) maxOddExpected * 100, "invalid maxOdd");
    }
}

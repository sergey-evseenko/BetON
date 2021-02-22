package adapters;

import io.restassured.http.ContentType;
import models.OptionsList;
import models.ResponseBetOn;

import static io.restassured.RestAssured.given;
import static tests.BaseTest.token;

public class PlayerAdapter extends MainAdapter {
    String url = "player/v1/players/";

    public ResponseBetOn putLanguage(String language, int expectedStatusCode) {
        requestSpec = given()
                .header("Authorization", "Bearer " + token)
                .header("lang", language);
        response = put(url + "lang", requestSpec, expectedStatusCode);
        return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
    }

    public String getLanguage() {
        requestSpec = given()
                .header("Authorization", "Bearer " + token);
        response = get(url + "lang", requestSpec, 200);
        return response.asString();
    }

    public ResponseBetOn putLanguageNoHeader() {
        requestSpec = given()
                .header("Authorization", "Bearer " + token);
        response = put(url + "lang", requestSpec, 400);
        return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
    }

    public ResponseBetOn putOptionsList(OptionsList optionsList, int expectedStatusCode) {
        body = gson.toJson(optionsList);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body);
        response = put(url + "options", requestSpec, expectedStatusCode);
        if (expectedStatusCode == 400) {
            ResponseBetOn[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBetOn[].class);
            return responseBodyErrors[0];
        } else {
            return null;
        }
    }

    public ResponseBetOn putOptionListUnauthorized(OptionsList optionsList) {
        body = gson.toJson(optionsList);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .body(body);
        response = put(url + "options", requestSpec, 401);
        return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
    }

    public OptionsList getOptionsList() {
        requestSpec = given()
                .header("Authorization", "Bearer " + token);
        response = get(url + "options", requestSpec, 200);
        return gson.fromJson(response.asString().trim(), OptionsList.class);
    }

    public ResponseBetOn getOptionListUnauthorized() {
        response = get(url + "options", 401);
        return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
    }

    public void putPPMO(String ppmo, int responseCode) {
        requestSpec = given()
                .header("Authorization", "Bearer " + token);
        put(url + "ppmo?ppmo=" + ppmo, requestSpec, responseCode);
    }

    public ResponseBetOn getPPMO() {
        requestSpec = given()
                .header("Authorization", "Bearer " + token);
        response = get(url + "ppmo", requestSpec, 200);
        return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
    }

}

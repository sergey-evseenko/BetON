package adapters;

import io.restassured.http.ContentType;
import models.OptionsList;
import models.ResponseBody;

import static io.restassured.RestAssured.given;

public class PlayerAdapter extends MainAdapter {
    String url = "player/v1/players/";

    public ResponseBody putLanguage(String token, String language, int expectedStatusCode) {
        requestSpec = given()
                .header("Authorization", "Bearer " + token)
                .header("lang", language);
        response = put(url + "lang", requestSpec, expectedStatusCode);
        return gson.fromJson(response.asString().trim(), ResponseBody.class);
    }

    public String getLanguage(String token) {
        requestSpec = given()
                .header("Authorization", "Bearer " + token);
        response = get(url + "lang", requestSpec, 200);
        return response.asString();
    }

    public ResponseBody putLanguageNoHeader(String token) {
        requestSpec = given()
                .header("Authorization", "Bearer " + token);
        response = put(url + "lang", requestSpec, 400);
        return gson.fromJson(response.asString().trim(), ResponseBody.class);
    }

    public ResponseBody putOptionsList(OptionsList optionsList, int expectedStatusCode, String token) {
        body = gson.toJson(optionsList);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body);
        response = put(url + "options", requestSpec, expectedStatusCode);
        if (expectedStatusCode == 400) {
            ResponseBody[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBody[].class);
            return responseBodyErrors[0];
        } else {
            return null;
        }
    }

    public ResponseBody putOptionListUnauthorized(OptionsList optionsList) {
        body = gson.toJson(optionsList);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .body(body);
        response = put(url + "options", requestSpec, 401);
        return gson.fromJson(response.asString().trim(), ResponseBody.class);
    }

    public OptionsList getOptionsList(String token) {
        requestSpec = given()
                .header("Authorization", "Bearer " + token);
        response = get(url + "options", requestSpec, 200);
        return gson.fromJson(response.asString().trim(), OptionsList.class);
    }

    public ResponseBody getOptionListUnauthorized() {
        response = get(url + "options", 401);
        return gson.fromJson(response.asString().trim(), ResponseBody.class);
    }

    public void putPPMO(String token, String ppmo, int responseCode) {
        requestSpec = given()
                .header("Authorization", "Bearer " + token);
        put(url + "ppmo?ppmo=" + ppmo, requestSpec, responseCode);
    }

    public ResponseBody getPPMO(String token) {
        requestSpec = given()
                .header("Authorization", "Bearer " + token);
        response = get(url + "ppmo", requestSpec, 200);
        return gson.fromJson(response.asString().trim(), ResponseBody.class);
    }

}

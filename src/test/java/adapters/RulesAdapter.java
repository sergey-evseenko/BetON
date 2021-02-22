package adapters;

import io.restassured.http.ContentType;
import models.ResponseBetOn;
import models.UserForCheck;

import static io.restassured.RestAssured.given;

public class RulesAdapter extends MainAdapter {
    String url = "rule/v1/rules/";

    public ResponseBetOn validate(String path, String param, String paramValue) {
        requestSpec = given()
                .queryParam(param, paramValue);
        response = get(url + "validate/register/" + path, requestSpec, 200);
        return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
    }

    public ResponseBetOn checkUserPost(UserForCheck userForCheck) {
        body = gson.toJson(userForCheck);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .body(body);
        response = post(url + "validate/register/user", requestSpec, 200);
        return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
    }

    public ResponseBetOn checkUserPut(UserForCheck userForCheck) {
        body = gson.toJson(userForCheck);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .body(body);
        response = put(url + "validate/update/user", requestSpec, 200);
        return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
    }

    public String getProviders(String countryCode) {
        requestSpec = given()
                .queryParam("code", countryCode);
        response = get(url + "bp/country", requestSpec, 200);
        return response.asString().trim();
    }
}

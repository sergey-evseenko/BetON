package adapters;

import models.ResponseBetOn;

import static io.restassured.RestAssured.given;
import static tests.BaseTest.token;

public class TemplateAdapter extends MainAdapter {
    String url = "bp/v1/bp-integrator/template/registration";

    public ResponseBetOn getTemplateUrl(int providerId, int expectedStatusCode) {
        requestSpec = given()
                .header("Authorization", "Bearer " + token)
                .queryParam("providerId", providerId);
        response = get(url, requestSpec, expectedStatusCode);
        return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
    }

    public ResponseBetOn getTemplateUrl(int expectedStatusCode) {
        requestSpec = given()
                .header("Authorization", "Bearer " + token);
        response = get(url, requestSpec, expectedStatusCode);
        return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
    }
}

package adapters;

import models.ResponseBody;

import static io.restassured.RestAssured.given;

public class TemplateAdapter extends MainAdapter {
    String url = "bp/v1/bp-integrator/template/registration";

    public ResponseBody getTemplateUrl(String token, int providerId, int expectedStatusCode) {
        requestSpec = given()
                .header("Authorization", "Bearer " + token)
                .queryParam("providerId", providerId);
        response = get(url, requestSpec, expectedStatusCode);
        return gson.fromJson(response.asString().trim(), ResponseBody.class);
    }

    public ResponseBody getTemplateUrl(String token, int expectedStatusCode) {
        requestSpec = given()
                .header("Authorization", "Bearer " + token);
        response = get(url, requestSpec, expectedStatusCode);
        return gson.fromJson(response.asString().trim(), ResponseBody.class);
    }
}

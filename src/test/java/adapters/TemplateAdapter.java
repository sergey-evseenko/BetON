package adapters;

import models.Response;

import static io.restassured.RestAssured.given;

public class TemplateAdapter extends MainAdapter {
    String url = "bp/v1/bp-integrator/template/registration";

    public Response getTemplateUrl(String token, int providerId, int expectedStatusCode) {
        requestSpec = given()
                .header("Authorization", "Bearer " + token)
                .queryParam("providerId", providerId);
        response = get(url, requestSpec, expectedStatusCode);
        return gson.fromJson(response.asString().trim(), Response.class);
    }

    public Response getTemplateUrl(String token, int expectedStatusCode) {
        requestSpec = given()
                .header("Authorization", "Bearer " + token);
        response = get(url, requestSpec, expectedStatusCode);
        return gson.fromJson(response.asString().trim(), Response.class);
    }
}

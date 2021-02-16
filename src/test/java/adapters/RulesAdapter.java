package adapters;

import io.restassured.http.ContentType;
import models.Response;
import models.UserForCheck;

import static io.restassured.RestAssured.given;

public class RulesAdapter extends MainAdapter {
    String urlRegister = "rule/v1/rules/validate/register/";
    String urlUpdate = "rule/v1/rules/validate/update/user";

    public Response get(String path, String param, String paramValue) {
        requestSpec = given()
                .queryParam(param, paramValue);
        response = get(urlRegister + path, requestSpec, 200);
        return gson.fromJson(response.asString().trim(), Response.class);
    }

    public Response checkUserPost(UserForCheck userForCheck) {
        body = gson.toJson(userForCheck);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .body(body);
        response = post(urlRegister + "user", requestSpec, 200);
        return gson.fromJson(response.asString().trim(), Response.class);
    }

    public Response checkUserPut(UserForCheck userForCheck) {
        body = gson.toJson(userForCheck);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .body(body);
        response = put(urlUpdate, requestSpec, 200);
        return gson.fromJson(response.asString().trim(), Response.class);
    }
}

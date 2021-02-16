package adapters;

import io.restassured.http.ContentType;
import models.*;

import static io.restassured.RestAssured.given;

public class UserAdapter extends MainAdapter {
    String url = "auth/v1/users";

    public Response confirmEmail(String code) {
        response = get(url + "/confirm/email/" + code, 400);
        Response[] responseBodyErrors = gson.fromJson(response.asString().trim(), Response[].class);
        return responseBodyErrors[0];
    }

    public UserInfo getUserInfoWithValidToken(String token) {
        requestSpec = given()
                .header("Authorization", "Bearer " + token);
        response = get(url + "/me", requestSpec, 200);
        return gson.fromJson(response.asString().trim(), UserInfo.class);
    }

    public Response getUserInfoWithExpiredToken(String token) {
        requestSpec = given()
                .header("Authorization", "Bearer " + token);
        response = get(url + "/me", requestSpec, 401);
        return gson.fromJson(response.asString().trim(), Response.class);
    }

    public Response getInfoWithoutToken() {
        response = get(url + "/me", 401);
        return gson.fromJson(response.asString().trim(), Response.class);
    }

    public UserInfo put(User user, String token) {
        body = gson.toJson(user);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body);
        response = put(url, requestSpec, 200);
        return gson.fromJson(response.asString().trim(), UserInfo.class);
    }

    public Response putInvalidData(User user, String token) {
        body = gson.toJson(user);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body);
        response = put(url, requestSpec, 400);
        Response[] responseBodyErrors = gson.fromJson(response.asString().trim(), Response[].class);
        return responseBodyErrors[0];
    }

    public Response putInvalidBirthDate(User user, String token) {
        body = gson.toJson(user);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body);
        response = put(url, requestSpec, 400);
        return gson.fromJson(response.asString().trim(), Response.class);
    }

    public Response putEmail(Email email, String token, int responseCode) {
        body = gson.toJson(email);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body);
        response = put(url + "/update-email", requestSpec, responseCode);
        if (responseCode == 400) {
            Response[] responseBodyErrors = gson.fromJson(response.asString().trim(), Response[].class);
            return responseBodyErrors[0];
        } else {
            return null;
        }
    }

    public Response putPassword(Password password, String token, int responseCode) {
        body = gson.toJson(password);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body);
        response = put(url + "/update-pwd", requestSpec, responseCode);
        if (responseCode == 400) {
            Response[] responseBodyErrors = gson.fromJson(response.asString().trim(), Response[].class);
            return responseBodyErrors[0];
        } else {
            return null;
        }
    }

    public String checkPassword(String password, String token) {
        body = String.format("{\"password\" : \"%s\"}", password);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body);
        response = post(url + "/check-pwd", requestSpec, 200);
        return response.asString();
    }

    public Response checkNullPassword() {
        requestSpec = given()
                .contentType(ContentType.JSON)
                .body("{}");
        response = post(url + "/check-pwd", requestSpec, 400);
        Response[] responseBodyErrors = gson.fromJson(response.asString().trim(), Response[].class);
        return responseBodyErrors[0];
    }

    public Response putSecurityOption(SecurityOption securityOption, String token, int responseCode) {
        body = gson.toJson(securityOption);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body);
        response = put(url + "/security", requestSpec, responseCode);
        if (responseCode == 400) {
            Response[] responseBodyErrors = gson.fromJson(response.asString().trim(), Response[].class);
            return responseBodyErrors[0];
        } else {
            return null;
        }
    }

    public SecurityOption getSecurityOption(String token) {
        requestSpec = given()
                .header("Authorization", "Bearer " + token);
        response = get(url + "/security", requestSpec, 200);
        return gson.fromJson(response.asString().trim(), SecurityOption.class);
    }
}

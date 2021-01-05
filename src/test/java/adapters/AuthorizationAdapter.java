package adapters;

import io.restassured.http.ContentType;
import models.Response;
import models.User;

import static io.restassured.RestAssured.given;

public class AuthorizationAdapter extends MainAdapter {

    io.restassured.response.Response response;

    public Response post(String refreshToken) {
        response = given()
                .formParam("grant_type", "refresh_token")
                .formParam("client_id", "clientId")
                .formParam("client_secret", "secret")
                .formParam("refresh_token", refreshToken)
                .when()
                .post("auth/v1/oauth/token/beton")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON).extract().response();

        return gson.fromJson(response.asString().trim(), Response.class);
    }

    public Response post(User user) {
        response = given()
                .formParam("username", user.getUserName())
                .formParam("password", user.getPassword())
                .formParam("grant_type", "password")
                .formParam("response_type", "token")
                .formParam("client_id", "clientId")
                .formParam("client_secret", "secret")
                .when()
                .post("auth/v1/oauth/token/beton")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON).extract().response();

        return gson.fromJson(response.asString().trim(), Response.class);
    }

    public Response[] post(String userName, String password, int errorCode) {
        response = given()
                .formParam("username", userName)
                .formParam("password", password)
                .formParam("grant_type", "password")
                .formParam("response_type", "token")
                .formParam("client_id", "clientId")
                .formParam("client_secret", "secret")
                .when()
                .post("auth/v1/oauth/token/beton")
                .then()
                .statusCode(errorCode)
                .contentType(ContentType.JSON).extract().response();

        return gson.fromJson(response.asString().trim(), Response[].class);
    }

    public Response post(String clientID, String clientSecret, User user) {
        response = given()
                .formParam("username", user.getUserName())
                .formParam("password", user.getPassword())
                .formParam("grant_type", "password")
                .formParam("response_type", "token")
                .formParam("client_id", clientID)
                .formParam("client_secret", clientSecret)
                .when()
                .post("auth/v1/oauth/token/beton")
                .then()
                .statusCode(401)
                .contentType(ContentType.JSON).extract().response();

        return gson.fromJson(response.asString().trim(), Response.class);
    }

}

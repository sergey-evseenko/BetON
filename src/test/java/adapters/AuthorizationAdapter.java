package adapters;

import models.Response;
import models.User;

import static io.restassured.RestAssured.given;

public class AuthorizationAdapter extends MainAdapter {
    String url = "auth/v1/oauth/token/beton";

    public Response post(String refreshToken) {
        requestSpec = given()
                .formParam("grant_type", "refresh_token")
                .formParam("client_id", "clientId")
                .formParam("client_secret", "secret")
                .formParam("refresh_token", refreshToken);
        response = post(url, requestSpec, 200);
        return gson.fromJson(response.asString().trim(), Response.class);
    }

    public Response post(User user, String clientID, String clientSecret, int expectedStatusCode) {
        requestSpec = given()
                .formParam("username", user.getUserName())
                .formParam("password", user.getPassword())
                .formParam("grant_type", "password")
                .formParam("response_type", "token")
                .formParam("client_id", clientID)
                .formParam("client_secret", clientSecret);
        response = post(url, requestSpec, expectedStatusCode);
        return gson.fromJson(response.asString().trim(), Response.class);
    }

    public Response post(String userName, String password, int expectedStatusCode) {
        requestSpec = given()
                .formParam("username", userName)
                .formParam("password", password)
                .formParam("grant_type", "password")
                .formParam("response_type", "token")
                .formParam("client_id", "clientId")
                .formParam("client_secret", "secret");
        response = post(url, requestSpec, expectedStatusCode);
        Response[] responseBodyErrors = gson.fromJson(response.asString().trim(), Response[].class);
        return responseBodyErrors[0];
    }
}

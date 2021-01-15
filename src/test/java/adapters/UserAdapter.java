package adapters;

import io.restassured.http.ContentType;
import models.ResponseBody;
import models.User;
import models.UserInfo;

import static io.restassured.RestAssured.given;

public class UserAdapter extends MainAdapter {

    public UserInfo get(String token) {
        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("auth/v1/users/me")
                .then()
                .statusCode(200)
                .extract().response();

        return gson.fromJson(response.asString().trim(), UserInfo.class);
    }

    public ResponseBody getUserInfoWithExpiredToken(String token) {
        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("auth/v1/users/me")
                .then()
                .statusCode(401)
                .extract().response();

        return gson.fromJson(response.asString().trim(), ResponseBody.class);
    }

    public ResponseBody getInfoWithoutToken() {
        response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("auth/v1/users/me")
                .then()
                .statusCode(401)
                .extract().response();

        return gson.fromJson(response.asString().trim(), ResponseBody.class);
    }

    public UserInfo put(User user, String token) {
        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(user))
                .log().all()
                .when()
                .put("auth/v1/users")
                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON).extract().response();

        return gson.fromJson(response.asString().trim(), UserInfo.class);
    }
}

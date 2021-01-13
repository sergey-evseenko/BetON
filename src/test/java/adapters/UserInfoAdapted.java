package adapters;

import io.restassured.http.ContentType;
import models.ResponseBody;
import models.UserInfo;

import static io.restassured.RestAssured.given;

public class UserInfoAdapted extends MainAdapter {

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
}

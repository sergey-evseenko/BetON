package adapters;

import models.ResponseBody;
import models.UserInfo;


public class UserAdapter extends MainAdapter {

    public UserInfo getUserInfoWithValidToken(String token) {
        response = getWithToken("auth/v1/users/me", 200, token);
        return gson.fromJson(response.asString().trim(), UserInfo.class);
    }

    public ResponseBody getUserInfoWithExpiredToken(String token) {
        response = getWithToken("auth/v1/users/me", 401, token);
        return gson.fromJson(response.asString().trim(), ResponseBody.class);
    }

    public ResponseBody getInfoWithoutToken() {
        response = get("auth/v1/users/me", 401);
        return gson.fromJson(response.asString().trim(), ResponseBody.class);
    }

    /*
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
     */
}

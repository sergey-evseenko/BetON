package adapters;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.ResponseBody;
import models.User;

import static io.restassured.RestAssured.given;


public class RegistrationAdapter extends MainAdapter {

    Response response;

    public ResponseBody post(User user, int statusCode) {
        response = given()
                .contentType(ContentType.JSON)
                .header("lang", "en")
                .body(gson.toJson(user))
                .when()
                .post("auth/v1/users/register")
                .then()
                .statusCode(statusCode)
                .contentType(ContentType.JSON).extract().response();

        if (statusCode == 200) {
            return gson.fromJson(response.asString().trim(), ResponseBody.class);
        } else {
            ResponseBody[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBody[].class);
            return responseBodyErrors[0];
        }
    }

}

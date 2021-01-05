package adapters;

import io.restassured.http.ContentType;
import models.Response;
import models.User;

import static io.restassured.RestAssured.given;

public class RegistrationAdapter extends MainAdapter {

    io.restassured.response.Response response;

    public Response post(User user, int statusCode) {
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
            return gson.fromJson(response.asString().trim(), Response.class);
        } else {
            Response[] responseErrors = gson.fromJson(response.asString().trim(), Response[].class);
            return responseErrors[0];
        }
    }

}

package adapters;

import io.restassured.http.ContentType;
import models.ResponseBody;

import static io.restassured.RestAssured.given;

public class ConfirmRegistrationAdapter extends MainAdapter {


    public ResponseBody get(String registrationCode, int responseCode) {
        response =
                given()
                        .when()
                        .get("auth/v1/users/confirm/registration/" + registrationCode)
                        .then()
                        .statusCode(responseCode)
                        .contentType(ContentType.JSON).extract().response();
        ResponseBody[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBody[].class);
        return responseBodyErrors[0];
    }
}

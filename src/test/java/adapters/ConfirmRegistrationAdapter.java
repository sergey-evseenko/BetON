package adapters;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.ResponseBody;

import static io.restassured.RestAssured.given;

public class ConfirmRegistrationAdapter extends MainAdapter {


    public ResponseBody[] get(String registrationCode, int responseCode) {
        Response response =
                given()
                        //.pathParam("registrationCode", registrationCode)
                        .when()
                        .log().all()
                        //.get("auth/v1/users/confirm/registration/{registrationCode}")
                        .get("auth/v1/users/confirm/registration/" + registrationCode)
                        .then()
                        .log().body()
                        .statusCode(responseCode)
                        .contentType(ContentType.JSON).extract().response();
        return gson.fromJson(response.asString().trim(), ResponseBody[].class);
    }
}

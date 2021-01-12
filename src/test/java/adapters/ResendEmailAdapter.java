package adapters;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.ResendEmailData;
import models.ResponseBody;

import static io.restassured.RestAssured.given;

public class ResendEmailAdapter extends MainAdapter {
    Response response;

    public ResponseBody post(ResendEmailData resendEmailData, String token, int responseCode) {
        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(resendEmailData))
                .when()
                .post("auth/v1/users/confirm/registration/resend")
                .then()
                .statusCode(responseCode)
                .extract().response();

        if (responseCode == 200) {
            return null;
        } else {
            ResponseBody[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBody[].class);
            return responseBodyErrors[0];
        }
    }

    public ResponseBody get(String externalId, String token, int responseCode) {
        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .pathParam("externalId", externalId)
                .when()
                .get("auth/v1/users/confirm/registration/resend/{externalId}")
                .then()
                .statusCode(responseCode)
                .extract().response();

        if (responseCode == 200) {
            return null;
        } else {
            ResponseBody[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBody[].class);
            return responseBodyErrors[0];
        }
    }
}

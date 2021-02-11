package adapters;

import io.restassured.http.ContentType;
import models.ResendEmailData;
import models.ResponseBody;

import static io.restassured.RestAssured.given;

public class ResendEmailAdapter extends MainAdapter {
    String url = "auth/v1/users/confirm/registration/resend";

    public ResponseBody post(ResendEmailData resendEmailData, String token, int expectedStatusCode) {
        body = gson.toJson(resendEmailData);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body);
        response = post(url, requestSpec, expectedStatusCode);
        if (expectedStatusCode == 200) {
            return null;
        } else {
            ResponseBody[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBody[].class);
            return responseBodyErrors[0];
        }
    }

    public ResponseBody get(String externalId, String token, int expectedStatusCode) {
        requestSpec = given()
                .header("Authorization", "Bearer " + token)
                .pathParam("externalId", externalId);
        response = get(url + "/{externalId}", requestSpec, expectedStatusCode);
        if (expectedStatusCode == 200) {
            return null;
        } else {
            ResponseBody[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBody[].class);
            return responseBodyErrors[0];
        }
    }
}

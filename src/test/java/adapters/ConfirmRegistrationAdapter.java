package adapters;

import models.ResponseBetOn;

import static io.restassured.RestAssured.given;

public class ConfirmRegistrationAdapter extends MainAdapter {
    String url = "auth/v1/users/confirm/registration/";

    public ResponseBetOn getConfirmRegistration(String registrationCode, int expectedStatusCode) {
        requestSpec = given();
        response = get(url + registrationCode, requestSpec, expectedStatusCode);
        ResponseBetOn[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBetOn[].class);
        return responseBodyErrors[0];
    }
}

package adapters;

import models.ResponseBetOn;

public class ConfirmRegistrationAdapter extends MainAdapter {
    String url = "auth/v1/users/confirm/registration/";

    public ResponseBetOn getConfirmRegistration(String registrationCode, int expectedStatusCode) {
        response = get(url + registrationCode, expectedStatusCode);
        ResponseBetOn[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBetOn[].class);
        return responseBodyErrors[0];
    }
}

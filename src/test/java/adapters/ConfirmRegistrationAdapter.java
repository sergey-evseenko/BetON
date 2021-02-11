package adapters;

import models.ResponseBody;

public class ConfirmRegistrationAdapter extends MainAdapter {
    String url = "auth/v1/users/confirm/registration/";

    public ResponseBody getConfirmRegistration(String registrationCode, int expectedStatusCode) {
        response = get(url + registrationCode, expectedStatusCode);
        ResponseBody[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBody[].class);
        return responseBodyErrors[0];
    }
}

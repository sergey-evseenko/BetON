package adapters;

import models.Response;

public class ConfirmRegistrationAdapter extends MainAdapter {
    String url = "auth/v1/users/confirm/registration/";

    public Response getConfirmRegistration(String registrationCode, int expectedStatusCode) {
        response = get(url + registrationCode, expectedStatusCode);
        Response[] responseBodyErrors = gson.fromJson(response.asString().trim(), Response[].class);
        return responseBodyErrors[0];
    }
}

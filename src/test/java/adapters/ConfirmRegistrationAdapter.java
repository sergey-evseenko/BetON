package adapters;

import models.ResponseBody;

public class ConfirmRegistrationAdapter extends MainAdapter {

    public ResponseBody getConfirmRegistration(String registrationCode, int responseCode) {
        response = get("auth/v1/users/confirm/registration/" + registrationCode, responseCode);
        ResponseBody[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBody[].class);
        return responseBodyErrors[0];
    }
}

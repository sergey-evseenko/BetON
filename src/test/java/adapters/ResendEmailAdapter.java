package adapters;

import models.ResendEmailData;
import models.ResponseBody;

public class ResendEmailAdapter extends MainAdapter {

    public ResponseBody post(ResendEmailData resendEmailData, String token, int responseCode) {
        response = postWithHeader("auth/v1/users/confirm/registration/resend", responseCode, gson.toJson(resendEmailData), "Authorization", "Bearer " + token);
        if (responseCode == 200) {
            return null;
        } else {
            ResponseBody[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBody[].class);
            return responseBodyErrors[0];
        }
    }

    public ResponseBody get(String externalId, String token, int responseCode) {
        response = getWithTokenAndPathParam("auth/v1/users/confirm/registration/resend/{externalId}", responseCode, token, "externalId", externalId);
        if (responseCode == 200) {
            return null;
        } else {
            ResponseBody[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBody[].class);
            return responseBodyErrors[0];
        }
    }
}

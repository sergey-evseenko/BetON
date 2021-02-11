package adapters;

import models.ResponseBody;
import models.User;

public class RegistrationAdapter extends MainAdapter {

    public ResponseBody post(User user, int statusCode) {
        body = gson.toJson(user);
        response = postWithHeader("auth/v1/users/register", statusCode, body, "lang", "en");
        if (statusCode == 200) {
            return gson.fromJson(response.asString().trim(), ResponseBody.class);
        } else {
            ResponseBody[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBody[].class);
            return responseBodyErrors[0];
        }
    }

}

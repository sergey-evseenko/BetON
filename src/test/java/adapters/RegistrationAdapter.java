package adapters;

import models.ResponseBody;
import models.User;

public class RegistrationAdapter extends MainAdapter {

    public ResponseBody post(User user, int statusCode) {
        response = postWithHeader("auth/v1/users/register", statusCode, gson.toJson(user), "lang", "en");
        if (statusCode == 200) {
            return gson.fromJson(response.asString().trim(), ResponseBody.class);
        } else {
            ResponseBody[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBody[].class);
            return responseBodyErrors[0];
        }
    }

}

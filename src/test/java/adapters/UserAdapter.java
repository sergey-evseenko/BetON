package adapters;

import models.*;

public class UserAdapter extends MainAdapter {

    String url = "auth/v1/users";

    public UserInfo getUserInfoWithValidToken(String token) {
        response = getWithToken(url + "/me", 200, token);
        return gson.fromJson(response.asString().trim(), UserInfo.class);
    }

    public ResponseBody getUserInfoWithExpiredToken(String token) {
        response = getWithToken(url + "/me", 401, token);
        return gson.fromJson(response.asString().trim(), ResponseBody.class);
    }

    public ResponseBody getInfoWithoutToken() {
        response = get(url + "/me", 401);
        return gson.fromJson(response.asString().trim(), ResponseBody.class);
    }

    public UserInfo put(User user, String token) {
        body = gson.toJson(user);
        response = put(url, token, body, 200);
        return gson.fromJson(response.asString().trim(), UserInfo.class);
    }

    public ResponseBody putInvalidData(User user, String token) {
        body = gson.toJson(user);
        response = put(url, token, body, 400);
        ResponseBody[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBody[].class);
        return responseBodyErrors[0];
    }

    public ResponseBody putInvalidBirthDate(User user, String token) {
        body = gson.toJson(user);
        response = put(url, token, body, 400);
        return gson.fromJson(response.asString().trim(), ResponseBody.class);
    }

    public ResponseBody putEmail(Email email, String token, int responseCode) {
        body = gson.toJson(email);
        response = put(url + "/update-email", token, body, responseCode);
        if (responseCode == 400) {
            ResponseBody[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBody[].class);
            return responseBodyErrors[0];
        } else {
            return null;
        }
    }

    public ResponseBody putPassword(Password password, String token, int responseCode) {
        body = gson.toJson(password);
        response = put(url + "/update-pwd", token, body, responseCode);
        if (responseCode == 400) {
            ResponseBody[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBody[].class);
            return responseBodyErrors[0];
        } else {
            return null;
        }
    }
}

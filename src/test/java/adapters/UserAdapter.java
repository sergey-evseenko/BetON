package adapters;

import models.*;

public class UserAdapter extends MainAdapter {

    String url = "auth/v1/users";

    public ResponseBody confirmEmail(String code) {
        response = get(url + "/confirm/email/" + code, 400);
        ResponseBody[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBody[].class);
        return responseBodyErrors[0];
    }

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

    public String checkPassword(String password, String token) {
        body = String.format("{\"password\" : \"%s\"}", password);
        response = postWithToken(url + "/check-pwd", 200, body, token);
        return response.asString();
    }

    public ResponseBody checkNullPassword() {
        response = post(url + "/check-pwd", 400, "{}");
        ResponseBody[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBody[].class);
        return responseBodyErrors[0];
    }

    public ResponseBody putSecurityOption(SecurityOption securityOption, String token, int responseCode) {
        body = gson.toJson(securityOption);
        response = put(url + "/security", token, body, responseCode);
        if (responseCode == 400) {
            ResponseBody[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBody[].class);
            return responseBodyErrors[0];
        } else {
            return null;
        }
    }

    public SecurityOption getSecurityOption(String token) {
        response = getWithToken(url + "/security", 200, token);
        return gson.fromJson(response.asString().trim(), SecurityOption.class);
    }


}

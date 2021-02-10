package adapters;

import models.ResponseBody;

public class PlayerAdapter extends MainAdapter {

    String url = "player/v1/players/lang";

    public ResponseBody changeLanguage(String token, String language, int responseCode) {
        response = putWithHeader(url, token, "", responseCode, "lang", language);
        return gson.fromJson(response.asString().trim(), ResponseBody.class);
    }

    public String getLanguage(String token) {
        response = getWithToken(url, 200, token);
        return response.asString();
    }

    public ResponseBody changeLanguageNoHeader(String token) {
        response = putWithToken(url, token, "", 400);
        return gson.fromJson(response.asString().trim(), ResponseBody.class);
    }
}

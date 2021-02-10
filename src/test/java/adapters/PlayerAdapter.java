package adapters;

import models.OptionsList;
import models.ResponseBody;

public class PlayerAdapter extends MainAdapter {

    String url = "player/v1/players/";

    public ResponseBody changeLanguage(String token, String language, int responseCode) {
        response = putWithHeader(url + "lang", token, "", responseCode, "lang", language);
        return gson.fromJson(response.asString().trim(), ResponseBody.class);
    }

    public String getLanguage(String token) {
        response = getWithToken(url + "lang", 200, token);
        return response.asString();
    }

    public ResponseBody changeLanguageNoHeader(String token) {
        response = putWithToken(url + "lang", token, "", 400);
        return gson.fromJson(response.asString().trim(), ResponseBody.class);
    }

    public ResponseBody putOptionsList(OptionsList optionsList, int responseCode, String token) {
        body = gson.toJson(optionsList);
        response = putWithToken(url + "options", token, body, responseCode);
        if (responseCode == 400) {
            ResponseBody[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBody[].class);
            return responseBodyErrors[0];
        } else {
            return null;
        }
    }

    public ResponseBody putOptionListUnauthorized(OptionsList optionsList) {
        body = gson.toJson(optionsList);
        response = putWithoutToken(url + "options", body, 401);
        return gson.fromJson(response.asString().trim(), ResponseBody.class);
    }

    public OptionsList getOptionsList(String token) {
        response = getWithToken(url + "options", 200, token);
        return gson.fromJson(response.asString().trim(), OptionsList.class);
    }

    public ResponseBody getOptionListUnauthorized() {
        response = get(url + "options", 401);
        return gson.fromJson(response.asString().trim(), ResponseBody.class);
    }

}

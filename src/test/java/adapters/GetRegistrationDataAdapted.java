package adapters;

import models.RegistrationDataAll;
import models.RegistrationDataEN;
import models.RegistrationDataRU;

public class GetRegistrationDataAdapted extends MainAdapter {

    public RegistrationDataEN getRegistrationDataEN() {
        response = getWithQueryParam("content/v1/registration/data", 200, "lng", "en");
        return gson.fromJson(response.asString().trim(), RegistrationDataEN.class);
    }

    public RegistrationDataRU getRegistrationDataRU() {
        response = getWithQueryParam("content/v1/registration/data", 200, "lng", "ru");
        return gson.fromJson(response.asString().trim(), RegistrationDataRU.class);
    }

    public RegistrationDataAll getRegistrationDataAll(String langParam) {
        response = getWithQueryParam("content/v1/registration/data", 200, "lng", langParam);
        return gson.fromJson(response.asString().trim(), RegistrationDataAll.class);
    }
}

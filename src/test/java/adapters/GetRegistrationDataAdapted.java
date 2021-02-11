package adapters;

import models.RegistrationDataAll;
import models.RegistrationDataEN;
import models.RegistrationDataRU;

import static io.restassured.RestAssured.given;

public class GetRegistrationDataAdapted extends MainAdapter {
    String url = "content/v1/registration/data";

    public RegistrationDataEN getRegistrationDataEN() {
        requestSpec = given()
                .queryParam("lng", "en");
        response = get(url, requestSpec, 200);
        return gson.fromJson(response.asString().trim(), RegistrationDataEN.class);
    }

    public RegistrationDataRU getRegistrationDataRU() {
        requestSpec = given()
                .queryParam("lng", "ru");
        response = get(url, requestSpec, 200);
        return gson.fromJson(response.asString().trim(), RegistrationDataRU.class);
    }

    public RegistrationDataAll getRegistrationDataAll(String langParam) {
        requestSpec = given()
                .queryParam("lng", langParam);
        response = get(url, requestSpec, 200);
        return gson.fromJson(response.asString().trim(), RegistrationDataAll.class);
    }
}

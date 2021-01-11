package adapters;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.RegistrationDataAll;
import models.RegistrationDataEN;
import models.RegistrationDataRU;

import static io.restassured.RestAssured.given;

public class GetRegistrationDataAdapted extends MainAdapter {

    public RegistrationDataEN getRegistrationDataEN() {
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .queryParam("lng", "en")
                        .when()
                        .get("content/v1/registration/data")
                        .then()
                        .statusCode(200)
                        .extract().response();

        return gson.fromJson(response.asString().trim(), RegistrationDataEN.class);
    }

    public RegistrationDataRU getRegistrationDataRU() {
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .queryParam("lng", "ru")
                        .when()
                        .get("content/v1/registration/data")
                        .then()
                        .statusCode(200)
                        .extract().response();

        return gson.fromJson(response.asString().trim(), RegistrationDataRU.class);
    }

    public RegistrationDataAll getRegistrationDataAll(String langParam) {
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .queryParam("lng", langParam)
                        .log().all()
                        .when()
                        .get("content/v1/registration/data")
                        .then()
                        .statusCode(200)
                        .extract().response();

        return gson.fromJson(response.asString().trim(), RegistrationDataAll.class);
    }
}

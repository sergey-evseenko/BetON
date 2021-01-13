package adapters;

import models.AddressForRegistration;

import static io.restassured.RestAssured.given;

public class SearchRegistrationDataAdapter extends MainAdapter {

    public AddressForRegistration[] post(int resultCount, String countryCode, String postCode, String city, String street) {

        String path = "content/v1/registration/search?";
        Boolean noParams = true;

        if (resultCount != 0) {
            path = path + "resultCount=" + resultCount + "&";
            noParams = false;
        }
        if (countryCode != null) {
            path = path + "countryCode=" + countryCode + "&";
            noParams = false;
        }
        if (postCode != null) {
            path = path + "postcode=" + postCode + "&";
            noParams = false;
        }
        if (city != null) {
            path = path + "city=" + city + "&";
            noParams = false;
        }
        if (street != null) {
            path = path + "street=" + street + "&";
            noParams = false;
        }
        if (noParams) {
            path = "content/v1/registration/search";
        }

        response = given()
                .when()
                .post(path)
                .then()
                .statusCode(200)
                .extract().response();

        return gson.fromJson(response.asString().trim(), AddressForRegistration[].class);


    }
}

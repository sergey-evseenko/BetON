package adapters;

import models.ResponseBetOn;
import models.Sport;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class CatalogAdapter extends MainAdapter {
    String url = "sport-events/v1/catalogs/";
    String urlSports = "sport-events/v1/catalogs/allSports";
    String urlCategories = "sport-events/v1/catalogs/allCategories";
    String urlTournaments = "sport-events/v1/catalogs/tournament";

    public Sport[] getSports(String langIso) {
        requestSpec = given()
                .queryParam("langIso", langIso);
        response = get(url + "allSports", requestSpec, 200);
        return gson.fromJson(response.asString().trim(), Sport[].class);
    }

    public ResponseBetOn getSports(String langIso, int expectedStatusCode) {
        requestSpec = given()
                .queryParam("langIso", langIso);
        response = get(url + "allSports", requestSpec, expectedStatusCode);
        if (expectedStatusCode == 200) {
            assertEquals(response.path("nm[0]"), "Soccer", "Invalid response");
            return null;
        }
        return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
    }

}

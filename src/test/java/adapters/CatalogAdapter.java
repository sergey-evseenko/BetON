package adapters;

import models.Category;
import models.ResponseBetOn;
import models.Sport;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class CatalogAdapter extends MainAdapter {
    String url = "sport-events/v1/catalogs/";
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

    public Category[] getCategories(String langIso, String sportId) {
        requestSpec = given()
                .queryParam("langIso", langIso)
                .queryParam("sportId", sportId);

        response = get(url + "allCategories", requestSpec, 200);
        return gson.fromJson(response.asString().trim(), Category[].class);
    }

    public ResponseBetOn getCategories(String langIso, String sportId, int expectedStatusCode) {
        if (langIso != null & sportId != null)
            requestSpec = given()
                    .queryParam("langIso", langIso)
                    .queryParam("sportId", sportId);
        if (sportId == null)
            requestSpec = given()
                    .queryParam("langIso", langIso);
        if (langIso == null)
            requestSpec = given()
                    .queryParam("sportId", sportId);

        response = get(url + "allCategories", requestSpec, expectedStatusCode);
        if (expectedStatusCode == 200) {
            assertEquals(response.path("sp.nm[0]"), "Soccer", "Invalid response");
            return null;
        }
        return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
    }

}

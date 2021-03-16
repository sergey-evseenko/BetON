package adapters;

import models.Category;
import models.ResponseBetOn;
import models.Sport;
import models.Tournament;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class CatalogAdapter extends MainAdapter {
    String url = "sport-events/v1/catalogs/";

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

    public Tournament[] getTournaments(String langIso, String categoryId) {
        requestSpec = given()
                .queryParam("langIso", langIso)
                .queryParam("categoryId", categoryId);

        response = get(url + "tournament", requestSpec, 200);
        return gson.fromJson(response.asString().trim(), Tournament[].class);
    }

    public ResponseBetOn getTournaments(String langIso, String categoryId, int expectedStatusCode) {
        if (langIso != null & categoryId != null)
            requestSpec = given()
                    .queryParam("langIso", langIso)
                    .queryParam("categoryId", categoryId);
        if (categoryId == null)
            requestSpec = given()
                    .queryParam("langIso", langIso);
        if (langIso == null)
            requestSpec = given()
                    .queryParam("categoryId", categoryId);

        response = get(url + "tournament", requestSpec, expectedStatusCode);
        if (expectedStatusCode == 200) {
            assertEquals(response.path("ct.nm[0]"), "International", "Invalid response");
            return null;
        }
        return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
    }

}

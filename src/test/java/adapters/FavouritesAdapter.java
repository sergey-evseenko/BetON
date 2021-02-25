package adapters;

import io.restassured.http.ContentType;
import models.FavouriteEvent;
import models.ResponseBetOn;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class FavouritesAdapter extends MainAdapter {
    String url = "sport-events/v1/favourite/events/";

    public ResponseBetOn post(FavouriteEvent favouriteEvent, int expectedStatusCode) {
        body = gson.toJson(favouriteEvent);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .body(body);
        response = post(url, requestSpec, expectedStatusCode);
        if (expectedStatusCode == 200) {
            assertEquals(response.asString(), "", "Invalid response");
            return null;
        } else {
            return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
        }

    }

    public void delete(FavouriteEvent favouriteEvent, int expectedStatusCode) {
        requestSpec = given()
                .contentType(ContentType.JSON);
        String deleteUrl = url + favouriteEvent.getEventId() + "/users/" + favouriteEvent.getUserId();
        response = delete(deleteUrl, requestSpec, expectedStatusCode);
        assertEquals(response.asString(), "", "Invalid response");
    }
}

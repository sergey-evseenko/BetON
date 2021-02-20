package tests.sportEvents;

import adapters.FavouritesAdapter;
import models.FavouriteEvent;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class FavouritesTest extends BaseTest {

    FavouriteEvent favouriteEvent;

    @Test(description = "add and remove favourite event", invocationCount = 2)
    public void addAndRemoveEvent() {
        favouriteEvent = data.get("favouriteEvent.json", FavouriteEvent.class);
        //add event
        new FavouritesAdapter().post(favouriteEvent, 200);
        //add event that was already added
        response = new FavouritesAdapter().post(favouriteEvent, 400);
        assertEquals(response.getMessage(), "User's favorite event already exists.", "Invalid response");
        //remove event
        new FavouritesAdapter().delete(favouriteEvent, 200);
    }


    @Test(description = "add favourite event without userId")
    public void addEventNoUserId() {
        favouriteEvent = data.get("favouriteEvent.json", FavouriteEvent.class);
        favouriteEvent.setEventId(null);
        response = new FavouritesAdapter().post(favouriteEvent, 400);
        assertEquals(response.getError(), "Bad Request", "Invalid response");
    }

    @Test(description = "add favourite event without eventId")
    public void addEventNoEventId() {
        favouriteEvent = data.get("favouriteEvent.json", FavouriteEvent.class);
        favouriteEvent.setEventId(null);
        response = new FavouritesAdapter().post(favouriteEvent, 400);
        assertEquals(response.getError(), "Bad Request", "Invalid response");
    }
}

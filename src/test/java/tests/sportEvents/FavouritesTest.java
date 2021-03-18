package tests.sportEvents;

import models.FavouriteEvent;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class FavouritesTest extends BaseTest {

    FavouriteEvent favouriteEvent;
    String eventId;
    String userId;

    @BeforeClass
    public void getUserIdEventId() throws Exception {
        userId = userAdapter.getUserId();
        eventId = getBetSlip(0).getEventId();
    }

    @Test(description = "add favourite event", priority = 1)
    public void addEvent() {
        favouriteEvent = new FavouriteEvent(eventId, userId);
        favouritesAdapter.post(favouriteEvent, 200);
    }

    @Test(description = "add already added favourite event", priority = 2)
    public void addEventAlreadyAdded() {
        responseBetOn = favouritesAdapter.post(favouriteEvent, 400);
        assertEquals(responseBetOn.getMessage(), "User's favorite event already exists.", "Invalid response");
    }

    @Test(description = "delete favourite event", priority = 3)
    public void deleteEvent() {
        favouritesAdapter.delete(favouriteEvent, 200);
    }


    @Test(description = "add favourite event without userId")
    public void addEventNoUserId() {
        favouriteEvent = new FavouriteEvent();
        favouriteEvent.setEventId(eventId);
        responseBetOn = favouritesAdapter.post(favouriteEvent, 400);
        assertEquals(responseBetOn.getError(), "Bad Request", "Invalid response");
    }

    @Test(description = "add favourite event without eventId")
    public void addEventNoEventId() {
        favouriteEvent = new FavouriteEvent();
        favouriteEvent.setUserId(userId);
        responseBetOn = favouritesAdapter.post(favouriteEvent, 400);
        assertEquals(responseBetOn.getError(), "Bad Request", "Invalid response");
    }
}

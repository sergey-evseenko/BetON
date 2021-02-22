package tests.sportEvents;

import adapters.FavouritesAdapter;
import adapters.UserAdapter;
import models.FavouriteEvent;
import models.UserInfo;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class FavouritesTest extends BaseTest {

    FavouriteEvent favouriteEvent;
    String eventId = "22350223";
    String userId;

    @BeforeClass
    public void getUserId() {
        UserInfo userInfo = new UserAdapter().getUserInfoWithValidToken();
        userId = String.valueOf(userInfo.getId());
    }

    @Test(description = "add and remove favourite event", invocationCount = 2)
    public void addAndRemoveEvent() {
        favouriteEvent = new FavouriteEvent(userId, eventId);
        //add event
        new FavouritesAdapter().post(favouriteEvent, 200);
        //add event that was already added
        responseBetOn = new FavouritesAdapter().post(favouriteEvent, 400);
        assertEquals(responseBetOn.getMessage(), "User's favorite event already exists.", "Invalid response");
        //remove event
        new FavouritesAdapter().delete(favouriteEvent, 200);
    }


    @Test(description = "add favourite event without userId")
    public void addEventNoUserId() {
        favouriteEvent = new FavouriteEvent();
        favouriteEvent.setEventId(eventId);
        responseBetOn = new FavouritesAdapter().post(favouriteEvent, 400);
        assertEquals(responseBetOn.getError(), "Bad Request", "Invalid response");
    }

    @Test(description = "add favourite event without eventId")
    public void addEventNoEventId() {
        favouriteEvent = new FavouriteEvent();
        favouriteEvent.setUserId(userId);
        responseBetOn = new FavouritesAdapter().post(favouriteEvent, 400);
        assertEquals(responseBetOn.getError(), "Bad Request", "Invalid response");
    }
}

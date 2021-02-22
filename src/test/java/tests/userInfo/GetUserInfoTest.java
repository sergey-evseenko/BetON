package tests.userInfo;

import adapters.UserAdapter;
import models.UserInfo;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class GetUserInfoTest extends BaseTest {

    @Test(description = "get user info with valid token")
    public void getUserInfoWithValidToken() {
        UserInfo expectedUserInfo = data.get("userInfo.json", UserInfo.class);
        UserInfo actualUserInfo = new UserAdapter().getUserInfoWithValidToken();
        assertEquals(actualUserInfo, expectedUserInfo, "invalid user info");
    }

    @Test(description = "get user info with expired token")
    public void getUserInfoWithExpiredToken() {
        responseBetOn = new UserAdapter().getUserInfoWithExpiredToken();
        assertEquals(responseBetOn.getError(), "invalid_token", "invalid error");
    }

    @Test(description = "get user info without token")
    public void getUserInfoWithoutToken() {
        responseBetOn = new UserAdapter().getInfoWithoutToken();
        assertEquals(responseBetOn.getError(), "unauthorized", "invalid error");
    }
}

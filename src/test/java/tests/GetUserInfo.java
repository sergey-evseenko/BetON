package tests;

import adapters.AuthorizationAdapter;
import adapters.UserInfoAdapted;
import models.User;
import models.UserInfo;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.testng.Assert.assertEquals;

public class GetUserInfo extends BaseTest {

    public GetUserInfo() throws FileNotFoundException {
    }

    @Test(description = "get user info with valid token")
    public void getUserInfoWithValidToken() throws FileNotFoundException {
        UserInfo expectedUserInfo, actualUserInfo;
        String token;

        user = gson.fromJson(new FileReader("src/test/resources/Data/userForLogin.json"), User.class);
        responseBody = new AuthorizationAdapter().post(user);
        token = responseBody.getAccessToken();

        actualUserInfo = gson.fromJson(new FileReader("src/test/resources/Data/userInfo.json"), UserInfo.class);
        expectedUserInfo = new UserInfoAdapted().get(token);
        assertEquals(actualUserInfo, expectedUserInfo, "invalid user info");
    }

    @Test(description = "get user info with expired token")
    public void getUserInfoWithExpiredToken() {
        String expiredToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJxd2VydHk4Nzk1MiIsInNjb3BlIjpbInJlYWQsd3JpdGUiXSwiZXh0ZXJuYWxJZCI6OTAwLCJleHAiOjE1ODg2ODE3MTUsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiI2Y2Y5MDkwNC1kOTY3LTRjMWItOGIyYi0zZDJhODY0NDIzNGQiLCJjbGllbnRfaWQiOiJjbGllbnRJZCJ9.Au631x0A3nop9VrQLziKpHO48nJX9493p4Jczwyuy2RqmoHu2DhwY0O-odyXcUjdHxZMti8ewqpf_HuhO7OpAnJa_zZz64X83e9dTzNeGwcKjINJEnl49-an6Ev1xqgvXUYtGSMDhfPOegqBA7GkmZcOW5XG45OJS89YvM4z_f254YPiKL94FCmfomB3CKEQNzHRID0kjxpVb1_iM8Q_sn6fKGh7pTHXatd_MhPa6f3nSg5zwkEPkXc7bBNBE1TNyDvgLocP_sC2TMX3rOrLg5HIhN7UmG7jdNgdv6qzS05b5VXkg10az0u3tIJHHBCnZHXqOpdetbjoP7K4HGHHFQ";
        responseBody = new UserInfoAdapted().getUserInfoWithExpiredToken(expiredToken);
        assertEquals(responseBody.getError(), "invalid_token", "invalid error");
    }

    @Test(description = "get user info without token")
    public void getUserInfoWithoutToken() {
        responseBody = new UserInfoAdapted().getInfoWithoutToken();
        assertEquals(responseBody.getError(), "unauthorized", "invalid error");
    }

}

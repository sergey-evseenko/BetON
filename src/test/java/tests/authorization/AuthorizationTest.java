package tests.authorization;

import adapters.AuthorizationAdapter;
import models.User;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class AuthorizationTest extends BaseTest {

    User user = data.get("userForLogin.json", User.class);

    @Test(description = "Login with valid login/pass")
    public void validLogin() {
        responseBody = new AuthorizationAdapter().post(user);
        assertEquals(responseBody.getEmail(), user.getEmail(), "Invalid email");
        assertNotEquals(responseBody.getAccessToken(), null, "Invalid access token");
    }

    @Test(description = "Login with refresh token", dependsOnMethods = "validLogin")
    public void validLoginWithRefreshToken() {
        responseBody = new AuthorizationAdapter().post(responseBody.getRefreshToken());
        assertEquals(responseBody.getEmail(), user.getEmail(), "Invalid email");
        assertNotEquals(responseBody.getAccessToken(), null, "Invalid access token");
    }

    @DataProvider(name = "List of invalid username/pass")
    public Object[][] invalidCredentials() {
        return new Object[][]{
                //invalid username
                {"pollyisthebestqa11", "Nextnext!1", "ER2002", "Bad credentials", 404},
                //username: is null
                {null, "Nextnext!1", "ER2002", "Bad credentials", 404},
                //username: empty field
                {"", "Qwerty!123", "ER2002", "Bad credentials", 404},
                //invalid password
                {"pollyisthebestqa", "Nextnext!11", "ER2002", "Bad credentials", 401},
                //password: is null
                {"pollyisthebestqa", null, "ER2002", "Bad credentials", 401},
                //password: empty field
                {"pollyisthebestqa", "", "ER2002", "Bad credentials", 401},
                //blocked user
                {"qwerty8291", "Qwerty!12311", "ER2003", "User has been blocked", 401},
                //unconfirmed registration
                {"qwerty46856", "Qwerty!123", "ER2001", "You must confirm your registration. Check your email", 401}
        };
    }

    @Test(description = "Login with invalid username/pass", dataProvider = "List of invalid username/pass")
    public void invalidLoginWithCredentials(String userName, String password, String code, String description, int responseCode) {
        responseBody = new AuthorizationAdapter().post(userName, password, responseCode);
        assertEquals(responseBody.getType(), "AUTHENTICATION", "Invalid type");
        assertEquals(responseBody.getCode(), code, "Invalid code");
        assertEquals(responseBody.getDescription(), description, "Invalid description");
        //for avoid blocking user
        if (userName == user.getUserName()) {
            return;
        }
        new AuthorizationAdapter().post(user);
    }

    @DataProvider(name = "List of invalid clientId/clientSecret")
    public Object[][] invalidFormData() {
        return new Object[][]{
                {"backOfficeClientId", "secret", "Bad client credentials"},
                {"clientId", "backOfficeSecret", "Bad client credentials"},
                {"backOfficeClientId", "backOfficeSecret", "Wrong clientId for this url"}
        };
    }

    @Test(description = "Login with invalid clientId/clientSecret", dataProvider = "List of invalid clientId/clientSecret")
    public void invalidLoginWithFormData(String clientID, String clientSecret, String errorDescription) {
        responseBody = new AuthorizationAdapter().post(clientID, clientSecret, user);
        assertEquals(responseBody.getError(), "invalid_client", "Invalid error message");
        assertEquals(responseBody.getErrorDescription(), errorDescription, "Invalid error description");
    }
}

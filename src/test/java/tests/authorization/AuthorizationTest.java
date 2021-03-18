package tests.authorization;

import models.User;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AuthorizationTest extends BaseTest {

    User user = data.get("userForLogin.json", User.class);

    @Test(description = "Login with valid login/pass")
    public void login() {
        responseBetOn = authorizationAdapter.post(user, "clientId", "secret", 200);
        assertEquals(responseBetOn.getEmail(), user.getEmail(), "Invalid email");
        assertTrue(StringUtils.isNotEmpty(responseBetOn.getAccessToken()), "Missed access token");
    }

    @Test(description = "Login with refresh token")
    public void loginWithRefreshToken() {
        responseBetOn = authorizationAdapter.post(user, "clientId", "secret", 200);
        responseBetOn = authorizationAdapter.post(responseBetOn.getRefreshToken());
        assertEquals(responseBetOn.getEmail(), user.getEmail(), "Invalid email");
        assertTrue(StringUtils.isNotEmpty(responseBetOn.getAccessToken()), "Missed access token");
    }

    @DataProvider(name = "List of invalid username/pass")
    public Object[][] invalidCredentials() {
        return new Object[][]{
                //invalid username
                {"pollyisthebestqa11", "Qwerty!123", "ER2002", "Bad credentials", 404},
                //username: is null
                {null, "Qwerty!123", "ER2002", "Bad credentials", 404},
                //username: empty field
                {"", "Qwerty!123", "ER2002", "Bad credentials", 404},
                //invalid password
                {"pollyisthebestqa", "Qwerty!1231", "ER2002", "Bad credentials", 401},
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

    @Test(description = "validate username/pass", dataProvider = "List of invalid username/pass")
    public void validateCredentials(String userName, String password, String code, String description, int responseCode) {
        responseBetOn = authorizationAdapter.post(userName, password, responseCode);
        assertEquals(responseBetOn.getType(), "AUTHENTICATION", "Invalid type");
        assertEquals(responseBetOn.getCode(), code, "Invalid code");
        assertEquals(responseBetOn.getDescription(), description, "Invalid description");
        //for avoid blocking user
        if (userName == user.getUserName()) {
            return;
        }
        authorizationAdapter.post(user, "clientId", "secret", 200);
    }

    @DataProvider(name = "List of invalid clientId/clientSecret")
    public Object[][] invalidFormData() {
        return new Object[][]{
                {"backOfficeClientId", "secret", "Bad client credentials"},
                {"clientId", "backOfficeSecret", "Bad client credentials"},
                {"backOfficeClientId", "backOfficeSecret", "Wrong clientId for this url"}
        };
    }

    @Test(description = "validate clientId/clientSecret", dataProvider = "List of invalid clientId/clientSecret")
    public void validateFormData(String clientID, String clientSecret, String errorDescription) {
        responseBetOn = authorizationAdapter.post(user, clientID, clientSecret, 401);
        assertEquals(responseBetOn.getError(), "invalid_client", "Invalid error message");
        assertEquals(responseBetOn.getErrorDescription(), errorDescription, "Invalid error description");
    }
}

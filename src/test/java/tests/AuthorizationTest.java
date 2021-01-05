package tests;

import adapters.AuthorizationAdapter;
import models.Response;
import models.User;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class AuthorizationTest extends BaseTest {

    Response response;
    User user = gson.fromJson(new FileReader("src/test/resources/Data/userForLogin.json"), User.class);

    public AuthorizationTest() throws FileNotFoundException {
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

    @DataProvider(name = "List of invalid clientId/clientSecret")
    public Object[][] invalidFormData() {
        return new Object[][]{
                {"backOfficeClientId", "secret", "Bad client credentials"},
                {"clientId", "backOfficeSecret", "Bad client credentials"},
                {"backOfficeClientId", "backOfficeSecret", "Wrong clientId for this url"}
        };
    }

    @Test(description = "Login with valid login/pass")
    public void validLogin() {
        response = new AuthorizationAdapter().post(user);
        assertEquals(response.getEmail(), user.getEmail(), "Invalid email");
        assertNotEquals(response.getAccessToken(), null, "Invalid access token");
    }

    @Test(description = "Login with refresh token", dependsOnMethods = "validLogin")
    public void validLoginWithRefreshToken() {
        response = new AuthorizationAdapter().post(response.getRefreshToken());
        assertEquals(response.getEmail(), user.getEmail(), "Invalid email");
        assertNotEquals(response.getAccessToken(), null, "Invalid access token");
    }

    @Test(description = "Login with invalid username/pass", dataProvider = "List of invalid username/pass")
    public void invalidLoginWithCredentials(String userName, String password, String code, String description, int errorCode) {
        Response[] response = new AuthorizationAdapter().post(userName, password, errorCode);
        assertEquals(response[0].getType(), "AUTHENTICATION", "Invalid type");
        assertEquals(response[0].getCode(), code, "Invalid code");
        assertEquals(response[0].getDescription(), description, "Invalid description");
        //for avoid blocking user
        if (userName == user.getUserName()) {
            new AuthorizationAdapter().post(user);
        }
    }

    @Test(description = "Login with invalid clientId/clientSecret", dataProvider = "List of invalid clientId/clientSecret")
    public void invalidLoginWithFormData(String clientID, String clientSecret, String errorDescription) {
        response = new AuthorizationAdapter().post(clientID, clientSecret, user);
        assertEquals(response.getError(), "invalid_client", "Invalid error message");
        assertEquals(response.getErrorDescription(), errorDescription, "Invalid error description");

    }

}

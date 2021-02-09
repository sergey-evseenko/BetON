package tests.userInfo;

import adapters.UserAdapter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class CheckPassword extends BaseTest {

    @DataProvider(name = "Passwords")
    public Object[][] passwords() {
        return new Object[][]{
                //valid password
                {"Qwerty!123", "", "true"},
                //invalid password
                {"Qwerty!1231111", "", "false"},
                //null
                {null, "ER0004", "Field is mandatory"}
        };
    }

    @Test(description = "check password", dataProvider = "Passwords")
    public void checkPasswordValidFormat(String password, String code, String description) {
        if (password != null) {
            String responseMessage = new UserAdapter().checkPassword(password, token);
            assertEquals(responseMessage, description, "Checking password error");
        } else {
            responseBody = new UserAdapter().checkNullPassword();
            assertEquals(responseBody.getField(), "password", "Invalid field");
            assertEquals(responseBody.getType(), "VALIDATION", "Invalid type");
            assertEquals(responseBody.getCode(), code, "Invalid code");
            assertEquals(responseBody.getDescription(), description, "Invalid description");
        }
    }

}

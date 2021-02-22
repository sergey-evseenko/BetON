package tests.userInfo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class CheckPasswordTest extends BaseTest {

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
    public void checkPassword(String password, String code, String description) {
        if (password != null) {
            String responseMessage = userAdapter.checkPassword(password);
            assertEquals(responseMessage, description, "Checking password error");
        } else {
            responseBetOn = userAdapter.checkNullPassword();
            assertEquals(responseBetOn.getField(), "password", "Invalid field");
            assertEquals(responseBetOn.getType(), "VALIDATION", "Invalid type");
            assertEquals(responseBetOn.getCode(), code, "Invalid code");
            assertEquals(responseBetOn.getDescription(), description, "Invalid description");
        }
    }

}

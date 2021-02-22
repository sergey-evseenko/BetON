package tests.userInfo;

import adapters.UserAdapter;
import models.Password;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class UpdatePasswordTest extends BaseTest {

    @Test(description = "update password")
    public void updatePassword() {
        UserAdapter userAdapter = new UserAdapter();
        Password validPassword = new Password("Qwerty!1231", "Qwerty!123", "Qwerty!1231");
        Password defaultPassword = new Password("Qwerty!123", "Qwerty!1231", "Qwerty!123");
        userAdapter.putPassword(validPassword, 200);
        userAdapter.putPassword(defaultPassword, 200);
    }

    @DataProvider(name = "Invalid passwords")
    public Object[][] invalidPasswords() {
        return new Object[][]{
                //newPassword with upper case
                {"QWERTY!1231", "Qwerty!123", "Qwerty!1231", "newPassword", "ER0001", "Wrong value format"},
                //newPassword with lower case
                {"qwerty!1231", "Qwerty!123", "Qwerty!1231", "newPassword", "ER0001", "Wrong value format"},
                //newPassword with min size
                {"Qw!1", "Qwerty!123", "Qwerty!1231", "newPassword", "ER0007", "Wrong value size"},
                //newPassword with max size
                {faker.lorem().characters(100) + "Qwerty!1231", "Qwerty!123", "Qwerty!1231", "newPassword", "ER0007", "Wrong value size"},
                //newPassword contains First Name
                {"TestQwerty!1", "Qwerty!123", "TestQwerty!1", "password", "ER0008", "Please do not use Username / Name / Surname as password"},
                //newPassword contains Last Name
                {"UserQwerty!1", "Qwerty!123", "UserQwerty!1", "password", "ER0008", "Please do not use Username / Name / Surname as password"},
                //newPassword contains User Name
                {"pollyisthebestqa!1QA", "Qwerty!123", "pollyisthebestqa!1QA", "password", "ER0008", "Please do not use Username / Name / Surname as password"},
                //new passwords are not equal
                {"Qwerty!1231", "Qwerty!123", "Qwerty!1231111", "password", "ER0005", "Values are mismatch"},
                //newPassword is null
                {null, "Qwerty!123", "Qwerty!1231", "newPassword", "ER0004", "Field is mandatory"},
                //password is null
                {"Qwerty!1231", null, "Qwerty!1231", "password", "ER0004", "Field is mandatory"},
                //repeatNewPassword is null
                {"Qwerty!1231", "Qwerty!123", null, "repeatNewPassword", "ER0004", "Field is mandatory"},
                //newPassword is empty
                {"", "Qwerty!123", "Qwerty!1231", "newPassword", "ER0001", "Wrong value format"},
                //password is empty
                {"Qwerty!1231", "", "Qwerty!1231", "password", "ER0007", "Wrong value size"},
                //repeatNewPassword is empty
                {"Qwerty!1231", "Qwerty!123", "", "repeatNewPassword", "ER0001", "Wrong value format"},
        };
    }

    @Test(description = "validate password", dataProvider = "Invalid passwords")
    public void validatePassword(String newPassword, String password, String repeatNewPassword, String field, String code, String description) {
        Password invalidPassword = new Password(newPassword, password, repeatNewPassword);
        responseBetOn = new UserAdapter().putPassword(invalidPassword, 400);
        assertEquals(responseBetOn.getField(), field, "Invalid field");
        assertEquals(responseBetOn.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBetOn.getCode(), code, "Invalid code");
        assertEquals(responseBetOn.getDescription(), description, "Invalid description");
    }
}

package tests.userInfo;

import models.Email;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class UpdateEmailTest extends BaseTest {

    @DataProvider(name = "Invalid codes")
    public Object[][] invalidCodes() {
        return new Object[][]{
                //invalid code
                {"qwerty", "ER3001", "Code not found"},
                //null
                {"null", "ER3001", "Code not found"},
                //expired code
                {"MpSzP5lOX0", "ER3002", "Code has been expired"}
        };
    }

    @Test(description = "Confirm that email was changed", dataProvider = "Invalid codes")
    public void confirmChangedEmail(String confirmationCode, String code, String description) {
        responseBetOn = userAdapter.confirmEmail(confirmationCode);
        assertEquals(responseBetOn.getField(), "Confirmation code", "Invalid field");
        assertEquals(responseBetOn.getType(), "CONFIRMATION", "Invalid type");
        assertEquals(responseBetOn.getCode(), code, "Invalid code");
        assertEquals(responseBetOn.getDescription(), description, "Invalid description");
    }

    @Test(description = "Update email")
    public void updateEmail() {
        Email validEmail = new Email("polinazz2@inbox.ru", "Qwerty!123", "polinazz2@inbox.ru");
        userAdapter.putEmail(validEmail, 200);
    }

    @DataProvider(name = "Invalid emails")
    public Object[][] invalidEmails() {
        return new Object[][]{
                //emails are not equals
                {"polinazz1234@inbox.ru", "Qwerty!123", "polinazz2@inbox.ru", "email", "ER0005", "Values are mismatch"},
                //email with invalid format
                {"polinazz2inbox.ru", "Qwerty!123", "polinazz2@inbox.ru", "email", "ER0001", "Wrong value format"},
                //email with not acceptable symbols
                {"йцукеув@gmail.com", "Qwerty!123", "polinazz2@inbox.ru", "email", "ER0001", "Wrong value format"},
                //email is not uniq
                {"qwerty81@gmail.com", "Qwerty!123", "polinazz2@inbox.ru", "email", "ER0002", "Not unique value"},
                //email with max size
                {"polinazz2@" + faker.lorem().characters(60) + ".com", "Qwerty!123", "polinazz2@inbox.ru", "email", "ER0007", "Wrong value size"},
                //email is null
                {null, "Qwerty!123", "polinazz2@inbox.ru", "email", "ER0004", "Field is mandatory"},
                //repeatedEmail is null
                {"polinazz2@inbox.ru", "Qwerty!123", null, "repeatedEmail", "ER0004", "Field is mandatory"},
                //email is null
                {"", "Qwerty!123", "polinazz2@inbox.ru", "email", "ER0001", "Wrong value format"},
                //repeatedEmail is empty
                {"polinazz2@inbox.ru", "Qwerty!123", "", "repeatedEmail", "ER0001", "Wrong value format"},
                //incorrect password
                {"polinazz2@inbox.ru", "Qwerty!123!!!", "polinazz2@inbox.ru", "password", "ER0009", "Incorrect value"},
                //password is null
                {"polinazz2@inbox.ru", null, "polinazz2@inbox.ru", "password", "ER0004", "Field is mandatory"},
                //password is empty
                {"polinazz2@inbox.ru", "", "polinazz2@inbox.ru", "password", "ER0007", "Wrong value size"},
        };
    }

    @Test(description = "validate email", dataProvider = "Invalid emails")
    public void validateEmail(String email, String password, String repeatedEmail, String field, String code, String description) {
        Email invalidEmail = new Email(email, password, repeatedEmail);
        responseBetOn = userAdapter.putEmail(invalidEmail, 400);
        assertEquals(responseBetOn.getField(), field, "Invalid field");
        assertEquals(responseBetOn.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBetOn.getCode(), code, "Invalid code");
        assertEquals(responseBetOn.getDescription(), description, "Invalid description");
    }
}

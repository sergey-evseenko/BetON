package tests.userInfo;

import adapters.UserAdapter;
import models.Email;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class UpdateEmailTest extends BaseTest {

    @Test(description = "Update valid email")
    public void updateValidEmail() {
        Email validEmail = new Email("polinazz2@inbox.ru", "Qwerty!123", "polinazz2@inbox.ru");
        new UserAdapter().putEmail(validEmail, token, 200);
    }

    @DataProvider(name = "Invalid emails")
    public Object[][] invalidPhones() {
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

    @Test(description = "Update invalid email", dataProvider = "Invalid emails")
    public void updateInvalidEmail(String email, String password, String repeatedEmail, String field, String code, String description) {
        Email invalidEmail = new Email(email, password, repeatedEmail);
        responseBody = new UserAdapter().putEmail(invalidEmail, token, 400);
        assertEquals(responseBody.getField(), field, "Invalid field");
        assertEquals(responseBody.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBody.getCode(), code, "Invalid code");
        assertEquals(responseBody.getDescription(), description, "Invalid description");
    }
}
package tests;

import adapters.RegistrationAdapter;
import adapters.ResendEmailAdapter;
import models.ResendEmailData;
import models.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

import static org.testng.Assert.assertEquals;

public class ResendEmailTest extends BaseTest {
    String externalId;
    String token;
    ResendEmailData resendEmailData = new ResendEmailData();

    @BeforeClass
    public void registration() throws FileNotFoundException {
        User user = new UserFactory().getUser(email, password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName);
        responseBody = new RegistrationAdapter().post(user, 200);
        externalId = responseBody.getExternalId();
        token = responseBody.getAccessToken();
    }

    @DataProvider(name = "resend to new email")
    public Object[][] resendToNewEmailData() {
        String newEmail = faker.internet().emailAddress();
        return new Object[][]{
                //valid data
                {newEmail, newEmail, 200, "", ""},
                //existing user
                {"qwerty81@gmail.com", "qwerty81@gmail.com", 400, "ER0002", "Not unique value"},
                //emails are not equals
                {"changedqwerty75263@gmail.com", "qwe2345rty@gmail.com", 400, "ER0005", "Values are mismatch"},
                //without @
                {"changedqwerty75263gmail.com", "changedqwerty75263@gmail.com", 400, "ER0001", "Wrong value format"},
                //without dot
                {"changedqwerty75263@gmailcom", "changedqwerty75263@gmail.com", 400, "ER0001", "Wrong value format"},
                //max size
                {"123456789@1234567890123456789012345678901234567890123456789012345678.ru", "changedqwerty75263@gmail.com", 400, "ER0007", "Wrong value size"},
                //not acceptable symbols
                {"йцуке@цуке.ке", "changedqwerty75263@gmail.com", 400, "ER0001", "Wrong value format"},
        };
    }

    @Test(description = "resend email to new email", dataProvider = "resend to new email")
    public void resendEmailToNewEmail(String email, String repeatedEmail, int responseCode, String code, String errorDescription) {
        resendEmailData.setEmail(email);
        resendEmailData.setRepeatedEmail(repeatedEmail);
        resendEmailData.setExternalId(externalId);
        responseBody = new ResendEmailAdapter().post(resendEmailData, token, responseCode);
        if (responseBody != null) {
            assertEquals(responseBody.getField(), "email", "Invalid field");
            assertEquals(responseBody.getType(), "VALIDATION", "Invalid type");
            assertEquals(responseBody.getCode(), code, "Invalid code");
            assertEquals(responseBody.getDescription(), errorDescription, "Invalid description");
        }
    }

    @DataProvider(name = "resend to old email")
    public Object[][] resendToOldEmailData() {
        return new Object[][]{
                //valid data
                {externalId, "", "", 200},
                //invalid externalId
                {"9999999", "ER2002", "Bad credentials", 404},
                //empty externalId
                {"", "ER3001", "Code not found", 400},
        };
    }

    @Test(description = "resend to old email", dataProvider = "resend to old email")
    public void resendEmailToOldEmail(String externalId, String code, String errorDescription, int responseCode) {
        responseBody = new ResendEmailAdapter().get(externalId, token, responseCode);
        if (responseBody != null) {
            assertEquals(responseBody.getCode(), code, "Invalid code");
            assertEquals(responseBody.getDescription(), errorDescription, "Invalid description");
        }
    }
}

package tests.registration;

import models.ResendEmailData;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;
import tests.UserFactory;

import static org.testng.Assert.assertEquals;

public class ResendEmailTest extends BaseTest {
    String externalId, accessToken;
    ResendEmailData resendEmailData = new ResendEmailData();

    @BeforeClass
    public void registration() {
        user = new UserFactory().getNewUser();
        responseBetOn = registrationAdapter.post(user, 200);
        externalId = responseBetOn.getExternalId();
        accessToken = responseBetOn.getAccessToken();
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
                //empty
                {"", "changedqwerty75263@gmail.com", 400, "ER0001", "Wrong value format"},
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
        responseBetOn = resendEmailAdapter.post(resendEmailData, accessToken, responseCode);
        if (responseBetOn != null) {
            assertEquals(responseBetOn.getField(), "email", "Invalid field");
            assertEquals(responseBetOn.getType(), "VALIDATION", "Invalid type");
            assertEquals(responseBetOn.getCode(), code, "Invalid code");
            assertEquals(responseBetOn.getDescription(), errorDescription, "Invalid description");
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
        responseBetOn = resendEmailAdapter.get(externalId, accessToken, responseCode);
        if (responseBetOn != null) {
            assertEquals(responseBetOn.getCode(), code, "Invalid code");
            assertEquals(responseBetOn.getDescription(), errorDescription, "Invalid description");
        }
    }
}

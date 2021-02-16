package tests.registration;

import adapters.ConfirmRegistrationAdapter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class ConfirmRegistrationTest extends BaseTest {

    @DataProvider(name = "invalid confirmation codes")
    public Object[][] registrationCodes() {
        return new Object[][]{
                //TODO: valid registration code
                //{"CeeB4Qp*Eh", "ER3001", "Body is correct", 200},
                //invalid code
                {"12345", "ER3001", "Code not found", 400},
                //code is null
                {null, "ER3001", "Code not found", 400},
        };
    }

    @Test(description = "validate confirmation code", dataProvider = "invalid confirmation codes")
    public void validateConfirmationCode(String registrationCode, String code, String errorDescription, int responseCode) {
        response = new ConfirmRegistrationAdapter().getConfirmRegistration(registrationCode, responseCode);
        assertEquals(response.getField(), "Confirmation code", "Invalid field");
        assertEquals(response.getType(), "CONFIRMATION", "Invalid type");
        assertEquals(response.getCode(), code, "Invalid code");
        assertEquals(response.getDescription(), errorDescription, "Invalid description");
    }
}

package tests;

import adapters.ConfirmRegistrationAdapter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

import static org.testng.Assert.assertEquals;

public class ConfirmRegistration extends BaseTest {

    public ConfirmRegistration() throws FileNotFoundException {
    }

    @DataProvider(name = "Registration codes")
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

    @Test(description = "Confirm registration", dataProvider = "Registration codes")
    public void registrationWithInvalidData(String registrationCode, String code, String errorDescription, int responseCode) throws FileNotFoundException {
        responseBody = new ConfirmRegistrationAdapter().get(registrationCode, responseCode);
        assertEquals(responseBody.getField(), "Confirmation code", "Invalid field");
        assertEquals(responseBody.getType(), "CONFIRMATION", "Invalid type");
        assertEquals(responseBody.getCode(), code, "Invalid code");
        assertEquals(responseBody.getDescription(), errorDescription, "Invalid description");
    }
}

package tests;

import adapters.ConfirmRegistrationAdapter;
import models.ResponseBody;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

import static org.testng.Assert.assertEquals;

public class ConfirmRegistration {


    @DataProvider(name = "Registration codes")
    public Object[][] registrationCodes() {
        return new Object[][]{
                //TODO: valid registreation code
                //{"CeeB4Qp*Eh", "ER3001", "Body is correct", 200},
                //invalid code
                {"12345", "ER3001", "Code not found", 400},
                //code is null
                {null, "ER3001", "Code not found", 400},
        };
    }

    @Test(description = "Confirm registration", dataProvider = "Registration codes")
    public void registrationWithInvalidData(String registrationCode, String code, String errorDescription, int responseCode) throws FileNotFoundException {
        ResponseBody[] responseBody = new ConfirmRegistrationAdapter().get(registrationCode, responseCode);
        assertEquals(responseBody[0].getField(), "Confirmation code", "Invalid field");
        assertEquals(responseBody[0].getType(), "CONFIRMATION", "Invalid type");
        assertEquals(responseBody[0].getCode(), code, "Invalid code");
        assertEquals(responseBody[0].getDescription(), errorDescription, "Invalid description");

    }


}

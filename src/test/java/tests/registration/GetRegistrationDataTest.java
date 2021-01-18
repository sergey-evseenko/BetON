package tests.registration;

import adapters.GetRegistrationDataAdapted;
import models.RegistrationDataAll;
import models.RegistrationDataEN;
import models.RegistrationDataRU;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.testng.Assert.assertEquals;

public class GetRegistrationDataTest extends BaseTest {


    @Test(description = "get registration data for English language")
    public void getRegistrationDataEN() throws FileNotFoundException {
        RegistrationDataEN expectedRegistrationData, actualRegistrationData;
        expectedRegistrationData = gson.fromJson(new FileReader("src/test/resources/Data/registrationDataEN.json"), RegistrationDataEN.class);
        actualRegistrationData = new GetRegistrationDataAdapted().getRegistrationDataEN();
        assertEquals(actualRegistrationData, expectedRegistrationData, "Invalid registration data for English language");
    }

    @Test(description = "get registration data for Russian language")
    public void getRegistrationDataRU() throws FileNotFoundException {
        RegistrationDataRU expectedRegistrationData, actualRegistrationData;
        expectedRegistrationData = gson.fromJson(new FileReader("src/test/resources/Data/registrationDataRU.json"), RegistrationDataRU.class);
        actualRegistrationData = new GetRegistrationDataAdapted().getRegistrationDataRU();
        assertEquals(actualRegistrationData, expectedRegistrationData, "Invalid registration data for German language");
    }

    @DataProvider(name = "Invalid language params")
    public Object[][] languageParams() {
        return new Object[][]{
                {"test"},
                {""},
                {null}
        };
    }

    @Test(description = "get registration data with invalid language parameter", dataProvider = "Invalid language params")
    public void getRegistrationDataWithInvalidParam(String langParam) throws FileNotFoundException {
        RegistrationDataAll expectedRegistrationData, actualRegistrationData;
        expectedRegistrationData = gson.fromJson(new FileReader("src/test/resources/Data/registrationDataAll.json"), RegistrationDataAll.class);
        actualRegistrationData = new GetRegistrationDataAdapted().getRegistrationDataAll(langParam);
        assertEquals(actualRegistrationData, expectedRegistrationData, "Invalid registration data for invalid language parameter");
    }
}

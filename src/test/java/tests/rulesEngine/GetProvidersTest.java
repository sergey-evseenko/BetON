package tests.rulesEngine;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class GetProvidersTest extends BaseTest {

    @DataProvider(name = "Country codes")
    public Object[][] countryCodes() {
        return new Object[][]{
                //valid country code
                {"BY", "[3,4]"},
                //unknown country code
                {"ZZ", "[]"},
                //invalid country code
                {"1234", "[]"},
        };
    }

    @Test(description = "get providers by country code", dataProvider = "Country codes")
    public void getProviders(String countryCode, String expectedProvidersList) {
        String providersList = rulesAdapter.getProviders(countryCode);
        assertEquals(providersList, expectedProvidersList, "Invalid providers list");
    }
}

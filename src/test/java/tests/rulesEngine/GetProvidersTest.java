package tests.rulesEngine;

import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GetProvidersTest extends BaseTest {

    int[] providersList;

    @Test(description = "get providers by country code")
    public void getProviders() {
        providersList = rulesAdapter.getProviders("BY");
        assertTrue(providersList.length > 0, "Invalid providers list");
    }

    @Test(description = "get providers by unknown country code")
    public void getProvidersUnknownCountryCode() {
        providersList = rulesAdapter.getProviders("ZZ");
        assertEquals(providersList.length, 0, "Invalid providers list");
    }

    @Test(description = "get providers by invalid country code")
    public void getProvidersInvalidCountryCode() {
        providersList = rulesAdapter.getProviders("1234");
        assertEquals(providersList.length, 0, "Invalid providers list");
    }
}

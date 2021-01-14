package tests;

import adapters.SearchRegistrationDataAdapter;
import models.AddressForRegistration;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

import static org.testng.Assert.assertEquals;

public class SearchRegistrationDataTest extends BaseTest {

    public SearchRegistrationDataTest() throws FileNotFoundException {
    }

    @DataProvider(name = "Search data params")
    public Object[][] searchDataParams() {
        return new Object[][]{
                //with all params
                {100, "DE", "80636", "München", "Marlene-Dietrich-Str.", true, 0},
                //without postalCode
                {100, "DE", null, "München", "Marlene-Dietrich-Str.", false, 7856},
                //without postalCode, city, street
                {100, "DE", null, null, null, false, 1},
                //not full postalcode
                {100, "DE", "81", null, null, false, 7938},
                //without city, street
                {100, "DE", "80636", null, null, false, 7849},
                //without without street, street
                {100, "DE", "80636", "München", null, false, 7849},
                //without params
                {0, null, null, null, null, true, 0},
        };
    }

    @Test(description = "search registration data", dataProvider = "Search data params")
    public void searchRegistrationData(int resultCount, String countryCode, String postcode, String city, String street, Boolean isEmptyResponse, int id) {
        AddressForRegistration[] listOfAddresses;
        listOfAddresses = new SearchRegistrationDataAdapter().post(resultCount, countryCode, postcode, city, street);
        if (isEmptyResponse == true) {
            assertEquals(listOfAddresses.length, 0, "Invalid search result");
        } else {
            assertEquals(listOfAddresses[0].getId(), id, "Invalid search result");
        }
    }
}

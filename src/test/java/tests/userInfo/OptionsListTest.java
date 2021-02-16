package tests.userInfo;

import adapters.PlayerAdapter;
import models.OptionsList;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class OptionsListTest extends BaseTest {

    @DataProvider(name = "Options list")
    public Object[][] passwords() {
        return new Object[][]{
                //invalid sportId
                {0, true, true, 400, "sportId", "ER0006", "Choose the value"},
                //autoAccept is null
                {1, null, true, 400, "autoAccept", "ER0004", "Field is mandatory"},
                //wantNewslettersOnEmail is null
                {1, true, null, 400, "wantNewslettersOnEmail", "ER0004", "Field is mandatory"},
                //valid option list
                {random.nextInt(10) + 1, random.nextBoolean(), random.nextBoolean(), 200, "", "", ""}
        };
    }

    @Test(description = "validate options list", dataProvider = "Options list")
    public void putOptionList(int sportId, Boolean autoAccept, Boolean wantNewslettersOnEmail, int responseCode, String field, String code, String description) {
        OptionsList optionsList = new OptionsList(sportId, autoAccept, wantNewslettersOnEmail);
        response = new PlayerAdapter().putOptionsList(optionsList, responseCode, token);
        if (responseCode == 400) {
            assertEquals(response.getField(), field, "Invalid field");
            assertEquals(response.getType(), "VALIDATION", "Invalid type");
            assertEquals(response.getCode(), code, "Invalid code");
            assertEquals(response.getDescription(), description, "Invalid description");
        } else {
            OptionsList actualOptionList = new PlayerAdapter().getOptionsList(token);
            assertEquals(actualOptionList, optionsList, "Updating options list error");
        }
    }

    @Test(description = "get options list unauthorized")
    public void getOptionListUnauthorized() {
        response = new PlayerAdapter().getOptionListUnauthorized();
        assertEquals(response.getError(), "unauthorized", "authorization error");
        assertEquals(response.getErrorDescription(), "Full authentication is required to access this resource", "authorization error");
    }

    @Test(description = "put options list unauthorized")
    public void putOptionListUnauthorized() {
        OptionsList optionsList = new OptionsList(1, true, true);
        response = new PlayerAdapter().putOptionListUnauthorized(optionsList);
        assertEquals(response.getError(), "unauthorized", "authorization error");
        assertEquals(response.getErrorDescription(), "Full authentication is required to access this resource", "authorization error");
    }
}

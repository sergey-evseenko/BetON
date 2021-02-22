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
        responseBetOn = new PlayerAdapter().putOptionsList(optionsList, responseCode);
        if (responseCode == 400) {
            assertEquals(responseBetOn.getField(), field, "Invalid field");
            assertEquals(responseBetOn.getType(), "VALIDATION", "Invalid type");
            assertEquals(responseBetOn.getCode(), code, "Invalid code");
            assertEquals(responseBetOn.getDescription(), description, "Invalid description");
        } else {
            OptionsList actualOptionList = new PlayerAdapter().getOptionsList();
            assertEquals(actualOptionList, optionsList, "Updating options list error");
        }
    }

    @Test(description = "get options list unauthorized")
    public void getOptionListUnauthorized() {
        responseBetOn = new PlayerAdapter().getOptionListUnauthorized();
        assertEquals(responseBetOn.getError(), "unauthorized", "authorization error");
        assertEquals(responseBetOn.getErrorDescription(), "Full authentication is required to access this resource", "authorization error");
    }

    @Test(description = "put options list unauthorized")
    public void putOptionListUnauthorized() {
        OptionsList optionsList = new OptionsList(1, true, true);
        responseBetOn = new PlayerAdapter().putOptionListUnauthorized(optionsList);
        assertEquals(responseBetOn.getError(), "unauthorized", "authorization error");
        assertEquals(responseBetOn.getErrorDescription(), "Full authentication is required to access this resource", "authorization error");
    }
}

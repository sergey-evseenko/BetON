package tests.template;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TemplateTest extends BaseTest {

    String templateUrl = "https://sdk1-beton.ngsb-rc.com/sdk/service/v1/bet-on/template/registration";

    @Test(description = "getTemplateLink")
    public void getTemplateLink() {
        responseBetOn = templateAdapter.getTemplateUrl(1, 200);
        assertEquals(responseBetOn.getTemplateUrl(), templateUrl, "invalid template url");
        assertTrue(StringUtils.isNotEmpty(responseBetOn.getJwtToken()), "Invalid jwt token");
    }

    @Test(description = "invalid providerId")
    public void invalidProviderId() {
        int providerId = 1234;
        responseBetOn = templateAdapter.getTemplateUrl(providerId, 400);
        assertEquals(responseBetOn.getMessage(), "Incorrect Provider ID=" + providerId, "invalid provider id error");
    }

    @Test(description = "missed providerId")
    public void missedProviderId() {
        responseBetOn = templateAdapter.getTemplateUrl(400);
        assertEquals(responseBetOn.getMessage(), "Required Long parameter 'providerId' is not present", "missed provider id error");
    }
}

package tests.userInfo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class PPMOTest extends BaseTest {

    @Test(description = "put PPMO")
    public void putPPMO() {
        String[] ppmos = new String[]{"D", "O", "M"};
        String ppmo = ppmos[random.nextInt(ppmos.length)];
        playerAdapter.putPPMO(ppmo, 200);
        responseBetOn = playerAdapter.getPPMO();
        assertEquals(responseBetOn.getPpmo(), ppmo, "Updating ppmo error");
    }

    @DataProvider(name = "Invalid PPMO")
    public Object[][] invalidPPMO() {
        return new Object[][]{
                //not existing typ
                {"Q"},
                //is empty
                {""},
                //is null
                {null}
        };
    }

    @Test(description = "validate PPMO", dataProvider = "Invalid PPMO")
    public void validatePPMO(String ppmo) {
        playerAdapter.putPPMO(ppmo, 400);
    }
}

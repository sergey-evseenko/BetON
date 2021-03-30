package tests;

import org.testng.annotations.BeforeClass;

public class Bets extends BaseTest {

    @BeforeClass
    public void getBet() throws Exception {
        bets = getBets();
    }
}

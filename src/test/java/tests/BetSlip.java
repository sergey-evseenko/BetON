package tests;

import org.testng.annotations.BeforeClass;

public class BetSlip extends BaseTest {

    protected int numberOfBets = 3;
    protected models.BetSlip[] bets = new models.BetSlip[numberOfBets];

    @BeforeClass()
    public void getBets() throws Exception {
        for (int i = 0; i < numberOfBets; i++) {
            bets[i] = getBetSlip(i);
        }
    }
}

package tests.betSlip;

import models.BetSlip;
import models.Wager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

public class WagerTests extends BaseTest {

    BetSlip[] bets = new BetSlip[3];
    Wager wager = new Wager(300, null);

    @BeforeClass()
    public void getBets() throws Exception {
        for (int i = 0; i < 3; i++) {
            bets[i] = getBetSlip(i);
        }
    }

    @DataProvider(name = "Wagers")
    public Object[][] wagers() {
        return new Object[][]{
                //single combination
                {"SINGLE", "singleCombinationThreeBets.json"},
                //system combination
                {"SYSTEM", "systemCombinationThreeBets.json"},
                //combi combination
                {"COMBINATION", "combiCombinationThreeBets.json"},
        };
    }

    @Test(description = "Change wager", dataProvider = "Wagers")
    public void changeWager(String combinationType, String fileName) {
        for (int i = 0; i < 3; i++) {
            betSlipAdapter.addBet(bets[i], 200);
        }
        betSlipAdapter.addCombination(fileName, bets, 3, combinationType, 200);

        wager.setWagerType("T");
        betSlipAdapter.changeWager(wager, combinationType, 200);

        wager.setWagerType("B");
        betSlipAdapter.changeWager(wager, combinationType, 200);

        betSlipAdapter.deleteAll(200);
    }

    @Test(description = "Change wager. No bets")
    public void changeWagerNoBets() {

        wager.setWagerType("T");
        betSlipAdapter.changeWager(wager, null, 404);

        wager.setWagerType("B");
        betSlipAdapter.changeWager(wager, null, 404);
    }
}

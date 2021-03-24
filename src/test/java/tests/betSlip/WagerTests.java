package tests.betSlip;

import models.BetSlip;
import models.Wager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

public class WagerTests extends BaseTest {

    BetSlip[] bets = new BetSlip[3];
    Wager wagerT = new Wager(300, "T");
    Wager wagerB = new Wager(300, "B");

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
        int numberOfBets = 3;
        for (int i = 0; i < numberOfBets; i++) {
            betSlipAdapter.addBet(bets[i], 200);
        }
        betSlipAdapter.addCombination(fileName, bets, numberOfBets, combinationType, 200);

        betSlipAdapter.changeWager(wagerT, combinationType, 200, numberOfBets);
        betSlipAdapter.changeWager(wagerB, combinationType, 200, numberOfBets);

        betSlipAdapter.deleteAll(200);
    }

    @DataProvider(name = "Invalid wagers")
    public Object[][] invalidWagers() {
        return new Object[][]{
                //no bets
                {100, "T", 404},
                //wager is negative
                {-100, "T", 400},
                //invalid wager type
                {100, "X", 400},
        };
    }

    @Test(description = "Change wager. Invalid wager.", dataProvider = "Invalid wagers")
    public void test(int value, String combinationType, int expectedStatusCode) {
        wagerT.setWager(value);
        wagerT.setWagerType(combinationType);
        betSlipAdapter.changeWager(wagerT, expectedStatusCode);
    }
}

package tests.betSlip;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.Bets;


public class CombinationsTest extends Bets {

    @DataProvider(name = "Combinations")
    public Object[][] combinations() {
        return new Object[][]{
                //single combination, no bets
                {0, "SINGLE", "singleCombinationOneBet.json", 404},
                //single combination, one bet
                {1, "SINGLE", "singleCombinationOneBet.json", 200},
                //single combination, two bets
                {2, "SINGLE", "singleCombinationTwoBets.json", 200},
                //single combination, three bets
                {3, "SINGLE", "singleCombinationThreeBets.json", 200},
                //combi combination, two bets
                {2, "COMBINATION", "combiCombinationTwoBets.json", 200},
                //combi combination, three bets
                {3, "COMBINATION", "combiCombinationThreeBets.json", 200},
                //system combination, three bets
                {3, "SYSTEM", "systemCombinationThreeBets.json", 200},
        };
    }

    @Test(description = "add/delete combinations", dataProvider = "Combinations")
    public void combinations(int numberOfBets, String combinationType, String fileName, int expectedStatusCode) {
        for (int i = 0; i < numberOfBets; i++) {
            betSlipAdapter.addBet(bets[i], 200);
        }
        betSlipAdapter.addCombination(fileName, expectedStatusCode);
        betSlipAdapter.validateCombinationResponse(bets, numberOfBets, combinationType, expectedStatusCode);
        betSlipAdapter.deleteCombination(fileName, expectedStatusCode);
        betSlipAdapter.validateCombinationResponse(bets, numberOfBets, combinationType, expectedStatusCode);
        betSlipAdapter.deleteAll(expectedStatusCode);

    }

    @Test(description = "Get full bet slip")
    public void getAllBets() {
        for (int i = 0; i < 3; i++) {
            betSlipAdapter.addBet(bets[i], 200);
        }
        betSlipAdapter.getAll(bets, 200);
        betSlipAdapter.deleteAll(200);
    }

    @Test(description = "Get full bet slip (no bets)")
    public void getAllBetsNoBets() {
        betSlipAdapter.getAll(bets, 404);
    }

    @Test(description = "Get short bet slip")
    public void getShortBet() {
        for (int i = 0; i < 3; i++) {
            betSlipAdapter.addBet(bets[i], 200);
        }
        betSlipAdapter.getShort(bets, 200);
        betSlipAdapter.deleteAll(200);
    }

    @Test(description = "Get short bet slip")
    public void getShortBetNoBets() {
        betSlipAdapter.getShort(bets, 404);
    }
}

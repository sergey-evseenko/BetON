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

    @Test(description = "add combinations", dataProvider = "Combinations")
    public void addCombinations(int numberOfBets, String combinationType, String fileName, int expectedStatusCode) {
        for (int i = 0; i < numberOfBets; i++) {
            betSlipAdapter.addBet(bets[i], 200);
        }
        betSlipAdapter.addCombination(fileName, expectedStatusCode);
        betSlipAdapter.validateCombinationResponse(bets, numberOfBets, combinationType, expectedStatusCode);
        betSlipAdapter.deleteAll(expectedStatusCode);
    }

    @Test(description = "Get full bet slip")
    public void getAllBets() {
        betSlipAdapter.addBets(bets);
        betSlipAdapter.getAll(bets, 200);
        betSlipAdapter.deleteAll(200);
    }

    @Test(description = "Get full bet slip (no bets)")
    public void getAllBetsNoBets() {
        betSlipAdapter.getAll(bets, 404);
    }

    @Test(description = "Get short bet slip")
    public void getShortBet() {
        betSlipAdapter.addBets(bets);
        betSlipAdapter.getShort(bets, 200);
        betSlipAdapter.deleteAll(200);
    }

    @Test(description = "Get short bet slip")
    public void getShortBetNoBets() {
        betSlipAdapter.getShort(bets, 404);
    }

    @Test(description = "Delete single combination")
    public void deleteSingleCombination() {
        betSlipAdapter.addBets(bets);
        betSlipAdapter.addCombination("singleCombinationThreeBets.json", 200);
        betSlipAdapter.deleteCombination("singleCombinationDelete.json", 200);
        betSlipAdapter.validateCombinationResponse(bets, bets.length, "COMBINATION", 200);
        betSlipAdapter.deleteAll(200);
    }

    @Test(description = "Delete combi combination")
    public void deleteCombiCombination() {
        betSlipAdapter.addBets(bets);
        betSlipAdapter.addCombination("singleCombinationThreeBets.json", 200);
        betSlipAdapter.addCombination("combiCombinationThreeBets.json", 200);
        betSlipAdapter.validateCombinationResponse(bets, bets.length, "SYSTEM", 200);
        betSlipAdapter.deleteCombination("combiCombinationDelete.json", 200);
        betSlipAdapter.validateCombinationResponse(bets, bets.length, "SINGLE", 200);
        betSlipAdapter.deleteAll(200);

    }

    @Test(description = "Delete system combination")
    public void deleteSystemCombination() {
        betSlipAdapter.addBets(bets);
        betSlipAdapter.addCombination("systemCombinationThreeBets.json", 200);
        betSlipAdapter.deleteCombination("systemCombinationDelete.json", 200);
        betSlipAdapter.validateCombinationResponse(bets, bets.length, "COMBINATION", 200);
        betSlipAdapter.deleteAll(200);
    }
}

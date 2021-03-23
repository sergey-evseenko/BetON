package tests.betSlip;

import models.BetSlip;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tests.BaseTest;


public class CombinationsTest extends BaseTest {

    BetSlip[] bets = new BetSlip[3];
    String singlePtah = "src/test/resources/data/single.json";
    String combinationPath = "src/test/resources/data/combination.json";
    String systemPath = "src/test/resources/data/system.json";

    @BeforeClass()
    public void getBets() throws Exception {
        for (int i = 0; i < 3; i++) {
            bets[i] = getBetSlip(i);
        }
    }

    @Test(description = "add/delete combination without bet slip")
    public void combinationNoBet() {
        betSlipAdapter.addCombination(singlePtah, null, 0, "", 404);
        betSlipAdapter.deleteCombination(singlePtah, null, 0, "", 404);
    }

    @Test(description = "add/delete single combination (one event)")
    public void single() {
        betSlipAdapter.addBet(bets[0], 200);
        betSlipAdapter.addCombination(singlePtah, bets, 1, "SINGLE", 200);
        betSlipAdapter.deleteCombination(singlePtah, bets, 1, "SINGLE", 200);
        betSlipAdapter.deleteAll(200);
    }

    @Test(description = "add/delete combination (two events)")
    public void combination() {
        for (int i = 0; i < 2; i++) {
            betSlipAdapter.addBet(bets[i], 200);
        }
        betSlipAdapter.addCombination(combinationPath, bets, 2, "COMBINATION", 200);
        betSlipAdapter.deleteCombination(combinationPath, bets, 2, "COMBINATION", 200);
        betSlipAdapter.deleteAll(200);
    }

    @Test(description = "add/delete system combination (three events)")
    public void system() {
        for (int i = 0; i < 3; i++) {
            betSlipAdapter.addBet(bets[i], 200);
        }
        betSlipAdapter.addCombination(systemPath, bets, 3, "SYSTEM", 200);
        betSlipAdapter.deleteCombination(systemPath, bets, 3, "SYSTEM", 200);
        betSlipAdapter.deleteAll(200);
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

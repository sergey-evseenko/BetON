package tests.betSlip;

import org.testng.annotations.Test;
import tests.BaseTest;


public class CombinationsTest extends BaseTest {

    String[] eventIds = new String[3];
    String singlePtah = "src/test/resources/data/single.json";
    String combinationPath = "src/test/resources/data/combination.json";
    String systemPath = "src/test/resources/data/system.json";

    @Test(description = "add/delete combination without bet slip")
    public void combinationNoBet() {
        betSlipAdapter.addCombination(singlePtah, null, 0, "");
        betSlipAdapter.deleteCombination(singlePtah, null, 0, "");
    }

    @Test(description = "add/delete single combination (one event)")
    public void single() throws Exception {
        betSlip = getBetSlip(0);
        betSlipAdapter.addBet(betSlip, 200);
        eventIds[0] = betSlip.getEventId();

        betSlipAdapter.addCombination(singlePtah, eventIds, 1, "SINGLE");
        betSlipAdapter.deleteCombination(singlePtah, eventIds, 1, "SINGLE");
        betSlipAdapter.deleteAll(200);
    }

    @Test(description = "add/delete combination (two events)")
    public void combination() throws Exception {
        for (int i = 0; i < 2; i++) {
            betSlip = getBetSlip(i);
            betSlipAdapter.addBet(betSlip, 200);
            eventIds[i] = betSlip.getEventId();
        }

        betSlipAdapter.addCombination(combinationPath, eventIds, 2, "COMBINATION");
        betSlipAdapter.deleteCombination(combinationPath, eventIds, 2, "COMBINATION");

        betSlipAdapter.deleteAll(200);
    }

    @Test(description = "add/delete system combination (three events)")
    public void system() throws Exception {
        for (int i = 0; i < 3; i++) {
            betSlip = getBetSlip(i);
            betSlipAdapter.addBet(betSlip, 200);
            eventIds[i] = betSlip.getEventId();
        }

        betSlipAdapter.addCombination(systemPath, eventIds, 3, "SYSTEM");
        betSlipAdapter.deleteCombination(systemPath, eventIds, 3, "SYSTEM");

        betSlipAdapter.deleteAll(200);
    }
}

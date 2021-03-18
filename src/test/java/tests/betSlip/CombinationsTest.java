package tests.betSlip;

import models.Bss;
import models.Combination;
import org.testng.annotations.Test;
import tests.BaseTest;


public class CombinationsTest extends BaseTest {

    Combination combination = data.get("combination.json", Combination.class);

    @Test(description = "add/delete combination without bet slip")
    public void combinationNoBet() {
        betSlipAdapter.addCombination(combination);
        betSlipAdapter.deleteCombination(combination);
    }

    @Test(description = "add/delete single combination")
    public void singleCombination() throws Exception {
        betSlip = getBetSlip(0);
        betSlipAdapter.addBet(betSlip, 200);
        betSlipAdapter.addCombination(combination, betSlip.getEventId());
        betSlipAdapter.deleteCombination(combination, betSlip.getEventId());
        betSlipAdapter.deleteAll(200);
    }

    @Test(description = "add/delete combination")
    public void doubleCombination() throws Exception {
        String[] eventIds = new String[2];

        betSlip = getBetSlip(0);
        betSlipAdapter.addBet(betSlip, 200);
        eventIds[0] = betSlip.getEventId();
        betSlip = getBetSlip(1);
        betSlipAdapter.addBet(betSlip, 200);
        eventIds[1] = betSlip.getEventId();

        Bss bss = combination.getBss();
        bss.setBssn("COMBINATION");
        combination.setBss(bss);

        betSlipAdapter.addCombination(combination, eventIds);
        betSlipAdapter.deleteCombination(combination, eventIds);

        betSlipAdapter.deleteAll(200);
    }
}

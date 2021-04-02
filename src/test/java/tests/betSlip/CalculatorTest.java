package tests.betSlip;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.Bets;

public class CalculatorTest extends Bets {
    float[] rates = new float[bets.length];

    @BeforeMethod
    public void addBetsAndGetRates() {
        betSlipAdapter.addBets(bets);
        rates = betSlipAdapter.getRates(bets.length, 200);
    }

    @AfterMethod
    public void deleteBets() {
        betSlipAdapter.deleteAll(200);
    }

    @Test
    public void calcMaxOddForSingle() {
        betSlipAdapter.addCombination("singleCombinationThreeBets.json", 200);
        betSlipAdapter.validateMaxOddSingle(rates);
    }

    @Test
    void calcMaxOddForCombi() {
        betSlipAdapter.addCombination("combiCombinationThreeBets.json", 200);
        betSlipAdapter.validateMaxOddCombi(rates);
    }

    @Test
    void calcmaxOddForSystem() {
        betSlipAdapter.addCombination("systemCombinationThreeBets.json", 200);
        betSlipAdapter.validateMaxOddSystem(rates);
    }
}

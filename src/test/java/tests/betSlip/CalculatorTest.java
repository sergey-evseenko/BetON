package tests.betSlip;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.Bets;

public class CalculatorTest extends Bets {
    float[] rates = new float[numberOfBets];

    @BeforeMethod
    public void addBets() {
        for (int i = 0; i < numberOfBets; i++) {
            betSlipAdapter.addBet(bets[i], 200);
        }
        rates = betSlipAdapter.getRates(numberOfBets, 200);
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

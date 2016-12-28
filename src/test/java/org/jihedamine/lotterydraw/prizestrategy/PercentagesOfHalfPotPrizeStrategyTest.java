package org.jihedamine.lotterydraw.prizestrategy;

import org.jihedamine.prizestrategy.PercentagesOfHalfPotPrizeStrategy;
import org.jihedamine.prizestrategy.exceptions.PrizeStrategyInvalidArgumentException;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

/**
 * @author Jihed Amine Maaref on 25-Dec-16.
 */
public class PercentagesOfHalfPotPrizeStrategyTest {

    double[] pricesPercentages = new double[] {0.75, 0.15, 0.10};
    BigDecimal pot = BigDecimal.valueOf(10);

    @Test
    public void testValidWinnerPrizeAmount() {
        PercentagesOfHalfPotPrizeStrategy strategy = new PercentagesOfHalfPotPrizeStrategy(pricesPercentages);
        assertTrue(strategy.getPrizeAmount(pot, 0).compareTo(BigDecimal.valueOf(3.75)) == 0);
        assertTrue(strategy.getPrizeAmount(pot, 1).compareTo(BigDecimal.valueOf(0.75)) == 0);
        assertTrue(strategy.getPrizeAmount(pot, 2).compareTo(BigDecimal.valueOf(0.5)) == 0);
        assertTrue(strategy.getPrizeAmount(pot, 3).compareTo(BigDecimal.ZERO) == 0);
    }

    @Test(expected = PrizeStrategyInvalidArgumentException.class)
    public void testNegativePot() {
        PercentagesOfHalfPotPrizeStrategy strategy = new PercentagesOfHalfPotPrizeStrategy(pricesPercentages);
        BigDecimal invalidPot = BigDecimal.valueOf(-10);
        strategy.getPrizeAmount(invalidPot, 0);
    }

    @Test
    public void testWinnerIndexUnspecifiedPrize() {
        PercentagesOfHalfPotPrizeStrategy strategy = new PercentagesOfHalfPotPrizeStrategy(pricesPercentages);
        assertTrue(strategy.getPrizeAmount(pot, 6).compareTo(BigDecimal.ZERO) == 0);
    }

    @Test(expected = PrizeStrategyInvalidArgumentException.class)
    public void testInvalidWinnerIndex() {
        PercentagesOfHalfPotPrizeStrategy strategy = new PercentagesOfHalfPotPrizeStrategy(pricesPercentages);
        strategy.getPrizeAmount(pot, -6);
    }

    @Test(expected = PrizeStrategyInvalidArgumentException.class)
    public void testPrizePercentageIsNegative() {
        double[] invalidPricesPercentages = new double[] {0.75, -0.15, 0.10};
        new PercentagesOfHalfPotPrizeStrategy(invalidPricesPercentages);
    }

    @Test(expected = PrizeStrategyInvalidArgumentException.class)
    public void testPrizePercentageIsMoreThanOne() {
        double[] invalidPricesPercentages = new double[] {0.75, 1.15, 0.10};
        new PercentagesOfHalfPotPrizeStrategy(invalidPricesPercentages);
    }

    @Test(expected = PrizeStrategyInvalidArgumentException.class)
    public void testPrizePercentagesSumMoreThanOne() {
        double[] invalidPricesPercentages = new double[] {0.75, 0.55, 0.20};
        new PercentagesOfHalfPotPrizeStrategy(invalidPricesPercentages);
    }


}

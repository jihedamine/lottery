package org.jihedamine.prizestrategy;

import org.jihedamine.prizestrategy.exceptions.PrizeStrategyInvalidArgumentException;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * This class is an implementation of the {@link PrizeStrategy} interface.
 * <p>
 * PercentagesOfHalfPotPrizeStrategy takes an array of prize percentages representing
 * the percentages winners get from half of the total pot amount.
 * <p>
 * Percentages are expressed as double values ranging from 0 to 1, 0 being 0% and 1 being 100%.
 * <p>
 * The prize percentage at index i of the percentages array defines the percentage
 * of half the pot amount that the winner who came in position i wins.
 * <p>
 * The sum of the prize percentages must not exceed 1
 * <p>
 * If no prize percentage is specified for index i then the winner at position i gets no prize
 * (the value returned by {@link #getPrizeAmount(BigDecimal, int)} will be zero)
 * <p>
 * Example: If the percentages are 0.75, 0.15 and 0.1 and the total pot amount is 200$,
 * then the prizes would calculated as follows:
 * <pre>
 * <code> - getPrizeAmount(200, 0) = 75
 *  - getPrizeAmount(200, 1) = 15
 *  - getPrizeAmount(200, 2) = 10
 *  - getPrizeAmount(200, 3) = 0</code>
 * </pre>
 *
 * @author Jihed Amine Maaref on 24-Dec-16.
 */
public class PercentagesOfHalfPotPrizeStrategy implements PrizeStrategy {

    private final double[] prizesPercentages;

    /**
     * Constructs a PercentagesOfHalfPotPrizeStrategy, taking as parameter the prize percentages for each winner.
     * @param prizesPercentages Prize percentages expressed as double values ranging from 0 to 1, 0 being 0% and 1 being 100%.
     *                          The prize percentage at index i of the percentages array defines the percentage
     * of half the pot amount that the winner who came in position i wins.
     * @throws PrizeStrategyInvalidArgumentException if a prize percentage is not between 0 and 1
     * or if the sum of the prize percentages exceeds 1
     */
    public PercentagesOfHalfPotPrizeStrategy(double[] prizesPercentages) throws PrizeStrategyInvalidArgumentException {
        if (Arrays.stream(prizesPercentages).anyMatch(p -> p < 0 || p > 1)) {
            throw new PrizeStrategyInvalidArgumentException("Prize percentage must be between 0 and 1");
        }

        if (Arrays.stream(prizesPercentages).sum() > 1) {
            throw new PrizeStrategyInvalidArgumentException("Prize percentages sum must not exceed 1");
        }

        this.prizesPercentages = prizesPercentages;
    }

    /**
     * Returns <code>(potAmount / 2) * prizePercentages[winnerIndex]</code> if a prize percentage is specified for winnerIndex.
     * Otherwise, returns BigDecimal.ZERO.
     *
     * @param potAmount The total amount available for all the prizes
     * @param winnerIndex The index of the winner, zero being the index of the winner who came in first position
     * @return The prize amount for the prize winner specified in the winnerIndex parameter
     * or BigDecimal.ZERO if no prize percentage is specified for the winnerIndex.
     *
     * @throws PrizeStrategyInvalidArgumentException if potAmount is negative or winnerIndex is negative
     */
    @Override
    public BigDecimal getPrizeAmount(BigDecimal potAmount, int winnerIndex) throws PrizeStrategyInvalidArgumentException {
        // Pot amount is negative
        if (potAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new PrizeStrategyInvalidArgumentException("Prize amount cannot be negative");
        }

        // Invalid winner index
        if (winnerIndex < 0) {
            throw new PrizeStrategyInvalidArgumentException("Lottery winner index cannot be negative");
        }

        // No prize is specified for the winnerIndex passed as parameter
        if (winnerIndex > prizesPercentages.length - 1) {
            return BigDecimal.ZERO;
        }

        return potAmount.divide(new BigDecimal(2), BigDecimal.ROUND_FLOOR)
                .multiply(BigDecimal.valueOf(prizesPercentages[winnerIndex]));
    }
}

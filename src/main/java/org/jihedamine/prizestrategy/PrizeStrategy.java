package org.jihedamine.prizestrategy;

import java.math.BigDecimal;

/**
 * A Prize Strategy defines the prize amount for prize winners
 *
 * @author Jihed Amine Maaref on 24-Dec-16.
 */
public interface PrizeStrategy {

    /**
     * Returns the prize amount for a prize winner
     * @param potAmount The total amount available for all the prizes
     * @param winnerIndex The index of the winner, zero being the index of the winner who came in first position
     * @return The prize amount for the prize winner specified in the winnerIndex parameter
     */
    BigDecimal getPrizeAmount(BigDecimal potAmount, int winnerIndex);
}

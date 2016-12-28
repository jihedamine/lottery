package org.jihedamine.prizestrategy.exceptions;

import java.math.BigDecimal;

/**
 * Thrown when the {@link org.jihedamine.prizestrategy.PrizeStrategy} is passed invalid arguments
 * @see org.jihedamine.prizestrategy.PrizeStrategy#getPrizeAmount(BigDecimal, int)
 *
 * @author Jihed Amine Maaref on 25-Dec-16.
 */
public class PrizeStrategyInvalidArgumentException extends RuntimeException {

    public PrizeStrategyInvalidArgumentException(String message) {
        super(message);
    }

}

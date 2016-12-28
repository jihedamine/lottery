package org.jihedamine.lotterydraw.exceptions;

/**
 * Thrown when the initialization of a {@link org.jihedamine.lotterydraw.LotteryDraw} fails
 * @see org.jihedamine.lotterydraw.LotteryDraw
 *
 * @author Jihed Amine Maaref on 25-Dec-16.
 */
public class LotteryDrawInitializationException extends RuntimeException {

    public LotteryDrawInitializationException(String message) {
        super(message);
    }

}

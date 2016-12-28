package org.jihedamine.lotterydraw.exceptions;

/**
 * Thrown when the lottery draw already happened
 * and an operation that must not be executed after the draw has happened is executed.
 * @see org.jihedamine.lotterydraw.LotteryDraw
 *
 * @author Jihed Amine Maaref on 25-Dec-16.
 */
public class LotteryDrawAlreadyHappenedException extends RuntimeException {

    public LotteryDrawAlreadyHappenedException() {
        super("Lottery Draw already happened");
    }
}

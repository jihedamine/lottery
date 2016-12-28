package org.jihedamine.lotterydraw.exceptions;

/**
 * Thrown when the lottery draw did not happen
 * and an operation that must be executed only after the draw has happened is executed.
 * @see org.jihedamine.lotterydraw.LotteryDraw
 *
 * @author Jihed Amine Maaref on 25-Dec-16.
 */
public class LotteryDrawDidNotHappenException extends RuntimeException {

    public LotteryDrawDidNotHappenException() {
        super("Lottery draw didn't happen yet");
    }
}

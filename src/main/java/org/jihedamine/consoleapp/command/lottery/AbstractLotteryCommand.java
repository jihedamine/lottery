package org.jihedamine.consoleapp.command.lottery;

import org.jihedamine.consoleapp.command.AbstractCommand;
import org.jihedamine.lotterydraw.LotteryDraw;

/**
 * This class provides a skeletal implementation of a Lottery Command, which is a special type of {@link org.jihedamine.consoleapp.command.Command} that is related to a lottery draw.
 * <p>
 * This class defines a field to store the {@link LotteryDraw} instance on which the command acts.
 *
 * @author Jihed Amine Maaref on 24-Dec-16.
 */
public abstract class AbstractLotteryCommand extends AbstractCommand {
    /**
     * Instance of the LotteryDraw used as context in the execution of the Lottery Command.
     */
    protected LotteryDraw lotteryDraw;
}

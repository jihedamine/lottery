package org.jihedamine.consoleapp.command.lottery;

import org.jihedamine.lotterydraw.LotteryDraw;
import org.jihedamine.lotterydraw.exceptions.LotteryDrawDidNotHappenException;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This class is a Lottery Command.
 * It displays the winners of a lottery draw as defined by {@link LotteryDraw#getDrawWinnersAsString()}.
 * <p>
 * This command should not be called before the lottery draw has happened.
 * <p>
 * Sample usage:
 * <pre>
 * <code>{@value COMMAND_ID}
 *
 *             1st ball            2nd ball
 *         John: 12.35$       Eddie: 02.30$
 * </code>
 * </pre>
 *
 * @see LotteryDraw
 * @see AbstractLotteryCommand
 * @see org.jihedamine.consoleapp.command.Command
 *
 * @author Jihed Amine Maaref on 24-Dec-16.
 */
public class DisplayLotteryWinnersCommand extends AbstractLotteryCommand {

    /**
     * The unique identifier of a DisplayLotteryWinnersCommand
     */
    public static final String COMMAND_ID = "winners";

    private static final Logger LOG = Logger.getLogger(DisplayLotteryWinnersCommand.class.getName());

    /**
     * Constructs a DisplayLotteryWinnersCommand that displays the winners of the lottery draw parameter
     * @param lotteryDraw Lottery draw of which the command displays the winners
     */
    public DisplayLotteryWinnersCommand(LotteryDraw lotteryDraw) {
        this.lotteryDraw = lotteryDraw;
    }

    /**
     * Displays the lottery draw winners as defined by {@link LotteryDraw#getDrawWinnersAsString()}
     * @return A list of the lottery draw winners
     */
    @Override
    public String execute() {
        try {
            return lotteryDraw.getDrawWinnersAsString();
        } catch (LotteryDrawDidNotHappenException e) {
            LOG.log(Level.FINE, e.getMessage(), e);
            return e.getMessage();
        }
    }

    /**
     * {@inheritDoc}
     * @return {@value #COMMAND_ID} {@inheritDoc}
     */
    @Override
    public String getId() {
        return COMMAND_ID;
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "Displays the winners of the draw";
    }
}

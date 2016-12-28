package org.jihedamine.consoleapp.command.lottery;

import org.jihedamine.lotterydraw.LotteryDraw;
import org.jihedamine.lotterydraw.exceptions.LotteryDrawAlreadyHappenedException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is a Lottery Command.
 * It triggers a lottery draw as defined by {@link LotteryDraw#draw()}.
 * Then it displays the drawn items as defined by {@link LotteryDraw#getDrawnBallsAsString()}
 * <p>
 * If the lottery draw has already happened, the command will notify the user and will display the winning balls.
 * <p>
 * Sample usage:
 * <pre>
 * <code>{@value COMMAND_ID}
 *
 * Drawn ball: 1
 * Drawn ball: 2
 * </code>
 * </pre>
 *
 * @see LotteryDraw
 * @see AbstractLotteryCommand
 * @see org.jihedamine.consoleapp.command.Command
 *
 * @author Jihed Amine Maaref on 24-Dec-16.
 */
public class DrawLotteryCommand extends AbstractLotteryCommand {

    /**
     * The unique identifier of a DrawLotteryCommand
     */
    public static final String COMMAND_ID = "draw";

    private static final Logger LOG = Logger.getLogger(DrawLotteryCommand.class.getName());

    /**
     * Constructs a DrawLotteryCommand
     * @param lotteryDraw Lottery draw of which the draw is triggered by this command
     */
    public DrawLotteryCommand(LotteryDraw lotteryDraw) {
        this.lotteryDraw = lotteryDraw;
    }

    /**
     * Triggers a lottery draw as defined by {@link LotteryDraw#draw()}.
     * Then displays the drawn items as defined by {@link LotteryDraw#getDrawnBallsAsString()}
     * @return a String representing the drawn items as defined by {@link LotteryDraw#getDrawnBallsAsString()}
     */
    @Override
    public String execute() {
        try {
            lotteryDraw.draw();
            return lotteryDraw.getDrawnBallsAsString();
        } catch (LotteryDrawAlreadyHappenedException e) {
            LOG.log(Level.FINE, e.getMessage(), e);
            return e.getMessage() + System.lineSeparator() + lotteryDraw.getDrawnBallsAsString();
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
        return "Draws balls from the lottery bucket";
    }
}

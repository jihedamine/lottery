package org.jihedamine.consoleapp.command.lottery;

import org.jihedamine.bucket.exceptions.EmptyBucketException;
import org.jihedamine.lotterydraw.LotteryDraw;
import org.jihedamine.lotterydraw.exceptions.LotteryDrawAlreadyHappenedException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is a Lottery Command.
 * It triggers a lottery ticket purchase as defined by {@link LotteryDraw#purchaseTicket(String)}
 * This command should be called only before the lottery draw has happened and while tickets are still available.
 * If the command is called after the draw has happened, or after all the tickets are sold,
 * the user will be notified that he cannot purchase a ticket.
 * <p>
 * This command requires an argument which is the first name of the person who wants to purchase a lottery ticket
 * <p>
 * Sample usage:
 * <pre>
 * <code>{@value COMMAND_ID} Jihed
 *
 * Purchased number for Jihed: 1
 * </code>
 * </pre>
 * @see LotteryDraw
 * @see AbstractLotteryCommand
 * @see org.jihedamine.consoleapp.command.Command
 *
 * @author Jihed Amine Maaref on 24-Dec-16.
 */
public class PurchaseLotteryTicketCommand extends AbstractLotteryCommand {

    /**
     * The unique identifier of a PurchaseLotteryTicketCommand
     */
    public static final String COMMAND_ID = "purchase";

    private static final Logger LOG = Logger.getLogger(PurchaseLotteryTicketCommand.class.getName());

    /**
     * Constructs a PurchaseLotteryTicketCommand
     * @param lotteryDraw Lottery draw from which the ticket purchase is triggered by this command
     */
    public PurchaseLotteryTicketCommand(LotteryDraw lotteryDraw) {
        this.lotteryDraw = lotteryDraw;
    }

    /**
     * Triggers a lottery ticket purchase as defined by {@link LotteryDraw#purchaseTicket(String)}
     * @return A confirmation message with the value of the purchased lottery item
     * if the method is called before the lottery draw has happened and while tickets are still available.
     * Otherwise, a message indicating that the purchase cannot happen is returned.
     */
    @Override
    public String execute() {
        if (args == null || args.length < 1) {
            return "Missing participant first name argument (usage: purchase <first name of participant>)";
        }

        String participantFirstName = args[0];

        try {
            Object ballNumber = lotteryDraw.purchaseTicket(participantFirstName);
            return String.format("Purchased number for %s: %s%n", participantFirstName, ballNumber.toString());
        } catch (LotteryDrawAlreadyHappenedException | EmptyBucketException e) {
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
        return "Assigns a lottery ticket to the participant whose first name is passed as argument " +
                "(purchase <first name of the participant>)";
    }
}

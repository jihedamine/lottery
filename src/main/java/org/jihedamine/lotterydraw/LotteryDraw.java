package org.jihedamine.lotterydraw;

import org.jihedamine.bucket.Bucket;
import org.jihedamine.bucket.exceptions.EmptyBucketException;
import org.jihedamine.lotterydraw.exceptions.LotteryDrawAlreadyHappenedException;
import org.jihedamine.lotterydraw.exceptions.LotteryDrawDidNotHappenException;
import org.jihedamine.lotterydraw.exceptions.LotteryDrawInitializationException;
import org.jihedamine.prizestrategy.PrizeStrategy;
import org.jihedamine.util.StringFormattingUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * A lottery draw is a lottery event where participants purchase tickets referring each to a distinct ball number
 * from a set of numbered balls of the lottery event.
 * <p>
 * The tickets are numbered the same way as the balls of the lottery event.
 * The lottery balls and the corresponding tickets are constructed from a Bucket.
 * <p>
 * The lottery draw has an initial pot of money.
 * A participant purchases a ticket for the lottery draw by providing his first name as a parameter to {@link #purchaseTicket(String)}.
 * When a participant purchases a ticket, the ticket price is added to the lottery draw's pot.
 * When a participant purchases a ticket, {@link #purchaseTicket(String)} returns the number of the ball he purchased a ticket for.
 * <p>
 * A Lottery draw event consists of randomly picking a subset of balls from the lottery event balls.
 * One lottery draw event has one and only one draw. The draw sets the drawn balls and the winners of the lottery draw.
 * The draw operation is triggered with {@link #draw()}.
 * {@link #getDrawnBalls()} returns the numbers of the balls picked in the draw.
 * <p>
 * Once a draw happens, tickets can no longer be purchased for that lottery event.
 * <p>
 * The winners of the lottery draw are the participants who purchased tickets referring to the balls
 * that were picked in the draw.
 * <p>
 * The prize amount for each winner depends on the prize strategy defined for the lottery draw.
 * <p>
 * The lottery draw is able to display the first names of the winners of the draw and their prizes amounts with {@link #getDrawWinnersAsString()}.
 *
 * @see Bucket
 *
 * @author Jihed Amine Maaref on 24-Dec-16.
 */
public class LotteryDraw {

    private Bucket<Integer> ballsMachine;
    private Bucket<Integer> tickets;

    private final int nbDraws;

    private BigDecimal pot;
    private BigDecimal ticketPrice;

    // Maps the ball number of the ticket purchased by a lottery draw participant
    // to the firstName of that participant
    private Map<Integer, String> purchasedBallsMap;

    // Stores the internal state of the lottery draw
    // i.e. whether the draw happened or not
    private boolean drawHappened;

    private final int[] drawnBalls;

    private PrizeStrategy prizeStrategy;

    /**
     * Constructs a lottery draw event by defining the balls numbers, instantiating the lottery draw tickets,
     * setting an initial pot amount, a ticket price and a prize strategy
     * @param bucket Bucket of integer items used to create the set of balls and tickets for the lottery draw
     * @param nbDraws The number of balls that will be drawn in the lottery draw
     * @param pot The initial amount of this lottery draw pot
     * @param ticketPrice The price of a ticket to participate in this lottery draw
     * @param prizeStrategy The prize strategy used to calculate prizes for the lottery draw winners
     * @throws LotteryDrawInitializationException if any parameter value used to construct the lottery draw is invalid
     */
    public LotteryDraw(Bucket<Integer> bucket,
                       int nbDraws,
                       double pot,
                       double ticketPrice,
                       PrizeStrategy prizeStrategy) throws LotteryDrawInitializationException {
        // Check that the parameters used to construct the lottery draw have valid values
        checkParametersValidity(bucket, nbDraws, pot, prizeStrategy);

        // The balls machine and the tickets buckets do not point to the same reference
        // as the bucket parameter used to construct the lottery draw
        // because they each have their own items list
        // and they must not be modifiable other than by calling the purchaseTicket and draw methods.
        this.ballsMachine = bucket.getCopy();
        this.tickets = bucket.getCopy();

        this.nbDraws = nbDraws;
        this.drawnBalls = new int[nbDraws];

        this.pot = BigDecimal.valueOf(pot);
        this.ticketPrice = BigDecimal.valueOf(ticketPrice);

        this.purchasedBallsMap = new HashMap<>();

        this.prizeStrategy = prizeStrategy;
    }

    private void checkParametersValidity(Bucket<Integer> bucket, int nbDraws, double pot, PrizeStrategy prizeStrategy)
            throws LotteryDrawInitializationException {
        if (bucket == null) {
            throw new LotteryDrawInitializationException("Items bucket can't be null");
        }
        if (bucket.size() <= 0 || nbDraws <= 0) {
            throw new LotteryDrawInitializationException("Bucket size or number of draws must be > 0");
        }
        if (bucket.size() < nbDraws) {
            throw new LotteryDrawInitializationException("Can't draw more items than available in the bucket");
        }
        if (pot <= 0) {
            throw new LotteryDrawInitializationException("Initial pot amount can't be negative");
        }
        if (prizeStrategy == null) {
            throw new LotteryDrawInitializationException("Prize strategy can't be null");
        }
    }

    /**
     * Gives a ticket from this lottery draws ticket bucket to the participant identified by his first name.
     * Adds the ticket price amount to this lottery draw pot.
     *
     * @param firstName The first name of the lottery participant who purchases the ticket
     * @return The number of the lottery ball this ticket refers to
     * @throws LotteryDrawAlreadyHappenedException if the lottery draw has already happened for this LotteryDraw instance
     * @throws EmptyBucketException if no more tickets are available for sale for this LotteryDraw instance
     */
    public int purchaseTicket(String firstName) throws LotteryDrawAlreadyHappenedException, EmptyBucketException {
        if (drawHappened) {
            throw new LotteryDrawAlreadyHappenedException();
        }

        int purchasedBallNumber = tickets.pickItem();

        purchasedBallsMap.put(purchasedBallNumber, firstName);
        pot = pot.add(ticketPrice);

        return purchasedBallNumber;
    }

    /**
     * Randomly picks a subset of balls from the lottery event balls.
     * The draw sets the drawn balls and the winners of this LotteryDraw instance.
     * One {@link #draw()} has been called for a LotteryDraw instance, the next calls to the {@link #draw()} will throw a {@link LotteryDrawAlreadyHappenedException}.
     * @throws LotteryDrawAlreadyHappenedException if the lottery draw has already happened for this LotteryDraw instance
     */
    public void draw() throws LotteryDrawAlreadyHappenedException {
        if (drawHappened) {
           throw new LotteryDrawAlreadyHappenedException();
        }

        IntStream.range(0, nbDraws).forEachOrdered(
                i -> drawnBalls[i] = ballsMachine.pickItem()
        );

        drawHappened = true;
    }

    /**
     * Returns a String listing the numbers of the balls that were drawn for this LotteryDraw instance
     * @return A String listing the numbers of the balls that were drawn for this LotteryDraw instance
     * @throws LotteryDrawDidNotHappenException if {@link #draw()} was never called on this LotteryDraw instance
     */
    public String getDrawnBallsAsString() throws LotteryDrawDidNotHappenException {
        if (!drawHappened) {
            throw new LotteryDrawDidNotHappenException();
        }

        StringBuilder sb = new StringBuilder();
        IntStream.range(0, nbDraws).forEachOrdered(i ->
                sb.append("Drawn ball: ").append(drawnBalls[i]).append(System.lineSeparator())
        );

        // deleted unneeded last line separator
        sb.delete(sb.lastIndexOf(System.lineSeparator()), sb.length());

        return sb.toString();
    }

    /**
     * Returns a String listing the winners of the draw with the prize amount for each winner.
     * <p>
     * Example:
     * The lottery draw picks 2 items.
     * <p>
     * The participant with first name 'Jihed' purchased a ticket referring to the number of the ball that was picked first.
     * The participant with first name 'Aroua' purchased a ticket referring to the number of the ball that was picked second.
     * <p>
     * The prize strategy for this lottery draw calculates that the prizes for the winners which ball numbers were picked first and second
     * are 50.50$ and 15.00$, respectively.
     * <p>
     * If no participant purchased a ticket referring to a ball that was picked in the draw, 'No winner' is displayed instead of the winner's first name.
     * <code>
     *             1st ball            2nd ball
     *        Jihed: 50.50$       Aroua: 15.00$
     * </code>
     * @return A String listing the winners of the draw with the prize amount for each winner.
     * @throws LotteryDrawDidNotHappenException if {@link #draw()} was never called on this LotteryDraw instance
     */
    public String getDrawWinnersAsString() throws LotteryDrawDidNotHappenException {
        if (!drawHappened) {
            throw new LotteryDrawDidNotHappenException();
        }

        StringBuilder sb = new StringBuilder();

        // Pad the first line to be aligned with the second line
        IntStream.range(0, nbDraws).forEachOrdered(i ->
                sb.append(String.format("%20s", StringFormattingUtil.getOrdinal(i + 1) + " ball"))
        );

        sb.append(System.lineSeparator());

        DecimalFormat decimalFormat = new DecimalFormat("00.00$");
        IntStream.range(0, nbDraws).forEachOrdered(i -> {
            String winner = purchasedBallsMap.getOrDefault(drawnBalls[i], "No winner");
            BigDecimal prizeAmount = prizeStrategy.getPrizeAmount(pot, i);
            // if no prize amount is returned, display zero
            if (prizeAmount == null) {
                prizeAmount = BigDecimal.ZERO;
            }
            // Pad the second line to be aligned with the first line
            sb.append(String.format("%14s", winner + ": "));
            sb.append(decimalFormat.format(prizeAmount));
        });

        return sb.toString();
    }

    /**
     * Returns the numbers of the balls that were drawn in this LotteryDraw
     * @return The numbers of the balls that were drawn in this LotteryDraw
     */
    public int[] getDrawnBalls() {
        // Create a copy of the drawnBalls instance so that the calling client is not able
        // to tamper with the balls that were drawn in this LotteryDraw.
        return Arrays.copyOf(drawnBalls, drawnBalls.length);
    }

    /**
     * Returns the number of balls that will be drawn in this lottery draw.
     * @return The number of balls that will be drawn in this lottery draw.
     */
    public int getNbDraws() {
        return nbDraws;
    }

    /**
     * Returns the number of balls used for this lottery draw.
     * @return The number of balls used for this lottery draw.
     */
    public int getNbItems() {
        return ballsMachine.size();
    }

    /**
     * Returns the pot amount that this lottery draw currently has.
     * @return The pot amount that this lottery draw currently has.
     */
    public BigDecimal getPot() {
        return pot;
    }

    /**
     * Returns the numbers of the tickets purchased by the participants to this lottery draw
     * mapped to the first names of the corresponding participants
     * @return The numbers of the tickets purchased by the participants to this lottery draw
     * mapped to the first names of the corresponding participants
     */
    public Map<Integer, String> getPurchasedBallsMap() {
        return new HashMap<>(purchasedBallsMap);
    }
}

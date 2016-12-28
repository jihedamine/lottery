package org.jihedamine;

import org.jihedamine.bucket.Bucket;
import org.jihedamine.bucket.SequentialNumbersBucket;
import org.jihedamine.consoleapp.LotteryApplication;
import org.jihedamine.lotterydraw.LotteryDraw;
import org.jihedamine.prizestrategy.PercentagesOfHalfPotPrizeStrategy;
import org.jihedamine.prizestrategy.PrizeStrategy;

/**
 * Main application that triggers a lottery draw
 *
 * @author Jihed Amine Maaref on 24-Dec-16.
 */
public class MainApplication {
    private MainApplication() {}

    public static void main(String[] args) {
        // the initial number to start the sequence of lottery balls
        int startIndex = 1;

        // the number of balls in the lottery draw
        int nbItems = 50;

        // the number of balls to randomly pick during the draw
        int nbDraws = 3;

        // the initial pot amount in dollars
        double initialPot = 200;

        // the price of a lottery ticket in dollars
        double ticketPrice = 10;

        // the price percentages to use for the prize strategy
        double[] pricesPercentages = new double[] {0.75, 0.15, 0.10};

        // if a first argument is provided, it is used as a custom startIndex value
        if (args.length >= 1) {
            startIndex = Integer.valueOf(args[0]);
        }

        // if a second argument is provided, it is used as a custom nbItems value
        if (args.length >= 2) {
            nbItems = Integer.valueOf(args[1]);
        }

        // if a third argument is provided, it is used as a custom nbDraws value
        if (args.length >= 3) {
            nbDraws = Integer.valueOf(args[2]);
        }

        // if a fourth argument is provided, it is used as a custom initialPot value
        if (args.length >= 4) {
            initialPot = Double.valueOf(args[3]);
        }

        // if a fifth argument is provided, it is used as a custom ticketPrice value
        if (args.length >= 5) {
            ticketPrice = Double.valueOf(args[4]);
        }

        Bucket<Integer> bucket = new SequentialNumbersBucket(startIndex, nbItems);

        PrizeStrategy prizeStrategy = new PercentagesOfHalfPotPrizeStrategy(pricesPercentages);

        LotteryDraw lotteryDraw = new LotteryDraw(bucket, nbDraws, initialPot, ticketPrice, prizeStrategy);

        LotteryApplication lotteryApplication = new LotteryApplication(lotteryDraw);

        lotteryApplication.run();
    }
}

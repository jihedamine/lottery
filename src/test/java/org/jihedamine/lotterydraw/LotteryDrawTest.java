package org.jihedamine.lotterydraw;

import org.jihedamine.bucket.Bucket;
import org.jihedamine.bucket.SequentialNumbersBucket;
import org.jihedamine.bucket.exceptions.EmptyBucketException;
import org.jihedamine.lotterydraw.exceptions.LotteryDrawAlreadyHappenedException;
import org.jihedamine.lotterydraw.exceptions.LotteryDrawDidNotHappenException;
import org.jihedamine.lotterydraw.exceptions.LotteryDrawInitializationException;
import org.jihedamine.prizestrategy.PrizeStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * @author Jihed Amine Maaref on 25-Dec-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class LotteryDrawTest {

    @Mock
    Bucket<Integer> mockBucket;

    @Mock
    PrizeStrategy mockPrizeStrategy;

    @Test(expected=LotteryDrawInitializationException.class)
    public void testExceptionThrownWhenBucketIsNull() {
        new LotteryDraw(null, 3, 200, 10, mockPrizeStrategy);
    }

    @Test(expected=LotteryDrawInitializationException.class)
    public void testExceptionThrownWhenPriceStrategyIsNull() {
        when(mockBucket.size()).thenReturn(4);
        new LotteryDraw(mockBucket, 3, 200, 10, null);
    }

    @Test(expected=LotteryDrawInitializationException.class)
    public void testExceptionThrownWhenBucketIsEmpty() {
        when(mockBucket.size()).thenReturn(0);
        new LotteryDraw(mockBucket, 3, 200, 10, mockPrizeStrategy);
    }

    @Test(expected=LotteryDrawInitializationException.class)
    public void testExceptionThrownWhenBucketSizeLessThanNbDraws() {
        when(mockBucket.size()).thenReturn(2);
        new LotteryDraw(mockBucket, 3, 200, 10, mockPrizeStrategy);
    }

    @Test(expected=LotteryDrawInitializationException.class)
    public void testExceptionThrownWhenInitialPotIsNegative() {
        when(mockBucket.size()).thenReturn(5);
        new LotteryDraw(mockBucket, 3, -200, 10, mockPrizeStrategy);
    }

    @Test(expected=LotteryDrawInitializationException.class)
    public void testExceptionThrownWhenBucketSizeIsNegative() {
        when(mockBucket.size()).thenReturn(-2);
        new LotteryDraw(mockBucket, 3, 200, 10, mockPrizeStrategy);
    }

    @Test
    public void testLotteryDrawInitializedWithValidParameters() {
        Bucket<Integer> bucket = new SequentialNumbersBucket(1, 5);
        LotteryDraw lotteryDraw = new LotteryDraw(bucket, 3, 200, 10, mockPrizeStrategy);
        assertEquals(5, lotteryDraw.getNbItems());
        assertEquals(3, lotteryDraw.getNbDraws());
        assertTrue(BigDecimal.valueOf(200).compareTo(lotteryDraw.getPot()) == 0);
    }

    @Test
    public void testPurchaseTicket() {
        String participantFirstName = "Jihed";
        Bucket<Integer> bucket = new SequentialNumbersBucket(1, 5);
        LotteryDraw lotteryDraw = new LotteryDraw(bucket, 3, 200, 10, mockPrizeStrategy);
        lotteryDraw.purchaseTicket(participantFirstName);
        assertTrue(BigDecimal.valueOf(210).compareTo(lotteryDraw.getPot()) == 0);
        assertEquals(1, lotteryDraw.getPurchasedBallsMap().size());
        assertTrue(lotteryDraw.getPurchasedBallsMap().values().contains(participantFirstName));
    }

    @Test
    public void testDrawWithoutPurchase() {
        Bucket<Integer> bucket = new SequentialNumbersBucket(1, 5);
        LotteryDraw lotteryDraw = new LotteryDraw(bucket, 3, 200, 10, mockPrizeStrategy);
        lotteryDraw.draw();
        assertEquals(2, lotteryDraw.getNbItems());
        assertEquals(3, lotteryDraw.getDrawnBalls().length);
    }

    @Test
    public void testDrawWithPurchase() {
        String participantFirstName = "Jihed";
        Bucket<Integer> bucket = new SequentialNumbersBucket(1, 5);
        LotteryDraw lotteryDraw = new LotteryDraw(bucket, 3, 200, 10, mockPrizeStrategy);
        lotteryDraw.purchaseTicket(participantFirstName);
        lotteryDraw.draw();
        assertEquals(2, lotteryDraw.getNbItems());
        assertEquals(3, lotteryDraw.getDrawnBalls().length);
    }

    @Test
    public void testDrawAllItems() {
        Bucket<Integer> bucket = new SequentialNumbersBucket(1, 5);
        LotteryDraw lotteryDraw = new LotteryDraw(bucket, 5, 200, 10, mockPrizeStrategy);
        lotteryDraw.draw();
        assertEquals(0, lotteryDraw.getNbItems());
        assertEquals(5, lotteryDraw.getDrawnBalls().length);
        assertTrue(IntStream.of(lotteryDraw.getDrawnBalls()).anyMatch(x -> x == 1));
        assertTrue(IntStream.of(lotteryDraw.getDrawnBalls()).anyMatch(x -> x == 2));
        assertTrue(IntStream.of(lotteryDraw.getDrawnBalls()).anyMatch(x -> x == 3));
        assertTrue(IntStream.of(lotteryDraw.getDrawnBalls()).anyMatch(x -> x == 4));
        assertTrue(IntStream.of(lotteryDraw.getDrawnBalls()).anyMatch(x -> x == 5));
    }

    @Test
    public void testDrawnBallsAsString() {
        Bucket<Integer> bucket = new SequentialNumbersBucket(1, 2);
        LotteryDraw lotteryDraw = new LotteryDraw(bucket, 2, 200, 10, mockPrizeStrategy);
        lotteryDraw.draw();
        String expected1 = "Drawn ball: 2" + System.lineSeparator() + "Drawn ball: 1";
        String expected2 = "Drawn ball: 1" + System.lineSeparator() + "Drawn ball: 2";
        assertThat(lotteryDraw.getDrawnBallsAsString(), anyOf(is(expected1), is(expected2)));
    }

    @Test
    public void testDisplayWinnersNoWinners() {
        Bucket<Integer> bucket = new SequentialNumbersBucket(1, 5);
        when(mockPrizeStrategy.getPrizeAmount(any(BigDecimal.class), eq(0))).thenReturn(BigDecimal.valueOf(12.35));
        when(mockPrizeStrategy.getPrizeAmount(any(BigDecimal.class), eq(1))).thenReturn(BigDecimal.valueOf(2.3));
        LotteryDraw lotteryDraw = new LotteryDraw(bucket, 2, 200, 10, mockPrizeStrategy);
        lotteryDraw.draw();
        String expected = "            1st ball            2nd ball" + System.lineSeparator() + "   No winner: 12.35$   No winner: 02.30$";
        assertEquals(expected, lotteryDraw.getDrawWinnersAsString());
    }

    @Test
    public void testDisplayWinnersTwoWinners() {
        Bucket<Integer> bucket = new SequentialNumbersBucket(1, 2);
        when(mockPrizeStrategy.getPrizeAmount(any(BigDecimal.class), eq(0))).thenReturn(BigDecimal.valueOf(12.35));
        when(mockPrizeStrategy.getPrizeAmount(any(BigDecimal.class), eq(1))).thenReturn(BigDecimal.valueOf(2.3));
        LotteryDraw lotteryDraw = new LotteryDraw(bucket, 2, 200, 10, mockPrizeStrategy);
        lotteryDraw.purchaseTicket("Jihed");
        lotteryDraw.purchaseTicket("Aroua");
        lotteryDraw.draw();
        String expected1 = "            1st ball            2nd ball" + System.lineSeparator() + "       Jihed: 12.35$       Aroua: 02.30$";
        String expected2 = "            1st ball            2nd ball" + System.lineSeparator() + "       Aroua: 12.35$       Jihed: 02.30$";
        assertThat(lotteryDraw.getDrawWinnersAsString(), anyOf(is(expected1), is(expected2)));
    }

    @Test
    public void testDisplayWinnersPrizeAmountIsNull() {
        Bucket<Integer> bucket = new SequentialNumbersBucket(1, 2);
        when(mockPrizeStrategy.getPrizeAmount(any(BigDecimal.class), eq(0))).thenReturn(BigDecimal.valueOf(12.35));
        LotteryDraw lotteryDraw = new LotteryDraw(bucket, 2, 200, 10, mockPrizeStrategy);
        lotteryDraw.draw();
        String expected = "            1st ball            2nd ball" + System.lineSeparator() + "   No winner: 12.35$   No winner: 00.00$";
        assertEquals(expected, lotteryDraw.getDrawWinnersAsString());
    }

    @Test
    public void testCannotAlterLotteryDrawUsingBucket() {
        Bucket<Integer> bucket = new SequentialNumbersBucket(1, 2);
        LotteryDraw lotteryDraw = new LotteryDraw(bucket, 2, 200, 10, mockPrizeStrategy);
        bucket.pickItem();
        bucket.pickItem();
        lotteryDraw.draw();
        String expected = "            1st ball            2nd ball" + System.lineSeparator() + "   No winner: 00.00$   No winner: 00.00$";
        assertEquals(expected, lotteryDraw.getDrawWinnersAsString());
    }

    @Test(expected = EmptyBucketException.class)
    public void testPurchaseMoreThanAvailable() {
        Bucket<Integer> bucket = new SequentialNumbersBucket(1, 2);
        LotteryDraw lotteryDraw = new LotteryDraw(bucket, 2, 200, 10, mockPrizeStrategy);
        lotteryDraw.purchaseTicket("Jihed");
        lotteryDraw.purchaseTicket("Aroua");
        lotteryDraw.purchaseTicket("Zeineb");
    }

    @Test(expected = LotteryDrawDidNotHappenException.class)
    public void testDisplayWinnersBeforeDraw() {
        Bucket<Integer> bucket = new SequentialNumbersBucket(1, 2);
        LotteryDraw lotteryDraw = new LotteryDraw(bucket, 2, 200, 10, mockPrizeStrategy);
        lotteryDraw.getDrawWinnersAsString();
    }

    @Test(expected = LotteryDrawDidNotHappenException.class)
    public void testDisplayDrawnBallsBeforeDraw() {
        Bucket<Integer> bucket = new SequentialNumbersBucket(1, 2);
        LotteryDraw lotteryDraw = new LotteryDraw(bucket, 2, 200, 10, mockPrizeStrategy);
        lotteryDraw.getDrawnBallsAsString();
    }

    @Test(expected = LotteryDrawAlreadyHappenedException.class)
    public void testPurchaseAfterDraw() {
        Bucket<Integer> bucket = new SequentialNumbersBucket(1, 2);
        LotteryDraw lotteryDraw = new LotteryDraw(bucket, 2, 200, 10, mockPrizeStrategy);
        lotteryDraw.draw();
        lotteryDraw.purchaseTicket("Jihed");
    }

    @Test(expected = LotteryDrawAlreadyHappenedException.class)
    public void testDrawMoreThanOnce() {
        Bucket<Integer> bucket = new SequentialNumbersBucket(1, 2);
        LotteryDraw lotteryDraw = new LotteryDraw(bucket, 2, 200, 10, mockPrizeStrategy);
        lotteryDraw.draw();
        lotteryDraw.draw();
    }

}

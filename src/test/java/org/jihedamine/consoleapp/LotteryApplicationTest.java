package org.jihedamine.consoleapp;

import org.jihedamine.bucket.Bucket;
import org.jihedamine.bucket.SequentialNumbersBucket;
import org.jihedamine.lotterydraw.LotteryDraw;
import org.jihedamine.prizestrategy.PrizeStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author Jihed Amine Maaref on 25-Dec-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class LotteryApplicationTest {

    @Mock
    PrizeStrategy mockPrizeStrategy;

    @Test
    public void testLotteryApplicationInstantiation() {
        Bucket<Integer> bucket = new SequentialNumbersBucket(1, 5);
        LotteryDraw lotteryDraw = new LotteryDraw(bucket, 5, 100, 10, mockPrizeStrategy);
        new LotteryApplication(lotteryDraw);
    }
}

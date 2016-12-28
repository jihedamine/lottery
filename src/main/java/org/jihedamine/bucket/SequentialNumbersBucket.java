package org.jihedamine.bucket;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class is an implementation of the Bucket interface.
 * Its items are integers that form a continuous sequence of n items, n being the size of the Bucket
 * <p>
 * Example: <code>SequentialNumbersBucket(2, 5)</code> is a bucket that contains 5 integer items
 * which form a sequence starting from 2 and having 5 items. The bucket items are: <code>2, 3, 4, 5, 6</code>.
 *
 * @author Jihed Amine Maaref on 24-Dec-16.
 */
public class SequentialNumbersBucket extends AbstractBucket<Integer> {

    private SequentialNumbersBucket() {
        // used by the getCopy method to instantiate a copy of the current bucket
    }

    /**
     * Constructs a bucket of integer items that form a sequence
     * <p>
     * Example: <code>SequentialNumbersBucket(2, 5)</code> is a bucket that contains 5 integer items
     * which form a sequence starting from the value 2 and having 5 items.
     * The bucket items are: <code>2, 3, 4, 5, 6</code>.
     * @param firstValue The first Integer value of the sequence of bucket items
     * @param nbItems The number of bucket items
     */
    public SequentialNumbersBucket(Integer firstValue, Integer nbItems) {
        items = IntStream.range(firstValue, firstValue + nbItems).boxed().collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public SequentialNumbersBucket getCopy() {
        SequentialNumbersBucket bucketPicker = new SequentialNumbersBucket();
        bucketPicker.items = new ArrayList<>(this.items);
        return bucketPicker;
    }

}

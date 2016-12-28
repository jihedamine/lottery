package org.jihedamine.bucket;

import org.jihedamine.bucket.exceptions.EmptyBucketException;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class provides a skeletal implementation of the Bucket interface to minimize the efforts to implement this interface
 * <p>
 * This class provides an items list to store the bucket items
 * as well as default implementations for {@link Bucket#pickItem()} and {@link Bucket#size()}
 *
 * @author Jihed Amine Maaref on 24-Dec-16.
 */
public abstract class AbstractBucket<T> implements Bucket<T> {

    /**
     * List containing the items of the bucket
     */
    protected List<T> items;

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     * @throws EmptyBucketException {@inheritDoc}
     */
    @Override
    public T pickItem() throws EmptyBucketException {
        if (items.isEmpty()) {
            throw new EmptyBucketException();
        }

        int ballIndex = ThreadLocalRandom.current().nextInt(0, items.size());
        return items.remove(ballIndex);
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public int size() {
        return items.size();
    }
}

package org.jihedamine.bucket;

import org.jihedamine.bucket.exceptions.EmptyBucketException;

/**
 * A Bucket is a container from which we can pick items.
 * <p>
 * Each time an item is picked from the bucket, the bucket size is reduced by one.
 *
 * @author Jihed Amine Maaref on 25-Dec-16.
 */
public interface Bucket<T> {
    /**
     * Creates a new bucket instance which items are identical copies of the calling bucket items.
     * @return New bucket instance which items are identical copies of the calling bucket items.
     */
    Bucket<T> getCopy();

    /**
     * Gets a random item from the bucket. The item is removed from the bucket items list.
     * @return The item that was picked from the bucket.
     * @throws EmptyBucketException if the bucket doesn't have any items.
     */
    T pickItem() throws EmptyBucketException;

    /**
     * Returns the number of items in the bucket.
     * @return Number of items in the bucket
     */
    int size();
}

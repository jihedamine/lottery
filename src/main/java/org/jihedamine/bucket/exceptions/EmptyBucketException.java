package org.jihedamine.bucket.exceptions;

/**
 * Thrown when the bucket does not have items and an operation tries to pick items from the bucket.
 *
 * @see org.jihedamine.bucket.Bucket
 *
 * @author Jihed Amine Maaref on 24-Dec-16.
 */
public class EmptyBucketException extends RuntimeException {

    public EmptyBucketException() {
        super("The bucket does not have any items");
    }
}

package org.jihedamine.bucket;

import org.jihedamine.bucket.exceptions.EmptyBucketException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Jihed Amine Maaref on 25-Dec-16.
 */
public class SequentialNumbersBucketTest {

    @Test
    public void testSequentialNumbersBucketStartingFromOne() {
        Bucket<Integer> bucket = new SequentialNumbersBucket(1, 3);
        List<Integer> items = new ArrayList<>();
        items.add(bucket.pickItem());
        items.add(bucket.pickItem());
        items.add(bucket.pickItem());
        Collections.sort(items);
        List<Integer> expected = Arrays.asList(1, 2, 3);
        assertThat(items, is(expected));
    }

    @Test
    public void testSequentialNumbersBucketNotStartingFromOne() {
        Bucket<Integer> bucket = new SequentialNumbersBucket(5, 3);
        List<Integer> items = new ArrayList<>();
        items.add(bucket.pickItem());
        items.add(bucket.pickItem());
        items.add(bucket.pickItem());
        Collections.sort(items);
        List<Integer> expected = Arrays.asList(5, 6, 7);
        assertThat(items, is(expected));
    }

    @Test(expected = EmptyBucketException.class)
    public void testPickMoreItemsThanAvailable() {
        Bucket<Integer> bucket = new SequentialNumbersBucket(1, 2);
        bucket.pickItem();
        bucket.pickItem();
        bucket.pickItem();
    }

    @Test(expected = EmptyBucketException.class)
    public void testCreateBucketWithNegativeNbElements() {
        Bucket<Integer> bucket = new SequentialNumbersBucket(1, -2);
        bucket.pickItem();
    }
}

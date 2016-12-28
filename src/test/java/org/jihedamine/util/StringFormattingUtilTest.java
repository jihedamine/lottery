package org.jihedamine.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Jihed Amine Maaref on 25-Dec-16.
 */
public class StringFormattingUtilTest {

    @Test
    public void testGetOrdinal() {
        assertEquals("1st", StringFormattingUtil.getOrdinal(1));
        assertEquals("2nd", StringFormattingUtil.getOrdinal(2));
        assertEquals("3rd", StringFormattingUtil.getOrdinal(3));
        assertEquals("4th", StringFormattingUtil.getOrdinal(4));
        assertEquals("11th", StringFormattingUtil.getOrdinal(11));
        assertEquals("21st", StringFormattingUtil.getOrdinal(21));
        assertEquals("22nd", StringFormattingUtil.getOrdinal(22));
        assertEquals("154th", StringFormattingUtil.getOrdinal(154));
    }
}

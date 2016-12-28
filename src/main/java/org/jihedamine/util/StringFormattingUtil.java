package org.jihedamine.util;

/**
 * Utility class defining helper methods for String formatting
 *
 * @author Jihed Amine Maaref on 24-Dec-16.
 */
public class StringFormattingUtil {

    private StringFormattingUtil() {
        // Constructor is private as utility class shouldn't be instantiated
    }

    /**
     * Returns the ordinal of the number passed as parameter in english language.
     * <p>
     * Examples:
     * <pre>
     * <code>- getOrdinal(0): "0th"
     * - getOrdinal(1): "1st"
     * - getOrdinal(15): "15th"
     * - getOrdinal(232): "232nd"</code></pre>
     * @param number A number to get the ordinal of.
     * @return The ordinal of the number passed as parameter in english language.
     */
    public static String getOrdinal(int number) {
        String[] suffixes = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        switch (number % 100) {
            case 11:
            case 12:
            case 13:
                return number + "th";
            default:
                return number + suffixes[number % 10];

        }
    }

}

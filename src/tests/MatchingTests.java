package tests;

import algorithms.KMP;
import algorithms.NaiveSearch;
import algorithms.RabinKarp;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MatchingTests {

    private static void checkAll(String name, String text, String pattern, List<Integer> expected) {
        TestUtil.checkEquals(name + " [Naive]", expected, NaiveSearch.search(text, pattern));
        TestUtil.checkEquals(name + " [KMP]", expected, KMP.search(text, pattern));
        TestUtil.checkEquals(name + " [Rabin-Karp]", expected, RabinKarp.search(text, pattern));
        TestUtil.checkEquals(name + " [Rabin-Karp, mod=7]", expected, RabinKarp.search(text, pattern, 7));
    }

    public static void run() {
        System.out.println("=== String matching tests ===");

        List<Integer> none = Collections.emptyList();

        checkAll("pattern at the beginning", "abcxxxxx", "abc", Arrays.asList(0));
        checkAll("pattern in the middle", "xxxabcxxx", "abc", Arrays.asList(3));
        checkAll("pattern at the end", "xxxxxabc", "abc", Arrays.asList(5));
        checkAll("multiple occurrences", "abababab", "ab", Arrays.asList(0, 2, 4, 6));
        checkAll("overlapping occurrences", "aaaa", "aa", Arrays.asList(0, 1, 2));
        checkAll("pattern absent", "abcdefgh", "xyz", none);
        checkAll("pattern longer than text", "abc", "abcdef", none);
        checkAll("pattern equals text", "abc", "abc", Arrays.asList(0));
        checkAll("empty pattern", "abc", "", none);
        checkAll("empty text", "", "abc", none);
        checkAll("classic KMP example", "ABABDABACDABABCABAB", "ABABCABAB", Arrays.asList(10));

        TestUtil.checkEquals("KMP LPS of abab", Arrays.toString(new int[]{0, 0, 1, 2}),
                Arrays.toString(KMP.buildLps("abab")));
        TestUtil.checkEquals("KMP LPS of aabaaab", Arrays.toString(new int[]{0, 1, 0, 1, 2, 2, 3}),
                Arrays.toString(KMP.buildLps("aabaaab")));
    }

    public static void main(String[] args) {
        run();
        TestUtil.finish();
    }
}
package tests;

import algorithms.EditDistance;
import algorithms.LCS;

public class DpTests {

    public static void run() {
        System.out.println("=== LCS tests ===");
        TestUtil.checkEquals("LCS identical strings", 6, LCS.lcsLength("abcdef", "abcdef"));
        TestUtil.checkEquals("LCS completely different", 0, LCS.lcsLength("abc", "xyz"));
        TestUtil.checkEquals("LCS first string empty", 0, LCS.lcsLength("", "abc"));
        TestUtil.checkEquals("LCS second string empty", 0, LCS.lcsLength("abc", ""));
        TestUtil.checkEquals("LCS both empty", 0, LCS.lcsLength("", ""));
        TestUtil.checkEquals("LCS classic example", 4, LCS.lcsLength("AGGTAB", "GXTXAYB"));

        System.out.println("=== Edit Distance tests ===");
        TestUtil.checkEquals("Edit identical strings", 0, EditDistance.distance("hello", "hello"));
        TestUtil.checkEquals("Edit first string empty", 3, EditDistance.distance("", "abc"));
        TestUtil.checkEquals("Edit second string empty", 3, EditDistance.distance("abc", ""));
        TestUtil.checkEquals("Edit substitution", 1, EditDistance.distance("cat", "cut"));
        TestUtil.checkEquals("Edit insertion", 1, EditDistance.distance("cat", "cart"));
        TestUtil.checkEquals("Edit deletion", 1, EditDistance.distance("cart", "cat"));
        TestUtil.checkEquals("Edit classic example", 3, EditDistance.distance("kitten", "sitting"));
    }

    public static void main(String[] args) {
        run();
        TestUtil.finish();
    }
}
package tests;

import algorithms.Kasai;
import algorithms.SuffixArrayBuilder;

import java.util.Arrays;
import java.util.Random;

public class KasaiTests {

    private static int[] slowLcp(String s, int[] sa) {
        int n = s.length();
        int[] lcp = new int[n];
        for (int i = 1; i < n; i++) {
            int a = sa[i - 1];
            int b = sa[i];
            int len = 0;
            while (a + len < n && b + len < n && s.charAt(a + len) == s.charAt(b + len)) {
                len++;
            }
            lcp[i] = len;
        }
        return lcp;
    }

    public static void run() {
        System.out.println("=== Kasai tests ===");

        int[] expectedSa = {5, 3, 1, 0, 4, 2};
        int[] expectedLcp = {0, 1, 3, 0, 0, 2};

        int[] sa = SuffixArrayBuilder.build("banana");
        TestUtil.checkEquals("suffix array of banana", Arrays.toString(expectedSa), Arrays.toString(sa));

        int[] lcp = Kasai.buildLcp("banana", expectedSa);
        TestUtil.checkEquals("LCP array of banana", Arrays.toString(expectedLcp), Arrays.toString(lcp));

        int[] lcp2 = Kasai.buildLcp("aaaa", new int[]{3, 2, 1, 0});
        TestUtil.checkEquals("LCP array of aaaa", Arrays.toString(new int[]{0, 1, 2, 3}), Arrays.toString(lcp2));

        Random random = new Random(1);
        for (int test = 1; test <= 20; test++) {
            StringBuilder sb = new StringBuilder();
            int length = 1 + random.nextInt(50);
            for (int i = 0; i < length; i++) {
                sb.append((char) ('a' + random.nextInt(3)));
            }
            String s = sb.toString();
            int[] suffixArray = SuffixArrayBuilder.build(s);
            TestUtil.checkEquals("random string " + test + " Kasai vs slow LCP",
                    Arrays.toString(slowLcp(s, suffixArray)),
                    Arrays.toString(Kasai.buildLcp(s, suffixArray)));
        }
    }

    public static void main(String[] args) {
        run();
        TestUtil.finish();
    }
}
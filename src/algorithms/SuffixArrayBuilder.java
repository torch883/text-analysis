package algorithms;

import java.util.Arrays;

public class SuffixArrayBuilder {

    public static int[] build(String s) {
        int n = s.length();

        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }

        Arrays.sort(indices, (a, b) -> compareSuffixes(s, a, b));

        int[] sa = new int[n];
        for (int i = 0; i < n; i++) {
            sa[i] = indices[i];
        }
        return sa;
    }

    private static int compareSuffixes(String s, int a, int b) {
        int n = s.length();
        while (a < n && b < n) {
            if (s.charAt(a) != s.charAt(b)) {
                return s.charAt(a) - s.charAt(b);
            }
            a++;
            b++;
        }
        if (a == n) {
            return -1;
        } else {
            return 1;
        }
    }
}
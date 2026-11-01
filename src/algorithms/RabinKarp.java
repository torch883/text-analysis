package algorithms;

import java.util.ArrayList;
import java.util.List;

public class RabinKarp {

    private static final int BASE = 256;
    private static final long BIG_MOD = 1_000_000_007L;

    public static long comparisons = 0;
    public static long spuriousHits = 0;

    public static List<Integer> search(String text, String pattern) {
        return search(text, pattern, BIG_MOD);
    }

    public static List<Integer> search(String text, String pattern, long mod) {
        List<Integer> result = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();

        if (m == 0 || m > n) {
            return result;
        }

        long h = 1;
        for (int i = 0; i < m - 1; i++) {
            h = (h * BASE) % mod;
        }

        long patternHash = 0;
        long windowHash = 0;
        for (int i = 0; i < m; i++) {
            patternHash = (patternHash * BASE + pattern.charAt(i)) % mod;
            windowHash = (windowHash * BASE + text.charAt(i)) % mod;
        }

        for (int i = 0; i <= n - m; i++) {
            if (patternHash == windowHash) {
                boolean same = true;
                for (int j = 0; j < m; j++) {
                    comparisons++;
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        same = false;
                        break;
                    }
                }
                if (same) {
                    result.add(i);
                } else {
                    spuriousHits++;
                }
            }

            if (i < n - m) {
                windowHash = (windowHash - text.charAt(i) * h % mod + mod) % mod;
                windowHash = (windowHash * BASE + text.charAt(i + m)) % mod;
            }
        }

        return result;
    }
}
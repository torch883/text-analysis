package experiments;

import algorithms.KMP;
import algorithms.NaiveSearch;
import algorithms.RabinKarp;
import util.Plot;
import util.RandomStrings;
import util.Report;
import util.Timer;

import java.util.List;

public class MatchingBenchmark {

    static final int PATTERN_LENGTH = 50;
    static final String[] CASE_NAMES = {"present", "absent", "worst-case"};

    static String csv = "case,text_length,matches,naive_ms,kmp_ms,rk_ms,naive_comparisons,kmp_comparisons,rk_comparisons,rk_spurious_hits\n";
    static String md = "| Case | Text length | Matches | Naive (ms) | KMP (ms) | Rabin-Karp (ms) "
            + "| Naive comparisons | KMP comparisons | RK comparisons | RK spurious hits |\n"
            + "|---|---|---|---|---|---|---|---|---|---|\n";

    public static void run() {
        System.out.println("=== String matching ===");

        int[] lengths = {10000, 50000, 100000};
        double[] lengthsAsDouble = new double[lengths.length];

        double[][][] times = new double[3][3][lengths.length];

        for (int k = 0; k < lengths.length; k++) {
            int n = lengths[k];
            lengthsAsDouble[k] = n;

            String text = RandomStrings.generate(n, 3);
            String present = text.substring(n / 2, n / 2 + PATTERN_LENGTH);
            String absent = present.substring(0, PATTERN_LENGTH - 1) + "#";

            String worstText = RandomStrings.repeat('a', n);
            String worstPattern = RandomStrings.repeat('a', PATTERN_LENGTH - 1) + "b";

            runCase(0, k, n, text, present, times);
            runCase(1, k, n, text, absent, times);
            runCase(2, k, n, worstText, worstPattern, times);
        }

        System.out.println(md);
        Report.write("results/matching_results.csv", csv);
        Report.write("results/matching_results.md", md);

        String[] algorithmNames = {"Naive", "KMP", "Rabin-Karp"};
        String[] plotFiles = {"results/plot_matching_present.png",
                "results/plot_matching_absent.png",
                "results/plot_matching_worst.png"};
        for (int c = 0; c < 3; c++) {
            Plot.lineChart(plotFiles[c],
                    "String matching, pattern: " + CASE_NAMES[c],
                    "Text length", "Time (ms)",
                    lengthsAsDouble, algorithmNames, times[c]);
        }
    }

    private static void runCase(int caseIndex, int lengthIndex, int n,
                                final String text, final String pattern, double[][][] times) {
        double naiveMs = Timer.averageMillis(() -> Timer.sink += NaiveSearch.search(text, pattern).size());
        double kmpMs = Timer.averageMillis(() -> Timer.sink += KMP.search(text, pattern).size());
        double rkMs = Timer.averageMillis(() -> Timer.sink += RabinKarp.search(text, pattern).size());

        times[caseIndex][0][lengthIndex] = naiveMs;
        times[caseIndex][1][lengthIndex] = kmpMs;
        times[caseIndex][2][lengthIndex] = rkMs;

        NaiveSearch.comparisons = 0;
        List<Integer> naiveResult = NaiveSearch.search(text, pattern);
        long naiveCmp = NaiveSearch.comparisons;

        KMP.comparisons = 0;
        List<Integer> kmpResult = KMP.search(text, pattern);
        long kmpCmp = KMP.comparisons;

        RabinKarp.comparisons = 0;
        RabinKarp.spuriousHits = 0;
        List<Integer> rkResult = RabinKarp.search(text, pattern);
        long rkCmp = RabinKarp.comparisons;
        long rkSpurious = RabinKarp.spuriousHits;

        if (!naiveResult.equals(kmpResult) || !naiveResult.equals(rkResult)) {
            System.out.println("ERROR: algorithms returned different results!");
        }

        String name = CASE_NAMES[caseIndex];
        int matches = naiveResult.size();
        csv += name + "," + n + "," + matches + "," + Report.fmt(naiveMs) + "," + Report.fmt(kmpMs) + ","
                + Report.fmt(rkMs) + "," + naiveCmp + "," + kmpCmp + "," + rkCmp + "," + rkSpurious + "\n";
        md += "| " + name + " | " + n + " | " + matches + " | " + Report.fmt(naiveMs) + " | " + Report.fmt(kmpMs)
                + " | " + Report.fmt(rkMs) + " | " + naiveCmp + " | " + kmpCmp + " | " + rkCmp
                + " | " + rkSpurious + " |\n";
    }
}
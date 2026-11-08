package experiments;

import algorithms.Kasai;
import algorithms.SuffixArrayBuilder;
import util.Plot;
import util.RandomStrings;
import util.Report;
import util.Timer;

public class KasaiBenchmark {

    public static void run() {
        System.out.println("=== Kasai's algorithm ===");

        int[] sizes = {1000, 5000, 10000};
        double[] sizesAsDouble = new double[sizes.length];
        double[] kasaiTimes = new double[sizes.length];

        String csv = "size,kasai_ms,comparisons,max_lcp\n";
        String md = "| String length | Kasai time (ms) | Character comparisons | Max LCP value |\n"
                + "|---|---|---|---|\n";

        for (int k = 0; k < sizes.length; k++) {
            int size = sizes[k];

            final String s = RandomStrings.generate(size, 4);
            final int[] sa = SuffixArrayBuilder.build(s);

            kasaiTimes[k] = Timer.averageMillis(() -> Timer.sink += Kasai.buildLcp(s, sa).length);

            Kasai.comparisons = 0;
            int[] lcp = Kasai.buildLcp(s, sa);
            long comparisons = Kasai.comparisons;
            int maxLcp = 0;
            for (int value : lcp) {
                maxLcp = Math.max(maxLcp, value);
            }

            sizesAsDouble[k] = size;
            csv += size + "," + Report.fmt(kasaiTimes[k]) + "," + comparisons + "," + maxLcp + "\n";
            md += "| " + size + " | " + Report.fmt(kasaiTimes[k]) + " | " + comparisons + " | " + maxLcp + " |\n";
        }

        System.out.println(md);
        Report.write("results/kasai_results.csv", csv);
        Report.write("results/kasai_results.md", md);
        Plot.lineChart("results/plot_kasai.png",
                "Kasai's algorithm: time vs string length",
                "String length", "Time (ms)",
                sizesAsDouble, new String[]{"Kasai"},
                new double[][]{kasaiTimes});
    }
}
package experiments;

import algorithms.EditDistance;
import algorithms.LCS;
import util.Plot;
import util.RandomStrings;
import util.Report;
import util.Timer;

public class DpBenchmark {

    public static void run() {
        System.out.println("=== LCS and Edit Distance ===");

        int[] sizes = {100, 500, 1000};
        double[] sizesAsDouble = new double[sizes.length];
        double[] lcsTimes = new double[sizes.length];
        double[] editTimes = new double[sizes.length];

        String csv = "size,lcs_ms,edit_ms,lcs_length,edit_distance,table_cells\n";
        String md = "| Length | LCS time (ms) | Edit Distance time (ms) | LCS length | Edit distance | Table cells |\n"
                + "|---|---|---|---|---|---|\n";

        for (int k = 0; k < sizes.length; k++) {
            int size = sizes[k];

            final String a = RandomStrings.generate(size, 1);
            final String b = RandomStrings.generate(size, 2);

            lcsTimes[k] = Timer.averageMillis(() -> Timer.sink += LCS.lcsLength(a, b));
            editTimes[k] = Timer.averageMillis(() -> Timer.sink += EditDistance.distance(a, b));

            int lcsValue = LCS.lcsLength(a, b);
            int editValue = EditDistance.distance(a, b);
            long cells = (long) size * size;

            sizesAsDouble[k] = size;
            csv += size + "," + Report.fmt(lcsTimes[k]) + "," + Report.fmt(editTimes[k]) + ","
                    + lcsValue + "," + editValue + "," + cells + "\n";
            md += "| " + size + " | " + Report.fmt(lcsTimes[k]) + " | " + Report.fmt(editTimes[k]) + " | "
                    + lcsValue + " | " + editValue + " | " + cells + " |\n";
        }

        System.out.println(md);
        Report.write("results/dp_results.csv", csv);
        Report.write("results/dp_results.md", md);
        Plot.lineChart("results/plot_dp.png",
                "LCS and Edit Distance: time vs input size",
                "String length", "Time (ms)",
                sizesAsDouble, new String[]{"LCS", "Edit Distance"},
                new double[][]{lcsTimes, editTimes});
    }
}
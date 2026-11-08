package util;

public class Timer {

    public static final int RUNS = 5;

    public static long sink = 0;

    public static double averageMillis(Runnable task) {
        task.run();

        long total = 0;
        for (int run = 0; run < RUNS; run++) {
            long start = System.nanoTime();
            task.run();
            long end = System.nanoTime();
            total += end - start;
        }
        return total / (double) RUNS / 1_000_000.0;
    }
}
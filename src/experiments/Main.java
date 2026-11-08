package experiments;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        new File("results").mkdirs();

        DpBenchmark.run();
        MatchingBenchmark.run();
        KasaiBenchmark.run();
    }
}
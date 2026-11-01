package tests;

public class RunAllTests {

    public static void main(String[] args) {
        DpTests.run();
        MatchingTests.run();
        KasaiTests.run();
        TestUtil.finish();
    }
}
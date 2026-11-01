package tests;


public class TestUtil {

    static int passed = 0;
    static int failed = 0;

    public static void checkEquals(String testName, Object expected, Object actual) {
        if (expected.equals(actual)) {
            passed++;
            System.out.println("[PASS] " + testName);
        } else {
            failed++;
            System.out.println("[FAIL] " + testName
                    + "  expected=" + expected + "  actual=" + actual);
        }
    }


    public static void finish() {
        System.out.println();
        System.out.println("Passed: " + passed + ", failed: " + failed);
        if (failed > 0) {
            System.exit(1);
        }
    }
}
package util;

import java.util.Random;

public class RandomStrings {

    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public static String generate(int length, long seed) {
        Random random = new Random(seed);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    public static String repeat(char c, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(c);
        }
        return sb.toString();
    }
}
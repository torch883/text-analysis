package util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class Report {

    public static void write(String path, String text) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(text);
        } catch (IOException e) {
            System.out.println("Cannot write " + path + ": " + e.getMessage());
        }
    }

    public static String fmt(double value) {
        return String.format(Locale.US, "%.3f", value);
    }
}   
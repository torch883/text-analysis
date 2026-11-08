package util;

import javax.imageio.ImageIO;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Plot {

    private static final Color[] COLORS = {
            new Color(31, 119, 180), new Color(214, 39, 40), new Color(44, 160, 44)
    };

    public static void lineChart(String path, String title, String xLabel, String yLabel,
                                 double[] x, String[] names, double[][] y) {
        System.setProperty("java.awt.headless", "true");

        int width = 900;
        int height = 600;
        int left = 90, right = 40, top = 70, bottom = 80;
        int plotW = width - left - right;
        int plotH = height - top - bottom;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        double maxY = 0;
        for (double[] series : y) {
            for (double value : series) {
                maxY = Math.max(maxY, value);
            }
        }
        if (maxY == 0) {
            maxY = 1;
        }
        maxY = maxY * 1.1;
        double minX = x[0];
        double maxX = x[x.length - 1];

        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 20));
        g.drawString(title, left, 40);

        g.setFont(new Font("SansSerif", Font.PLAIN, 13));
        for (int i = 0; i <= 5; i++) {
            int yy = top + plotH - i * plotH / 5;
            g.setColor(new Color(225, 225, 225));
            g.drawLine(left, yy, left + plotW, yy);
            g.setColor(Color.BLACK);
            g.drawString(Report.fmt(maxY * i / 5), 10, yy + 5);
        }

        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawLine(left, top, left, top + plotH);
        g.drawLine(left, top + plotH, left + plotW, top + plotH);

        g.setFont(new Font("SansSerif", Font.PLAIN, 13));
        for (int k = 0; k < x.length; k++) {
            int xx = left + (int) ((x[k] - minX) / (maxX - minX) * plotW);
            g.drawLine(xx, top + plotH, xx, top + plotH + 5);
            g.drawString(String.valueOf((long) x[k]), xx - 20, top + plotH + 22);
        }
        g.drawString(xLabel, left + plotW / 2 - 40, height - 25);
        g.drawString(yLabel, 10, 55);

        for (int s = 0; s < y.length; s++) {
            g.setColor(COLORS[s % COLORS.length]);
            g.setStroke(new BasicStroke(3));
            int prevX = 0, prevY = 0;
            for (int k = 0; k < x.length; k++) {
                int xx = left + (int) ((x[k] - minX) / (maxX - minX) * plotW);
                int yy = top + plotH - (int) (y[s][k] / maxY * plotH);
                if (k > 0) {
                    g.drawLine(prevX, prevY, xx, yy);
                }
                g.fillOval(xx - 5, yy - 5, 10, 10);
                prevX = xx;
                prevY = yy;
            }
            g.fillRect(left + 20, top + 15 + s * 25, 18, 4);
            g.setColor(Color.BLACK);
            g.drawString(names[s], left + 45, top + 21 + s * 25);
        }

        g.dispose();

        try {
            ImageIO.write(image, "png", new File(path));
        } catch (IOException e) {
            System.out.println("Cannot write " + path + ": " + e.getMessage());
        }
    }
}
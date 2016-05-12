package Algorithm.colour_histogram;

import Algorithm.Algorithm;
import javafx.scene.chart.XYChart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

/**
 * Created by MSI on 2016-05-12.
 */
public class RGBHistogram extends Algorithm {

    BufferedImage templateImage;
    int[] redHistogram = new int[256],
            greenHistogram = new int[256],
            blueHistogram = new int[256];

    XYChart.Series seriesRed = new XYChart.Series(),
            seriesGreen = new XYChart.Series(),
            seriesBlue = new XYChart.Series();

    public RGBHistogram(BufferedImage bufferedImage) {
        super(bufferedImage);

        this.templateImage = new BufferedImage(getFilterImage().getWidth(), getFilterImage().getHeight(), getFilterImage().getType());
    }

    @Override
    protected void run() {

    }

    public void histogram(BufferedImage bufferedImage) {
        Raster raster = bufferedImage.getRaster();

        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                redHistogram[raster.getSample(x, y, 0)]++;
                greenHistogram[raster.getSample(x, y, 1)]++;
                blueHistogram[raster.getSample(x, y, 2)]++;
            }
        }

        seriesRed.setName("red");
        seriesGreen.setName("green");
        seriesBlue.setName("blue");

        for (int i = 0; i < 256; i++) {
            seriesRed.getData().add(new XYChart.Data(String.valueOf(i), redHistogram[i]));
            seriesGreen.getData().add(new XYChart.Data(String.valueOf(i), greenHistogram[i]));
            seriesBlue.getData().add(new XYChart.Data(String.valueOf(i), blueHistogram[i]));
        }
    }

    public BufferedImage getStretching() {
        stretching();
        histogram(templateImage);

        return templateImage;
    }


    public BufferedImage getEqualization() {
        return templateImage;
    }

    private void stretching() {
        int r, g, b;

        int minR = 255, maxR = 0;
        int minG = 255, maxG = 0;
        int minB = 255, maxB = 0;

        for (int x = 0; x < filterImage.getWidth(); x++) {
            for (int y = 0; y < filterImage.getHeight(); y++) {
                if (getRedPixel(x, y) > maxR)
                    maxR = getRedPixel(x ,y);
                if (getRedPixel(x, y) < minR)
                    minR = getRedPixel(x ,y);

                if (getGreenPixel(x, y) > maxG)
                    maxG = getGreenPixel(x, y);
                if (getGreenPixel(x, y) < minG)
                    minG = getGreenPixel(x, y);

                if (getBluePixel(x ,y) > maxB)
                    maxB = getBluePixel(x ,y);
                if (getBluePixel(x ,y) < minB)
                    minB = getBluePixel(x ,y);
            }
        }


        for (int x = 0; x < filterImage.getWidth(); x++) {
            for (int y = 0; y < filterImage.getHeight(); y++) {
                r = ( 255 / (maxR - minR) ) * (getRedPixel(x ,y) - minR);
                g = ( 255 / (maxG - minG) ) * (getGreenPixel(x ,y) - minG);
                b = ( 255 / (maxB - minB) ) * (getBluePixel(x ,y) - minB);

                r = r > 255 ? 255 :
                        r < 0 ? 0 : r;
                g = g > 255 ? 255 :
                        g < 0 ? 0 : g;
                b = b > 255 ? 255 :
                        b < 0 ? 0 : b;

                Color color = new Color(r, g, b);

                templateImage.setRGB(x, y, color.getRGB());
            }
        }
    }

    public XYChart.Series getSeriesRed() {
        return seriesRed;
    }

    public XYChart.Series getSeriesGreen() {
        return seriesGreen;
    }

    public XYChart.Series getSeriesBlue() {
        return seriesBlue;
    }

    protected int getRedPixel(int x, int y) {
        int rgb = filterImage.getRGB(x, y);
        return (rgb >> 16) & 0xFF;
    }

    protected int getGreenPixel(int x, int y) {
        int rgb = filterImage.getRGB(x, y);
        return (rgb >> 8) & 0xFF;
    }

    protected int getBluePixel(int x , int y) {
        int rgb = filterImage.getRGB(x, y);
        return (rgb & 0xFF);
    }

}

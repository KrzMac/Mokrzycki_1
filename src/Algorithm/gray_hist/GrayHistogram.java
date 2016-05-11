package Algorithm.gray_hist;

import Algorithm.Algorithm;
import javafx.scene.chart.XYChart;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

/**
 * Created by MSI on 2016-05-11.
 */
public class GrayHistogram extends Algorithm {

    int[] histogram = new int[256];
    BufferedImage templateImage;

    XYChart.Series seriesGray;

    public GrayHistogram(BufferedImage bufferedImage) {
        super(bufferedImage);

        this.templateImage = new BufferedImage(getGrayImage().getWidth(), getGrayImage().getHeight(), getGrayImage().getType());
    }

    @Override
    protected void run() {

    }

    public void histogram(BufferedImage bufferedImage) {
        Raster raster = bufferedImage.getRaster();

        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                histogram[raster.getSample(x, y, 0)]++;
            }
        }

        seriesGray = new XYChart.Series();
        seriesGray.setName("gray");

        for (int i = 0; i < histogram.length; i++)
            seriesGray.getData().add(new XYChart.Data(String.valueOf(i), histogram[i]));
    }

    public BufferedImage getStretching() {
        stretching();
        histogram(templateImage);

        return templateImage;
    }

    public BufferedImage getEqualization() {
        histogram(grayImage);
        equalization();
        histogram(templateImage);

        return templateImage;
    }

    private void stretching() {
        int g;

        for (int x = 0; x < grayImage.getWidth(); x++) {
            for (int y = 0; y < grayImage.getHeight(); y++) {
                g = ((getGrayPixel(x, y) - 0) / (255 - 0) ) * 255;
               templateImage.setRGB(x, y, g);
            }
        }

    }

    private void equalization() {
        int sum = 0;
        int anzpixel = grayImage.getWidth() * grayImage.getHeight();
        int[] iarray = new int[1];

        float[] lut = new float[anzpixel];
        for (int i = 0; i < histogram.length; ++i) {
            sum += histogram[i];
            lut[i] = sum * 255 / anzpixel;
        }

        int i = 0;

        for (int x = 0; x < grayImage.getWidth(); x++) {
            for (int y = 0; y < grayImage.getHeight(); y++) {
                int valueBefore = grayImage.getRaster().getPixel(x, y, iarray)[0];
                int valueAfter = (int) lut[valueBefore];
                iarray[0] = valueAfter;

                templateImage.getRaster().setPixel(x, y, iarray);
                i++;
            }
        }
    }

    protected int getGrayPixel(int x, int y) {
        int rgb = grayImage.getRGB(x, y);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = (rgb & 0xFF);

        int grayLevel = (r + g +b) / 3;
        int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
        return gray;
    }

    public XYChart.Series getSeriesGray() {
        return seriesGray;
    }
}

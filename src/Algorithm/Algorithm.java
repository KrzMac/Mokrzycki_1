package Algorithm;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by MSI on 2016-04-18.
 */
public abstract class Algorithm {

    protected BufferedImage binaryImage;
    protected BufferedImage grayImage;
    protected BufferedImage filterImage;

    public Algorithm(BufferedImage bufferedImage) {

        this.binaryImage = setBinaryImage(bufferedImage);
        this.grayImage = setGrayImage(bufferedImage);
        this.filterImage = setFilterImage(bufferedImage);
    }

    public BufferedImage setBinaryImage(BufferedImage bufferedImage) {
        BufferedImage image = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics graphics = image.getGraphics();
        graphics.drawImage(bufferedImage, 0, 0, null);
        graphics.dispose();

        return image;
    }

    public BufferedImage getBinaryImage() {
        return this.binaryImage;
    }

    public BufferedImage getGrayImage() {
        return this.grayImage;
    }

    protected abstract void run();

    public BufferedImage setGrayImage(BufferedImage bufferedImage) {
        BufferedImage image = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics graphics = image.getGraphics();
        graphics.drawImage(bufferedImage, 0, 0, null);
        graphics.dispose();

        return image;
    }

    public BufferedImage setFilterImage(BufferedImage bufferedImage) {
        BufferedImage image = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.drawImage(bufferedImage, 0, 0, null);
        graphics.dispose();

        return image;
    }
}
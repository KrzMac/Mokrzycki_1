package Algorithm.bin_oper_logicz;

import Algorithm.Algorithm;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

/**
 * Created by MSI on 2016-05-15.
 */
public abstract class Operations extends Algorithm {

    BufferedImage templateImage;
    Raster templateRaster;

    public Operations(BufferedImage bufferedImage) {
        super(bufferedImage);

        this.templateRaster = binaryImage.getData();
        this.templateImage = new BufferedImage(getBinaryImage().getWidth(), getBinaryImage().getHeight(), getBinaryImage().getType());
    }

    @Override
    protected void run() {
        for (int x = 0; x < binaryImage.getWidth(); x++) {
            for (int y = 0; y < binaryImage.getHeight(); y++) {
                makeAlgorithm(x, y);
            }
        }
    }

    public abstract void makeAlgorithm(int x, int y);

    protected int getBinaryPixel(int x, int y) {
        return this.templateRaster.getSample(x, y, 0);
    }

    public BufferedImage getTemplateImage() {
        return templateImage;
    }
}

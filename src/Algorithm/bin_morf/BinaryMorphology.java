package Algorithm.bin_morf;

import Algorithm.*;

import javax.xml.crypto.Data;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferUShort;
import java.awt.image.WritableRaster;

/**
 * Created by MSI on 2016-04-18.
 */
public abstract class BinaryMorphology extends Algorithm {

    private BufferedImage templateImage;
    private int size = 2;

    public BinaryMorphology(String path) {
        super(path);

        this.templateImage = new BufferedImage(getBinaryImage().getWidth(), getBinaryImage().getHeight(), getBinaryImage().getType());
    }

    protected BinaryMorphology(BufferedImage templateImage, String path) {
        super(templateImage, path);

        this.templateImage = new BufferedImage(getBinaryImage().getWidth(), getBinaryImage().getHeight(), getBinaryImage().getType());
    }

    @Override
    protected void run() {
        for (int x = 0; x < binaryImage.getHeight(); x++) {
            for (int y = 0; y < binaryImage.getWidth(); y++) {
                MaskArray mask = new MaskArray(2 * size + 1);
                Integer[][] maskArray = mask.getMaskArray();
                int sum = getBinaryPixel(x, y);
                int k, l;

                for (int i = x - size; i < x + size; i++) {
                    k = 0; l = 0;
                    for (int j = y - size; j < y + size; j++) {
                        if ((i >= 0&&j >= 0) && (i < binaryImage.getHeight()&&j < binaryImage.getWidth())) {
                            sum = makeAlgorithm(i, j, k, l, sum, maskArray);
//                            sum = sum & getBinaryPixel(i, j) & maskArray[k][l];
                            l++;
                        }
                    }
                    k++;
                }

                if (sum == 1)
                    templateImage.setRGB(x, y, 0xffffffff);
                else if (sum == 0)
                    templateImage.setRGB(x, y, 0xff000000);
            }
        }
    }

    protected int getBinaryPixel(int x, int y) {
        return binaryImage.getData().getSample(x, y, 0);
    }

    public BufferedImage getTemplateImage() {
        return templateImage;
    }

    public abstract int makeAlgorithm(int i, int j, int k, int l, int sum, Integer[][] maskArray);
}
package Algorithm.gray_oper_arytm;

import java.awt.image.BufferedImage;

/**
 * Created by MSI on 2016-05-15.
 */
public class GrayImageMultiplicationConst extends Operations {

    public GrayImageMultiplicationConst(BufferedImage firstImage, int constant) {
        super(firstImage, constant);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int firstPixel = getGrayPixel(firstImage, x, y);

        firstPixel *= constant;

        templateImage.setRGB(x, y, firstPixel);
    }

}
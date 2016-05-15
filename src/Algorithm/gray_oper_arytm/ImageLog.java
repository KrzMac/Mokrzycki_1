package Algorithm.gray_oper_arytm;

import java.awt.image.BufferedImage;

/**
 * Created by MSI on 2016-05-15.
 */
public class ImageLog extends Operations {

    public ImageLog(BufferedImage firstImage) {
        super(firstImage);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int firstPixel = getGrayPixel(firstImage, x, y);

        firstPixel = (int) Math.log((double)firstPixel);

        templateImage.setRGB(x, y, firstPixel);
    }

}

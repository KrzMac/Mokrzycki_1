package Algorithm.gray_oper_arytm;

import java.awt.image.BufferedImage;

/**
 * Created by MSI on 2016-05-15.
 */
public class ImageMultiplication extends Operations {

    public ImageMultiplication(BufferedImage firstImage, BufferedImage secondImage) {
        super(firstImage, secondImage);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        if (x < secondImage.getWidth() && y < secondImage.getHeight()) {
            int firstPixel = getGrayPixel(firstImage, x, y);
            int secondPixel = getGrayPixel(secondImage, x, y);

            int sum = firstPixel * secondPixel;

            templateImage.setRGB(x, y, sum);
        }
        else {
            templateImage.setRGB(x, y, getGrayPixel(firstImage, x, y));
        }
    }

}

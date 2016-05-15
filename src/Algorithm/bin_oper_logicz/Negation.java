package Algorithm.bin_oper_logicz;

import java.awt.image.BufferedImage;

/**
 * Created by MSI on 2016-05-15.
 */
public class Negation extends Operations {

    public Negation(BufferedImage bufferedImage) {
        super(bufferedImage);

        run();
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        if (getBinaryPixel(x, y) == 0)
            templateImage.setRGB(x, y, 0xffffffff);
        else
            templateImage.setRGB(x, y, 0xff000000);
    }

}

package Algorithm.color_oper_arytm;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by MSI on 2016-05-15.
 */
public class ColorImageLog extends Operations {

    public ColorImageLog(BufferedImage bufferedImage) {
        super(bufferedImage);
    }

    @Override
    public void makeAlgorithm(int x, int y) {
        int firstRedPixel = firstRaster.getSample(x, y, 0);
        int firstGreenPixel = firstRaster.getSample(x, y, 1);
        int firstBluePixel = firstRaster.getSample(x, y, 2);

        firstRedPixel = (int) Math.log((double) firstRedPixel);
        firstGreenPixel = (int) Math.log((double) firstGreenPixel);
        firstBluePixel = (int) Math.log((double) firstBluePixel);

        Color color = new Color(firstRedPixel, firstGreenPixel, firstBluePixel);

        templateImage.setRGB(x, y, color.getRGB());
    }

}

package Algorithm.gray_morf;

import java.awt.image.BufferedImage;

/**
 * Created by MSI on 2016-04-21.
 */
public class GrayDilation extends GrayMorphology {
    public GrayDilation(String path) {
        super(path);

        run();
    }

    public GrayDilation(BufferedImage templateImage, String path) {
        super(templateImage, path);

        run();
    }

    @Override
    protected int makeAlgorithm(int centralPixel, int i, int j) {
        int actualPixel = getGrayPixel(i, j);
        if (actualPixel < centralPixel)
            centralPixel = actualPixel;
        return centralPixel;
    }
}

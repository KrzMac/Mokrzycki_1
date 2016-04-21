package Algorithm.bin_morf;

import java.awt.image.BufferedImage;

/**
 * Created by MSI on 2016-04-20.
 */
public class BinErosion extends BinaryMorphology {
    public BinErosion(String path) {
        super(path);

        run();
    }

    public BinErosion(BufferedImage templateImage, String path) {
        super(templateImage, path);

        run();
    }

    @Override
    public int makeAlgorithm(int i, int j, int k, int l, int sum, Integer[][] maskArray) {
        int center = sum;
        if ((maskArray[k][l] & getBinaryPixel(i, j)) == 1) {
            center = 1;
        }
        return center;
    }
}

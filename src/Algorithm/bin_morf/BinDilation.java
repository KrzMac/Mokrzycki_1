package Algorithm.bin_morf;

import java.awt.image.BufferedImage;

/**
 * Created by MSI on 2016-04-20.
 */
public class BinDilation extends BinaryMorphology {
    public BinDilation(String path) {
        super(path);

        run();
    }

    public BinDilation(BufferedImage templateImage, String path) {
        super(templateImage, path);

        run();
    }

    @Override
    public int makeAlgorithm(int i, int j, int k, int l, int sum, Integer[][] maskArray) {
        int center = sum;
        if ((maskArray[k][l] & getBinaryPixel(i, j)) == 0) {
            center = 0;
        }
        return center;
    }
}

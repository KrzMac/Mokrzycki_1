package Algorithm.bin_morf;

import java.awt.image.BufferedImage;

/**
 * Created by MSI on 2016-04-20.
 */
public class BinClosing {
    private BufferedImage templateImage;

    public BinClosing(BufferedImage bufferedImage) {
        BinDilation binDilation = new BinDilation(bufferedImage);
        BinErosion binErosion = new BinErosion(binDilation.getTemplateImage());

        this.templateImage = binErosion.getTemplateImage();
    }

    public BufferedImage getTemplateImage() {
        return templateImage;
    }
}
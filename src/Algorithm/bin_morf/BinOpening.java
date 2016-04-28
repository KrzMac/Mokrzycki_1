package Algorithm.bin_morf;

import java.awt.image.BufferedImage;

/**
 * Created by MSI on 2016-04-20.
 */
public class BinOpening {

    private BufferedImage templateImage;

    public BinOpening(String path) {
        BinErosion binErosion = new BinErosion(path);
//        binErosion.saveImage(binErosion.getTemplateImage(), "_erosion");
        BinDilation binDilation = new BinDilation(binErosion.getTemplateImage(), path);
//        binDilation.saveImage(binDilation.getTemplateImage(), "_opening");
        this.templateImage = binDilation.getTemplateImage();
    }

    public BufferedImage getTemplateImage() {
        return templateImage;
    }
}

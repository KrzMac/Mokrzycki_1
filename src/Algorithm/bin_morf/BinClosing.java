package Algorithm.bin_morf;

import java.awt.image.BufferedImage;

/**
 * Created by MSI on 2016-04-20.
 */
public class BinClosing {
    private BufferedImage templateImage;

    public BinClosing(String path) {
        BinDilation binDilation = new BinDilation(path);
//        binDilation.saveImage(binDilation.getTemplateImage(), "_dilation");
        BinErosion binErosion = new BinErosion(binDilation.getTemplateImage(), path);
//        binErosion.saveImage(binErosion.getTemplateImage(), "_closing");
        this.templateImage = binErosion.getTemplateImage();
    }

    public BufferedImage getTemplateImage() {
        return templateImage;
    }
}
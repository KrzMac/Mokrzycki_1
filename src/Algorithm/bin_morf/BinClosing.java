package Algorithm.bin_morf;

/**
 * Created by MSI on 2016-04-20.
 */
public class BinClosing {
    public BinClosing(String path) {
        BinDilation binDilation = new BinDilation(path);
        binDilation.saveImage(binDilation.getTemplateImage(), "_dilation");
        BinErosion binErosion = new BinErosion(binDilation.getTemplateImage(), path);
        binErosion.saveImage(binErosion.getTemplateImage(), "_closing");
    }
}
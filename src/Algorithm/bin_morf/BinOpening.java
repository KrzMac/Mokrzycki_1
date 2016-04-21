package Algorithm.bin_morf;

/**
 * Created by MSI on 2016-04-20.
 */
public class BinOpening {
    public BinOpening(String path) {
        BinErosion binErosion = new BinErosion(path);
        binErosion.saveImage(binErosion.getTemplateImage(), "_erosion");
        BinDilation binDilation = new BinDilation(binErosion.getTemplateImage(), path);
        binDilation.saveImage(binDilation.getTemplateImage(), "_opening");
    }
}

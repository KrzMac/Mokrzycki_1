package Algorithm.gray_morf;

/**
 * Created by MSI on 2016-04-21.
 */
public class GrayOpening {
    public GrayOpening(String path) {
        GrayErosion grayErosion = new GrayErosion(path);
        grayErosion.saveImage(grayErosion.getTemplateImage(), "_gray_erosion");
        GrayDilation grayDilation = new GrayDilation(grayErosion.getTemplateImage(), path);
        grayDilation.saveImage(grayDilation.getTemplateImage(), "_gray_opening");
    }
}

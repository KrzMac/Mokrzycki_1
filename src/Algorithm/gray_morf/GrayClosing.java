package Algorithm.gray_morf;

/**
 * Created by MSI on 2016-04-21.
 */
public class GrayClosing {
    public GrayClosing(String path) {
        GrayDilation grayDilation = new GrayDilation(path);
        grayDilation.saveImage(grayDilation.getTemplateImage(), "_gray_dilation");
        GrayErosion grayErosion = new GrayErosion(grayDilation.getTemplateImage(), path);
        grayErosion.saveImage(grayErosion.getTemplateImage(), "_gray_closing");
    }
}

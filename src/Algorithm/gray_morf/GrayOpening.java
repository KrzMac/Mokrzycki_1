package Algorithm.gray_morf;

import java.awt.image.BufferedImage;

/**
 * Created by MSI on 2016-04-21.
 */
public class GrayOpening {
    private BufferedImage templateImage;

    public GrayOpening(BufferedImage bufferedImage) {
        GrayErosion grayErosion = new GrayErosion(bufferedImage);
        GrayDilation grayDilation = new GrayDilation(grayErosion.getTemplateImage());

        this.templateImage = grayDilation.getTemplateImage();
    }

    public BufferedImage getTemplateImage() {
        return templateImage;
    }
}

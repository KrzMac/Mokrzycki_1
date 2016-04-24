package Algorithm.filters;

import java.awt.image.BufferedImage;

/**
 * Created by MSI on 2016-04-23.
 */
public class FilterTest extends Filters {
    public FilterTest(String path) {
        super(path);

        run();
        saveImage(getTemplateImage(), "_filterTest");
    }
}

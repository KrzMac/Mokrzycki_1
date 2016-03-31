package oper_log_obrazy_binarne;

import com.sun.org.apache.xpath.internal.operations.Neg;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by MSI on 2016-03-30.
 */
public class Negacja {

    private int[][] matrix;
    private String path;

    private BufferedImage originalImage;
    private BufferedImage binaryImage;

    public Negacja(String path) {
        this.path = path;

        try {
            originalImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rgb_to_binary() throws IOException {
        binaryImage = binarizeImage(originalImage);
        negation(binaryImage);
        save(negation(binaryImage));
    }

    private BufferedImage binarizeImage(BufferedImage img_param) {
        // To binary
        BufferedImage image = new BufferedImage(img_param.getWidth(), img_param.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics graphics = image.getGraphics();
        graphics.drawImage(img_param, 0, 0, null);
        graphics.dispose();

        return image;
    }

    private BufferedImage negation(BufferedImage img_param) throws IOException {
        // Write it to byte array in memory
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(img_param, "jpg", byteArrayOutputStream);

        // Do negation
        byte[] jpgByteArray = byteArrayOutputStream.toByteArray();
        byte[] negation = new byte[jpgByteArray.length];

        for (int i = 0; i < jpgByteArray.length; i++) {
            negation[i] = (byte) ~jpgByteArray[i];
        }

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(jpgByteArray));
        BufferedImage image1 = ImageIO.read(new ByteArrayInputStream(negation));

        Graphics graphics = image.getGraphics();
        graphics.drawImage(img_param, 0, 0, null);
        graphics.dispose();

        return image;
    }

    private void save(BufferedImage image) {
        String path = new StringBuffer(this.path).insert(this.path.length() - 4, "_binary").toString();
        try {
            ImageIO.write(image, "PNG", new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

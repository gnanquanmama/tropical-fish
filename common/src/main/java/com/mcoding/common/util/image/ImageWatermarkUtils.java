package com.mcoding.common.util.image;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 打水印工具类
 */
public class ImageWatermarkUtils {

    public static Logger logger = LoggerFactory.getLogger(ImageWatermarkUtils.class);

    /**
     * 给图片添加文字水印
     *
     * @param text            打水印的文字
     * @param sourceImageFile 需要打印的图片
     * @param destImageFile   需要输出的图片
     */
    public static void addTextWatermark(String text, File sourceImageFile, File destImageFile) {
        try {

            BufferedImage sourceImage = ImageIO.read(sourceImageFile);
            Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();

            // initializes necessary graphic properties
            AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
            g2d.setComposite(alphaChannel);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 64));
            FontMetrics fontMetrics = g2d.getFontMetrics();
            Rectangle2D rect = fontMetrics.getStringBounds(text, g2d);

            // calculates the coordinate where the String is painted
            int centerX = (sourceImage.getWidth() - (int) rect.getWidth()) / 2;
            int centerY = sourceImage.getHeight() / 2;

            // paints the textual watermark
            g2d.drawString(text, centerX, centerY);

            //获取后缀名
            String extension = FilenameUtils.getExtension(destImageFile.getName());
            ImageIO.write(sourceImage, extension, destImageFile);

            g2d.dispose();
            logger.info("添加水印成功！");

        } catch (IOException ex) {

            logger.info("添加水印失败！");
            logger.error(ex.getMessage());
        }
    }


    /**
     * 给图片加图片水印
     *
     * @param watermarkImageFile
     * @param sourceImageFile
     * @param destImageFile
     */
    static void addImageWatermark(File watermarkImageFile, File sourceImageFile, File destImageFile) {

        try {
            BufferedImage sourceImage = ImageIO.read(sourceImageFile);
            BufferedImage watermarkImage = ImageIO.read(watermarkImageFile);

            // initializes necessary graphic properties
            Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();
            AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
            g2d.setComposite(alphaChannel);

            // calculates the coordinate where the image is painted
            int topLeftX = (sourceImage.getWidth() - watermarkImage.getWidth()) / 2;
            int topLeftY = (sourceImage.getHeight() - watermarkImage.getHeight()) / 2;

            // paints the image watermark
            g2d.drawImage(watermarkImage, topLeftX, topLeftY, null);

            //获取后缀名
            String extension = FilenameUtils.getExtension(destImageFile.getName());
            ImageIO.write(sourceImage, extension, destImageFile);

            g2d.dispose();
            logger.info("添加水印成功！");

        } catch (IOException e) {
            e.printStackTrace();
            logger.info("添加水印失败！");
            logger.error(e.getMessage());
        }

    }

    public static void main(String[] args) {

        //给图片加文字水印
        File sourceImageFile = new File("D:\\test\\image\\originalimage.jpg");
        File destImageFile = new File("D:\\test\\image\\text_watermarked.jpg");
        addTextWatermark("kangni", sourceImageFile, destImageFile);

        //
        File watermarkImageFile = new File("D:\\test\\image\\logo.png");
        File destImageFile2 = new File("D:\\test\\image\\water_image_marked.jpg");
        addImageWatermark(watermarkImageFile, sourceImageFile, destImageFile2);
    }

}

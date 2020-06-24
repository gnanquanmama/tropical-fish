package com.mcoding.common.util.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;


public class ImageUtils {
	
	/**
	 * 裁剪图片
	 * @param inputStream
	 * @param x
	 * @param y
	 * @param desWidth
	 * @param desHeight
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage cropImage(InputStream inputStream, int x, int y, int desWidth, int desHeight) throws IOException {
		BufferedImage bImage = ImageIO.read(inputStream);

		int srcWidth = bImage.getWidth();
		int srcHeight = bImage.getHeight();

		if (desWidth > srcWidth || desHeight > srcHeight) {
			throw new IllegalArgumentException("裁剪的长宽，超出范围");
		}
		BufferedImage desImage = bImage.getSubimage(x, y, desWidth, desHeight);
		return desImage;
	}
	
	/**
	 * 垂直合并两张图片
	 * @param imageUp
	 * @param imageBelow
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage contactVertical(File imageUp, File imageBelow) throws IOException{
		return contactVertical(ImageIO.read(imageUp), ImageIO.read(imageBelow));
	}

	/**
	 * 垂直合并 两张图片
	 * @param imageUp
	 * @param imageBelow
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage contactVertical(BufferedImage imageUp, BufferedImage imageBelow) throws IOException {
		int upWidth = imageUp.getWidth();
		int upHeight = imageUp.getHeight();

		int belowWidth = imageBelow.getWidth();
		int belowHeight = imageBelow.getHeight();
		
		int newWidth = upWidth > belowWidth ? upWidth : belowWidth;
		int newHeigth = upHeight + belowHeight;

		if (upWidth != belowWidth) {
			int blankLeftWidth = Math.abs(upWidth - belowWidth) / 2;
			int blankHeight = upWidth > belowWidth ? belowHeight : upHeight;

			BufferedImage blankLeftImage = createRectlang(blankLeftWidth, blankHeight);
			
			int blankRightWidth = Math.abs(upWidth - belowWidth) - blankLeftWidth;
			BufferedImage blankRightImage = createRectlang(blankRightWidth, blankHeight);

			if (upWidth > belowWidth) {
				imageBelow = contactHorizontal(blankLeftImage, imageBelow);
				imageBelow = contactHorizontal(imageBelow, blankRightImage);
			}else{
				imageUp = contactHorizontal(blankLeftImage, imageUp);
				imageUp = contactHorizontal(imageUp, blankRightImage);
			}
		}

		BufferedImage imageNew = new BufferedImage(newWidth, newHeigth, BufferedImage.TYPE_INT_RGB);
		
		// 从图片中读取RGB
		int[] upImageByte = imageUp.getRGB(0, 0, newWidth, upHeight, new int[newWidth * upHeight], 0, newWidth);
		imageNew.setRGB(0, 0, newWidth, upHeight, upImageByte, 0, newWidth);
		
		int[] belowImageByte = imageBelow.getRGB(0, 0, newWidth, belowHeight, new int[newWidth * belowHeight], 0, newWidth);
		imageNew.setRGB(0, upHeight, newWidth, belowHeight, belowImageByte, 0, newWidth);

		return imageNew;
	}
	
	public static BufferedImage contactCenter(BufferedImage outter, BufferedImage inner, int paddingTop, int paddingLeft){
		if (paddingTop <0 || paddingLeft <0) {
			throw new IllegalArgumentException("内边距不能少于0");
		}
		
		int outWidth = outter.getWidth();
		int outHeight = outter.getHeight();
		
		int inWidth = inner.getWidth();
		int inHeight = inner.getHeight();
		
		if (inWidth > outWidth) {
			throw new IllegalArgumentException("无法合并，因为内图片宽度大于外图片");
		}
		
		if ((paddingTop + inHeight) >outHeight) {
			throw new IllegalArgumentException("上内边距过大");
		}
		
		if ((inWidth + paddingLeft) > outWidth) {
			throw new IllegalArgumentException("左内边距过大");
		}
		
		int[] inImageByte = inner.getRGB(0, 0, inWidth, inHeight, new int[inWidth * inHeight], 0, inWidth);
		outter.setRGB(paddingLeft, paddingTop, inWidth, inHeight, inImageByte, 0, inWidth);
		
		return outter;
		
	}

	/**
	 * 水平合并两张图片
	 * @param imageLeft
	 * @param imageRight
	 * @return
	 * @throws IOException 
	 */
	public static BufferedImage contactHorizontal(File imageLeft, File imageRight) throws IOException {
		return contactHorizontal(ImageIO.read(imageLeft), ImageIO.read(imageRight));
	}
	
	/**
	 * 水平合并两张图片
	 * @param imageLeft
	 * @param imageRight
	 * @return
	 * @throws IOException 
	 */
	public static BufferedImage contactHorizontal(BufferedImage imageLeft, BufferedImage imageRight) throws IOException {
		int leftWidth = imageLeft.getWidth();
		int leftHeight = imageLeft.getHeight();

		int rightWidth = imageRight.getWidth();
		int rightHeigth = imageRight.getHeight();

		int newWidth = leftWidth + rightWidth;
		int newHeigth = leftHeight > rightHeigth ? leftHeight : rightHeigth;
		
		if (leftHeight != rightHeigth) {
			int blankHeight = Math.abs(leftHeight - rightHeigth);
			int blankWidth = leftHeight > rightHeigth ? rightWidth : leftWidth;
			
			BufferedImage blankImage = createRectlang(blankWidth, blankHeight);
			
			if (leftHeight > rightHeigth) {
				imageRight = contactVertical(imageRight, blankImage);
			}else{
				imageLeft = contactVertical(imageLeft, blankImage);
			}
		}

		BufferedImage imageNew = new BufferedImage(newWidth, newHeigth, BufferedImage.TYPE_INT_RGB);
		// 从图片中读取RGB
		int[] leftImageByte = imageLeft.getRGB(0, 0, leftWidth, newHeigth, new int[leftWidth * newHeigth], 0, leftWidth);
		imageNew.setRGB(0, 0, leftWidth, newHeigth, leftImageByte, 0, leftWidth);
		
		int[] rightImageByte = imageRight.getRGB(0, 0, rightWidth, newHeigth, new int[rightWidth * newHeigth], 0, rightWidth);
		imageNew.setRGB(leftWidth, 0, rightWidth, newHeigth, rightImageByte, 0, rightWidth);

		return imageNew;
	}
	
	public static BufferedImage createRectlang(int width, int height){
		BufferedImage rectlangImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D rightG = (Graphics2D) rectlangImage.createGraphics();
		rightG.setColor(Color.WHITE);
		rightG.fillRect(0, 0, width, height);
		rightG.dispose();
		
		return rectlangImage;
	}


}

package com.meteorite.core.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片处理工具类
 * 
 * @author wei_jc
 * @since 1.0.0
 */
public class UImage {
    /**
     * 将已有图片按给定的宽、高重新生成图片，写到目标文件中
     *
     * @param imageFile 原始图片
     * @param width 宽
     * @param height 高
     * @param targetFile 目标图片
     * @throws IOException
     */
    public static void zoomImage(File imageFile, int width, int height, File targetFile) throws IOException {
        byte[] bytes = getImageBytes(imageFile, width, height);
        if (bytes != null) {
            UFile.write(bytes, targetFile);
        }
    }

	/**
	 * 将已有的图片按百分比进行缩放
	 * 
	 * @param imageFile
	 * @param percernt
	 * @return
	 * @throws java.io.IOException
	 */
    public static byte[] zoomImage(File imageFile, double percernt) throws IOException {
        Image image = ImageIO.read(imageFile);
        int width = (int) (image.getWidth(null) * percernt);
        int height = (int) (image.getHeight(null) * percernt);
        return getImageBytes(imageFile, width, height);
    }
    
    /**
     * 将已有的图片按新的宽度和高度进行缩放
     * 
     * @param imageFile
     * @param width
     * @param height
     * @return
     * @throws java.io.IOException
     */
    public static byte[] getImageBytes(File imageFile, int width, int height) throws IOException {
    	Image image = ImageIO.read(imageFile);
    	BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bi.getGraphics().drawImage(image, 0, 0, width, height, null);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String extName = UFile.getFileExt(imageFile.getName());
        if("JPEG".equalsIgnoreCase(extName) || "JPG".equalsIgnoreCase(extName)) {
        	ImageIO.write(bi, "JPEG", os);
        } else if("BMP".equalsIgnoreCase(extName)) {
        	ImageIO.write(bi, "BMP", os);
        } else if("WBMP".equalsIgnoreCase(extName)) {
        	ImageIO.write(bi, "WBMP", os);
        } else if("PNG".equalsIgnoreCase(extName)) {
        	ImageIO.write(bi, "PNG", os);
        } else if("GIF".equalsIgnoreCase(extName)) {
        	ImageIO.write(bi, "GIF", os);
        } else {
        	return null;
        }
        
        return os.toByteArray();
    }
    
    public static byte[] getImageBytes(InputStream inputStream, int width, int height) throws IOException {
    	BufferedImage image = ImageIO.read(inputStream);
    	BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bi.getGraphics().drawImage(image, 0, 0, width, height, null);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bi, "JPEG", os);
        
        return os.toByteArray();
    }
    
    /**
     * 将已有的图片按新的宽度和高度进行缩放，如果没有达到指定的最小尺寸，则宽和高增加50，再进行放大
     * 
     * @param inputStream
     * @param width
     * @param height
     * @param minBytes 新图片的最小字节长度
     * @return
     * @throws java.io.IOException
     */
    /*public static byte[] getImageBytes(InputStream inputStream, int width, int height, int minBytes) throws IOException {
    	byte[] bytes = getImageBytes(inputStream, width, height);
    	int count = 0;
    	while(bytes.length < minBytes && count++ <= 8) {
    		width = width + 50;
    		height = height + 50;
    		bytes = getImageBytes(inputStream, width, height);
    	}
    	
    	return bytes;
    }*/
    
    /**
     * 将已有的图片按新的宽度和高度进行缩放，如果没有达到指定的最小尺寸，则宽和高增加50，再进行放大
     * 
     * @param imageFile
     * @param width
     * @param height
     * @param minBytes 新图片的最小字节长度
     * @return
     * @throws java.io.IOException
     */
    public static byte[] getImageBytes(File imageFile, int width, int height, int minBytes) throws IOException {
    	byte[] bytes = getImageBytes(imageFile, width, height);
    	int count = 0;
    	while(bytes.length < minBytes && count++ <= 8) {
    		width = width + 50;
    		height = height + 50;
    		bytes = getImageBytes(imageFile, width, height);
    	}
    	
    	return bytes;
    }
}

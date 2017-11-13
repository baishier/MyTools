package org.bai.shi.er.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

public class ImageCompress {

	private Image img;

	private int width;

	private int height;

	/**
	 * 构造函数
	 */
	public ImageCompress(InputStream is) throws Exception {
		if (null != is) {
			System.out.println("inputImagesize=" + is.available());
		}
		img = ImageIO.read(is);
		width = img.getWidth(null);
		height = img.getHeight(null);
	}

	/**
	 * 按照宽度还是高度进行压缩
	 * 
	 * @param w
	 *            int 最大宽度
	 * @param h
	 *            int 最大高度
	 * @return
	 */
	public InputStream resizeFix(int w, int h) throws IOException {
		if (width / height > w / h) {
			return resizeByWidth(w);
		} else {
			return resizeByHeight(h);
		}
	}

	/**
	 * 以宽度为基准，等比例放缩图片
	 * 
	 * @param w
	 *            int 新宽度
	 * @return
	 */
	public InputStream resizeByWidth(int w) throws IOException {
		int h = 0;
		if (width < w) {
			/** 如果原来的宽度比，缩放目标的宽度小，则无需缩放。 */
			w = width;
			h = height;
		} else {
			h = (int) (height * w / width);
		}
		return resize(w, h);
	}

	/**
	 * 以高度为基准，等比例缩放图片
	 * 
	 * @param h
	 *            int 新高度
	 * @return
	 */
	public InputStream resizeByHeight(int h) throws IOException {
		int w = (int) (width * h / height);
		return resize(w, h);
	}

	/**
	 * 强制压缩/放大图片到固定的大小
	 * 
	 * @param w
	 *            int 新宽度
	 * @param h
	 *            int 新高度
	 * @return
	 */
	public InputStream resize(int w, int h) throws IOException {
		// SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
		// File destFile = new File("D:\\4.jpg");
		// FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
		// // 可以正常实现bmp、png、gif转jpg
		// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		// encoder.encode(image); // JPEG编码
		// out.close();
		return getImageStream(image);
	}

	public InputStream getImageStream(BufferedImage bi) {
		InputStream is = null;
		bi.flush();

		ByteArrayOutputStream bs = new ByteArrayOutputStream();

		ImageOutputStream imOut = null;
		try {
			imOut = ImageIO.createImageOutputStream(bs);

			ImageIO.write(bi, "jpg", imOut);// 这里写死JPG

			is = new ByteArrayInputStream(bs.toByteArray());
			if (null != is) {
				System.out.println("outImagesize=" + is.available());
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != imOut) {
				try {
					imOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return is;
	}

	// public static void main(String[] args) throws Exception {
	// System.out.println("开始：" + new Date().toLocaleString());
	// File file = new File("D:\\sfz\\f.jpg");
	// InputStream is = new FileInputStream(file);
	// System.out.println(is.available());
	// ImageCompress imgCom = new ImageCompress(is);
	// InputStream iss = imgCom.resizeByWidth(800);
	// System.out.println(iss.available());
	// System.out.println("结束：" + new Date().toLocaleString());
	// }
}

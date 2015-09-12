package core.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {
	public static Image loadImage(String filename) {
		try {
			return ImageIO.read(FileUtils.getInputStream(filename, true));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void writeImage(Image img, String directory, String filename) {
		try {
			File outputfile = new File(FileUtils.toPath(directory, filename));
			
			// stackoverflow.com/a/2833883
			outputfile.getParentFile().mkdirs();
			
			ImageIO.write((RenderedImage) img, "png", outputfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage createImage(Rectangle bounds) {
		return createImage(bounds, BufferedImage.TYPE_INT_ARGB);
	}

	public static BufferedImage createImage(Rectangle bounds, int type) {
		return createImage(bounds.width, bounds.height, type);
	}

	public static BufferedImage createImage(Image image) {
		return createImage(image, BufferedImage.TYPE_INT_ARGB);
	}
	public static BufferedImage createImage(Image image, int type) {
		return createImage(image.getWidth(null), image.getHeight(null), type);
	}

	public static BufferedImage createImage(int width, int height) {
		return createImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	public static BufferedImage createImage(int width, int height, int type) {
		return new BufferedImage(width, height, type);
	}

	public static Image cropImage(Image image, Shape shape, int xOffset, int yOffset) {
		BufferedImage out = createImage(shape.getBounds());
		Graphics g = out.getGraphics();

		g.setClip(shape);
		g.drawImage(image, -xOffset, -yOffset, null);
		g.dispose();
		
		return out;
	}

	public static Image cropImage(Image img, int x, int y, int w, int h) {
		BufferedImage out = createImage(w, h);
		Graphics g = out.getGraphics();
		BufferedImage sub = ((BufferedImage) img).getSubimage(x, y, w, h);

		g.drawImage(sub, 0, 0, w, h, null);
		g.dispose();

		return out;
	}

	public static Image brightenImage(BufferedImage image, float brigtness, float offset) {
		RescaleOp rescaleOp = new RescaleOp(brigtness, offset, null);

		rescaleOp.filter(image, image);

		return image;
	}

	public static BufferedImage flipHorizontally(BufferedImage image) {
		return transform(image, -1, 1, -image.getWidth(), 0);
	}

	public static BufferedImage flipVertically(BufferedImage image) {
		return transform(image, 1, -1, 0, -image.getHeight());
	}

	public static BufferedImage rotateLeft(BufferedImage image) {
		return rotateCenter(image, -Math.PI / 2);
	}

	public static BufferedImage rotateRight(BufferedImage image) {
		return rotateCenter(image, Math.PI / 2);
	}

	public static BufferedImage rotate180(BufferedImage image) {
		return rotateCenter(image, Math.PI);
	}

	private static BufferedImage rotateCenter(BufferedImage image, double theta) {
		return rotate(image, theta, image.getWidth() / 2, image.getHeight() / 2);
	}

	private static BufferedImage rotate(BufferedImage image, double theta, int anchorX, int anchorY) {
		return createTransformed(image, AffineTransform.getRotateInstance(theta, anchorX, anchorY));
	}

	// stackoverflow.com/a/23458883
	private static BufferedImage transform(BufferedImage image, int sx, int sy, int dx, int dy) {
		AffineTransform at = new AffineTransform();

		at.concatenate(AffineTransform.getScaleInstance(sx, sy));
		at.concatenate(AffineTransform.getTranslateInstance(dx, dy));

		return createTransformed(image, at);
	}

	private static BufferedImage createTransformed(BufferedImage image, AffineTransform at) {
		BufferedImage newImage = createImage(image);
		Graphics2D g = newImage.createGraphics();

		g.transform(at);
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return newImage;
	}
}

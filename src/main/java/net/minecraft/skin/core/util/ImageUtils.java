package net.minecraft.skin.core.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {
	public static Image loadImage(String filename) {
		return loadImage(filename, true);
	}
	
	public static Image loadImage(String filename, boolean isLocal) {
		try {
			return ImageIO.read(FileUtils.getInputStream(filename, isLocal));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static BufferedImage copy(Image original) {
		if (original == null) {
			return null;
		}
		
		BufferedImage copied = ImageUtils.createImage(original);
		Graphics g = copied.getGraphics();
		
		g.drawImage(original, 0, 0, null);
		g.dispose();
		
		return copied;
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
	
	/**
	 * Scales a BufferedImage to fit the provided height and width.
	 *
	 * @param image - the image to scale.
	 * @param width - the maximum size in width.
	 * @param height - the maximum size in height.
	 * @param doCrop - determine if we should crop the image.
	 * @param interpolationType - the scale interpolation type.
	 * @return a new image at the expected scale.
	 */
	public static BufferedImage scaleToFit(BufferedImage image, int width, int height, boolean doCrop, int interpolationType) {
		int w = image.getWidth();
		int h = image.getHeight();

		// If we do not need to scale the image, return the original image.
		//if (w <= width && h <= height) {
		//	return image;
		//}

		// Scale the image down.
		// Note: The resulting image is still the size of the original.
		double scaleFactor = Math.min((double) width / w, (double) height / h);

		if (scaleFactor <= 0) {
			return image;
		}

		return scaleImage(image, scaleFactor, scaleFactor, interpolationType);
	}

	/**
	 * Scales an image and returns the resulting image after the scaling is applied.
	 *
	 * @param image - the image to scale.
	 * @param scaleX - the scale ratio for the width.
	 * @param scaleY - the scale ratio for the height.
	 * @param interpolationType - the interpolation type.
	 * @return a scaled image of the original image.
	 */
	public static BufferedImage scaleImage(BufferedImage image, double scaleX, double scaleY, int interpolationType) {
		int newWidth = (int) (image.getWidth() * scaleX);
		int newHeight = (int) (image.getHeight() * scaleY);
		BufferedImage filtered = new BufferedImage(newWidth, newHeight, image.getType());
		AffineTransform at = new AffineTransform();

		at.scale(scaleX, scaleY);

		AffineTransformOp scaleOp = new AffineTransformOp(at, interpolationType);

		scaleOp.filter(image, filtered);
		
		return filtered;
	}

	/**
	 * Scales an image and returns the resulting image after the scaling is applied.
	 *
	 * @param image - the image to scale.
	 * @param scaleFactor - the scale factor to be applied.
	 * @return a scaled image at the specified scale.
	 */
	public static BufferedImage scaleImage(BufferedImage image, double scaleFactor) {
		return scaleImage(image, scaleFactor, scaleFactor, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	}

	/**
	 * Scales a BufferedImage to fit the provided height and width. The
	 * transformation uses a bilinear interpolation.
	 *
	 * @param image - the image to scale.
	 * @param width - the minimum size in width.
	 * @param height - the minimum size in height.
	 * @param doCrop - determine if we should crop the image.
	 * @return a new image at the expected scale.
	 */
	public static BufferedImage scaleToFit(BufferedImage before, int width, int height, boolean crop) {
		return scaleToFit(before, width, height, crop, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	}

	/**
	 * Tiles an image as the background of a graphics object.
	 *
	 * @param g - graphics to draw on.
	 * @param tileImage - image used for tiling.
	 * @param width - tile along x-axis.
	 * @param height - tile along y-axis.
	 */
	public static void tileImage(Graphics g, BufferedImage tileImage,
			int width, int height) {
		int iw = tileImage.getWidth();
		int ih = tileImage.getHeight();

		if (iw > 0 && ih > 0) {
			for (int x = 0; x < width; x += iw) {
				for (int y = 0; y < height; y += ih) {
					g.drawImage(tileImage, x, y, iw, ih, null);
				}
			}
		}
	}
}

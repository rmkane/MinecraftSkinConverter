package core;

import java.awt.Graphics;
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
		try {
			return ImageIO.read(FileUtils.getInputStream(filename, true));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void writeImage(Image img, String directory, String filename) {
		try {
			String path = FileUtils.toPath(directory, filename);
			File outputfile = new File(path);
			// http://stackoverflow.com/a/2833883/1762224
			outputfile.getParentFile().mkdirs();
			ImageIO.write((RenderedImage) img, "png", outputfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Image cropImage(Image image, Shape shape, int xOffset, int yOffset) {
		Rectangle bounds = shape.getBounds();
		int width = bounds.width;
		int height = bounds.height;
		int type = BufferedImage.TYPE_INT_ARGB;
		BufferedImage out = new BufferedImage(width, height, type);
		Graphics go = out.getGraphics();
		
		go.setClip(shape);
		go.drawImage(image, -xOffset, -yOffset, null);
		
		return out;
	}
	
	public static Image cropImage(Image img, int x, int y, int w, int h) {
		BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics g = out.getGraphics();
		BufferedImage sub = ((BufferedImage) img).getSubimage(x, y, w, h);

		g.drawImage(sub, 0, 0, w, h, null);

		return out;
	}

	public static Image brightenImage(Image image, float brigtness, float offset) {
		RescaleOp rescaleOp = new RescaleOp(brigtness, offset, null);

		rescaleOp.filter((BufferedImage) image, (BufferedImage) image);

		return image;
	}
	
	public static Image flipImage(Image image) {
		BufferedImage result = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
		
		// Flip the image horizontally
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-image.getWidth(null), 0);
		
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		op.filter((BufferedImage) image, result);
		
		return result;
	}
}

package core;

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
			String path = FileUtils.toPath(directory, filename);
			File outputfile = new File(path);
			outputfile.getParentFile().mkdirs(); // stackoverflow.com/a/2833883
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
	
	public static BufferedImage flipHorizontally(BufferedImage image) {
		return transform(image, -1, 1, -image.getWidth(), 0);
    }
	
	public static BufferedImage flipVertically(BufferedImage image) {
		return transform(image, 1, -1, 0, -image.getHeight());
    }
	
	public static BufferedImage createImage(BufferedImage image) {
		return createImage(image, BufferedImage.TYPE_INT_ARGB);
	}
	
	public static BufferedImage createImage(Image image, int type) {
		return new BufferedImage(image.getWidth(null), image.getHeight(null), type);
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
        AffineTransform at = AffineTransform.getRotateInstance(theta, anchorX, anchorY);
        return createTransformed(image, at);
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

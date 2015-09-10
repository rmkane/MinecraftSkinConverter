package core;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {
	public static Image loadImage(String filename) {
		try {
			ClassLoader loader = ImageUtils.class.getClassLoader();
			return ImageIO.read(loader.getResourceAsStream("resources/" + filename));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void writeImage(Image img, String directory, String filename) {
		try {
			StringBuffer path = new StringBuffer();
			if (directory != null && !directory.isEmpty()) {
				path.append(directory).append('\\');
			}
			path.append(filename);
			File outputfile = new File(path.toString());
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
}

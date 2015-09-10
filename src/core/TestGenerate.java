package core;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class TestGenerate {
	public static void main(String[] args) {
		createSkins();
	}
	
	public static void createSkins() {
		combineSkins("skin3.png", generateSkins("target.png"));
	}
	
	private static void combineSkins(String filename, Image[] layers) {
		int offset = 32;
		int height = offset * layers.length;

		BufferedImage out = new BufferedImage(64, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = out.getGraphics();

		for (int i = 0; i < layers.length; i++) {
			g.drawImage(layers[i], 0, i * offset, 64, 32, null);
		}

		ImageUtils.writeImage(out, "output", filename);
	}

	public static Image[] generateSkins(String filename) {
		Image img = ImageUtils.loadImage(filename);

		Image layer1 = ImageUtils.cropImage(img, 0, 0, 64, 32);
		Image layer2 = ImageUtils.brightenImage(ImageUtils.cropImage(img, 0, 0, 64, 32), 0.6f, 0.0f);

		ImageUtils.writeImage(layer1, "output", "skin1.png");
		ImageUtils.writeImage(layer2, "output", "skin2.png");

		return new Image[] { layer1, layer2 };
	}
}

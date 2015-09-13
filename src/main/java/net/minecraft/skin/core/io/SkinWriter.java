package net.minecraft.skin.core.io;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.skin.core.Section;
import net.minecraft.skin.core.util.ConversionUtils;
import net.minecraft.skin.core.util.FileUtils;
import net.minecraft.skin.core.util.ImageUtils;

public class SkinWriter {
	private static int combinedImageWidth = 64;
	private static int combinedImageHeight = 64;

	public static void writeSections(Map<String, Section> sections, String directory) {
		for (Entry<String, Section> entry : sections.entrySet()) {
			writeSection(entry.getValue(), directory);
		}
	}
	
	public static void writeSection(Section section, String directory) {
	    ImageUtils.writeImage(section.getImage(), directory, section.getName() + ".png");
	}

	public static void writeCombined(String configPath, Map<String, Section> sections, String filename, String directory) {
		ImageUtils.writeImage(combineSections(configPath, sections), directory, filename);
	}

	public static Image combineSections(String configPath, Map<String, Section> sections) {
		Image out = ImageUtils.createImage(combinedImageWidth, combinedImageHeight);
		Graphics g = out.getGraphics();
		BufferedReader reader = null;
		String line = null;

		try {
			reader = FileUtils.loadConfig(configPath);

			while ((line = reader.readLine()) != null)   {
				combineSection(g, sections, line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return out;
	}
	
	public static void combineSection(Graphics g, Map<String, Section> sections, String line) {
		String[] data = line.split("\\|");
		Section section = sections.get(data[0]);
		
		if (section != null) {
			int[] off = ConversionUtils.mapToInt(data[1], ",");
			Point offset = new Point(off[0], off[1]);
			boolean mirror = Boolean.parseBoolean(data[2]);
			BufferedImage image = (BufferedImage) section.getImage();

			if (mirror) {
				image = (BufferedImage) flipLimb(image);
			}

			g.drawImage(image, offset.x, offset.y, null);
		}
	}

	private static Image flipLimb(BufferedImage image) {
		Image shifted = ImageUtils.createImage(image);
		Image fliped = ImageUtils.flipHorizontally(image);
		Graphics g = shifted.getGraphics();

		// Top
		g.drawImage(ImageUtils.cropImage(fliped, 8, 0, 4, 4), 4, 0, null);

		// Bottom
		g.drawImage(ImageUtils.cropImage(fliped, 4, 0, 4, 4), 8, 0, null);

		// Face
		g.drawImage(ImageUtils.cropImage(fliped, 4, 4, 12, 12), 0, 4, null);

		// Side
		g.drawImage(ImageUtils.cropImage(fliped, 0, 4, 4, 12), 12, 4, null);

		g.dispose();

		return shifted;
	}
}

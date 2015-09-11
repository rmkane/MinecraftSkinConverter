import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import core.Section;
import core.util.ConversionUtils;
import core.util.FileUtils;
import core.util.ImageUtils;
import core.util.SectionUtils;

public class App {
	private static String ouputDirectory = "output";
	private static String configDirectory = "config";
	private static String readerConfig = FileUtils.toPath(configDirectory, "reader.conf");
	private static String writerConfig = FileUtils.toPath(configDirectory, "writer.conf");
	private static int combinedImageWidth = 64;
	private static int combinedImageHeight = 64;
	
	public static void main(String[] args) {
		Map<String, Section> sections = new HashMap<String, Section>();
		
		String directory = "skins";
		String outDir = FileUtils.toPath(ouputDirectory, directory);
		
		String[] skins = {
			FileUtils.toPath(directory, "x-tony-stark.png"),
			FileUtils.toPath(directory, "x-iron-man.png")
		};
		
		processSkins(sections, readerConfig, skins);
		writeSections(sections, outDir);
		combineSections(sections, writerConfig, "combined.png", outDir);
	}
	
	public static void processSkins(Map<String, Section> sections, String config, String[] skins) {
		for (int i = 0; i < skins.length; i++) {
			processSkin(sections, config, skins[i], Integer.toString(i));
		}
	}
	
	public static void processSkin(Map<String, Section> sections, String config, String filename, String suffix) {
		Image skin = ImageUtils.loadImage(filename);
		
		loadSections(sections, skin, config, suffix);
		processImage(skin, sections, suffix);
	}
	
	public static void processImage(Image image, Map<String, Section> sections, String suffix) {
		for (Entry<String, Section> entry : sections.entrySet()) {
			if (entry.getKey().endsWith(suffix)) {
				Section section = entry.getValue();
		    	
		    	section.setImage(SectionUtils.cropSection(image, section));
			}
	    }
	}
	
	public static void writeSections(Map<String, Section> sections, String directory) {
		for (Entry<String, Section> entry : sections.entrySet()) {
	    	SectionUtils.writeSection(entry.getValue(), directory);
	    }
	}
	
	public static void combineSections(Map<String, Section> sections, String config, String filename, String directory) {
		ImageUtils.writeImage(combineSections(sections, config), directory, filename);
	}
	
	public static Image combineSections(Map<String, Section> sections, String config) {
		Image out = ImageUtils.createImage(combinedImageWidth, combinedImageHeight);
		Graphics g = out.getGraphics();
		BufferedReader reader = null;
		String line = null;
		
		try {
			reader = FileUtils.loadConfig(config);
			
			while ((line = reader.readLine()) != null)   {
				String[] data = line.split("\\|");
				int[] off = ConversionUtils.mapToInt(data[1], ",");
				Section section = sections.get(data[0]);
				Point offset = new Point(off[0], off[1]);
				boolean mirror = Boolean.parseBoolean(data[2]);
				BufferedImage image = (BufferedImage) section.getImage();
				
				if (mirror) {
					image = ImageUtils.flipHorizontally(image);
				}
				
				g.drawImage(image, offset.x, offset.y, null);
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
	
	public static void loadSections(Map<String, Section> sections, Image image, String configName, String suffix) {
		BufferedReader reader = null;
		String line = null;
		
		try {
			reader = FileUtils.loadConfig(configName);
			
			while ((line = reader.readLine()) != null)   {
				Section section = SectionUtils.parseSection(line, suffix);
				sections.put(section.getName(), section);
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
	}
}

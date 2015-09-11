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

import core.FileUtils;
import core.ImageUtils;
import core.Section;
import core.SectionUtils;
import core.TransformationUtils;

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
	
	public static void processImage(Image img, Map<String, Section> sections, String suffix) {
		for (Entry<String, Section> entry : sections.entrySet()) {
			if (entry.getKey().endsWith(suffix)) {
				Section section = entry.getValue();
		    	
		    	section.setImage(SectionUtils.cropSection(img, section));
			}
	    }
	}
	
	public static void writeSections(Map<String, Section> sections, String directory) {
		for (Entry<String, Section> entry : sections.entrySet()) {
	    	Section section = entry.getValue();
	    	
	    	ImageUtils.writeImage(section.getImage(), directory, entry.getKey() + ".png");
	    }
	}
	
	public static void combineSections(Map<String, Section> sections, String config, String filename, String directory) {
		Image out = new BufferedImage(combinedImageWidth, combinedImageHeight, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = out.getGraphics();
		BufferedReader reader = null;
		String line = null;
		
		try {
			reader = FileUtils.loadConfig(config);
			
			while ((line = reader.readLine()) != null)   {
				String[] data = line.split("\\|");
				int[] off = TransformationUtils.mapToInt(data[1], ",");
				Section section = sections.get(data[0]);
				Point offset = new Point(off[0], off[1]);
				boolean mirror = Boolean.parseBoolean(data[2]);
				int direction = mirror ? -1 : 1;
				
				g.drawImage(section.getImage(), direction * offset.x, offset.y, null);
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
		
		ImageUtils.writeImage(out, directory, filename);
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

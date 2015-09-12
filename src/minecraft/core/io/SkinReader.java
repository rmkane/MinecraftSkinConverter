package minecraft.core.io;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import minecraft.core.Section;
import minecraft.core.util.FileUtils;
import minecraft.core.util.ImageUtils;
import minecraft.core.util.SectionUtils;

public class SkinReader {
	public static void processSkins(String configPath, Map<String, Section> sections, String[] skinPaths) {
		Image[] skins = new Image[skinPaths.length];
		
		for (int i = 0; i < skins.length; i++) {
			skins[i] = ImageUtils.loadImage(skinPaths[i]);
		}
		
		processSkins(configPath, sections, skins);
	}
	
	public static void processSkins(String configPath, Map<String, Section> sections, Image[] skins) {
		for (int i = 0; i < skins.length; i++) {
			processSkin(configPath, sections, skins[i], Integer.toString(i));
		}
	}
	
	public static void processSkin(String configPath, Map<String, Section> sections, Image skin, String suffix) {
		loadSections(configPath, sections, skin, suffix);
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
	
	public static void loadSections(String configPath, Map<String, Section> sections, Image image, String suffix) {
		BufferedReader reader = null;
		String line = null;
		
		try {
			reader = FileUtils.loadConfig(configPath);
			
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

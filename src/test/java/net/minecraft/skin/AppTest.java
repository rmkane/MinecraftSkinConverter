package net.minecraft.skin;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.skin.core.Section;
import net.minecraft.skin.core.io.ConfigMap;
import net.minecraft.skin.core.io.SkinReader;
import net.minecraft.skin.core.io.SkinWriter;
import net.minecraft.skin.core.util.FileUtils;

public class AppTest {
	public static void main(String[] args) {
		example();
	}
	
	public static void example() {
		ConfigMap configMap = App.defaultConfigMap();
		String skinDirectory = App.getResourceDirectory("skins");
		String outputDirectory = FileUtils.toPath("output", "skins");

		String[] skins = {
			FileUtils.toPath(skinDirectory, "x-tony-stark.png"),
			FileUtils.toPath(skinDirectory, "x-iron-man.png")
		};
		
		export(configMap, skins, outputDirectory, "combined.png");
	}
	
	public static void export(ConfigMap configMap, String[] skins, String directory, String exportPath) {
		Map<String, Section> sections = new HashMap<String, Section>();

		String readerConfig = configMap.getConfig("reader"); 
		String writerConfig = configMap.getConfig("writer" + skins.length);
		
		SkinReader.processSkins(readerConfig, sections, skins);
		SkinWriter.writeSections(sections, directory);
		SkinWriter.writeCombined(writerConfig, sections, exportPath, directory);
	}
}

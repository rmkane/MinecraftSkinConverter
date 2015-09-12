import java.util.HashMap;
import java.util.Map;

import minecraft.core.Section;
import minecraft.core.io.SkinReader;
import minecraft.core.io.SkinWriter;
import minecraft.core.util.FileUtils;

public class AppTest {
	private static String ouputDirectory = "output";
	private static String configDirectory = "config";
	private static String readerConfig = FileUtils.toPath(configDirectory, "reader.conf");
	private static String writerConfig1 = FileUtils.toPath(configDirectory, "writer1.conf");
	private static String writerConfig2 = FileUtils.toPath(configDirectory, "writer2.conf");

	public static void main(String[] args) {
		example();
	}
	
	public static void example() {
		String directory = "skins";

		String[] skins = {
			//FileUtils.toPath(directory, "x-tony-stark.png"),
			FileUtils.toPath(directory, "x-iron-man.png")
		};
		
		String writerConf = skins.length == 1 ? writerConfig1 : writerConfig2;

		export(skins, directory, writerConf, "combined.png");
	}
	
	public static void export(String[] skins, String directory, String writerConf, String exportPath) {
		Map<String, Section> sections = new HashMap<String, Section>();

		String outDir = FileUtils.toPath(ouputDirectory, directory);

		SkinReader.processSkins(readerConfig, sections, skins);
		SkinWriter.writeSections(sections, outDir);
		SkinWriter.writeCombined(writerConf, sections, exportPath, outDir);
	}
}

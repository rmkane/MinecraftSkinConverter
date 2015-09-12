import java.util.HashMap;
import java.util.Map;

import core.Section;
import core.io.SkinReader;
import core.io.SkinWriter;
import core.util.FileUtils;

public class App {
	private static String ouputDirectory = "output";
	private static String configDirectory = "config";
	private static String readerConfig = FileUtils.toPath(configDirectory, "reader.conf");
	private static String writerConfig = FileUtils.toPath(configDirectory, "writer.conf");

	public static void main(String[] args) {
		example();
	}
	
	public static void example() {
		String directory = "skins";

		String[] skins = {
			FileUtils.toPath(directory, "x-tony-stark.png"),
			FileUtils.toPath(directory, "x-iron-man.png")
		};

		export(skins, directory, "combined.png");
	}
	
	public static void export(String[] skins, String directory, String exportPath) {
		Map<String, Section> sections = new HashMap<String, Section>();

		String outDir = FileUtils.toPath(ouputDirectory, directory);

		SkinReader.processSkins(readerConfig, sections, skins);
		SkinWriter.writeSections(sections, outDir);
		SkinWriter.writeCombined(writerConfig, sections, exportPath, outDir);
	}
}

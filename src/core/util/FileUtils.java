package core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {
	private static ClassLoader loader = FileUtils.class.getClassLoader();
	
	public static InputStream getInputStream(String filename, boolean isResource) throws FileNotFoundException {
        if (isResource) {
            return loader.getResourceAsStream("resources/" + filename);
        } else {
            return new FileInputStream(new File(filename));
        }
    }
	
	public static BufferedReader loadConfig(String name) throws FileNotFoundException {
		return new BufferedReader(new InputStreamReader(getInputStream(name, true)));
	}
	
	public static String toPath(String... path) {
		return StringUtils.join(path, File.separator);
	}
}

package core;

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
		return join(path, File.separator);
	}
	
	public static String join(String[] path, String str) {
		StringBuffer buff = new StringBuffer();
		
		if (path != null && path.length > 0) {
			for (int i = 0; i < path.length; i++) {
				if (i > 0) {
					buff.append(str);
				}
				buff.append(path[i]);
			}
		}
		
		return buff.toString();
	}
}

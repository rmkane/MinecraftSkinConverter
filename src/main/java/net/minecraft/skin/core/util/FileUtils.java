package net.minecraft.skin.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;

public class FileUtils {
	private static ClassLoader loader = FileUtils.class.getClassLoader();
	
	public static InputStream getInputStream(String filename, boolean isResource) throws FileNotFoundException {
		System.out.println("Resource Path: " + filename);
		
		try {
			Enumeration<URL> resources = loader.getResources(filename);
			System.out.println("Found resources...");
			while(resources.hasMoreElements()) {
				System.out.println("- " + resources.nextElement());
			}
		} catch (IOException e) {
			System.out.println("Could not find resources...");
			e.printStackTrace();
		}
		
        if (isResource) {
            return loader.getResourceAsStream(filename);
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

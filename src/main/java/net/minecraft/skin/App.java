package net.minecraft.skin;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.minecraft.skin.core.io.ConfigMap;
import net.minecraft.skin.core.util.FileUtils;
import net.minecraft.skin.gui.MainPanel;

public class App {
	private static final String APPLICATION_NAME = "Mincraft Skin Converter";
	
	public static void main(String[] args) {
		setLookAndFeel();
		
		
		String resourceName = "net/minecraft/skin/config/reader.conf";
		try {
			InputStream stream = FileUtils.getInputStream(resourceName, true);
			Scanner s = new Scanner(stream);
			s.useDelimiter("\\A");
			System.out.println(s.next());
			s.close();
		} catch (FileNotFoundException e) {
			System.out.println("Could Not Find Resource: " + resourceName);
			e.printStackTrace();
		}
		
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame(APPLICATION_NAME);
				MainPanel panel = new MainPanel();

				// Inject config map.
				panel.setConfigMap(defaultConfigMap());
				
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setContentPane(panel);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
	
	public static BufferedReader loadConfig(String name) throws FileNotFoundException {
		return FileUtils.loadConfig(name);
	}
	
	public static void setLookAndFeel() {
		try {
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}
	}
	
	public static ConfigMap defaultConfigMap() {
		return new ConfigMap(getConfigDirectory());
	}
	
	public static String getConfigDirectory() {
		return getResourceDirectory("config");
	}
	
	public static String getResourceDirectory(String resourceDirectory) {
		return FileUtils.toPath(getRootPackage(App.class), resourceDirectory);
	}
	
	private static String getRootPackage(Class<?> clazz) {
	    return clazz.getPackage().getName().replace(".", File.separator);
	}
}

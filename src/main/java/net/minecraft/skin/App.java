package net.minecraft.skin;
import java.io.File;

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

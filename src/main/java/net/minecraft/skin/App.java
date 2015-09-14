package net.minecraft.skin;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.minecraft.skin.core.io.ConfigMap;
import net.minecraft.skin.core.util.FileUtils;
import net.minecraft.skin.gui.AppConfig;
import net.minecraft.skin.gui.AppIcons;
import net.minecraft.skin.gui.MainView;

public class App {
	public static void main(String[] args) {
		setLookAndFeel();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame(AppConfig.APP_TITLE);
				MainView view = new MainView();

				// Inject configuration map.
				view.setConfigMap(defaultConfigMap());

				// Set application icon and menu.
				frame.setIconImage(AppIcons.getAppImage());
				frame.setJMenuBar(view.getMenuBar());
				
				// Render the frame.
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setContentPane(view);
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
		return clazz.getPackage().getName().replace(".", "/");
	}
}

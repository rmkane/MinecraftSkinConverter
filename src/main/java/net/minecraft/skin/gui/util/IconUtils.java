package net.minecraft.skin.gui.util;

import java.awt.Image;

import javax.swing.ImageIcon;

import net.minecraft.skin.core.util.FileUtils;

public class IconUtils {
	public static Image loadResourceImage(String iconName) {
		return loadResourceIcon(iconName).getImage();
	}

	public static ImageIcon loadResourceIcon(String iconName) {
		return loadIcon(FileUtils.toPath("assets", "icons", iconName));
	}
	
	public static ImageIcon loadIcon(String path) {
		return new ImageIcon(IconUtils.class.getClassLoader().getResource(path));
	}
}
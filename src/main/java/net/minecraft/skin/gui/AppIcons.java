package net.minecraft.skin.gui;

import java.awt.Image;

import javax.swing.ImageIcon;

import net.minecraft.skin.gui.util.IconUtils;

public class AppIcons {
	public static final Image getAppImage() {
		return AppIcons.getAppImage(64);
	}

	public static final ImageIcon getAppIcon() {
		return AppIcons.getAppIcon(16);
	}

	public static final ImageIcon getAppLogo() {
		return AppIcons.getAppIcon(128);
	}

	public static final ImageIcon getExitIcon() {
		return IconUtils.loadResourceIcon("door.png");
	}

	private static Image getAppImage(int size) {
		return AppIcons.getAppIcon(size).getImage();
	}

	private static ImageIcon getAppIcon(int size) {
		return IconUtils.loadResourceIcon(String.format("app-icon-%d.png", size));
	}
}

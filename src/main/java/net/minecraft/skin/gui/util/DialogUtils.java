package net.minecraft.skin.gui.util;

import java.awt.Component;

import javax.swing.JOptionPane;

public class DialogUtils {
	public static void showErrorMessage(Component parentComponent, String message, String title) {
		showMessage(parentComponent, title, message, JOptionPane.ERROR_MESSAGE);
	}

	public static void showErrorMessage(Component parentComponent, String message) {
		showErrorMessage(parentComponent, message, "Error");
	}

	public static void showErrorMessage(String message) {
		showErrorMessage(null, message);
	}

	public static void showSuccessMessage(Component parentComponent, String message, String title) {
		showMessage(parentComponent, title, message, JOptionPane.INFORMATION_MESSAGE);
	}

	public static void showSuccessMessage(Component parentComponent, String message) {
		showSuccessMessage(parentComponent, message, "Success");
	}

	public static void showSuccessMessage(String message) {
		showSuccessMessage(null, message);
	}

	public static void showMessage(Component parentComponent, String title, String message, int messageType) {
		JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
	}
}
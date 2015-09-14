package net.minecraft.skin.gui.util;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.EventListener;

import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MenuUtils {
	public static JMenu createMenu(String label, int mnemonic, String description) {
		return createMenuItem(new JMenu(label), mnemonic, description, null);
	}

	public static <T extends Action> JMenuItem createMenu(Class<T> actionClass) {
		try {
			return new JMenuItem(actionClass.newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JMenuItem createMenuItem(String label, int mnemonic, String description, ActionListener action) {
		return createMenuItem(new JMenuItem(label), mnemonic, description, action);
	}

	public static JCheckBoxMenuItem createCheckBoxMenuItem(String label, int mnemonic, String description, ItemListener action) {
		return createMenuItem(new JCheckBoxMenuItem(label), mnemonic, description, action);
	}

	public static <T extends JMenuItem, E extends EventListener> T createMenuItem(T source, int mnemonic, String description, E action) {
		source.setMnemonic(mnemonic);
		source.getAccessibleContext().setAccessibleDescription(description);

		if (action instanceof ActionListener) {
			source.addActionListener((ActionListener) action);
		} else if (action instanceof ItemListener) {
			source.addItemListener((ItemListener) action);
		}

		return source;
	}
}

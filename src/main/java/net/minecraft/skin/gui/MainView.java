package net.minecraft.skin.gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.sun.glass.events.KeyEvent;

import net.minecraft.skin.core.Section;
import net.minecraft.skin.core.io.ConfigMap;
import net.minecraft.skin.core.io.SkinReader;
import net.minecraft.skin.core.io.SkinWriter;
import net.minecraft.skin.gui.action.MenuItemAction;
import net.minecraft.skin.gui.component.AboutPanel;
import net.minecraft.skin.gui.layout.GridBagLayoutPanel;
import net.minecraft.skin.gui.skin.InputSkinPanel;
import net.minecraft.skin.gui.skin.OutputSkinPanel;
import net.minecraft.skin.gui.util.DialogUtils;
import net.minecraft.skin.gui.util.MenuUtils;

public class MainView extends GridBagLayoutPanel {
	private static final long serialVersionUID = 2811241532549531812L;

	private JMenuBar menuBar;
	private ConfigMap configMap;
	private AboutPanel aboutPanel;
	private InputSkinPanel skinPanelInput1;
	private InputSkinPanel skinPanelInput2;
	private OutputSkinPanel skinPanelOutput;

	private JButton convBtn;

	public MainView() {
		super();

		init();
	}

	@Override
	protected void init() {
		super.init();

		createMenu();
	}

	@Override
	protected void initialize() {
		int scale = 6;

		this.skinPanelInput1 = new InputSkinPanel("Select Primary Layer", scale);
		this.skinPanelInput2 = new InputSkinPanel("Select Secondary Layer", scale);
		this.skinPanelOutput = new OutputSkinPanel("Combined Output", scale);

		this.convBtn = new JButton("Convert >");

		convBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Image skin1 = skinPanelInput1.getImage();
				Image skin2 = skinPanelInput2.getImage();
				Image skin3 = null;

				if (skin1 != null && skin2 != null) {
					skin3 = combine(configMap, skin1, skin2);
				} else if (skin1 != null && skin2 == null) {
					skin3 = combine(configMap, skin1);
				} else if (skin1 == null && skin2 != null) {
					skin3 = combine(configMap, skin2);
				} else {
					DialogUtils.showErrorMessage("Please load at least 1 skin.");
					return;
				}

				skinPanelOutput.setImage((BufferedImage) skin3);
			}
		});
	}

	protected void createChildren() {
		addComponent(this, skinPanelInput1, 0, 0, 1, 1, 2);
		addComponent(this, skinPanelInput2, 2, 0, 1, 1, 2);
		addComponent(this, convBtn, 0, 1, 1, 3, 2);
		addComponent(this, skinPanelOutput, 0, 2, 1, 3, 2);
	}

	private void createMenu() {
		menuBar = new JMenuBar();

		// Create menus.
		JMenu fileMenu = MenuUtils.createMenu("File", KeyEvent.VK_F, "");
		JMenu helpMenu = MenuUtils.createMenu("Help", KeyEvent.VK_H, "");

		// Create menu items.
		JMenuItem aboutMenu = new JMenuItem(new AboutAction());
		JMenuItem exitMenu = new JMenuItem(new ExitAction());

		// Add menu items to menus.
		fileMenu.add(exitMenu);
		helpMenu.add(aboutMenu);

		// Add menus to menubar.
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}

	public ConfigMap getConfigMap() {
		return configMap;
	}

	public void setConfigMap(ConfigMap configMap) {
		this.configMap = configMap;
	}

	public static Image combine(ConfigMap configMap, Image... skins) {
		Map<String, Section> sections = new HashMap<String, Section>();
		String readerConfig = configMap.getConfig("reader"); 
		String writerConfig = configMap.getConfig("writer" + skins.length); 

		SkinReader.processSkins(readerConfig, sections, skins);

		return SkinWriter.combineSections(writerConfig, sections);
	}

	public static String getConfig(Map<String, String> map, String name) {
		return map.get(name);
	}

	// ========================================================================
	// Menu Actions
	// ========================================================================
	private class AboutAction extends MenuItemAction {
		private static final long serialVersionUID = -6266116884935376990L;

		public AboutAction() {
			super("About", AppIcons.getAppIcon(), KeyEvent.VK_A, "About application");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (aboutPanel == null) {
				aboutPanel = new AboutPanel();
			}

			JOptionPane.showConfirmDialog(null, aboutPanel,
					AppConfig.APP_TITLE,
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
		}
	}

	private class ExitAction extends MenuItemAction {
		private static final long serialVersionUID = -2045597246972400617L;

		public ExitAction() {
			super("Exit", AppIcons.getExitIcon(), KeyEvent.VK_X, "Exit application.");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int option = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to exit?", "Confirm Exit",
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

			if (option == JOptionPane.YES_OPTION) {
				System.exit(0);
			} else {
				//JOptionPane.showMessageDialog(null, "Action aborted.",
				//		appTitle, JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}

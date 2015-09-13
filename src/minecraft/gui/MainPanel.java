package minecraft.gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;

import minecraft.core.Section;
import minecraft.core.io.SkinReader;
import minecraft.core.io.SkinWriter;
import minecraft.core.util.FileUtils;
import minecraft.gui.layout.GridBagLayoutPanel;
import minecraft.gui.skin.InputSkinPanel;
import minecraft.gui.skin.OutputSkinPanel;
import minecraft.gui.util.GuiUtils;

public class MainPanel extends GridBagLayoutPanel {
	private static final long serialVersionUID = 2811241532549531812L;

	private static String configDirectory = "config";
	private static String readerConfig = FileUtils.toPath(configDirectory, "reader.conf");
	private static String writerConfig1 = FileUtils.toPath(configDirectory, "writer1.conf");
	private static String writerConfig2 = FileUtils.toPath(configDirectory, "writer2.conf");

	private InputSkinPanel skinPanelInput1;
	private InputSkinPanel skinPanelInput2;
	private OutputSkinPanel skinPanelOutput;

	private JButton convBtn;

	public MainPanel() {
		super();

		init();
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
					skin3 = combine(skin1, skin2);
				} else if (skin1 != null && skin2 == null) {
					skin3 = combine(skin1);
				} else if (skin1 == null && skin2 != null) {
					skin3 = combine(skin2);
				} else {
					GuiUtils.showErrorMessage("Please load at least 1 skin.");
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

	public static Image combine(Image... skins) {
		Map<String, Section> sections = new HashMap<String, Section>();
		String configPath = skins.length == 1 ? writerConfig1 : writerConfig2;

		SkinReader.processSkins(readerConfig, sections, skins);

		return SkinWriter.combineSections(configPath, sections);
	}
}

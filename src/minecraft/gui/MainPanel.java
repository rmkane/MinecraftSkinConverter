package minecraft.gui;

import minecraft.core.util.FileUtils;
import minecraft.gui.layout.GridBagLayoutPanel;
import minecraft.gui.skin.InputSkinPanel;
import minecraft.gui.skin.OutputSkinPanel;
import minecraft.gui.skin.SkinPanel;

public class MainPanel extends GridBagLayoutPanel {
	private static final long serialVersionUID = 2811241532549531812L;

	private static String configDirectory = "config";
	private static String readerConfig = FileUtils.toPath(configDirectory, "reader.conf");
	private static String writerConfig1 = FileUtils.toPath(configDirectory, "writer1.conf");
	private static String writerConfig2 = FileUtils.toPath(configDirectory, "writer2.conf");
	
	public MainPanel() {
		super(700, 500);
		
		init();
	}
	
	@Override
	protected void initialize() {
		
	}

	protected void createChildren() {
		int scale = 4;
		
		SkinPanel skinPanelInput1 = new InputSkinPanel("Select Primary Layer", scale);
		SkinPanel skinPanelInput2 = new InputSkinPanel("Select Secondary Layer" ,scale);
		SkinPanel skinPanelOutput = new OutputSkinPanel("Combined Output", scale);
		
		addComponent(this, skinPanelInput1, 0, 0, 1, 1, 2);
		addComponent(this, skinPanelInput2, 1, 0, 1, 1, 2);
		addComponent(this, skinPanelOutput, 0, 1, 1, 2, 2);
	}
}

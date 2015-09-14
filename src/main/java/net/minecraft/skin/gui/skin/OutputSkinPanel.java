package net.minecraft.skin.gui.skin;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import net.minecraft.skin.gui.util.DialogUtils;

public class OutputSkinPanel extends SkinPanel {
	private static final long serialVersionUID = -4132272902757750659L;

	public OutputSkinPanel(String defaultLabelText, int scale) {
		super(64, 64, scale, defaultLabelText, "Save");
	}
	
	@Override
	protected void initialize() {
		super.initialize();
						
		getActionBtn().addActionListener(new ExportAction(this));
	}
	
	public void handleExportImage(JFileChooser fileChooser) throws IOException {
		BufferedImage outputImage = this.getImage();
		
		if (outputImage == null) {
			DialogUtils.showErrorMessage("Cannot export because the output image is null.");
			return;
		}

		int returnVal = fileChooser.showSaveDialog(this.getParent());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			DialogUtils.showSuccessMessage("Saving: " + file.getName() + "...");

			try {
				ImageIO.write(outputImage, "png", file);
				DialogUtils.showSuccessMessage("Successfully exported: " + file.getName());
			} catch (IOException e1) {
				DialogUtils.showErrorMessage("Could not save: " + file.getName());
			}
		} else {
			DialogUtils.showSuccessMessage("Save command cancelled.");
		}
	}
	
	private class ExportAction extends AbstractAction {
		private static final long serialVersionUID = 709910346625017825L;
		
		private OutputSkinPanel panel;
		
		public ExportAction(OutputSkinPanel panel) {
			super();
			
			this.panel = panel;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				panel.handleExportImage(EXPLORER);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}

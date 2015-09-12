package minecraft.gui.skin;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import minecraft.core.util.ImageUtils;
import minecraft.gui.util.GuiUtils;

public class InputSkinPanel extends SkinPanel {
	private static final long serialVersionUID = 1123342531732136208L;

	public InputSkinPanel(String defaultLabelText, int scale) {
		super(64, 32, scale, defaultLabelText, "Open");
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		
		SkinPanel self = this;
		
		openBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					handleLoadImage(EXPLORER, self, self.getParent());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	
	public static BufferedImage handleLoadImage(JFileChooser fileChooser, SkinPanel panel, Component parent) throws IOException {
		BufferedImage image = null;
		
		fileChooser.setFileFilter(EXT_FILTER);
		
		int returnVal = fileChooser.showOpenDialog(parent);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			GuiUtils.showSuccessMessage("Opening: " + file.getName() + "...");

			// Load and draw image.
			image = (BufferedImage) ImageUtils.loadImage(file.getAbsolutePath(), false);

			if (image == null) {
				GuiUtils.showErrorMessage("Not a valid image file.");
				throw new IOException();
			}

			panel.setName(file.getName());
			panel.setImage(image);

		} else {
			GuiUtils.showSuccessMessage("Open command cancelled.");
		}

		return image;
	}
}

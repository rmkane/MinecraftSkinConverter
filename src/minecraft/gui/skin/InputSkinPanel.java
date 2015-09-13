package minecraft.gui.skin;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import minecraft.core.util.ImageUtils;
import minecraft.gui.util.GuiUtils;

public class InputSkinPanel extends SkinPanel {
	private static final long serialVersionUID = 1123342531732136208L;

	protected JButton clearBtn;
	private String filePath;
	
	public InputSkinPanel(String defaultLabelText, int scale) {
		super(64, 32, scale, defaultLabelText, "Open");
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		
		this.clearBtn = new JButton("Clear");
		
		getActionBtn().addActionListener(new LoadAction(this));
		clearBtn.addActionListener(new ClearAction(this));
	}
	
	@Override
	protected void createChildren() {
		super.createChildren();
		
		getButtonPanel().add(clearBtn);
	}
	
	public void handleLoadImage(JFileChooser fileChooser) throws IOException {
		BufferedImage image = null;
		
		fileChooser.setFileFilter(EXT_FILTER);
		
		int returnVal = fileChooser.showOpenDialog(this.getParent());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			GuiUtils.showSuccessMessage("Opening: " + file.getName() + "...");

			// Load and draw image.
			image = (BufferedImage) ImageUtils.loadImage(file.getAbsolutePath(), false);

			if (image == null) {
				GuiUtils.showErrorMessage("Not a valid image file.");
				throw new IOException();
			}

			setName(file.getName());
			setImage(image);
			setFilePath(file.getAbsolutePath());
		} else {
			GuiUtils.showSuccessMessage("Open command cancelled.");
		}
	}
	
	protected void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public BufferedImage getImage() {
		BufferedImage image = getCanvas().getImage();
		
		if (image != null) {
			return image;
		}
		
		if (filePath != null) {
			return (BufferedImage) ImageUtils.loadImage(filePath, false);
		}
		
		return null;
	}
	
	private class LoadAction extends AbstractAction {
		private static final long serialVersionUID = 5472577683532689561L;
		
		private InputSkinPanel panel;
		
		public LoadAction(InputSkinPanel panel) {
			super();
			
			this.panel = panel;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				panel.handleLoadImage(EXPLORER);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private class ClearAction extends AbstractAction {
		private static final long serialVersionUID = -3858819891767723345L;
		
		private InputSkinPanel panel;
		
		public ClearAction(InputSkinPanel panel) {
			super();
			
			this.panel = panel;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			panel.setImage(null);
			panel.setName(defaultLabelText);
			panel.setFilePath(null);
		}
	}
}

package net.minecraft.skin.gui.skin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.minecraft.skin.gui.component.ImageCanvas;
import net.minecraft.skin.gui.layout.BorderLayoutPanel;

public class SkinPanel extends BorderLayoutPanel {
	private static final long serialVersionUID = -2726935343890248609L;

	// Skin file filter
	protected static final FileNameExtensionFilter EXT_FILTER = new FileNameExtensionFilter("Skin Files", "png", "gif");

	// Create a file chooser
	protected final JFileChooser EXPLORER = new JFileChooser(System.getenv("user.dir"));

	private ImageCanvas canvas;
	private JLabel name;
	private JPanel buttonPanel;
	private JButton actionBtn;
	private int scale;

	private int imgWidth;
	private int imgHeight;

	protected String defaultLabelText;
	protected String buttonText;

	public SkinPanel(int imgWidth, int imgHeight, int scale, String defaultLabelText, String buttonText) {
		super();

		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
		this.scale = scale;

		this.defaultLabelText = defaultLabelText;
		this.buttonText = buttonText;

		init();
	}

	@Override
	protected void initialize() {
		this.buttonPanel = new JPanel(new GridLayout(1, 2));
		this.canvas = new ImageCanvas(2, true, true);
		this.name = new JLabel(defaultLabelText);
		this.actionBtn = new JButton(buttonText);
	}

	@Override
	protected void createChildren() {
		getCanvas().setPreferredSize(new Dimension(imgWidth * scale, imgHeight * scale));

		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setBackground(Color.WHITE);

		getActionBtn().setPreferredSize(new Dimension(60, 24));

		getButtonPanel().add(getActionBtn());

		this.add(name, BorderLayout.NORTH);
		this.add(getCanvas(), BorderLayout.CENTER);
		this.add(getButtonPanel(), BorderLayout.SOUTH);
	}

	public void setName(String filename) {
		this.name.setText(filename);
	}

	public BufferedImage getImage() {
		return getCanvas().getImage();
	}

	public void setImage(BufferedImage image) {
		this.getCanvas().setImage(image);
		this.repaint();
	}

	public JButton getActionBtn() {
		return actionBtn;
	}

	public ImageCanvas getCanvas() {
		return canvas;
	}

	protected JPanel getButtonPanel() {
		return buttonPanel;
	}
}

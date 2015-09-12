package minecraft.gui.skin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import minecraft.gui.component.ImageCanvas;
import minecraft.gui.layout.BorderLayoutPanel;

public class SkinPanel extends BorderLayoutPanel {
	private static final long serialVersionUID = -2726935343890248609L;

	// Skin file filter
	protected static final FileNameExtensionFilter EXT_FILTER = new FileNameExtensionFilter("Skin Files", "png", "gif");
	
	// Create a file chooser
	protected final JFileChooser EXPLORER = new JFileChooser("./");
	
	protected ImageCanvas canvas;
	protected JLabel name;
	protected JButton openBtn;
	protected int scale;
	
	private int imgWidth;
	private int imgHeight;
	
	protected String defaultLabelText;
	protected String buttonText;

	public SkinPanel() {
		super();
		
		init();
	}
	
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
		this.canvas = new ImageCanvas(2, true, true);
		this.name = new JLabel(defaultLabelText);
		this.openBtn = new JButton(buttonText);
	}

	@Override
	protected void createChildren() {
		canvas.setPreferredSize(new Dimension(imgWidth * scale, imgHeight * scale));
		
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setBackground(Color.WHITE);
		
		openBtn.setPreferredSize(new Dimension(60, 24));
				
		this.add(name,   BorderLayout.NORTH);
		this.add(canvas, BorderLayout.CENTER);
		this.add(openBtn, BorderLayout.SOUTH);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
	}
	
	public void setName(String filename) {
		this.name.setText(filename);
	}
	
	public BufferedImage getImage() {
		return canvas.getImage();
	}

	public void setImage(BufferedImage image) {
		this.canvas.setImage(image);
		this.repaint();
	}
}

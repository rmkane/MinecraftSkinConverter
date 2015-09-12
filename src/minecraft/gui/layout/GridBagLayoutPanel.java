package minecraft.gui.layout;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;

public abstract class GridBagLayoutPanel extends LayoutPanel {
	private static final long serialVersionUID = -4049364819569567698L;

	public GridBagLayoutPanel() {
		super(new GridBagLayout());
		
		init();
	}

	public GridBagLayoutPanel(int width, int height) {
		super(new GridBagLayout());

		this.setPreferredSize(new Dimension(width, height));
		
		init();
	}

	public void addComponent(Container container, JComponent component, int row, int col) {
		addComponent(container, component, row, col, 1, 1, 0);
	}

	public void addComponent(Container container, JComponent component, int row, int col, int colspan, int rowspan, int margin) {
		addComponent(container, component, row, col, colspan, rowspan, margin, GridBagConstraints.HORIZONTAL);
	}
	
	public void addComponent(Container container, JComponent component, int row, int col, int colspan, int rowspan, int margin, int fill) {
		addComponent(container, component, row, col, colspan, rowspan, margin, margin, margin, margin, fill);
	}

	public void addComponent(Container container, JComponent component, int row, int col, int colspan, int rowspan, int marginTop, int marginLeft, int marginBottom, int marginRight, int fill) {
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = col;
		gbc.gridy = row;
		gbc.gridwidth = colspan;
		gbc.gridheight = rowspan;
		gbc.fill = fill;
		
		gbc.insets = new Insets(marginTop, marginLeft, marginBottom, marginRight);

		container.add(component, gbc);
	}
}

package minecraft.gui.layout;

import java.awt.LayoutManager;

import javax.swing.JPanel;

public abstract class LayoutPanel extends JPanel {
	private static final long serialVersionUID = 747440740473596984L;

	public LayoutPanel(LayoutManager layout) {
		super(layout);
	}
	
	protected void init() {
		initialize();
		createChildren();
	}

	protected abstract void initialize();

	protected abstract void createChildren();
}

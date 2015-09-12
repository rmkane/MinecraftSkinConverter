import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import minecraft.gui.MainPanel;

public class App {
	private static final String APPLICATION_NAME = "Mincraft Skin Converter";

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame(APPLICATION_NAME);
				MainPanel panel = new MainPanel();
				
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setContentPane(panel);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}

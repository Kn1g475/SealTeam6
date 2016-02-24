package GUI;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.Constants;

/**
 * This panel one of the content windows that get switched by the Main GUI. Its
 * only purpose is to display simple text.
 * 
 * @author matt
 * 
 */
@SuppressWarnings("serial")
public class About extends JPanel {

	/**
	 * Creates the about panel
	 */
	public About() {
		setBackground(Constants.CONTENT_BACKGROUND_COLOR);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JLabel about = new JLabel(Constants.ABOUT);
		add(about);
	}
}

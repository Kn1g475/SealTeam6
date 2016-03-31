package GUI;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.Constants;





@SuppressWarnings("serial")
public class Schedule extends JPanel{

	
	
	public Schedule() {
		setBackground(Constants.CONTENT_BACKGROUND_COLOR);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JLabel about = new JLabel();
		add(about);
	}
}

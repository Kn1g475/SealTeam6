
	package GUI;

	import javax.swing.BoxLayout;
	import javax.swing.JLabel;
	import javax.swing.JPanel;

	import Main.Constants;
import java.awt.CardLayout;

	/**
	 * This panel one of the content windows that get switched by the Main GUI. Its
	 * only purpose is to display simple text.
	 * 
	 * @author matt
	 * 
	 */
	@SuppressWarnings("serial")
	public class Profile extends JPanel {

		/**
		 * Creates the profile panel
		 */
		public Profile() {
			setBackground(Constants.CONTENT_BACKGROUND_COLOR);
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			JLabel about = new JLabel(Constants.PROFILE);// Constants.ABOUT);
			add(about);
			
			
		}
	}



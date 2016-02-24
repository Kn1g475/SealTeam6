package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.Constants;
import Main.Main.ButtonListener;

/**
 * The top bar of the GUI.
 * 
 * @author matt
 * 
 */
public class TopBar extends JPanel {

	public JLabel fileLabel, errorLabel;
	public JButton addButton, submitButton, deleteButton;

	/**
	 * Creates a new top bar object, takes in a listener class so that this
	 * object can communicate with the main class
	 * 
	 * @param buttonListener
	 */
	public TopBar(ButtonListener buttonListener) {

		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setPreferredSize(new Dimension(0, Constants.TOP_ROW_HEIGHT));
		this.setBackground(Constants.MENU_BACKGROUND_COLOR);
		this.setBorder(BorderFactory.createMatteBorder(0, 0,
				Constants.TOP_ROW_BORDER_THICKNESS, 0,
				Constants.TOP_ROW_BORDER_COLOR));
		
		// set up Icon and Program Title in top left corner
		JLabel title = new JLabel();
		Image img = new ImageIcon(Constants.MIAMI_ICON_NAME).getImage();
		img = img.getScaledInstance(Constants.TOP_ROW_HEIGHT,
				Constants.TOP_ROW_HEIGHT, Image.SCALE_SMOOTH);
		title.setIcon(new ImageIcon(img));
		title.setFont(Constants.TITLE_FONT);
		title.setText(Constants.TITLE);
		this.add(title);

		this.add(Box.createHorizontalGlue());
		
		
		// set up all buttons and labels
		JPanel buttonsGroup = new JPanel();
		buttonsGroup
				.setLayout(new BoxLayout(buttonsGroup, BoxLayout.PAGE_AXIS));
		buttonsGroup.setBackground(Constants.MENU_BACKGROUND_COLOR);
		JPanel fileButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		fileLabel = new JLabel();
		fileLabel.setFont(Constants.SMALL_FONT);
		addButton = new JButton("Add File");
		addButton.setActionCommand("addButton");
		addButton.addActionListener(buttonListener);
		deleteButton = new JButton("Remove");
		deleteButton.setActionCommand("deleteButton");
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(buttonListener);
		submitButton = new JButton("Submit");
		submitButton.setActionCommand("submitButton");
		submitButton.setEnabled(false);
		submitButton.addActionListener(buttonListener);

		// add buttons and labels to GUI
		fileButtons.add(fileLabel);
		fileButtons.add(addButton);
		fileButtons.add(deleteButton);
		fileButtons.add(submitButton);
		fileButtons.setBackground(Constants.MENU_BACKGROUND_COLOR);

		errorLabel = new JLabel();
		errorLabel.setFont(Constants.SMALL_FONT);
		errorLabel.setForeground(Color.RED);
		errorLabel.setBackground(Constants.MENU_BACKGROUND_COLOR);

		buttonsGroup.add(fileButtons);
		buttonsGroup.add(errorLabel);
		
		this.add(buttonsGroup);
	}

}

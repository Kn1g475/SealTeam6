package GUI;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.Constants;

/**
 * The side bar of the GUI.
 * 
 * @author matt
 * 
 */
public class SideBar extends JPanel {

	public JLabel aboutButton, instructionsButton, reportButton;

	/**
	 * Creates a new side bar object, takes in a listener class so that this
	 * object can communicate with the main class
	 * 
	 * @param sideButtonListener
	 */
	public SideBar(MouseListener sideButtonListener) {

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBackground(Constants.MENU_BACKGROUND_COLOR);
		// create 10 pixels of padding
		this.setBorder(BorderFactory.createLineBorder(
				Constants.MENU_BACKGROUND_COLOR, 10));

		// about button
		aboutButton = new JLabel();
		aboutButton.setFont(Constants.MAIN_FONT);
		Image aboutImg = new ImageIcon(Constants.ABOUT_ICON_NAME).getImage();
		aboutImg = aboutImg.getScaledInstance(Constants.MAIN_FONT.getSize(),
				Constants.MAIN_FONT.getSize(), Image.SCALE_SMOOTH);
		aboutButton.setIcon(new ImageIcon(aboutImg));
		aboutButton.setText("About");
		aboutButton.addMouseListener(sideButtonListener);
		aboutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// instructions button
		instructionsButton = new JLabel();
		instructionsButton.setFont(Constants.MAIN_FONT);
		Image instructionsImg = new ImageIcon(Constants.INSTRUCTIONS_ICON_NAME)
				.getImage();
		instructionsImg = instructionsImg.getScaledInstance(
				Constants.MAIN_FONT.getSize(), Constants.MAIN_FONT.getSize(),
				Image.SCALE_SMOOTH);
		instructionsButton.setIcon(new ImageIcon(instructionsImg));
		instructionsButton.setText("Instructions");
		instructionsButton.addMouseListener(sideButtonListener);
		instructionsButton.setCursor(Cursor
				.getPredefinedCursor(Cursor.HAND_CURSOR));

		// report button
		reportButton = new JLabel();
		reportButton.setFont(Constants.MAIN_FONT);
		Image reportImg = new ImageIcon(Constants.REPORT_ICON_NAME).getImage();
		reportImg = reportImg.getScaledInstance(Constants.MAIN_FONT.getSize(),
				Constants.MAIN_FONT.getSize(), Image.SCALE_SMOOTH);
		reportButton.setIcon(new ImageIcon(reportImg));
		reportButton.setText("Report");
		reportButton.addMouseListener(sideButtonListener);
		reportButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// add made by text
		JLabel teamName = new JLabel("Made By: " + Constants.TEAM_NAME);
		teamName.setFont(Constants.SMALL_ITALIC_FONT);

		// add items to side bar with proper spacing
		this.add(reportButton);
		this.add(Box.createVerticalStrut(Constants.SIDE_BAR_SPACING));
		this.add(instructionsButton);
		this.add(Box.createVerticalStrut(Constants.SIDE_BAR_SPACING));
		this.add(aboutButton);

		this.add(Box.createVerticalGlue());
		this.add(teamName);

	}
}

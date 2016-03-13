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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

/**
 * The side bar of the GUI.
 * 
 * @author matt
 * 
 */
@SuppressWarnings("serial")
public class SideBar extends JPanel {
	public JLabel aboutButton, instructionsButton, reportButton, profileButton;
	private Component verticalStrut;
	private Component verticalStrut_1;
	private JLabel lblSealTeam;
	

	/**
	 * Creates a new side bar object, takes in a listener class so that this
	 * object can communicate with the main class
	 * 
	 * @param sideButtonListener
	 */
	public SideBar(MouseListener sideButtonListener) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBackground(Constants.MENU_BACKGROUND_COLOR);
		// create 10 pixels of padding
		setBorder(BorderFactory.createLineBorder(Constants.MENU_BACKGROUND_COLOR, 10));

		// about button
		aboutButton = new JLabel();
		aboutButton.setRequestFocusEnabled(false);
		aboutButton.setFont(Constants.MAIN_FONT);
		Image aboutImg = new ImageIcon(Constants.ABOUT_ICON_NAME).getImage();
		aboutImg = aboutImg.getScaledInstance(Constants.MAIN_FONT.getSize(),
				Constants.MAIN_FONT.getSize(), Image.SCALE_SMOOTH);
		aboutButton.setIcon(new ImageIcon(aboutImg));
		aboutButton.setText("About");
		aboutButton.addMouseListener(sideButtonListener);
		aboutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Image instructionsImg = new ImageIcon(Constants.INSTRUCTIONS_ICON_NAME)
				.getImage();
		instructionsImg = instructionsImg.getScaledInstance(
				Constants.MAIN_FONT.getSize(), Constants.MAIN_FONT.getSize(),
				Image.SCALE_SMOOTH);
		Image reportImg = new ImageIcon(Constants.REPORT_ICON_NAME).getImage();
		reportImg = reportImg.getScaledInstance(Constants.MAIN_FONT.getSize(),
				Constants.MAIN_FONT.getSize(), Image.SCALE_SMOOTH);

		// add made by text
		JLabel teamName = new JLabel("Made By: ");
		teamName.setPreferredSize(new Dimension(96, 24));
		teamName.setFont(Constants.SMALL_ITALIC_FONT);
		
		// profile button
		profileButton = new JLabel();
		profileButton.setFont(Constants.MAIN_FONT);
		profileButton.setIcon(new ImageIcon(reportImg));
		profileButton.setText("Profile");
		profileButton.addMouseListener(sideButtonListener);
		profileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
						//add item to side bar
						add(profileButton);
		add(Box.createVerticalStrut(Constants.SIDE_BAR_SPACING));
		
				// report button
				reportButton = new JLabel();
				reportButton.setFont(Constants.MAIN_FONT);
				reportButton.setIcon(new ImageIcon(reportImg));
				reportButton.setText("Report");
				reportButton.addMouseListener(sideButtonListener);
				reportButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				
						// add items to side bar with proper spacing
						add(reportButton);
		add(Box.createVerticalStrut(Constants.SIDE_BAR_SPACING));
		
				// instructions button
				instructionsButton = new JLabel();
				instructionsButton.setFont(Constants.MAIN_FONT);
				instructionsButton.setIcon(new ImageIcon(instructionsImg));
				instructionsButton.setText("Instructions");
				instructionsButton.addMouseListener(sideButtonListener);
				instructionsButton.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));
				add(instructionsButton);
		
		verticalStrut = Box.createVerticalStrut(20);
		add(verticalStrut);
		add(aboutButton);
		
		verticalStrut_1 = Box.createVerticalStrut(325);
		add(verticalStrut_1);
		add(teamName);
		
		lblSealTeam = new JLabel(Constants.TEAM_NAME);
		lblSealTeam.setFont(new Font("Arial", Font.ITALIC, 13));
		add(lblSealTeam);
	}
}

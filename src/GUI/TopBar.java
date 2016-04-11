package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.MainGUI.ButtonListener;
import Main.Constants;

import javax.swing.JRadioButton;

import Main.FileState;

import java.awt.Cursor;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

/**
 * The top bar of the GUI.
 * 
 * @author matt
 * 
 */
@SuppressWarnings("serial")
public class TopBar extends JPanel {
	
	public static FileState state = FileState.COURSE;
	
	public JLabel fileLabel, errorLabel;
	public JButton addButton, submitButton, deleteButton;
	

	/**
	 * Creates a new top bar object, takes in a listener class so that this
	 * object can communicate with the main class
	 * 
	 * @param buttonListener
	 */
	public TopBar(ButtonListener buttonListener) {
		setPreferredSize(new Dimension(795, 55));
		setBackground(Constants.MENU_BACKGROUND_COLOR);
		setBorder(BorderFactory.createMatteBorder(0, 0,
				Constants.TOP_ROW_BORDER_THICKNESS, 0,
				Constants.TOP_ROW_BORDER_COLOR));

		// set up Icon and Program Title in top left corner
		JLabel title = new JLabel();
		title.setBounds(0, 10, 400, 32);
		Image img = new ImageIcon(Constants.MIAMI_ICON_NAME).getImage();
		img = img.getScaledInstance(Constants.TOP_ROW_HEIGHT,
				Constants.TOP_ROW_HEIGHT, Image.SCALE_SMOOTH);
		setLayout(null);
		title.setIcon(new ImageIcon(img));
		title.setFont(Constants.TITLE_FONT);
		title.setText(Constants.TITLE);
		add(title);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		buttonsPanel.setBounds(400, 5, 380, 32);
		buttonsPanel.setBackground(Constants.MENU_BACKGROUND_COLOR);
		add(buttonsPanel);
		buttonsPanel.setLayout(null);
		
		addButton = new JButton("Add File");
		addButton.setPreferredSize(new Dimension(110, 29));
		addButton.setBounds(0, 0, 117, 29);
		addButton.setActionCommand("addButton");
		buttonsPanel.add(addButton);
		addButton.addActionListener(buttonListener);
		
		deleteButton = new JButton("Remove");
		deleteButton.setBounds(130, 0, 117, 29);
		buttonsPanel.add(deleteButton);
		deleteButton.setActionCommand("deleteButton");
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(buttonListener);
		
		submitButton = new JButton("Submit");
		submitButton.setBounds(260, 0, 117, 29);
		buttonsPanel.add(submitButton);
		submitButton.setActionCommand("submitButton");
		submitButton.setEnabled(false);
		submitButton.addActionListener(buttonListener);
		
		JPanel labelPanel = new JPanel();
		labelPanel.setBounds(400, 33, 380, 16);
		labelPanel.setBackground(Constants.MENU_BACKGROUND_COLOR);
		labelPanel.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		add(labelPanel);
		labelPanel.setLayout(null);
		
		fileLabel = new JLabel();
		fileLabel.setBounds(27, 0, 406, 17);
		labelPanel.add(fileLabel);
		fileLabel.setFont(Constants.SMALL_FONT);
		
		errorLabel = new JLabel();
		errorLabel.setBounds(27, 0, 406, 17);
		labelPanel.add(errorLabel);
		errorLabel.setFont(Constants.SMALL_FONT);
		errorLabel.setForeground(Color.RED);
		errorLabel.setBackground(Constants.MENU_BACKGROUND_COLOR);

	}
}

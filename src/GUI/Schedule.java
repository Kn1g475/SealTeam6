package GUI;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.Constants;

import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;





@SuppressWarnings("serial")
public class Schedule extends JPanel{

	JList coursesTakenList, coursesDesiredList;
	JLabel coursesTakenLabel, coursesDesiredLabel;
	JRadioButton takenRadioButton, desiredRadioButton;
	JButton addButton, removeButton, checkButton, saveButton;




	public Schedule() {
		setBorder(new CompoundBorder());
		setBackground(Constants.CONTENT_BACKGROUND_COLOR);
		setLayout(null);

		coursesTakenList = new JList();
		coursesTakenList.setBorder(new LineBorder(new Color(0, 0, 0)));
		coursesTakenList.setBounds(20, 51, 602, 106);
		add(coursesTakenList);

		coursesTakenLabel = new JLabel("Courses Taken");
		coursesTakenLabel.setBounds(20, 23, 104, 16);
		add(coursesTakenLabel);

		coursesDesiredList = new JList();
		coursesDesiredList.setBorder(new LineBorder(new Color(0, 0, 0)));
		coursesDesiredList.setBounds(20, 202, 601, 106);
		add(coursesDesiredList);

		coursesDesiredLabel = new JLabel("Courses Desired");
		coursesDesiredLabel.setBounds(20, 186, 104, 16);
		add(coursesDesiredLabel);

		takenRadioButton = new JRadioButton("Taken");
		takenRadioButton.setBounds(20, 350, 141, 23);
		takenRadioButton.setSelected(true);
		takenRadioButton.addActionListener(new RadioButton());
		add(takenRadioButton);


		desiredRadioButton = new JRadioButton("Desired");
		desiredRadioButton.setBounds(20, 409, 141, 23);
		desiredRadioButton.addActionListener(new RadioButton());
		add(desiredRadioButton);


		addButton = new JButton("Add");
		addButton.setBounds(249, 349, 117, 29);
		add(addButton);

		removeButton = new JButton("Remove");
		removeButton.setBounds(249, 408, 117, 29);
		add(removeButton);

		checkButton = new JButton("Check");
		checkButton.setBounds(463, 350, 117, 38);
		add(checkButton);

		saveButton = new JButton("Save");
		saveButton.setBounds(463, 408, 117, 29);
		add(saveButton);
	}
	private class RadioButton implements ActionListener {

		
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equalsIgnoreCase("Taken")) {
				takenRadioButton.setSelected(true);
				desiredRadioButton.setSelected(false);
			}
			if(e.getActionCommand().equalsIgnoreCase("Desired")) {
				desiredRadioButton.setSelected(true);
				takenRadioButton.setSelected(false);
			}
		}
	}
}

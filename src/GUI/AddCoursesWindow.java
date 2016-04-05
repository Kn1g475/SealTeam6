package GUI;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Rectangle;

import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.List;
import java.awt.Scrollbar;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Point;

import javax.swing.JList;
import javax.swing.border.LineBorder;

public class AddCoursesWindow extends JFrame {

	Button acceptButton, cancelButton;
	Label courseLabel;
	JList courseToAdd;

	public AddCoursesWindow() {
		setLocation(new Point(400, 300));
		setTitle("Taken Courses");
		setResizable(false);
		getContentPane().setLayout(null);
		setSize(500,275);
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(10, 6, 102, 27);
		getContentPane().add(comboBox);


		acceptButton = new Button("Accept");
		acceptButton.setBounds(10, 215, 84, 28);
		getContentPane().add(acceptButton);

		courseToAdd = new JList();
		courseToAdd.setBorder(new LineBorder(new Color(0, 0, 0)));
		courseToAdd.setBounds(16, 45, 465, 151);
		getContentPane().add(courseToAdd);

		cancelButton = new Button("Cancel");
		cancelButton.setBounds(417, 215, 77, 28);
		getContentPane().add(cancelButton);

		courseLabel = new Label("Course Name");
		courseLabel.setBounds(118, 10, 117, 17);
		getContentPane().add(courseLabel);
		final JButton button = new JButton();




		button.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(evt.getActionCommand().equalsIgnoreCase("cancelWindowButton")){


				}
				if(evt.getActionCommand().equalsIgnoreCase("acceptWindowButton")){

					
				}

			}

		});
	}
}

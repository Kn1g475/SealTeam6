package GUI;

import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.Button;
import java.awt.Label;
import java.awt.Point;

import javax.swing.JList;
import javax.swing.border.LineBorder;

import CourseData.Course;

public class AddCoursesWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Button acceptButton, cancelButton;
	Label courseLabel;
	JList<Course> courseToAdd;
	DefaultListModel<Course> courseList;
	AddCoursesWindow us;
	public Course selected;
	public AddCoursesWindow(JFrame parent,ModalityType modal, List<Course> courses) {
		super(parent, modal);
		us = this;
		setLocation(new Point(400, 300));
		setTitle("Taken Courses");
		setResizable(false);
		getContentPane().setLayout(null);
		setSize(500,275);
		
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setBounds(10, 6, 102, 27);
		getContentPane().add(comboBox);

		acceptButton = new Button("Accept");
		acceptButton.setBounds(10, 215, 84, 28);
        acceptButton.setActionCommand("acceptWindowButton");
        acceptButton.addActionListener(new ButtonEvent());
		getContentPane().add(acceptButton);
		
		courseList = new DefaultListModel<>();
		for (Course course : courses)
			courseList.addElement(course);
		courseToAdd = new JList<>(courseList);
		JScrollPane courseBox = new JScrollPane(courseToAdd);
		courseBox.setBorder(new LineBorder(new Color(0, 0, 0)));
		courseBox.setBounds(16, 45, 465, 151);
		getContentPane().add(courseBox);

		cancelButton = new Button("Cancel");
		cancelButton.setBounds(417, 215, 77, 28);
        cancelButton.setActionCommand("cancelWindowButton");
		cancelButton.addActionListener(new ButtonEvent());
		getContentPane().add(cancelButton);

		courseLabel = new Label("Course Name");
		courseLabel.setBounds(118, 10, 117, 17);
		getContentPane().add(courseLabel);
		setVisible(true);
	}
	private void close() {
		setVisible(false);
		dispose();
	}
	private class ButtonEvent implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.getActionCommand().equalsIgnoreCase("cancelWindowButton")){
				us.close();
			}
			if(arg0.getActionCommand().equalsIgnoreCase("acceptWindowButton")){
				selected = courseToAdd.getSelectedValue();
				us.close();
			}
		}
	}
}

package GUI;


import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.Constants;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import CourseData.Course;
import CourseData.Class;
import CourseData.Profile;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.List;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Schedule extends JPanel{
	JFrame us;
	Profile profile;
	private DefaultListModel<Class> classList;
	private DefaultListModel<Course> courseList;
	JList<Course> coursesTakenList;
	JList<Class> coursesDesiredList;
	JLabel coursesTakenLabel, coursesDesiredLabel;
	JRadioButton takenRadioButton, desiredRadioButton;
	JButton addButton, removeButton, checkButton, saveButton;
	JComboBox<String> courseAddWindow, campusBox;
	List listAddWindow;
	Scrollbar scrollbarAddWindow;
	Button acceptWindowButton, cancelWindowButton;
	
	public java.util.List<Course> courses;
	public java.util.List<Class> classes;

	public Schedule(Profile prof) {
		profile = prof;
		us = (JFrame) this.getParent();
		courses = new ArrayList<>();
		classes = new ArrayList<>();
		
		setBorder(new CompoundBorder());
		setBackground(Constants.CONTENT_BACKGROUND_COLOR);
		setLayout(null);
		
		courseList = new DefaultListModel<>();
		coursesTakenList = new JList<>(courseList);
		JScrollPane coursePane = new JScrollPane(coursesTakenList);
		coursePane.setBorder(new LineBorder(new Color(0, 0, 0)));
		coursePane.setBounds(20, 51, 602, 106);
		add(coursePane);

		coursesTakenLabel = new JLabel("Courses Taken");
		coursesTakenLabel.setBounds(20, 23, 104, 16);
		add(coursesTakenLabel);
		classList = new DefaultListModel<>();
		coursesDesiredList = new JList<>(classList);
		JScrollPane classesPane = new JScrollPane(coursesDesiredList);
		classesPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		classesPane.setBounds(20, 202, 601, 106);
		add(classesPane);

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
		addButton.addActionListener(new ButtonEvent());
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
	
	private class ButtonEvent implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equalsIgnoreCase("Add")){
				if (desiredRadioButton.isSelected()){
					AddClassWindow classWindow = new AddClassWindow(us, ModalityType.APPLICATION_MODAL, classes);
					if (classWindow.selected != null && !profile.getCoursesTaken().contains(classWindow.selected) && !classList.contains(classWindow.selected)) {
						if (!profile.addClass(classWindow.selected))
							JOptionPane.showMessageDialog(us,"Class overlaps with a course that you already want to take","Invalid" ,JOptionPane.ERROR_MESSAGE);
						else {
							classList.addElement(classWindow.selected);
						}
					}
				}
				if(takenRadioButton.isSelected()){
					AddCoursesWindow courseWindow = new AddCoursesWindow(us, ModalityType.APPLICATION_MODAL, courses);
					if (courseWindow.selected != null && !profile.getCoursesTaken().contains(courseWindow.selected)) {
						profile.addCourse(courseWindow.selected);
						courseList.addElement(courseWindow.selected);
					}
				}
			}	
		}
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

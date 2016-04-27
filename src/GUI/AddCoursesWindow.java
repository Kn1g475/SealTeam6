package GUI;

import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.awt.Button;
import java.awt.Label;
import java.awt.Point;

import javax.swing.JList;
import javax.swing.border.LineBorder;

import CourseData.Course;

public class AddCoursesWindow extends JDialog {

	/**
	 * This window will allow the user to add courses to their
	 * 'Taken' list for later checking. Includes prerequisite checking.
	 * 
	 * @author npatterson
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Button acceptButton, cancelButton;
	Label courseLabel;
	JList<Course> courseToAdd;
	ArrayList<Course> classContents;
	DefaultListModel<Course> courseList;
	AddCoursesWindow us;
	JComboBox<String> comboBox;
	JScrollPane courseBox;
	List<Course> courses;
	public Course selected;
	public AddCoursesWindow(JFrame parent,ModalityType modal, List<Course> courses) {
		super(parent, modal);
		us = this;
		this.courses = courses;
		setLocation(new Point(400, 300));
		setTitle("Taken Courses");
		setResizable(false);
		getContentPane().setLayout(null);
		setSize(500,275);


		Collections.sort(courses);
		comboBox = new JComboBox<>();
		comboBox.addItem("Select Class");
		comboBox.setSelectedItem(comboBox.getItemAt(0));
		for(Course course : courses)
			comboBox.addItem(course.getShortName());	

		comboBox.addActionListener(new ButtonEvent());
		comboBox.setActionCommand("comboBox");
		comboBox.setBounds(10, 6, 125, 27);
		getContentPane().add(comboBox);

		acceptButton = new Button("Accept");
		acceptButton.setBounds(10, 215, 84, 28);
		acceptButton.setActionCommand("acceptWindowButton");
		acceptButton.addActionListener(new ButtonEvent());
		getContentPane().add(acceptButton);

		courseList = new DefaultListModel<>();
		for (Course course : courses){
			courseList.addElement(course);
		}

		courseToAdd = new JList<>(courseList);
		courseBox = new JScrollPane(courseToAdd);
		courseBox.setBorder(new LineBorder(new Color(0, 0, 0)));
		courseBox.setBounds(16, 45, 465, 151);
		getContentPane().add(courseBox);

		cancelButton = new Button("Cancel");
		cancelButton.setBounds(417, 215, 77, 28);
		cancelButton.setActionCommand("cancelWindowButton");
		cancelButton.addActionListener(new ButtonEvent());
		getContentPane().add(cancelButton);


		courseLabel = new Label();
		courseLabel.setText("Course Title");
		courseLabel.setBounds(139, 10, 363, 17);
		getContentPane().add(courseLabel);
		setVisible(true);
	}
	private void close() {
		setVisible(false);
		dispose();
	}
	public class ButtonEvent implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.getActionCommand().equalsIgnoreCase("cancelWindowButton")){
				us.close();
			}
			if(arg0.getActionCommand().equalsIgnoreCase("acceptWindowButton")){
				selected = courseToAdd.getSelectedValue();
				us.close();
			}
			if(arg0.getActionCommand().equalsIgnoreCase("comboBox")){
				if(comboBox.getSelectedItem().equals("Select Class")){
					if(courseList.size() == 1)
						courseList.clear();
					courseLabel.setText("Course Title");
					for (Course course : courses){
						courseList.addElement(course);
					}					
				}else{
					int index = courses.indexOf(new Course((String) comboBox.getSelectedItem()));
					courseLabel.setText(courses.get(index).getTitle());
					courseList.clear();
					courseList.addElement(new Course(courses.get(index).getSubject(),courses.get(index).getTitle(),courses.get(index).getCourseNumber()));
				}
			}
		}
	}
}

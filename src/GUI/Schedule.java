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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import CourseData.Course;
import CourseData.Class;
import CourseData.Profile;
import GUI.MainGUI.CheckButton;

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
	JFrame parent;
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

	public Schedule(Profile prof, CheckButton event) {
		profile = prof;
		parent = (JFrame) this.getParent();
		courses = new ArrayList<>();
		classes = new ArrayList<>();

		setBorder(new CompoundBorder());
		setBackground(Constants.CONTENT_BACKGROUND_COLOR);
		setLayout(null);

		courseList = new DefaultListModel<>();
		for (Course c : prof.getCoursesTaken()) 
			courseList.addElement(c);
		coursesTakenList = new JList<>(courseList);
		coursesTakenList.addListSelectionListener(new SelectionEvent());
		JScrollPane coursePane = new JScrollPane(coursesTakenList);
		coursePane.setBorder(new LineBorder(new Color(0, 0, 0)));
		coursePane.setBounds(20, 51, 602, 106);
		add(coursePane);

		coursesTakenLabel = new JLabel("Courses Taken");
		coursesTakenLabel.setBounds(20, 23, 104, 16);
		add(coursesTakenLabel);


		classList = new DefaultListModel<>();
		for (Class c : prof.getSchedule())
			classList.addElement(c);
		coursesDesiredList = new JList<>(classList);
		coursesDesiredList.addListSelectionListener(new SelectionEvent());
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
		removeButton.addActionListener(new ButtonEvent());
		add(removeButton);

		checkButton = new JButton("Check");
		checkButton.setBounds(463, 350, 117, 38);
		checkButton.addActionListener(event);
		add(checkButton);

		saveButton = new JButton("Save");
		saveButton.setBounds(463, 408, 117, 29);
		saveButton.addActionListener(new ButtonEvent());
		add(saveButton);

	}
	private class SelectionEvent implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if (arg0.getSource().equals(coursesDesiredList)) {
				coursesTakenList.clearSelection();
			}
			if (arg0.getSource().equals(coursesTakenList)) {
				coursesDesiredList.clearSelection();
			}
		}

	}
	
	private String errorDump(java.util.List<String> err) {
		StringBuilder sb = new StringBuilder();
		sb.append("Errors: \n");
		for (String error : err) {
			sb.append(String.format("\t%s\n", error.substring(8)));
		}
		return sb.toString();
	}
	
	private class ButtonEvent implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equalsIgnoreCase("Add")){
				if (desiredRadioButton.isSelected()){
					AddClassWindow classWindow = new AddClassWindow(parent, ModalityType.APPLICATION_MODAL, classes);
					
					if (classWindow.selected != null && !profile.getCoursesTaken().contains(classWindow.selected) && !classList.contains(classWindow.selected)) {
						
						java.util.List<String> errors = profile.addClasses(classWindow.selected);
						if (!errors.isEmpty())
							JOptionPane.showMessageDialog(parent,errorDump(errors),"Invalid" ,JOptionPane.ERROR_MESSAGE);
						classList.clear();
						for (Class c : profile.getSchedule())
							classList.addElement(c);
					}
				}
				if(takenRadioButton.isSelected()){
					AddCoursesWindow courseWindow = new AddCoursesWindow(parent, ModalityType.APPLICATION_MODAL, courses);
					if (courseWindow.selected != null && !profile.getCoursesTaken().contains(courseWindow.selected)) {
						profile.addCourse(courseWindow.selected);
						for (Course c : courseWindow.selected)
							courseList.addElement(c);
					}
				}
			}
			if (e.getActionCommand().equalsIgnoreCase("Save")) {
				profile.saveProfile();
			}
			if (e.getActionCommand().equalsIgnoreCase("Remove")) {
				profile.removeClass(coursesDesiredList.getSelectedValuesList());
				for (Class c : coursesDesiredList.getSelectedValuesList())
						classList.removeElement(c);
				profile.removeCourse(coursesTakenList.getSelectedValuesList());
				for (Course c : coursesTakenList.getSelectedValuesList())
					courseList.removeElement(c);
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

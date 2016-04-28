package GUI;

import CourseData.Class;
import CourseData.Course;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Button;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class AddClassWindow extends JDialog{
	/**
	 *
	 * This window will add classes to the 'Desired'
	 * list and be used for building the schedule for the final Report.  
	 *
	 * @author npatterson
	 */
	
	AddClassWindow us;
	JList<Class> courseToAdd;
	DefaultListModel<Class> list;
	Button acceptButton, cancelButton;
	Label courseLabel;
	JComboBox<String> comboBox;
	List<Class> selected;
	List<Class> classes;
	
	public AddClassWindow(JFrame parent,ModalityType modal ,List<Class> classes) {
		super(parent, modal);
		us = this;
		this.classes = classes;
		setTitle("Desired Class");
		setLocation(new Point(400, 300));
		setResizable(false);
		getContentPane().setLayout(null);
		setSize(600,275);

		Collections.sort(classes);
		DefaultComboBoxModel<String> model  = new DefaultComboBoxModel<>();
		comboBox = new JComboBox<>(model);
		comboBox.addItem("Select Class");
		comboBox.setSelectedItem(comboBox.getItemAt(0));
		List<Course> tempList = new ArrayList<>();
		for (Class clas : classes) {
			if (!tempList.contains(clas.getClass()))
				tempList.add(clas.getCourse());
		}
		Collections.sort(tempList);
		for (Course c: tempList){
			if (model.getIndexOf(c.getShortName()) == -1) 
				model.addElement(c.getShortName());
		}
		comboBox.addActionListener(new AddEvent());
		comboBox.setActionCommand("comboBox");
		comboBox.setBounds(10, 6, 125, 27);
		getContentPane().add(comboBox);



		acceptButton = new Button("Accept");
		acceptButton.setBounds(10, 215, 84, 28);
		acceptButton.setActionCommand("acceptWindowButton");
		acceptButton.addActionListener(new AddEvent());
		getContentPane().add(acceptButton);

		Collections.sort(classes);
		list = new DefaultListModel<>();
		for (Class clas : classes){
			list.addElement(clas);
		}

		courseToAdd = new JList<>(list);
		JScrollPane pane = new JScrollPane(courseToAdd);
		pane.setBorder(new LineBorder(new Color(0, 0, 0)));
		pane.setBounds(16, 45, 565, 151);
		getContentPane().add(pane);

		cancelButton = new Button("Cancel");
		cancelButton.setBounds(517, 215, 77, 28);
		cancelButton.setActionCommand("cancelWindowButton");
		cancelButton.addActionListener(new AddEvent());
		getContentPane().add(cancelButton);

		courseLabel = new Label("Class Name");
		courseLabel.setBounds(141, 10, 363, 17);
		getContentPane().add(courseLabel);

		setVisible(true);
	}
	private void close() {
		setVisible(false);
		dispose();
	}
	
	private List<Integer> indexesOfClass(List<Class> list, Course c) {
		List<Integer> index = new ArrayList<>();
		for (int i = 0; i < list.size(); i++)
			if(list.get(i).getCourse().equals(c))
				index.add(i);
		return index;
	}
	
	private class AddEvent implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equalsIgnoreCase("cancelWindowButton")){
				us.close();
			}
			if (arg0.getActionCommand().equalsIgnoreCase("acceptWindowButton")){
				selected = courseToAdd.getSelectedValuesList();
				us.close();
			}
			if(arg0.getActionCommand().equalsIgnoreCase("comboBox")){
				if(comboBox.getSelectedItem().equals("Select Class")){
						list.clear();
						
					for(Class clas : classes)
						list.addElement(clas);
				}else{

					List<Integer> index = indexesOfClass(classes, new Course((String) comboBox.getSelectedItem()));
					courseLabel.setText(classes.get(index.get(0)).getCourse().getTitle());
					list.clear();
					for (int i = 0; i < index.size(); i++)
						list.addElement(classes.get(index.get(i)));
				}

			}
		}
	}
}

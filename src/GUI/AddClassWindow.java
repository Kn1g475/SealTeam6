package GUI;

import CourseData.Class;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class AddClassWindow extends JDialog{
    AddClassWindow us;
	JList<Class> courseToAdd;
	DefaultListModel<Class> list;
	Button acceptButton, cancelButton;
	Label courseLabel;
	JComboBox<String> comboBox;
	Class selected;
	public AddClassWindow(JFrame parent,ModalityType modal ,List<Class> classes) {
		super(parent, modal);
		us = this;
		setTitle("Desired Class");
		setLocation(new Point(400, 300));
        setResizable(false);
        getContentPane().setLayout(null);
        setSize(600,275);
        
        Collections.sort(classes);
        comboBox = new JComboBox<>();
        comboBox.addItem("Select Class");
        comboBox.setSelectedItem(comboBox.getItemAt(0));
        for (Class clas : classes)
        	comboBox.addItem(clas.getCourse().getShortName());
        comboBox.setBounds(10, 6, 125, 27);
        getContentPane().add(comboBox);
        
   
        
        acceptButton = new Button("Accept");
        acceptButton.setBounds(10, 215, 84, 28);
        acceptButton.setActionCommand("acceptWindowButton");
        acceptButton.addActionListener(new ButtonEvent());
        getContentPane().add(acceptButton);
        
        Collections.sort(classes);
        list = new DefaultListModel<>();
        for (Class clas : classes)
        	list.addElement(clas);
        
        
        courseToAdd = new JList<>(list);
        JScrollPane pane = new JScrollPane(courseToAdd);
        pane.setBorder(new LineBorder(new Color(0, 0, 0)));
        pane.setBounds(16, 45, 565, 151);
		getContentPane().add(pane);
        
        cancelButton = new Button("Cancel");
        cancelButton.setBounds(517, 215, 77, 28);
        cancelButton.setActionCommand("cancelWindowButton");
        cancelButton.addActionListener(new ButtonEvent());
        getContentPane().add(cancelButton);
        
        courseLabel = new Label("Class Name");
        courseLabel.setBounds(141, 10, 117, 17);
        getContentPane().add(courseLabel);
        
        setVisible(true);
    }
	private void close() {
		setVisible(false);
		dispose();
	}
	private class ButtonEvent implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equalsIgnoreCase("cancelWindowButton")){
        		us.close();
			}
			if (arg0.getActionCommand().equalsIgnoreCase("acceptWindowButton")){
				selected = courseToAdd.getSelectedValue();
				us.close();
			}
		}
	}
}
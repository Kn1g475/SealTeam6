package GUI;

import CourseData.Class;

import javax.swing.DefaultListModel;
import javax.swing.JButton;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.Color;
import java.awt.Button;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JList;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class AddClassWindow extends JDialog{
    JDialog us;
	JList<Class> courseToAdd;
	DefaultListModel<Class> list;
	Button acceptButton, cancelButton;
	Label courseLabel;
	JComboBox<String> comboBox;
	public Class selected;
	
	public AddClassWindow(List<Class> classes) {
		us = this;
		setTitle("Desired Class");
		setLocation(new Point(400, 300));
        setResizable(false);
        getContentPane().setLayout(null);
        setSize(500,275);
        
        
        comboBox = new JComboBox<>();
        comboBox.setBounds(10, 6, 102, 27);
        getContentPane().add(comboBox);
        
        acceptButton = new Button("Accept");
        acceptButton.setBounds(10, 215, 84, 28);
        acceptButton.setActionCommand("acceptWindowButton");
        acceptButton.addActionListener(new ButtonEvent());
        getContentPane().add(acceptButton);
        
        list = new DefaultListModel<>();
        for (Class clas : classes)
        	list.addElement(clas);
        courseToAdd = new JList<>(list);
        courseToAdd.setBorder(new LineBorder(new Color(0, 0, 0)));
        courseToAdd.setBounds(16, 45, 465, 151);
        
		getContentPane().add(courseToAdd);
        
        cancelButton = new Button("Cancel");
        cancelButton.setBounds(417, 215, 77, 28);
        cancelButton.setActionCommand("cancelWindowButton");
        cancelButton.addActionListener(new ButtonEvent());
        getContentPane().add(cancelButton);
        
        courseLabel = new Label("Class Name");
        courseLabel.setBounds(118, 10, 117, 17);
        getContentPane().add(courseLabel);
        
        
        setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
	private class ButtonEvent implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equalsIgnoreCase("cancelWindowButton")){
        		us.setVisible(false);
				us.dispose();
			}
			if (arg0.getActionCommand().equalsIgnoreCase("acceptWindowButton")){
				selected = courseToAdd.getSelectedValue();
				us.setVisible(false);
				us.dispose();
			}
			
		}
		
	}
}
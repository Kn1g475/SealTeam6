package GUI;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Rectangle;
import javax.swing.JComboBox;
import java.awt.List;
import java.awt.Scrollbar;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Point;

public class AddButtonWindow {
	public final static JFrame PARENT = new JFrame();
    public AddButtonWindow() {
        
        //PARENT.getContentPane().setPreferredSize(new Dimension(300, 100));
      //  PARENT.setLocation(new Point(100, 100));
        PARENT.setResizable(false);
        PARENT.getContentPane().setLayout(null);
        
        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(30, 6, 102, 27);
        PARENT.getContentPane().add(comboBox);
        
        JComboBox comboBox_1 = new JComboBox();
        comboBox_1.setBounds(392, 6, 102, 27);
        comboBox_1.addItem("Select");
        comboBox_1.addItem("Oxford");
        comboBox_1.addItem("Hamilton");
        comboBox_1.addItem("MiddleTown");
        comboBox_1.addItem("Luxembourg");
        PARENT.getContentPane().add(comboBox_1);
        
        List list = new List();
        list.setBounds(91, 39, 326, 170);
        list.add("CRN CourseName Professor Time");
        PARENT.getContentPane().add(list);
        
        Scrollbar scrollbar = new Scrollbar();
        scrollbar.setBounds(402, 39, 15, 170);
        PARENT.getContentPane().add(scrollbar);
        
        Button button_1 = new Button("Accept");
        button_1.setBounds(6, 215, 84, 28);
        PARENT.getContentPane().add(button_1);
        
        Button button_2 = new Button("Cancel");
        button_2.setBounds(417, 215, 77, 28);
        PARENT.getContentPane().add(button_2);
        
        Label courseLabel = new Label("New label");
        courseLabel.setBounds(222, 6, 117, 17);
        PARENT.getContentPane().add(courseLabel);
        final JButton button = new JButton();

       
       

        button.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	PARENT.getContentPane().setPreferredSize(new Dimension(300, 100));
                PARENT.getContentPane().add(button);
                PARENT.pack();
                PARENT.setVisible(true);
               
            }
         
        });
    }
}
package GUI;

import java.awt.Color;
import java.awt.Component;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import CourseData.Category;
import CourseData.Class;
import Main.Constants;

@SuppressWarnings("serial")
public class ConflictTable extends JTable{
	List<Category> cats;

	public ConflictTable(TableModel arg0, List<Category> cats) {
		super(arg0);
		this.cats = cats;
		
		int time = 800;
		for (int i = 1; i < getRowCount(); i++) {
			getModel().setValueAt(Constants.timeToString(time), i, 0);
			switch (i) {
			case 1: case 3: case 5: case 7: case 9: case 11:
				time += 200;
				break;
			case 2: case 6: case 10: 
				time += 15;
				break;
			case 4: case 8:
				time +=30;
				break;
			}
			if (time % 100 == 60)
				time += 40;
			if (time % 100 == 90)
				time += 10;
		}
		
		for (Category cat : cats) {

			Collections.sort(cat.classesInThisCategory);
			String finalsInfo = "";
			for (CourseData.Class c : cat.classesInThisCategory) 
				finalsInfo += c.CoursenSection() + "\n"; 
			getModel().setValueAt(finalsInfo,getRow(cat),getColumnInt(cat.finalDay.charAt(0)));
		}
		setDefaultRenderer(getColumnClass(0), new WeekRenderer());
		revalidate();

	}
	public boolean isCellEditable(int rowIndex, int vColIndex) {
		return false;
	}
	public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int vColIndex) {
        Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
        if (c instanceof JComponent) {
          JComponent jc = (JComponent) c;
          jc.setToolTipText(null);
        }
        return c;
      }
	private class WeekRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			setForeground(Color.black);
			setBackground(Color.WHITE);
			for (Category cat : cats) {
				for (Class c : cat.classesInThisCategory){
					if (c.hasConflict) {
						int rw = getRow(cat);
						int clmn = getColumnInt(cat.finalDay.charAt(0));
						if (row == rw && clmn == column)
							setBackground(Color.RED);
					}
				}
			}
			return this; 
		}
	}
	
	
	private int getColumnInt(char day) {
		switch (day) {
		case 'M': return 1;
		case 'T': return 2;
		case 'W': return 3;
		case 'R': return 4;
		case 'F': return 5;
		default: return 0;
		}
	}

	private int getRow(Category cat) {
		switch (cat.finalTime) {
		case 800: return 1;
		case 1015: return 3;
		case 1245: return 5;
		case 1500: return 7;
		case 1730: return 9;
		case 1945: return 11;
		default: return 0;
		}
	}
}

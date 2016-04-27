package GUI;

import java.awt.Color;
import java.awt.Component;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import CourseData.Class;
import CourseData.Course;
import CourseData.TimeInterval;
import Main.Constants;

@SuppressWarnings("serial")
public class WeekTable extends JTable {

	List<Class> schedule;

	public WeekTable(TableModel arg0, List<Class> schedule) {
		super(arg0);
		this.schedule = schedule;
		int time = 800;
		for (int i = 1; i < getRowCount(); i++) {
			getModel().setValueAt(Constants.timeToString(time), i, 0);
			time += (i % 2 == 1) ? 30 : 70; 
		}

		for (CourseData.Class c : schedule) {
			for (Entry<Character, TimeInterval> entry : c.times.entrySet()) {
				int row = getRow(entry.getValue().getStartTime());
				int column = getColumnInt(entry.getKey());
				getModel().setValueAt(c.CoursenSection(), row, column);
			}
		}
		setDefaultRenderer(getColumnClass(2), new WeekRenderer());

	}
	public boolean isCellEditable(int rowIndex, int vColIndex) {
		return false;
	}
	public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int vColIndex) {
        Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
        if (c instanceof JComponent) {
          JComponent jc = (JComponent) c;
          jc.setToolTipText(null);
          for (Class cs : schedule) {
        	  for (Entry<Character, TimeInterval> entry : cs.times.entrySet()) {
        		  int row = getRow(entry.getValue().getStartTime());	
        		  int column = getColumnInt(entry.getKey());
        		  if (rowIndex == row && vColIndex == column)
        			  jc.setToolTipText(cs.toString());
        	  }
          }
        }
        return c;
      }
	private class WeekRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			setForeground(Color.black);
			setBackground(Color.WHITE);
			for (Class c : schedule) {
				for (Entry<Character, TimeInterval> entry : c.times.entrySet()) {
					int lastRow = getLastRow(entry.getValue().getEndTime());
					int startRow = getRow(entry.getValue().getStartTime());
					int classColumn = getColumnInt(entry.getKey());
					if (startRow <= row && row <= lastRow && column == classColumn)
						setBackground(Color.CYAN);
				}
			}
			return this; 
		}
	}

	private int getLastRow (int time) {
		int tempTime = time % 100;
		time -= tempTime;
		if (tempTime > 0 && tempTime < 30)
			time += 30;
		else if (tempTime > 30 && tempTime < 60)
			time += 100;
		else 
			time += tempTime;
		return getRow(time);
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
	private int getRow(int time) {
		switch (time) {
		case 800: return 1;
		case 830: return 2;
		case 900: return 3;
		case 930: return 4;
		case 1000: return 5;
		case 1030: return 6;
		case 1100: return 7;
		case 1130: return 8;
		case 1200: return 9;
		case 1230: return 10;
		case 1300: return 11;
		case 1330: return 12;
		case 1400: return 13;
		case 1430: return 14;
		case 1500: return 15;
		case 1530: return 16;
		case 1600: return 17;
		case 1630: return 18;
		case 1700: return 19;
		case 1730: return 20;
		case 1800: return 21;
		case 1830: return 22;
		case 1900: return 23;
		case 1930: return 24;
		case 2000: return 25;
		case 2030: return 26;
		case 2100: return 27;
		default: return 0;
		}
	}
}

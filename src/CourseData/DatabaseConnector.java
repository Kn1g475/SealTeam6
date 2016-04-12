package CourseData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Main.Constants;

public class DatabaseConnector {
	private static final String USER_NAME = "levysj-r";
	private static final String PASSWORD = "q8mYPAyUAHeeByQ4";
	private static final String DATABASE = "jdbc:mysql://134.53.148.193/levysj";
	
	private Connection connector;
	private Statement statement;
	private ResultSet resultSet;
	public DatabaseConnector() throws SQLException{
		connector = DriverManager.getConnection(DATABASE,USER_NAME,PASSWORD);
		statement = connector.createStatement();
	}
	
	public List<Requirement> getRequrements(String SQLStatement) throws SQLException {
		resultSet = statement.executeQuery(SQLStatement);
		String[] columnNames = Constants.COLUMNS_OF_REQUIREMENTS.split(",");
		String[] rowArgs = new String[columnNames.length];
		List<Requirement> requirements = new ArrayList<>();
		ReadRow: while (resultSet.next()) {
			for (int i = 0; i < columnNames.length; i++) {
				rowArgs[i] = resultSet.getString(columnNames[i]);
			}
			if (!rowArgs[6].matches("\\d+")) {
				System.out.printf("Warning: unable to parse an Hours for a line, skipped: %s\n", dump(rowArgs));
				continue ReadRow;
			}
			
			requirements.add(new Requirement(rowArgs));
		}
		resultSet.close();
		return requirements;
	}
	
	public List<Class> getClasses(String SQLStatement) throws SQLException{
		resultSet = statement.executeQuery(SQLStatement);
		String[] columnNames = Constants.COLUMNS_OF_DATABASE.split(",");
		String[] rowArgs = new String[columnNames.length];
		List<Class> classes = new ArrayList<>();
		List<Class> labs = new ArrayList<>();
		ReadRow: while (resultSet.next()) {
			for (int i = 0; i < columnNames.length; i++) {
				rowArgs[i] = resultSet.getString(columnNames[i]);
			}
			// for each argument, read in data
			for (int i = 0; i < rowArgs.length; i++) {
				// skip the line if any element is blank
				if (rowArgs[i].equals("")) {
					System.out.printf("Warning: a line in the file had blank elements, skipped: %s\n",dump(rowArgs));
					continue ReadRow;
				}
				// skip the line is unable to parse numerical values in line
				if ((i == 6 || i == 7) && !rowArgs[i].matches("\\d+")) {
					System.out.printf("Warning: unable to parse a start/end time for a line, skipped: %s\n", dump(rowArgs));
					continue ReadRow;
				}
			}
			// Note: meeting days is not part of checking equality
			Class tempClass = new Class(rowArgs);
			if(rowArgs[10].equals("2")){
				labs.add(tempClass);
				System.out.printf("Note: Lab detected: %s\n", dump(rowArgs));
			} else {
				classes.add(tempClass);
			}
		}
		//Adds labs to their respective classes.
		for(Class lab : labs) {
			classes.get(classes.indexOf(lab)).addLab(lab);
		}
		resultSet.close();
		return classes;
	}
	
	public void close() throws SQLException{
		statement.close();
		connector.close();
	}

	private String dump(String[] array) {
		StringBuilder br = new StringBuilder();
		for(String ele : array) {
			br.append(ele + '\t');
		}
		return br.toString();
		
	}
}

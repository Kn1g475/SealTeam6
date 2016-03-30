package CourseData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Exceptions.InvalidClassException;
import Main.Constants;

public class DatabaseConnector {
	private final String USER_NAME = "levysj-r";
	private final String PASSWORD = "q8mYPAyUAHeeByQ4";
	private final String DATABASE = "jdbc:mysql://134.53.148.193/levysj";
	
	private Connection connector;
	private Statement statement;
	private ResultSet resultSet;
	
	public DatabaseConnector(String sqlStatement) throws SQLException{
		connector = DriverManager.getConnection("jdbc:mysql://134.53.148.193/levysj","levysj-r","q8mYPAyUAHeeByQ4");
		statement = connector.createStatement();
		resultSet = statement.executeQuery(sqlStatement);
	}
	
	public List<Requirement> getRequrements() throws SQLException {
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
		return requirements;
	}
	
	public List<Class> getClasses() throws SQLException{
		String[] columnNames = Constants.COLUMNS_OF_DATABASE.split(",");
		String[] rowArgs = new String[columnNames.length];
		List<Class> classes = new ArrayList<>();
		
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
			if(classes.contains(tempClass))
				classes.add(tempClass);
			else {
				// if duplicate class has different meeting times, add them
				classes.get(classes.indexOf(tempClass)).addMeetingDays(rowArgs[5]);
				System.out.printf("Note: Duplicate class detected, added meeting days and skipped: %s\n", tempClass);
			}
		}
		return classes;
	}
	
	public void close() throws SQLException{
		resultSet.close();
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

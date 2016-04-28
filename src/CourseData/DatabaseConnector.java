package CourseData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Main.Constants;
/**
 * This Handles all Connections to the database for information Retrieval
 * @author Sam Levy
 */
public class DatabaseConnector {
	private static final String USER_NAME = "levysj-r"; //user name for database
	private static final String PASSWORD = "q8mYPAyUAHeeByQ4"; //password for database
	private static final String DATABASE = "jdbc:mysql://134.53.148.193/levysj"; //database location
	
	private Connection connector;
	private Statement statement;
	private ResultSet resultSet;
	public DatabaseConnector() throws SQLException{
		//open connection to database
		connector = DriverManager.getConnection(DATABASE,USER_NAME,PASSWORD);
		statement = connector.createStatement();
	}
	/**
	 * Accesses the requirements' information from the database and stores that information for later use
	 * @param SQLStatement - What information you want to access from the database in SQL format
	 * @return - A mapping of CSE Course short names to their requirements
	 * @throws SQLException - If any errors trying to connect to the database
	 */
	public Map<String,Requirement> getRequrements(String SQLStatement) throws SQLException {
		//sends the SQL statement to the database and retrieves the result.
		resultSet = statement.executeQuery(SQLStatement);
		
		//prepare to handle and store the information
		String[] columnNames = Constants.COLUMNS_OF_REQUIREMENTS.split(",");
		String[] rowArgs = new String[columnNames.length];
		Map<String, Requirement> requirements = new HashMap<>();
		
		//parse all the information in to the mapping of Course short names to their requirements
		ReadRow: while (resultSet.next()) {
			for (int i = 0; i < columnNames.length; i++) {
				rowArgs[i] = resultSet.getString(columnNames[i]);
			}
			if (!rowArgs[6].matches("\\d+")) {
				System.out.printf("Warning: unable to parse an Hours for a line, skipped: %s\n", dump(rowArgs));
				continue ReadRow;
			}
			Course temp = new Course(rowArgs[0], rowArgs[2], rowArgs[1]);
			//System.out.println(temp);
			requirements.put(temp.getShortName(), new Requirement(rowArgs));
		}
		//Close down and return
		resultSet.close();
		return requirements;
	}
	/**
	 * Accesses the class information from the database and stores it into a list
	 * @param SQLStatement - what information you want to access form the database in SQL format
	 * @return A List of classes
	 * @throws SQLException - If any errors trying to connect to the database
	 */
	public List<Class> getClasses(String SQLStatement) throws SQLException{
		//sends the SQL statement to the database and retrieves the result.
		resultSet = statement.executeQuery(SQLStatement);
		
		//Prepare to handle and store information
		String[] columnNames = Constants.COLUMNS_OF_DATABASE.split(",");
		String[] rowArgs = new String[columnNames.length];
		List<Class> classes = new ArrayList<>();
		List<Class> labs = new ArrayList<>();
		
		//parse all the information into a List of classes
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
			Class tempClass = new Class(rowArgs);
			//Check to see if a class is a lab or not
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
		//Close down and return the List of classes
		resultSet.close();
		return classes;
	}
	/**
	 * Disconnects from the database
	 * @throws SQLException - If any errors trying to disconnect to the database
	 */
	public void close() throws SQLException{
		statement.close();
		connector.close();
	}
	/**
	 * debug String dump for an array of information
	 * @param array
	 * @return
	 */
	private String dump(String[] array) {
		StringBuilder br = new StringBuilder();
		for(String ele : array) {
			br.append(ele + '\t');
		}
		return br.toString();
		
	}
}

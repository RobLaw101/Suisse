package Suisse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.apache.commons.math3.util.Pair;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HSQLDatabase implements EventDatabase {
	
	private static final Logger logger = LoggerFactory.getLogger(HSQLDatabase.class);
	private Connection connection;
	private static final String connectionString = "jdbc:hsqldb:file:database/hsqldatabase";
	private static String CREATE_EVENT_TABLE = "CREATE TABLE if not exists EventLog (ID VARCHAR(50) NOT NULL, "
			+  "DURATION INT NOT NULL, FLAG BIT NOT NULL,"
			+  "TYPE VARCHAR(20) DEFAULT NULL NULL, HOST VARCHAR(20) DEFAULT NULL NULL);";
	
	public HSQLDatabase () throws ClassNotFoundException, SQLException {
		Statement createStatement = null;
		try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            connection = DriverManager.getConnection(connectionString, "SA", "");
    		createStatement = connection.createStatement();
    		createStatement.executeUpdate(CREATE_EVENT_TABLE);
        } catch (ClassNotFoundException exception) {
            throw exception;
        } finally {
        	if (connection != null) connection.close();
        	if (createStatement != null) createStatement.close();
        }
	}
	
	@Override
	public void addStandardEventToDatabase(Map <Event, Pair <Long, Boolean>> events) throws Exception {
		PreparedStatement insertStatement = null;
		try {
			connection = DriverManager.getConnection(connectionString, "SA", "");
			String insertSQL = "INSERT INTO EventLog (ID, DURATION, FLAG) VALUES (?, ?, ?)";
			insertStatement = connection.prepareStatement(insertSQL);
			
			for (Event event : events.keySet()) {
				addStandardEvent(insertStatement, event.getId(), events.get(event).getFirst(), 
						events.get(event).getSecond());		
				insertStatement.executeUpdate();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (insertStatement != null) insertStatement.close();
			if (connection != null) connection.close();
		}
	}

	@Override
	public void addApplicationEventToDatabase(Map <ApplicationEvent, Pair <Long, Boolean>> events) throws Exception {
		PreparedStatement insertStatement = null;
		try {
			connection = DriverManager.getConnection(connectionString, "SA", "");
			String insertSQL = "INSERT INTO EventLog VALUES (?, ?, ?, ?, ?)";
			insertStatement = connection.prepareStatement(insertSQL);
			
			for (ApplicationEvent event : events.keySet()) {
				addStandardEvent(insertStatement, event.getId(),events.get(event).getFirst(), 
						events.get(event).getSecond());	
				insertStatement.setString(4, event.getType());
				insertStatement.setString(5, event.getHost());
				insertStatement.executeUpdate();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (insertStatement != null) insertStatement.close();
			if (connection != null) connection.close();
		}		
	}
	
	private void addStandardEvent(PreparedStatement statement, String id, long duration, boolean alert) 
			throws SQLException {
		statement.setString(1, id);
		statement.setLong(2, duration);
		statement.setBoolean(3, alert);
	}
	
	@Override
	public void outputAllEventsToConsole () throws Exception {
		PreparedStatement selectStatement = null;
		try {
			connection = DriverManager.getConnection(connectionString, "SA", "");
			String selectSQL = "select * from EventLog";
			selectStatement = connection.prepareStatement(selectSQL);
			ResultSet results = selectStatement.executeQuery();
			while(results.next()) {
				logger.debug (results.getString("id") + " is alert " + results.getString("flag"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (selectStatement != null) selectStatement.close();
			if (connection != null) connection.close();
		}		
	}
	

}

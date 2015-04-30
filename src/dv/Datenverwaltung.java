package dv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import svt.Einstellungen;

public class Datenverwaltung {
	
	private String dbIp;
	
	private String dbName;
	private String dbTable;
	
	private String dbUser;
	private String dbPassword;
	
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	public Datenverwaltung(){
		
		dbIp = "localhost";
		dbUser = "dittich";
		dbPassword = "!Casi2009!";
		
		dbName = "sv_ausweise";
		dbTable = "sv_schueler";
	}
	
	public ResultSet sqlQuery(String query)
	{
		try {
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			resultSet = null;
		}
		return resultSet;
	}
	
	public boolean sqlUpdate(String query)
	{
		try {
			statement.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isConnected() {
		try {
			return !connect.isClosed();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	public boolean connect()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			// Setup the connection with the DB
			connect = DriverManager.getConnection("jdbc:mysql://"+dbIp+"/"+dbName, dbUser, dbPassword);
			
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
		
	}
	
	public boolean close()
	{
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			
			if (statement != null) {
				statement.close();
			}
			
			if (connect != null) {
				connect.close();
			}
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbTable() {
		return dbTable;
	}

	public void setDbTable(String dbTable) {
		this.dbTable = dbTable;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getDbIp() {
		return dbIp;
	}

	public void setDbIp(String dbIp) {
		this.dbIp = dbIp;
	}
}

package DV;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Datenverwaltung {

	private String dbName;
	private String dbTable;
	
	private String sqlUser;
	private String sqlPassword;
	
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	public Datenverwaltung(){
		sqlUser = "dittich";
		sqlPassword = "!Casi2009!";
		
		dbName = "sv_ausweise";
		dbTable = "sv_schueler";
	}
	
	public ResultSet sqlQuery(String query) throws SQLException
	{
		resultSet = statement.executeQuery(query);
		return resultSet;
	}
	
	public void connect() throws SQLException, ClassNotFoundException
	{
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName, sqlUser, sqlPassword);
		
		// Statements allow to issue SQL queries to the database
		statement = connect.createStatement();
	}
	
	public void close()
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
		} catch (Exception e) {
			//
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

	public String getSqlUser() {
		return sqlUser;
	}

	public void setSqlUser(String sqlUser) {
		this.sqlUser = sqlUser;
	}

	public String getSqlPassword() {
		return sqlPassword;
	}

	public void setSqlPassword(String sqlPassword) {
		this.sqlPassword = sqlPassword;
	}
}

package de.dittich.sv.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBDienste {
	
	private static final DBDienste OBJ = new DBDienste();

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	
	private DBDienste(){
		System.out.println("DB Dienste gestartet...");
		boolean connected = this.connect();
		System.out.println("DB Dienste: Datenbank connectet ... "+connected);
	}
	
	public static DBDienste getInstance(){
		return OBJ;
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
	
	public boolean sqlInsert(String query)
	{
		boolean result = false;
		try {
			statement.executeUpdate(query);
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(query+" - "+result);
		return result;
	}
	
	public boolean sqlUpdate(String query)
	{
		boolean result = false;
		try {
			statement.executeUpdate(query);
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
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
		Config cfg = Config.getInstance();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			// Setup the connection with the DB
			connect = DriverManager.getConnection("jdbc:mysql://"+cfg.getDbIp()+"/"+cfg.getDbName(), cfg.getDbUser(), cfg.getDbPassword());
			
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
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

	public Connection getConnect() {
		return connect;
	}

	public void setConnect(Connection connect) {
		this.connect = connect;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}
}

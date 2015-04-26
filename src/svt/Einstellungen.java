package svt;

import java.io.Serializable;

public class Einstellungen implements Serializable{
	
	//DB Daten
	private String dbIp;
	private int dbPort = 3306;
	
	private String dbName;
	private String dbTable;
	
	private String dbUser;
	private String dbPassword;
	
	private String sicherungsDatei = "config.svt";;
	
	public Einstellungen()
	{
		
	}

	public String getDbIp() {
		return dbIp;
	}

	public void setDbIp(String dbIp) {
		this.dbIp = dbIp;
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

	public String getSicherungsDatei() {
		return sicherungsDatei;
	}

	public void setSicherungsDatei(String sicherungsDatei) {
		this.sicherungsDatei = sicherungsDatei;
	}

	public int getDbPort() {
		return dbPort;
	}

	public void setDbPort(int dbPort) {
		this.dbPort = dbPort;
	}
}

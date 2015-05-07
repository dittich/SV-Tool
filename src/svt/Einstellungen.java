package svt;


import java.io.File;
import java.io.Serializable;

public class Einstellungen implements Serializable{
	
	//DB Daten
	private String dbIp;
	private int dbPort = 3306;
	
	private String dbName;
	private String dbTable;
	
	private String dbUser;
	private String dbPassword;
	
	private File importOrdner;
	
	private String sicherungsDatei = "config.svt";
	
	private File cmdFile = new File("cmd.exe");
	private File mysqldumpFile = new File("C:/xampp/mysql/bin/mysqldump.exe");
	private File mysqlFile = new File("C:/xampp/mysql/bin/mysql.exe");
	private File mysqlimportFile = new File("C:/xampp/mysql/bin/mysqlimport.exe");
	
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

	public File getImportOrdner() {
		return importOrdner;
	}

	public void setImportOrdner(File importOrdner) {
		this.importOrdner = importOrdner;
	}

	public File getCmdFile() {
		return cmdFile;
	}

	public void setCmdFile(File cmdFile) {
		this.cmdFile = cmdFile;
	}

	public File getMysqldumpFile() {
		return mysqldumpFile;
	}

	public void setMysqldumpFile(File mysqldumpFile) {
		this.mysqldumpFile = mysqldumpFile;
	}

	public File getMysqlFile() {
		return mysqlFile;
	}

	public void setMysqlFile(File mysqlFile) {
		this.mysqlFile = mysqlFile;
	}

	public File getMysqlimportFile() {
		return mysqlimportFile;
	}

	public void setMysqlimportFile(File mysqlimportFile) {
		this.mysqlimportFile = mysqlimportFile;
	}
}

package de.dittich.sv.basic;

public class Config {
	
	private static final Config OBJ = new Config(); 
	
	private String filenameConfig = "config.ser.xml";
	
	private String dbIp = "localhost";
	private int dbPort = 3306;
	
	private String dbName = "sv_ausweise";
	private String dbUser = "sv";
	private String dbPassword = "sv";
	
	private Object changeCboKlassen = null;

	private Config() { 
        System.out.println("Config gebildet..."); 
    } 
         
    public static Config getInstance() { 
      return OBJ; 
    }
    
	public String getDbIp() {
		return dbIp;
	}

	public void setDbIp(String dbIp) {
		this.dbIp = dbIp;
	}

	public int getDbPort() {
		return dbPort;
	}

	public void setDbPort(int dbPort) {
		this.dbPort = dbPort;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
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

	public String getFilenameConfig() {
		return filenameConfig;
	}

	public void setFilenameConfig(String filenameConfig) {
		this.filenameConfig = filenameConfig;
	}

	public Object getChangeCboKlassen() {
		return changeCboKlassen;
	}

	public void setChangeCboKlassen(Object changeCboKlassen) {
		this.changeCboKlassen = changeCboKlassen;
	}
}

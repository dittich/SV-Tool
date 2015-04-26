package svt;

import java.sql.ResultSet;
import dv.Datenverwaltung;

public class SVTool {
	
	private Datenverwaltung dv;
	private Einstellungen einstellungen;
	
	
	public SVTool()
	{
		dv = new Datenverwaltung();
		einstellungen = new Einstellungen();
	}
	
	public void setDbTable(String dbTable)
	{
		dv.setDbTable(dbTable);
	}
	
	public ResultSet sqlQuery(String query)
	{
		return dv.sqlQuery(query);
	}
	
	public boolean isDbConnected()
	{
		return dv.isConnected();
	}
	
	public String getDbName()
	{
		return dv.getDbName();
	}
	
	public boolean dbConnect()
	{
		return dv.connect();
	}
	
	public boolean dbClose()
	{
		return dv.close();
	}
	
	public void setDbDaten(String dbIp, String dbName, String dbUser, String dbPassword)
	{
		einstellungen.setDbIp(dbIp);
		einstellungen.setDbName(dbName);
		einstellungen.setDbUser(dbUser);
		einstellungen.setDbPassword(dbPassword);
		
		dv.setDbName(dbName);
		dv.setDbPassword(dbPassword);
		dv.setDbUser(dbUser);
		dv.setDbIp(dbIp);
	}
	
	public boolean testDbVerbindung()
	{
		boolean testDbConnect = dv.connect();
		boolean testDbClose = dv.close();
		return testDbClose&&testDbConnect;
	}
}

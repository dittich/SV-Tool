package svt;


import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.ResultSet;

import dv.Dateimanager;
import dv.Datenverwaltung;

public class SVTool {
	
	private Dateimanager dm;
	private Datenverwaltung dv;
	private Einstellungen einstellungen;
	
	
	public SVTool()
	{
		dv = new Datenverwaltung();
		dm = new Dateimanager();
		einstellungenLaden();
	}
	
	public boolean sqlImportBackup(File backupFile){
		return dv.sqlImportBackup(backupFile, einstellungen.getMysql());
	}
	
	public boolean sqlDump(File fbackup){
		return dv.sqlDump(fbackup, einstellungen.getCmdFile(), einstellungen.getMysqldumpFile());
	}
	
	public void einstellungenLaden()
	{
		Object objEinstellungen = dm.laden("config.svt");
		if(objEinstellungen!=null)einstellungen=(Einstellungen)objEinstellungen;
		else einstellungen = new Einstellungen();
	}
	
	public void einstellungenSpeichern()
	{
		dm.speichern(einstellungen,"config.svt");
	}
	
	public File getImportOrdner()
	{
		File importOrdner = einstellungen.getImportOrdner();
		if(importOrdner==null)return new File("C:");
		return importOrdner;
	}
	
	public void setImportOrdner(File importOrdner)
	{
		einstellungen.setImportOrdner(importOrdner);
		einstellungenSpeichern();
	}
	
	public String getDbTable()
	{
		return einstellungen.getDbTable();
	}
	
	public void setDbTable(String dbTable)
	{
		einstellungen.setDbTable(dbTable);
		einstellungenSpeichern();
	}
	
	public boolean updateSqlImg(int id, BufferedImage img){
		return dv.sqlUpdateImg(id,img);
	}
	
	public ResultSet sqlQuery(String query)
	{
		return dv.sqlQuery(query);
	}
	
	public boolean sqlUpdate(String query)
	{
		return dv.sqlUpdate(query);
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

	public Einstellungen getEinstellungen() {
		return einstellungen;
	}

	public void setEinstellungen(Einstellungen einstellungen) {
		this.einstellungen = einstellungen;
	}
}

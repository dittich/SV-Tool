package de.dittich.sv.basic;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.imageio.ImageIO;

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
	
	public boolean sqlImportBackup(File backupFile) {
		File mysqlFile = new File(UserPreferences.getInstance().getSubNode("sv_mysql"));
		File cmd = new File(UserPreferences.getInstance().getSubNode("sv_cmd"));
		
		String dbUser = Config.getInstance().getDbUser();
		String dbPassword = Config.getInstance().getDbPassword();
		String dbName = Config.getInstance().getDbName();
		
		boolean wert = false;
		String mysqlPfad = prepareSpaces(mysqlFile.getParent())+"\\"+mysqlFile.getName();
		String source = prepareSpaces(backupFile.getParent())+"\\"+backupFile.getName();
		String[] executeCmd = new String[] {cmd.getName(), "/c", mysqlPfad+" -u "+dbUser+" -p"+dbPassword+" --max_allowed_packet=1G "+dbName+" < "+source};
		
		Process runtimeProcess;
		try {
			System.out.print("Starte Prozess SQL-Import...");
			runtimeProcess = Runtime.getRuntime().exec(executeCmd);
			int processComplete = runtimeProcess.waitFor();  
			if (processComplete == 0) {
				System.out.println("Backup restored successfully");  
				wert = true;  
			}
			else{  
				System.out.println("Could not restore the backup"); 
			} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return wert;
	}
	
	public boolean sqlDump(File fbackup) {
		File mysqldump = new File(UserPreferences.getInstance().getSubNode("sv_mysqldump"));
		File cmd = new File(UserPreferences.getInstance().getSubNode("sv_cmd"));
		String dbIp = Config.getInstance().getDbIp();
		String dbUser = Config.getInstance().getDbUser();
		String dbPassword = Config.getInstance().getDbPassword();
		String dbName = Config.getInstance().getDbName();
		
		boolean wert = false;
		String mysqldumpPfad = prepareSpaces(mysqldump.getParent())+"\\"+mysqldump.getName();
		String source = prepareSpaces(fbackup.getParent())+"\\"+fbackup.getName();
		
		String[] executeCmd = new String[] {cmd.getName(), "/c", mysqldumpPfad+" -h "+dbIp+" --u "+dbUser+" -p"+dbPassword+" --hex-blob --max_allowed_packet=1G "+dbName+" > "+source};
		
		Process runtimeProcess;
		try {
			runtimeProcess = Runtime.getRuntime().exec(executeCmd);
			int processComplete = runtimeProcess.waitFor();  
			if (processComplete == 0) {
				//System.out.println("Dump successfully");  
				wert = true;  
			}
			else{  
				//System.out.println("Could not dump"); 
			} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}  
		return wert;
	}
	
	private String prepareSpaces(String kette){
		String result = "";
		StringTokenizer tko = new StringTokenizer(kette,"\\");
		while(tko.hasMoreTokens()){
			String part = tko.nextToken();
			if(part.indexOf(' ')!=0){
				part = "\""+part+"\"";
			}
			result+=part;
			if(tko.hasMoreTokens()){
				result+="\\";
			}
		}
		return result;
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
	
	public boolean sqlUpdateImg(int id, BufferedImage img)
	{
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(img, "jpeg", baos);
			baos.flush();
	        
			PreparedStatement ps = connect.prepareStatement("UPDATE sv_schueler SET bild=?, typ=? WHERE id=?");
	        ps.setBytes(1, baos.toByteArray());
	        ps.setString(2, "image/jpeg");
	        ps.setInt(3, id);
	        
	        ps.executeUpdate();
	        
	        ps.close();
	        baos.close();
	        return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean sqlUpdate(int schueler_id, Vector vec)
	{
		boolean result = false;
		try {
			String sql = "update sv_schueler SET name = ?, vorname = ?, gebdatum = ?, geschlecht = ?, geloescht = ?, klasse = ? where schueler_id = ?";
			PreparedStatement stmt = connect.prepareStatement(sql);
			
			stmt.setString(1, (String)vec.get(1));
			stmt.setString(2, (String)vec.get(2));
			stmt.setString(3, (String)vec.get(3));
			stmt.setString(4, (String)vec.get(4));
			boolean boolGeloescht = false;
			if(((String)vec.get(5)).equals("Ja"))boolGeloescht=true;
			stmt.setBoolean(5, boolGeloescht);
			stmt.setString(6, (String)vec.get(6));
			stmt.setInt(7, schueler_id);
			stmt.executeUpdate();
			
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean sqlInsert(int schueler_id, Vector vec){
		boolean result = false;
		String query = " insert into sv_schueler (schueler_id, name, vorname, gebdatum, geschlecht, geloescht, selektiert, klasse)"
		        + " values (?, ?, ?, ?, ?, ?, ?, ?)";
		boolean boolGeloescht = false;
		if(((String)vec.get(5)).equals("Ja"))boolGeloescht=true;
		
		try {
			PreparedStatement preparedStmt = connect.prepareStatement(query);
			preparedStmt.setInt (1, schueler_id);
			preparedStmt.setString (2, (String)vec.get(1));
			preparedStmt.setString (3, (String)vec.get(2));
			preparedStmt.setString (4, (String)vec.get(3));
			preparedStmt.setString (5, (String)vec.get(4));
			preparedStmt.setBoolean(6, boolGeloescht);
			preparedStmt.setBoolean(7, false);
			preparedStmt.setString (8, (String)vec.get(6));
			
			// execute the preparedstatement
			preparedStmt.execute();
			
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean isId(int susId){
		boolean check = false;
		String query = "SELECT count(*) FROM sv_schueler WHERE schueler_id="+susId;
		ResultSet rs = sqlQuery(query);
		try {
			while (rs.next()) {
				if(rs.getInt(1)==1){
					check=true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return check;
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

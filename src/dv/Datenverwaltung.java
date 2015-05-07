package dv;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;

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
	
	public boolean sqlImportBackup(File backupFile, File mysqlFile) { 
		boolean wert = false;
		String mysqlPfad = mysqlFile.getParent()+"/"+mysqlFile.getName();
		String source = backupFile.getParent()+"/"+backupFile.getName();
		String[] executeCmd = new String[]{mysqlPfad, "--user=" + dbUser, "--password=" + dbPassword, dbName,"-e", " source "+source};  
		Process runtimeProcess;
		try {
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

	
	public boolean sqlDump(File fbackup, File cmd, File mysqldump){
	    // execute mysqldump command
		String mysqldumpPfad = mysqldump.getParent()+"/"+mysqldump.getName();
		//String cmdPfad = cmd.getParent()+"/"+cmd.getName();
		
	    String[] command = new String[] {cmd.getName(), "/c", mysqldumpPfad+" -u "+dbUser+" -p"+dbPassword+" "+dbName};
	    Process process;
		try {
			process = Runtime.getRuntime().exec(command);
		    // write process output line by line to file
		    if(process!=null) {
		        new Thread(new Runnable() {
		            @Override
		            public void run() {
		                try{
		                    try(BufferedReader reader = new BufferedReader(new InputStreamReader(new DataInputStream(process.getInputStream()))); 
		                        BufferedWriter writer = new BufferedWriter(new FileWriter(fbackup))) {
		                        String line;
		                        while((line=reader.readLine())!=null)   {
		                            writer.write(line);
		                            writer.newLine();
		                        }
		                    }
		                } catch(Exception ex){
		                    // handle or log exception ...
		                	System.out.println("Das war wohl nix ;-(");
		                }
		            }
		        }).start();
		    }
		    if(process!=null && process.waitFor()==0) {
		        // success ...
		    	System.out.println("Dump erfolgreich...");
		    	return true;
		    } else {
		        // failed
		    	System.out.println("Dump failed...");
		    	return false;
		    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
	
	public void sqlImport(File jarFile){
		//CodeSource codeSource = YourImplementingClass.class.getProtectionDomain().getCodeSource();
		String jarDir = jarFile.getParentFile().getPath();
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

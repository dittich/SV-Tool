package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class MySQLDumper {
	
	public static void export3() throws Exception{
	    // define backup file
	    File fbackup = new File("d:/backup.sql");
	    // execute mysqldump command
	    String[] command = new String[] {"cmd.exe", "/c", "C:/xampp/mysql/bin/mysqldump -u dittich -p!Casi2009! sv_ausweise"};
	    final Process process = Runtime.getRuntime().exec(command);
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
	                }
	            }
	        }).start();
	    }
	    if(process!=null && process.waitFor()==0) {
	        // success ...
	    } else {
	        // failed
	    }
	}
	
	public static void export4(){
		String dumpCommand = "mysqldump -u dittich -p!Casi2009! sv_ausweise > backup.sql";
		String importCommand = "mysql -u dittich -p!Casi2009! sv_ausweise < backup.sql";
		System.out.println(dumpCommand);
	}

	public static void main(String args[]) throws Exception{
		export3();
	}
}
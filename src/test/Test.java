package test;

import svt.Einstellungen;
import dv.Dateimanager;

 
public class Test {
 
    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception
    {
    	Einstellungen est = new Einstellungen();
    	Dateimanager dm = new Dateimanager();  
    	est.setDbName("Holger");
    	
    	//boolean gespeichert = dm.speichern(est);
    	
    	//System.out.println("Speichern in: "+est.getSicherungsDatei());
    	//System.out.println("Speichern..."+gespeichert);
    	
    	est = new Einstellungen();
    	
    	System.out.println("DBName: "+est.getDbName());
    	
    	est = (Einstellungen)dm.laden(est.getSicherungsDatei());
    	
    	System.out.println("DBName: "+est.getDbTable());
    	
    }
}
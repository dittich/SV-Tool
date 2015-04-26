package test;

import java.sql.ResultSet;

import dv.Datenverwaltung;
 
public class Test {
 
    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception
    {
        Datenverwaltung dv = new Datenverwaltung();
        
        dv.connect();
        
        ResultSet rs = dv.sqlQuery("select * from sv_schueler");
        
        while(rs.next())
        {
        	String name = rs.getString("name");
        	String vorname = rs.getString("vorname");
        	System.out.println(name+", "+vorname);
        }
        
        dv.close();
    }
}
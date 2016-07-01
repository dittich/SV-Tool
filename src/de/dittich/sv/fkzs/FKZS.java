package de.dittich.sv.fkzs;

import java.awt.image.BufferedImage;
import java.sql.ResultSet;

import de.dittich.sv.basic.DBDienste;

public class FKZS {
	
	private static final FKZS OBJ = new FKZS(); 
	
	private FKZS() { 
        System.out.println("FKZS gebildet..."); 
    }
	
	public static FKZS getInstance() { 
		return OBJ; 
	}
	
	public ResultSet sqlQuery(String query){
		return DBDienste.getInstance().sqlQuery(query);
	}
	
	public boolean sqlUpdate(String query){
		return DBDienste.getInstance().sqlUpdate(query);
	}
	
	public boolean sqlUpdateImg(int id, BufferedImage img){
		return DBDienste.getInstance().sqlUpdateImg(id, img);
	}
}

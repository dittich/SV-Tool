package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.swing.JPanel;

import svt.SVTool;

public class CSVDienste {
	
	public CSVDienste(){
	}
	
	public void csvImport(JPanel pnl, File csvFile, SVTool svTool){
		int neueSus = 0;
		int updateSuS = 0;
		int geloeschteSuS = 0;
		boolean headline = true;
		
		try {
			File fileDir = new File("d:/sus2014.csv");
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(fileDir), "UTF8"));
			String zeile = null;
			while ((zeile = in.readLine()) != null) {
				if(!headline) cutSuS(zeile);
				else headline=false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void cutSuS(String zeile){
		zeile = zeile.replace("\"", "");
		
		StringTokenizer st = new StringTokenizer(zeile, ";"); 
		
		int susID = Integer.parseInt(st.nextToken());
		String name = st.nextToken(); 
		String vorname = st.nextToken(); 
		String gebdatum = st.nextToken(); 
		String klasse = st.nextToken(); 
		String geloeschtStr = st.nextToken(); 
		String geschlecht = st.nextToken(); 
		
		boolean geloescht = false;
		if(geloeschtStr.equals("Ja"))geloescht=true;
		
		SuS sus = new SuS(susID,name,vorname,gebdatum,klasse,geloescht,geschlecht);
		
		System.out.println(sus.toString());
	}
}

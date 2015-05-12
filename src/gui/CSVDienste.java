package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.JPanel;

import svt.SVTool;

public class CSVDienste {
	
	private HashMap<String, String> hmDbName;
	private HashMap<String, Integer> hmCsvIndex;
	
	public CSVDienste(){
		hmDbName = this.generateHmDbName();
	}
	
	public void csvImport(JPanel pnl, File csvFile, SVTool svTool){
		int neueSus = 0;
		int updateSuS = 0;
		int geloeschteSuS = 0;
		boolean headline = true;
		
		try {
			File fileDir = new File("d:/SAusweis.csv");
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(fileDir), "UTF8"));
			String zeile = null;
			while ((zeile = in.readLine()) != null) {
				System.out.println(zeile);
				if(!headline) cutSuS(zeile);
				else {
					headline=false;
					hmCsvIndex = this.generateHmCsvIndex(zeile);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private HashMap<String, Integer> generateHmCsvIndex(String zeile){
		zeile = ";"+zeile;
		System.out.println(zeile);
		HashMap<String, Integer> lHmCsvIndex = new HashMap<String, Integer>();
		String[] parts = zeile.split(";");
		for(int i=0; i<parts.length; i++){
			System.out.println("Line: "+parts[i]);
			if(hmDbName.containsKey(parts[i])){
				String keyTabName = hmDbName.get(parts[i]);
				lHmCsvIndex.put(keyTabName, i);
				System.out.println(keyTabName+" - "+ i);
			}
			else System.out.println("OUT: "+parts[i]);
		}
		return lHmCsvIndex;
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
		
		//System.out.println(sus.toString());
	}
	
	private HashMap generateHmDbName(){
		// "Interne ID-Nummer";"Nachname";"Vorname";"Geburtsdatum";"Klasse";"Gelöscht?";"Geschlecht"
		HashMap<String, String> lDbName = new HashMap<String, String>();
		lDbName.put("\"Interne ID-Nummer\"", "schueler_id");
		lDbName.put("\"Nachname\"","name");
		lDbName.put("\"Vorname\"","vorname");
		lDbName.put("\"Geburtsdatum\"","gebdatum");
		lDbName.put("\"Klasse\"","klasse");
		lDbName.put("\"Gelöscht?\"","geloescht");
		lDbName.put("\"Geschlecht\"","geschlecht");
		
		return lDbName;	
	}
}

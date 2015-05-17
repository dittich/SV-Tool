package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class XLSDienste {
	
	private Vector<Vector<String>> xlsSheet;
	private HashMap<String, String> xls2Db;
	private HashMap<String, Integer> dbHeadline2xlsHeadlineIndex;
	
	public XLSDienste(){
		this.xls2Db = zuordnungXls2DB();
	}
	
	public void dbImport(){
		
	}
	
	public void susUpdate(File filename){
		this.susUpdate(filename.toString());
	}
	
	public void susUpdate(String filename){
		Vector vecSuS = this.readXLSFile(filename.toString());
		vecSuS = this.filterVector(vecSuS);
		
		//SQL-Anweisung für Update in DB - Vector vecSuS ist gefiltert inc. Headline
		
		//Hier folgt eine Test-Ausgabe des Vectors vecSuS
		String result = "";
		
		Iterator iter = vecSuS.iterator();
		while(iter.hasNext()){
			Vector vecRow = (Vector)iter.next();
			result = result + this.vector2String(vecRow) + "\n";
		}
		
		System.out.println(result);
	}
	
	private Vector<Vector<String>> readXLSFile(String fileName)
	{
		Vector<Vector<String>> xlsRows = new Vector<Vector<String>>();
		InputStream ExcelFileToRead;
		try {
			ExcelFileToRead = new FileInputStream(fileName);
			HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
			 
			HSSFSheet sheet=wb.getSheetAt(0);
			HSSFRow row; 
			HSSFCell cell;
	 
			Iterator rows = sheet.rowIterator();
	 
			while (rows.hasNext())
			{
				row=(HSSFRow) rows.next();
				Iterator cells = row.cellIterator();
				Vector<String> xlsCells = new Vector<String>();
				
				while (cells.hasNext())
				{
					cell=(HSSFCell) cells.next();	
					xlsCells.add(cell.getStringCellValue());
				}
				
				xlsRows.add(xlsCells);
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		this.zuordnungHeadlineName2Index(xlsRows.get(0));
		this.xlsSheet = xlsRows;
		
		return xlsRows;
	}
	
	public String toString(){
		String result = "";
		Vector vec = this.filterVector(xlsSheet);
		
		Iterator iter = vec.iterator();
		while(iter.hasNext()){
			Vector vecRow = (Vector)iter.next();
			result = result + this.vector2String(vecRow) + "\n";
		}
		
		return result;
	}
	
	private Vector filterVector(Vector vec){
		Vector result = new Vector();
		//result.add(selectedRow(xlsSheetHeadLine));
		Iterator iter = vec.iterator();
		while(iter.hasNext()){
			Vector vecRow = (Vector)iter.next();
			result.add(selectedRow(vecRow));
		}
		return result;
	}
	
	private Vector<String> selectedRow(Vector<?> row){
		Vector<String> vec = new Vector<String>();
		vec.add((String) row.get(dbHeadline2xlsHeadlineIndex.get("schueler_id")));
		vec.add((String) row.get(dbHeadline2xlsHeadlineIndex.get("name")));
		vec.add((String) row.get(dbHeadline2xlsHeadlineIndex.get("vorname")));
		vec.add((String) row.get(dbHeadline2xlsHeadlineIndex.get("gebdatum")));
		vec.add((String) row.get(dbHeadline2xlsHeadlineIndex.get("geschlecht")));
		vec.add((String) row.get(dbHeadline2xlsHeadlineIndex.get("geloescht")));
		vec.add((String) row.get(dbHeadline2xlsHeadlineIndex.get("klasse")));
		
		return vec;
	}
	
	private void zuordnungHeadlineName2Index(Vector vec){
		HashMap<String, Integer> lXls2IntIndex = new HashMap<String, Integer>();
		Iterator iter = vec.iterator();
		int counter = 0;
		while(iter.hasNext()){
			String wert = (String)iter.next();
			if(xls2Db.containsKey(wert)){
				String keyTabName = xls2Db.get(wert);
				lXls2IntIndex.put(keyTabName, counter);
			}
			counter++;
		}
		this.dbHeadline2xlsHeadlineIndex = lXls2IntIndex;
	}
	
	private HashMap zuordnungXls2DB(){
		// "Interne ID-Nummer";"Nachname";"Vorname";"Geburtsdatum";"Klasse";"Gelöscht?";"Geschlecht"
		HashMap<String, String> lDbName = new HashMap<String, String>();
		lDbName.put("Interne ID-Nummer", "schueler_id");
		lDbName.put("Nachname","name");
		lDbName.put("Vorname","vorname");
		lDbName.put("Geburtsdatum","gebdatum");
		lDbName.put("Klasse","klasse");
		lDbName.put("Gelöscht?","geloescht");
		lDbName.put("Geschlecht","geschlecht");
		
		return lDbName;	
	}
	
	private String vector2String(Vector vec){
		String result = "";
		Iterator iter = vec.iterator();
		while(iter.hasNext()){
			String wert = (String)iter.next();
			result = result + wert + " ";
		}
		return result;
	}
}

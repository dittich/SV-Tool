package de.dittich.sv.gui.panel.ausweis;

import java.sql.ResultSet;

import javax.swing.JScrollPane;

public class ScrollPaneTable extends JScrollPane{
	
	private static final ScrollPaneTable OBJ = new ScrollPaneTable(); 
	
	private TableConverter tblConv;

	private ScrollPaneTable() {
		System.out.println("ScrollPaneTable gebildet..."); 
		tblConv = new TableConverter();
		
    }
	
	public static ScrollPaneTable getInstance(){
		return OBJ;
	}
	
	public void showResultSet(ResultSet rs){
		tblConv.showResultSet(rs);
		setViewportView(tblConv);
	}
}

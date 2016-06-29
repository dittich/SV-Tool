package de.dittich.sv.gui.panel.ausweis;

import java.sql.ResultSet;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ScrollPaneTable extends JScrollPane{
	
	private static final ScrollPaneTable OBJ = new ScrollPaneTable();

	private ScrollPaneTable() {
		System.out.println("ScrollPaneTable gebildet..."); 
		TableConverter.getInstance().setScrPane(this);
		
    }
	
	public static ScrollPaneTable getInstance(){
		return OBJ;
	}
	
	public void showTable(String query){
		JTable tbl = TableConverter.getInstance().showTable(query);
		setViewportView(tbl);
	}
}

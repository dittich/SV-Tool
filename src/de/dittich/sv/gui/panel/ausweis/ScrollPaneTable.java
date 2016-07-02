package de.dittich.sv.gui.panel.ausweis;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import de.dittich.sv.basic.DBDienste;
import de.dittich.sv.basic.UserPreferences;

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
		zeigeTabelle(tbl);
	}
	
	public void showTable(){
		JTable tbl = TableConverter.getInstance().showTable();
		zeigeTabelle(tbl);
	}
	
	public void selectAllTable(){
		String query = TableConverter.getInstance().getOldSqlQuery();
		JTable tbl = TableConverter.getInstance().showTable(query);
		this.alleSelektieren(tbl, true);
		tbl = TableConverter.getInstance().showTable(query);
		zeigeTabelle(tbl);
	}
	
	public void deSelectAllTable(){
		String query = TableConverter.getInstance().getOldSqlQuery();
		JTable tbl = TableConverter.getInstance().showTable(query);
		this.alleSelektieren(tbl, false);
		tbl = TableConverter.getInstance().showTable(query);
		zeigeTabelle(tbl);
	}
	
	private void alleSelektieren(JTable tbl, boolean select){
		int rows = tbl.getRowCount();
		for(int row=0; row<tbl.getRowCount(); row++){
			int idIndex = (int)tbl.getModel().getValueAt(row, (tbl.getColumn("id").getModelIndex() ));
			String sqlUpdate = "UPDATE sv_schueler SET selektiert="+select+" WHERE id="+idIndex;
			DBDienste.getInstance().sqlUpdate(sqlUpdate);
		}
		tbl = TableConverter.getInstance().showTable();
		zeigeTabelle(tbl);
	}
	
	private void zeigeTabelle(JTable tbl){
		headerListener(tbl);
		setViewportView(tbl);
	}
	
	private void headerListener(JTable tbl){
		tbl.getTableHeader().addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        int col = tbl.columnAtPoint(e.getPoint());
		        String name = tbl.getColumnName(col);
		        System.out.println("Column index selected " + col + " " + name);
		        UserPreferences.getInstance().setSubNode("sv_orderby", name);
		        showTable();
		    }
		});
	}
}

package de.dittich.sv.gui.panel.ausweis;

import gui.SvTableCellRenderer;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TableConverter extends JTable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TableConverter() { 
        System.out.println("TableConverter gebildet..."); 
        setDefaultRenderer(Object.class, new SvTableCellRenderer());
        System.out.println("TableDefaultRenderer ueberschrieben..."); 
    }
	
	public void showResultSet(ResultSet rs){
		this.setModel(resultSetToTableModel(rs));
		this.hideCols();
	}
	
	public DefaultTableModel resultSetToTableModel(ResultSet rs) {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            Vector<String> columnNames = new Vector<String>();

            // Get the column names
            for (int column = 0; column < numberOfColumns; column++) {
                columnNames.addElement(metaData.getColumnLabel(column + 1));
            }

            // Get all rows.
            Vector<Vector<Object>> rows = new Vector<Vector<Object>>();

            while (rs.next()) {
                Vector<Object> newRow = new Vector<Object>();

                for (int i = 1; i <= numberOfColumns; i++) {
                    newRow.addElement(rs.getObject(i));
                }

                rows.addElement(newRow);
            }

            //return new SvTableModel(rows, columnNames);
            return new DefaultTableModel(rows, columnNames);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
	
	private void hideCols(){
		minCol("id");
		minCol("geschlecht");
		minCol("geloescht");
		minCol("selektiert");
		minCol("bild");
		minCol("typ");
	}
	
	private void minCol(String name){
		this.getColumn(name).setMinWidth(0);
		getColumn(name).setMaxWidth(0);
	}
}

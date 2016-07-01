package de.dittich.sv.gui.panel.bilder;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import de.dittich.sv.fkzs.FKZS;

public class JTableNamen extends JTable{
	
	private static final JTableNamen OBJ = new JTableNamen();
	
	private JTableNamen(){
		
	}
	
	public static JTableNamen getInstance(){
		return OBJ;
	}
	
	public JTable getTable(String sqlQuery){
		ResultSet rs = FKZS.getInstance().sqlQuery(sqlQuery);
		setModel(resultSetToTableModel(rs));
		setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
		return this;
	}
	
	public JTable getTable(){
		return this;
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
}

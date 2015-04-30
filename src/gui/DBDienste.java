package gui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import svt.SVTool;

public class DBDienste {
	
	private SVTool svtool;
	private LinkedList allMenuePanels;
	
	public DBDienste(SVTool svtool)
	{
		this.svtool = svtool;
		allMenuePanels = new LinkedList();
	}
	
	public void setVisibleMenuePanel(JPanel activatePanel)
	{
		Iterator iter = allMenuePanels.listIterator();
		while(iter.hasNext())
		{
			JPanel menuePanel = (JPanel)iter.next();
			menuePanel.setVisible(false);
		}
		activatePanel.setVisible(true);
	}
	
	public void addMenuePanel(JPanel pnl){
		this.allMenuePanels.add(pnl);
	}
	
	public void setBtnDbConnect(JButton btn, JPanel pnl, JComboBox cbo)
	{
		if(svtool.isDbConnected())
		{
			btn.setText("Connect");
			btn.setForeground(new Color(0, 128, 0));
			pnl.setVisible(true);
			fuelleCombobox(cbo, "SHOW TABLES");
		}
		else
		{
			btn.setText("Disconnect");
			btn.setForeground(Color.RED);
			pnl.setVisible(false);
		}
	}
	
	public void fuelleCombobox(JComboBox jcbBox, String sqlQuery)
	{
		jcbBox.removeAllItems();
		ResultSet rs = svtool.sqlQuery(sqlQuery);
		try {
			while(rs.next()){
				jcbBox.addItem(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jcbBox.setSelectedItem(svtool.getDbTable());
	}
	
	public String suchText(String text, KeyEvent arg0){
		int keyCode = arg0.getKeyCode();
		if(keyCode==127)return "";
		else if(keyCode==8 && text.length()>0)return text.substring(0, text.length()-1);
		else{
			return text+arg0.getKeyChar();
		}
	}
	
	public static TableModel resultSetToTableModel(ResultSet rs) {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            Vector columnNames = new Vector();

            // Get the column names
            for (int column = 0; column < numberOfColumns; column++) {
                columnNames.addElement(metaData.getColumnLabel(column + 1));
            }

            // Get all rows.
            Vector rows = new Vector();

            while (rs.next()) {
                Vector newRow = new Vector();

                for (int i = 1; i <= numberOfColumns; i++) {
                    newRow.addElement(rs.getObject(i));
                }

                rows.addElement(newRow);
            }

            return new DefaultTableModel(rows, columnNames);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}

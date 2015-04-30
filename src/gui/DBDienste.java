package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import svt.SVTool;

public class DBDienste {
	
	private SVTool svtool;
	private LinkedList<JPanel> allMenuePanels;
	private LinkedList<JButton> menuButton;
	
	public DBDienste(SVTool svtool)
	{
		this.svtool = svtool;
		allMenuePanels = new LinkedList<JPanel>();
		menuButton = new LinkedList<JButton>();
	}
	
	public void setEnableDbButton()
	{
		boolean btnEnable = svtool.isDbConnected();
		Iterator<JButton> iter = menuButton.listIterator();
		while(iter.hasNext()){
			JButton btn = (JButton)iter.next();
			btn.setEnabled(btnEnable);
		}
	}
	
	public void setVisibleMenuePanel(JPanel activatePanel)
	{
		Iterator<JPanel> iter = allMenuePanels.listIterator();
		while(iter.hasNext())
		{
			JPanel menuePanel = (JPanel)iter.next();
			menuePanel.setVisible(false);
		}
		activatePanel.setVisible(true);
	}
	
	public void addMenuButton(JButton btn){
		this.menuButton.add(btn);
	}
	
	public void addMenuePanel(JPanel pnl){
		this.allMenuePanels.add(pnl);
	}
	
	public void setBtnDbConnect(JButton btn, JPanel pnl, JComboBox<String> cbo)
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
	
	public void fuelleCombobox(JComboBox<String> jcbBox, String sqlQuery)
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
	
	public JTable resultSetToTable(String sqlQuery){
		ResultSet rs = svtool.sqlQuery(sqlQuery);
		JTable tbl = new JTable(resultSetToTableModel(rs));
		tbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){
					System.out.println("Detected Mouse Left Click!");
				}	    
				else if(e.getButton() == MouseEvent.BUTTON3){
					System.out.println("Detected Mouse Right Click!");
				}
				else if(e.getButton() == MouseEvent.BUTTON2){
					System.out.println("Detected Mouse Middle Click!");
				}
			}
		});
		
		tbl.setDefaultRenderer( Object.class, new DefaultTableCellRenderer(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				Component component = super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column);
				boolean select = (boolean) table.getModel().getValueAt(row, (table.getColumn("selektiert").getModelIndex() ));
				Object imageType = (Object)table.getModel().getValueAt(row, (table.getColumn("bild").getModelIndex() ));
				
				if( select ) {
					component.setBackground(Color.green);
				}
				else {
					component.setBackground(Color.white);
				}
				
				if( imageType == null){
					component.setForeground(Color.red);
				}
				else {
					component.setForeground(Color.black);
				}
				
				return component;
			};
		});
					
		return tbl;
	}
	
	public static TableModel resultSetToTableModel(ResultSet rs) {
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

           return new SvTableModel(rows, columnNames);
            //return new DefaultTableModel(rows, columnNames);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}

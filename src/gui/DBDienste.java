package gui;

import java.awt.Color;
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
import javax.swing.table.DefaultTableModel;

import svt.SVTool;

public class DBDienste{
	
	private SVTool svtool;
	private LinkedList<JPanel> allMenuePanels;
	private LinkedList<JButton> menuButton;
	private Main main;
	private String lastSql = "";
	
	public DBDienste(Main main, SVTool svtool)
	{
		this.main = main;
		this.svtool = svtool;
		allMenuePanels = new LinkedList<JPanel>();
		menuButton = new LinkedList<JButton>();
	}
	
	public void alleAbwaehlen(JTable tbl){
		alleSelektieren(tbl,false);
	}
	
	public void alleAuswaehlen(JTable tbl){
		alleSelektieren(tbl,true);
	}
	
	private void alleSelektieren(JTable tbl, boolean select){
		int rows = tbl.getRowCount();
		for(int row=0; row<tbl.getRowCount(); row++){
			int idIndex = (int)tbl.getModel().getValueAt(row, (tbl.getColumn("id").getModelIndex() ));
			String sqlUpdate = "UPDATE sv_schueler SET selektiert="+select+" WHERE id="+idIndex;
			boolean result = svtool.sqlUpdate(sqlUpdate);
		}
		main.updateTable(lastSql);
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
		if(rs!=null) lastSql=sqlQuery;
		JTable tbl = new JTable(resultSetToTableModel(rs));
		
		tbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int row = tbl.rowAtPoint(e.getPoint());
				int spalte = tbl.columnAtPoint(e.getPoint());
				int idIndex = (int)tbl.getModel().getValueAt(row, (tbl.getColumn("id").getModelIndex() ));
				boolean newSelect = !(boolean) tbl.getModel().getValueAt(row, (tbl.getColumn("selektiert").getModelIndex() ));
				
				if(e.getButton() == MouseEvent.BUTTON1){
					String sqlUpdate = "UPDATE sv_schueler SET selektiert="+newSelect+" WHERE id="+idIndex;
					boolean result = svtool.sqlUpdate(sqlUpdate);
					main.updateTable(sqlQuery);
				}	    
				else if(e.getButton() == MouseEvent.BUTTON2){
					System.out.println("Detected Mouse Middle Click!");
				}
				else if(e.getButton() == MouseEvent.BUTTON3){
					System.out.println("Detected Mouse Right Click!");
				}
			}
		});
		
		tbl.setDefaultRenderer(Object.class, new SvTableCellRenderer());
		
		return tbl;
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

            return new SvTableModel(rows, columnNames);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}

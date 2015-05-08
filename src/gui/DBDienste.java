package gui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
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
	
	public File checkFile(File file){
		if(file.getParent()==null){
			String newFileName = file.getName();
			newFileName.replace("\\", "");
			file = new File(newFileName);
		}
		return file;
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
	
	public JTable resultSetToTableZuweisen(String sqlQuery){
		ResultSet rs = svtool.sqlQuery(sqlQuery);
		JTable tbl = new JTable(resultSetToTableModel(rs));
		
		tbl.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
		
		minCol(tbl,"id");
		minCol(tbl,"schueler_id");
		minCol(tbl,"gebdatum");
		minCol(tbl,"geschlecht");
		minCol(tbl,"geloescht");
		minCol(tbl,"selektiert");
		minCol(tbl,"klasse");
		minCol(tbl,"bild");
		minCol(tbl,"typ");
		
		return tbl;
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
					main.setTxtInfoId(idIndex);
					
					String sqlQuery = "SELECT schueler_id,name,vorname,gebdatum,geschlecht,klasse,bild FROM sv_schueler WHERE id="+idIndex;
					ResultSet rs = svtool.sqlQuery(sqlQuery);

					try {
						while(rs.next()){
							main.setTxtInfoSchuelerId(rs.getInt(1));
							main.setTxtInfoNameVorname(rs.getString(2), rs.getString(3));
							main.setTxtInfoGebDatum(rs.getString(4));
							if(rs.getString(5).equals("m"))main.setTxtInfoGeschlecht("männlich");
							else main.setTxtInfoGeschlecht("weiblich");
							main.setTxtInfoKlasse(rs.getString(6));
							BufferedImage im = ImageIO.read(rs.getBinaryStream(7));
							ImageIcon image1 = new ImageIcon(im);
							main.getLblInfoImage().setIcon(image1);
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						main.getLblInfoImage().setIcon(null);
					}
				}
			}
		});
		
		tbl.setDefaultRenderer(Object.class, new SvTableCellRenderer());
		
		minCol(tbl,"id");
		minCol(tbl,"geschlecht");
		minCol(tbl,"geloescht");
		minCol(tbl,"selektiert");
		minCol(tbl,"bild");
		minCol(tbl,"typ");
		
		return tbl;
	}
	
	private void minCol(JTable tbl, String name){
		tbl.getColumn(name).setMinWidth(0);
		tbl.getColumn(name).setMaxWidth(0);
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
	
	public MyTableModel resultSetToMyTableModel1(ResultSet rs) {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            Vector<String> columnNames = new Vector<String>();
            int ids[] = new int[numberOfColumns];
            boolean hidden[] = new boolean[numberOfColumns];
            Object data[] = new Object[numberOfColumns];

            // Get the column names
            for (int column = 0; column < numberOfColumns; column++) {
                columnNames.addElement(metaData.getColumnLabel(column + 1));
                ids[column]=column;
                hidden[column]=false;
            }
            
            String[] names = columnNames.toArray(new String[columnNames.size()]);
            
            MyTableModel mtm = new MyTableModel(names, ids, hidden);
            
            // Get all rows.
            Vector<Vector<Object>> rows = new Vector<Vector<Object>>();

            while (rs.next()) {
                Vector<Object> newRow = new Vector<Object>();

                for (int i = 1; i <= numberOfColumns; i++) {
                    newRow.addElement(rs.getObject(i));
                }

                mtm.addRowVec(newRow);
            }
            
            mtm.setColumnHidden("schueler_id",true);
			mtm.setColumnHidden("gebdatum",true);
	        mtm.setColumnHidden("geschlecht",true);
	        mtm.setColumnHidden("geloescht",true);
	        mtm.setColumnHidden("selektiert",true);
	        mtm.setColumnHidden("bild",true);
	        mtm.setColumnHidden("typ",true);
            
            return mtm;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}

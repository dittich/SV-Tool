package de.dittich.sv.gui.panel.ausweis;

import gui.SvTableCellRenderer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import de.dittich.sv.fkzs.FKZS;

public class TableConverter extends JTable{
	
	private static final TableConverter OBJ = new TableConverter(); 
	
	private ScrollPaneTable scrPane = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TableConverter() {
		System.out.println("TableConverter gebildet..."); 
        setDefaultRenderer(Object.class, new SvTableCellRenderer());
        System.out.println("TableDefaultRenderer ueberschrieben...");
    }
	
	public static TableConverter getInstance(){
		return OBJ;
	}
	
	public JTable showTable(String query){
		JTable tbl = new JTable();
		
		tbl.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int row = tbl.rowAtPoint(e.getPoint());
				int idIndex = (int)tbl.getModel().getValueAt(row, (tbl.getColumn("id").getModelIndex() ));
				boolean newSelect = !(boolean) tbl.getModel().getValueAt(row, (tbl.getColumn("selektiert").getModelIndex() ));
				
				if(e.getButton() == MouseEvent.BUTTON1){
					System.out.println("Detected Mouse Left Click!");
					String sqlUpdate = "UPDATE sv_schueler SET selektiert="+newSelect+" WHERE id="+idIndex;
					System.out.println(sqlUpdate);
					FKZS.getInstance().sqlUpdate(sqlUpdate);
					refreshTable(tbl, query);
				}	    
				else if(e.getButton() == MouseEvent.BUTTON2){
					System.out.println("Detected Mouse Middle Click!");
				}
				else if(e.getButton() == MouseEvent.BUTTON3){
					System.out.println("Detected Mouse Right Click!");
					
					PnlSuSInfo.getInstance().setTxtId(""+idIndex);
					
					String sqlQuery = "SELECT schueler_id,name,vorname,gebdatum,geschlecht,klasse,bild FROM sv_schueler WHERE id="+idIndex;
					ResultSet rs = FKZS.getInstance().sqlQuery(sqlQuery);

					try {
						while(rs.next()){
							PnlSuSInfo.getInstance().setTxtSuSId(rs.getString("schueler_id"));
							PnlSuSInfo.getInstance().setTxtName(rs.getString("name")+", "+rs.getString("vorname"));
							PnlSuSInfo.getInstance().setTxtGeb(rs.getString("gebdatum"));
							PnlSuSInfo.getInstance().setTxtGeschlecht(rs.getString("geschlecht"));
							PnlSuSInfo.getInstance().setTxtKlasse(rs.getString("klasse"));
							
							BufferedImage buffImage = ImageIO.read(rs.getBinaryStream("bild"));
							ImageIcon imageIcon = new ImageIcon(buffImage);
							PnlSuSInfo.getInstance().getLblInfoImage().setIcon(imageIcon);
							//BufferedImage im = ImageIO.read(rs.getBinaryStream(7));
							//ImageIcon image1 = new ImageIcon(im);
							//main.getLblInfoImage().setIcon(image1);
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						//main.getLblInfoImage().setIcon(null);
					}
					
				}
			}
		});
		
		refreshTable(tbl, query);
		
		return tbl;
	}
	
	private void refreshTable(JTable tbl, String query){
		ResultSet rs = FKZS.getInstance().sqlQuery(query);
		tbl.setModel(resultSetToTableModel(rs));
		tbl.setDefaultRenderer(Object.class, new SvTableCellRenderer());
		
		minCol(tbl,"id");
		minCol(tbl,"geschlecht");
		minCol(tbl,"geloescht");
		minCol(tbl,"selektiert");
		minCol(tbl,"bild");
		minCol(tbl,"typ");
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
	
	private void minCol(JTable tbl, String name){
		tbl.getColumn(name).setMinWidth(0);
		tbl.getColumn(name).setMaxWidth(0);
	}

	public ScrollPaneTable getScrPane() {
		return scrPane;
	}

	public void setScrPane(ScrollPaneTable scrPane) {
		this.scrPane = scrPane;
	}
}

package de.dittich.sv.basic;

import gui.SvTableCellRenderer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import de.dittich.sv.gui.Main;

public class DBDienste {

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private Main main;
	private Config cfg;
	
	private String lastSql = "";
	
	public DBDienste(Main main){
		this.main = main;
		cfg = Config.getInstance();
	}
	
	public void fuelleCombobox(JComboBox<String> jcbBox, String sqlQuery)
	{
		jcbBox.removeAllItems();
		ResultSet rs = this.sqlQuery(sqlQuery);
		try {
			while(rs.next()){
				jcbBox.addItem(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object cboSelect = cfg.getChangeCboKlassen();
		if(cboSelect!=null){
			jcbBox.setSelectedItem(cboSelect);
		}
	}
	
	/*
	public JTable resultSetToTable(String sqlQuery){
		ResultSet rs = this.sqlQuery(sqlQuery);
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
					boolean result = this.sqlUpdate(sqlUpdate);
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

			private boolean sqlUpdate(String sqlUpdate) {
				// TODO Auto-generated method stub
				try {
					statement.executeUpdate(sqlUpdate);
					return true;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
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
	*/
	
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
	
	public ResultSet sqlQuery(String query)
	{
		try {
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			resultSet = null;
		}
		return resultSet;
	}
	
	public boolean isConnected() {
		try {
			return !connect.isClosed();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	public boolean connect()
	{
		Config cfg = Config.getInstance();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			// Setup the connection with the DB
			connect = DriverManager.getConnection("jdbc:mysql://"+cfg.getDbIp()+"/"+cfg.getDbName(), cfg.getDbUser(), cfg.getDbPassword());
			
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
			return false;
		}
		
	}
	
	public boolean close()
	{
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			
			if (statement != null) {
				statement.close();
			}
			
			if (connect != null) {
				connect.close();
			}
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}

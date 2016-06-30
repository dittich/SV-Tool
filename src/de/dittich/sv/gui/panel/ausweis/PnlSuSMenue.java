package de.dittich.sv.gui.panel.ausweis;

import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

import de.dittich.sv.fkzs.FKZS;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PnlSuSMenue extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ResultSet rs = null;
	/**
	 * Create the panel.
	 */
	public PnlSuSMenue() {
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JButton btnSuSAlle = new JButton("Alle");
		btnSuSAlle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sqlQuery = "SELECT * FROM sv_schueler WHERE geloescht=0";
				ScrollPaneTable.getInstance().showTable(sqlQuery);
			}
		});
		add(btnSuSAlle);
		
		JButton btnSuSSelektiert = new JButton("Selektierte");
		btnSuSSelektiert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String sqlQuery = "SELECT * FROM sv_schueler WHERE selektiert=1";
				ScrollPaneTable.getInstance().showTable(sqlQuery);
			}
		});
		add(btnSuSSelektiert);
		
		JComboBox<String> cboSuSKlasse = new JComboBox<String>();
		cboSuSKlasse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cboSuSKlasse.getSelectedItem()!=null){
					String klasse = cboSuSKlasse.getSelectedItem().toString();
					if(!klasse.equals("")){
						String sqlQuery = "SELECT * FROM sv_schueler WHERE klasse='"+klasse+"'";
						ScrollPaneTable.getInstance().showTable(sqlQuery);
					}
				}
			}
		});
		cboSuSKlasse.removeAllItems();
		rs = FKZS.getInstance().sqlQuery("SELECT klasse FROM sv_schueler GROUP BY klasse ORDER BY klasse");
		try {
			while(rs.next()){
				String kl = rs.getString("klasse");
				cboSuSKlasse.addItem(kl);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cboSuSKlasse.setSelectedIndex(1);
		add(cboSuSKlasse);
		
		JTextField txtSuSSearchname = new JTextField();
		txtSuSSearchname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				String suchText = suchText(txtSuSSearchname.getText(),arg0);
				String sqlQuery = "SELECT * FROM sv_schueler WHERE name LIKE '%"+suchText+"%' OR vorname LIKE '%"+suchText+"%'";
				ScrollPaneTable.getInstance().showTable(sqlQuery);
			}
		});
		txtSuSSearchname.setColumns(10);
		add(txtSuSSearchname);
		
		JButton btnSuSAusgewaehlteAuswaehlen = new JButton("Gelistete markieren");
		btnSuSAusgewaehlteAuswaehlen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScrollPaneTable.getInstance().selectAllTable();
			}
		});
		add(btnSuSAusgewaehlteAuswaehlen);
		
		JButton btnSuSAusgewaehlteAbwaehlen = new JButton("Gelistete abwählen");
		btnSuSAusgewaehlteAbwaehlen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScrollPaneTable.getInstance().deSelectAllTable();
			}
		});
		add(btnSuSAusgewaehlteAbwaehlen);
	}
	
	private String suchText(String text, KeyEvent arg0){
		int keyCode = arg0.getKeyCode();
		if(keyCode==127)return "";
		else if(keyCode==8 && text.length()>0)return text.substring(0, text.length()-1);
		else{
			return text+arg0.getKeyChar();
		}
	}
}

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
		add(btnSuSAlle);
		
		JButton btnSuSSelektiert = new JButton("Selektierte");
		add(btnSuSSelektiert);
		
		JComboBox<String> cboSuSKlasse = new JComboBox<String>();
		cboSuSKlasse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cboSuSKlasse.getSelectedItem()!=null){
					String klasse = cboSuSKlasse.getSelectedItem().toString();
					if(!klasse.equals("")){
						rs = FKZS.getInstance().sqlQuery("SELECT * FROM sv_schueler WHERE klasse='"+klasse+"'");
						ScrollPaneTable.getInstance().showResultSet(rs);
					}
				}
			}
		});
		cboSuSKlasse.removeAllItems();
		rs = FKZS.getInstance().sqlQuery("SELECT klasse FROM sv_schueler GROUP BY klasse ORDER BY klasse");
		try {
			while(rs.next()){
				cboSuSKlasse.addItem(rs.getString("klasse"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cboSuSKlasse.setSelectedIndex(1);
		add(cboSuSKlasse);
		
		JTextField txtSuSSearchname = new JTextField();
		txtSuSSearchname.setColumns(10);
		add(txtSuSSearchname);
		
		JButton btnSuSAusgewaehlteAuswaehlen = new JButton("Gelistete markieren");
		add(btnSuSAusgewaehlteAuswaehlen);
		
		JButton btnSuSAusgewaehlteAbwaehlen = new JButton("Gelistete abwählen");
		add(btnSuSAusgewaehlteAbwaehlen);
	}

}

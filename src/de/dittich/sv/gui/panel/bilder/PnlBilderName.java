package de.dittich.sv.gui.panel.bilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import de.dittich.sv.fkzs.FKZS;
import de.dittich.sv.gui.VerticalLayout;
import de.dittich.sv.gui.panel.ausweis.ScrollPaneTable;
import de.dittich.sv.gui.panel.ausweis.TableConverter;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.GridLayout;

public class PnlBilderName extends JPanel {

	private JScrollPane scrPaneBilder;
	
	public PnlBilderName() {
		setLayout(new VerticalLayout(0,3));
		//setLayout(new GridLayout(4,0));
		
		JButton btnSelektierte = new JButton("Selektierte SuS");
		btnSelektierte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sqlQuery = "SELECT id, klasse, name, vorname FROM sv_schueler WHERE selektiert='1'";
				JTable tbl = JTableNamen.getInstance().getTable(sqlQuery);
				scrPaneBilder.setViewportView(tbl);
			}
		});
		add(btnSelektierte);
		
		scrPaneBilder = new JScrollPane();
		
		add(PnlChopImage.getInstance());
		
		JComboBox cboImgKlasse = new JComboBox();
		cboImgKlasse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(cboImgKlasse.getSelectedItem()!=null){
            		String klasse = cboImgKlasse.getSelectedItem().toString();
            		if(!klasse.equals("")){
						String sqlQuery = "SELECT id, klasse, name, vorname FROM sv_schueler WHERE klasse='"+klasse+"'";
						JTable tbl = JTableNamen.getInstance().getTable(sqlQuery);
						scrPaneBilder.setViewportView(tbl);
					}
            	}
            }
        });
		cboImgKlasse.removeAllItems();
		ResultSet rs = FKZS.getInstance().sqlQuery("SELECT klasse FROM sv_schueler GROUP BY klasse ORDER BY klasse");
		try {
			while(rs.next()){
				String kl = rs.getString("klasse");
				cboImgKlasse.addItem(kl);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cboImgKlasse.setSelectedIndex(1);
		add(cboImgKlasse);
		
		add(scrPaneBilder);
	}
	
	private void nameTable(String query){
		JTable tbl = JTableNamen.getInstance().getTable(query);
		scrPaneBilder = new JScrollPane(tbl);
		scrPaneBilder.setViewportView(tbl);
	}

}

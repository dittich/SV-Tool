package de.dittich.sv.gui.panel.ausweis;

import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

public class PnlSuSMenue extends JPanel {

	/**
	 * Create the panel.
	 */
	public PnlSuSMenue() {
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JButton btnSuSAlle = new JButton("Alle");
		add(btnSuSAlle);
		
		JButton btnSuSSelektiert = new JButton("Selektierte");
		add(btnSuSSelektiert);
		
		JComboBox cboSuSKlasse = new JComboBox();
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

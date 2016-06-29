package de.dittich.sv.gui.panel.bilder;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.dittich.sv.gui.VerticalLayout;

public class PnlBilderName extends JPanel {

	/**
	 * Create the panel.
	 */
	public PnlBilderName() {
		setLayout(new VerticalLayout());
		
		JLabel lblNewLabel = new JLabel("SuS Name");
		add(lblNewLabel);
		
		JTextField txtBilderNameSuche = new JTextField();
		txtBilderNameSuche.setColumns(10);
		add(txtBilderNameSuche);
		
		JButton btnSuchen = new JButton("Suchen");
		add(btnSuchen);
	}

}

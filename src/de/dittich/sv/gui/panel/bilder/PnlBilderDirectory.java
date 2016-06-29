package de.dittich.sv.gui.panel.bilder;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PnlBilderDirectory extends JPanel {

	/**
	 * Create the panel.
	 */
	public PnlBilderDirectory() {
		setLayout(new BorderLayout(0, 0));
		
		JLabel lblBilderSuche = new JLabel("Bilder Directory suchen");
		add(lblBilderSuche);
	}

}

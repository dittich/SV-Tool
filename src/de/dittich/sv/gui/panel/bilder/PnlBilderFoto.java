package de.dittich.sv.gui.panel.bilder;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PnlBilderFoto extends JPanel {

	/**
	 * Create the panel.
	 */
	public PnlBilderFoto() {
		setLayout(new BorderLayout(0, 0));
		
		JLabel lblBilderFoto = new JLabel("Foto Infos");
		add(lblBilderFoto);
	}

}

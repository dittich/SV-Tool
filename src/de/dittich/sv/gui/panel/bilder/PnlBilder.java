package de.dittich.sv.gui.panel.bilder;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class PnlBilder extends JPanel {

	/**
	 * Create the panel.
	 */
	public PnlBilder() {
		setLayout(new BorderLayout(0, 0));
		
		add(new PnlBilderName(), BorderLayout.WEST);
		add(new PnlBilderDirectory(), BorderLayout.CENTER);
		add(new PnlBilderFoto(), BorderLayout.EAST);
	}

}

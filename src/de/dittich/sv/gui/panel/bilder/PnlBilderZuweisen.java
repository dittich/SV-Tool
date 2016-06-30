package de.dittich.sv.gui.panel.bilder;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;

public class PnlBilderZuweisen extends JPanel {

	/**
	 * Create the panel.
	 */
	public PnlBilderZuweisen() {
		setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnZuweisen = new JButton("Zuweisen");
		add(btnZuweisen);

	}

}

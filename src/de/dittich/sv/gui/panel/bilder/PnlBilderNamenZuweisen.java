package de.dittich.sv.gui.panel.bilder;

import javax.swing.JPanel;
import java.awt.FlowLayout;

public class PnlBilderNamenZuweisen extends JPanel {

	/**
	 * Create the panel.
	 */
	public PnlBilderNamenZuweisen() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 1));
		
		add(new PnlBilderName());
	}

}

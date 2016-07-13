package de.dittich.sv.gui.panel.daten;

import javax.swing.JPanel;
import java.awt.BorderLayout;

public class PnlDaten extends JPanel {

	/**
	 * Create the panel.
	 */
	public PnlDaten() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel pnlDatenMenue = new PnlDatenMenue();
		add(pnlDatenMenue, BorderLayout.NORTH);
		
		JPanel pnlDatenInfo = new PnlDatenInfo();
		add(pnlDatenInfo, BorderLayout.CENTER);
	}

}

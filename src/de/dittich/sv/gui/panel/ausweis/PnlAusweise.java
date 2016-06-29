package de.dittich.sv.gui.panel.ausweis;

import javax.swing.JPanel;
import java.awt.BorderLayout;

public class PnlAusweise extends JPanel {

	/**
	 * Create the panel.
	 */
	public PnlAusweise() {
		setLayout(new BorderLayout(0, 0));
		add(new PnlSuSMenue(), BorderLayout.NORTH);
		add(new PnlSuSInfo(), BorderLayout.EAST);
		add(new PnlSuSTable(), BorderLayout.CENTER);
		add(new PnlSuSErstellen(), BorderLayout.SOUTH);
	}

}

package de.dittich.sv.gui.panel.ausweis;

import javax.swing.JPanel;
import java.awt.BorderLayout;

public class PnlAusweise extends JPanel {

	/**
	 * Create the panel.
	 */
	public PnlAusweise() {
		ScrollPaneTable.getInstance();
		
		setLayout(new BorderLayout(0, 0));
		PnlSuSTable pnlTable = new PnlSuSTable();
		add(pnlTable, BorderLayout.CENTER);
		add(new PnlSuSMenue(), BorderLayout.NORTH);
		add(PnlSuSInfo.getInstance(), BorderLayout.EAST);
		add(new PnlSuSErstellen(), BorderLayout.SOUTH);
	}

}

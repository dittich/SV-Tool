package de.dittich.sv.gui.panel.schild;

import javax.swing.JPanel;
import java.awt.BorderLayout;

public class PnlSchild extends JPanel {

	/**
	 * Create the panel.
	 */
	public PnlSchild() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel pnlSchildMenue = new PnlSchildMenue();
		add(pnlSchildMenue, BorderLayout.NORTH);
		
		JPanel pnlSchildInfo = new PnlSchildInfo();
		add(pnlSchildInfo, BorderLayout.CENTER);

	}

}

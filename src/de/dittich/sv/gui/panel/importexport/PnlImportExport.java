package de.dittich.sv.gui.panel.importexport;

import javax.swing.JPanel;
import java.awt.BorderLayout;

public class PnlImportExport extends JPanel {

	/**
	 * Create the panel.
	 */
	public PnlImportExport() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel pnlImportExportMenue = new PnlImportExportMenue();
		add(pnlImportExportMenue, BorderLayout.NORTH);
		
		JPanel pnlImportExportInfo = new PnlImportExportInfo();
		add(pnlImportExportInfo, BorderLayout.CENTER);

	}

}

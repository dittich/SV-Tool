package de.dittich.sv.gui.panel.ausweis;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PnlSuSTable extends JPanel {
	private JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public PnlSuSTable() {
	
		scrollPane = ScrollPaneTable.getInstance();
		add(scrollPane);
		
	}

}

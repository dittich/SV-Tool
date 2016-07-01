package de.dittich.sv.gui.panel.ausweis;

import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PnlSuSTable extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public PnlSuSTable() {
		scrollPane = ScrollPaneTable.getInstance();
		add(scrollPane);
		//setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		setLayout(new GridLayout(0, 1));
	}

}

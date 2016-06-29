package de.dittich.sv.gui.panel.ausweis;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PnlSuSErstellen extends JPanel {

	/**
	 * Create the panel.
	 */
	public PnlSuSErstellen() {
		setLayout(new BorderLayout(0, 0));
		
		JButton btnPdfErstellen = new JButton("PDF erstellen");
		add(btnPdfErstellen, BorderLayout.CENTER);
	}

}

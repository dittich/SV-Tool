package de.dittich.sv.gui.panel.ausweis;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PnlSuSErstellen extends JPanel {

	/**
	 * Create the panel.
	 */
	public PnlSuSErstellen() {
		setLayout(new BorderLayout(0, 0));
		
		JButton btnPdfErstellen = new JButton("PDF erstellen");
		btnPdfErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("PDF jetzt erstellen - ToDo...");
			}
		});
		add(btnPdfErstellen, BorderLayout.CENTER);
	}

}

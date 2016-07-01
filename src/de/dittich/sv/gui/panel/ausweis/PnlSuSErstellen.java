package de.dittich.sv.gui.panel.ausweis;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.dittich.sv.fkzs.FKZS;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

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
				PDFDienste pdfDienst = new PDFDienste(FKZS.getInstance().sqlQuery("SELECT schueler_id, name, vorname, gebdatum, klasse, selektiert, bild AS foto, "
						+ "																			nrw_logo AS nrwlogo, ad_logo AS adlogo, no_foto AS nofoto "
						+ "																			FROM sv_schueler, sv_config WHERE selektiert=1"));
				pdfDienst.makePDF(new File("d:/test.pdf"));
			}
		});
		add(btnPdfErstellen, BorderLayout.CENTER);
	}

}

package de.dittich.sv.gui.panel.ausweis;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import de.dittich.sv.basic.PDFDienste;

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
				JFileChooser chooser = new JFileChooser();
				String query = "SELECT schueler_id, name, vorname, gebdatum, klasse, selektiert, bild AS foto, nrw_logo AS nrwlogo, ad_logo AS adlogo, no_foto AS nofoto FROM sv_schueler, sv_config WHERE selektiert=1";
				int rueckgabeWert = chooser.showSaveDialog(null);
				if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
		        {
					PDFDienste pdfDienst = new PDFDienste();
					String pdfDatei = chooser.getSelectedFile().getAbsolutePath()+".pdf";
					pdfDienst.makePDF(new File(pdfDatei),query);
					pdfDienst.viewPDF(new File(pdfDatei), PnlPDFView.getInstance());
		        }
				
				//pdfDienst.makePDF(new File("d:/test.pdf"),query);
				//pdfDienst.viewPDF(new File("d:/test.pdf"), PnlPDFView.getInstance());
			}
		});
		add(btnPdfErstellen, BorderLayout.CENTER);
	}

}

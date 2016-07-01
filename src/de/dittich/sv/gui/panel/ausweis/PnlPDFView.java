package de.dittich.sv.gui.panel.ausweis;

import javax.swing.JPanel;

import de.dittich.sv.basic.PDFDienste;
import java.awt.BorderLayout;

public class PnlPDFView extends JPanel {
	
	private static final PnlPDFView OBJ = new PnlPDFView();
	
	private PnlPDFView() {
		PDFDienste pdfDienste = new PDFDienste();
		pdfDienste.viewPDFBlanc(this);
		setLayout(new BorderLayout(0, 0));
	}
	
	public static PnlPDFView getInstance(){
		return OBJ;
	}
}

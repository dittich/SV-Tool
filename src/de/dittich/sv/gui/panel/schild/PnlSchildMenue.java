package de.dittich.sv.gui.panel.schild;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.dittich.sv.basic.XLSDienste;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PnlSchildMenue extends JPanel {

	/**
	 * Create the panel.
	 */
	public PnlSchildMenue() {
		
		JButton btnSchildXlsschlerdatenImportieren = new JButton("Schild: XLS-Sch\u00FClerdaten importieren");
		btnSchildXlsschlerdatenImportieren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				XLSDienste xlsD = new XLSDienste();
				FileFilter filter = new FileNameExtensionFilter("XLS-Datei", "xls");         
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(filter);
				int rueckgabeWert = chooser.showOpenDialog(null);
				if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
		        {
		             // Ausgabe der ausgewaehlten Datei
		            String pathXLS = chooser.getSelectedFile().getAbsolutePath();
		            xlsD.susSqlImport(pathXLS);
		        }
			}
		});
		add(btnSchildXlsschlerdatenImportieren);

	}

}

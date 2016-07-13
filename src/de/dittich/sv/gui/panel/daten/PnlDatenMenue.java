package de.dittich.sv.gui.panel.daten;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.dittich.sv.basic.DBDienste;
import de.dittich.sv.basic.XLSDienste;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

public class PnlDatenMenue extends JPanel {

	/**
	 * Create the panel.
	 */
	public PnlDatenMenue() {
		
		JButton btnSqldatenBackupErstellen = new JButton("SQL-Daten Backup sichern");
		btnSqldatenBackupErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
		        int rueckgabeWert = chooser.showSaveDialog(null);
		        
		        if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
		        {
		            String exportDatei = chooser.getSelectedFile().getAbsolutePath()+".sql";
		            boolean check = DBDienste.getInstance().sqlDump(new File(exportDatei));
		            System.out.println(exportDatei+" --> "+check);
		        }
			}
		});
		add(btnSqldatenBackupErstellen);
		
		JButton btnSqldatenBackupZurcksichern = new JButton("SQL-Daten Backup zur\u00FCcksichern");
		btnSqldatenBackupZurcksichern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
		        int rueckgabeWert = chooser.showOpenDialog(null);
		        
		        if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
		        {
		        	//String userdir = System.getProperty("user.dir");
		        	String importDatei = chooser.getSelectedFile().getAbsolutePath();
		            boolean check = DBDienste.getInstance().sqlImportBackup(new File(importDatei));
		            System.out.println(importDatei+" --> "+check);
		        }
			}
		});
		add(btnSqldatenBackupZurcksichern);
		
		JButton btnSchlerdatenAktualisierenMit = new JButton("Sch\u00FClerdaten aktualisieren mit Schild-XLS");
		btnSchlerdatenAktualisierenMit.addActionListener(new ActionListener() {
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
		add(btnSchlerdatenAktualisierenMit);

	}

}

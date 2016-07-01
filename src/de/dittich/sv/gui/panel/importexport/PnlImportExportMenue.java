package de.dittich.sv.gui.panel.importexport;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import java.awt.GridLayout;

import javax.swing.JButton;

import de.dittich.sv.basic.DBDienste;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

public class PnlImportExportMenue extends JPanel {

	/**
	 * Create the panel.
	 */
	public PnlImportExportMenue() {
		setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnDatenImport = new JButton("Daten Import");
		btnDatenImport.addActionListener(new ActionListener() {
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
		add(btnDatenImport);
		
		JButton btnDatenExport = new JButton("Daten Export");
		btnDatenExport.addActionListener(new ActionListener() {
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
		add(btnDatenExport);

	}

}

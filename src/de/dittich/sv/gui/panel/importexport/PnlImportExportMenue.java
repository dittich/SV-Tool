package de.dittich.sv.gui.panel.importexport;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import java.awt.GridLayout;

import javax.swing.JButton;

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
		        	String userdir = System.getProperty("user.dir");
		        	System.out.println((new File(userdir)).getAbsolutePath());
		            System.out.println("Die zu öffnende Datei ist: " +
		                  chooser.getSelectedFile().getAbsolutePath());
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
		            System.out.println("Die zu speichernde Datei ist: " +
		                  chooser.getSelectedFile().getAbsolutePath());
		        }
			}
		});
		add(btnDatenExport);

	}

}

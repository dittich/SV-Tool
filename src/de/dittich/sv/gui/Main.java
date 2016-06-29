package de.dittich.sv.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import java.awt.FlowLayout;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;

import de.dittich.sv.basic.Config;
import de.dittich.sv.basic.DBDienste;
import de.dittich.sv.dv.Dateimanager;
import de.dittich.sv.gui.panel.ausweis.PnlAusweise;
import de.dittich.sv.gui.panel.bilder.PnlBilder;
import de.dittich.sv.gui.panel.einstellungen.PnlEinstellungen;
import de.dittich.sv.gui.panel.importexport.PnlImportExport;
import de.dittich.sv.gui.panel.schild.PnlSchild;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {

	private JFrame frmSvausweise;
	private JTextField txtBilderNamesSuche;
	private JTextField txtSuSSearchname;
	
	private DBDienste dbd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmSvausweise.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		dbd = new DBDienste(this);
		dbd.connect();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSvausweise = new JFrame();
		frmSvausweise.setTitle("SV-Ausweise 0.3");
		frmSvausweise.setBounds(100, 100, 952, 699);
		frmSvausweise.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSvausweise.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabMenue = new JTabbedPane(JTabbedPane.TOP);
		frmSvausweise.getContentPane().add(tabMenue, BorderLayout.CENTER);
		
		JPanel pnlAusweise = new PnlAusweise();
		tabMenue.addTab("Ausweise erstellen", null, pnlAusweise, null);
		
		JPanel pnlBilder = new PnlBilder();
		tabMenue.addTab("Bilder zuweisen", null, pnlBilder, null);
		
		JPanel pnlSchild = new PnlSchild();
		tabMenue.addTab("Schülerdaten aktualisieren", null, pnlSchild, null);
		
		JPanel pnlImportExport = new PnlImportExport();
		tabMenue.addTab("Daten-Backup", null, pnlImportExport, null);
		
		JPanel pnlEinstellungen = new PnlEinstellungen();
		tabMenue.addTab("Einstellungen", null, pnlEinstellungen, null);
		
		
	}

}

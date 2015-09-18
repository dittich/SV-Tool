package gui;


import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;

import svt.Einstellungen;
import svt.SVTool;

import javax.swing.JComboBox;
import javax.swing.JTable;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JScrollPane;

import java.awt.FlowLayout;

public class Main{

	private JFrame frmSvausweise;
	private JTextField txtEsDbIp;
	private JTextField txtEsDbPort;
	private JTextField txtEsDbUser;
	private JTextField txtEsDbPassword;
	
	private SVTool svtool;
	private DBDienste dbDienste;
	private JTextField txtEsDbName;
	private JTextField txtDbName;
	private JButton btnDbConnect;
	private JComboBox<String> cboDbTable;
	private JComboBox<String> cboListKlasse;
	private JPanel pnlDbTableAuswahl;
	private JButton btnListe;
	private JButton btnBilder;
	private JButton btnImport;
	private JButton btnPDF;
	private JTextField txtSuche;
	private JPanel pnlListTable;
	private JScrollPane scrollPane;
	private JScrollPane scrollPaneZuweisen;
	private JTextField txtInfoId;
	private JTextField txtInfoSchuelerId;
	private JTextField txtInfoNameVorname;
	private JTextField txtInfoGebDatum;
	private JTextField txtInfoGeschlecht;
	private JTextField txtInfoKlasse;
	private JLabel lblInfoImage;
	private JTextField txtEsImportOrdner;
	private DragPanel pnlImgWork;
	private BufferedImage imgDbCut;
	private JLabel lblImgNewImage;
	private JTextField txtEsExportOrdner;
	private JTextField txtEsMysqlFile;
	private JTextField txtEsMysqldumpFile;
	private JTextField txtEsBilderOrdner;
	private JTextField txtEsPDFDatei;
	private JTextField txtXLSImport;
	

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
	 * @throws IOException 
	 */
	public Main(){
		svtool = new SVTool();
		dbDienste = new DBDienste(this,svtool);
		initializeGUI();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initializeGUI(){
		frmSvausweise = new JFrame("");
		frmSvausweise.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("sv_ad.png")));
		frmSvausweise.setTitle("SV-Ausweise 0.2");
		frmSvausweise.setBounds(100, 100, 797, 582);
		frmSvausweise.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSvausweise.getContentPane().setLayout(null);
		
		UIManager.put("FileChooser.readOnly", Boolean.TRUE);
		
		JPanel pnlDB = new JPanel();
		pnlDB.setBounds(10, 45, 764, 495);
		frmSvausweise.getContentPane().add(pnlDB);
		pnlDB.setLayout(null);
		pnlDB.setVisible(false);
		
		JPanel pnlDbConnect = new JPanel();
		pnlDbConnect.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlDbConnect.setBounds(10, 11, 227, 133);
		pnlDB.add(pnlDbConnect);
		pnlDbConnect.setLayout(null);
		
		JLabel lblDbName = new JLabel("Db-Name");
		lblDbName.setBounds(10, 10, 100, 19);
		pnlDbConnect.add(lblDbName);
		
		txtDbName = new JTextField();
		txtDbName.setEnabled(false);
		txtDbName.setEditable(false);
		txtDbName.setBounds(10, 30, 100, 19);
		pnlDbConnect.add(txtDbName);
		txtDbName.setColumns(10);
		
		btnDbConnect = new JButton();
		btnDbConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(svtool.isDbConnected())svtool.dbClose();
				else svtool.dbConnect();
				dbDienste.setBtnDbConnect(btnDbConnect, pnlDbTableAuswahl, cboDbTable);
				dbDienste.setEnableDbButton();
			}
		});
		btnDbConnect.setBounds(120, 30, 100, 19);
		pnlDbConnect.add(btnDbConnect);
		
		pnlDbTableAuswahl = new JPanel();
		pnlDbTableAuswahl.setBounds(10, 60, 210, 66);
		pnlDbConnect.add(pnlDbTableAuswahl);
		pnlDbTableAuswahl.setLayout(null);
		pnlDbTableAuswahl.setVisible(false);
		
		JLabel lblDbTable = new JLabel("Tableauswahl");
		lblDbTable.setBounds(0, 0, 100, 19);
		pnlDbTableAuswahl.add(lblDbTable);
		
		cboDbTable = new JComboBox<String>();
		cboDbTable.setBounds(0, 20, 210, 19);
		pnlDbTableAuswahl.add(cboDbTable);
		
		JButton btnDbTable = new JButton("Set");
		btnDbTable.setBounds(0, 45, 210, 19);
		pnlDbTableAuswahl.add(btnDbTable);
		btnDbTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				svtool.setDbTable((String)cboDbTable.getSelectedItem());
			}
		});
		dbDienste.addMenuePanel(pnlDB);
		
		JPanel pnlCSV = new JPanel();
		pnlCSV.setBounds(10, 45, 764, 495);
		frmSvausweise.getContentPane().add(pnlCSV);
		pnlCSV.setLayout(null);
		pnlCSV.setVisible(false);
		dbDienste.addMenuePanel(pnlCSV);
		
		JPanel pnlXLSImport = new JPanel();
		pnlXLSImport.setBounds(0, 0, 254, 155);
		pnlCSV.add(pnlXLSImport);
		pnlXLSImport.setLayout(null);
		
		JLabel lblXlSImport = new JLabel("XLS-Datei");
		lblXlSImport.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblXlSImport.setBounds(10, 10, 150, 19);
		pnlXLSImport.add(lblXlSImport);
		
		txtXLSImport = new JTextField();
		txtXLSImport.setBounds(10, 40, 220, 19);
		pnlXLSImport.add(txtXLSImport);
		txtXLSImport.setColumns(10);
		
		JButton btnXLSImport = new JButton("Ausw\u00E4hlen");
		btnXLSImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//FilenameFilter filter = 
				new FilenameFilter() {
			        public boolean accept(File directory, String fileName) {
			            return fileName.endsWith(".xls");
			        }
		        };
				
				File xlsFile = SwingUtil.getFileChoice(new JDialog(), "c:", null, "XLS-Import Datei");
				txtXLSImport.setText(xlsFile.toString());
				svtool.setXlsFile(xlsFile);
			}
		});
		btnXLSImport.setBounds(10, 60, 220, 19);
		pnlXLSImport.add(btnXLSImport);
		
		JButton btnImportStarten = new JButton("Import starten");
		btnImportStarten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				XLSDienste xls = new XLSDienste(svtool);
				String xlsDateiName = svtool.getXlsFile().toString();
				xls.susSqlImport(xlsDateiName);
			}
		});
		btnImportStarten.setBounds(10, 90, 220, 19);
		pnlXLSImport.add(btnImportStarten);
		
		JPanel pnlEinstellungen = new JPanel();
		pnlEinstellungen.setBounds(10, 45, 764, 495);
		frmSvausweise.getContentPane().add(pnlEinstellungen);
		pnlEinstellungen.setLayout(null);
		
		JPanel pnlEsDb = new JPanel();
		pnlEsDb.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlEsDb.setBounds(0, 0, 240, 272);
		pnlEinstellungen.add(pnlEsDb);
		pnlEsDb.setLayout(null);
		
		JLabel lblEsDbIp = new JLabel("Server (IP)");
		lblEsDbIp.setBounds(10, 51, 100, 19);
		pnlEsDb.add(lblEsDbIp);
		lblEsDbIp.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		txtEsDbIp = new JTextField();
		txtEsDbIp.setBounds(10, 71, 100, 19);
		pnlEsDb.add(txtEsDbIp);
		txtEsDbIp.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtEsDbIp.setColumns(10);
		
		JLabel lblEsDbPort = new JLabel("Port");
		lblEsDbPort.setBounds(130, 50, 100, 19);
		pnlEsDb.add(lblEsDbPort);
		
		txtEsDbPort = new JTextField();
		txtEsDbPort.setBounds(130, 70, 100, 19);
		pnlEsDb.add(txtEsDbPort);
		txtEsDbPort.setColumns(10);
		
		JLabel lblEsDbName = new JLabel("Db-Name");
		lblEsDbName.setBounds(10, 95, 100, 19);
		pnlEsDb.add(lblEsDbName);
		
		txtEsDbName = new JTextField();
		txtEsDbName.setBounds(10, 115, 100, 19);
		pnlEsDb.add(txtEsDbName);
		txtEsDbName.setColumns(10);
		pnlEinstellungen.setVisible(false);
		
		JLabel lblEsDbUsername = new JLabel("Username");
		lblEsDbUsername.setBounds(10, 140, 100, 19);
		pnlEsDb.add(lblEsDbUsername);
		
		txtEsDbUser = new JTextField();
		txtEsDbUser.setBounds(10, 160, 100, 19);
		pnlEsDb.add(txtEsDbUser);
		txtEsDbUser.setColumns(10);
		
		JLabel lblEsDbPasswort = new JLabel("Passwort");
		lblEsDbPasswort.setBounds(130, 140, 100, 19);
		pnlEsDb.add(lblEsDbPasswort);
		
		txtEsDbPassword = new JTextField();
		txtEsDbPassword.setBounds(130, 160, 100, 19);
		pnlEsDb.add(txtEsDbPassword);
		txtEsDbPassword.setColumns(10);
		
		JLabel lblEsDbStatus = new JLabel("DB Status");
		lblEsDbStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblEsDbStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEsDbStatus.setBounds(10, 245, 220, 19);
		pnlEsDb.add(lblEsDbStatus);
		
		JButton btnEsDbVerbindungstest = new JButton("Test");
		btnEsDbVerbindungstest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean dbTest = svtool.testDbVerbindung();
				if(dbTest)lblEsDbStatus.setText("Erfolgreich");
				else lblEsDbStatus.setText("Verbindungsfehler");
				
			}
		});
		btnEsDbVerbindungstest.setBounds(10, 220, 220, 19);
		pnlEsDb.add(btnEsDbVerbindungstest);
		
		JLabel lblEsDbVerbindung = new JLabel("Datenbank - Verbindung");
		lblEsDbVerbindung.setHorizontalAlignment(SwingConstants.CENTER);
		lblEsDbVerbindung.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEsDbVerbindung.setBounds(10, 10, 220, 40);
		pnlEsDb.add(lblEsDbVerbindung);
		
		JButton btnEsDbSpeichern = new JButton("Speichern");
		btnEsDbSpeichern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				svtool.setDbDaten(txtEsDbIp.getText(), txtEsDbName.getText(), txtEsDbUser.getText(), txtEsDbPassword.getText());
				svtool.einstellungenSpeichern();
			}
		});
		btnEsDbSpeichern.setBounds(10, 195, 220, 19);
		pnlEsDb.add(btnEsDbSpeichern);
		dbDienste.addMenuePanel(pnlEinstellungen);
		
		JPanel pnlEsImportOrdner = new JPanel();
		pnlEsImportOrdner.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlEsImportOrdner.setBounds(251, 0, 170, 90);
		pnlEinstellungen.add(pnlEsImportOrdner);
		pnlEsImportOrdner.setLayout(null);
		
		JLabel lblEsImportOrdner = new JLabel("DB-Import-Ordner");
		lblEsImportOrdner.setBounds(10, 10, 150, 20);
		lblEsImportOrdner.setHorizontalAlignment(SwingConstants.CENTER);
		lblEsImportOrdner.setFont(new Font("Tahoma", Font.BOLD, 16));
		pnlEsImportOrdner.add(lblEsImportOrdner);
		
		txtEsImportOrdner = new JTextField();
		txtEsImportOrdner.setEditable(false);
		txtEsImportOrdner.setText(svtool.getImportOrdner().toString());
		txtEsImportOrdner.setBounds(10, 40, 150, 19);
		pnlEsImportOrdner.add(txtEsImportOrdner);
		txtEsImportOrdner.setColumns(10);
		
		JButton btnEsImportOrdner = new JButton("Ordner w\u00E4hlen");
		btnEsImportOrdner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File importOrdner = SwingUtil.getDirectoryChoice(new JDialog(), txtEsImportOrdner.getText(), "Ordner");
				if(importOrdner!=null){
					txtEsImportOrdner.setText(importOrdner.toString());
					svtool.setImportOrdner(dbDienste.checkFile(importOrdner));
				}
			}
		});
		btnEsImportOrdner.setBounds(10, 60, 150, 19);
		pnlEsImportOrdner.add(btnEsImportOrdner);
		
		JPanel pnlEsExportOrdner = new JPanel();
		pnlEsExportOrdner.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlEsExportOrdner.setBounds(250, 100, 170, 90);
		pnlEinstellungen.add(pnlEsExportOrdner);
		pnlEsExportOrdner.setLayout(null);
		
		JLabel lblExportOrdner = new JLabel("DB-Export-Ordner");
		lblExportOrdner.setBounds(10, 10, 150, 20);
		pnlEsExportOrdner.add(lblExportOrdner);
		lblExportOrdner.setHorizontalAlignment(SwingConstants.CENTER);
		lblExportOrdner.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		txtEsExportOrdner = new JTextField();
		txtEsExportOrdner.setBounds(10, 40, 150, 19);
		txtEsExportOrdner.setText(svtool.getExportOrdner().toString());
		txtEsExportOrdner.setEditable(false);
		txtEsExportOrdner.setColumns(10);
		pnlEsExportOrdner.add(txtEsExportOrdner);
		
		JButton btnEsExportOrdner = new JButton("Ordner w\u00E4hlen");
		btnEsExportOrdner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File exportOrdner = SwingUtil.getDirectoryChoice(new JDialog(), txtEsExportOrdner.getText(), "Export - Ordner");
				if(exportOrdner!=null){
					txtEsExportOrdner.setText(exportOrdner.toString());
					svtool.setExportOrdner(dbDienste.checkFile(exportOrdner));
				}
			}
		});
		btnEsExportOrdner.setBounds(10, 60, 150, 19);
		pnlEsExportOrdner.add(btnEsExportOrdner);
		
		JPanel lblEsSQLDateien = new JPanel();
		lblEsSQLDateien.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblEsSQLDateien.setBounds(430, 0, 334, 140);
		pnlEinstellungen.add(lblEsSQLDateien);
		lblEsSQLDateien.setLayout(null);
		
		JLabel lblSqlDateien = new JLabel("SQL - Dateien");
		lblSqlDateien.setHorizontalAlignment(SwingConstants.CENTER);
		lblSqlDateien.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSqlDateien.setBounds(100, 10, 129, 20);
		lblEsSQLDateien.add(lblSqlDateien);
		
		txtEsMysqlFile = new JTextField();
		txtEsMysqlFile.setText(svtool.getMysqlFile().toString());
		txtEsMysqlFile.setEditable(false);
		txtEsMysqlFile.setColumns(10);
		txtEsMysqlFile.setBounds(10, 40, 315, 19);
		lblEsSQLDateien.add(txtEsMysqlFile);
		
		JButton btnEsMysqlFile = new JButton("mysql - Datei");
		btnEsMysqlFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//FilenameFilter filter = 
				new FilenameFilter() {
			        public boolean accept(File directory, String fileName) {
			            return fileName.endsWith(".exe");
			        }
		        };
				
				File mysqlFile = SwingUtil.getFileChoice(new JDialog(), txtEsMysqlFile.getText(), null, "mysql - Datei");
				txtEsMysqlFile.setText(mysqlFile.toString());
				svtool.setMysqlFile(mysqlFile);
			}
		});
		btnEsMysqlFile.setBounds(10, 60, 150, 19);
		lblEsSQLDateien.add(btnEsMysqlFile);
		
		txtEsMysqldumpFile = new JTextField();
		txtEsMysqldumpFile.setText(svtool.getMysqldumpFile().toString());
		txtEsMysqldumpFile.setEditable(false);
		txtEsMysqldumpFile.setColumns(10);
		txtEsMysqldumpFile.setBounds(10, 90, 315, 19);
		lblEsSQLDateien.add(txtEsMysqldumpFile);
		
		JButton btnEsMysqldumpFile = new JButton("mysqldump - Datei");
		btnEsMysqldumpFile.setBounds(10, 110, 150, 19);
		lblEsSQLDateien.add(btnEsMysqldumpFile);
		
		JPanel pnlEsBilderOrdner = new JPanel();
		pnlEsBilderOrdner.setLayout(null);
		pnlEsBilderOrdner.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlEsBilderOrdner.setBounds(251, 201, 170, 90);
		pnlEinstellungen.add(pnlEsBilderOrdner);
		
		JLabel lblBilderOrdner = new JLabel("Bilder-Ordner");
		lblBilderOrdner.setHorizontalAlignment(SwingConstants.CENTER);
		lblBilderOrdner.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblBilderOrdner.setBounds(10, 10, 150, 20);
		pnlEsBilderOrdner.add(lblBilderOrdner);
		
		txtEsBilderOrdner = new JTextField();
		txtEsBilderOrdner.setText(svtool.getBilderOrdner().toString());
		txtEsBilderOrdner.setEditable(false);
		txtEsBilderOrdner.setColumns(10);
		txtEsBilderOrdner.setBounds(10, 40, 150, 19);
		pnlEsBilderOrdner.add(txtEsBilderOrdner);
		
		JButton btnEsBilderOrdner = new JButton("Ordner w\u00E4hlen");
		btnEsBilderOrdner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File bilderOrdner = SwingUtil.getDirectoryChoice(new JDialog(), txtEsBilderOrdner.getText(), "Bilder - Ordner");
				if(bilderOrdner!=null){
					txtEsBilderOrdner.setText(bilderOrdner.toString());
					svtool.setBilderOrdner(dbDienste.checkFile(bilderOrdner));
				}
			}
		});
		btnEsBilderOrdner.setBounds(10, 60, 150, 19);
		pnlEsBilderOrdner.add(btnEsBilderOrdner);
		
		JPanel pnlEsPDFOrdner = new JPanel();
		pnlEsPDFOrdner.setLayout(null);
		pnlEsPDFOrdner.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlEsPDFOrdner.setBounds(430, 151, 334, 90);
		pnlEinstellungen.add(pnlEsPDFOrdner);
		
		JLabel lblPDFDatei = new JLabel("PDF - Datei");
		lblPDFDatei.setHorizontalAlignment(SwingConstants.CENTER);
		lblPDFDatei.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPDFDatei.setBounds(77, 9, 150, 20);
		pnlEsPDFOrdner.add(lblPDFDatei);
		
		txtEsPDFDatei = new JTextField();
		txtEsPDFDatei.setText(svtool.getPDFFile().toString());
		txtEsPDFDatei.setEditable(false);
		txtEsPDFDatei.setColumns(10);
		txtEsPDFDatei.setBounds(10, 40, 314, 19);
		pnlEsPDFOrdner.add(txtEsPDFDatei);
		
		JButton btnEsPDFDatei = new JButton("Datei w\u00E4hlen");
		btnEsPDFDatei.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				//FilenameFilter filter = 
				new FilenameFilter() {
			        public boolean accept(File directory, String fileName) {
			            return fileName.endsWith(".pdf");
			        }
		        };
				
				File pdfFile = SwingUtil.getFileChoice(new JDialog(), txtEsPDFDatei.getText(), null, "PDF - Datei");
				if(pdfFile!=null){
					txtEsPDFDatei.setText(pdfFile.toString());
					svtool.setPDFOrdner(dbDienste.checkFile(pdfFile));
				}
			}
		});
		btnEsPDFDatei.setBounds(10, 60, 150, 19);
		pnlEsPDFOrdner.add(btnEsPDFDatei);
		
		JPanel pnlImport = new JPanel();
		pnlImport.setBounds(10, 45, 764, 495);
		frmSvausweise.getContentPane().add(pnlImport);
		pnlImport.setLayout(null);
		pnlImport.setVisible(false);
		dbDienste.addMenuePanel(pnlImport);
		
		JLabel lblImport = new JLabel("Import");
		lblImport.setBounds(659, 38, 64, 43);
		pnlImport.add(lblImport);
		
		JPanel pnlImportFileChooser = new JPanel();
		pnlImportFileChooser.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlImportFileChooser.setBounds(0, 0, 417, 495);
		pnlImport.add(pnlImportFileChooser);
		pnlImportFileChooser.setLayout(null);
		
		JFileChooser importFileChooser = new JFileChooser();
		importFileChooser.setApproveButtonText("Import");
		importFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		importFileChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				JFileChooser theFileChooser = (JFileChooser) actionEvent.getSource();
				String command = actionEvent.getActionCommand();
				
				if (command.equals(JFileChooser.APPROVE_SELECTION)) {
					File selectedFile = theFileChooser.getSelectedFile();
					svtool.sqlImportBackup(selectedFile);
				}
				else if (command.equals(JFileChooser.CANCEL_SELECTION)) {
					theFileChooser.setCurrentDirectory(svtool.getImportOrdner());
				}
			}
		});
		importFileChooser.setCurrentDirectory(svtool.getImportOrdner());
		importFileChooser.setBounds(10, 10, 397, 475);
		pnlImportFileChooser.add(importFileChooser);
		
		JPanel pnlBilder = new JPanel();
		pnlBilder.setBounds(10, 45, 764, 495);
		frmSvausweise.getContentPane().add(pnlBilder);
		pnlBilder.setLayout(null);
		pnlBilder.setVisible(false);
		dbDienste.addMenuePanel(pnlBilder);
		
		JPanel pnlImgDirectory = new JPanel();
		pnlImgDirectory.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlImgDirectory.setBounds(0, 0, 220, 495);
		pnlBilder.add(pnlImgDirectory);
		pnlImgDirectory.setLayout(null);
		
		JPanel pnlImgBearbeiten = new JPanel();
		pnlImgBearbeiten.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlImgBearbeiten.setBounds(225, 0, 540, 495);
		pnlBilder.add(pnlImgBearbeiten);
		pnlImgBearbeiten.setLayout(null);
		
		JPanel pnlImgBild = new JPanel();
		pnlImgBild.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlImgBild.setBounds(10, 10, 370, 475);
		pnlImgBearbeiten.add(pnlImgBild);
		pnlImgBild.setLayout(null);
		
		pnlImgWork = new DragPanel(this);
		pnlImgWork.setBounds(10, 10, 350, 450);
		pnlImgBild.add(pnlImgWork);
		
		JPanel pnlImgZuweisen = new JPanel();
		pnlImgZuweisen.setBounds(390, 10, 150, 475);
		pnlImgBearbeiten.add(pnlImgZuweisen);
		pnlImgZuweisen.setLayout(null);
		
		lblImgNewImage = new JLabel("");
		lblImgNewImage.setBounds(23, 10, 105, 135);
		pnlImgZuweisen.add(lblImgNewImage);
		
		JComboBox<String> cboImgKlasse = new JComboBox<String>();
		cboImgKlasse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(cboImgKlasse.getSelectedItem()!=null){
            		String klasse = cboImgKlasse.getSelectedItem().toString();
                	if(!klasse.equals("")){
                		String sqlQuery = "SELECT * FROM sv_schueler WHERE klasse='"+klasse+"'";
                        JTable t = dbDienste.resultSetToTableZuweisen(sqlQuery);
                        scrollPaneZuweisen.setViewportView(t);
                	}
            	}
            }
        });
		cboImgKlasse.setBounds(10, 155, 130, 19);
		pnlImgZuweisen.add(cboImgKlasse);
		
		scrollPaneZuweisen = new JScrollPane();
		scrollPaneZuweisen.setViewportBorder(null);
		scrollPaneZuweisen.setBounds(10, 185, 130, 230);
		pnlImgZuweisen.add(scrollPaneZuweisen);
		
		JTable tblImgSuS = new JTable();
		tblImgSuS.setBounds(0, 0, 0, 0);
		scrollPaneZuweisen.add(tblImgSuS);
		
		JButton btnImgZuweisen = new JButton("Zuweisen");
		btnImgZuweisen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTable tbl = (JTable)scrollPaneZuweisen.getViewport().getComponent(0);
				int idIndex = (int)tbl.getModel().getValueAt(tbl.getSelectedRow(),tbl.getColumn("id").getModelIndex());
				svtool.updateSqlImg(idIndex, imgDbCut);
			}
		});
		btnImgZuweisen.setBounds(10, 426, 130, 38);
		pnlImgZuweisen.add(btnImgZuweisen);
		
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				JFileChooser theFileChooser = (JFileChooser) actionEvent.getSource();
				String command = actionEvent.getActionCommand();
				
				if (command.equals(JFileChooser.APPROVE_SELECTION)) {
					File selectedFile = theFileChooser.getSelectedFile();
					BufferedImage image;
					try {
						image = ImageIO.read(new File(selectedFile.getParent()+"\\"+selectedFile.getName()));
						BufferedImage image1 = new ImageScaler().scaleImage(image, new Dimension(350,450));
						pnlImgWork.setImage(image1);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if (command.equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
					File selectedFile = theFileChooser.getSelectedFile();
					BufferedImage image;
					try {
						image = ImageIO.read(new File(selectedFile.getParent()+"\\"+selectedFile.getName()));
						BufferedImage image1 = new ImageScaler().scaleImage(image, new Dimension(350,450));
						pnlImgWork.setImage(image1);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//Image image = Toolkit.getDefaultToolkit().getImage(selectedFile.getParent()+"\\"+selectedFile.getName());
					//Image image1 = new ImageScaler().scaleImage(image, new Dimension(210,270));
					
				}
				else if (command.equals(JFileChooser.CANCEL_SELECTION)) {
					theFileChooser.setCurrentDirectory(svtool.getImportOrdner());
				}
			}
		});
		fileChooser.setCurrentDirectory(svtool.getBilderOrdner());
		fileChooser.setBounds(0, 10, 220, 475);
		pnlImgDirectory.add(fileChooser);
		
		JPanel pnlPDF = new JPanel();
		pnlPDF.setBounds(10, 45, 764, 495);
		frmSvausweise.getContentPane().add(pnlPDF);
		pnlPDF.setLayout(null);
		pnlPDF.setVisible(false);
		dbDienste.addMenuePanel(pnlPDF);
		
		JPanel pnlPDFViewer = new JPanel();
		pnlPDFViewer.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlPDFViewer.setBounds(-3, 0, 770, 470);
		pnlPDF.add(pnlPDFViewer);
		pnlPDFViewer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel pnlExport = new JPanel();
		pnlExport.setBounds(10, 45, 764, 495);
		frmSvausweise.getContentPane().add(pnlExport);
		pnlExport.setLayout(null);
		pnlExport.setVisible(false);
		dbDienste.addMenuePanel(pnlExport);
		
		JLabel lblExport = new JLabel("Export");
		lblExport.setBounds(270, 74, 46, 14);
		pnlExport.add(lblExport);
		
		JPanel pnlExportFileChooser = new JPanel();
		pnlExportFileChooser.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlExportFileChooser.setBounds(0, 0, 417, 495);
		pnlExport.add(pnlExportFileChooser);
		pnlExportFileChooser.setLayout(null);
		
		JFileChooser exportFileChooser = new JFileChooser();
		exportFileChooser.setApproveButtonText("Export");
		exportFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		exportFileChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				JFileChooser theFileChooser = (JFileChooser) actionEvent.getSource();
				String command = actionEvent.getActionCommand();
				
				if (command.equals(JFileChooser.APPROVE_SELECTION)) {
					File selectedFile = theFileChooser.getSelectedFile();
					svtool.sqlDump(selectedFile);
				}
				else if (command.equals(JFileChooser.CANCEL_SELECTION)) {
					theFileChooser.setCurrentDirectory(svtool.getImportOrdner());
				}
			}
		});
		exportFileChooser.setCurrentDirectory(svtool.getExportOrdner());
		exportFileChooser.setBounds(10, 10, 397, 475);
		pnlExportFileChooser.add(exportFileChooser);
		
		JPanel pnlListe = new JPanel();
		pnlListe.setBounds(10, 45, 764, 495);
		frmSvausweise.getContentPane().add(pnlListe);
		pnlListe.setLayout(null);
		pnlListe.setVisible(false);
		
		JPanel pnlListMenue = new JPanel();
		pnlListMenue.setBounds(0, 0, 764, 34);
		pnlListe.add(pnlListMenue);
		pnlListMenue.setLayout(null);
		
		JButton btnListAlle = new JButton("A");
		btnListAlle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sqlQuery = "SELECT * FROM sv_schueler WHERE geloescht=0";
				JTable t = dbDienste.resultSetToTable(sqlQuery);
				scrollPane.setViewportView(t);
			}
		});
		btnListAlle.setBounds(100, 0, 69, 34);
		pnlListMenue.add(btnListAlle);
		
		JButton btnListSelektiert = new JButton("S");
		btnListSelektiert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sqlQuery = "SELECT * FROM sv_schueler WHERE selektiert=1";
				JTable t = dbDienste.resultSetToTable(sqlQuery);
				scrollPane.setViewportView(t);
			}
		});
		btnListSelektiert.setBounds(0, 0, 69, 34);
		pnlListMenue.add(btnListSelektiert);
		
		cboListKlasse = new JComboBox<String>();
		cboListKlasse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(cboListKlasse.getSelectedItem()!=null){
            		String klasse = cboListKlasse.getSelectedItem().toString();
            		if(!klasse.equals("")){
                		String sqlQuery = "SELECT * FROM sv_schueler WHERE klasse='"+klasse+"'";
                        JTable t = dbDienste.resultSetToTable(sqlQuery);
        				scrollPane.setViewportView(t);
                	}
            	}
            }
        });
		cboListKlasse.setBounds(175, 0, 69, 34);
		pnlListMenue.add(cboListKlasse);
		
		txtSuche = new JTextField();
		txtSuche.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				String suchText = dbDienste.suchText(txtSuche.getText(),arg0);
				String sqlQuery = "SELECT * FROM sv_schueler WHERE name LIKE '%"+suchText+"%' OR vorname LIKE '%"+suchText+"%'";
				JTable t = dbDienste.resultSetToTable(sqlQuery);
				scrollPane.setViewportView(t);
			}
		});
		txtSuche.setText("");
		txtSuche.setBounds(250, 0, 139, 34);
		pnlListMenue.add(txtSuche);
		txtSuche.setColumns(10);
		
		JPanel pnlWaehlen = new JPanel();
		pnlWaehlen.setBounds(450, 0, 305, 34);
		pnlListMenue.add(pnlWaehlen);
		pnlWaehlen.setLayout(null);
		
		JButton btnAuswaehlen = new JButton("Alle ausw\u00E4hlen");
		btnAuswaehlen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dbDienste.alleAuswaehlen((JTable)scrollPane.getViewport().getComponent(0));
			}
		});
		btnAuswaehlen.setBounds(0, 0, 150, 34);
		pnlWaehlen.add(btnAuswaehlen);
		
		JButton btnAbwaehlen = new JButton("Alle abw\u00E4hlen");
		btnAbwaehlen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbDienste.alleAbwaehlen((JTable)scrollPane.getViewport().getComponent(0));
			}
		});
		btnAbwaehlen.setBounds(155, 0, 150, 34);
		pnlWaehlen.add(btnAbwaehlen);
		
		pnlListTable = new JPanel();
		pnlListTable.setBounds(10, 44, 500, 440);
		pnlListe.add(pnlListTable);
		pnlListTable.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(0, 0, 500, 440);
		pnlListTable.add(scrollPane);
		dbDienste.addMenuePanel(pnlListe);
		
		JPanel pnlListInfo = new JPanel();
		pnlListInfo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlListInfo.setBounds(520, 45, 234, 439);
		pnlListe.add(pnlListInfo);
		pnlListInfo.setLayout(null);
		
		JLabel lblInfoId = new JLabel("ID");
		lblInfoId.setBounds(10, 10, 80, 19);
		pnlListInfo.add(lblInfoId);
		
		JLabel lblInfoSchuelerId = new JLabel("Sch\u00FCler ID");
		lblInfoSchuelerId.setBounds(10, 30, 80, 19);
		pnlListInfo.add(lblInfoSchuelerId);
		
		JLabel lblInfoNameVorname = new JLabel("Name, Vorname");
		lblInfoNameVorname.setBounds(10, 50, 80, 19);
		pnlListInfo.add(lblInfoNameVorname);
		
		JLabel lblInfoGebDatum = new JLabel("Geburtsdatum");
		lblInfoGebDatum.setBounds(10, 70, 80, 19);
		pnlListInfo.add(lblInfoGebDatum);
		
		JLabel lblInfoGeschlecht = new JLabel("Geschlecht");
		lblInfoGeschlecht.setBounds(10, 90, 80, 19);
		pnlListInfo.add(lblInfoGeschlecht);
		
		JLabel lblInfoKlasse = new JLabel("Klasse");
		lblInfoKlasse.setBounds(10, 110, 80, 19);
		pnlListInfo.add(lblInfoKlasse);
		
		txtInfoId = new JTextField();
		txtInfoId.setBounds(100, 10, 125, 19);
		pnlListInfo.add(txtInfoId);
		txtInfoId.setColumns(10);
		
		txtInfoSchuelerId = new JTextField();
		txtInfoSchuelerId.setBounds(100, 30, 125, 19);
		pnlListInfo.add(txtInfoSchuelerId);
		txtInfoSchuelerId.setColumns(10);
		
		txtInfoNameVorname = new JTextField();
		txtInfoNameVorname.setBounds(100, 50, 125, 19);
		pnlListInfo.add(txtInfoNameVorname);
		txtInfoNameVorname.setColumns(10);
		
		txtInfoGebDatum = new JTextField();
		txtInfoGebDatum.setBounds(100, 70, 125, 19);
		pnlListInfo.add(txtInfoGebDatum);
		txtInfoGebDatum.setColumns(10);
		
		txtInfoGeschlecht = new JTextField();
		txtInfoGeschlecht.setBounds(100, 90, 125, 19);
		pnlListInfo.add(txtInfoGeschlecht);
		txtInfoGeschlecht.setColumns(10);
		
		txtInfoKlasse = new JTextField();
		txtInfoKlasse.setBounds(100, 110, 125, 19);
		pnlListInfo.add(txtInfoKlasse);
		txtInfoKlasse.setColumns(10);
		
		lblInfoImage = new JLabel("");
		lblInfoImage.setBounds(10, 142, 215, 286);
		pnlListInfo.add(lblInfoImage);
		
		JPanel pnl_buttonMenue = new JPanel();
		pnl_buttonMenue.setBounds(0, 0, 571, 34);
		frmSvausweise.getContentPane().add(pnl_buttonMenue);
		pnl_buttonMenue.setLayout(null);
		
		btnListe = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("sv_list.png"))));
		btnListe.setBounds(0, 0, 34, 34);
		pnl_buttonMenue.add(btnListe);
		btnListe.setToolTipText("Sch\u00FClerliste");
		dbDienste.addMenuButton(btnListe);
		btnListe.setEnabled(false);
		
		btnBilder = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("sv_userfoto.png"))));
		btnBilder.setBounds(35, 0, 34, 34);
		pnl_buttonMenue.add(btnBilder);
		btnBilder.setToolTipText("Bildbearbeitung");
		dbDienste.addMenuButton(btnBilder);
		btnBilder.setEnabled(false);
		
		btnImport = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("sv_import.png"))));
		btnImport.setBounds(70, 0, 34, 34);
		pnl_buttonMenue.add(btnImport);
		btnImport.setToolTipText("SQL Import");
		dbDienste.addMenuButton(btnImport);
		btnImport.setEnabled(false);
		
		btnPDF = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("sv_pdf.png"))));
		btnPDF.setBounds(140, 0, 34, 34);
		pnl_buttonMenue.add(btnPDF);
		btnPDF.setToolTipText("PDF erstellen");
		dbDienste.addMenuButton(btnPDF);
		btnPDF.setEnabled(false);
		
		JButton btnDB = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("sv_db.png"))));
		btnDB.setBounds(175, 0, 34, 34);
		pnl_buttonMenue.add(btnDB);
		btnDB.setToolTipText("Datenbank Optionen");
		
		JButton btnEinstellungen = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("sv_einstellungen.png"))));
		btnEinstellungen.setToolTipText("Einstellungen");
		btnEinstellungen.setBounds(225, 0, 34, 34);
		pnl_buttonMenue.add(btnEinstellungen);
		
		JButton btnExport = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("sv_export.png"))));
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dbDienste.setVisibleMenuePanel(pnlExport);
			}
		});
		btnExport.setBounds(105, 0, 34, 34);
		pnl_buttonMenue.add(btnExport);
		btnExport.setToolTipText("SQL Export");
		dbDienste.addMenuButton(btnExport);
		btnExport.setEnabled(false);
		
		JButton btnCSV = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("sv_xls.png"))));
		btnCSV.setToolTipText("SuS Import aus Schild");
		btnCSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbDienste.setVisibleMenuePanel(pnlCSV);
			}
		});
		btnCSV.setBounds(300, 0, 34, 34);
		pnl_buttonMenue.add(btnCSV);
		dbDienste.addMenuButton(btnCSV);
		btnCSV.setEnabled(false);
		
		btnEinstellungen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Einstellungen est = svtool.getEinstellungen();
				txtEsDbIp.setText(est.getDbIp());
				txtEsDbPort.setText(""+est.getDbPort());
				txtEsDbName.setText(est.getDbName());
				txtEsDbUser.setText(est.getDbUser());
				txtEsDbPassword.setText(est.getDbPassword());
				dbDienste.setVisibleMenuePanel(pnlEinstellungen);
			}
		});
		btnDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbDienste.setVisibleMenuePanel(pnlDB);
				dbDienste.setBtnDbConnect(btnDbConnect, pnlDbTableAuswahl, cboDbTable);
				txtDbName.setText(svtool.getDbName());
			}
		});
		btnPDF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbDienste.setVisibleMenuePanel(pnlPDF);
				PDFDienste pdfDienst = new PDFDienste(svtool.getPDFFile(),svtool.sqlQuery("SELECT schueler_id, name, vorname, gebdatum, klasse, selektiert, bild AS foto, "
						+ "																			nrw_logo AS nrwlogo, ad_logo AS adlogo, no_foto AS nofoto "
						+ "																			FROM sv_schueler, sv_config WHERE selektiert=1"));
				pdfDienst.makePDF(svtool.getPDFFile());
				pdfDienst.viewPDF(svtool.getPDFFile(), pnlPDFViewer);
			}
		});
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbDienste.setVisibleMenuePanel(pnlImport);
			}
		});
		btnBilder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbDienste.setVisibleMenuePanel(pnlBilder);
				dbDienste.fuelleCombobox(cboImgKlasse, "SELECT klasse FROM sv_schueler GROUP BY klasse ORDER BY klasse");
			}
		});
		btnListe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dbDienste.fuelleCombobox(cboListKlasse, "SELECT klasse FROM sv_schueler GROUP BY klasse ORDER BY klasse");
				dbDienste.setVisibleMenuePanel(pnlListe);
			}
		});
	}
	
	public void updateTable(String sqlQuery){
		JTable t = dbDienste.resultSetToTable(sqlQuery);
		scrollPane.setViewportView(t);
	}

	public void setTxtInfoId(int id) {
		this.txtInfoId.setText(""+id);
	}

	public void setTxtInfoSchuelerId(int susId) {
		this.txtInfoSchuelerId.setText(""+susId);
	}

	public void setTxtInfoNameVorname(String name, String vorname) {
		this.txtInfoNameVorname.setText(name+", "+vorname);
	}

	public void setTxtInfoGebDatum(String gebDatum) {
		this.txtInfoGebDatum.setText(gebDatum);
	}

	public void setTxtInfoGeschlecht(String geschlecht) {
		this.txtInfoGeschlecht.setText(geschlecht);
	}

	public void setTxtInfoKlasse(String klasse) {
		this.txtInfoKlasse.setText(klasse);
	}

	public JLabel getLblInfoImage() {
		return lblInfoImage;
	}

	public JLabel getLblImgNewImage() {
		return lblImgNewImage;
	}

	public void setLblImgNewImage(JLabel lblImgNewImage) {
		this.lblImgNewImage = lblImgNewImage;
	}

	public void setImgDbCut(BufferedImage imgDbCut) {
		this.imgDbCut = imgDbCut;
	}
}

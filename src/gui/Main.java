package gui;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;

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

import javax.swing.JScrollPane;

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
	private JComboBox cboDbTable;
	private JComboBox cboListKlasse;
	private JPanel pnlDbTableAuswahl;
	private JButton btnListe;
	private JButton btnBilder;
	private JButton btnDisk;
	private JButton btnPDF;
	private JTextField txtSuche;
	private JPanel pnlListTable;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField txtInfoId;
	private JTextField txtInfoSchuelerId;
	private JTextField txtInfoNameVorname;
	private JTextField txtInfoGebDatum;
	private JTextField txtInfoGeschlecht;
	private JTextField txtInfoKlasse;
	private JLabel lblInfoImage;
	

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
		frmSvausweise.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("../IMG/sv_ad.png")));
		frmSvausweise.setTitle("SV-Ausweise 0.1");
		frmSvausweise.setBounds(100, 100, 800, 587);
		frmSvausweise.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSvausweise.getContentPane().setLayout(null);
		
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
		
		cboListKlasse = new JComboBox();
		cboListKlasse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String klasse = cboListKlasse.getSelectedItem().toString();
            	if(!klasse.equals("")){
            		String sqlQuery = "SELECT * FROM sv_schueler WHERE klasse='"+klasse+"'";
                    JTable t = dbDienste.resultSetToTable(sqlQuery);
    				scrollPane.setViewportView(t);
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
		
		cboDbTable = new JComboBox();
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
		
		JPanel pnlBilder = new JPanel();
		pnlBilder.setBounds(10, 45, 764, 495);
		frmSvausweise.getContentPane().add(pnlBilder);
		pnlBilder.setLayout(null);
		pnlBilder.setVisible(false);
		
		JPanel pnlDisk = new JPanel();
		pnlDisk.setBounds(10, 45, 764, 495);
		frmSvausweise.getContentPane().add(pnlDisk);
		pnlDisk.setLayout(null);
		pnlDisk.setVisible(false);
		
		JPanel pnlPDF = new JPanel();
		pnlPDF.setBounds(10, 45, 764, 495);
		frmSvausweise.getContentPane().add(pnlPDF);
		pnlPDF.setLayout(null);
		pnlPDF.setVisible(false);
		
		JPanel pnl_buttonMenue = new JPanel();
		pnl_buttonMenue.setBounds(0, 0, 784, 34);
		frmSvausweise.getContentPane().add(pnl_buttonMenue);
		pnl_buttonMenue.setLayout(null);
		
		btnListe = new JButton(new ImageIcon(Main.class.getResource("../IMG/sv_list.png")));
		btnListe.setBounds(0, 0, 34, 34);
		pnl_buttonMenue.add(btnListe);
		btnListe.setToolTipText("Sch\u00FClerliste");
		dbDienste.addMenuButton(btnListe);
		btnListe.setEnabled(false);
		
		btnBilder = new JButton(new ImageIcon(Main.class.getResource("../IMG/sv_userfoto.png")));
		btnBilder.setBounds(35, 0, 34, 34);
		pnl_buttonMenue.add(btnBilder);
		btnBilder.setToolTipText("Bildbearbeitung");
		dbDienste.addMenuButton(btnBilder);
		btnBilder.setEnabled(false);
		
		btnDisk = new JButton(new ImageIcon(Main.class.getResource("../IMG/sv_disk.png")));
		btnDisk.setBounds(70, 0, 34, 34);
		pnl_buttonMenue.add(btnDisk);
		btnDisk.setToolTipText("Laden/Speichern");
		dbDienste.addMenuButton(btnDisk);
		btnDisk.setEnabled(false);
		
		btnPDF = new JButton(new ImageIcon(Main.class.getResource("../IMG/sv_pdf.png")));
		btnPDF.setBounds(105, 0, 34, 34);
		pnl_buttonMenue.add(btnPDF);
		btnPDF.setToolTipText("PDF erstellen");
		dbDienste.addMenuButton(btnPDF);
		btnPDF.setEnabled(false);
		
		JButton btnDB = new JButton(new ImageIcon(Main.class.getResource("../IMG/sv_db.png")));
		btnDB.setBounds(140, 0, 34, 34);
		pnl_buttonMenue.add(btnDB);
		btnDB.setToolTipText("Datenbank Optionen");
		
		JButton btnEinstellungen = new JButton(new ImageIcon(Main.class.getResource("../IMG/sv_einstellungen.png")));
		btnEinstellungen.setBounds(190, 0, 34, 34);
		pnl_buttonMenue.add(btnEinstellungen);
		
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
				System.out.println("Nele");
			}
		});
		btnDisk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Mama");
			}
		});
		btnBilder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Papa");
			}
		});
		btnListe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dbDienste.fuelleCombobox(cboListKlasse, "SELECT klasse FROM sv_schueler GROUP BY klasse ORDER BY klasse");
				dbDienste.setVisibleMenuePanel(pnlListe);
				System.out.println("Rahel");
			}
		});
		dbDienste.addMenuePanel(pnlBilder);
		dbDienste.addMenuePanel(pnlDisk);
		dbDienste.addMenuePanel(pnlPDF);
		dbDienste.addMenuePanel(pnlDB);
		dbDienste.addMenuePanel(pnlEinstellungen);
		dbDienste.addMenuePanel(pnlListe);
		
		table = new JTable();
		scrollPane.add(table);
		
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
}

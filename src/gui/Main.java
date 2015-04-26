package gui;

import java.awt.EventQueue;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;

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

import java.awt.Color;

import javax.swing.JComboBox;

public class Main {

	private JFrame frmSvausweise;
	private JTextField txtEsDbIp;
	private JTextField txtEsDbPort;
	private JTextField txtEsDbUser;
	private JTextField txtEsDbPassword;
	
	private LinkedList allMenuePanels;
	private SVTool svtool;
	private DBDienste dbDienste;
	private JTextField txtEsDbName;
	private JTextField txtDbName;
	private JButton btnDbConnect;
	private JComboBox cboDbTable;
	private JPanel pnlDbTableAuswahl;
	private JButton btnListe;
	private JButton btnBilder;
	private JButton btnDisk;
	private JButton btnPDF;
	

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
		dbDienste = new DBDienste();
		allMenuePanels = new LinkedList();
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
		frmSvausweise.setBounds(100, 100, 800, 590);
		frmSvausweise.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSvausweise.getContentPane().setLayout(null);
		
		JPanel pnlDB = new JPanel();
		pnlDB.setBounds(10, 45, 764, 495);
		frmSvausweise.getContentPane().add(pnlDB);
		pnlDB.setLayout(null);
		pnlDB.setVisible(false);
		allMenuePanels.add(pnlDB);
		
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
				setBtnDbConnectColor();
				setEnableButton();
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
		
		allMenuePanels.add(pnlEinstellungen);
		
		JPanel pnlListe = new JPanel();
		pnlListe.setBounds(10, 45, 764, 495);
		frmSvausweise.getContentPane().add(pnlListe);
		pnlListe.setLayout(null);
		pnlListe.setVisible(false);
		
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
		btnListe.setEnabled(false);
		
		btnBilder = new JButton(new ImageIcon(Main.class.getResource("../IMG/sv_userfoto.png")));
		btnBilder.setBounds(35, 0, 34, 34);
		pnl_buttonMenue.add(btnBilder);
		btnBilder.setToolTipText("Bildbearbeitung");
		btnBilder.setEnabled(false);
		
		btnDisk = new JButton(new ImageIcon(Main.class.getResource("../IMG/sv_disk.png")));
		btnDisk.setBounds(70, 0, 34, 34);
		pnl_buttonMenue.add(btnDisk);
		btnDisk.setToolTipText("Laden/Speichern");
		btnDisk.setEnabled(false);
		
		btnPDF = new JButton(new ImageIcon(Main.class.getResource("../IMG/sv_pdf.png")));
		btnPDF.setBounds(105, 0, 34, 34);
		pnl_buttonMenue.add(btnPDF);
		btnPDF.setToolTipText("PDF erstellen");
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
				setVisiblePanel(pnlEinstellungen);
			}
		});
		btnDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisiblePanel(pnlDB);
				setBtnDbConnectColor();
				txtDbName.setText(svtool.getDbName());
			}
		});
		btnPDF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisiblePanel(pnlPDF);
				System.out.println("Nele");
			}
		});
		btnDisk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisiblePanel(pnlDisk);
				System.out.println("Mama");
			}
		});
		btnBilder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisiblePanel(pnlBilder);
				System.out.println("Papa");
			}
		});
		btnListe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisiblePanel(pnlListe);
				System.out.println("Rahel");
			}
		});
		allMenuePanels.add(pnlListe);
		allMenuePanels.add(pnlBilder);
		allMenuePanels.add(pnlDisk);
		allMenuePanels.add(pnlPDF);
	}
	/*
	private ArrayList setCboDbTable()
	{
		ResultSet resultSet = svtool.sqlQuery("SHOW TABLES");
		return dbDienste.convertResultSetToArrayList(resultSet);
	}
	*/
	private void setBtnDbConnectColor()
	{
		if(svtool.isDbConnected())
		{
			btnDbConnect.setText("Connect");
			btnDbConnect.setForeground(new Color(0, 128, 0));
			pnlDbTableAuswahl.setVisible(true);
			setcboDbTable();
		}
		else
		{
			btnDbConnect.setText("Disconnect");
			btnDbConnect.setForeground(Color.RED);
			pnlDbTableAuswahl.setVisible(false);
		}
	}
	
	private void setcboDbTable()
	{
		cboDbTable.removeAllItems();
		ResultSet rs = svtool.sqlQuery("SHOW TABLES");
		try {
			while(rs.next()){
				cboDbTable.addItem(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cboDbTable.setSelectedItem(svtool.getDbTable());
	}
	
	private void setEnableButton()
	{
		boolean btnEnable = svtool.isDbConnected();
		btnListe.setEnabled(btnEnable);
		btnBilder.setEnabled(btnEnable);
		btnDisk.setEnabled(btnEnable);
		btnPDF.setEnabled(btnEnable);
	}
	
	private void setVisiblePanel(JPanel activatePanel)
	{
		Iterator iter = allMenuePanels.listIterator();
		while(iter.hasNext())
		{
			JPanel menuePanel = (JPanel)iter.next();
			menuePanel.setVisible(false);
		}
		activatePanel.setVisible(true);
	}
}

package sv.Gui;

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
		
		JPanel pnlAusweise = new JPanel();
		tabMenue.addTab("Ausweise erstellen", null, pnlAusweise, null);
		pnlAusweise.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlSuSMenue = new JPanel();
		pnlAusweise.add(pnlSuSMenue, BorderLayout.NORTH);
		pnlSuSMenue.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JButton btnSuSAlle = new JButton("Alle");
		btnSuSAlle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		pnlSuSMenue.add(btnSuSAlle);
		
		JButton btnSuSSelektiert = new JButton("Selektierte");
		btnSuSSelektiert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		pnlSuSMenue.add(btnSuSSelektiert);
		
		JComboBox cboSuSKlasse = new JComboBox();
		cboSuSKlasse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		pnlSuSMenue.add(cboSuSKlasse);
		
		txtSuSSearchname = new JTextField();
		txtSuSSearchname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		pnlSuSMenue.add(txtSuSSearchname);
		txtSuSSearchname.setColumns(10);
		
		JButton btnSuSAusgewaehlteAuswaehlen = new JButton("Gelistete markieren");
		btnSuSAusgewaehlteAuswaehlen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		pnlSuSMenue.add(btnSuSAusgewaehlteAuswaehlen);
		
		JButton btnSuSAusgewaehlteAbwaehlen = new JButton("Gelistete abwählen");
		btnSuSAusgewaehlteAbwaehlen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		pnlSuSMenue.add(btnSuSAusgewaehlteAbwaehlen);
		
		JPanel pnlSuSInfo = new JPanel();
		pnlAusweise.add(pnlSuSInfo, BorderLayout.EAST);
		
		JPanel pnlSuSTable = new JPanel();
		pnlAusweise.add(pnlSuSTable, BorderLayout.CENTER);
		
		JPanel pnlSuSErstellen = new JPanel();
		pnlAusweise.add(pnlSuSErstellen, BorderLayout.SOUTH);
		pnlSuSErstellen.setLayout(new BorderLayout(0, 0));
		
		JButton btnPdfErstellen = new JButton("PDF erstellen");
		pnlSuSErstellen.add(btnPdfErstellen, BorderLayout.CENTER);
		
		JPanel pnlBilder = new JPanel();
		tabMenue.addTab("Bilder zuweisen", null, pnlBilder, null);
		pnlBilder.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlBilderName = new JPanel();
		pnlBilder.add(pnlBilderName, BorderLayout.WEST);
		//pnlBilderName.setLayout(new BoxLayout(pnlBilderName, BoxLayout.Y_AXIS));
		pnlBilderName.setLayout(new VerticalLayout());
		
		JLabel lblNewLabel = new JLabel("SuS Name");
		pnlBilderName.add(lblNewLabel);
		
		txtBilderNamesSuche = new JTextField();
		pnlBilderName.add(txtBilderNamesSuche);
		txtBilderNamesSuche.setColumns(10);
		
		JButton btnSuchen = new JButton("Suchen");
		pnlBilderName.add(btnSuchen);
		
		JPanel pnlBilderDirectory = new JPanel();
		pnlBilder.add(pnlBilderDirectory, BorderLayout.CENTER);
		pnlBilderDirectory.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("Bilder Directory suchen");
		pnlBilderDirectory.add(lblNewLabel_1);
		
		JPanel pnlBilderFoto = new JPanel();
		pnlBilder.add(pnlBilderFoto, BorderLayout.EAST);
		pnlBilderFoto.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("Foto Infos");
		pnlBilderFoto.add(lblNewLabel_2);
		
		JPanel pnlSchild = new JPanel();
		tabMenue.addTab("Schülerdaten aktualisieren", null, pnlSchild, null);
		
		JPanel pnlImportExport = new JPanel();
		tabMenue.addTab("Daten-Backup", null, pnlImportExport, null);
		
		JPanel pnlEinstellungen = new JPanel();
		tabMenue.addTab("Einstellungen", null, pnlEinstellungen, null);
		
	}

}

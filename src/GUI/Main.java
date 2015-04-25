package GUI;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class Main {

	private JFrame frmSvausweise;

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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize(){
		frmSvausweise = new JFrame("N");
		frmSvausweise.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("../IMG/sv_ad.png")));
		frmSvausweise.setTitle("SV-Ausweise 0.1");
		frmSvausweise.setBounds(100, 100, 800, 590);
		frmSvausweise.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSvausweise.getContentPane().setLayout(null);
		
		JButton btnListe = new JButton(new ImageIcon(Main.class.getResource("../IMG/sv_list.png")));
		btnListe.setToolTipText("Sch\u00FClerliste");
		btnListe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Rahel");
			}
		});
		btnListe.setBounds(0, 0, 34, 34);
		frmSvausweise.getContentPane().add(btnListe);
		
		JButton btnNewButton = new JButton(new ImageIcon(Main.class.getResource("../IMG/sv_userfoto.png")));
		btnNewButton.setToolTipText("Bildbearbeitung");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Papa");
			}
		});
		btnNewButton.setBounds(35, 0, 34, 34);
		frmSvausweise.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton(new ImageIcon(Main.class.getResource("../IMG/sv_disk.png")));
		btnNewButton_1.setToolTipText("Laden/Speichern");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Mama");
			}
		});
		btnNewButton_1.setBounds(70, 0, 34, 34);
		frmSvausweise.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton(new ImageIcon(Main.class.getResource("../IMG/sv_pdf.png")));
		btnNewButton_2.setToolTipText("PDF erstellen");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Nele");
			}
		});
		btnNewButton_2.setBounds(105, 0, 34, 34);
		frmSvausweise.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton(new ImageIcon(Main.class.getResource("../IMG/sv_db.png")));
		btnNewButton_3.setToolTipText("Datenbank Optionen");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Elia");
			}
		});
		btnNewButton_3.setBounds(140, 0, 34, 34);
		frmSvausweise.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton(new ImageIcon(Main.class.getResource("../IMG/sv_einstellungen.png")));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Kevin");
			}
		});
		btnNewButton_4.setBounds(190, 0, 34, 34);
		frmSvausweise.getContentPane().add(btnNewButton_4);
	}
}

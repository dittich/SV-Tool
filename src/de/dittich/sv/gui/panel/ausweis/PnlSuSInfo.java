package de.dittich.sv.gui.panel.ausweis;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;

public class PnlSuSInfo extends JPanel {
	private JTextField txtId;
	private JTextField txtSuSId;
	private JTextField txtName;
	private JTextField txtGeb;
	private JTextField txtGeschlecht;
	private JTextField txtKlasse;

	/**
	 * Create the panel.
	 */
	public PnlSuSInfo() {
		setLayout(new GridLayout(0, 1, 5, 5));
		
		JPanel pnlId = new JPanel();
		pnlId.setLayout(new GridLayout(0, 2));
		JLabel lblId = new JLabel("ID");
		pnlId.add(lblId);
		txtId = new JTextField();
		txtId.setColumns(10);
		pnlId.add(txtId);
		add(pnlId);
		
		JPanel pnlSuSId = new JPanel();
		pnlSuSId.setLayout(new GridLayout(0, 2));
		JLabel lblSuSId = new JLabel("Sch\u00FCler ID");
		pnlSuSId.add(lblSuSId);
		txtSuSId = new JTextField();
		txtSuSId.setColumns(10);
		pnlSuSId.add(txtSuSId);
		add(pnlSuSId);
		
		JPanel pnlName = new JPanel();
		pnlName.setLayout(new GridLayout(0, 2));
		JLabel lblName = new JLabel("Name, Vorname");
		pnlName.add(lblName);
		txtName = new JTextField();
		txtName.setColumns(10);
		pnlName.add(txtName);
		add(pnlName);
		
		JPanel pnlGeb = new JPanel();
		pnlGeb.setLayout(new GridLayout(0, 2));
		JLabel lblGeb = new JLabel("Geburtsdatum");
		pnlGeb.add(lblGeb);
		txtGeb = new JTextField();
		txtGeb.setColumns(10);
		pnlGeb.add(txtGeb);
		add(pnlGeb);
		
		JPanel pnlGeschlecht = new JPanel();
		pnlGeschlecht.setLayout(new GridLayout(0, 2));
		JLabel lblGeschlecht = new JLabel("Geschlecht");
		pnlGeschlecht.add(lblGeschlecht);
		txtGeschlecht = new JTextField();
		txtGeschlecht.setColumns(10);
		pnlGeschlecht.add(txtGeschlecht);
		add(pnlGeschlecht);
		
		JPanel pnlKlasse = new JPanel();
		pnlKlasse.setLayout(new GridLayout(0, 2));
		JLabel lblKlasse = new JLabel("Klasse");
		pnlKlasse.add(lblKlasse);
		txtKlasse = new JTextField();
		txtKlasse.setColumns(10);
		pnlKlasse.add(txtKlasse);
		add(pnlKlasse);
		
		JPanel pnlBild = new JPanel();
		pnlBild.setLayout(new GridLayout(1, 1));
		JLabel lblInfoImage = new JLabel("InfoImage");
		pnlBild.add(lblInfoImage);
		add(pnlBild);
	}
}

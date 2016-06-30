package de.dittich.sv.gui.panel.ausweis;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.SpringLayout;

import de.dittich.sv.gui.VerticalLayout;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

public class PnlSuSInfo extends JPanel {
	
	private static final PnlSuSInfo OBJ = new PnlSuSInfo();
	
	private int txtColSize = 20;
	private int lblXSize = 100;
	private int lblYSize = 10;
	private Dimension lblDim;
	
	private JTextField txtId;
	private JTextField txtSuSId;
	private JTextField txtName;
	private JTextField txtGeb;
	private JTextField txtGeschlecht;
	private JTextField txtKlasse;
	
	private JLabel lblInfoImage;

	
	private PnlSuSInfo() {
		lblDim = new Dimension(lblXSize, lblYSize);
		setLayout(new VerticalLayout());
		JPanel pnlId = new JPanel();
		pnlId.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JLabel lblId = new JLabel("ID");
		lblId.setPreferredSize(lblDim);
		pnlId.add(lblId);
		txtId = new JTextField();
		txtId.setColumns(txtColSize);
		pnlId.add(txtId);
		add(pnlId);
		
		JPanel pnlSuSId = new JPanel();
		pnlSuSId.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JLabel lblSuSId = new JLabel("Sch\u00FCler ID");
		lblSuSId.setPreferredSize(lblDim);
		pnlSuSId.add(lblSuSId);
		txtSuSId = new JTextField();
		txtSuSId.setColumns(txtColSize);
		pnlSuSId.add(txtSuSId);
		add(pnlSuSId);
		
		JPanel pnlName = new JPanel();
		pnlName.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JLabel lblName = new JLabel("Name, Vorname");
		lblName.setPreferredSize(lblDim);
		pnlName.add(lblName);
		txtName = new JTextField();
		txtName.setColumns(txtColSize);
		pnlName.add(txtName);
		add(pnlName);
		
		JPanel pnlGeb = new JPanel();
		pnlGeb.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JLabel lblGeb = new JLabel("Geburtsdatum");
		lblGeb.setPreferredSize(lblDim);
		pnlGeb.add(lblGeb);
		txtGeb = new JTextField();
		txtGeb.setColumns(txtColSize);
		pnlGeb.add(txtGeb);
		add(pnlGeb);
		
		JPanel pnlGeschlecht = new JPanel();
		pnlGeschlecht.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JLabel lblGeschlecht = new JLabel("Geschlecht");
		lblGeschlecht.setPreferredSize(lblDim);
		pnlGeschlecht.add(lblGeschlecht);
		txtGeschlecht = new JTextField();
		txtGeschlecht.setColumns(txtColSize);
		pnlGeschlecht.add(txtGeschlecht);
		add(pnlGeschlecht);
		
		JPanel pnlKlasse = new JPanel();
		pnlKlasse.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JLabel lblKlasse = new JLabel("Klasse");
		lblKlasse.setPreferredSize(lblDim);
		pnlKlasse.add(lblKlasse);
		txtKlasse = new JTextField();
		txtKlasse.setColumns(txtColSize);
		pnlKlasse.add(txtKlasse);
		add(pnlKlasse);
		
		JPanel pnlBild = new JPanel();
		pnlBild.setLayout(new GridLayout(0, 1));
		lblInfoImage = new JLabel();
		pnlBild.add(lblInfoImage);
		add(pnlBild);
	}
	
	public static PnlSuSInfo getInstance(){
		return OBJ;
	}

	public JTextField getTxtId() {
		return txtId;
	}

	public void setTxtId(String txtId) {
		this.txtId.setText(txtId);
	}

	public void setTxtSuSId(String txtSuSId) {
		this.txtSuSId.setText(txtSuSId);
	}

	public void setTxtName(String txtName) {
		this.txtName.setText(txtName);
	}

	public void setTxtGeb(String txtGeb) {
		this.txtGeb.setText(txtGeb);
	}
	
	public void setTxtGeschlecht(String txtGeschlecht) {
		this.txtGeschlecht.setText(txtGeschlecht);
	}

	public void setTxtKlasse(String txtKlasse) {
		this.txtKlasse.setText(txtKlasse);
	}

	public JLabel getLblInfoImage() {
		return lblInfoImage;
	}
}

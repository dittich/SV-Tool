package de.dittich.sv.gui.panel.bilder;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PnlChopImage extends JPanel{
	
	private static final PnlChopImage OBJ = new PnlChopImage();
	private JLabel lblChopImage;
	private ImageIcon chopIcon = null;
	
	private PnlChopImage(){
		lblChopImage = new JLabel();
		add(lblChopImage);
	}
	
	public static PnlChopImage getInstance(){
		return OBJ;
	}

	public JLabel getLblChopImage() {
		return lblChopImage;
	}

	public void setLblChopImage(JLabel lblChopImage) {
		this.lblChopImage = lblChopImage;
	}

	public ImageIcon getChopIcon() {
		return chopIcon;
	}

	public void setChopIcon(ImageIcon chopIcon) {
		this.chopIcon = chopIcon;
	}
}

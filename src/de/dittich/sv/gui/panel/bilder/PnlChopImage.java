package de.dittich.sv.gui.panel.bilder;

import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PnlChopImage extends JPanel{
	
	private static final PnlChopImage OBJ = new PnlChopImage();
	private JLabel lblChopImage;
	private BufferedImage chopImage = null;
	
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

	public BufferedImage getChopImage() {
		return chopImage;
	}

	public void setChopImage(BufferedImage chopImage) {
		this.chopImage = chopImage;
	}
}

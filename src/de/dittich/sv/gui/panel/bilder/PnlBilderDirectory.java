package de.dittich.sv.gui.panel.bilder;

import gui.ImageScaler;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

public class PnlBilderDirectory extends JPanel {

	/**
	 * Create the panel.
	 */
	public PnlBilderDirectory() {
		//setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnBildffnen = new JButton("Bild \u00F6ffnen");
		btnBildffnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileFilter filter = new FileNameExtensionFilter("Bilder", "gif", "png", "jpg"); 
				JFileChooser chooser = new JFileChooser("c:");
				//chooser.addChoosableFileFilter(filter);
				chooser.setFileFilter(filter);
				int rueckgabeWert = chooser.showOpenDialog(null);
				if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
		        {
		             // Ausgabe der ausgewaehlten Datei
		            System.out.println("Die zu öffnende Datei ist: " +
		                  chooser.getSelectedFile().getName());
		            File selectedFile = chooser.getSelectedFile();
					BufferedImage image;
					try {
						image = ImageIO.read(new File(selectedFile.getParent()+"\\"+selectedFile.getName()));
						PnlBilderFoto.getInstance().setImage(image);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
			}
		});
		setLayout(new GridLayout(0, 1, 0, 0));
		add(btnBildffnen);
	}

}

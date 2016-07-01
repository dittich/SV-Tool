package de.dittich.sv.gui.panel.bilder;

import javax.swing.JPanel;
import javax.swing.JTable;

import java.awt.GridLayout;

import javax.swing.JButton;

import de.dittich.sv.fkzs.FKZS;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class PnlBilderZuweisen extends JPanel {

	/**
	 * Create the panel.
	 */
	public PnlBilderZuweisen() {
		setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnZuweisen = new JButton("Zuweisen");
		btnZuweisen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JTable tbl = JTableNamen.getInstance().getTable();
				int idIndex = (int)tbl.getModel().getValueAt(tbl.getSelectedRow(),tbl.getColumn("id").getModelIndex());
				BufferedImage buffImage = PnlChopImage.getInstance().getChopImage();
				System.out.println(idIndex+" - "+buffImage.toString());
				FKZS.getInstance().sqlUpdateImg(idIndex, buffImage);
			}
		});
		add(btnZuweisen);

	}

}

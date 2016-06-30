package de.dittich.sv.gui.panel.bilder;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class PnlBilder extends JPanel {

	/**
	 * Create the panel.
	 */
	public PnlBilder() {
		setLayout(new BorderLayout(0, 0));
		
		/*
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(getHeight()-300);
		splitPane.setRightComponent(PnlBilderFoto.getInstance());
		splitPane.setLeftComponent(new PnlBilderName());
		add(splitPane, BorderLayout.CENTER);
		add(new PnlBilderDirectory(), BorderLayout.NORTH);
		add(new PnlBilderZuweisen(), BorderLayout.SOUTH);
		*/
		
		add(PnlBilderFoto.getInstance(), BorderLayout.CENTER);
		add(new PnlBilderName(), BorderLayout.EAST);
		add(new PnlBilderDirectory(), BorderLayout.NORTH);
		add(new PnlBilderZuweisen(), BorderLayout.SOUTH);
		
	}

}

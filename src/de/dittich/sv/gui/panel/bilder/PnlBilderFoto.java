package de.dittich.sv.gui.panel.bilder;

public class PnlBilderFoto extends DragPanel {

	private static final PnlBilderFoto OBJ = new PnlBilderFoto();
	
	private PnlBilderFoto() {
		
	}
	
	public static PnlBilderFoto getInstance(){
		return OBJ;
	}
}

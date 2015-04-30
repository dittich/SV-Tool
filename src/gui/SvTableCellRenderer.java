package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class SvTableCellRenderer extends DefaultTableCellRenderer{
	
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component component = super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column);
		boolean select = (boolean) table.getModel().getValueAt(row, (table.getColumn("selektiert").getModelIndex() ));
		Object imageType = (Object)table.getModel().getValueAt(row, (table.getColumn("bild").getModelIndex() ));
		
		if( select ) {
			component.setBackground(Color.green);
		}
		else {
			component.setBackground(Color.white);
		}
		
		if( imageType == null){
			component.setForeground(Color.red);
		}
		else {
			component.setForeground(Color.black);
		}
		
		return component;
	};
}

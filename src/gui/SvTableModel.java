package gui;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class SvTableModel extends DefaultTableModel{

	public SvTableModel(Vector rows, Vector columnNames){
		super(rows, columnNames);
	}
}

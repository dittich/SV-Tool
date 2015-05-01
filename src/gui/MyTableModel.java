package gui;


import javax.swing.table.*;
import javax.swing.JTable;
import java.util.*;
/**
* <p>Überschrift: </p>
*
* <p>Beschreibung: </p>
*
* <p>Copyright: Copyright (c) 2003</p>
*
* <p>Organisation: </p>
*
* @author Tobias Viehweger
* @version 1.0
*/
public class MyTableModel extends AbstractTableModel {
	
	private int nonEditCols[] = null;
	protected Vector data = new Vector();
	protected Vector columnNames = new Vector();
	protected Vector columnIds = new Vector();
	protected Vector columnHidden = new Vector();
	
	public MyTableModel(Object[] names, int[] ids, boolean[] hidden) {
		if (names.length == ids.length && names.length == hidden.length) {
			for (int i = 0; i < names.length; i++) {
				columnNames.addElement(names[i]);
				columnIds.addElement(ids[i] + "");
				columnHidden.addElement(hidden[i] + "");
			}
		}
	}
	
	public void addRow(Object[] data) {
		addRow(toVector(data));
	}
	
	public void addRowVec(Vector vec) {
		addRow(vec);
	}
	
	private Vector toVector(Object[] data) {
		Vector ret = new Vector();
		for (int i = 0; i < data.length; i++) {
			ret.addElement(data[i]);
		}
		return ret;
	}
	
	public void addRow(Vector d) {
		insertRow(data.size(), d);
	}
	
	public void setColumnHidden(int column, boolean hidden) throws Exception{
		if (column < columnIds.size()) {
			setColumnHidden((String)columnNames.elementAt(columnIds.indexOf(column+"")),hidden);
		}else {
			throw new Exception("Fehler: Index größer als Spaltenanzahl");
		}
	}
	
	public void setColumnHidden(String name, boolean hidden) throws Exception {
		if (!columnNames.contains(name))
			throw new Exception("Fehler: Spalte nicht gefunden");
		int index = columnNames.indexOf(name);
		if (hidden) {
			if (!isHidden(index)) {
				setHidden(index, true);
			}
		}else {
			if (isHidden(index)) {
				setHidden(index, false);
			}
		}
		fireTableStructureChanged();
	}
	
	private void setHidden (int column ,boolean hidden) {
		columnHidden.setElementAt(hidden+"", column);
	}
	
	public boolean isHidden(int column) {
		return (columnHidden.elementAt(column).equals("false")) ? false : true;
	}
	
	public void removeRow(int row) {
		data.removeElementAt(row);
		super.fireTableRowsDeleted(row, row);
	}
	
	public String getColumnName(int column) {
		Object id = null;
		if (column < columnNames.size()) {
			id = columnNames.elementAt(getColNonHidden(column)) ;
		}
		return (id == null) ? super.getColumnName(column)
				: id.toString();
	}
	
	public void setNotEditableColumns(int[] columns) {
		nonEditCols = columns;
	}
	
	public boolean isCellEditable1(int row, int column) {
		if (nonEditCols != null) {
			boolean ret = true;
			for (int i = 0; i < nonEditCols.length; i++) {
				if (getColNonHidden(column) == nonEditCols[i])
					ret = false;
			}
			return ret;
		}
		else {
			throw new NullPointerException(
			"Erst Spalten definieren! (setNonEditableColumns())");
		}
	}
	
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		( (Vector) data.elementAt(rowIndex)).setElementAt(aValue, columnIndex);
	}
	
	public int getColumnCount() {
		return columnNames.size() - getHidden();
	}
	
	public int getRowCount() {
		return data.size();
	}
	
	public Vector getDataVector() {
		return data;
	}
	public void setDataVector(Vector data) {
		this.data = data;
		fireTableDataChanged();
	}
	
	public void insertRow(int row, Vector rowData) {
	   data.insertElementAt(rowData, row);
	   fireTableRowsInserted(row, row);
	}
	
	public Object getValueAt(int row, int col) {
		Object ret = null;
		if (row < data.size()) {
			if (col < ( (Vector) data.elementAt(row)).size()) {
				if (nothingIsHidden()) {
					ret = ( (Vector) data.elementAt(row)).elementAt(col);
				}
				else {
					int skip = 0;
					Vector info = ( (Vector) data.elementAt(row));
					ret = ( (Vector) data.elementAt(row)).elementAt(getColNonHidden(col));
				}
		    }
		}
		return ret;
	}
	
	public Object[] getRow (int row)
	{
		if (row < data.size()){
			return ((Vector)data.elementAt(row)).toArray();
		}
		return null;
	}
	
	public void replaceAll(String column, String with){
		for (int i = 0; i< data.size(); i++){
			Vector row = (Vector)data.elementAt(i);
			int index = columnNames.indexOf(column);
			if (index < row.size())
				row.setElementAt(with, index);
		}
		fireTableDataChanged();
	}
	
	//Gibt den Spaltenindex zurück, der zu col gehört
	private int getColNonHidden(int col) {
		int newCol = 0;
		int count = 0;
		int tmp = 0;
		while (count != col) {
			if (!isHidden(tmp)) {
				count++;
			}
			tmp++;
		}
		if (isHidden(tmp))
			tmp = getNextNonHidden(tmp);
	
		newCol = tmp;
		return newCol;
	}
	
	private int getNextNonHidden(int col) {
		int ret = col;
		while (isHidden(ret)) {
			ret++;
		}
		return ret;
	}
	
	private int getHidden() {
		int skip = 0;
		for (int i = 0; i < columnNames.size(); i++) {
			if (isHidden(i))
				skip++;
		}
		return skip;
	}
	
	public boolean nothingIsHidden() {
		boolean ret = true;
		for (int i = 0; i < columnHidden.size(); i++) {
			if (columnHidden.elementAt(i).equals("true")) {
				ret = false;
				break;
			}
		}
		return ret;
	}
	
	public void replaceOnly(String column, int[] rows, String with){
		int index = columnNames.indexOf(column);
		for (int i = 0; i < rows.length; i++){
			Vector row = (Vector) data.elementAt(rows[i]);
			if (index < row.size())
			row.setElementAt(with, index);
		}
		fireTableDataChanged();
	}
	
	public void replaceWhere(String column, String where, String with){
		int index = columnNames.indexOf(column);
		for (int i = 0; i < data.size(); i++){
		Vector row = (Vector) data.elementAt(i);
			if (index < row.size() && row.elementAt(index).equals(where))
			row.setElementAt(with, index);
		}
		fireTableDataChanged();
	}
}
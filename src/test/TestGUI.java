package test;

import gui.DBDienste;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import svt.SVTool;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.util.LinkedList;

public class TestGUI {

	private JFrame frame;
	private JScrollPane scroll;
	private SVTool svtool;
	private JScrollPane scrollPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestGUI window = new TestGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestGUI() {
		svtool = new SVTool();
		svtool.dbConnect();
		System.out.println(svtool.isDbConnected());
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 735, 470);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSet rs = svtool.sqlQuery("SELECT * FROM sv_schueler");
				JTable t = new JTable(DbUtils.resultSetToTableModel(rs));
				t.setBounds(20, 55, 668, 323);
				scrollPane.setViewportView(t);
				
				/*
				t.setBounds(39, 85, 597, 282);
				frame.getContentPane().add(t);
				frame.validate();
				frame.repaint();
				frame.getContentPane().remove(table);
				*/
			}
		});
		btnNewButton.setBounds(10, 11, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		
		
		scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(20, 55, 668, 323);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setBounds(20, 55, 668, 323);
		
		scrollPane.setViewportView(table);
	}
}

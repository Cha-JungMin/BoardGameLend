package com.boardgame.application;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.SystemColor;

public class SelectGenre extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTable table;
	static Connection con;
	static String selectedName;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectGenre frame = new SelectGenre(con, selectedName);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				
			}
		});
		
		
	}

	/**
	 * Create the frame.
	 */
	public SelectGenre(Connection con, String name) {
		this.con = con;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 477, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setTitle("장르 선택");
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column"
			}
		));
		table.setCellSelectionEnabled(true);
		table.setRowHeight(20);
		table.setBounds(12, 92, 340, 220);
		contentPane.add(table);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
//		textArea.setText("검색");
		textArea.setBounds(12, 48, 198, 33);
		contentPane.add(textArea);
		
		JButton btnNewButton = new JButton("장르명 검색");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(238, 49, 114, 33);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("<html>선택 장르<br>적용하기</html>");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(363, 92, 88, 220);
		contentPane.add(btnNewButton_1);
		
		JTextPane textPane = new JTextPane();
		textPane.setBackground(SystemColor.menu);
		textPane.setEditable(false);
		textPane.setText("선택한 게임:");
		textPane.setBounds(12, 10, 88, 33);
		contentPane.add(textPane);
		
		JTextPane selectedGame = new JTextPane();
		selectedGame.setEditable(false);
		selectedGame.setBackground(SystemColor.menu);
		selectedGame.setBounds(112, 10, 88, 33);
		selectedGame.setText(name);
		contentPane.add(selectedGame);
		
		
		
		loadGenre(con);
	}
	
	
    public static void loadGenre(Connection con) {
    	ResultSet result = com.boardgame.db.BoardPack.getGenre(con);
    	try {
			int num = 0;
			int row = 0;
			int col = 0;
    		while (result.next()) {
                table.setValueAt(result.getObject(1), row, col);
                col++;
                if (col >= table.getColumnCount()) { 
                    col = 0; 
                    row++; 
                }
			}
		} catch (SQLException e) {
			System.out.print("SQL 예외: ");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}

package com.boardgame.application;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
	static int board_id;
	String selectedGenre;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectGenre frame = new SelectGenre(con, board_id, selectedName);
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
	public SelectGenre(Connection con, int board_id, String name) {
		this.con = con;
		this.board_id = board_id;
		this.selectedName = name;
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
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = table.getSelectedRow();
                int selectedColumn = table.getSelectedColumn();

                if (selectedRow != -1 && selectedColumn != -1) {
                    selectedGenre = (String) table.getValueAt(selectedRow, selectedColumn);
                }
            }
        });
		
		
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
//		textArea.setText("검색");
		textArea.setBounds(12, 48, 198, 33);
		contentPane.add(textArea);
		
		JButton btnNewButton = new JButton("장르명 검색");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String genreName = textArea.getText();
				searchGenre(con, genreName);
			}
		});
		btnNewButton.setBounds(222, 49, 114, 33);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("<html>선택 장르<br>적용하기</html>");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedGenre != null && board_id != 0) {	
					addGenre(con, board_id, selectedGenre);
				}
			}
		});
		btnNewButton_1.setBounds(363, 92, 88, 168);
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
		selectedGame.setText(name);
		selectedGame.setBounds(112, 10, 88, 33);
		contentPane.add(selectedGame);
		
		JButton btnNewButton_2 = new JButton("장르 만들기");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createGenre(con, textArea.getText());
				loadGenre(con);
			}
		});
		btnNewButton_2.setBounds(337, 49, 114, 33);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_2_1 = new JButton("장르 삭제");
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteGenre(con, selectedGenre);
				loadGenre(con);
			}
		});
		btnNewButton_2_1.setBounds(364, 279, 87, 33);
		contentPane.add(btnNewButton_2_1);
		
		loadGenre(con);
	}
	
	
    public static void loadGenre(Connection con) {
    	ResultSet result = com.boardgame.db.GenrePack.viewGenre(con);
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
    
    public void searchGenre(Connection con, String genre) {
    	ResultSet result = com.boardgame.db.GenrePack.searchGenre(con, genre);
    	clearTable();
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
    
    public void addGenre(Connection con, int board_id, String genre) {
    	if (genre != null) {
    		com.boardgame.db.GenrePack.addGenre(con, board_id, genre);
    		JOptionPane.showMessageDialog(null, "변경이 완료되었습니다.");
    	} else {
    		JOptionPane.showMessageDialog(null, "게임을 선택 후 장르를 추가해주세요.");
    	}
    }
    
    public void createGenre(Connection con, String genre) {
    	if (genre != null) {
    		com.boardgame.db.GenrePack.createGenre(con, genre);
    		JOptionPane.showMessageDialog(null, "새로운 장르를 생성하였습니다.");
    	}
    }
    
    public void deleteGenre(Connection con, String genre) {
    	if (genre != null) {
    		com.boardgame.db.GenrePack.deleteGenre(con, genre);
    		JOptionPane.showMessageDialog(null, "장르를 삭제했습니다.");
    		clearTable();
    	}
    }
    
    public void clearTable() {
    	for (int row = 0; row < table.getRowCount(); row++) {
            for (int col = 0; col < table.getColumnCount(); col++) {
                table.setValueAt(null, row, col);
            }
    	}
    }
    
}

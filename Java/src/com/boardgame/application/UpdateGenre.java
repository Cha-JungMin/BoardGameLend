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
import java.awt.SystemColor;

public class UpdateGenre extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTable table;
	static Connection con;
	static String selectedName;
	static int board_id;
	UpdateBoardGame updateBoardGame;
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
	 * @wbp.parser.constructor
	 */
	public UpdateGenre(Connection con, int board_id, String name, UpdateBoardGame updateBoardGame) {
		this(con, board_id, name);
		this.updateBoardGame = updateBoardGame;
	}
	/**
	 * Create the frame.
	 */
	public UpdateGenre(Connection con, int board_id, String name) {
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

		
		JButton btnNewButton_2_1 = new JButton("장르 삭제");
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteGenre(con, selectedGenre);
				loadGenre(con);
			}
		});
		btnNewButton_2_1.setBounds(364, 279, 87, 33);
		contentPane.add(btnNewButton_2_1);
		
		JButton btnNewButton_2_1_1 = new JButton("장르 추가");
		btnNewButton_2_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new SelectGenre(con, board_id, selectedName, updateBoardGame);
        		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        		frame.setLocationRelativeTo(null);
                frame.setVisible(true); 
                dispose();
			}
		});
		btnNewButton_2_1_1.setBounds(364, 90, 87, 33);
		contentPane.add(btnNewButton_2_1_1);
		
		loadGenre(con);
	}
	
	
    public static void loadGenre(Connection con) {
    	ResultSet result = com.boardgame.db.GenrePack.getGenreByGame(con, board_id);
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
   
    
    public void deleteGenre(Connection con, String genre) {
    	if (genre != null) {
    		com.boardgame.db.GenrePack.deleteGenreByGame(con, board_id, genre);
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

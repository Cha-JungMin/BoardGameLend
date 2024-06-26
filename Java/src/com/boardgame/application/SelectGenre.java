package com.boardgame.application;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class SelectGenre extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTable table;
	static String selectedName;
	static int board_id;
	UpdateBoardGame updateBoardGame;
	String selectedGenre;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectGenre frame = new SelectGenre(board_id, selectedName);
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
	public SelectGenre(int board_id, String name, UpdateBoardGame updateBoardGame) {
		this(board_id, name);
		this.updateBoardGame = updateBoardGame;
	}

	
	public SelectGenre(int board_id, String name) {
		SelectGenre.selectedName = name;
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
				searchGenre(genreName);
			}
		});
		btnNewButton.setBounds(222, 49, 114, 33);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("<html>선택 장르<br>적용하기</html>");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedGenre != null && board_id != 0) {	
					addGenre(board_id, selectedGenre);
				}
			}
		});
		btnNewButton_1.setBounds(363, 92, 88, 113);
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
				createGenre(textArea.getText());
				loadGenre();
			}
		});
		btnNewButton_2.setBounds(337, 49, 114, 33);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_2_1 = new JButton("장르명 삭제");
		btnNewButton_2_1.setFont(new Font("굴림", Font.PLAIN, 10));
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteGenre(selectedGenre);
				loadGenre();
			}
		});
		btnNewButton_2_1.setBounds(364, 279, 87, 33);
		contentPane.add(btnNewButton_2_1);
		
		JButton btnNewButton_3 = new JButton("장르 변경하기");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new UpdateGenre(board_id, selectedName, updateBoardGame);
        		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        		frame.setLocationRelativeTo(null);
                frame.setVisible(true); 
                dispose();
			}
		});
		btnNewButton_3.setBounds(222, 10, 156, 33);
		contentPane.add(btnNewButton_3);
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					loadGenre();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}
	
	
    public static void loadGenre() {
    	ResultSet result = com.boardgame.db.GenrePack.viewGenre();
    	try {
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
    
    public void searchGenre(String genre) {
    	ResultSet result = com.boardgame.db.GenrePack.searchGenre(genre);
    	clearTable();
    	try {
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
    
    public void addGenre(int board_id, String genre) {
    	if (genre != null) {
    		com.boardgame.db.GenrePack.addGenre(board_id, genre);
    		if (updateBoardGame != null) {
    			JTextPane genreText = updateBoardGame.textPane_2;
    			if (genreText.getText().equals("장르없음")) {
    				genreText.setText(genre);
    			} else {
    				String text = genreText.getText();
    				genreText.setText(text + ", " + genre);
    			}
    			
    			
    		}
    		JOptionPane.showMessageDialog(null, "변경이 완료되었습니다.");
    		dispose();
    	} else {
    		JOptionPane.showMessageDialog(null, "게임을 선택 후 장르를 추가해주세요.");
    	}
    }
    
    public void createGenre(String genre) {
    	if (genre != null) {
    		com.boardgame.db.GenrePack.createGenre(genre);
    		JOptionPane.showMessageDialog(null, "새로운 장르를 생성하였습니다.");
    	}
    }
    
    public void deleteGenre(String genre) {
    	if (genre != null) {
    		com.boardgame.db.GenrePack.deleteGenre(genre);
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

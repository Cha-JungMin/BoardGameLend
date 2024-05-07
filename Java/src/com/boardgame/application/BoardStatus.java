package com.boardgame.application;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class BoardStatus extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private JTextArea textArea_5, pageNumber;
    static Connection con;
    int board_id;
    String selectName, description;
    int copy, min_people, max_people, min_playtime, max_playtime, rental_fee;
    
    private int currentPage = 1;
    private int pageSize = 11;
    private int totalPage = 1;
    
    public BoardStatus() {
    	setBackground(SystemColor.menu);
        setLayout(null);
        
        
        table = new JTable();
        table.setFillsViewportHeight(true);
        table.setModel(new DefaultTableModel(
        	new Object[][] {
        		{null, null, null, null, null, null, null, null, null, null},
        		{null, null, null, null, null, null, null, null, null, null},
        		{null, null, null, null, null, null, null, null, null, null},
        		{null, null, null, null, null, null, null, null, null, null},
        		{null, null, null, null, null, null, null, null, null, null},
        		{null, null, null, null, null, null, null, null, null, null},
        		{null, null, null, null, null, null, null, null, null, null},
        		{null, null, null, null, null, null, null, null, null, null},
        		{null, null, null, null, null, null, null, null, null, null},
        		{null, null, null, null, null, null, null, null, null, null},
        		{null, null, null, null, null, null, null, null, null, null},
        	},
        	new String[] {
        		"New column", "board_title", "description", "copy", "genre", "min_people", "max_people", "min_play_time", "max_play_time", "rental_fee"
        	}
        ));
        table.getColumnModel().getColumn(0).setPreferredWidth(1);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(1);
        table.getColumnModel().getColumn(1).setPreferredWidth(95);
        table.getColumnModel().getColumn(2).setPreferredWidth(220);
        table.getColumnModel().getColumn(3).setPreferredWidth(60);
        table.getColumnModel().getColumn(4).setPreferredWidth(151);
        table.getColumnModel().getColumn(5).setPreferredWidth(60);
        table.getColumnModel().getColumn(6).setPreferredWidth(60);
        table.getColumnModel().getColumn(7).setPreferredWidth(60);
        table.getColumnModel().getColumn(8).setPreferredWidth(60);
        table.getColumnModel().getColumn(9).setPreferredWidth(95);
        table.setCellSelectionEnabled(true);
        table.setBounds(26, 89, 639, 275);
        table.setRowHeight(25);
        add(table);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 5; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        
        JButton btnNewButton_1 = new JButton("보드게임 추가");
        btnNewButton_1.setBounds(677, 95, 109, 36);
        add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("장르 추가");
        btnNewButton_2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JFrame frame = new SelectGenre(con, board_id, selectName);
        		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼을 누르면 프레임이 닫히도록 설정
        		frame.setLocationRelativeTo(null);
                frame.setVisible(true); // 프레임을 보이게 함
        	}
        });
        btnNewButton_2.setBounds(677, 147, 109, 36);
        add(btnNewButton_2);
        
        JButton btnNewButton_2_1 = new JButton("돌아가기");
        btnNewButton_2_1.setBounds(677, 328, 109, 36);
        add(btnNewButton_2_1);
        
        JTextArea textArea = new JTextArea();
        textArea.setText("이름");
        textArea.setEditable(false);
        textArea.setBackground(SystemColor.menu);
        textArea.setBounds(26, 61, 68, 29);
        add(textArea);
        
        JTextArea textArea_1 = new JTextArea();
        textArea_1.setText("설명");
        textArea_1.setEditable(false);
        textArea_1.setBackground(SystemColor.menu);
        textArea_1.setBounds(94, 61, 152, 29);
        add(textArea_1);
        
        JTextArea textArea_2 = new JTextArea();
        textArea_2.setText("개수");
        textArea_2.setEditable(false);
        textArea_2.setBackground(SystemColor.menu);
        textArea_2.setBounds(253, 61, 52, 29);
        add(textArea_2);
        
        JTextArea textArea_3 = new JTextArea();
        textArea_3.setText("장르");
        textArea_3.setBackground(SystemColor.menu);
        textArea_3.setBounds(306, 61, 68, 29);
        add(textArea_3);
        
        JTextArea textArea_4 = new JTextArea();
        textArea_4.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea_4.setText("최소/최대 인원");
        textArea_4.setBackground(SystemColor.menu);
        textArea_4.setBounds(405, 62, 92, 29);
        add(textArea_4);
        
        JTextArea textArea_6 = new JTextArea();
        textArea_6.setFont(new Font("Monospaced", Font.PLAIN, 10));
        textArea_6.setText("최소/최대 플레이 시간");
        textArea_6.setEditable(false);
        textArea_6.setBackground(SystemColor.menu);
        textArea_6.setBounds(497, 64, 114, 29);
        add(textArea_6);
        
        JTextArea textArea_6_2 = new JTextArea();
        textArea_6_2.setText("대여료(일)");
        textArea_6_2.setBackground(SystemColor.menu);
        textArea_6_2.setBounds(611, 61, 68, 29);
        add(textArea_6_2);
        
        JButton btnNewButton_2_2 = new JButton("새로고침");
        btnNewButton_2_2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		currentPage = 1;
        		pageNumber.setText(Integer.toString(currentPage));
        		loadBoardGame();
        	}
        });
        btnNewButton_2_2.setBounds(677, 282, 109, 36);
        add(btnNewButton_2_2);
        
        pageNumber = new JTextArea();
        pageNumber.setText("1");
        pageNumber.setEditable(false);
        pageNumber.setBackground(SystemColor.menu);
        pageNumber.setBounds(264, 398, 37, 28);
        add(pageNumber);
        
        JButton btnNewButton_1_1 = new JButton("게임 정보 수정");
        btnNewButton_1_1.setFont(new Font("굴림", Font.PLAIN, 11));
        btnNewButton_1_1.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		if (selectName == null) {
        			JOptionPane.showMessageDialog(null, "먼저 게임을 선택해 주세요", "오류", JOptionPane.ERROR_MESSAGE);
        			return;
        		}
        		JFrame frame = new EditBoardGame(con, board_id, selectName, description,
        	    copy, min_people, max_people, min_playtime, max_playtime, rental_fee);
        		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
//                frame.setSize(400, 300); // 프레임의 크기 설정
        		frame.setLocationRelativeTo(null);
                frame.setTitle("보드게임 추가");
                frame.setVisible(true); 
        	}
        });
        btnNewButton_1_1.setBounds(677, 193, 109, 36);
        add(btnNewButton_1_1);
        
        JButton btnNewButton_1_1_1 = new JButton("보드게임 삭제");
        btnNewButton_1_1_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (selectName == null) {
        			JOptionPane.showMessageDialog(null, "먼저 게임을 선택해 주세요", "오류", JOptionPane.ERROR_MESSAGE);
        			return;
        		}
        		int result = JOptionPane.showConfirmDialog(null, selectName + "을(를) 삭제하시겠습니까?", "게임 삭제", JOptionPane.YES_NO_OPTION);
        		if (result == JOptionPane.YES_OPTION) {
                	com.boardgame.db.BoardPack.deleteBoardGame(con, board_id);
                	JOptionPane.showMessageDialog(null, selectName + "을(를) 삭제하였습니다.", "성공", JOptionPane.PLAIN_MESSAGE);
                	selectName = null;
                } 
                
        		loadBoardGame();
        	}
        });
        btnNewButton_1_1_1.setBounds(677, 236, 109, 36);
        add(btnNewButton_1_1_1);
        
        textArea_5 = new JTextArea();
        textArea_5.setText("전체 페이지:");
        textArea_5.setEditable(false);
        textArea_5.setVisible(false);
        textArea_5.setBackground(SystemColor.menu);
        textArea_5.setBounds(405, 398, 96, 29);
        add(textArea_5);

        JButton btnNewButton = new JButton("▶");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
                if (currentPage < totalPage) {
                    currentPage++;
                    pageNumber.setText(Integer.toString(currentPage));
                    loadBoardGame();
                }
        	}
        });
        btnNewButton.setBounds(317, 399, 58, 23);
        add(btnNewButton);
        
        JButton btnNewButton_3 = new JButton("◀");
        btnNewButton_3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 if (currentPage > 1) {
                     currentPage--;
                     pageNumber.setText(Integer.toString(currentPage));
                     loadBoardGame();
                 }
        	}
        });
        btnNewButton_3.setBounds(171, 399, 58, 23);
        add(btnNewButton_3);
        
       
        
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // 선택된 행의 인덱스를 가져옴
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) { 
                    
                	if (table.getValueAt(selectedRow, 0) != null) {   		
                		board_id = ((BigDecimal) table.getValueAt(selectedRow, 0)).intValue();
                		description = (String) table.getValueAt(selectedRow, 2);
                		copy = ((BigDecimal) table.getValueAt(selectedRow, 3)).intValue();
                		min_people = ((BigDecimal) table.getValueAt(selectedRow, 5)).intValue();
                		max_people = ((BigDecimal) table.getValueAt(selectedRow, 6)).intValue();
                		min_playtime = ((BigDecimal) table.getValueAt(selectedRow, 7)).intValue();
                		max_playtime = ((BigDecimal) table.getValueAt(selectedRow, 8)).intValue();
                		rental_fee = ((BigDecimal) table.getValueAt(selectedRow, 9)).intValue();
                	}
                	selectName = (String) table.getValueAt(selectedRow, 1);
                }
            }
            
        });
        
        btnNewButton_1.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		JFrame frame = new AddBoardGame(con);
        		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
//                frame.setSize(400, 300); // 프레임의 크기 설정
        		frame.setLocationRelativeTo(null);
                frame.setTitle("보드게임 추가");
                frame.setVisible(true); 
        	}
        });
    }
    
    public void loadBoardGame() {
    	for (int row = 0; row < table.getRowCount(); row++) {
            for (int col = 0; col < table.getColumnCount(); col++) {
                table.setValueAt(null, row, col);
            }
    	}
    	
    	 int startRow = (currentPage - 1) * pageSize;
         int endRow = currentPage * pageSize -1;
    	
    	ResultSet result = com.boardgame.db.BoardPack.getBoardGameStatement(con);
    	try {
    		int columnCount = result.getMetaData().getColumnCount();
			int row = 0;
    		while (result.next()) {
    			if (startRow <= row && row <= endRow) { 				
    				for (int i = 1; i <= columnCount; i++) {
//                	rowData[i-1] = result.getObject(i);
    					table.setValueAt(result.getObject(i), row - startRow, i-1);		
    				}
    			}
                row++; 
			}
    		
    		totalPage = row / table.getColumnCount() + 1;
    		this.textArea_5.setText("전체 페이지: " + totalPage);
    		textArea_5.setVisible(true);
		} catch (SQLException e) {
			System.out.print("SQL 예외: ");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    
    }
}

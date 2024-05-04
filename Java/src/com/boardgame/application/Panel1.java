package com.boardgame.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.JTextArea;
import java.awt.Font;


public class Panel1 extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable table;
    static Connection con;
    private DefaultTableModel statemodel;
    String selectName;
    public Panel1() {
    	setBackground(SystemColor.menu);
        setLayout(null);
        statemodel = new DefaultTableModel(
                new Object[][] {
                	{"asdasd", "111", "33", "3444", null, null, null, null, null},
            		{null, null, null, null, null, null, null, null, null},
            		{null, null, null, null, null, null, null, null, null},
            		{null, null, null, null, null, null, null, null, null},
            		{null, null, null, null, null, null, null, null, null},
            		{null, null, null, null, null, null, null, null, null},
            		{null, null, null, null, null, null, null, null, null},
            		{null, null, null, null, null, null, null, null, null},
            		{null, null, null, null, null, null, null, null, null},
            		{null, null, null, null, null, null, null, null, null},
            		{null, null, null, null, null, null, null, null, null},
                },
                new String[] {
                    "board_title", "description", "copy", "genre", "min_people",
                    "max_people", "min_play_time", "max_play_time", "rental_fee"
                }
            );
        
        table = new JTable();
        table.setFillsViewportHeight(true);
        table.setModel(new DefaultTableModel(
        	new Object[][] {
        		{null, "asdasd", "111", "33", "3444", null, null, null, null, null},
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
        table.getColumnModel().getColumn(0).setPreferredWidth(15);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(1);
        table.getColumnModel().getColumn(1).setPreferredWidth(95);
        table.getColumnModel().getColumn(2).setPreferredWidth(220);
        table.getColumnModel().getColumn(4).setPreferredWidth(105);
        table.getColumnModel().getColumn(9).setPreferredWidth(95);
        table.setCellSelectionEnabled(true);
        table.setBounds(26, 89, 639, 275);
        table.setRowHeight(25);
        add(table);
        
        JButton btnNewButton_1 = new JButton("보드게임 추가");
        btnNewButton_1.setBounds(677, 95, 109, 36);
        add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("장르 추가");
        btnNewButton_2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JFrame frame = new SelectGenre(con, selectName);
        		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼을 누르면 프레임이 닫히도록 설정
        		frame.setLocationRelativeTo(null);
                frame.setVisible(true); // 프레임을 보이게 함
        	}
        });
        btnNewButton_2.setBounds(677, 147, 109, 36);
        add(btnNewButton_2);
        
        JButton btnNewButton_2_1 = new JButton("돌아가기");
        btnNewButton_2_1.setBounds(677, 310, 109, 36);
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
        textArea_2.setBounds(244, 61, 52, 29);
        add(textArea_2);
        
        JTextArea textArea_3 = new JTextArea();
        textArea_3.setText("장르");
        textArea_3.setBackground(SystemColor.menu);
        textArea_3.setBounds(300, 61, 68, 29);
        add(textArea_3);
        
        JTextArea textArea_4 = new JTextArea();
        textArea_4.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea_4.setText("최소/최대 인원");
        textArea_4.setBackground(SystemColor.menu);
        textArea_4.setBounds(380, 59, 99, 29);
        add(textArea_4);
        
        JTextArea textArea_6 = new JTextArea();
        textArea_6.setFont(new Font("Monospaced", Font.PLAIN, 10));
        textArea_6.setText("최소/최대 플레이 시간");
        textArea_6.setEditable(false);
        textArea_6.setBackground(SystemColor.menu);
        textArea_6.setBounds(484, 61, 114, 29);
        add(textArea_6);
        
        JTextArea textArea_6_2 = new JTextArea();
        textArea_6_2.setText("대여료(분)");
        textArea_6_2.setBackground(SystemColor.menu);
        textArea_6_2.setBounds(597, 61, 68, 29);
        add(textArea_6_2);
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // 선택된 행의 인덱스를 가져옴
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) { // 선택된 행이 있는 경우
                    // 해당 행의 첫 번째 열의 값을 selectName 변수에 할당
                    selectName = (String) table.getValueAt(selectedRow, 1);
                    System.out.println("값변경:" + selectName);
                }
            }
            
        });
        
        btnNewButton_1.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		JFrame frame = new AddBoardGame();
        		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼을 누르면 프레임이 닫히도록 설정
//                frame.setSize(400, 300); // 프레임의 크기 설정
        		frame.setLocationRelativeTo(null);
                frame.setTitle("보드게임 추가");
                frame.setVisible(true); // 프레임을 보이게 함
        	}
        });
    }
    
    public void loadBoardGame() {
    	ResultSet result = com.boardgame.db.BoardPack.getBoardGameStatement(con);
    	try {
    		int columnCount = result.getMetaData().getColumnCount();
			int col = 0;
    		while (result.next()) {
//				String board_title = result.getString(1);
//                String description = result.getString(2);
//                int copy = result.getInt(3);
//                String genre = result.getString(4);
//                int min_people = result.getInt(5);
//                int max_people = result.getInt(6);
//                int min_play_time = result.getInt(7);
//                int max_play_time = result.getInt(8);
//                int rental_fee = result.getInt(9);
//				Object[] rowData = new Object[columnCount];
				
                for (int i = 1; i <= columnCount; i++) {
//                	rowData[i-1] = result.getObject(i);
                	table.setValueAt(result.getObject(i), col, i-1);
                }
                col++; 
			}
		} catch (SQLException e) {
			System.out.print("SQL 예외: ");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}

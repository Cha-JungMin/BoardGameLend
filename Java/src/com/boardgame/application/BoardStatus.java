package com.boardgame.application;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import javax.swing.JScrollPane;


public class BoardStatus extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private JTextArea textArea_5;
    private boolean isAsc = false;
    int board_id;
    String selectName, description, genre;
    int copy, min_people, max_people, min_playtime, max_playtime, rental_fee;
    

	public BoardStatus() {
    	setBackground(SystemColor.menu);
        setLayout(null);
        
        textArea_5 = new JTextArea();
        textArea_5.setText("총 0개의 결과");
        textArea_5.setEditable(false);
        textArea_5.setBackground(SystemColor.menu);
        textArea_5.setBounds(502, 414, 114, 29);
        add(textArea_5);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(26, 61, 969, 327);
        add(scrollPane);
        
        
        table = new JTable();
        scrollPane.setViewportView(table);
        table.setFillsViewportHeight(true);
        table.setModel(new DefaultTableModel(
        	new Object[][] {
        		{null, null, null, null, null, null, null, null, null, null},
        	},
        	new String[] {
        		"New column", "\uAC8C\uC784 \uC774\uB984", "\uAC8C\uC784 \uC124\uBA85", "\uAC1C\uC218", "\uD3EC\uD568 \uC7A5\uB974", "\uCD5C\uC18C \uC778\uC6D0", "\uCD5C\uB300 \uC778\uC6D0", "\uCD5C\uC18C \uD50C\uB808\uC774 \uC2DC\uAC04", "\uCD5C\uB300 \uD50C\uB808\uC774 \uC2DC\uAC04", "\uB300\uC5EC\uB8CC"
        	}
        ) {
			Class[] columnTypes = new Class[] {
        		Object.class, Object.class, Object.class, Integer.class, Object.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class
        	};
        	public Class getColumnClass(int columnIndex) {
        		return columnTypes[columnIndex];
        	}
        });
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
        table.setRowHeight(25);
 
		table.getTableHeader().addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        int col = table.columnAtPoint(e.getPoint());
		        DefaultTableModel model = (DefaultTableModel) table.getModel();
		        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
		        table.setRowSorter(sorter);
		        
		        if (isAsc == false)  {
		        	isAsc = true;
		        	sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(col, SortOrder.ASCENDING)));
		        } else {
		        	isAsc = false;
		        	sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(col, SortOrder.DESCENDING)));
		        }
		    }
		});
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) { 
                    
                	if (table.getValueAt(selectedRow, 0) != null) {   		
                		board_id = ((BigDecimal) table.getValueAt(selectedRow, 0)).intValue();
                		description = (String) table.getValueAt(selectedRow, 2);
                		copy = ((BigDecimal) table.getValueAt(selectedRow, 3)).intValue();
                		genre = (String) table.getValueAt(selectedRow, 4);
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
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 5; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        
        JButton btnNewButton_1 = new JButton("보드게임 추가");
        btnNewButton_1.setBounds(1007, 87, 109, 36);
        add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("장르 추가");
        btnNewButton_2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JFrame frame = new SelectGenre(board_id, selectName);
        		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        		frame.setLocationRelativeTo(null);
                frame.setVisible(true); 
        	}
        });
        btnNewButton_2.setBounds(1007, 139, 109, 36);
        add(btnNewButton_2);
        
        JButton btnNewButton_2_1 = new JButton("대여현황");
        btnNewButton_2_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JFrame frame = new RentalStatus();
        		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        		frame.setLocationRelativeTo(null);
                frame.setVisible(true); 
        	}
        });
        btnNewButton_2_1.setFont(new Font("굴림", Font.PLAIN, 25));
        btnNewButton_2_1.setBounds(650, 392, 136, 63);
        add(btnNewButton_2_1);
        
        JButton btnNewButton_2_2 = new JButton("새로고침");
        btnNewButton_2_2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		refresh();
        	}
        });
        btnNewButton_2_2.setBounds(1007, 274, 109, 36);
        add(btnNewButton_2_2);
        
        JButton btnNewButton_1_1 = new JButton("게임 정보 수정");
        btnNewButton_1_1.setFont(new Font("굴림", Font.PLAIN, 11));
        btnNewButton_1_1.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		if (selectName == null) {
        			JOptionPane.showMessageDialog(null, "먼저 게임을 선택해 주세요", "오류", JOptionPane.ERROR_MESSAGE);
        			return;
        		}
        		JFrame frame = new UpdateBoardGame (board_id, selectName, genre, description,
        	    copy, min_people, max_people, min_playtime, max_playtime, rental_fee, BoardStatus.this);
        		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        		frame.setLocationRelativeTo(null);
                frame.setVisible(true); 
        	}
        });
        btnNewButton_1_1.setBounds(1007, 185, 109, 36);
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
                	com.boardgame.db.BoardPack.deleteBoardGame(board_id);
                	JOptionPane.showMessageDialog(null, selectName + "을(를) 삭제하였습니다.", "성공", JOptionPane.PLAIN_MESSAGE);
                	selectName = null;
                	refresh();
                } 
                
        	}
        });
        btnNewButton_1_1_1.setBounds(1007, 228, 109, 36);
        add(btnNewButton_1_1_1);

        
        JButton btnSearchFilter = new JButton("조회 필터");
        btnSearchFilter.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JFrame frame = new SearchFilter (BoardStatus.this);
                		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
                		frame.setLocationRelativeTo(null); // 위치 
                        frame.setVisible(true); 		
        	}
        });
        btnSearchFilter.setFont(new Font("굴림", Font.PLAIN, 16));
        btnSearchFilter.setBounds(26, 15, 109, 36);
        add(btnSearchFilter);
        
        btnNewButton_1.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		JFrame frame = new AddBoardGame(BoardStatus.this);
        		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        		frame.setLocationRelativeTo(null);
                frame.setTitle("보드게임 추가");
                frame.setVisible(true); 
        	}
        });
    }
    
    public void loadBoardGame(ResultSet resultSet) {
    	DefaultTableModel model = (DefaultTableModel) table.getModel();
    	model.setRowCount(0);
    	
    	try {
			int colCount = table.getColumnCount();
			int cnt = 0;
			while (resultSet.next()) {
				Object[] row = new Object[colCount];
				for (int i = 1; i <= colCount; i++) {
		            row[i-1] = resultSet.getObject(i); 
				}	
				
				model.addRow(row);
				cnt++;
			}

			textArea_5.setText("총 " + cnt +"개의 결과");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    public void refresh() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ResultSet result = com.boardgame.db.BoardPack.getBoardGameStatement();
					loadBoardGame(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
    }
}

package com.boardgame.application;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.boardgame.panel.DatePicker;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class RentalStatistic extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	public static DatePicker startDate;
	public static DatePicker endDate;
	private boolean isAsc = false;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RentalStatistic frame = new RentalStatistic();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public RentalStatistic() {
		
		setTitle("게임별 통계");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 539, 620);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 189, 501, 297);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
			},
			new String[] {
				"\uAC8C\uC784 \uC774\uB984", "\uB9E4\uCD9C", "\uD310\uB9E4\uB7C9", "\uB313\uAE00 \uC218", "\uD3C9\uC810 \uC218", "\uD3C9\uADE0 \uD3C9\uC810"
			}
		) {
			Class[] columnTypes = new Class[] {
				Object.class, Integer.class, Integer.class, Integer.class, Integer.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPane.setViewportView(table);
		
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
		
		JButton btnNewButton = new JButton("닫기");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 26));
		btnNewButton.setBounds(12, 501, 501, 58);
		contentPane.add(btnNewButton);
		
		startDate = new DatePicker(200,26);
		startDate.setLocation(12, 32);
		startDate.setSize(209, 42);
		startDate.setDateFromToday(-1);
		contentPane.add(startDate);
		startDate.setLayout(null);
		
		endDate = new DatePicker(200,26);
		endDate.setLocation(12, 114);
		endDate.setSize(209, 42);
		endDate.setDateFromToday(1);
		contentPane.add(endDate);
		endDate.setLayout(null);
		
		startDate.setDateFromToday(-1);
		endDate.setDateFromToday(1);
		
		JTextPane _TextPane = new JTextPane();
		_TextPane.setFont(new Font("굴림", Font.PLAIN, 25));
		_TextPane.setText("~");
		_TextPane.setEditable(false);
		_TextPane.setBackground(SystemColor.control);
		_TextPane.setBounds(12, 74, 30, 30);
		contentPane.add(_TextPane);
		
		JButton btnNewButton_1 = new JButton("게임별 통계");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							loadRentalStatistic(getRentalStatisticByGame());
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				thread.start();
			}
		});
		btnNewButton_1.setFont(new Font("굴림", Font.PLAIN, 26));
		btnNewButton_1.setBounds(304, 20, 209, 58);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("회원별 통계");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							loadRentalStatistic(getRentalStatisticByMember());
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				thread.start();
			}
		});
		btnNewButton_1_1.setFont(new Font("굴림", Font.PLAIN, 26));
		btnNewButton_1_1.setBounds(304, 98, 209, 58);
		contentPane.add(btnNewButton_1_1);
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					loadRentalStatistic(getRentalStatisticByGame());
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}
	
	private ResultSet getRentalStatisticByGame() {
		ResultSet result = com.boardgame.db.RentalPack.getRentalStatisticByGame(startDate.getDatafomat(), endDate.getDatafomat());
		return result;
	}
	private ResultSet getRentalStatisticByMember() {
		ResultSet result = com.boardgame.db.RentalPack.getRentalStatisticByMember(startDate.getDatafomat(), endDate.getDatafomat());
		return result;
	}
	
	
	private void loadRentalStatistic(ResultSet result) {
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    model.setRowCount(0); 

	    try {
	        ResultSetMetaData metaData = result.getMetaData();
	        int columnCount = metaData.getColumnCount();

	       
	        if (columnCount > 0 && metaData.getColumnLabel(1).equals("게임 이름")) {   
	            model.setColumnCount(6); 
	            model.setColumnIdentifiers(new Object[]{"게임 이름", "매출", "판매량", "댓글 수", "평점 수", "평균 평점"});

	            while (result.next()) {
	                String gameName = result.getString(1);
	                int revenue = result.getInt(2);
	                int salesQuantity = result.getInt(3);
	                int commentCount = result.getInt(4);
	                int gradeCount = result.getInt(5);
	                double avgGrade = result.getDouble(6);

	                model.addRow(new Object[]{gameName, revenue, salesQuantity, commentCount, gradeCount, avgGrade});
	            }
	        } else if (columnCount > 0 && metaData.getColumnLabel(1).equals("이름")) {
	            model.setColumnCount(6); 
	            model.setColumnIdentifiers(new Object[]{"이름", "매출", "판매량", "댓글 수", "평점 수", "평균 평점"});

	            while (result.next()) {
	                String memberName = result.getString(1);
	                int revenue = result.getInt(2);
	                int salesQuantity = result.getInt(3);
	                int commentCount = result.getInt(4);
	                int gradeCount = result.getInt(5);
	                double avgGrade = result.getDouble(6);

	                model.addRow(new Object[]{memberName, revenue, salesQuantity, commentCount, gradeCount,avgGrade });
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
}
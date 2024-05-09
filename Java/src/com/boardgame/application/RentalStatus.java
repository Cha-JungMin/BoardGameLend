package com.boardgame.application;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.boardgame.panel.DatePicker;

public class RentalStatus extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DatePicker	datepick1, datepick2;
	private JTable table;
	private JTextField userTextField;
	private JTextField boardTextField;
	JTextPane resultCountTextPane;
	private boolean isAsc = false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RentalStatus frame = new RentalStatus();
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
	public RentalStatus() {
		setTitle("대여 현황 관리");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 822, 839);
		contentPane =  new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DatePicker startDate = new DatePicker(200,26);
		startDate.setLocation(97, 32);
		startDate.setSize(200, 42);
		startDate.setDateFromToday(-1);
		contentPane.add(startDate);
		startDate.setLayout(null);
		
		DatePicker endDate = new DatePicker(200,26);
		endDate.setLocation(416, 32);
		endDate.setSize(200, 42);
		endDate.setDateFromToday(1);
		contentPane.add(endDate);
		endDate.setLayout(null);
		
		JTextPane startDateTextPane = new JTextPane();
		startDateTextPane.setEditable(false);
		startDateTextPane.setBackground(SystemColor.control);
		startDateTextPane.setText("시작일");
		startDateTextPane.setBounds(36, 32, 49, 30);
		contentPane.add(startDateTextPane);
		
		JTextPane endDateTextPane = new JTextPane();
		endDateTextPane.setEditable(false);
		endDateTextPane.setBackground(SystemColor.control);
		endDateTextPane.setText("종료일");
		endDateTextPane.setBounds(355, 32, 49, 30);
		contentPane.add(endDateTextPane);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 165, 642, 409);
		contentPane.add(scrollPane);

		
		table = new JTable();
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
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"rental_id", "\uC81C\uD488 \uBC88\uD638", "\uBCF4\uB4DC\uAC8C\uC784", "\uD68C\uC6D0", "\uB300\uC5EC \uC2DC\uC791\uC77C", "\uB300\uC5EC \uC885\uB8CC\uC77C", "\uB300\uC5EC\uB8CC", "\uC0C1\uD0DC", "\uD3C9\uC810", "\uB313\uAE00"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(1).setPreferredWidth(72);
		table.getColumnModel().getColumn(2).setPreferredWidth(61);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(6).setPreferredWidth(50);
		table.getColumnModel().getColumn(8).setPreferredWidth(50);
		table.getColumnModel().getColumn(8).setMaxWidth(50);
		table.getColumnModel().getColumn(9).setMaxWidth(75);
		scrollPane.setViewportView(table);
//		table.setBounds(25, 165, 642, 418);
//		contentPane.add(table);
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
		
		
		
		
		userTextField = new JTextField();
		userTextField.setBounds(97, 93, 200, 35);
		contentPane.add(userTextField);
		userTextField.setVisible(false);
		userTextField.setColumns(10);
		
		JTextPane userTextPane = new JTextPane();
		userTextPane.setText("사용자명");
		userTextPane.setEditable(false);
		userTextPane.setVisible(false);
		userTextPane.setBackground(SystemColor.control);
		userTextPane.setBounds(25, 93, 60, 30);
		contentPane.add(userTextPane);
		
		boardTextField = new JTextField();
		boardTextField.setColumns(10);
		boardTextField.setVisible(false);
		boardTextField.setBounds(416, 93, 200, 35);
		contentPane.add(boardTextField);
		//  총매출 판매량 
		JTextPane boardTextPane = new JTextPane();
		boardTextPane.setText("보드게임");
		boardTextPane.setEditable(false);
		boardTextPane.setVisible(false);
		boardTextPane.setBackground(SystemColor.control);
		boardTextPane.setBounds(344, 93, 60, 30);
		contentPane.add(boardTextPane);
		
		JRadioButton rdbtnByDate = new JRadioButton("기간 별 검색");
		rdbtnByDate.setToolTipText("기간 별 검색");
        rdbtnByDate.setSelected(true);
		rdbtnByDate.setBounds(675, 165, 119, 23);
		contentPane.add(rdbtnByDate);
		
		JRadioButton rdbtnByUserName = new JRadioButton("사용자명 검색");
		rdbtnByUserName.setBounds(675, 190, 119, 23);
		contentPane.add(rdbtnByUserName);
		
		JRadioButton rdbtnBoardGame = new JRadioButton("보드게임 검색");
		rdbtnBoardGame.setBounds(675, 215, 119, 23);
		contentPane.add(rdbtnBoardGame);
		
		JRadioButton rdbtnNewRadioButton_1_2_1 = new JRadioButton("평점있는 보드게임");
		rdbtnNewRadioButton_1_2_1.setBounds(675, 483, 145, 23);
		contentPane.add(rdbtnNewRadioButton_1_2_1);
		
		JRadioButton rdbtnNewRadioButton_1_2_1_1 = new JRadioButton("대여중인 보드게임");
		rdbtnNewRadioButton_1_2_1_1.setBounds(675, 458, 145, 23);
		contentPane.add(rdbtnNewRadioButton_1_2_1_1);
		
		JButton btnSearch_1 = new JButton("보유현황");
		btnSearch_1.setFont(new Font("굴림", Font.PLAIN, 25));
		btnSearch_1.setBounds(25, 603, 133, 42);
		contentPane.add(btnSearch_1);
		
		resultCountTextPane = new JTextPane();
		resultCountTextPane.setText("총 ...개의 결과");
		resultCountTextPane.setEditable(false);
		resultCountTextPane.setBackground(SystemColor.control);
		resultCountTextPane.setBounds(507, 588, 215, 30);
		contentPane.add(resultCountTextPane);
		
		JButton btnSearch_2 = new JButton("<html>보드게임 <br>상태변경</html>");
		btnSearch_2.setEnabled(false);
		btnSearch_2.setFont(new Font("굴림", Font.PLAIN, 16));
		btnSearch_2.setBounds(679, 512, 115, 42);
		contentPane.add(btnSearch_2);
		
		JButton btnSearch = new JButton("검색");
		btnSearch.setFont(new Font("굴림", Font.PLAIN, 25));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String start_date = null;
				String end_date = null;
				String username = null;
				String board = null;

				if (rdbtnByDate.isSelected()) {
					start_date = startDate.getDatafomat();
					end_date = endDate.getDatafomat();
				}    
                if (rdbtnByUserName.isSelected()) {
                    username = userTextField.getText();
                    if (username.equals("")) {username = null;}
                }
                if (rdbtnBoardGame.isSelected()) {
                    board = boardTextField.getText();
                    if (board.equals("")) {board = null;}
                }
				get_rental_history(start_date, end_date, username, board);
			}
		});
		btnSearch.setBounds(680, 32, 104, 42);
		contentPane.add(btnSearch);

		
        ActionListener radioListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (rdbtnByDate.isSelected()) {
                	startDate.setVisible(true);
                	endDate.setVisible(true);
                    startDateTextPane.setVisible(true);
                    endDateTextPane.setVisible(true);
                } else {
                	startDate.setVisible(false);
                	endDate.setVisible(false);
                    startDateTextPane.setVisible(false);
                    endDateTextPane.setVisible(false);
                }

                if (rdbtnByUserName.isSelected()) {
                    userTextPane.setVisible(true);
                    userTextField.setVisible(true);
                } else {
                    userTextPane.setVisible(false);
                    userTextField.setVisible(false);
                }

                if (rdbtnBoardGame.isSelected()) {
                    boardTextPane.setVisible(true);
                    boardTextField.setVisible(true);
                } else {
                    boardTextPane.setVisible(false);
                    boardTextField.setVisible(false);
                }
            }
        };
        rdbtnByDate.addActionListener(radioListener);
        rdbtnByUserName.addActionListener(radioListener);
        rdbtnBoardGame.addActionListener(radioListener);
        
        get_rental_history();
    }
	
	public void load_rental_hishory(ResultSet resultSet) {
		  DefaultTableModel model = (DefaultTableModel) table.getModel();
		   model.setRowCount(0); 
		
		try {
			ResultSetMetaData metaData = resultSet.getMetaData();
			int colCount = metaData.getColumnCount();
			int cnt = 0;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			while (resultSet.next()) {
				Object[] row = new Object[colCount];
				for (int i = 1; i <= colCount; i++) {
					 if (metaData.getColumnType(i) == Types.DATE || metaData.getColumnType(i) == Types.TIMESTAMP) {
				            Date date = resultSet.getDate(i); 
				            String formattedDate = (date != null) ? dateFormat.format(date) : ""; 
				            row[i-1] = formattedDate; 
				        } else {
				            row[i-1] = resultSet.getObject(i); 
				        }
					 if (i == 8) {
						 System.out.println(resultSet.getObject(i));
					 }
				}
				model.addRow(row);
				cnt++;
			}

			resultCountTextPane.setText("총 " + cnt +"개의 결과");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void get_rental_history() {
		get_rental_history(null, null, null, null);
	}
	public void get_rental_history(String start_date, String end_date, String username, String title) {
		ResultSet result = com.boardgame.db.RentalPack.getRentalHistory(start_date, end_date, username, title);
		load_rental_hishory(result);
	}
}


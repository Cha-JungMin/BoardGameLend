package com.boardgame.panel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.boardgame.db.SQLCall;
import com.boardgame.menubar.UserMenuBar;
import com.boardgame.window.RentalStatusUpdateWindow;

import oracle.jdbc.internal.OracleTypes;

public class MyRentalPanel extends JPanel {

	private int		userId;
	private JFrame	frame;
	
	private JMenuBar				menuBar;
	private JLabel					labelStart, labelEnd;
	private DatePicker				startPickerPanel, endPickerPanel;
	private TablePanel				tbMyRental;
	private ArrayList<Object[]> 	listMyRental, dataMyRental;
	

	public MyRentalPanel(JFrame _frame, int user_id) {
		frame = _frame;
		userId = user_id;
		menuBar = new UserMenuBar(frame, userId);
		labelStart = new JLabel("Start Date:");
		labelEnd = new JLabel("End Date:");
		startPickerPanel = new DatePicker(200, 30);
		endPickerPanel = new DatePicker(200, 30);
		initialization();
	}
	
	private void initialization() {
		setLayout(null);
		frame.setJMenuBar(menuBar);
		labelStart.setBounds(20, 10, 80, 30);
		labelEnd.setBounds(400, 10, 80, 30);
		startPickerPanel.setBounds(180, 15, 200, 30);
		endPickerPanel.setBounds(570, 15, 200, 30);
		tbMyRental = new TablePanel(
				20, 55,
				750, 470,
				new String[] {"Product", "BoardGame", "Rental Date", "Rental Status", "Rental Fee"},
				null
				);
		setDateTime();
		add(labelStart);
		add(labelEnd);
		add(startPickerPanel);
		add(endPickerPanel);
		add(tbMyRental);
		startPickerPanel.setChangeEvent(() -> {
            if (getDateComparison()) setDateTime();
            getDataList();
        });
        endPickerPanel.setChangeEvent(() -> {
            if (getDateComparison()) setDateTime();
            getDataList();
        });
	}
	
	private void setDateTime() {
		startPickerPanel.setNowDate();
		endPickerPanel.setNowDate();
	}
	
	private boolean getDateComparison() {
		LocalDate startDate = startPickerPanel.getLocalDate();
		LocalDate endDate = endPickerPanel.getLocalDate();
		return startDate.isAfter(endDate);
	}
	
	private void setDataTable() {
		tbMyRental.setTableData(dataMyRental.stream().toArray(Object[][]::new));
		tbMyRental.getSltItem(num -> {
			int rental_detail_id = (int) listMyRental.get(num)[0];
			String status = String.valueOf(listMyRental.get(num)[6]);
			if (status.equals("대여중") || status.equals("대여예정")) {
				new RentalStatusUpdateWindow(frame, userId, rental_detail_id);
			}
			if (status.equals("대여완료")) {
				
			}
//			listMyRental.get(num)
		});
	}
	
	private void getDataList() {
		new SQLCall(
				"{ call rental_pack.get_member_rental_detail(?, ?, ?, ?) }",
				cs -> {
					try {
						cs.registerOutParameter(4, OracleTypes.CURSOR);
						cs.setInt(1, userId);
						cs.setString(2, startPickerPanel.getDatafomat());
						cs.setString(3, endPickerPanel.getDatafomat());
						cs.execute();
						ResultSet resultSet = (ResultSet) cs.getObject(4);
						listMyRental = new ArrayList<>();
						dataMyRental = new ArrayList<>();
						while (resultSet.next()) {
							listMyRental.add(new Object[] {
									resultSet.getInt(1),
									resultSet.getString(2),
									resultSet.getString(3),
									resultSet.getString(4),
									resultSet.getString(5),
									resultSet.getInt(6),
									resultSet.getString(7),
									resultSet.getInt(8)
							});
							dataMyRental.add(new Object[] {
									resultSet.getString(2),
									resultSet.getString(3),
									resultSet.getString(4) + " ~ " + resultSet.getString(5),
									resultSet.getString(7),
									resultSet.getInt(8)
							});
						}
						setDataTable();
					} catch (SQLException err) {
						System.err.format("SQL State: %s\n%s", err.getSQLState(), err.getMessage());
						err.printStackTrace();
					}
				});
	}
	
}

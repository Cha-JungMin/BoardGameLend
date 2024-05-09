package com.boardgame.panel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.boardgame.db.SQLCall;
import com.boardgame.menubar.UserMenuBar;
import com.boardgame.window.RentalWindow;

import oracle.jdbc.internal.OracleTypes;

public class RentalPanel extends JPanel {
	
	private int				userId;
	private JFrame			frame;
	
	private JMenuBar		menuBar;
	
	private JLabel			labelStart, labelEnd;
	
	private DatePicker		startPickerPanel, endPickerPanel;
	
	private TablePanel		tbIsRental, tbCheckRental;
	private ArrayList<Object[]> tbListData;
	
	public RentalPanel(JFrame _frame, int user_id) {
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
		labelEnd.setBounds(20, 50, 80, 30);
		
		setDateTime();
		
		startPickerPanel.setBounds(180, 15, 200, 30);
		endPickerPanel.setBounds(180, 55, 200, 30);
		
		String[] headName = {"Product", "BoardGame", "Rental Fee"};
		add(labelStart);
		add(labelEnd);
		add(startPickerPanel);
		add(endPickerPanel);
		
		tbIsRental = new TablePanel(20, 90, 360, 430, headName, null);
		tbCheckRental = new TablePanel(400, 90, 360, 430, headName, null);
        add(tbIsRental);
        add(tbCheckRental);
        
        startPickerPanel.setChangeEvent(() -> {
        	System.out.println("Selected Date: " + getDateComparison());
            if (getDateComparison()) setDateTime();
            getIsRental();
        });
        endPickerPanel.setChangeEvent(() -> {
        	System.out.println("Selected Date: " + getDateComparison());
            if (getDateComparison()) setDateTime();
            getIsRental();
        });
        
	}
	
	private void getIsRental() {
		new SQLCall(
				"{ call rental_pack.get_available_games(?, ?, ?) }",
				cs -> {
					try {
						cs.registerOutParameter(3, OracleTypes.CURSOR);
						
						cs.setString(1, startPickerPanel.getDatafomat());
						cs.setString(2, endPickerPanel.getDatafomat());
						cs.execute();
						ResultSet resultSet = (ResultSet) cs.getObject(3);
						tbListData = new ArrayList<>();
						while (resultSet.next()) {
							Object[] objData = {
									resultSet.getInt(1),
									resultSet.getString(2),
									resultSet.getInt(3)
									};
							tbListData.add(objData);
						}
						tbIsRental.setTableData(tbListData.stream().toArray(Object[][]::new));
					} catch (SQLException err) {
						System.err.format("SQL State: %s\n%s", err.getSQLState(), err.getMessage());
						err.printStackTrace();
					}
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
	
}

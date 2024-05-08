package com.boardgame.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.Year;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.boardgame.db.SQLCall;
import com.boardgame.menubar.UserMenuBar;
import com.boardgame.window.RentalWindow;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class RentalPanel extends JPanel {
	
	private int				userId;
	private RentalWindow	frame;
	
	private JMenuBar		menuBar;
	
	private JLabel			labelStart, labelEnd;
	
	private UtilDateModel	startModel, endModel;
	private JDatePanelImpl	startDatePanel, endDatePanel;
	private JDatePickerImpl	startDatePicker, endDatePicker;
	
	private TablePanel		tbIsRental, tbCheckRental;
	
	public RentalPanel(RentalWindow _frame, int user_id) {
		frame = _frame;
		userId = user_id;
		menuBar = new UserMenuBar(frame, userId);
		labelStart = new JLabel("Start Date:");
		labelEnd = new JLabel("End Date:");
		startModel = new UtilDateModel();
		endModel = new UtilDateModel();
		startDatePanel = new JDatePanelImpl(startModel);
		endDatePanel = new JDatePanelImpl(endModel);
		startDatePicker = new JDatePickerImpl(startDatePanel);
		endDatePicker = new JDatePickerImpl(endDatePanel);
		initialization();
	}
	
	private void initialization() {
		setLayout(null);
		frame.setJMenuBar(menuBar);
		
		labelStart.setBounds(20, 10, 80, 30);
		labelEnd.setBounds(20, 50, 80, 30);
		
		setDateTime();
		
		startDatePanel.setBounds(180, 15, 200, 30);
		startDatePicker.setBounds(180, 15, 200, 30);
		endDatePanel.setBounds(180, 55, 200, 30);
		endDatePicker.setBounds(180, 55, 200, 30);
		
		String[] headName = {"Product", "BoardGame", "Rental Fee"};
		add(labelStart);
		add(labelEnd);
		add(startDatePicker);
		add(endDatePicker);
		
		tbIsRental = new TablePanel(20, 90, 360, 430, headName, null);
		tbCheckRental = new TablePanel(400, 90, 360, 430, headName, null);
        add(tbIsRental);
        add(tbCheckRental);
        
        startDatePicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                System.out.println("Selected Date: " + getDateComparison());
                if (getDateComparison()) setDateTime();
                getIsRental();
			}
		});
        endDatePicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Selected Date: " + getDateComparison());
				if (getDateComparison()) setDateTime();
				getIsRental();
			}
		});
	}
	
	private void getIsRental() {
		new SQLCall(
				"{ call rental_pack.get_available_games(?, ?, ?) }",
				cs -> {
//					try {
//						cs.registerOutParameter(3, Types.REF_CURSOR);
//						cs.setString(1, txtID.getText());
//						cs.setString(2, new String(txtPwd.getPassword()));
//					} catch (SQLException err) {
//						System.err.format("SQL State: %s\n%s", err.getSQLState(), err.getMessage());
//						err.printStackTrace();
//					}
				});
	}
	
//	private String getDatafomat(UtilDateModel dateModel) {
//		int month = dateModel.getMonth()
//		return
//	}
	
	private void setDateTime() {
		LocalDate now = LocalDate.now();
		startModel.setDate(now.getYear(), now.getMonthValue() - 1, now.getDayOfMonth());
		startModel.setSelected(true);
		endModel.setDate(now.getYear(), now.getMonthValue() - 1, now.getDayOfMonth());
		endModel.setSelected(true);
	}
	
	private boolean getDateComparison() {
		LocalDate startDate = LocalDate.of(
				startModel.getYear(),
				startModel.getMonth() + 1,
				startModel.getDay());
		LocalDate endDate = LocalDate.of(
				endModel.getYear(),
				endModel.getMonth() + 1,
				endModel.getDay());
		return startDate.isAfter(endDate);
	}
	
}

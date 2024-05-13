package com.boardgame.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.boardgame.db.SQLCall;
import com.boardgame.menubar.UserMenuBar;
import com.boardgame.window.Alert;

import oracle.jdbc.internal.OracleTypes;

public class RentalPanel extends JPanel {
	
	private int				userId;
	private JFrame			frame;
	
	private JMenuBar		menuBar;
	private JLabel			labelStart, labelEnd;
	private DatePicker		startPickerPanel, endPickerPanel;
	private TablePanel		tbIsRental, tbCheckRental;
	private JButton			btnRental;
	
	private ArrayList<Object[]> dataRentalList, dataRentalCheckList;
	
	public RentalPanel(JFrame _frame, int user_id) {
		frame = _frame;
		userId = user_id;
		menuBar = new UserMenuBar(frame, userId);
		labelStart = new JLabel("Start Date:");
		labelEnd = new JLabel("End Date:");
		
		startPickerPanel = new DatePicker(200, 30);
		endPickerPanel = new DatePicker(200, 30);
		
		btnRental = new JButton("Rental");
		
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
		
		btnRental.setBounds(685, 30, 75, 30);
		
		String[] headName = {"Product", "BoardGame", "Rental Fee"};
		add(labelStart);
		add(labelEnd);
		add(startPickerPanel);
		add(endPickerPanel);
		add(btnRental);
		
		tbIsRental = new TablePanel(20, 90, 360, 430, headName, null);
		tbCheckRental = new TablePanel(400, 90, 360, 430, headName, null);
		
        add(tbIsRental);
        add(tbCheckRental);
        
        startPickerPanel.setChangeEvent(() -> {
            if (getDateComparison()) setDateTime();
            getIsRental();
        });
        endPickerPanel.setChangeEvent(() -> {
            if (getDateComparison()) setDateTime();
            getIsRental();
        });
        btnRental.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dataRentalCheckList.forEach(v -> {
					sendAddRental((int) v[0]);
				});
			}
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
						dataRentalList = new ArrayList<>();
						dataRentalCheckList = new ArrayList<>();
						while (resultSet.next()) {
							Object[] objData = {
									resultSet.getInt(1),
									resultSet.getString(2),
									resultSet.getInt(3)
									};
							dataRentalList.add(objData);
						}
						setDataTbIsRental();
					} catch (SQLException err) {
						System.err.format("SQL State: %s\n%s", err.getSQLState(), err.getMessage());
						err.printStackTrace();
					}
				});
	}
	
	private void sendAddRental(int copy_id) {
		new SQLCall(
				"{ call rental_pack.add_rental_detail(?, ?, ?, ?, ?) }",
				cs -> {
					try {
						cs.registerOutParameter(5, java.sql.Types.INTEGER);
						cs.setInt(1, copy_id);
						cs.setString(2, startPickerPanel.getDatafomat());
						cs.setString(3, endPickerPanel.getDatafomat());
						cs.setInt(4, userId);
						cs.execute();
						if (cs.getInt(5) == 1) new Alert("해당 보드게임이 대여가 되었습니다.");
						if (cs.getInt(5) == 0) new Alert("해당 보드게임을 대여하지를 못 했습니다.\n 다시 시도해 주세요.");
						getIsRental();
					} catch (SQLException err) {
						System.err.format("SQL State: %s\n%s", err.getSQLState(), err.getMessage());
						err.printStackTrace();
					}
				});
	}
	
	private void setTbCheckRental() {
		tbCheckRental.setTableData(dataRentalCheckList.stream().toArray(Object[][]::new));
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
	
	private void setDataTbIsRental() {
		setTbCheckRental();
		tbIsRental.setTableData(dataRentalList.stream().toArray(Object[][]::new));
		tbIsRental.getSltItem(num -> {
			boolean check = dataRentalCheckList.stream()
					.anyMatch(data -> Arrays.equals(dataRentalList.get(num), data));
			if (!check) dataRentalCheckList.add(
					dataRentalList.stream()
                    .skip(num)
                    .findFirst()
                    .orElse(null)
			);
			setTbCheckRental();
        });
	}
	
}

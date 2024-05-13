package com.boardgame.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.boardgame.db.SQLCall;
import com.boardgame.menubar.UserMenuBar;
import com.boardgame.window.BoardGameDetailWindow;

import oracle.jdbc.internal.OracleTypes;

public class SearchRentalFeePanel extends JPanel {

	private int		userId;
	private JFrame	frame;
	
	private JMenuBar		menuBar;
	private TablePanel		tbGameList;
	private JLabel			labelRentalFee;
	private JTextField		txtRentalFee;
	private JButton			btnSearch;
	
	private ArrayList<Object[]>		dataGameList, dataGameListCopy;

	public SearchRentalFeePanel(JFrame _frame, int user_id) {
		frame = _frame;
		userId = user_id;
		menuBar = new UserMenuBar(frame, userId);
		String[] headName = {"BoardGame", "People", "Time", "Rental Fee"};
		tbGameList = new TablePanel(20, 60, 500, 400, headName, null);
		labelRentalFee = new JLabel("Rental Fee:");
		txtRentalFee = new JTextField();
		btnSearch = new JButton("Search");
		initialization();
	}
	
	private void initialization() {
		setLayout(null);
		frame.setJMenuBar(menuBar);
		labelRentalFee.setBounds(20, 20, 80, 30);
		txtRentalFee.setBounds(100, 27, 100, 20);
		btnSearch.setBounds(205, 27, 75, 20);
		add(tbGameList);
		add(labelRentalFee);
		add(txtRentalFee);
		add(btnSearch);
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getRentalFee(Integer.parseInt(txtRentalFee.getText()));
			}
		});
	}
	
	private void getRentalFee(int rental_fee) {
		new SQLCall(
				"{ call board_pack.get_filter_rental_fee_info(?, ?) }",
				cs -> {
					try {
						cs.registerOutParameter(2, OracleTypes.CURSOR);
						cs.setInt(1, rental_fee);
						cs.execute();
						ResultSet resultSet = (ResultSet) cs.getObject(2);
						dataGameList = new ArrayList<>();
						dataGameListCopy = new ArrayList<>();
						while (resultSet.next()) {
							dataGameList.add(new Object[] {
									resultSet.getInt(1),
									resultSet.getString(2),
									resultSet.getInt(3) + " ~ " + resultSet.getInt(4),
									resultSet.getInt(5) + " ~ " + resultSet.getInt(6),
									resultSet.getInt(7)
							});
							dataGameList.forEach(item -> dataGameListCopy.add(Arrays.copyOfRange(item, 1, item.length)));
						}
						tbGameList.setTableData(dataGameListCopy.stream().toArray(Object[][]::new));
						tbGameList.getSltItem(sltNum -> {
							new BoardGameDetailWindow(frame, userId, (int) dataGameList.get(sltNum)[0]);
						});
					} catch (SQLException err) {
						System.err.format("SQL State: %s\n%s", err.getSQLState(), err.getMessage());
						err.printStackTrace();
					}
				});
	}
	
}

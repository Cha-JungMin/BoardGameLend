package com.boardgame.panel;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.boardgame.db.SQLCall;

import oracle.jdbc.internal.OracleTypes;

public class BoardGameDetailPanel extends JPanel {
	
	private int		userId, boardgameId;
	private JDialog	frame;
	
	private JLabel	labelGameName, labelPeople, labelTime, labelRentalFee,
					labelDetail, labelGenre;

	public BoardGameDetailPanel(JDialog frame, int userId, int boardgameId) {
		this.frame = frame;
		this.userId = userId;
		this.boardgameId = boardgameId;
		initialization();
	}
	
	private void initialization() {
		setLayout(null);
		getGameDetail();
	}
	
	private void createObjects() {
		labelGameName.setBounds(20, 20, 480, 30);
		labelPeople.setBounds(20, 60, 480, 30);
		labelTime.setBounds(20, 100, 480, 30);
		labelRentalFee.setBounds(20, 140, 480, 30);
		labelGenre.setBounds(20, 180, 480, 30);
		labelDetail.setBounds(20, 220, 480, 30);
		add(labelGameName);
		add(labelPeople);
		add(labelTime);
		add(labelRentalFee);
		add(labelGenre);
		add(labelDetail);
	}
	
	private void getGameDetail() {
		new SQLCall(
				"{ call board_pack.get_boardgame_info(?, ?) }",
				cs -> {
					try {
						cs.registerOutParameter(2, OracleTypes.CURSOR);
						cs.setInt(1, boardgameId);
						cs.execute();
						ResultSet resultSet = (ResultSet) cs.getObject(2);
						while (resultSet.next()) {
							labelGameName = new JLabel("Board Game: " + resultSet.getString(2));
							labelPeople = new JLabel("People Number: " + resultSet.getInt(4) + " ~ " + resultSet.getInt(5));
							labelTime = new JLabel("Play Time: " + resultSet.getInt(6) + " ~ " + resultSet.getInt(8));
							labelRentalFee = new JLabel("Rental Fee: " + resultSet.getInt(7));
							labelGenre = new JLabel("Genre:");
							labelDetail = new JLabel("Desc:");
							createObjects();
						}
					} catch (SQLException err) {
						System.err.format("SQL State: %s\n%s", err.getSQLState(), err.getMessage());
						err.printStackTrace();
					}
				});
	}
	
}

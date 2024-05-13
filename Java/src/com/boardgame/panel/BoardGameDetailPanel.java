package com.boardgame.panel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	private TablePanel		tbGenre;
	private TextAreaPanel 	txtArea;

	public BoardGameDetailPanel(JDialog frame, int userId, int boardgameId) {
		this.frame = frame;
		this.userId = userId;
		this.boardgameId = boardgameId;
		initialization();
	}
	
	private void initialization() {
		setLayout(null);
		getGameDetail();
		getGameGenre();
	}
	
	private void createObjects() {
		labelGameName.setBounds(20, 20, 480, 30);
		labelPeople.setBounds(20, 60, 480, 30);
		labelTime.setBounds(20, 100, 480, 30);
		labelRentalFee.setBounds(20, 140, 480, 30);
		labelGenre.setBounds(20, 180, 480, 30);
		labelDetail.setBounds(20, 300, 480, 30);
		add(labelGameName);
		add(labelPeople);
		add(labelTime);
		add(labelRentalFee);
		add(labelGenre);
		add(labelDetail);
		add(txtArea);
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
							txtArea = new TextAreaPanel(20, 340, 440, 150);
							txtArea.setText(resultSet.getString(3));
							txtArea.notEdit();
							createObjects();
						}
					} catch (SQLException err) {
						System.err.format("SQL State: %s\n%s", err.getSQLState(), err.getMessage());
						err.printStackTrace();
					}
				});
	}
	
	private void getGameGenre() {
		new SQLCall(
				"{ call genre_pack.get_board_game_genres(?, ?) }",
				cs -> {
					try {
						cs.registerOutParameter(2, OracleTypes.CURSOR);
						cs.setInt(1, boardgameId);
						cs.execute();
						ResultSet resultSet = (ResultSet) cs.getObject(2);
						tbGenre = new TablePanel(
								20, 220, 450, 80,
								new String[] {"Genre"},
								null);
						ArrayList<Object[]> dataGameList = new ArrayList<>();
						while (resultSet.next()) {
							dataGameList.add(new Object[] {resultSet.getString(1)});
						}
						tbGenre.setTableData(dataGameList.stream().toArray(Object[][]::new));
						add(tbGenre);
					} catch (SQLException err) {
						System.err.format("SQL State: %s\n%s", err.getSQLState(), err.getMessage());
						err.printStackTrace();
					}
				});
	}
}

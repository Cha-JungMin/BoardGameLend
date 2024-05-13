package com.boardgame.panel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.boardgame.db.SQLCall;
import com.boardgame.menubar.UserMenuBar;
import com.boardgame.window.BoardGameDetailWindow;
import com.boardgame.window.SearchGenreWindow;

import oracle.jdbc.internal.OracleTypes;

public class SearchGenrePanel extends JPanel {

	private int userId;
	private JFrame frame;

	private JMenuBar menuBar;
	private SelectBoxPanel sltbox;
	private TablePanel tbGameList;

	private ArrayList<Object[]> dataGenreList,
								dataGameList, dataGameListCopy;
	private String[] dataSltName;

	public SearchGenrePanel(JFrame _frame, int user_id) {
		frame = _frame;
		userId = user_id;
		getGenre();
		menuBar = new UserMenuBar(frame, userId);
		sltbox = new SelectBoxPanel(20, 20, 100, 30, dataSltName);
		String[] headName = { "BoardGame", "People", "Time", "Rental Fee" };
		tbGameList = new TablePanel(20, 60, 500, 400, headName, null);
		initialization();
	}

	private void initialization() {
		setLayout(null);
		frame.setJMenuBar(menuBar);
		sltbox.setChangeEvent(num -> {
			getEenreBoardGameInfo((int) dataGenreList.get(num)[0]);
		});
		add(sltbox);
		add(tbGameList);
	}

	private void getGenre() {
		new SQLCall("{ call genre_pack.get_genre_info(?) }", cs -> {
			try {
				cs.registerOutParameter(1, OracleTypes.CURSOR);
				cs.execute();
				ResultSet resultSet = (ResultSet) cs.getObject(1);
				dataGenreList = new ArrayList<>();
				while (resultSet.next()) {
					dataGenreList.add(new Object[] { resultSet.getInt(1), resultSet.getString(2) });
				}
				if (!dataGenreList.isEmpty())
					dataSltName = dataGenreList.stream().map(row -> row[1]).toArray(String[]::new);
			} catch (SQLException err) {
				System.err.format("SQL State: %s\n%s", err.getSQLState(), err.getMessage());
				err.printStackTrace();
			}
		});
	}

	private void getEenreBoardGameInfo(int genre_id) {
		new SQLCall("{ call genre_pack.get_serch_genre_boardgame_info(?, ?) }", cs -> {
			try {
				cs.registerOutParameter(2, OracleTypes.CURSOR);
				cs.setInt(1, genre_id);
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

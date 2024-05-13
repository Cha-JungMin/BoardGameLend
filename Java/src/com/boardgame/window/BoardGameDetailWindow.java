package com.boardgame.window;

import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.boardgame.panel.BoardGameDetailPanel;

public class BoardGameDetailWindow extends JDialog implements Window {

	private Container container = getContentPane();
	private int userId = 0;
	private int boardgameId = 0;

	public BoardGameDetailWindow(JFrame frame, int user_id, int boardgameId) {
		super(frame, "BoardGameRental", true);
		this.userId = user_id;
		this.boardgameId = boardgameId;
		initialization();
	}
	
	@Override
	public void initialization() {
        setSize(500, 600);
        setLocationRelativeTo(null);
        container.add(new BoardGameDetailPanel(this, this.userId, boardgameId));
        setVisible(true);
	}
	
}

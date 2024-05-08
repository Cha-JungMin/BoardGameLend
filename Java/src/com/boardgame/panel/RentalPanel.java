package com.boardgame.panel;

import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.boardgame.menubar.UserMenuBar;
import com.boardgame.window.RentalWindow;

public class RentalPanel extends JPanel {
	
	private RentalWindow	frame;
	private JMenuBar		menuBar;
	private int				userId;
	
	public RentalPanel(RentalWindow _frame, int user_id) {
		frame = _frame;
		userId = user_id;
		menuBar = new UserMenuBar(frame, userId);
		initialization();
	}
	
	private void initialization() {
		setLayout(null);
		frame.setJMenuBar(menuBar);
	}
	
}

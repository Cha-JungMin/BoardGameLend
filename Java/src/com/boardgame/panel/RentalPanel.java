package com.boardgame.panel;

import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.boardgame.menubar.UserMenuBar;
import com.boardgame.window.RentalWindow;

public class RentalPanel extends JPanel {
	
	private RentalWindow	frame;
	private JMenuBar		menuBar;
	
	public RentalPanel(RentalWindow _frame) {
		frame = _frame;
		menuBar = new UserMenuBar(frame);
		initialization();
	}
	
	private void initialization() {
		setLayout(null);
		frame.setJMenuBar(menuBar);
	}
	
}

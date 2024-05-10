package com.boardgame.panel;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RentalStatusUpdatePanel extends JPanel {

	private int		userId;
	private int		rentalDetailId;
	private JDialog	frame;
	
	public RentalStatusUpdatePanel(JDialog frame, int userId, int rentalDetailId) {
		this.frame = frame;
		this.userId = userId;
		this.rentalDetailId = rentalDetailId;
	}
}

package com.boardgame.window;

import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.boardgame.panel.RentalStatusUpdatePanel;

public class RentalStatusUpdateWindow extends JDialog implements Window {

	private Container container = getContentPane();
	private int userId = 0;
	private int rentalDetailId = 0;

	public RentalStatusUpdateWindow(JFrame frame, int user_id, int rentalDetailId) {
		super(frame, "BoardGameRental", true);
		this.userId = user_id;
		this.rentalDetailId = rentalDetailId;
		initialization();
	}
	
	@Override
	public void initialization() {
        setSize(400, 300);
        setLocationRelativeTo(null);
        container.add(new RentalStatusUpdatePanel(this, this.userId, this.rentalDetailId));
        setVisible(true);
	}
	
}

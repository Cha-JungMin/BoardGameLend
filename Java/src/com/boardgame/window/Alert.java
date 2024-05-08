package com.boardgame.window;

import javax.swing.JOptionPane;

public class Alert {

	public Alert(String msg) {
        JOptionPane.showMessageDialog(
        		null,
        		msg,
        		"BoardGameRental Alert",
        		JOptionPane.INFORMATION_MESSAGE
        );
	}
	
}

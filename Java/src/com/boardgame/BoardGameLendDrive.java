package com.boardgame;

import com.boardgame.db.DBConnection;
import com.boardgame.window.LoginWindow;

public class BoardGameLendDrive {
	
	public static void main(String[] args) {
		LoginWindow window = new LoginWindow();
//		new RentalWindow(5);
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					DBConnection.getConnection();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	
	}
}

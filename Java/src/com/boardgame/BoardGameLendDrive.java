package com.boardgame;

import javax.swing.SwingUtilities;

import com.boardgame.page.Window;

public class BoardGameLendDrive {
	
	public static void main(String[] args) {
//		Window window = new Window();
		SwingUtilities.invokeLater(Window::new);
	}

}

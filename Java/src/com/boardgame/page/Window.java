package com.boardgame.page;

import java.awt.Container;

import javax.swing.JFrame;

public class Window extends JFrame {
	
	private Container container = getContentPane();

	public Window() {
		initialization();
	}
	
	private void initialization() {
		setTitle("BoardGameLend Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(450, 300);
        
        LoginPanel loginPanel = new LoginPanel();
        
        container.add(loginPanel);
        
        setVisible(true);
	}
	
}


package com.boardgame.window;

import java.awt.Container;

import javax.swing.JFrame;

import com.boardgame.panel.LoginPanel;

public class LoginWindow extends JFrame implements Window {
	
	private Container container = getContentPane();

	public LoginWindow() {
		initialization();
	}
	
	@Override
	public void initialization() {
		setTitle("BoardGameRental Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        
        setSize(450, 300);
        setLocationRelativeTo(null);
        
        LoginPanel loginPanel = new LoginPanel(LoginWindow.this);
        container.add(loginPanel);
        
        setVisible(true);
	}
	
}


package com.boardgame.window;

import java.awt.Container;

import javax.swing.JFrame;

import com.boardgame.panel.SearchGenrePanel;

public class SearchGenreWindow extends JFrame implements Window {

	private Container container = getContentPane();
	private int userId = 0;

	public SearchGenreWindow(int user_id) {
		this.userId = user_id;
		initialization();
	}
	
	@Override
	public void initialization() {
		setTitle("BoardGameRental");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        container.add(new SearchGenrePanel(SearchGenreWindow.this, this.userId));
        
        setVisible(true);
	}
	
}
